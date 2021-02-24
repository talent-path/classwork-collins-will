import { Board } from "../Board";
import { Move } from "../Move";
import { Position } from "../Position";
import { ChessPiece } from "./ChessPiece";
import { PieceType } from "./Piece";

export class Bishop extends ChessPiece {


    constructor( isWhite : boolean ){
        super( PieceType.Bishop, isWhite );
    }

    generateMoves: (moveOn: Board, loc: Position) => Move[] = 
         (moveOn: Board, loc: Position)  => {

            let bishopMoves : Move[]  = [];

            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results

            let bishopDirections : Position[] = [];

            bishopDirections.push( {row : 1, col: 1} );
            bishopDirections.push( {row : 1, col: -1} );
            bishopDirections.push( {row : -1, col: 1} );
            bishopDirections.push( {row : -1, col: -1} );

            for( let direction of bishopDirections ){
                let directionMoves : Move[] = Bishop.slidePiece( moveOn, loc, direction, this.isWhite );
                bishopMoves.push( ...directionMoves );
            }

            return bishopMoves;
         };

    static slidePiece: (moveOn : Board, loc : Position, dir : Position, isWhite : boolean ) =>  Move[] = 
        ( moveOn : Board, loc : Position, dir : Position, isWhite: boolean ) : Move[] => {

            let allMoves : Move[] = [];

            let currentLoc : Position = { row : loc.row + dir.row, col : loc.col + dir.col };

            while( Bishop.isOnBoard( currentLoc ) && moveOn.pieceAt(currentLoc) === null ){
                allMoves.push( { from: loc, to: currentLoc  });
                currentLoc = { row: currentLoc.row + dir.row, col : currentLoc.col + dir.col };
            }

            if( Bishop.isOnBoard( currentLoc )){
                if( moveOn.pieceAt(currentLoc).isWhite != isWhite  ){
                    allMoves.push( {from: loc, to: currentLoc});
                }
            }

            return allMoves;
        }

    static isOnBoard( loc : Position ) : boolean {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    }

}