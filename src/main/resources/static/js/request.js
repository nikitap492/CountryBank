const POST = "POST";
const GET = "GET";
const PUT = "PUT";
const PATCH = "PATCH";
const DELETE = "DELETE";

const request = (config, onSuccess, onError) => {
    $.ajax({
        url: config.url,
        contentType: 'application/json',
        type: config.type,
        data: JSON.stringify(config.data),
        success: onSuccess,
        error: onError
    });
};