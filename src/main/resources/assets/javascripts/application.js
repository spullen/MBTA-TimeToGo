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
    model: Direction
});

var Stops = Backbone.Collection.extend({
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

var Router = Backbone.Router.extend({

    routes: {
        '(/)': 'routeList'
    },

    routeList: function() {
        this.swap(App.routesView);
    },

    swap: function(view) {
        $('#application').empty()
        $('#application').html(view.render().el);
    }
});

var App = {
    routes: null,
    router: null
}

function initialize() {
    App.routes = new Routes(window.routes, {parse: true});
    App.routesView = new RouteListView({collection: App.routes}).render();
    App.router = new Router();
    Backbone.history.start();
}

$(function() {
    initialize();

    $(document).on('click', 'a[data-bypass!="true"]', function(evt) {
        var href = $(this).attr('href'),
            protocol = this.protocol + '//';

        if(href.slice(protocol.length) !== protocol) {
            evt.preventDefault();
            context.router.navigate(href, true);
        }
    });
});
