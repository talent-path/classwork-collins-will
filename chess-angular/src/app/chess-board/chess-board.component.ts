import { Component, OnInit } from '@angular/core';
import {Board, ChessBoard} from '../chess/Board'

@Component({
  selector: 'app-chess-board',
  templateUrl: './chess-board.component.html',
  styleUrls: ['./chess-board.component.css']
})
export class ChessBoardComponent implements OnInit {

  board : Board = new ChessBoard();

  constructor() { 
    console.log(this.board.allSquares);
  }

  ngOnInit(): void {}

}
