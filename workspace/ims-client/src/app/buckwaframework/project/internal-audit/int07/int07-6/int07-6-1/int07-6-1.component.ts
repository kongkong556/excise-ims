import { Component, OnInit, AfterViewInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { AjaxService } from '../../../../../common/services';
import { MessageBarService } from 'app/buckwaframework/common/services';
import { BreadCrumb } from '../../../../../common/models';

@Component({
  selector: 'app-int07-6-1',
  templateUrl: './int07-6-1.component.html',
  styleUrls: ['./int07-6-1.component.css']
})
export class Int0761Component implements OnInit {
  breadcrumb: BreadCrumb[];
  constructor(
    private router: Router,
    private ajax: AjaxService,
    private message: MessageBarService
  ) { 
    this.breadcrumb = [
      { label: "ตรวจสอบภายใน", route: "#" },
      { label: "ตรวจสอบบัญชี", route: "#" },
      { label: "ตรวจสอบการนำส่งเงินบัญชีเจ้าหนี้ อปท.", route: "#" },
      { label: "ตรวจสอบข้อมูล", route: "#" }
    ];
  }

  ngOnInit() {
  }

  backPage() {
    console.log("กลับไปยังหน้าอัพโหลดดาต้า");
    this.router.navigate(["/int07/6"], {
      
    });
  }

}
