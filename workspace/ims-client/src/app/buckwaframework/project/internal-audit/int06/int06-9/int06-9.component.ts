import { Component, OnInit } from "@angular/core";
import { TextDateTH, formatter } from "helpers/datepicker";
import { AjaxService } from "services/ajax.service";
import { Router, ActivatedRoute } from "@angular/router";
import { MessageBarService } from "../../../../common/services";
import { Observable } from "rxjs";
import { ComboBox } from "models/combobox";
import { Int069Service } from "./int06-9.service";
import { BreadCrumb } from "models/breadcrumb";

declare var $: any;
@Component({
  selector: "app-int06-9",
  templateUrl: "./int06-9.component.html",
  styleUrls: ["./int06-9.component.css"],
  providers: [Int069Service]
})
export class Int069Component implements OnInit {
  breadcrumb: BreadCrumb[] = [];
  comboBox1: Observable<ComboBox[]>;
  comboBox2: Observable<ComboBox[]>;
  comboBox3: Observable<ComboBox[]>;
  combo1: any;
  combo2: any;
  combo3: any;
  endDate: any;
  startDate: any;

  constructor(
    private selfService: Int069Service,
    private ajax: AjaxService,
    private router: Router,
    private route: ActivatedRoute,
    private msg: MessageBarService
  ) {
    this.breadcrumb = [
      { label: "ตรวจสอบภายใน", route: "#" },
      { label: "ตรวจสอบเบิกจ่าย", route: "#" },
      { label: "ทะเบียนคุมการรับจ่ายเงิน", route: "#" },
      { label: "บันทึกข้อมูลรับโอนเงิน", route: "#" }
    ];
  }

  async ngOnInit() {
    this.comboBox1 = await this.selfService.dropdown("TRANSFER");
    this.comboBox2 = await this.selfService.dropdown("BUDGET_TYPE");
    this.comboBox3 = await this.selfService.dropdown("ACTIVITY");
    $(".ui.dropdown.ai")
      .dropdown()
      .css("width", "100%");

    this.hidedata();

    $("#calendar1").calendar({
      // maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter(),
      onChange: (a, b) => {
        this.startDate = b;
        console.log(this.startDate);
      }
    });
    $("#calendar2").calendar({
      // maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter(),
      onChange: (a, b) => {
        this.endDate = b;
        console.log(this.endDate);
      }
    });
  }
  onFilter = () => {
    $("#int069").show();
    console.log("combo1: ", this.combo1);
    console.log("combo2: ", this.combo2);
    console.log("combo3: ", this.combo3);
    let DATA = {
      transferList: this.combo1,
      budgetType: this.combo2,
      activities: this.combo3,
      start: this.startDate,
      end: this.endDate
    };
    this.selfService.filterDropdrown(DATA);
  };

  hidedata() {
    $("#int069").hide();
    $("#combo1").dropdown("restore defaults");
    $("#combo2").dropdown("restore defaults");
    $("#combo3").dropdown("restore defaults");
    this.startDate = "";
    this.endDate = "";
  }

  popupEditData() {
    $("#int0699").modal("show");
  }

  closePopupEdit() {
    $("#int0699").modal("hide");
  }
}
