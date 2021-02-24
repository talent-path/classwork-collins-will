import { Position } from "./Position";
import { PieceType } from "./Pieces/Piece";

export interface Move{

    from : Position;
    to : Position;
    //end piece (for promotions)
    promoteTo?: PieceType;
}