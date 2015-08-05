package templates.layouts

yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        title("MBTA Time2Go")

        meta(charset: 'utf-8')
        meta(name: 'viewport', content: 'width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1')
        link(rel: 'stylesheet', type: 'text/css', href: '/assets/stylesheets/application.css')
    }
    body {
        mainBody()

        script(type: 'text/javascript', src: 'https://code.jquery.com/jquery-2.1.4.min.js') { }
        script(type: 'text/javascript', src: 'https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.3/handlebars.min.js') { }
        script(type: 'text/javascript', src: 'https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js') { }
        script(type: 'text/javascript', src: 'https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.2.1/backbone-min.js') { }
        script(type: 'text/javascript', src: '/assets/javascripts/templates.js') { }
        script(type: 'text/javascript', src: '/assets/javascripts/application.js') { }
    }
}