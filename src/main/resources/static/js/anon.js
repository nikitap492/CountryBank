var s_up = $("#sign_up");
var s_in = $("#sign_in");
var err = $("#error");
var con = $("#congratulation");
var forget = $("#forget");
var s_f = true;

function loadPage() {
    if (s_f) s_in.show();
}

function showSignIn(time) {
        s_f = true;
        s_in.delay(time).fadeIn(sec, positionFooter);
}

$("#registration_btn").bind("click", function (e) {
    e.preventDefault();
    var username = $("#username").val();
    var password = $("#password").val();
    var email = $("#email").val();
    var name = $("#name").val();
    var address = $("#address").val();
    $.ajax({
        url: '/registration',
        contentType: 'application/json',
        type: 'post',
        data: JSON.stringify({
            username: username,
            password: password,
            email: email,
            name: name,
            address: address
        }),
        success: function () {
            s_up.fadeOut(sec);
            con.delay(sec).fadeIn(sec, positionFooter);
        },
        error: function (xhr) {
            s_up.fadeOut(sec);
            showError(xhr.responseText)
        }
    });

});

$("#forget_btn").bind("click", function () {
    var loginOrEmail = $("#email_or_login").val();
    var info = $("#info-message");
    $.ajax({
        url: '/api/user/forget?loginOrEmail=' + loginOrEmail,
        type: 'post',
        success: function (text) {
            showResult("#forget", text);
        },
        error: function (xhr) {
            showResult("#forget", xhr.responseText);
        }
    });
});

$("#reset_btn").bind("click", function () {
    var password = $("#password").val();
    $.ajax({
        url: window.location.href,
        contentType: 'application/json',
        type: 'post',
        data: JSON.stringify({
            password: password
        }),
        success: function (text) {
            showResult("#reset", text);
        },
        error: function (xhr) {
            showResult("#reset", xhr.responseText);
        }
    });
});

$("#confirm_btn").bind("click", function () {
    $.ajax({
        url: window.location.href,
        contentType: 'application/json',
        type: 'post',
        success: function (text) {
            showResult("#confirm", text);
        },
        error: function (xhr) {
            showResult("#confirm", xhr.responseText);
        }
    });
});

function showResult(id, text) {
    var result = $("#result");
    $(id).fadeOut(sec);
    result.delay(sec).fadeIn(sec, positionFooter);
    $("#result_ans").html(text);
    result.click(function () {
        window.location.replace('/sign');
    });
}

function showError(text) {
    err.delay(sec).fadeIn(sec, positionFooter);
    $("#error-text").text(text);
}

function showForget() {
    hideSignIn();
    forget.delay(sec).fadeIn(sec, positionFooter)
}

function showRegistration() {
    hideSignIn();
    s_up.delay(sec).fadeIn(sec, positionFooter)
}

function hideSignIn() {
    s_in.fadeOut(sec);
    s_f = false;
}

function showLogInForm() {
    s_up.fadeOut(sec);
    forget.fadeOut(sec);
    showSignIn(sec, sec);
}

$("#error-btn-yes").bind("click", function () {
    err.fadeOut(sec);
    s_up.delay(sec).fadeIn(sec, positionFooter);
});

function validateText(val, id) {
    var hint = $('#' + id + '-hint');
    hint.removeClass("inv");
    if (val.length >= 5) {
        hint.removeClass("fa-close");
        hint.addClass("fa-check");
    } else {
        hint.removeClass("fa-check");
        hint.addClass("fa-close");
    }
}

function validateConfirmPass() {
    var hint = $("#confirm-password-hint");
    hint.removeClass("inv");
    if ($("#password").val() == $("#confirm-password").val()) {
        hint.removeClass("fa-close");
        hint.addClass("fa-check");
    } else {
        hint.removeClass("fa-check");
        hint.addClass("fa-close");
    }
}

function validateUser(val, id) {
    var hint = $('#' + id + '-hint');
    hint.removeClass("inv");
    hint.removeClass("fa-check");
    hint.removeClass("fa-close");
    hint.addClass("fa-spinner fa-spin");
    if (val.length >= 4) {
        $.ajax({
            url: '/api/user/check?val=' + val,
            type: 'get',
            contentType: 'application/json',
            dataType: 'json',
            success: function (ans) {
                if (ans) {
                    hint.addClass("fa-close");
                } else hint.addClass("fa-check");
                hint.removeClass("fa-spinner fa-spin")

            }
        });
    } else {
        hint.addClass("fa-close");
        hint.removeClass("fa-spinner fa-spin")
    }
}

/**
 * For wrong authentication
 */

$(document).ready(function () {
    var str = window.location.search.substring(1);
    var pair = str.split("=");
    if (pair[0] == 'error' && pair[1].toLowerCase() == 'wrong') {
        $("#error-login-msg").show();
    }
    if (pair[0] == 'error' && pair[1].toLowerCase() == 'block') {
        $("#error-block-msg").show();
    }
});

$(".btn").bind("click", function () {
    openSignForm();
});

$("#credit_btn").bind("click", function () {
    openSignForm();
});

$("#deposit_btn").bind("click", function () {
    openSignForm();
});

function openSignForm() {
    $("#offer").fadeOut(sec);
    if (s_f) s_in.delay(sec).fadeIn(sec, positionFooter);
}


function hideCongratulation() {
    con.fadeOut(sec / 2);
    s_in.delay(sec).fadeIn(sec, positionFooter)
}
