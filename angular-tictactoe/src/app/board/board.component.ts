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
  xWins : number = 0;
  oWins : number = 0;
  draws : number = 0;

  constructor() { }

  ngOnInit(): void {
  }

  onSquareClicked(pos : Position) : void {
    if(this.game.makeMove(pos)) {
      switch(this.game.gameStatus) {
        case 1:
          this.status = "X wins!";
          this.xWins++;
          break;
        case 0:
          this.status = "Draw!";
          this.draws++;
          break;
        case -1:
          this.status = "O wins!";
          this.oWins++;
          break;
        default:
          this.status = "Ongoing...";
      }
    }
  }

  resetGame() : void {
    this.game = new TicTacToeGame();
    this.status = "New Game";
  }

}
