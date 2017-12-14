import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ope05-6',
  templateUrl: './ope05-6.component.html',
  styleUrls: ['./ope05-6.component.css']
})
export class Ope056Component implements OnInit {

  public showData: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  uploadData() {
    this.showData = true;
  }

  clearData() {
    this.showData = false;
  }

}
