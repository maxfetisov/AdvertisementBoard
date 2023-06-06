onload = function () {
    checkToken();
    initCategories();
    initContacts();
    initCategoriesAdvertisements();
}

function checkToken(){
    if(localStorage.getItem('token') != null){
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/account",
            dataType: 'json',
            headers:{
                'Authorization': localStorage.getItem('token')
            },
            statusCode: {
                200:
                    function (data) {
                        account = data.login;
                        drawLogin();
                    },
                403:
                    function (e) {
                        account = "";
                        localStorage.setItem('token', "");
                        drawButtons();
                        console.log(e);
                    }
            },
        });
    }
    else{
        drawButtons();
    }
}

function drawButtons(){
    $("#navbarCollapse").children().remove();
    let html = "<button type=\"button\" class=\"btn btn-outline-light me-2\" data-bs-toggle=\"modal\" " +
        "data-bs-target=\"#authorization\">Войти</button>" +
        "<button type=\"button\" class=\"btn btn-warning\" data-bs-toggle=\"modal\" " +
        "data-bs-target=\"#registration\">Зарегистрироваться</button>";
    $("#navbarCollapse").append(html);
}

function drawLogin(){
    $("#navbarCollapse").children().remove();
    $("#navbarCollapse").append("<a class=\"navbar-brand\" href=\"#\">" + account + "</a>");
}

function initContacts(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/contacts",
        headers:{
            'Authorization': localStorage.getItem('token')
        },
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
        headers:{
            'Authorization': localStorage.getItem('token')
        },
        dataType: 'json',
        success: function (data) {
            data.forEach((category) => {
                $("#vertical").append("<li id='" + category.id + "' onclick='categoryOpenPage(this)'>" + category.name +
                    "</li>");
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

function initCategoriesAdvertisements(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        headers:{
            'Authorization': localStorage.getItem('token')
        },
        dataType: 'json',
        success: function (data) {
            if(data.length % 3 === 0){
                let html = "";

                for(let i = 0; i < data.length; i += 3){
                    k = data[i].id;
                    html += "<div class=\"row blocksOfAdvertisements\">" +
                        "<div class=\"col-md-4\">" +
                        "<div class=\"h-100 p-4 bg-light border rounded-3\" onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
            }
            else if(data.length % 3 === 2){
                let html = "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-6\">" +
                    "<div class=\"h-100 p-4 bg-light border rounded-3\"  onclick=\"openAdvertisement(this)\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div>";
                html += "<div class=\"col-md-6\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                    "onclick=\"openAdvertisement(this)\">";
                html += "<h3>" + data[1].name + "</h3><p>" + data[1].id + "</p>";
                html += "</div></div></div>";
                for(let i = 2; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-4\"><div " +
                        "class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
            }
            else{
                let html = "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-12\"><div " +
                    "class=\"h-100 p-4 bg-light border rounded-3\"" +
                    " onclick=\"openAdvertisement(this)\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div></div>";
                for(let i = 1; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-4\"><div " +
                        "class=\"h-100 p-4 bg-light border rounded-3\"" +
                        " onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\"  " +
                        "onclick=\"openAdvertisement(this)\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function openAdvertisement(cat){
    let nameCategory = $(cat).children("h3")[0].innerText;
    console.log(nameCategory);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        headers:{
            'Authorization': localStorage.getItem('token')
        },
        dataType: "json",
        success: function (data) {
            data.forEach((category) => {
                if(category.name === nameCategory){
                    localStorage.setItem('category', category.id);
                    location.assign("/advertisements");
                }
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

    let request = {
        login: email,
        password: pass
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/account/authenticate",
        headers:{
            'Authorization': localStorage.getItem('token')
        },
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
                    checkToken();
                },
            403:
                function (data) {
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
        headers:{
            'Authorization': localStorage.getItem('token')
        },
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
                    checkToken();
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

function createAdvertisement(){
    checkToken();
    if(account !== ""){
        location.assign("/createAdvertisement");
    }
    else{
        $("#authorization").modal('show');
    }
}