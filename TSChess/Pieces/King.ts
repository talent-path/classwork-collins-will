import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class King extends ChessPiece {

    constructor(isWhite: boolean) {
        super(PieceType.King, isWhite);
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

            return kingMoves;
        };

    static isOnBoard(loc: Position): boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }
}