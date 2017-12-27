const contactEmail = $("#contact-email");
const messageBody = $("#contact-text");
const contactName = $("#contact-name");
const err = $("#error-msg");
const contactSuccess =  $("#contact-success");
const messageSendButton = $("#contact-msg");


messageSendButton.bind("click", function () {
    addMessage(contactEmail.val(), contactName.val(), messageBody.val())(
        () => {
            err.text('');
            contactSuccess.show();
            window.setTimeout(function () {
                redirect("/");
            }, 4000);
        },
        (xhr) => err.text(xhr.responseJSON.message)
    )
});

const addMessage = (email, name, body) => (success, error) =>
    request({
        url: '/feedback',
        type: POST,
        data: {
            email: email,
            name: name,
            body: body
        }
    }, success, error);
