var Templates = Templates || {};
Templates['RouteList'] = Handlebars.compile($('#route-list-tpl').html());
Templates['RouteListItem'] = Handlebars.compile($('#route-list-item-tpl').html());
Templates['DirectionList'] = Handlebars.compile($('#direction-list-tpl').html());
Templates['DirectionListItem'] = Handlebars.compile($('#direction-list-item-tpl').html());
Templates['StopListItem'] = Handlebars.compile($('#stop-list-item-tpl').html());
Templates['PredictionList'] = Handlebars.compile($('#prediction-list-tpl').html());