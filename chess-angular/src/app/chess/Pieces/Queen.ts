import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class Queen extends ChessPiece {

    constructor(isWhite: boolean) {
        super(PieceType.Queen, isWhite);
    }

    generateMoves: (moveOn: Board, loc: Position) => Move[] =
        (moveOn: Board, loc: Position) => {

            let queenMoves: Move[] = [];

            //we'll generate 4 "position" objects that represent different directions the queen might move
            //then try those one at a time and add the results

            let queenDirections: Position[] = [];

            queenDirections.push({ row: 0, col: 1 });
            queenDirections.push({ row: 0, col: -1 });
            queenDirections.push({ row: 1, col: 0 });
            queenDirections.push({ row: -1, col: 0 });
            queenDirections.push({ row: 1, col: 1 });
            queenDirections.push({ row: 1, col: -1 });
            queenDirections.push({ row: -1, col: 1 });
            queenDirections.push({ row: -1, col: -1 });

            for (let direction of queenDirections) {
                let directionMoves: Move[] = Queen.slidePiece(moveOn, loc, direction, this.isWhite);
                queenMoves.push(...directionMoves);
            }

            return queenMoves;
        };

    static slidePiece: (moveOn: Board, loc: Position, dir: Position, isWhite: boolean) => Move[] =
        (moveOn: Board, loc: Position, dir: Position, isWhite: boolean): Move[] => {

            let allMoves: Move[] = [];

            let currentLoc: Position = { row: loc.row + dir.row, col: loc.col + dir.col };

            while (Queen.isOnBoard(currentLoc) && moveOn.pieceAt(currentLoc) === null) {
                allMoves.push({ from: loc, to: currentLoc });
                currentLoc = { row: currentLoc.row + dir.row, col: currentLoc.col + dir.col };
            }

            if (Queen.isOnBoard(currentLoc)) {
                if (moveOn.pieceAt(currentLoc).isWhite != isWhite) {
                    allMoves.push({ from: loc, to: currentLoc });
                }
            }

            return allMoves;
        }

    static isOnBoard(loc: Position): boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }
}