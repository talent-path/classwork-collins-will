import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class King extends ChessPiece {

    inCheck : boolean;

    constructor(isWhite: boolean) {
        super(PieceType.King, isWhite);
        this.inCheck = false;
    }

    generateMoves: (moveOn: Board, loc: Position) => Move[] =
        (moveOn: Board, loc: Position) => {

            let kingMoves: Move[] = [];

            //we'll generate 4 "position" objects that represent different directions the king might move
            //then try those one at a time and add the results

            let kingDirections: Position[] = [];

            kingDirections.push({ row: 0, col: 1 });
            kingDirections.push({ row: 0, col: -1 });
            kingDirections.push({ row: 1, col: 0 });
            kingDirections.push({ row: -1, col: 0 });
            kingDirections.push({ row: 1, col: 1 });
            kingDirections.push({ row: 1, col: -1 });
            kingDirections.push({ row: -1, col: 1 });
            kingDirections.push({ row: -1, col: -1 });

            for (let direction of kingDirections) {
                let newLoc: Position = { row: loc.row + direction.row, col: loc.col + direction.col }
                if (King.isOnBoard(newLoc)
                    && (moveOn.allSquares[newLoc.row][newLoc.col] === null 
                        || moveOn.allSquares[newLoc.row][newLoc.col].isWhite != this.isWhite )) {
                    
                    kingMoves.push({ from: loc, to: newLoc });
                }
            }

            // castling
            if (this.isWhite) {
                if (moveOn.wKingSideCastle
                    && moveOn.allSquares[0][5] === null
                    && moveOn.allSquares[0][6] === null) {
                    kingMoves.push({from: loc, to: {row: loc.row, col: loc.col + 2}});
                }
                if (moveOn.wQueenSideCastle
                    && moveOn.allSquares[0][3] === null
                    && moveOn.allSquares[0][2] === null
                    && moveOn.allSquares[0][1]) {
                    kingMoves.push({from: loc, to: {row: loc.row, col: loc.col - 2}});
                }
            } else {
                if (moveOn.bKingSideCastle
                    && moveOn.allSquares[7][5] === null
                    && moveOn.allSquares[7][6] === null) {
                    kingMoves.push({from: loc, to: {row: loc.row, col: loc.col + 2}});
                }
                if (moveOn.bQueenSideCastle
                    && moveOn.allSquares[7][3] === null
                    && moveOn.allSquares[7][2] === null
                    && moveOn.allSquares[7][1]) {
                    kingMoves.push({from: loc, to: {row: loc.row, col: loc.col - 2}});
                }
            }

            return kingMoves;
        };

    static isOnBoard(loc: Position): boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }

    static adjacentKing(moveOn : Board, loc : Position) : boolean {
        let kingDirections: Position[] = [];

        kingDirections.push({ row: 0, col: 1 });
        kingDirections.push({ row: 0, col: -1 });
        kingDirections.push({ row: 1, col: 0 });
        kingDirections.push({ row: -1, col: 0 });
        kingDirections.push({ row: 1, col: 1 });
        kingDirections.push({ row: 1, col: -1 });
        kingDirections.push({ row: -1, col: 1 });
        kingDirections.push({ row: -1, col: -1 });

        for (let direction of kingDirections) {
            let newLoc = {row: loc.row + direction.row, col: loc.col + direction.col};
            if (this.isOnBoard(newLoc)
                && moveOn.allSquares[newLoc.row][newLoc.col] != null
                && moveOn.allSquares[newLoc.row][newLoc.col].kind == PieceType.King) {
                    return true;
            }
        }

        return false;
    }
}