$("#credit_btn").bind("click", function () {
    detailOffer("#credit");
});

$("#deposit_btn").bind("click", function () {
    detailOffer("#deposit");
});

$("#pay_btn").bind("click", function () {
    detailOffer("#pay");
});

$("#transfer_btn").bind("click", function () {
    detailOffer("#transfer");
});

function detailOffer(id) {
    $("#offer").fadeOut(sec);
    $(id).delay(1000).fadeIn(sec, positionFooter);
}



function showConfirmation(func) {
    $("#confirmation").fadeIn(sec / 2);
    $("#confirm-btn-yes").bind("click", func);
}

function showResult(id, text) {
    $(id).fadeOut(sec);
    confirmHide();
    $("#result").delay(1000).fadeIn(sec, positionFooter);
    $("#result_ans").html(text);
}

function confirmHide() {
    $("#confirmation").fadeOut(sec / 2);
}
