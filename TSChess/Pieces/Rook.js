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
exports.Rook = void 0;
var ChessPiece_1 = require("./ChessPiece");
var Piece_1 = require("./Piece");
var Rook = /** @class */ (function (_super) {
    __extends(Rook, _super);
    function Rook(isWhite) {
        var _this = _super.call(this, Piece_1.PieceType.Rook, isWhite) || this;
        _this.generateMoves = function (moveOn, loc) {
            var rookMoves = [];
            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results
            var rookDirections = [];
            rookDirections.push({ row: 0, col: 1 });
            rookDirections.push({ row: 0, col: -1 });
            rookDirections.push({ row: 1, col: 0 });
            rookDirections.push({ row: -1, col: 0 });
            for (var _i = 0, rookDirections_1 = rookDirections; _i < rookDirections_1.length; _i++) {
                var direction = rookDirections_1[_i];
                var directionMoves = Rook.slidePiece(moveOn, loc, direction, _this.isWhite);
                rookMoves.push.apply(rookMoves, directionMoves);
            }
            return rookMoves;
        };
        return _this;
    }
    Rook.isOnBoard = function (loc) {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    };
    Rook.slidePiece = function (moveOn, loc, dir, isWhite) {
        var allMoves = [];
        var currentLoc = { row: loc.row + dir.row, col: loc.col + dir.col };
        while (Rook.isOnBoard(currentLoc) && moveOn.pieceAt(currentLoc) === null) {
            allMoves.push({ from: loc, to: currentLoc });
            currentLoc = { row: currentLoc.row + dir.row, col: currentLoc.col + dir.col };
        }
        if (Rook.isOnBoard(currentLoc)) {
            if (moveOn.pieceAt(currentLoc).isWhite != isWhite) {
                allMoves.push({ from: loc, to: currentLoc });
            }
        }
        return allMoves;
    };
    return Rook;
}(ChessPiece_1.ChessPiece));
exports.Rook = Rook;
