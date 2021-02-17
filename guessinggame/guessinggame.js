let secretNum = "1234";

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
    digitBoxes.style.marginLeft = "auto";
    digitBoxes.style.marginRight = "auto";
    
    let digitElements = [];
    for (let i = 0; i < 4; i++) {
        let digitBox = document.createElement("div");
        digitBox.id = "digit" + (i+1);
        digitBox.style.border = "5px solid black";
        digitBox.style.display = "inline-block";
        digitBox.style.height = "100px";
        digitBox.style.width = "100px";
        digitBox.style.textAlign = "center";
        digitBox.style.fontSize = "48px";
        digitBox.style.margin = "auto";
        digitBox.style.padding = "0px 0";
        digitBoxes.appendChild(digitBox);
    }
    digitBoxes.style.justifyContent = "center";
    digitBoxes.style.justifySelf = "center";

    bodyTag.appendChild(digitBoxes);

    let input = document.createElement("div");
    let textBox = document.createElement("input");
    textBox.id = "textbox";
    textBox.type = "text";
    textBox.style.width = "100px";
    textBox.style.display = "inline-block";
    input.appendChild(textBox);

    let inputButton = document.createElement("button");
    inputButton.append("Guess");
    inputButton.onclick = guess;
    inputButton.style.width = "50px";
    input.appendChild(inputButton);
    bodyTag.appendChild(input);
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
        for (let i = 0; i < 4; i++) {
            let currentBox = document.getElementById("digit"+(i+1));
            currentBox.innerHTML = currentGuess.charAt(i);
            if (secretNum.charAt(i) === currentGuess.charAt(i)) {
                currentBox.style.backgroundColor = "green";
            } else if (secretNum.includes(currentGuess.charAt(i))) {
                currentBox.style.backgroundColor = "yellow";
            } else {
                currentBox.style.backgroundColor = "red";
            }
        }
    }
}

setup();
secretNum = randomNum();