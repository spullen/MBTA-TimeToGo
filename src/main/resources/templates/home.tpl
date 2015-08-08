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
                <div>
                <div id="coordinates"></div>
                <div id="route-directions"></div>
                """
            }

            script(type: 'text/x-handlebars', id: 'coordinates-tpl') {
                yieldUnescaped """
                {{#if latitude}}
                Current coordinates (Lat, Lon): ({{latitude}}, {{longitude}})
                {{else}}
                No coordinates found, or hasn't finished updating.
                {{/if}}
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
                <div><button class="get-estimations">Retrieve/update current T2G estimations</button></div>
                <div class="estimations"></div>
                """
            }

            script(type: 'text/x-handlebars', id: 'estimation-list-tpl') {
                yieldUnescaped """
                <ul>
                {{#each estimations}}
                    <li>Leave at <strong>{{leave_at}}</strong> to catch the train at <strong>{{sch_arr_dt}}</strong> going to <strong>{{headsign}}</strong>.</li>
                {{else}}
                    <li>No estimates available for travel mode.</li>
                {{/each}}
                </ul>
                """
            }
        }