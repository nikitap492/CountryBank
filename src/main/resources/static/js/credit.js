
const credit = (type) => {
    if (type === BUSINESS_DEPOSIT) {
        let months = $("#depositMonths").val();
        let amount = $("#depositAmount").val();
        saveCredit(months, amount, type)
    }else {
        let months = $("#months").val();
        let amount = $("#creditAmount").val();
        saveCredit(months, amount, type)
    }
};

const saveCredit = (months, amount, type) => {
    request(
        {
            url: '/credits',
            type: POST,
            data: {
                numOfMonths: months,
                amount: amount,
                type: type
            }
        },
        () => showResult("#deposit", 'Successful!'),
        (err) => showResult("#deposit", err.responseJSON.message)
    );
};

$("#save_credit").bind("click", function () {
    let full = window.location.href;
    let url = full.substr(full.lastIndexOf("/") + 1);
    if ("personal" === url.toLowerCase())
        showConfirmation(() => credit(PERSONAL_CREDIT));
    else
        showConfirmation(() => credit(BUSINESS_CREDIT));
});

$("#save_deposit").bind("click", function () {
    showConfirmation(() => credit(BUSINESS_DEPOSIT));
});

const BUSINESS_CREDIT = "BUSINESS_CREDIT";
const BUSINESS_DEPOSIT = "BUSINESS_DEPOSIT";
const PERSONAL_CREDIT = "PERSONAL_CREDIT";