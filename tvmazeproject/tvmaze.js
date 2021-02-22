let startActorID = 14245;
let startName = "";
let endActorID = 12328;
let endName = "";
let gameIDPairs = [[14245, 12328], [6454, 12328]];
let gamesFinished = 0;
let steps = 0;

let setStartName = function() {
    return $.get(
        `http://api.tvmaze.com/people/${gameIDPairs[gamesFinished % gameIDPairs.length][0]}`,
        function(data, textStatus, jqXHR) {
            startName = data.name;
        }
    );
}

let setEndName = function() {
    return $.get(
        `http://api.tvmaze.com/people/${gameIDPairs[gamesFinished % gameIDPairs.length][1]}`,
        function(data, textStatus, jqXHR) {
            endName = data.name;
        }
    );
}

let setStartStatusText = function() {
    $.when(setStartName(), setEndName()).done(function(start, end) {
        $("#status-text").text(`Find a link between ${start[0].name} and ${end[0].name}`);
    })
}

let showSetup = function(actorID) {
    $("#start-game").attr("disabled",true);
    $("#reset").attr("disabled",false);
    console.log("actorID = " + actorID);
    let url = actorID === undefined ? `http://api.tvmaze.com/people/${gameIDPairs[gamesFinished % gameIDPairs.length][0]}?embed=castcredits` :
    `http://api.tvmaze.com/people/${actorID}?embed=castcredits`
    $.get(
        url,
        function(data, textStatus, jqXHR) {

            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);

            $("#status-text").text(`Shows Featuring ${data.name}`);

            $("#result-list").empty();

            let actorShows = data._embedded.castcredits;
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
                newShow.style.borderRadius = "10px";
                newShow.style.backgroundColor = "white";
                newShow.style.boxShadow = "10px 10px #7E0B22";
                newShow.style.fontFamily = "'Courier New', Courier, monospace";
                newShow.style.fontWeight = "bold";
                newShow.addEventListener("click", () => {
                    getShowCast(data.id, data.name);
                });
                let showImage = document.createElement("img");
                showImage.id = "showImg" + data.id;
                showImage.style.borderRadius = "10px";
                let imgURL = getShowImage(data.id);
                console.log("imgURL = " + imgURL);
                $(showImage).attr("src", imgURL);
                showImage.style.objectFit = "cover";
                showImage.style.width = "100%";
                newShow.appendChild(showImage);

                newShow.append(data.name);
                document.getElementById("result-list").appendChild(newShow);
        }
    )
}

let getShowImage = function(showID) {
    console.log("ID = " + showID);
    $.get(
        `http://api.tvmaze.com/shows/${showID}`,
        function(data, textStatus, jqXHR) {
            console.log(data);
            let newSrc = data.image.medium;
            $("#showImg"+showID).attr("src",newSrc);
            return newSrc;
        }
    )
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
                    newActor.id = "actor" + resultList[i].person.id;
                    console.log("newActor.id = " + newActor.id);
                    newActor.class = "ShowInfo";
                    newActor.style.width = "20%";
                    newActor.style.display = "inline-block";
                    newActor.style.padding = "10px";
                    newActor.style.margin = "10px";
                    newActor.style.textAlign = "center";
                    newActor.style.border = "5px solid black";
                    newActor.style.borderRadius = "10px";
                    newActor.style.backgroundColor = "white";
                    newActor.style.boxShadow = "10px 10px #7E0B22";
                    newActor.style.fontFamily = "'Courier New', Courier, monospace";
                    newActor.style.fontWeight = "bold";
                    newActor.addEventListener("click", () => {
                        actorClick(resultList[i].person.id)
                    })
                    let actorImage = document.createElement("img");
                    actorImage.style.borderRadius = "10px";
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

let actorClick = function(actorID) {
    steps++;
    $("#search-count").text(`Search Count: ${steps}`)
    $.get(
        `http://api.tvmaze.com/people/${actorID}?embed=castcredits`,
        function(data, textStatus, jqXHR) {
            console.log(data);
            console.log(endActorID);

            $("#result-list").empty();

            let idMatch = actorID === gameIDPairs[gamesFinished % gameIDPairs.length][1];
            console.log(idMatch);

            if (idMatch === true) {
                $("#status-text").text(`Link Found to ${data.name}!`);
                $("#start-game").text("New Game");
                $("#start-game").attr("disabled", false);
                $("#reset").attr("disabled",true);
                $("#start-game").attr("onclick", "reset(false)");
            } else {
                showSetup(actorID);
            }
        }
    )
}

let reset = function(resetButtonUsed) {
    $("#result-list").empty();
    $("#start-game").text("Start");
    $("#start-game").attr("onclick", "showSetup()");
    $("#start-game").attr("disabled", false);

    if (!resetButtonUsed) {
        gamesFinished++;
    }

    setStartStatusText();
    steps = 0;
    $("#search-count").text("Search Count: 0");
    
}

setStartStatusText();