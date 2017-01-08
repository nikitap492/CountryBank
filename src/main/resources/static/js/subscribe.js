$(document).ready(function () {
    checkIsSubscribed();
});

$("#subscribe-btn").bind("click", function () {
    var email = $("#subscribe-email").val();
    $.ajax({
        url: '/api/subscriber/add',
        type: 'post',
        contentType: "application/json",
        datatype: 'json',
        data: JSON.stringify({
            email: email
        }),
        success: function (msg) {
            showOk(msg);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            showError(xhr.responseText);
        }
    })
});

$("#unfollow-btn").bind("click", function () {
    var email = $("#subscribe-email").val();
    $.ajax({
        url: '/api/subscriber/delete',
        type: 'post',
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
    var subscribe = $("#subscribe-btn")
    var unfollow = $("#unfollow-btn");
    subscribe.hide();
    unfollow.hide();
    var txt = $(".subscribe-text");
    $.ajax({
        url: '/api/subscriber/check',
        type: 'get',
        success: function () {
            unfollow.show();
            txt.text("You are already subscribed");
        },
        error: function (xhr, ajaxOptions, thrownError) {
            subscribe.show();
            txt.text("You will know all CB news");
        }
    })
}
function showOk(msg) {
    var img = $(".subscribed");
    img.show();
    img.delay(3000).fadeOut(1000);
    checkIsSubscribed();
}

function showError(msg) {
    var img = $(".error-img");
    img.show();
    img.delay(3000).fadeOut(1000);
}
