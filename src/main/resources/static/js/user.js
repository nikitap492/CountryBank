const s_up = $("#sign_up");
const s_in = $("#sign_in");
const err = $("#error");
const con = $("#congratulation");
const forget = $("#forget");
const username = $("#username");
const password = $("#password");
const email = $("#email");
const name = $("#name");
const address = $("#address");

var s_f = true;

const loadPage = () => {
    if (s_f) s_in.show();
};

const showSignIn = (time) => {
    s_f = true;
    s_in.delay(time).fadeIn(sec, positionFooter);
};

$("#registration_btn").bind("click", function (e) {
    e.preventDefault();
    request({
            url: '/users/registration',
            type: POST,
            data: {
                username: username.val(),
                password: password.val(),
                email: email.val(),
                name: name.val(),
                address: address.val()
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

$("#confirm_btn").bind("click", () =>
    request({
            url: "/users/confirmation",
            type: POST,
            data: {token : getQueryVariable("token")}
        },
        () => showResult("#confirm", "Your account was confirmed successfully"),
        err => showResult("#confirm", err.responseJSON.message)
    )
);

const showResult = (id, text) =>  {
    let result = $("#result");
    $(id).fadeOut(sec);
    result.delay(sec).fadeIn(sec, positionFooter);
    $("#result_ans").html(text);
    result.click(function () {
        window.location.replace('/sign');
    });
};

const showError = text => {
    err.delay(sec).fadeIn(sec, positionFooter);
    $("#error-text").text(text);
};

const showForget = () => {
    hideSignIn();
    forget.delay(sec).fadeIn(sec, positionFooter)
};

const showRegistration = () => {
    hideSignIn();
    s_up.delay(sec).fadeIn(sec, positionFooter)
};

const hideSignIn = () => {
    s_in.fadeOut(sec);
    s_f = false;
};

const showLogInForm = () => {
    s_up.fadeOut(sec);
    forget.fadeOut(sec);
    showSignIn(sec, sec);
};

$("#error-btn-yes").bind("click", function () {
    err.fadeOut(sec);
    s_up.delay(sec).fadeIn(sec, positionFooter);
});

/**
 * For wrong authentication
 */

$(document).ready(function () {
    let str = window.location.search.substring(1);
    let pair = str.split("=");
    if (pair[0] === 'error' && pair[1].toLowerCase() === 'wrong') {
        $("#error-login-msg").show();
    }
    if (pair[0] === 'error' && pair[1].toLowerCase() === 'block') {
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

const openSignForm = () => {
    $("#offer").fadeOut(sec);
    if (s_f) s_in.delay(sec).fadeIn(sec, positionFooter);
};


const hideCongratulation = () => {
    con.fadeOut(sec / 2);
    s_in.delay(sec).fadeIn(sec, positionFooter)
};
