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
exports.WhitePawn = void 0;
var ChessPiece_1 = require("./ChessPiece");
var Piece_1 = require("./Piece");
var WhitePawn = /** @class */ (function (_super) {
    __extends(WhitePawn, _super);
    function WhitePawn() {
        var _this = _super.call(this, Piece_1.PieceType.Pawn, true) || this;
        _this.generateMoves = function (moveOn, loc) {
            var whitePawnMoves = [];
            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results
            var whitePawnDirections = [];
            whitePawnDirections.push({ row: 1, col: 0 });
            whitePawnDirections.push({ row: 2, col: 0 });
            whitePawnDirections.push({ row: 1, col: 1 });
            whitePawnDirections.push({ row: 1, col: -1 });
            var newLoc = { row: loc.row + whitePawnDirections[0].row, col: loc.col + whitePawnDirections[0].col };
            if (WhitePawn.isOnBoard(newLoc)
                && moveOn.allSquares[newLoc.row][newLoc.col] === null) {
                if (WhitePawn.canPromote(newLoc)) {
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Bishop });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Rook });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Knight });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Queen });
                }
                else {
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
                    && !moveOn.allSquares[newLoc.row][newLoc.col].isWhite))
                || moveOn.enPassantCaptureLoc.includes(newLoc)) {
                if (WhitePawn.canPromote(newLoc)) {
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Bishop });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Rook });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Knight });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Queen });
                }
                else {
                    whitePawnMoves.push({ from: loc, to: newLoc });
                }
            }
            newLoc = { row: loc.row + whitePawnDirections[3].row, col: loc.col + whitePawnDirections[3].col };
            if (WhitePawn.isOnBoard(newLoc)
                && ((moveOn.allSquares[newLoc.row][newLoc.col] != null
                    && !moveOn.allSquares[newLoc.row][newLoc.col].isWhite))
                || moveOn.enPassantCaptureLoc.includes(newLoc)) {
                if (WhitePawn.canPromote(newLoc)) {
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Bishop });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Rook });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Knight });
                    whitePawnMoves.push({ from: loc, to: newLoc, promoteTo: Piece_1.PieceType.Queen });
                }
                else {
                    whitePawnMoves.push({ from: loc, to: newLoc });
                }
            }
            return whitePawnMoves;
        };
        return _this;
    }
    WhitePawn.isOnBoard = function (loc) {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    };
    WhitePawn.canPromote = function (loc) {
        return loc.row === 7;
    };
    return WhitePawn;
}(ChessPiece_1.ChessPiece));
exports.WhitePawn = WhitePawn;
