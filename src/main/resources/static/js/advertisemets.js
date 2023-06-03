onload = function () {
    initCategories();
    initContacts();
    initTitle();
    initAdvertisements();

    //TODO управление пагинацией
}

function initTitle(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        dataType: "json",
        success: function (data) {
            data.forEach((category) => {
                if(category.id == localStorage.getItem('category')){
                    $('#categoryListAdverts').append('<h3>' + category.name + '</h3>');
                }
            })
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function initAdvertisements(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/advertisements",
        dataType: 'json',
        success: function (data) {
            let html = "";
            data.forEach((advert) => {
                if(advert.category.id == localStorage.getItem('category')) {
                    html += "<div class=\"row advert\">" +
                        "<div class=\"card mb-3 cardAdvert\" onclick=\"showAdvert()\">" +
                        "<div class=\"row g-0\">" +
                        "<div class=\"col-md-4\">" +
                        "<img src=\"...\" class=\"img-fluid rounded-start\" alt=\"...\">" +
                        "</div>" +
                        "<div class=\"col-md-8\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + advert.heading + "</h5>" +
                        "<p class=\"card-text\">" + advert.text + "</p>" +
                        "<p class=\"card-text\"><small class=\"text-body-secondary\">" + advert.user.name + "</small></p>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>";
                }
            });
            $("#listAdverts").prepend(html);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function initContacts(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/contacts",
        dataType: 'json',
        success: function (data) {
                $("#contacts").append("<li>" + data.phone + "</li>").append("<li>" + data.email + "</li>");
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function initCategories(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        dataType: 'json',
        success: function (data) {
            data.forEach((category) => {
                $("#vertical").append("<li>" + category.name + "</li>");
            })
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function authorization(){
    let email = $("#exampleInputEmail").val().trim();
    let pass = $("#exampleInputPassword").val().trim();

    if(!email){
        if(!$("#exampleInputEmail").hasClass("is-invalid")) {
            $("#exampleInputEmail").addClass("is-invalid");
            $("#authValEmail").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#exampleInputEmail").hasClass("is-invalid")) {
            $("#exampleInputEmail").removeClass("is-invalid");
            $("#authValEmail").empty();
        }
    }

    if(!pass){
        if(!$("#exampleInputPassword").hasClass("is-invalid")) {
            $("#exampleInputPassword").addClass("is-invalid");
            $("#authValPassword").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#exampleInputPassword").hasClass("is-invalid")) {
            $("#exampleInputPassword").removeClass("is-invalid");
            $("#authValPassword").empty();
        }
    }
}

function registration(){
    let email = $("#exampleInputEmail1").val().trim();
    let pass1 = $("#exampleInputPassword1").val().trim();
    let pass2 = $("#exampleInputPassword2").val().trim();

    if(!email){
        if(!$("#exampleInputEmail1").hasClass("is-invalid")) {
            $("#exampleInputEmail1").addClass("is-invalid");
            $("#regValEmail").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#exampleInputEmail1").hasClass("is-invalid")) {
            $("#exampleInputEmail1").removeClass("is-invalid");
            $("#regValEmail").empty();
        }
    }
    if(!pass1){
        if(!$("#exampleInputPassword1").hasClass("is-invalid")) {
            $("#exampleInputPassword1").addClass("is-invalid");
            $("#regValPassword1").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#exampleInputPassword1").hasClass("is-invalid")) {
            $("#exampleInputPassword1").removeClass("is-invalid");
            $("#regValPassword1").empty();
        }
    }
    if(!pass2){
        if(!$("#exampleInputPassword2").hasClass("is-invalid")) {
            $("#exampleInputPassword2").addClass("is-invalid");
            $("#regValPassword2").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#exampleInputPassword2").hasClass("is-invalid")) {
            $("#exampleInputPassword2").removeClass("is-invalid");
            $("#regValPassword2").empty();
        }
        if(pass1 !== pass2){
            if(!$("#exampleInputPassword1").hasClass("is-invalid") && !$("#exampleInputPassword2").hasClass("is-invalid")) {
                $("#exampleInputPassword1").addClass("is-invalid");
                $("#exampleInputPassword2").addClass("is-invalid");
                $("#regValPassword2").append("<p>Пароли не совпадают</p>");
            }
        }
        else{
            if($("#exampleInputPassword1").hasClass("is-invalid") && $("#exampleInputPassword2").hasClass("is-invalid")) {
                $("#exampleInputPassword2").removeClass("is-invalid");
                $("#regValPassword2").empty();
            }
        }
    }
}

function showAdvert(){
    $('#staticBackdrop').modal('show');

    //TODO получение данных об объявлении
}