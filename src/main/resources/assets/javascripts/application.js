var Route = Backbone.Model.extend({
    parse: function(data) {
        this.directions = new Directions(data.direction, {parse: true});
        delete data.direction;
        return data;
    }
});

var Direction = Backbone.Model.extend({
    parse: function(data) {
        this.stops = new Stops(data.stop);
        delete data.stop;
        return data;
    }
});

var Stop = Backbone.Model.extend({
    initialize: function() {
        this.estimations = new Estimations([], {stop: this});
    }
});

var Routes = Backbone.Collection.extend({
    model: Route
});

var Directions = Backbone.Collection.extend({
    model: Direction,
    comparator: 'direction_id'
});

var Stops = Backbone.Collection.extend({
    model: Stop,
    comparator: 'stop_order'
});

var Estimations = Backbone.Collection.extend({
    initialize: function(data, options) {
        this.stop = options.stop;
    },
    url: function() {
        return '/api/estimations/' + this.stop.get('stop_id');
    }
});

// Being used as the overall application view
var RouteListView = Backbone.View.extend({
    template: Templates.RouteList,
    events: {
        'change .travel_mode': 'updateTravelMode'
    },
    initialize: function() {
        _.bindAll(this, 'renderRoutes', 'renderRoute');
    },
    render: function() {
        this.$el.html(this.template());
        this.renderRoutes();
        this.renderCoordinates();
        return this;
    },
    renderRoutes: function() {
        this.$('#route-list').empty();
        this.collection.each(this.renderRoute);
    },
    renderRoute: function(route) {
        var v = new RouteListItemView({model: route});
        this.$('#route-list').append(v.render().el);
    },
    renderCoordinates: function() {
        //eh..
        var v = new CoordinatesView({model: App.coords});
        this.$('#coordinates').append(v.render().el);
    },
    updateTravelMode: function(e) {
        e.preventDefault();
        App.state.set('travelMode', e.target.value);
    }
});

var RouteListItemView = Backbone.View.extend({
    tagName: 'li',
    className: 'route-list-item',
    template: Templates.RouteListItem,
    render: function() {
        this.$el.html(this.template(this.model.attributes));
        return this;
    }
});

var CoordinatesView = Backbone.View.extend({
    template: Templates.Coordinates,
    initialize: function() {
        this.listenTo(this.model, 'change', this.render);
    },
    render: function() {
        this.$el.html(this.template(this.model.attributes));
        return this;
    }
});

var DirectionListView = Backbone.View.extend({
    template: Templates.DirectionList,
    childViews: [],
    initialize: function() {
        _.bindAll(this, 'renderDirections', 'renderDirection');
    },
    render: function() {
        this.$el.html(this.template());
        this.renderDirections();
        return this;
    },
    renderDirections: function() {
        this.collection.each(this.renderDirection);
    },
    renderDirection: function(direction) {
        var v = new DirectionListItemView({model: direction});
        this.$el.append(v.render().el);
        this.childViews.push(v);
    },
    leave: function() {
        _.each(this.childViews, function(v) {
            v.leave();
        });
        this.childViews = [];
        this.remove();
    }
});

var DirectionListItemView = Backbone.View.extend({
    className: 'direction-list-item',
    template: Templates.DirectionListItem,
    childViews: [],
    initialize: function() {
        _.bindAll(this, 'renderStops', 'renderStop');
    },
    render: function() {
        this.$el.html(this.template(this.model.attributes));
        this.renderStops();
        return this;
    },
    renderStops: function() {
        this.model.stops.each(this.renderStop);
    },
    renderStop: function(stop) {
        var v = new StopListItemView({model: stop});
        this.$('#stop-list').append(v.render().el);
        this.childViews.push(v);
    },
    leave: function() {
        _.each(this.childViews, function(v) {
            if(v.leave) {
                v.leave();
            } else {
                v.remove();
            }
        });
        this.childViews = [];
        this.remove();
    }
});

var StopListItemView = Backbone.View.extend({
    className: 'stop',
    template: Templates.StopListItem,
    events: {
        'click .get-estimations': 'getEstimations'
    },
    initialize: function() {
        _.bindAll(this, 'renderEstimations');
        this.listenTo(this.model.estimations, 'reset', this.renderEstimations);
    },
    render: function() {
        this.$el.html(this.template(this.model.attributes));
        return this;
    },
    getEstimations: function(e) {
        e.preventDefault();
        var self = this;
        this.$('.estimations').html('Updating coordinates and retrieving upcoming trips and T2Gs...');
        App.coords.fetch().done(function(coords) {
            var data = {
                latitude: coords.get('latitude'),
                longitude: coords.get('longitude'),
                travelMode: App.state.get('travelMode')
            };
            self.model.estimations.fetch({data: data, reset: true});
        });
    },
    renderEstimations: function() {
        this.$('.estimations').empty();
        this.$('.estimations').html(Templates.EstimationList({estimations: this.model.estimations.toJSON()}));
    }
});

var Router = Backbone.Router.extend({
    routes: {
        '(/)': 'routeList',
        'routes/:routeId(/)': 'viewRouteDirections'
    },

    initialize: function() {
        $('#application').empty()
        $('#application').html(App.routesView.render().el);
    },

    routeList: function() {
        /* what should be done here since the application view's already been run... */
        console.log('routes list');
    },

    viewRouteDirections: function(routeId) {
        var route = App.routes.findWhere({route_id: routeId});
        var el = '';

        // clean up the current directions view if present
        if(this.directionsView && this.directionsView.leave) {
            this.directionsView.leave()
        }

        if(route) {
            this.directionsView = new DirectionListView({collection: route.directions});
            el = this.directionsView.render().el;
        } else {
            el = '<strong>No route found for ' + routeId + '</strong>';
        }

        App.routesView.$('#route-directions').html(el);
    }
});

var State = Backbone.Model.extend({
    defaults: {
        travelMode: 'walking'
    }
});

var Coords = Backbone.Model.extend({
    fetch: function(options) {
        var deferred = $.Deferred();

        if(this.get('latitude') && this.get('longitude')) {
            console.log("Have coordinates already");
            deferred.resolve(this);
        } else {
            var self = this;
            navigator.geolocation.getCurrentPosition(
                function(position) {
                    self.set({latitude: position.coords.latitude, longitude: position.coords.longitude });
                    deferred.resolve(self);
                },
                deferred.reject,
                {}
            );
        }

        return deferred.promise();
    },
    defaults: {
        latitude: null,
        longitude: null
    }
});

var App = {
    routes: null,
    router: null,
    state: new State(),
    coords: null
};

function initialize() {
    App.coords = new Coords();
    App.coords.fetch();

    App.routes = new Routes(window.routes, {parse: true});
    App.routesView = new RouteListView({collection: App.routes}).render(); // this should really be application view
    App.router = new Router();
    Backbone.history.start({pushState: true});
}

$(function() {
    initialize();

    $(document).on('click', 'a[data-bypass!="true"]', function(evt) {
        var href = $(this).attr('href'),
            protocol = this.protocol + '//';

        if(href.slice(protocol.length) !== protocol) {
            evt.preventDefault();
            App.router.navigate(href, {trigger: true});
        }
    });
});
