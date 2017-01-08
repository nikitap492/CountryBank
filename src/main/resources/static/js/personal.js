$("#transfer_btn").bind("click", function () {
    $("#offer").fadeOut(1000);
    $("#transfer").fadeIn(1000);
});

$("#credit_btn").bind("click", function () {
    $("#offer").fadeOut(1000);
    $("#credit").fadeIn(1000);
});


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
            error: function (xhr, ajaxOptions, thrownError) {
                showResult("#transfer", xhr.responseText);
            }
        });
    };
    showConfirmation(f);
});

function showConfirmation(func) {
    $("#confirmation").fadeIn(500);
    $("#confirm-btn-yes").bind("click", func);
}

$("#pay_btn").bind("click", function () {
    $("#offer").fadeOut(1000);
    $("#pay").fadeIn(1000);
});

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
            error: function (xhr, ajaxOptions, thrownError) {
                showResult("#pay", xhr.responseText);
            }
        })
    };
    showConfirmation(f);
});


$("#save_credit").bind("click", function () {
    showConfirmation(credit(0));
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
            error: function (xhr, ajaxOptions, thrownError) {
                showResult("#credit", xhr.responseText);
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

$("#open-bill-btn").bind("click", function () {
    var f = function () {
        $.ajax({
            url: "/api/bill/new",
            type: "post",
            contentType: "application/json",
            success: function (data) {
                window.location.replace('/private');
            },
            error: function (xhr, ajaxOptions, thrownError) {
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
