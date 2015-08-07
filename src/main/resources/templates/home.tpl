package templates

layout 'layouts/main.tpl',
        mainBody: contents {
            div(id: 'application') { }
            script(type: 'text/javascript') {
                yieldUnescaped """
                    window.routes = ${routesJSON};
                """
            }

            script(type: 'text/x-handlebars', id: 'route-list-tpl') {
                yieldUnescaped """
                <h1>Select Routes</h1>
                <!-- could also be a drop down -->
                <ul id="route-list"></ul>
                <select class="travel_mode">
                    <option value="walking" selected="selected">Walking</option>
                    <option value="driving">Driving</option>
                    <option value="bicycling">Bicycling</option>
                </select>
                <div id="route-directions"></div>
                """
            }

            script(type: 'text/x-handlebars', id: 'route-list-item-tpl') {
                yieldUnescaped """
                <a href="/routes/{{route_id}}">{{route_name}}</a>
                """
            }

            script(type: 'text/x-handlebars', id: 'direction-list-tpl') {
                yieldUnescaped """
                <div id="direction-list"></div>
                """
            }

            script(type: 'text/x-handlebars', id: 'direction-list-item-tpl') {
                yieldUnescaped """
                <h2>{{direction_name}}</h2>
                     <ul id="stop-list"></ul>
                """
            }

            script(type: 'text/x-handlebars', id: 'stop-list-item-tpl') {
                yieldUnescaped """
                <h3>{{stop_name}}</h3>
                <div>{{stop_lat}}, {{stop_lon}}</div>
                <div><button class="get-prediction">Retrieve/update current T2G prediction</button></div>
                <div class="predictions"></div>
                """
            }

            script(type: 'text/x-handlebars', id: 'prediction-list-tpl') {
                yieldUnescaped """
                <ul>
                {{#each predictions}}
                    <li>Leave at <strong>{{leave_at}}</strong> to catch the train at <strong>{{sch_arr_dt}}</strong> going to <strong>{{headsign}}</strong>.</li>
                {{else}}
                    <li>No estimates available for travel mode.</li>
                {{/each}}
                </ul>
                """
            }
        }