$(document).ready(() => isSubscribed());

const subscribe = $("#subscribe-btn");
const unfollow = $("#unfollow-btn");
const error = $(".error-img");
const ok = $(".subscribed");

subscribe.bind("click", () => manageSubscriber(POST));
unfollow.bind("click", () => manageSubscriber(DELETE));

const manageSubscriber = (method) => request({
        url: '/subscribers',
        type: method,
        data: {email: $("#subscribe-email").val()}
    },
    () => {
        error.hide();
        ok.show();
        ok.delay(3000).fadeOut(1000);
        isSubscribed()
    },
    () => {
        ok.hide();
        error.show();
        error.delay(3000).fadeOut(1000);
    });


const isSubscribed = () => {
    unfollow.hide();
    subscribe.hide();
    request(
        {
            url: '/subscribers',
            type: GET
        },
        () => {unfollow.show()},
        () => {subscribe.show()}
    );
};
