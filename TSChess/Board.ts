import { Move } from "./Move";
import { Bishop } from "./Pieces/Bishop";
import { BlackPawn } from "./Pieces/BlackPawn";
import { ChessPiece } from "./Pieces/ChessPiece";
import { King } from "./Pieces/King";
import { Knight } from "./Pieces/Knight";
import { Piece, PieceType } from "./Pieces/Piece"
import { Queen } from "./Pieces/Queen";
import { Rook } from "./Pieces/Rook";
import { WhitePawn } from "./Pieces/WhitePawn";
import { Position } from "./Position"

export interface Board {

    allSquares: Piece[][];

    isWhiteTurn: boolean;
    wKingSideCastle: boolean;
    wQueenSideCastle: boolean;
    bKingSideCastle: boolean;
    bQueenSideCastle: boolean;

    fiftyMoveCount: number;
    enPassantCaptureLoc: Position[];

    makeMove: (this: Board, toMake: Move) => Board;
    pieceAt: (loc: Position) => Piece;
    generateMoves: () => Move[];
}


class ChessBoard implements Board {

    //TODO: capture this in the copy constructor at some point...
    fiftyMoveCount: number;
    enPassantCaptureLoc: Position[];

    isWhiteTurn: boolean;
    wKingSideCastle: boolean;
    wQueenSideCastle: boolean;
    bKingSideCastle: boolean;
    bQueenSideCastle: boolean;

    allSquares: Piece[][];

    //  rnbqkbnr        7
    //  pppppppp        6
    //  ........        5
    //  ........        4
    //  ........        3
    //  ........        2
    //  PPPPPPPP        1
    //  RNBQKBNR        0

    //  01234567

    pieceAt(loc: Position): Piece {

        return this.allSquares[loc.row][loc.col];
    }


    constructor(copyFrom?: ChessBoard) {
        if (copyFrom) {
            this.buildFrom(copyFrom);
        }
        this.allSquares = [];
        for (let row = 0; row < 8; row++) {
            this.allSquares[row] = [];
            for (let col = 0; col < 8; col++) {
                if (row === 6) {
                    this.allSquares[row][col] = new BlackPawn();
                }
                if (row === 1) {
                    this.allSquares[row][col] = new WhitePawn();
                }

                // if( row === 1 || row === 6 ){
                //     this.allSquares[row][col] =  row === 1 ? new WhitePawn() : new BlackPawn();
                // }

                if ((row === 0 || row === 7) && (col === 0 || col === 7)) {
                    this.allSquares[row][col] = new Rook(row === 0);
                }

                if ((row === 0 || row === 7) && (col === 1 || col === 6)) {
                    this.allSquares[row][col] = new Knight(row === 0);
                }

                if ((row === 0 || row === 7) && (col === 2 || col === 5)) {
                    this.allSquares[row][col] = new Bishop(row === 0);
                }

                if (col === 3 && (row === 0 || row === 7)) {
                    this.allSquares[row][col] = new Queen(row === 0);
                }

                if (col === 4 && (row === 0 || row === 7)) {
                    this.allSquares[row][col] = new King(row === 0);
                }

                if (!this.allSquares[row][col]) {

                    this.allSquares[row][col] = null;
                }

            }
        }
    }

    buildFrom(toCopy: Board) {

        // [a, b, c]
        // [[a, b, c]]

        this.allSquares = [];
        for (let i = 0; i < toCopy.allSquares.length; i++) {
            this.allSquares.push([...toCopy.allSquares[i]]);
        }
    }


    makeMove: (toMake: Move) => Board = toMake => {

        //TODO: enpassant stuff
        //TODO: 50 move rule stuff

        let nextBoard: ChessBoard = new ChessBoard(this);


        let oldPiece: Piece = nextBoard.allSquares[toMake.from.row][toMake.from.col];

        // check enpassant
        if (oldPiece.kind === PieceType.Pawn && Math.abs(toMake.from.row - toMake.to.row) === 2) {
            if (oldPiece.isWhite) {
                this.enPassantCaptureLoc.push({ row: toMake.from.row + 1, col: toMake.from.col })
            } else {
                this.enPassantCaptureLoc.push({ row: toMake.from.row - 1, col: toMake.from.col })
            }
        }

        nextBoard.allSquares[toMake.from.row][toMake.from.col] = null;
        nextBoard.allSquares[toMake.to.row][toMake.to.col] = oldPiece;



        return nextBoard;
    }

    kingInCheck(loc: Position, whiteKing: boolean): boolean {

        // horizontal and vertical
        let rookDirections: Position[] = [];

        rookDirections.push({ row: 0, col: 1 });
        rookDirections.push({ row: 0, col: -1 });
        rookDirections.push({ row: -1, col: 0 });
        rookDirections.push({ row: 1, col: 0 });

        for (let rook of rookDirections) {
            let currentLoc: Position = { row: loc.row + rook.row, col: loc.col + rook.col };

            while (Rook.isOnBoard(currentLoc) && this.pieceAt(currentLoc) === null) {
                currentLoc = { row: currentLoc.row + rook.row, col: currentLoc.col + rook.col };
            }

            if (Rook.isOnBoard(currentLoc)) {
                if (this.pieceAt(currentLoc).isWhite != whiteKing
                    && (this.pieceAt(currentLoc).kind === PieceType.Queen
                        || this.pieceAt(currentLoc).kind === PieceType.Rook)) {
                    return true;
                }
            }
        }

        // diagonal
        let bishopDirections: Position[] = [];
        let diagOffset: number = 0;

        bishopDirections.push({ row: 1, col: 1 });
        bishopDirections.push({ row: -1, col: -1 });
        bishopDirections.push({ row: -1, col: 1 });
        bishopDirections.push({ row: 1, col: -1 });

        for (let bishop of bishopDirections) {
            let currentLoc: Position = { row: loc.row + bishop.row, col: loc.col + bishop.col };

            while (Rook.isOnBoard(currentLoc) && this.pieceAt(currentLoc) === null) {
                currentLoc = { row: currentLoc.row + bishop.row, col: currentLoc.col + bishop.col };
                diagOffset++;
            }

            if (Rook.isOnBoard(currentLoc)) {
                if (this.pieceAt(currentLoc).isWhite != whiteKing
                    && (this.pieceAt(currentLoc).kind === PieceType.Queen
                        || this.pieceAt(currentLoc).kind === PieceType.Bishop
                        || (this.pieceAt(currentLoc).kind === PieceType.Pawn && diagOffset === 1))) {
                    return true;
                }
            }
        }

        // knights
        let knightDirections: Position[] = [];

        knightDirections.push({ row: 2, col: -1 });
        knightDirections.push({ row: 2, col: 1 });
        knightDirections.push({ row: -2, col: -1 });
        knightDirections.push({ row: -2, col: 1 });
        knightDirections.push({ row: -1, col: 2 });
        knightDirections.push({ row: 1, col: 2 });
        knightDirections.push({ row: -1, col: -2 });
        knightDirections.push({ row: 1, col: -2 });

        for (let direction of knightDirections) {
            let newLoc: Position = { row: loc.row + direction.row, col: loc.col + direction.col }
            if (Knight.isOnBoard(newLoc)
                && this.allSquares[newLoc.row][newLoc.col] != null
                && this.allSquares[newLoc.row][newLoc.col].isWhite != whiteKing) {
                return true;
            }
            // let directionMoves: Move[] = Knight.slidePiece(moveOn, loc, direction, this.isWhite);
        }

        return false;
    }

    generateMoves(): Move[] {

        let allMoves: Move[] = [];
        let kingMoves: Move[] = [];
        let whiteKingLoc: Position = null;
        let blackKingLoc: Position = null;

        for (let row = 0; row < 8; row++) {
            for (let col = 0; col < 8; col++) {
                if (this.allSquares[row][col]) {
                    let pieceMoves: Move[] = this.allSquares[row][col].generateMoves(this, { row, col });
                    if (this.allSquares[row][col].kind === PieceType.King) {
                        if (this.allSquares[row][col].isWhite) {
                            whiteKingLoc = { row: row, col: col };
                        } else {
                            blackKingLoc = { row: row, col: col };
                        }
                        kingMoves.push(...pieceMoves);
                    } else {
                        allMoves.push(...pieceMoves);
                    }
                }
            }
        }

        for (let pieceMove of allMoves) {
            let newBoard: ChessBoard = new ChessBoard(this);
            let pieceIsWhite: boolean = this.allSquares[pieceMove.from.row][pieceMove.from.col].isWhite;
            newBoard.makeMove(pieceMove);
            let inCheck = newBoard.kingInCheck(pieceIsWhite ? whiteKingLoc : blackKingLoc, pieceIsWhite);
            if (inCheck) {
                allMoves.splice(allMoves.indexOf(pieceMove), 1);
            }
        }

        for (let kingMove of kingMoves) {
            let inCheck: boolean = false;

            inCheck = King.adjacentKing(this, kingMove.to);

            for (let j = 0; j < allMoves.length && !inCheck; j++) {
                if (kingMove.to === allMoves[j].to) {
                    inCheck = true;
                }
            }
            if (inCheck) {
                kingMoves.splice(kingMoves.indexOf(kingMove), 1);
            }
        }
        allMoves.push(...kingMoves);


        return allMoves;
    }

}

console.log("attempting to create a board")
let testBoard: Board = new ChessBoard();
console.log("done creating a board:");
//console.log( testBoard );

console.log(testBoard.generateMoves());

