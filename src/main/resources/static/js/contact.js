$("#contact-msg").bind("click", function () {
    let email = $("#contact-email").val();
    let msg = $("#contact-text").val();
    let name = $("#contact-name").val();
    let err = $("#error-msg");
    $.ajax({
        url: '/feedback',
        type: 'post',
        contentType: "application/json",
        datatype: 'json',
        data: JSON.stringify({
            email: email,
            name: name,
            body: msg
        }),
        success: function () {
            err.text('');
            $("#contact-success").show();
            window.setTimeout(function () {
                window.location.replace("/");
            }, 4000);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            err.text(xhr.responseJSON.message);
        }
    })
});