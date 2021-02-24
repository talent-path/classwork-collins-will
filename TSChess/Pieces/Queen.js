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
exports.Queen = void 0;
var ChessPiece_1 = require("./ChessPiece");
var Piece_1 = require("./Piece");
var Queen = /** @class */ (function (_super) {
    __extends(Queen, _super);
    function Queen(isWhite) {
        var _this = _super.call(this, Piece_1.PieceType.Queen, isWhite) || this;
        _this.generateMoves = function (moveOn, loc) {
            var queenMoves = [];
            //we'll generate 4 "position" objects that represent different directions the queen might move
            //then try those one at a time and add the results
            var queenDirections = [];
            queenDirections.push({ row: 0, col: 1 });
            queenDirections.push({ row: 0, col: -1 });
            queenDirections.push({ row: 1, col: 0 });
            queenDirections.push({ row: -1, col: 0 });
            queenDirections.push({ row: 1, col: 1 });
            queenDirections.push({ row: 1, col: -1 });
            queenDirections.push({ row: -1, col: 1 });
            queenDirections.push({ row: -1, col: -1 });
            for (var _i = 0, queenDirections_1 = queenDirections; _i < queenDirections_1.length; _i++) {
                var direction = queenDirections_1[_i];
                var directionMoves = Queen.slidePiece(moveOn, loc, direction, _this.isWhite);
                queenMoves.push.apply(queenMoves, directionMoves);
            }
            return queenMoves;
        };
        return _this;
    }
    Queen.isOnBoard = function (loc) {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    };
    Queen.slidePiece = function (moveOn, loc, dir, isWhite) {
        var allMoves = [];
        var currentLoc = { row: loc.row + dir.row, col: loc.col + dir.col };
        while (Queen.isOnBoard(currentLoc) && moveOn.pieceAt(currentLoc) === null) {
            allMoves.push({ from: loc, to: currentLoc });
            currentLoc = { row: currentLoc.row + dir.row, col: currentLoc.col + dir.col };
        }
        if (Queen.isOnBoard(currentLoc)) {
            if (moveOn.pieceAt(currentLoc).isWhite != isWhite) {
                allMoves.push({ from: loc, to: currentLoc });
            }
        }
        return allMoves;
    };
    return Queen;
}(ChessPiece_1.ChessPiece));
exports.Queen = Queen;
