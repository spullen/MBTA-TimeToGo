var Templates = Templates || {};
Templates['RouteList'] = Handlebars.compile($('#route-list-tpl').html());
Templates['Coordinates'] = Handlebars.compile($('#coordinates-tpl').html());
Templates['RouteListItem'] = Handlebars.compile($('#route-list-item-tpl').html());
Templates['DirectionList'] = Handlebars.compile($('#direction-list-tpl').html());
Templates['DirectionListItem'] = Handlebars.compile($('#direction-list-item-tpl').html());
Templates['StopListItem'] = Handlebars.compile($('#stop-list-item-tpl').html());
Templates['EstimationList'] = Handlebars.compile($('#estimation-list-tpl').html());