onload = function () {
    initCategories();
    initContacts();
    initCategoriesAdvertisements();
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

function initCategoriesAdvertisements(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        dataType: 'json',
        success: function (data) {
            if(data.length % 3 === 0){
                let html = "";

                for(let i = 0; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\">" +
                        "<div class=\"col-md-4\">" +
                        "<div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
            }
            else if(data.length % 3 === 2){
                let html = "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-6\">" +
                    "<div class=\"h-100 p-4 bg-light border rounded-3\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div>";
                html += "<div class=\"col-md-6\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                html += "<h3>" + data[1].name + "</h3><p>" + data[1].id + "</p>";
                html += "</div></div></div>";
                for(let i = 2; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
            }
            else{
                let html = "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-12\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div></div>";
                for(let i = 1; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
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