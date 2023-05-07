onload = function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/categories",
        dataType: 'json',
        success: function (data) {
            console.log(data.length);
            console.log(data.length % 3);
            data.forEach((category)=>{
                $("#vertical").append("<li>" + category.name + "</li>");
            })

            if(data.length % 3 === 0){
                let html = "";

                for(let i = 0; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\">" +
                        "<div class=\"col-md-4\">" +
                        "<div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
            }
            else if(data.length % 3 === 2){
                console.log("1");
                let html = "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-6\">" +
                    "<div class=\"h-100 p-4 bg-light border rounded-3\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div>";
                html += "<div class=\"col-md-6\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div></div>";
                for(let i = 2; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+2].name + "</h3><p>" + data[i+2].id + "</p>";
                    html += "</div></div></div>";
                }
                $("#contentAdvertisements").append(html);
                console.log(2);
            }
            else{
                let html = "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-12\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                html += "<h3>" + data[0].name + "</h3><p>" + data[0].id + "</p>";
                html += "</div></div></div>";
                for(let i = 1; i < data.length; i += 3){
                    html += "<div class=\"row blocksOfAdvertisements\"><div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i].name + "</h3><p>" + data[i].id + "</p>";
                    html += "</div></div></div>";
                    html += "<div class=\"col-md-4\"><div class=\"h-100 p-4 bg-light border rounded-3\">";
                    html += "<h3>" + data[i+1].name + "</h3><p>" + data[i+1].id + "</p>";
                    html += "</div></div></div>";
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