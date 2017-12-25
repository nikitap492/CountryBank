const s_up = $("#sign_up");
const s_in = $("#sign_in");
const err = $("#error");
const con = $("#congratulation");
const forget = $("#forget");
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
    let username = $("#username").val();
    let password = $("#password").val();
    let email = $("#email").val();
    let name = $("#name").val();
    let address = $("#address").val();

    request({
            url: '/users/registration',
            type: POST,
            data: {
                username: username,
                password: password,
                email: email,
                name: name,
                address: address
            }
        },
        () => {
            s_up.fadeOut(sec);
            con.delay(sec).fadeIn(sec, positionFooter);
        },
        (err) => {
            s_up.fadeOut(sec);
            showError(err.responseJSON.message)
        })
});

$("#forget_btn").bind("click", function () {
    let loginOrEmail = $("#email_or_login").val();
    let info = $("#info-message");
    request({
            url: '/users/password?loginOrEmail=' + loginOrEmail,
            type: GET,
        },
        (text) => {
            showResult("#forget", text);
        },
        (err) => {
            showResult("#forget", err.responseJSON.message);
        }
    )
});

$("#reset_btn").bind("click", function () {
    let password = $("#password").val();
    request({
            url: "/users/password",
            type: POST,
            data: {password: password}
        },
        (text) => showResult("#reset", text),
        (err) => showResult("#reset", err.responseJSON.message)
    )
});

$("#confirm_btn").bind("click", () =>
    request({
            url: window.location.href,
            type: POST
        },
        (text) => {
            showResult("#confirm", text);
        },
        (err) => {
            showResult("#confirm", err.responseJSON.message);
        }
    )
);

function showResult(id, text) {
    let result = $("#result");
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
    let hint = $('#' + id + '-hint');
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
    let hint = $("#confirm-password-hint");
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
    let hint = $('#' + id + '-hint');
    hint.removeClass("inv");
    hint.removeClass("fa-check");
    hint.removeClass("fa-close");
    hint.addClass("fa-spinner fa-spin");
    if (val.length >= 4) {
        request({
                url: '/users/registration?usernameOrEmail=' + val,
                type: GET
            }, (ans) => {
                hint.addClass("fa-check");
                hint.removeClass("fa-spinner fa-spin")
            },
            () => {
                hint.addClass("fa-close");
                hint.removeClass("fa-spinner fa-spin")
            }
        )
    } else {
        hint.addClass("fa-close");
        hint.removeClass("fa-spinner fa-spin")
    }
}

/**
 * For wrong authentication
 */

$(document).ready(function () {
    let str = window.location.search.substring(1);
    let pair = str.split("=");
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
