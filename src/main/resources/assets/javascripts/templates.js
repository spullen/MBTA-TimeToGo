var Templates = Templates || {};

// Eh, a little messy, mustache on backend doesn't play too well with inline handlebar templates as it tries to render them
// could probably add some grunt task to compile handlebar files, but this is such a small app

var stringHackRegExp = /[^]*\/\*([^]*)\*\/\}$/;

Templates['RouteList'] = Handlebars.compile(
    (function() {/*
        <h1>Select Routes</h1>
        <ul id="route-list"></ul>
        <div id="route-directions"></div>
    */}).toString().match(stringHackRegExp)[1]
);

Templates['RouteListItem'] = Handlebars.compile(
    (function() {/*
        {{route_name}}
     */}).toString().match(stringHackRegExp)[1]
);

Templates['DirectionList'] = Handlebars.compile(
    (function() {/*
     <div id="direction-list"></div>
     */}).toString().match(stringHackRegExp)[1]
);

Templates['DirectionListItem'] = Handlebars.compile(
    (function() {/*
     {{direction_name}}
     <ul id="stop-list"></ul>
     */}).toString().match(stringHackRegExp)[1]
);

Templates['StopListItem'] = Handlebars.compile(
    (function() {/*

     */}).toString().match(stringHackRegExp)[1]
);


