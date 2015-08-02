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

/*var Stop = Backbone.Model.extend({});*/

var Routes = Backbone.Collection.extend({
    model: Route
});

var Directions = Backbone.Collection.extend({
    model: Direction,
    comparator: 'direction_id'
});

var Stops = Backbone.Collection.extend({
    comparator: 'stop_order'
});

var RouteListView = Backbone.View.extend({
    template: Templates.RouteList,
    initialize: function() {
        _.bindAll(this, 'renderRoutes', 'renderRoute');
    },
    render: function() {
        this.$el.html(this.template());
        this.renderRoutes();
        return this;
    },
    renderRoutes: function() {
        this.$('#route-list').empty();
        this.collection.each(this.renderRoute);
    },
    renderRoute: function(route) {
        var v = new RouteListItemView({model: route});
        this.$('#route-list').append(v.render().el);
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
    render: function() {
        this.$el.html(this.template(this.model.attributes));
        // render stops list
        return this;
    },
    leave: function() {
        _.each(this.childViews, function(v) {
            v.remove();
        });
        this.childViews = [];
        this.remove();
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
        console.log('routes list');
    },

    viewRouteDirections: function(routeId) {
        console.log('route view');

        var route = App.routes.findWhere({route_id: routeId});
        var el = '';

        // clean up the current directions view if present
        if(this.directionsView && this.directionsView.leave) {
            this.directionsView.leave()
        }

        if(route) {
            console.log(route);
            console.log(route.directions);
            this.directionsView = new DirectionListView({collection: route.directions});
            el = this.directionsView.render().el;
        } else {
            el = 'No route found for ' + routeId;
        }

        App.routesView.$('#route-directions').html(el);
    }
});

var App = {
    routes: null,
    router: null,
    coords: null
};

function initialize() {
    getPosition().done(function(position) {
        console.log('Retrieved current location');
        console.log(position);
        App.coords = position.coords;
    });

    App.routes = new Routes(window.routes, {parse: true});
    App.routesView = new RouteListView({collection: App.routes}).render(); // this should really be application view
    App.router = new Router();
    Backbone.history.start({pushState: true});
}


function getPosition() {
    var deferred = $.Deferred();

    navigator.geolocation.getCurrentPosition(
        deferred.resolve,
        deferred.reject,
        {}
    );

    return deferred.promise();
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
