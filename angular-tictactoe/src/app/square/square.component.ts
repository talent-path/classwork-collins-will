import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import {Position} from '../game/Position';

@Component({
  selector: 'app-square',
  templateUrl: './square.component.html',
  styleUrls: ['./square.component.css']
})
export class SquareComponent implements OnInit {

  @Input()piece : number = 0;
  imageSrc : string = "./assets/";
  @Input()row : number = -1;
  @Input()col : number = -1;
  @Output()squareClickedEvent : EventEmitter<Position> = new EventEmitter<Position>();

  constructor() { }

  ngOnInit(): void {
    if (this.piece === -1) {
      this.imageSrc += "O.png";
    } else if (this.piece === 1) {
      this.imageSrc += "X.png";
    } else {
      this.imageSrc += "empty.png";
    }
  }

  squareClicked() : void {
    this.squareClickedEvent.emit({row: this.row, col: this.col});
  }

}
