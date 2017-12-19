$("#open-account-btn").bind("click", () => makeNewAccount());

const makeNewAccount = () => showConfirmation(() =>
    request({
        url: "/accounts",
        type: POST,
    }, () => showResult("#accounts", "Account was created successfully") ,
        (xhr) => showResult("#accounts", xhr.responseJSON.message)));

const markAccountAsCurrent = () => showConfirmation(() =>
    request({
            url: "/accounts",
            type: PATCH,
            data: {accountNum : $("#account-num").text()}
        },
        () => redirect('/private'),
        () => redirect('/error')
    ));