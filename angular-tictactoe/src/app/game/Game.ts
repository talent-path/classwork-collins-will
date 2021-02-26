import { Position } from "./Position";

export interface Game {
    pieces : number[][];
    isXTurn : boolean;
    gameStatus : number;

    makeMove : (pos : Position) => void;
    checkGameOver : () => number;
}

export class TicTacToeGame implements Game {
    pieces: number[][];
    isXTurn: boolean = true;
    gameStatus : number = -2;

    constructor() {
        this.pieces = [];
        for (let i = 0; i < 3; i++) {
            this.pieces[i] = [];
            for (let j = 0; j < 3; j++) {
                this.pieces[i][j] = 0;
            }
        }
    }

    makeMove(pos : Position) : void {
        if (this.pieces[pos.row][pos.col] === 0 && this.gameStatus === -2) {
            this.pieces[pos.row][pos.col] = this.isXTurn ? 1 : -1;
            console.log(this.gameStatus);
            this.gameStatus = this.checkGameOver();
            this.isXTurn = !this.isXTurn;
        }
    }

    checkGameOver() : number {
        if (this.checkRows() || this.checkCols() || this.checkDiags()) {
            return this.isXTurn ? 1 : -1;
        } else {
            if (this.checkFullBoard()) {
                return 0;
            }
        }
        return -2;
    }

    checkRows() : boolean {
        for (let i = 0; i < 3; i++) {
            let rowSum = this.pieces[i][0] + this.pieces[i][1] + this.pieces[i][2];
            if (Math.pow(rowSum, 2) === 9) {
                return true;
            }
        }
        return false;
    }

    checkCols() : boolean {
        for (let i = 0; i < 3; i++) {
            let colSum = this.pieces[0][i] + this.pieces[1][i] + this.pieces[2][i];
            if (Math.pow(colSum, 2) === 9) {
                return true;
            }
        }
        return false;
    }

    checkDiags() : boolean {
        let diag1Sum = this.pieces[0][0] + this.pieces[1][1] + this.pieces[2][2];
        if (Math.pow(diag1Sum, 2) === 9) {
            return true;
        }
        let diag2Sum = this.pieces[0][2] + this.pieces[1][1] + this.pieces[2][0];
        if (Math.pow(diag2Sum, 2) === 9) {
            return true;
        }
        return false;
    }

    checkFullBoard() : boolean {
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                if (this.pieces[i][j] === 0) {
                    return false;
                }
            }
        }
        return true;
    }

}