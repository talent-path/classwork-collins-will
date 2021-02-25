import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";

export enum PieceType{
    Pawn,
    Knight,
    Bishop,
    Rook,
    Queen,
    King
}

export interface Piece{
    kind : PieceType;
    isWhite : boolean;
    generateMoves : ( moveOn : Board, loc: Position ) => Move[];
}

