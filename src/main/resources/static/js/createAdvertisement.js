onload = function () {
    initCategories();
    initContacts();
    initChapter();
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

function initChapter(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        dataType: 'json',
        success: function (data) {
            data.forEach((category) => {
                $("#chapter").append("<option>" + category.name + "</option>");
            })
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function create(){
    // Проверка заполнения полей
    let title = $('#title').val();
    console.log(title);
    let description = $('#description').val();
    console.log(description);
    let url = $('#url').val();
    console.log(url);
    let yourContacts = $('#yourContacts').val();
    console.log(yourContacts);
    if(!title){
        if(!$("#title").hasClass("is-invalid")) {
            $("#title").addClass("is-invalid");
            $("#validTitle").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#title").hasClass("is-invalid")) {
            $("#title").removeClass("is-invalid");
            $("#validTitle").empty();
        }
    }
    if(!description){
        if(!$("#description").hasClass("is-invalid")) {
            $("#description").addClass("is-invalid");
            $("#validDescription").append("<p>Необходимо заполнить поле</p>");
        }
    }
    else{
        if($("#description").hasClass("is-invalid")) {
            $("#description").removeClass("is-invalid");
            $("#validDescription").empty();
        }
    }
    if(!url || !yourContacts) {
        if (!$("#url").hasClass("is-invalid")) {
            $("#url").addClass("is-invalid");
        }
        if (!$("#yourContacts").hasClass("is-invalid")) {
            $("#yourContacts").addClass("is-invalid");
        }
        $("#validYourContacts").append("<p>Необходимо заполнить поле \"Url\" или \"Ваши контакты\"</p>");

    }
    else {
        if ($("#url").hasClass("is-invalid")) {
            $("#url").removeClass("is-invalid");
        }
        if ($("#yourContacts").hasClass("is-invalid")) {
            $("#yourContacts").removeClass("is-invalid");
        }
        $("#validYourContacts").empty();
    }

    //TODO проверка добавленных файлов на размер

    //TODO создание объявления, нужен эндпоинт
/*
    let request = {
        heading: title,
        text: description,
        user:{
            name: yourContacts
        },
        category: {
            name: $('#chapter').val()
        }
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/advertisements",
        data: JSON.stringify(request),
        dataType: 'json',
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            console.log(e);
        }
    });*/
}

function cancel(){
    //TODO возможно стоит сделать переадресацию на предыдущую страницу, т.к. при открытии только этой страницы закрытие вкладки не работает

    window.close();
}

function remove(){
    if(window.confirm("Вы уверены, что хотите удалить объявление?Э")){
        //TODO удаление объявления, нужен эндпоинт
    }
}
