package templates

layout 'layouts/main.tpl',
        mainBody: contents {
            div(id: 'application') { }
            script(type: 'text/javascript') {
                yieldUnescaped """
                    window.routes = ${routesJSON};
                """
            }
        }