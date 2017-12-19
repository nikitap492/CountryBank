
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
        (data) =>  showResult("#transfer", data),
        (err) => showResult("#transfer", err.responseText)
    )
};

$("#commit").bind("click", function () {
    let accountNum = $("#accountNum").val();
    let amount = $("#amount").val();
    let details = $("#details").val();
    showConfirmation(() => commit(accountNum, amount, details));
});