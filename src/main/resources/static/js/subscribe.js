$(document).ready(function () {
    checkIsSubscribed();
});

$("#subscribe-btn").bind("click", function () {
    let email = $("#subscribe-email").val();
    $.ajax({
        url: '/subscribers',
        type: 'post',
        contentType: "application/json",
        datatype: 'json',
        data: JSON.stringify({
            email: email
        }),
        success: function (msg) {
            showOk(msg);
        },
        error: function (xhr) {
            showError(xhr.responseJSON.message);
        }
    })
});

$("#unfollow-btn").bind("click", function () {
    let email = $("#subscribe-email").val();
    $.ajax({
        url: '/subscribers',
        type: 'DELETE',
        contentType: "application/json",
        datatype: 'json',
        data: JSON.stringify({
            email: email
        }),
        success: function () {
            showOk('Success');
        }
    })
});


function checkIsSubscribed() {
    let subscribe = $("#subscribe-btn");
    let unfollow = $("#unfollow-btn");
    subscribe.hide();
    unfollow.hide();
    let txt = $(".subscribe-text");
    $.ajax({
        url: '/subscribers',
        type: 'GET',
        success: function () {
            unfollow.show();
            txt.text("You have already subscribed");
        },
        error: function () {
            subscribe.show();
            txt.text("You will know all CB news");
        }
    })
}

//todo what's about message?
function showOk(msg) {
    let img = $(".subscribed");
    img.show();
    img.delay(3000).fadeOut(1000);
    checkIsSubscribed();
}

function showError(msg) {
    let img = $(".error-img");
    img.show();
    img.delay(3000).fadeOut(1000);
}
