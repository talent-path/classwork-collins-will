"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
exports.__esModule = true;
exports.BlackPawn = void 0;
var ChessPiece_1 = require("./ChessPiece");
var Piece_1 = require("./Piece");
var BlackPawn = /** @class */ (function (_super) {
    __extends(BlackPawn, _super);
    function BlackPawn() {
        var _this = _super.call(this, Piece_1.PieceType.Pawn, false) || this;
        _this.generateMoves = function (moveOn, loc) {
            var blackPawnMoves = [];
            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results
            var blackPawnDirections = [];
            blackPawnDirections.push({ row: -1, col: 0 });
            blackPawnDirections.push({ row: -2, col: 0 });
            blackPawnDirections.push({ row: -1, col: 1 });
            blackPawnDirections.push({ row: -1, col: -1 });
            var newLoc = { row: loc.row + blackPawnDirections[0].row, col: loc.col + blackPawnDirections[0].col };
            if (BlackPawn.isOnBoard(newLoc)
                && moveOn.allSquares[newLoc.row][newLoc.col] === null) {
                if (BlackPawn.canPromote(newLoc)) {
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Bishop });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Rook });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Knight });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Queen });
                }
                else {
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
                && (((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && moveOn.allSquares[newLoc.row][newLoc.col].isWhite))
                    || moveOn.enPassantCaptureLoc.includes(newLoc))) {
                if (BlackPawn.canPromote(newLoc)) {
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Bishop });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Rook });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Knight });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Queen });
                }
                else {
                    blackPawnMoves.push({ from: loc, to: newLoc });
                }
            }
            newLoc = { row: loc.row + blackPawnDirections[3].row, col: loc.col + blackPawnDirections[3].col };
            if (BlackPawn.isOnBoard(newLoc)
                && ((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && moveOn.allSquares[newLoc.row][newLoc.col].isWhite))
                || moveOn.enPassantCaptureLoc.includes(newLoc)) {
                if (BlackPawn.canPromote(newLoc)) {
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Bishop });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Rook });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Knight });
                    blackPawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Queen });
                }
                else {
                    blackPawnMoves.push({ from: loc, to: newLoc });
                }
            }
            return blackPawnMoves;
        };
        return _this;
    }
    BlackPawn.isOnBoard = function (loc) {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    };
    BlackPawn.canPromote = function (loc) {
        return loc.row === 0;
    };
    return BlackPawn;
}(ChessPiece_1.ChessPiece));
exports.BlackPawn = BlackPawn;
