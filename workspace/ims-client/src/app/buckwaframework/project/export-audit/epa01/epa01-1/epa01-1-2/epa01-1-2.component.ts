import { Component, OnInit } from '@angular/core';
import { AuthService } from 'services/auth.service';

@Component({
  selector: 'app-epa01-1-2',
  templateUrl: './epa01-1-2.component.html',
  styleUrls: ['./epa01-1-2.component.css']
})
export class Epa0112Component implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.reRenderVersionProgram('EXP-01120');
  }

}
