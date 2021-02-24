import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class WhitePawn extends ChessPiece {

    constructor() {
        super(PieceType.Pawn, true);
    }

    generateMoves: (moveOn: Board, loc: Position) => Move[] =
        (moveOn: Board, loc: Position) => {

            let whitePawnMoves: Move[] = [];

            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results

            let whitePawnDirections: Position[] = [];

            whitePawnDirections.push({ row: 1, col: 0 });
            whitePawnDirections.push({ row: 2, col: 0 });
            whitePawnDirections.push({ row: 1, col: 1 });
            whitePawnDirections.push({ row: 1, col: -1 });

            let newLoc = { row: loc.row + whitePawnDirections[0].row, col: loc.col + whitePawnDirections[0].col };
            if (WhitePawn.isOnBoard(newLoc)
                && moveOn.allSquares[newLoc.row][newLoc.col] === null) {
                if (WhitePawn.canPromote(newLoc)) {
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Bishop })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Rook })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Knight })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Queen })
                } else {
                    whitePawnMoves.push({ from: loc, to: newLoc });
                }

            }

            newLoc = { row: loc.row + whitePawnDirections[1].row, col: loc.col + whitePawnDirections[1].col };
            if (WhitePawn.isOnBoard(newLoc)
                && whitePawnMoves.length === 1
                && loc.row === 1
                && moveOn.allSquares[newLoc.row][newLoc.col] === null) {

                whitePawnMoves.push({ from: loc, to: newLoc });

            }

            newLoc = { row: loc.row + whitePawnDirections[2].row, col: loc.col + whitePawnDirections[2].col };
            if (WhitePawn.isOnBoard(newLoc)
                && ((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && !moveOn.allSquares[newLoc.row][newLoc.col].isWhite)
                    )
                    || moveOn.enPassantCaptureLoc.includes(newLoc)) {
                if (WhitePawn.canPromote(newLoc)) {
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Bishop })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Rook })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Knight })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Queen })
                } else {
                    whitePawnMoves.push({ from: loc, to: newLoc });
                }
            }

            newLoc = { row: loc.row + whitePawnDirections[3].row, col: loc.col + whitePawnDirections[3].col };
            if (WhitePawn.isOnBoard(newLoc)
                && ((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && !moveOn.allSquares[newLoc.row][newLoc.col].isWhite)
                    )
                    || moveOn.enPassantCaptureLoc.includes(newLoc)) {
                if (WhitePawn.canPromote(newLoc)) {
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Bishop })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Rook })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Knight })
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: PieceType.Queen })
                } else {
                    whitePawnMoves.push({ from: loc, to: newLoc });
                }
            }

            return whitePawnMoves;
        };



    static isOnBoard(loc: Position): boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }

    static canPromote(loc: Position): boolean {
        return loc.row === 7;
    }
}