const EMAIL = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
const ALPHA_NUMERIC = /^[A-Za-z]{4,}$/i;

const confirmPasswordHint = $("#confirm-password-hint");
const confirmPassword = $("#confirm-password");
const emailHint = $("#email-hint");
const loginHint = $("#username-hint");

const validateText= (val, id) => {
    let hint = $('#' + id + '-hint');
    let result = val.length >= 5 ? "fa-check" : "fa-close";
    classesSwap(hint, "inv fa-close fa-check", result);
};

const validateConfirmPass = () => {
    confirmPasswordHint.removeClass("inv");
    let result = password.val() === confirmPassword.val() ? "fa-check" : "fa-close";
    classesSwap(confirmPasswordHint, "fa-close fa-check", result)
};

const validateLogin = val => {
    classesSwap(loginHint, "inv fa-close fa-check", "fa-spinner fa-spin");
    if (val && ALPHA_NUMERIC.test(val))  checkToExist(loginHint, val);
    else classesSwap(loginHint, "fa-spinner fa-spin", "fa-close")
};

const validateEmail = val => {
    classesSwap(emailHint, "inv fa-close fa-check", "fa-spinner fa-spin");
    if (val && EMAIL.test(val))  checkToExist(emailHint, val);
    else classesSwap(emailHint, "fa-spinner fa-spin", "fa-close")
};

const checkToExist = (hint, usernameOrEmail) =>
    request({
            url: '/users/registration?usernameOrEmail=' + usernameOrEmail,
            type: GET
        }, () => classesSwap(hint, "fa-spinner fa-spin", "fa-check"),
        () => classesSwap(hint, "fa-spinner fa-spin", "fa-close")
    );

const classesSwap = (target, from, to) => {
    target.removeClass(from);
    target.addClass(to);
};