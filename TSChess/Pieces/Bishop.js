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
exports.Bishop = void 0;
var ChessPiece_1 = require("./ChessPiece");
var Piece_1 = require("./Piece");
var Bishop = /** @class */ (function (_super) {
    __extends(Bishop, _super);
    function Bishop(isWhite) {
        var _this = _super.call(this, Piece_1.PieceType.Bishop, isWhite) || this;
        _this.generateMoves = function (moveOn, loc) {
            var bishopMoves = [];
            //we'll generate 4 "position" objects that represent different directions the bishop might move
            //then try those one at a time and add the results
            var bishopDirections = [];
            bishopDirections.push({ row: 1, col: 1 });
            bishopDirections.push({ row: 1, col: -1 });
            bishopDirections.push({ row: -1, col: 1 });
            bishopDirections.push({ row: -1, col: -1 });
            for (var _i = 0, bishopDirections_1 = bishopDirections; _i < bishopDirections_1.length; _i++) {
                var direction = bishopDirections_1[_i];
                var directionMoves = Bishop.slidePiece(moveOn, loc, direction, _this.isWhite);
                bishopMoves.push.apply(bishopMoves, directionMoves);
            }
            return bishopMoves;
        };
        return _this;
    }
    Bishop.isOnBoard = function (loc) {
        return loc.col >= 0 && loc.col < 8 && loc.row >= 0 && loc.row < 8;
    };
    Bishop.slidePiece = function (moveOn, loc, dir, isWhite) {
        var allMoves = [];
        var currentLoc = { row: loc.row + dir.row, col: loc.col + dir.col };
        while (Bishop.isOnBoard(currentLoc) && moveOn.pieceAt(currentLoc) === null) {
            allMoves.push({ from: loc, to: currentLoc });
            currentLoc = { row: currentLoc.row + dir.row, col: currentLoc.col + dir.col };
        }
        if (Bishop.isOnBoard(currentLoc)) {
            if (moveOn.pieceAt(currentLoc).isWhite != isWhite) {
                allMoves.push({ from: loc, to: currentLoc });
            }
        }
        return allMoves;
    };
    return Bishop;
}(ChessPiece_1.ChessPiece));
exports.Bishop = Bishop;
