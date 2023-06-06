var currentPage = 0;
let pageCount = 0;
let pageSize = 2;

onload = function () {
    initCategories();
    initContacts();
    initTitle();
    initPage();
    initAdvertisements(currentPage,pageSize);
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

function initAdvertisements(curPage, pageSize){
    $("#listAdverts").children(".row .advert").remove();

    let request = {
        page: curPage,
        pageSize: pageSize,
        categoryFilter: localStorage.getItem('category')
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/advertisements/filter",
        data: JSON.stringify(request),
        dataType: 'json',
        success: function (data) {
            let html = "";
            if(data.pageCount === 0){
                html = "<h3>Список пуст!</h3>";
                $("#listEmpty").prepend(html);
                return;
            }
            data.advertisements.forEach((advert) => {
                html += "<div class=\"row advert\">" +
                    "<div class=\"card mb-3 cardAdvert\" onclick=\"showAdvert(this)\">" +
                    "<div class=\"row g-0\">" +
                    "<div class=\"col-md-4\">" +
                    "<img src=\"image/2768339668.jpg\" class=\"img-fluid rounded-start\" alt=\"...\">" +
                    "</div>" +
                    "<div class=\"col-md-8\">" +
                    "<div class=\"card-body\">" +
                    "<h5 class=\"card-title\">" + advert.heading + "</h5>" +
                    "<p class=\"card-text\">" + advert.text + "</p>" +
                    "<p id=\"idAdvert\" class=\"card-text\" style=\"visibility: hidden\">" + advert.id + "</p>" +
                    "<p class=\"card-text\"><small class=\"text-body-secondary\">" + advert.user.name + "</small></p>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>";
            });
            $("#listAdverts").prepend(html);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function initPage(){
    let request = {
        page: currentPage,
        pageSize: pageSize,
        categoryFilter: localStorage.getItem('category')
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/advertisements/filter",
        data: JSON.stringify(request),
        dataType: 'json',
        success: function (data) {
            if(data.pageCount === 0){
                return;
            }
            pageCount = data.pageCount;
            let html = "<li class=\"page-item\" id=\"page_prev\" onclick=\"pagePrev(this)\">" +
                "<a class=\"page-link\" href=\"#\" aria-label=\"Previous\">" +
                "<span aria-hidden=\"true\">&laquo;</span>" +
                "</a>" +
                "</li>";
            for(let i = 0; i < pageCount && i < pageSize; i++){
                html += "<li class=\"page-item\" id=\"page_" + i + "\" onclick=\"pageChange(" + i + ")\"><a class=\"page-link\" href=\"#\">" + (i+1) + "</a></li>";
            }
            html += "<li class=\"page-item\" id=\"page_next\" onclick=\"pageNext(this)\">" +
                "<a class=\"page-link\" href=\"#\" aria-label=\"Next\">" +
                "<span aria-hidden=\"true\">&raquo;</span>" +
                "</a>" +
                "</li>";
            $("#paginator").append(html);
            $("#page_" + currentPage).addClass("active");
            $("#page_prev").addClass("disabled");
            if(currentPage == pageCount-1){
                $("#page_next").addClass("disabled");
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function pagePrev(elem){
    if($(elem).hasClass("disabled")){
        return;
    }
    $("#page_" + currentPage).removeClass("active");
    currentPage -= 1;
    $("#page_" + currentPage).addClass("active");
    if(currentPage == 0){
        $("#page_prev").addClass("disabled");
    }
    if(currentPage < pageCount){
        $("#page_next").removeClass("disabled");
    }
    initAdvertisements(currentPage, pageSize);
}

function pageNext(elem){
    if($(elem).hasClass("disabled")){
        return;
    }
    $("#page_" + currentPage).removeClass("active");
    currentPage += 1;
    $("#page_" + currentPage).addClass("active");
    if(currentPage > 0){
        $("#page_prev").removeClass("disabled");
    }
    if(currentPage == pageCount-1){
        $("#page_next").addClass("disabled");
    }
    initAdvertisements(currentPage, pageSize);
}

function pageChange(page){
    $("#page_" + currentPage).removeClass("active");
    $("#page_" + page).addClass("active");
    currentPage = page;
    if(currentPage == pageCount-1){
        $("#page_next").addClass("disabled");
    }
    else if($("#page_next").hasClass("disabled")){
        $("#page_next").removeClass("disabled");
    }
    if(currentPage == 0){
        $("#page_prev").addClass("disabled");
    }
    else if($("#page_prev").hasClass("disabled")){
        $("#page_prev").removeClass("disabled");
    }
    initAdvertisements(currentPage, pageSize);
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
                $("#vertical").append("<li id='" + category.id + "' onclick='categoryOpenPage(this)'>" + category.name + "</li>");
            })
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function categoryOpenPage(elem){
    localStorage.setItem('category', $(elem).attr('id'));
    location.assign("/advertisements");
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

    let request = {
        login: email,
        password: pass
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/account/authenticate",
        dataType: "json",
        data: JSON.stringify(request),
        statusCode: {
            200:
                function (data) {
                        if($("#authorModalDialog").find(".error").children().length > 0) {
                            $("#authorModalDialog").find("#error").remove();
                        }
                        localStorage.setItem('token', data.token);
                        $("#authorization").modal('hide');
                        $("#navbarCollapse").children().remove();
                        //TODO выводить имя пользователя
                        $("#navbarCollapse").append("<a class=\"navbar-brand\" href=\"#\">" + "Я" + "</a>");
                    },
            403:
                function (data) {
                    console.log($("#authorModalDialog").find(".error").children().length === 0);
                    if($("#authorModalDialog").find(".error").children().length === 0) {
                        let html = "<div id='error' class=\"alert alert-danger\" role=\"alert\">" +
                            "Возникла ошибка при авторизации!" +
                            "</div>";
                        $(".error").append(html);
                    }
                    console.log(data);
                }
        }
    });
}

function registration(){
    let email = $("#exampleInputEmail1").val().trim();
    let name = $("#exampleInputName").val().trim();
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
    if(!name){
        if(!$("#exampleInputName").hasClass("is-invalid")) {
            $("#exampleInputName").addClass("is-invalid");
            $("#regValName").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#exampleInputName").hasClass("is-invalid")) {
            $("#exampleInputName").removeClass("is-invalid");
            $("#regValName").empty();
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

    let request = {
        login: email,
        name: name,
        password: pass1
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/account/register",
        dataType: "json",
        data: JSON.stringify(request),
        statusCode: {
            200:
                function (data) {
                    if($("#registerModalDialog").find(".error").children().length > 0) {
                        $("#registerModalDialog").find("#error").remove();
                    }
                    localStorage.setItem('token', data.token);
                    $("#registration").modal('hide');
                    $("#navbarCollapse").children().remove();
                    //TODO выводить имя пользователя
                    $("#navbarCollapse").append("<a class=\"navbar-brand\" href=\"#\">" + "Я" + "</a>");
                },

            403:
                function (data) {
                    if($("#registerModalDialog").find(".error").children().length === 0) {
                        let html = "<div id='error' class=\"alert alert-danger\" role=\"alert\">" +
                            "Возникла ошибка при регистрации!" +
                            "</div>";
                        $(".error").append(html);
                    }
                    console.log(data);
                }
        }
    });
}

function showAdvert(advertisement){
    let advertId = $(advertisement).find("#idAdvert")[0].innerHTML;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/advertisements/" + advertId,
        dataType: 'json',
        success: function (data) {
            // добавление изображений
            $("#carouselImages").empty();
            let images = "<div class=\"carousel-item active\">" +
                "<img src='image/2768339668.jpg' class=\"d-block w-100\" alt=\"Первое изображение\">" +
                "</div>" +
                "<div class=\"carousel-item\">" +
                "<img src=\"image/2790875887.jpg\" class=\"d-block w-100\" alt=\"Второе изображение\">" +
                "</div>" +
                "<div class=\"carousel-item\">" +
                "<img src=\"image/artleo.jpg\" class=\"d-block w-100\" alt=\"Третье изображение\">" +
                "</div>";
            $("#carouselImages").append(images);

            // добавление заголовка
            $("#advertModalHeader").empty();
            let title = "<h5 class=\"modal-title\" id=\"staticBackdropLabel\">" + data.heading + "</h5>" +
                "<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Закрыть\"></button>";
            $("#advertModalHeader").prepend(title);

            // добавление текста
            $("#advertModalText").empty();
            let text = "<p class=\"card-text\">" + data.text + "</p>" +
                "<p class=\"card-text\"><small class=\"text-body-secondary\">" + data.user.name + "</small></p>";
            $("#advertModalText").append(text);

            // показать окно
            $('#staticBackdrop').modal('show');
        },
        error: function (e) {
            console.log(e);
        }
    });
}