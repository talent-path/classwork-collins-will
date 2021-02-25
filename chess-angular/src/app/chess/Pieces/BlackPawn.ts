import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class BlackPawn extends ChessPiece {
    constructor() {
        super(PieceType.Pawn, false);
    }

    generateMoves: (moveOn: Board, loc: Position) => Move[] =
        (moveOn: Board, loc: Position) => {

            let blackPawnMoves: Move[] = [];

            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results

            let blackPawnDirections: Position[] = [];

            blackPawnDirections.push({ row: -1, col: 0 });
            blackPawnDirections.push({ row: -2, col: 0 });
            blackPawnDirections.push({ row: -1, col: 1 });
            blackPawnDirections.push({ row: -1, col: -1 });

            let newLoc = { row: loc.row + blackPawnDirections[0].row, col: loc.col + blackPawnDirections[0].col };
            if (BlackPawn.isOnBoard(newLoc)
                && moveOn.allSquares[newLoc.row][newLoc.col] === null) {
                if (BlackPawn.canPromote(newLoc)) {
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Bishop })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Rook })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Knight })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Queen })
                } else {
                    blackPawnMoves.push({ from: loc, to: newLoc });
                }

            }

            newLoc = { row: loc.row + blackPawnDirections[1].row, col: loc.col + blackPawnDirections[1].col };
            if (BlackPawn.isOnBoard(newLoc)
                && blackPawnMoves.length === 1
                && loc.row === 1
                && moveOn.allSquares[newLoc.row][newLoc.col] === null) {

                blackPawnMoves.push({ from: loc, to: newLoc });

            }

            newLoc = { row: loc.row + blackPawnDirections[2].row, col: loc.col + blackPawnDirections[2].col };
            if (BlackPawn.isOnBoard(newLoc)
                && ((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && moveOn.allSquares[newLoc.row][newLoc.col].isWhite)
                )
                || (moveOn.enPassantCaptureLoc != undefined
                    && (moveOn.enPassantCaptureLoc.row === newLoc.row
                        && moveOn.enPassantCaptureLoc.col === newLoc.col))) {
                if (BlackPawn.canPromote(newLoc)) {
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Bishop })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Rook })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Knight })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Queen })
                } else {
                    blackPawnMoves.push({ from: loc, to: newLoc });
                }
            }

            newLoc = { row: loc.row + blackPawnDirections[3].row, col: loc.col + blackPawnDirections[3].col };
            if (BlackPawn.isOnBoard(newLoc)
                && ((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && moveOn.allSquares[newLoc.row][newLoc.col].isWhite)
                )
                || (moveOn.enPassantCaptureLoc != undefined
                    && (moveOn.enPassantCaptureLoc.row === newLoc.row
                        && moveOn.enPassantCaptureLoc.col === newLoc.col))) {
                if (BlackPawn.canPromote(newLoc)) {
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Bishop })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Rook })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Knight })
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Queen })
                } else {
                    blackPawnMoves.push({ from: loc, to: newLoc });
                }
            }

            return blackPawnMoves;
        };



    static isOnBoard(loc: Position): boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }

    static canPromote(loc: Position): boolean {
        return loc.row === 0;
    }
}