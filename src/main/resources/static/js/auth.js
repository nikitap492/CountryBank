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

$("#save_transfer").bind("click", function () {

    var f = function () {
        var uuid = $("#transfer_uuid").val();
        var quantity = $("#transfer_quantity").val();
        var message = $("#transfer_message").val();
        $.ajax({
            url: "/api/movement/transfer",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                quantity: quantity,
                uuid: uuid,
                message: message
            }),
            type: "post",
            success: function (data) {
                showResult("#transfer", data);
            },
            error: function (xhr) {
                showResult("#transfer", xhr.responseText);
            }
        });
    };
    showConfirmation(f);
});

function showConfirmation(func) {
    $("#confirmation").fadeIn(sec / 2);
    $("#confirm-btn-yes").bind("click", func);
}

$("#save_pay").bind("click", function () {
    var f = function () {
        var quantity = $("#pay_quantity").val();
        $.ajax({
            url: "/api/movement/pay",
            contentType: "application/json",
            data: JSON.stringify({
                quantity: quantity
            }),
            type: "post",
            success: function (data) {
                showResult("#pay", data);
            },
            error: function (xhr) {
                showResult("#pay", xhr.responseText);
            }
        })
    };
    showConfirmation(f);
});


$("#save_credit").bind("click", function () {
    var full = window.location.href;
    var url = full.substr(full.lastIndexOf("/") + 1);
    if (url.toLowerCase() == "personal")
        showConfirmation(credit(0));
    if (url.toLowerCase() == "business")
        showConfirmation(credit(1));
});

$("#save_deposit").bind("click", function () {
    showConfirmation(deposit());
});


function credit(type) {
    return function () {
        var money = $("#credit_quantity").val();
        var months = $("#credit_months").val();
        $.ajax({
            url: "/api/credit/save",
            contentType: "application/json",
            data: JSON.stringify({
                num: months,
                money: money,
                type: type
            }),
            type: "post",
            success: function () {
                showResult("#credit", 'Successful!');
            },
            error: function (xhr) {
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
            error: function (xhr) {
                showResult("#deposit", xhr.responseText);
            }
        })
    };
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

// /private client api

$("#open-bill-btn").bind("click", function () {
    var f = function () {
        $.ajax({
            url: "/api/bill/new",
            type: "post",
            contentType: "application/json",
            success: function () {
                window.location.replace('/private');
            },
            error: function (xhr) {
                showResult("#bills", xhr.responseText);
            }
        })
    };
    showConfirmation(f);
});


function lookupMovements(uuid, flag) {
    var movements = $('#movements');
    var setter = $("#current-setter-btn");
    $("#footer").addClass("footer");
    $("#mov").load("/api/movement/list?uuid=" + uuid);
    if (flag) {
        setter.hide();
    } else setter.show();
    $("#bill-uuid").text(uuid);
    movements.fadeIn(100);
    $("html, body").animate({scrollTop: movements.offset().top}, 'slow');
}

function setCurrent() {
    var f = function () {
        $.ajax({
            url: "/api/bill/set_as_current?uuid=" + $("#bill-uuid").text(),
            type: "post",
            success: function () {
                window.location.replace('/private');
            },
            error: function () {
                window.location.replace('/error');
            }
        })
    };
    showConfirmation(f);
}