import { Component, OnInit } from '@angular/core';
import { AuthService } from 'services/auth.service';

@Component({
  selector: 'app-cop05-1',
  templateUrl: './cop05-1.component.html',
  styleUrls: ['./cop05-1.component.css']
})
export class Cop051Component implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.reRenderVersionProgram('OPE-05010');
  }

}
