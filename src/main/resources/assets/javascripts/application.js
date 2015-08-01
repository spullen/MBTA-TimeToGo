window.App = {
    Models: {},
    Collections: {},
    Views: {},
    Routers: {},
    initialize: function() {

    }
};


$(function() {
    $(document).on('click', 'a[data-bypass!="true"]', function(evt) {
        var href = $(this).attr('href'),
            protocol = this.protocol + '//';

        if(href.slice(protocol.length) !== protocol) {
            evt.preventDefault();
            context.router.navigate(href, true);
        }
    });
});
