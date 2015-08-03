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
        <a href="/routes/{{route_id}}">{{route_name}}</a>
     */}).toString().match(stringHackRegExp)[1]
);

Templates['DirectionList'] = Handlebars.compile(
    (function() {/*
     <div id="direction-list"></div>
     */}).toString().match(stringHackRegExp)[1]
);

Templates['DirectionListItem'] = Handlebars.compile(
    (function() {/*
     <h2>{{direction_name}}</h2>
     <ul id="stop-list"></ul>
     */}).toString().match(stringHackRegExp)[1]
);

Templates['StopListItem'] = Handlebars.compile(
    (function() {/*
        <h3>{{stop_name}}</h3>
        <div>{{stop_lat}}, {{stop_lon}}</div>
        <div><button class="get-prediction">Retrieve current T2G prediction</button></div>
        <div class="predictions"></div>
     */}).toString().match(stringHackRegExp)[1]
);


