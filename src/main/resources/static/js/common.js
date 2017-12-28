let $footer = $("#footer");
let sec = 1000;

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

function animate(item, time, anim, delay = 0) {
    $.when(anim(item, time, delay)).then(function () {
        positionFooter();
        resize();
    });
}

function fadeOut(item, time, delay) {
    item.delay(delay).fadeOut(time);
}

function fadeIn(item, time, delay) {
    item.delay(delay).fadeIn(time);
}

const redirect = url => window.location.replace(url);


function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}