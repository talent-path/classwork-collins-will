import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class Knight extends ChessPiece {

    constructor(isWhite: boolean) {
        super(PieceType.Knight, isWhite);
    }

    generateMoves: (moveOn: Board, loc: Position) => Move[] =
        (moveOn: Board, loc: Position) => {

            let knightMoves: Move[] = [];

            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results

            let knightDirections: Position[] = [];

            knightDirections.push({ row: 2, col: 1 });
            knightDirections.push({ row: 2, col: -1 });
            knightDirections.push({ row: -2, col: 1 });
            knightDirections.push({ row: -2, col: -1 });
            knightDirections.push({ row: 1, col: 2 });
            knightDirections.push({ row: -1, col: 2 });
            knightDirections.push({ row: 1, col: -2 });
            knightDirections.push({ row: -1, col: -2 });

            for (let direction of knightDirections) {
                let newLoc: Position = { row: loc.row + direction.row, col: loc.col + direction.col }
                if (Knight.isOnBoard(newLoc)
                    && (moveOn.allSquares[newLoc.row][newLoc.col] === null 
                        || moveOn.allSquares[newLoc.row][newLoc.col].isWhite != this.isWhite )) {
                    knightMoves.push({ from: loc, to: newLoc });
                }
                // let directionMoves: Move[] = Knight.slidePiece(moveOn, loc, direction, this.isWhite);
            }

            return knightMoves;
        };


    static isOnBoard(loc: Position): boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }
}