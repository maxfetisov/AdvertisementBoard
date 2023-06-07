var account = "";
var advertId = null;

onload = function () {
    checkToken();
    initCategories();
    initContacts();
    initChapter();
}

function checkAdvertId(){
    if(localStorage.getItem('advertId') !== null){
        advertId = localStorage.getItem('advertId');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/advertisements/" + advertId,
            headers:{
                'Authorization': localStorage.getItem('token')
            },
            dataType: 'json',
            success: function (data) {
                $('#title').val(data.heading);
                $('#description').val(data.text);
                $('#url').val(data.url);
                $('#yourContacts').val(data.contacts);

                $('#create').remove();
                $("#remove").remove();
                $('#buttonsRow').append("<button type=\"button\" id=\"create\" class=\"btn btn-primary col-2 offset-5\" " +
                    "onclick=\"update()\">Изменить</button>");
                $('#buttonsRow').append("<button type=\"button\" id=\"remove\" class=\"btn btn-primary col-2 offset-1\" " +
                    "onclick=\"remove()\">Удалить</button>");
            },
            error: function (e) {
                console.log(e);
            }
        });
    }
    else {
        $("#remove").remove();
    }
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
                        checkAdvertId();
                    },
                403:
                    function (e) {
                        account = "";
                        localStorage.setItem('token', "");
                        if(localStorage.getItem('category') === null){
                            localStorage.setItem('advertId', "");
                            location.assign("/home");
                        }
                        else{
                            localStorage.setItem('advertId', "");
                            location.assign('/advertisements');
                        }
                        console.log(e);
                    }
            },
        });
    }
    else{
        if(localStorage.getItem('category') === null){
            localStorage.setItem('advertId', "");
            location.assign("/home");
        }
        else{
            localStorage.setItem('advertId', "");
            location.assign('/advertisements');
        }
    }
}

function drawLogin(){
    $("#navbarCollapse").children().remove();
    $("#navbarCollapse").append("<a class=\"navbar-brand\" href=\"#\">" + account + "</a>");
    let html = "<button type=\"button\" class=\"btn btn-outline-light me-2\" onclick='exit()'>Выйти</button>";
    $("#navbarCollapse").append(html);
}

function exit(){
    localStorage.setItem('token', "");
    if(localStorage.getItem('category') === null){
        localStorage.setItem('advertId', "");
        location.assign("/home");
    }
    else{
        localStorage.setItem('advertId', "");
        location.assign('/advertisements');
    }
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
    localStorage.setItem('advertId', "");
    location.assign("/advertisements");
}

function initChapter(){
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
                $("#chapter").append("<option value='" + category.id + "'>" + category.name + "</option>");
            })
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function create() {
    // Проверка заполнения полей
    let title = $('#title').val();
    console.log(title);
    let description = $('#description').val();
    console.log(description);
    let url = $('#url').val();
    console.log(url);
    let yourContacts = $('#yourContacts').val();
    console.log(yourContacts);
    if (!title) {
        if (!$("#title").hasClass("is-invalid")) {
            $("#title").addClass("is-invalid");
            $("#validTitle").append("<p>Необходимо заполнить поле</p>");
        }
    } else {
        if ($("#title").hasClass("is-invalid")) {
            $("#title").removeClass("is-invalid");
            $("#validTitle").empty();
        }
    }
    if (!description) {
        if (!$("#description").hasClass("is-invalid")) {
            $("#description").addClass("is-invalid");
            $("#validDescription").append("<p>Необходимо заполнить поле</p>");
        }
    } else {
        if ($("#description").hasClass("is-invalid")) {
            $("#description").removeClass("is-invalid");
            $("#validDescription").empty();
        }
    }
    if (!url || !yourContacts) {
        if (!$("#url").hasClass("is-invalid")) {
            $("#url").addClass("is-invalid");
        }
        if (!$("#yourContacts").hasClass("is-invalid")) {
            $("#yourContacts").addClass("is-invalid");
        }
        $("#validYourContacts").append("<p>Необходимо заполнить поле \"Url\" или \"Ваши контакты\"</p>");

    } else {
        if ($("#url").hasClass("is-invalid")) {
            $("#url").removeClass("is-invalid");
        }
        if ($("#yourContacts").hasClass("is-invalid")) {
            $("#yourContacts").removeClass("is-invalid");
        }
        $("#validYourContacts").empty();
    }

    //TODO проверка добавленных файлов на размер

        let request = {
            heading: title,
            text: description,
            categoryId: $('#chapter').val(),
            contacts: yourContacts,
            url: url
        };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/advertisements",
        headers:{
            'Authorization': localStorage.getItem('token')
        },
        data: JSON.stringify(request),
        dataType: 'json',
        statusCode: {
            201:
                function (data) {
                    $("#toastBodyText").append("Объявление успешно создано.");
                    const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                    toast.show();
                    if(localStorage.getItem('category') === null){
                        localStorage.setItem('advertId', "");
                        location.assign("/home");
                    }
                    else{
                        localStorage.setItem('advertId', "");
                        location.assign('/advertisements');
                    }
                },
            400:
                function (data) {
                    $("#toastBodyText").append("Объявление не создано.");
                    const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                    toast.show();
                    console.log(data);
                }
        }
    });
}

function cancel(){
    localStorage.setItem('advertId', "");
    location.assign("/advertisements");
}

function update(){
    // Проверка заполнения полей
    let title = $('#title').val();
    console.log(title);
    let description = $('#description').val();
    console.log(description);
    let url = $('#url').val();
    console.log(url);
    let yourContacts = $('#yourContacts').val();
    console.log(yourContacts);
    if (!title) {
        if (!$("#title").hasClass("is-invalid")) {
            $("#title").addClass("is-invalid");
            $("#validTitle").append("<p>Необходимо заполнить поле</p>");
        }
    } else {
        if ($("#title").hasClass("is-invalid")) {
            $("#title").removeClass("is-invalid");
            $("#validTitle").empty();
        }
    }
    if (!description) {
        if (!$("#description").hasClass("is-invalid")) {
            $("#description").addClass("is-invalid");
            $("#validDescription").append("<p>Необходимо заполнить поле</p>");
        }
    } else {
        if ($("#description").hasClass("is-invalid")) {
            $("#description").removeClass("is-invalid");
            $("#validDescription").empty();
        }
    }
    if (!url || !yourContacts) {
        if (!$("#url").hasClass("is-invalid")) {
            $("#url").addClass("is-invalid");
        }
        if (!$("#yourContacts").hasClass("is-invalid")) {
            $("#yourContacts").addClass("is-invalid");
        }
        $("#validYourContacts").append("<p>Необходимо заполнить поле \"Url\" или \"Ваши контакты\"</p>");

    } else {
        if ($("#url").hasClass("is-invalid")) {
            $("#url").removeClass("is-invalid");
        }
        if ($("#yourContacts").hasClass("is-invalid")) {
            $("#yourContacts").removeClass("is-invalid");
        }
        $("#validYourContacts").empty();
    }

    //TODO проверка добавленных файлов на размер

    let request = {
        id: advertId,
        heading: title,
        text: description,
        categoryId: $('#chapter').val(),
        contacts: yourContacts,
        url: url
    };

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/api/advertisements",
        headers:{
            'Authorization': localStorage.getItem('token')
        },
        data: JSON.stringify(request),
        dataType: 'json',
        statusCode: {
            200:
                function (data) {
                    $("#toastBodyText").append("Объявление успешно обновлено.");
                    const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                    toast.show();
                    if(localStorage.getItem('category') === null){
                        localStorage.setItem('advertId', "");
                        location.assign("/home");
                    }
                    else{
                        localStorage.setItem('advertId', "");
                        location.assign('/advertisements');
                    }
                },
            400:
                function (data) {
                    $("#toastBodyText").append("Объявление не обновлено.");
                    const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                    toast.show();
                    console.log(data);
                },
            403:
            function (data){
                $("#toastBodyText").innerText = "";
                $("#toastBodyText").append("Это не ваше объявление, вы не можете его обновить.");
                const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                toast.show();
                console.log(data);
            }
        }
    });
}

function remove(){
    if(window.confirm("Вы уверены, что хотите удалить объявление?")){
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            url: "/api/advertisements/" + localStorage.getItem('advertId'),
            headers:{
                'Authorization': localStorage.getItem('token')
            },
            dataType: 'json',
            statusCode: {
                200:
                    function (data) {
                        $("#toastBodyText").append("Объявление успешно удалено.");
                        const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                        toast.show();
                        if(localStorage.getItem('category') === null){
                            localStorage.setItem('advertId', "");
                            location.assign("/home");
                        }
                        else{
                            localStorage.setItem('advertId', "");
                            location.assign('/advertisements');
                        }
                    },
                400:
                    function (data) {
                        $("#toastBodyText").append("Объявление не удалено.");
                        const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                        toast.show();
                        console.log(data);
                    },
                403:
                    function (data) {
                        $("#toastBodyText").append("Это не ваше объявление, вы не можете его удалить.");
                        const toast = new bootstrap.Toast(document.getElementById('liveToast'));
                        toast.show();
                        console.log(data);
                    }
            }
        });
    }
}
