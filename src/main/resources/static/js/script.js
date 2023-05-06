onload = function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        dataType: 'json',
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            console.log(e);
        }
    });
}