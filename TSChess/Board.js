"use strict";
var __spreadArrays = (this && this.__spreadArrays) || function () {
    for (var s = 0, i = 0, il = arguments.length; i < il; i++) s += arguments[i].length;
    for (var r = Array(s), k = 0, i = 0; i < il; i++)
        for (var a = arguments[i], j = 0, jl = a.length; j < jl; j++, k++)
            r[k] = a[j];
    return r;
};
exports.__esModule = true;
var Bishop_1 = require("./Pieces/Bishop");
var BlackPawn_1 = require("./Pieces/BlackPawn");
var King_1 = require("./Pieces/King");
var Knight_1 = require("./Pieces/Knight");
var Piece_1 = require("./Pieces/Piece");
var Queen_1 = require("./Pieces/Queen");
var Rook_1 = require("./Pieces/Rook");
var WhitePawn_1 = require("./Pieces/WhitePawn");
var ChessBoard = /** @class */ (function () {
    function ChessBoard(copyFrom) {
        var _this = this;
        this.makeMove = function (toMake) {
            //TODO: enpassant stuff
            //TODO: 50 move rule stuff
            var nextBoard = new ChessBoard(_this);
            var oldPiece = nextBoard.allSquares[toMake.from.row][toMake.from.col];
            // check enpassant
            if (oldPiece.kind === Piece_1.PieceType.Pawn && Math.abs(toMake.from.row - toMake.to.row) === 2) {
                if (oldPiece.isWhite) {
                    _this.enPassantCaptureLoc.push({ row: toMake.from.row + 1, col: toMake.from.col });
                }
                else {
                    _this.enPassantCaptureLoc.push({ row: toMake.from.row - 1, col: toMake.from.col });
                }
            }
            nextBoard.allSquares[toMake.from.row][toMake.from.col] = null;
            nextBoard.allSquares[toMake.to.row][toMake.to.col] = oldPiece;
            return nextBoard;
        };
        if (copyFrom) {
            this.buildFrom(copyFrom);
        }
        this.allSquares = [];
        for (var row = 0; row < 8; row++) {
            this.allSquares[row] = [];
            for (var col = 0; col < 8; col++) {
                if (row === 6) {
                    this.allSquares[row][col] = new BlackPawn_1.BlackPawn();
                }
                if (row === 1) {
                    this.allSquares[row][col] = new WhitePawn_1.WhitePawn();
                }
                // if( row === 1 || row === 6 ){
                //     this.allSquares[row][col] =  row === 1 ? new WhitePawn() : new BlackPawn();
                // }
                if ((row === 0 || row === 7) && (col === 0 || col === 7)) {
                    this.allSquares[row][col] = new Rook_1.Rook(row === 0);
                }
                if ((row === 0 || row === 7) && (col === 1 || col === 6)) {
                    this.allSquares[row][col] = new Knight_1.Knight(row === 0);
                }
                if ((row === 0 || row === 7) && (col === 2 || col === 5)) {
                    this.allSquares[row][col] = new Bishop_1.Bishop(row === 0);
                }
                if (col === 3 && (row === 0 || row === 7)) {
                    this.allSquares[row][col] = new Queen_1.Queen(row === 0);
                }
                if (col === 4 && (row === 0 || row === 7)) {
                    this.allSquares[row][col] = new King_1.King(row === 0);
                }
                if (!this.allSquares[row][col]) {
                    this.allSquares[row][col] = null;
                }
            }
        }
    }
    //  rnbqkbnr        7
    //  pppppppp        6
    //  ........        5
    //  ........        4
    //  ........        3
    //  ........        2
    //  PPPPPPPP        1
    //  RNBQKBNR        0
    //  01234567
    ChessBoard.prototype.pieceAt = function (loc) {
        return this.allSquares[loc.row][loc.col];
    };
    ChessBoard.prototype.buildFrom = function (toCopy) {
        // [a, b, c]
        // [[a, b, c]]
        this.allSquares = [];
        for (var i = 0; i < toCopy.allSquares.length; i++) {
            this.allSquares.push(__spreadArrays(toCopy.allSquares[i]));
        }
    };
    ChessBoard.prototype.generateMoves = function () {
        var allMoves = [];
        var kingMoves = [];
        for (var row = 0; row < 8; row++) {
            for (var col = 0; col < 8; col++) {
                if (this.allSquares[row][col]) {
                    var pieceMoves = this.allSquares[row][col].generateMoves(this, { row: row, col: col });
                    if (this.allSquares[row][col].kind === Piece_1.PieceType.King) {
                        kingMoves.push.apply(kingMoves, pieceMoves);
                    }
                    else {
                        allMoves.push.apply(allMoves, pieceMoves);
                    }
                }
            }
        }
        for (var _i = 0, kingMoves_1 = kingMoves; _i < kingMoves_1.length; _i++) {
            var kingMove = kingMoves_1[_i];
            var inCheck = false;
            inCheck = King_1.King.adjacentKing(this, kingMove.to);
            for (var j = 0; j < allMoves.length && !inCheck; j++) {
                if (kingMove.to === allMoves[j].to) {
                    inCheck = true;
                }
            }
            if (inCheck) {
                kingMoves.splice(kingMoves.indexOf(kingMove), 1);
            }
        }
        allMoves.push.apply(allMoves, kingMoves);
        return allMoves;
    };
    return ChessBoard;
}());
console.log("attempting to create a board");
var testBoard = new ChessBoard();
console.log("done creating a board:");
//console.log( testBoard );
console.log(testBoard.generateMoves());
