import { Component, OnInit } from '@angular/core';
import {Board, ChessBoard} from '../chess/Board'
import { Move } from '../chess/Move';
import { Piece } from '../chess/Pieces/Piece';
import { Position } from '../chess/Position';

@Component({
  selector: 'app-chess-board',
  templateUrl: './chess-board.component.html',
  styleUrls: ['./chess-board.component.css']
})
export class ChessBoardComponent implements OnInit {

  firstSquareSelected : Position = null;
  secondSquareSelected: Position = null;
  board : Board = new ChessBoard();

  constructor() { 
    console.log(this.board.allSquares);
  }

  ngOnInit(): void {}

  onSquareClicked(pos : Position) : void {
    let pieceAtPos : Piece = this.board.pieceAt(pos);

    if (this.firstSquareSelected === null) {
      if (pieceAtPos != null) {
        if (pieceAtPos.isWhite === this.board.isWhiteTurn) {
          this.firstSquareSelected = pos;
        }
      }
      console.log(this.firstSquareSelected);
    } else {
      let move : Move = this.board.generateMoves().find(
        (currMove : Move) => {
          console.log(currMove);
          return currMove.from.row === this.firstSquareSelected.row && currMove.to.col === this.firstSquareSelected.col
          && currMove.to.row === pos.row && currMove.to.col === pos.col;
        });
      if (move != undefined) {
        this.board = this.board.makeMove(move);
        this.firstSquareSelected = null;
      }
    }
  }

}
