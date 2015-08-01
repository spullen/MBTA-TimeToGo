var Templates = Templates || {};

// Eh, a little messy, mustache on backend doesn't play too well with inline handlebar templates
// could probably add some grunt task to compile handlebar files, but this is such a small app

var stringHackRegExp = /[^]*\/\*([^]*)\*\/\}$/;

Templates['RouteList'] = Handlebars.compile(
    (function() {/*
        <h1>Select Routes</h1>
        <ul id="route-list"></ul>
    */}).toString().match(stringHackRegExp)[1]
);

Templates['RouteListItem'] = Handlebars.compile(
    (function() {/*
        {{route_name}}
     */}).toString().match(stringHackRegExp)[1]
);


