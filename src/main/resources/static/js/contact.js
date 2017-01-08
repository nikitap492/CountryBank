$("#contact-msg").bind("click", function () {
    var email = $("#contact-email").val();
    var msg = $("#contact-text").val();
    var name = $("#contact-name").val();
    var err = $("#error-msg");
    $.ajax({
        url: '/api/message/save',
        type: 'post',
        contentType: "application/json",
        datatype: 'json',
        data: JSON.stringify({
            email: email,
            name: name,
            message: msg
        }),
        success: function () {
            err.text('');
            $("#contact-success").show();
            window.setTimeout(function () {
                window.location.replace("/");
            }, 4000);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            err.text(xhr.responseText);
        }
    })
});