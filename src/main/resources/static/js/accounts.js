$("#open-account-btn").bind("click", () => makeNewAccount());

const makeNewAccount = () => showConfirmation(() =>
    request({
        url: "/accounts",
        type: POST,
    }, () => showResult("#accounts", "6543") ,
        (xhr) => showResult("#accounts", xhr.responseJSON.message)));

const markAccountAsCurrent = () => showConfirmation(() =>
    request({
            url: "/accounts",
            type: PATCH,
            data: $("#account-num").text()
        },
        () => redirect('/private'),
        () => redirect('/error')
    ));