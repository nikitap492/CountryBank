var $footer = $("#footer");

$(window).bind("load", function () {
    positionFooter();
    resize();
});

function positionFooter() {
    if (($(document.body).height()) <= $(window).height()) {
        $footer.css({
            position: "absolute",
            bottom: 0
        })
    } else {
        $footer.css({
            position: "static"
        })
    }
}

$(document).ready(resize);

function resize() {
    $(window)
        .scroll(positionFooter)
        .resize(positionFooter);
}