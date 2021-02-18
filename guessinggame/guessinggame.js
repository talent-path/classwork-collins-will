let secretNum = "";

let setup = function() {
    let allBodyTags = document.getElementsByTagName("body");

    let bodyTag = allBodyTags[0];

    bodyTag.style.alignContent = "center";

    let titleSpan = document.createElement("p");
    titleSpan.append("Guessing Game");
    titleSpan.style.textAlign = "center";

    bodyTag.appendChild(titleSpan);

    let digitBoxes = document.createElement("div");
    digitBoxes.id = "boxes";
    digitBoxes.style.width = "50%";
    digitBoxes.style.height = "100px";
    digitBoxes.style.marginLeft = "auto";
    digitBoxes.style.marginRight = "auto";
    digitBoxes.style.border = "1px solid black";
    
    let digitElements = [];
    for (let i = 0; i < 4; i++) {
        let digitBox = document.createElement("div");
        digitBox.id = "digit" + (i+1);
        digitBox.style.border = "2px solid black"
        digitBox.style.display = "inline-block";
        digitBox.style.height = "100px";
        digitBox.style.width = "25%";
        digitBox.style.textAlign = "center";
        digitBox.style.fontSize = "72px";
        digitBox.style.margin = "auto";
        digitBox.style.padding = "auto";
        digitBox.style.boxSizing = "border-box";
        digitBoxes.appendChild(digitBox);
    }

    bodyTag.appendChild(digitBoxes);

    let input = document.createElement("div");
    input.style.width = "300px";
    input.style.marginLeft = "auto";
    input.style.marginRight = "auto";
    input.style.paddingTop = "15px";
    let textBox = document.createElement("input");
    textBox.id = "textbox";
    textBox.type = "text";
    textBox.style.width = "100px";
    textBox.style.display = "inline-block";
    input.appendChild(textBox);

    let inputButton = document.createElement("button");
    inputButton.id = "guess";
    inputButton.append("Guess");
    inputButton.onclick = guess;
    inputButton.style.width = "75px";
    inputButton.style.display = "inline-block";
    input.appendChild(inputButton);

    let resetButton = document.createElement("button");
    resetButton.append("Reset");
    resetButton.onclick = reset;
    resetButton.style.width = "75px";
    resetButton.style.display = "inline-block";
    input.appendChild(resetButton);
    bodyTag.appendChild(input);

    let errorMessage = document.createElement("p");
    errorMessage.id = "error";
    errorMessage.innerHTML = "Welcome!";
    errorMessage.style.textAlign = "center";
    bodyTag.appendChild(errorMessage);
    
}

let randomNum = function() {
    let digits = [0,1,2,3,4,5,6,7,8,9];
    let output = "";
    for (let i = 0; i < 4; i++) {
        let index = Math.floor(Math.random() * digits.length)
        let newDigit = digits[index];
        output += newDigit;
        digits.splice(index, 1);
    }
    return output;
}

let guess = function() {
    let currentGuess = document.getElementById("textbox").value;
    if (currentGuess.length == 4) {
        let valid = true;
        for (let check = 0; check < 4; check++) {
            if (currentGuess[check] < '0' || currentGuess[check] > '9') {
                valid = false;
                break;
            }
        }
        if (valid) {
            document.getElementById("error").innerHTML = "";
            let goodDigitCount = 0;
            for (let i = 0; i < 4; i++) {
                let currentBox = document.getElementById("digit"+(i+1));
                currentBox.innerHTML = currentGuess.charAt(i);
                if (secretNum.charAt(i) === currentGuess.charAt(i)) {
                    currentBox.style.backgroundColor = "green";
                    goodDigitCount++;
                } else if (secretNum.includes(currentGuess.charAt(i))) {
                    currentBox.style.backgroundColor = "yellow";
                } else {
                    currentBox.style.backgroundColor = "red";
                }
            }
            if (goodDigitCount === 4) {
                document.getElementById("error").innerHTML = "You win!";
                document.getElementById("guess").disabled = "true";
            }
        } else {
            document.getElementById("error").innerHTML = "Guess must only contain digits";
        }
    } else {
        document.getElementById("error").innerHTML = "Guess must be 4 digits long";
    }
}

let reset = function() {
    for (let i = 1; i <= 4; i++) {
        let currentBox = document.getElementById("digit" + i);
        currentBox.innerHTML = "";
        currentBox.style.backgroundColor = "white";
    }
    secretNum = randomNum();
    document.getElementById("textbox").value = "";
    document.getElementById("error").innerHTML = "New game started";
    document.getElementById("guess").disabled = "";
}

setup();
secretNum = randomNum();