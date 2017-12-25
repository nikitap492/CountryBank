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

function animate(item, time, anim, delay) {
    if(arguments.length == 3){
        start(0);
    }else if (arguments.length == 4) {
        start(delay)
    }
    function start(delay){
        $.when(anim(item, time, delay)).then(function () {
            positionFooter();
            resize();
        });
    }
}

function fadeOut(item, time, delay) {
    item.delay(delay).fadeOut(time);
}

function fadeIn(item, time, delay) {
    item.delay(delay).fadeIn(time);
}

const redirect = url => window.location.replace(url);