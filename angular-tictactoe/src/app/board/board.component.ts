import { Component, OnInit } from '@angular/core';
import { Game, TicTacToeGame } from '../game/game';
import { Position } from '../game/Position';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  game : Game = new TicTacToeGame();
  status : string = "New Game";

  constructor() { }

  ngOnInit(): void {
  }

  onSquareClicked(pos : Position) : void {
    this.game.makeMove(pos);
    switch(this.game.gameStatus) {
      case 1:
        this.status = "X wins!";
        break;
      case 0:
        this.status = "Draw!";
        break;
      case -1:
        this.status = "O wins!";
        break;
      default:
        this.status = "Ongoing...";
    }
  }

}
