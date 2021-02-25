import { Component, Input, OnInit } from '@angular/core';
import { Bishop } from '../chess/Pieces/Bishop';
import { Piece, PieceType } from '../chess/Pieces/Piece';

@Component({
  selector: 'app-chess-square',
  templateUrl: './chess-square.component.html',
  styleUrls: ['./chess-square.component.css']
})
export class ChessSquareComponent implements OnInit {

  @Input()squarePiece : Piece;
  imageSrc : string = "./assets/";
  @Input()row : number = 0;
  @Input()col : number = 7;
  isLight : boolean;

  constructor() {
    
  }

  ngOnInit(): void {
    if (this.squarePiece === null) {
      this.imageSrc = "";
    }
    else {
      this.imageSrc += (this.squarePiece.isWhite ? "w" : "b");
      switch(this.squarePiece.kind) {
        case PieceType.Pawn: this.imageSrc += "P"; break;
        case PieceType.Rook: this.imageSrc += "R"; break;
        case PieceType.Knight: this.imageSrc += "N"; break;
        case PieceType.Bishop: this.imageSrc += "B"; break;
        case PieceType.Queen: this.imageSrc += "Q"; break;
        case PieceType.King: this.imageSrc += "K"; break;
      }
      this.imageSrc += ".png";
    }
    this.isLight = (this.row + this.col) % 2 === 0;
  }

}
