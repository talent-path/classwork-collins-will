let xTurn = true;
let board = [0,0,0,0,0,0,0,0,0];
let wins = {
    xWins: 0,
    oWins: 0,
    draws: 0
}
reset();

function handleClick(buttonID) {
    console.log(buttonID);

    const xImg = document.createElement("img");
    xImg.setAttribute("src","https://cdn.pixabay.com/photo/2012/04/12/20/12/x-30465_960_720.png");
    xImg.classList.add("button-image");

    const oImg = document.createElement("img");
    oImg.setAttribute("src","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Letter_o.svg/1200px-Letter_o.svg.png");
    oImg.classList.add("button-image");

    let toDisable = document.getElementById('Button' + buttonID);
    toDisable.disabled = true;
    
    if (xTurn) {
        toDisable.appendChild(xImg);
        board[buttonID-1] = -1;
    } else {
        toDisable.appendChild(oImg);
        board[buttonID-1] = 1;
    }
    console.log(board);
    let w = checkWin();
    let status = document.getElementById("GameStatus");
    if (w == 1) {
        status.innerHTML = `${xTurn ? 'X Wins!' : 'O Wins!'}`;
        if (xTurn) {
            wins.xWins++;
            document.getElementById("XWins").innerHTML = wins.xWins;
        } else {
            wins.oWins++;
            document.getElementById("OWins").innerHTML = wins.oWins;
        }
        //alert(`${xTurn ? 'X Wins!' : 'O Wins!'}`);
        disableAll();
    } else if (w == 0) {
        status.innerHTML = "It's a draw!";
        wins.draws++;
        document.getElementById("Draws").innerHTML = wins.draws;
    }
    xTurn = !xTurn;
    console.log(wins);
}

function checkWin() {
    let status = -1;
    let product = 1;

    for (let i = 0; i < 9; i++) {
        product *= board[i];
    }
    if (product != 0) {
        status = 0;
    }

    for (let row = 0; row < 3; row++) {
        let rowSum = board[row * 3 + 0] + board[row * 3 + 1] + board[row * 3 + 2];
        if (rowSum * rowSum == 9) {
            status = 1;
        }
    }

    for (let col = 0; col < 3; col++) {
        let colSum = board[3 * 0 + col] + board[3 * 1+ col] + board[3 * 2 + col];
        if (colSum * colSum == 9) {
            status = 1;
        }
    }

    let d1Sum = board[0] + board[4] + board[8];
    if (d1Sum * d1Sum == 9) {
        status = 1;
    }

    let d2Sum = board[2] + board[4] + board[6];
    if (d2Sum * d2Sum == 9) {
        status = 1;
    }

    return status;
}

function reset() {
    nextRound();
    wins.xWins = 0;
    wins.oWins = 0;
    wins.draws = 0;
    document.getElementById("XWins").innerHTML = wins.xWins;
    document.getElementById("OWins").innerHTML = wins.oWins;
    document.getElementById("Draws").innerHTML = wins.draws;
}

function nextRound() {
    let status = document.getElementById("GameStatus");
    status.innerHTML = "";
    for (let i = 1; i <= 9; i++) {
        let toDisable = document.getElementById('Button' + i);
    toDisable.disabled = false;
    toDisable.innerHTML = "";
    board[i-1] = 0;
    }
    xTurn = true;
}

function disableAll() {
    for (let i = 1; i <= 9; i++) {
        let toDisable = document.getElementById('Button' + i);
    toDisable.disabled = true;
    }
}