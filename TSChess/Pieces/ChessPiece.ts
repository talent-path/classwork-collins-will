import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { PieceType, Piece } from "./Piece";


export abstract class ChessPiece implements Piece {
    kind : PieceType;
    isWhite : boolean;

    constructor( kind : PieceType, isWhite : boolean ){
        this.kind = kind;
        this.isWhite = isWhite;
    }

    generateMoves : ( moveOn : Board, loc: Position ) => Move[];

}