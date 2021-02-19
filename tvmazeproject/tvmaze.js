let startActorID = 14245;

let startGame = function(actorID) {
    $.get(
        `http://api.tvmaze.com/people/${startActorID}?embed=castcredits`,
        function(data, textStatus, jqXHR) {

            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);

            $("#status-text").text(`Shows Featuring ${data.name}`);

            $("#result-list").empty();

            let actorShows = data._embedded.castcredits;
            console.log(actorShows);
            for (let i = 0; i < actorShows.length; i++) {
                getShowByID(actorShows[i]._links.show.href);
            }
        }
    )
}

let getShowByID = function(showURL) {
    $.get(
        showURL,
        function(data, textStatus, jqXHR) {
            console.log(data);

            let newShow = document.createElement("div");
                newShow.id = "show" + data.id;
                newShow.class = "ShowInfo";
                newShow.style.width = "20%";
                newShow.style.display = "inline-block";
                newShow.style.padding = "10px";
                newShow.style.margin = "10px";
                newShow.style.textAlign = "center";
                newShow.style.border = "5px solid black";
                newShow.addEventListener("click", () => {
                    getShowCast(data.id, data.name);
                });
                let showImage = document.createElement("img");
                let imgURL = getShowImage(data.id);
                if (imgURL != null) {
                    showImage.src = imgURL;
                }
                console.log(showImage.src);
                showImage.style.objectFit = "cover";
                showImage.style.width = "100%";
                newShow.appendChild(showImage);

                newShow.append(data.name);
                document.getElementById("result-list").appendChild(newShow);
        }
    )
}

let getShowImage = function(showID) {
    $.get(
        `http://api.tvmaze.com/shows/${showID}`,
        function(data, textStatus, jqXHR) {
            console.log(data);
            let newSrc = data.image.medium;
            console.log(data.image.medium);
            return newSrc;
        }
    )
}

let getShows = function() {
    const search = $("#show-name").val();
    
    $.get(
        `http://api.tvmaze.com/search/shows?q=${search}`,
        function(data, textStatus, jqXHR) {

            console.log(search);
            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);

            $("#status-text").text("Shows Found");

            $("#result-list").empty();

            let resultList = data;
            for (let i = 0; i < resultList.length; i++) {
                let newShow = document.createElement("div");
                newShow.id = "show" + resultList[i].guid;
                newShow.class = "ShowInfo";
                newShow.style.width = "20%";
                newShow.style.display = "inline-block";
                newShow.style.padding = "10px";
                newShow.style.margin = "10px";
                newShow.style.textAlign = "center";
                newShow.style.border = "5px solid black";
                newShow.addEventListener("click", () => {
                    getShowCast(resultList[i].show.id, resultList[i].show.name);
                });
                let showImage = document.createElement("img");
                if (resultList[i].show.image != null) {
                    showImage.src = resultList[i].show.image.medium;
                }
                showImage.style.objectFit = "cover";
                showImage.style.width = "100%";
                newShow.appendChild(showImage);

                newShow.append(resultList[i].show.name);
                document.getElementById("result-list").appendChild(newShow);
                
            }
        }
    );
}

let getShowCast = function(showID, showName) {
    $.get(
        `http://api.tvmaze.com/shows/${showID}/cast`,
        function(data, textStatus, jqXHR) {

            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);

            $("#result-list").empty();

            if (data.length === 0) {
                $("#status-text").text(`No Cast Found`);
            } else {
                $("#status-text").text(`Major Cast of ${showName}`);

                let resultList = data;
                for (let i = 0; i < resultList.length; i++) {
                    let newActor = document.createElement("div");
                    newActor.id = "actor" + resultList[i].guid;
                    newActor.class = "ShowInfo";
                    newActor.style.width = "20%";
                    newActor.style.display = "inline-block";
                    newActor.style.padding = "10px";
                    newActor.style.margin = "10px";
                    newActor.style.textAlign = "center";
                    newActor.style.border = "5px solid black";
                    let actorImage = document.createElement("img");
                    if (resultList[i].character.image != null) {
                        actorImage.src = resultList[i].character.image.medium;
                    } else if (resultList[i].person.image != null) {
                        actorImage.src = resultList[i].person.image.medium;
                    }
                    actorImage.style.objectFit = "cover";
                    actorImage.style.width = "100%";
                    newActor.appendChild(actorImage);

                    newActor.append(resultList[i].person.name + " as " + resultList[i].character.name);
                    document.getElementById("result-list").appendChild(newActor);
                }
            }
        }
    );
}