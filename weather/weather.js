const getWeather = function() {

    const zipcode = $("#zip").val();
    $.get(
        `http://api.openweathermap.org/data/2.5/weather?zip=${zipcode},us&units=imperial&appid=a797e91b88b7389aa0547361f4e7bb06`,
        function(data, textStatus, jqXHR) {

            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);

            let imageURL = `http://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png`;

            $("#reportHeader").text(`Weather Report for ${data.name}`);
            $("#weatherDesc").text(data.weather[0].description);
            $("#weatherIcon").attr("src", imageURL);
            $("#currentTemp").text(`${data.main.temp} degrees Fahrenheit`);
        }
    );
}