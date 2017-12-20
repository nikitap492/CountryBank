let balanceHolder = $("#balance");
let statementSection = $('#statement');
let setterBtn = $("#current-setter-btn");
let footer = $("#footer");
let transactionElement = $("#mov");
let accountNumElement = $("#account-num");

const commit = (accountNum, amount, details) => {
    request(
        {
            url: '/transactions',
            type: POST,
            data: {
                amount: amount,
                recipient: accountNum,
                details: details
            }
        },
        (data) =>  showResult("#transfer", "Transaction was successfully saved"),
        (err) => showResult("#transfer", err.responseJSON.message)
    )
};

$("#commit").bind("click", function () {
    let accountNum = $("#accountNum").val();
    let amount = $("#amount").val();
    let details = $("#details").val();
    showConfirmation(() => commit(accountNum, amount, details));
});

$("#commitPayment").bind("click", function () {
    let amount = $("#paymentAmount").val();
    showConfirmation(() => commit(governmentAccountNum, amount));
});

const statement = (accountNum, flag) => {

    footer.addClass("footer");
    transactionElement.load("/accounts/" + accountNum + "/statement");

    balance(accountNum);
    accountNumElement.text(accountNum);

    if (flag) setterBtn.hide();
    else setterBtn.show();

    statementSection.fadeIn(100);
    $("html, body").animate({scrollTop: statementSection.offset().top}, 'slow');
};

const balance  = (accountNum) => {
    request(
        {
            url: '/accounts/' + accountNum + '/balance',
            type: GET
        },
        (data) =>  balanceHolder.text(data.balance), (err) => {console.log(err.text)}
    )
};

const governmentAccountNum = "9999999999999999";