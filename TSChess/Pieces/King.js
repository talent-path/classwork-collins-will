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
exports.King = void 0;
var ChessPiece_1 = require("./ChessPiece");
var Piece_1 = require("./Piece");
var King = /** @class */ (function (_super) {
    __extends(King, _super);
    function King(isWhite) {
        var _this = _super.call(this, Piece_1.PieceType.King, isWhite) || this;
        _this.generateMoves = function (moveOn, loc) {
            var kingMoves = [];
            //we'll generate 4 "position" objects that represent different directions the king might move
            //then try those one at a time and add the results
            var kingDirections = [];
            kingDirections.push({ row: 0, col: 1 });
            kingDirections.push({ row: 0, col: -1 });
            kingDirections.push({ row: 1, col: 0 });
            kingDirections.push({ row: -1, col: 0 });
            kingDirections.push({ row: 1, col: 1 });
            kingDirections.push({ row: 1, col: -1 });
            kingDirections.push({ row: -1, col: 1 });
            kingDirections.push({ row: -1, col: -1 });
            for (var _i = 0, kingDirections_1 = kingDirections; _i < kingDirections_1.length; _i++) {
                var direction = kingDirections_1[_i];
                var newLoc = { row: loc.row + direction.row, col: loc.col + direction.col };
                if (King.isOnBoard(newLoc)
                    && (moveOn.allSquares[newLoc.row][newLoc.col] === null
                        || moveOn.allSquares[newLoc.row][newLoc.col].isWhite != _this.isWhite)) {
                    kingMoves.push({ from: loc, to: newLoc });
                }
            }
            return kingMoves;
        };
        _this.inCheck = false;
        return _this;
    }
    King.isOnBoard = function (loc) {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    };
    King.adjacentKing = function (moveOn, loc) {
        var kingDirections = [];
        kingDirections.push({ row: 0, col: 1 });
        kingDirections.push({ row: 0, col: -1 });
        kingDirections.push({ row: 1, col: 0 });
        kingDirections.push({ row: -1, col: 0 });
        kingDirections.push({ row: 1, col: 1 });
        kingDirections.push({ row: 1, col: -1 });
        kingDirections.push({ row: -1, col: 1 });
        kingDirections.push({ row: -1, col: -1 });
        for (var _i = 0, kingDirections_2 = kingDirections; _i < kingDirections_2.length; _i++) {
            var direction = kingDirections_2[_i];
            var newLoc = { row: loc.row + direction.row, col: loc.col + direction.col };
            if (this.isOnBoard(newLoc)
                && moveOn.allSquares[newLoc.row][newLoc.col] != null
                && moveOn.allSquares[newLoc.row][newLoc.col].kind == Piece_1.PieceType.King) {
                return true;
            }
        }
        return false;
    };
    return King;
}(ChessPiece_1.ChessPiece));
exports.King = King;
