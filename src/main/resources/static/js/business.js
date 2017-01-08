$("#credit_btn").bind("click", function () {
    $("#offer").fadeOut(1000);
    $("#credit").delay(1000).fadeIn(1000);
});

$("#deposit_btn").bind("click", function () {
    $("#offer").fadeOut(1000);
    $("#deposit").delay(1000).fadeIn(1000);
});


function showConfirmation(func) {
    $("#confirmation").fadeIn(500);
    $("#confirm-btn-yes").bind("click", func);
}

$("#save_credit").bind("click", function () {
    showConfirmation(credit());
});

$("#save_deposit").bind("click", function () {
    showConfirmation(deposit());
});

function credit() {
    return function () {
        var money = $("#credit_quantity").val();
        var months = $("#credit_months").val();
        $.ajax({
            url: "/api/credit/save",
            contentType: "application/json",
            data: JSON.stringify({
                num: months,
                money: money,
                type: 1
            }),
            type: "post",
            success: function () {
                showResult("#credit", 'Successful!');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                showResult("#credit", xhr.responseText);
            }
        })
    };
}

function deposit() {
    return function () {
        var money = $("#deposit_quantity").val();
        var months = $("#deposit_months").val();
        $.ajax({
            url: "/api/credit/save",
            contentType: "application/json",
            data: JSON.stringify({
                num: months,
                money: money,
                type: 2
            }),
            type: "post",
            success: function () {
                showResult("#deposit", 'Successful!');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                showResult("#deposit", xhr.responseText);
            }
        })
    };
}


function showResult(id, text) {
    $(id).fadeOut(1000);
    $("#confirmation").fadeOut(500);
    $("#result").fadeIn(1000);
    $("#result_ans").html(text);
}

function confirmHide() {
    $("#confirmation").fadeOut(500);
}