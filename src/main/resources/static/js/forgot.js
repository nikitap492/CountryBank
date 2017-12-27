$("#forget_btn").bind("click", () => {
    let loginOrEmail = $("#email_or_login").val();
    forgotPassword(loginOrEmail)
});

$("#reset_btn").bind("click", () => {
    let password = $("#password").val();
    let confirmation = $("#confirm-password").val();
    if (password !== confirmation) showResult("#reset", "The passwords do not equal");
    else resetPassword(password)
});

const resetPassword = (password) => request({
        url: "/users/password",
        type: POST,
        data: {password: password, token : getQueryVariable("token")}
    },
    () => showResult("#reset", "New password has been set"),
    err => showResult("#reset", err.responseJSON.message)
);

const forgotPassword = (loginOrEmail) =>
    request({
            url: '/users/password?loginOrEmail=' + loginOrEmail,
            type: GET,
        },
        () => showResult("#forget", "The message has been sent to your email. Please, follow the instructions in the letter"),
        err => showResult("#forget", err.responseJSON.message)
    );
