import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AjaxService } from "../../../../../common/services";

@Component({
  selector: 'app-ts01-02',
  templateUrl: './ts01-02.component.html',
  styleUrls: ['./ts01-02.component.css']
})
export class Ts0102Component implements OnInit {

  @Output() discard = new EventEmitter<any>();

  add: number;

  obj: Ts0102;

  constructor(
    private ajax: AjaxService
  ) {
    this.add = 0;
    this.obj = new Ts0102();
  }

  ngOnInit() {
  }

  onDiscard = () => {
    // on click this view hide them
    // ... Don't change or delete this
    this.discard.emit(false);
  };

  onAddField = () => {
    this.add++;
  };
  onSubmit = e => {
    e.preventDefault();
    const url = "report/pdf/ts/mis_t_s_01_02";
    if (this.obj.agreeBox == "factory") {
      this.obj.factory = true;
      this.obj.service = false;
      this.obj.company = false;
    } else if (this.obj.agreeBox == "service") {
      this.obj.factory = false;
      this.obj.service = true;
      this.obj.company = false;
    } else if (this.obj.agreeBox == "company") {
      this.obj.factory = false;
      this.obj.service = false;
      this.obj.company = true;
    }
    this.ajax.post(url, `'${JSON.stringify(this.obj).toString()}'`, res => {
      if (res.status == 200 && res.statusText == "OK") {
        window.open("/ims-webapp/api/report/pdf/mis_t_s_01_02/file");
      }
    });
  };

}

class Ts0102 {
  [x: string]: any;
  sendTo: string;
  department: string;
  item: string;
  agreeBox: string;
  factory: boolean;
  service: boolean;
  company: boolean;
  name: string;
  exciseId: string;
  address: string;
  workType: string;
  date: string;
  case: string;
  signature: string;
  position:string;

}