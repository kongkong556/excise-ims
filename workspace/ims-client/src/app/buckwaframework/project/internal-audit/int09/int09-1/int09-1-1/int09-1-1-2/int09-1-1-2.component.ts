import { Component, OnInit, AfterViewInit } from "@angular/core";
import {
  TravelCostHeader,
  TravelCostDetail,
  Contract
} from "../../../../../../common/models";
import {
  AjaxService,
  MessageBarService,
  AuthService
} from "../../../../../../common/services";
import { Prices } from "../../../../../../common/helper/travel";
import { Router, ActivatedRoute } from "@angular/router";
import { TravelService } from "../../../../../../common/services/travel.service";
import { BreadCrumb } from "models/index";
import { Int09112Service } from "./Int09-1-1-2.service";
import { FormGroup, Validators, FormBuilder } from "@angular/forms";
import { TextDateTH, formatter } from "helpers/datepicker";
import { Utils } from "helpers/utils";

declare var $: any;
@Component({
  selector: "app-int09-1-1-2",
  templateUrl: "./int09-1-1-2.component.html",
  styleUrls: ["./int09-1-1-2.component.css"],
  providers: [Int09112Service]
})
export class Int09112Component implements OnInit, AfterViewInit {
  loanAgreementForm: FormGroup;

  breadcrumb: BreadCrumb[];
  idProcess: any;
  submitted: boolean = false;

  constructor(
    private selfService: Int09112Service,
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private route: ActivatedRoute,
    private travelService: TravelService,
    private msg: MessageBarService
  ) {
    this.breadcrumb = [
      { label: "ตรวจสอบภายใน", route: "#" },
      { label: "ประมาณการค่าใช้จ่ายในการเดินทางไปราชการ", route: "#" },
      { label: "รายละเอียดเอกสาร", route: "#" },
      { label: "สร้างเอกสารสัญญาการยืมเงิน", route: "#" }
    ];
  }

  ngAfterViewInit() {}

  ngOnInit() {
    this.authService.reRenderVersionProgram("INT-09112");
    //set variable and validate
    this.loanAgreementForm = this.formBuilder.group({
      order: ["", Validators.required],
      filing: ["", Validators.required],
      deadLinesStr: ["", Validators.required],
      ourself: ["", Validators.required],
      position: ["", Validators.required],
      affiliation: ["", Validators.required],
      borrowFrom: ["", Validators.required],
      forCost: ["", Validators.required],
      startDateStr: ["", Validators.required],
      endDateStr: ["", Validators.required],
      allowance: [0, Validators.required],
      accommodationCost: [0, Validators.required],
      travelCost: [0, Validators.required],
      otherCost: [0, Validators.required],
      totalCostStr: [{ value: "", disabled: true }, Validators.required],
      totalCost: [{ value: "", disabled: true }, Validators.required],
      deserveLend: ["", Validators.required],
      deserveLendStr: [{ value: "", disabled: true }, Validators.required],
      approvedLend: ["", Validators.required],
      approvedLendStr: [{ value: "", disabled: true }, Validators.required],
      loanAmount: ["", Validators.required],
      loanAmountStr: [{ value: "", disabled: true }, Validators.required],
      idProcess: [""]
    });

    this.idProcess = this.route.snapshot.queryParams["idProcess"];
    console.log("idProcess : ", this.idProcess);
    this.calendar();
  }

  //form controls
  get f() {
    return this.loanAgreementForm.controls;
  }

  calendar = function() {
    $("#deadLinesCld").calendar({
      minDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter("ว/ด/ป"),
      onChange: (date, ddmmyyyy) => {
        this.loanAgreementForm.patchValue({ deadLinesStr: ddmmyyyy });
      }
    });

    $("#startDateCld").calendar({
      minDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter("ว/ด/ป"),
      onChange: (date, ddmmyyyy) => {
        this.loanAgreementForm.patchValue({ startDateStr: ddmmyyyy });
      }
    });

    $("#endDateCld").calendar({
      minDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter("ว/ด/ป"),
      onChange: (date, ddmmyyyy) => {
        this.loanAgreementForm.patchValue({ endDateStr: ddmmyyyy });
      }
    });
  };

  numberToThaiText(name: string) {
    let sum = 0;
    const myObj = this.getObj();

    if ("sumOtherCost" === name) {
      let arrKeySum = [
        "allowance",
        "accommodationCost",
        "travelCost",
        "otherCost"
      ];
      arrKeySum.forEach((elem, idx) => {
        if (!isNaN(myObj[elem])) sum += +myObj[elem];
      });

      this.loanAgreementForm.patchValue({
        totalCost: Utils.moneyFormatDecimal(sum),
        totalCostStr: Utils.thaiBaht(Utils.moneyFormatDecimal(sum))
      });
    } else {
      let arrKeySum = [name];
      arrKeySum.forEach((elem, idx) => {
        if (!isNaN(myObj[elem])) sum += +myObj[elem];
      });

      if ("deserveLend" === name) {
        this.loanAgreementForm.patchValue({
          deserveLendStr: Utils.thaiBaht(Utils.moneyFormatDecimal(sum))
        });
      }

      if ("approvedLend" === name) {
        this.loanAgreementForm.patchValue({
          approvedLendStr: Utils.thaiBaht(Utils.moneyFormatDecimal(sum))
        });
      }

      if ("loanAmount" === name) {
        this.loanAgreementForm.patchValue({
          loanAmountStr: Utils.thaiBaht(Utils.moneyFormatDecimal(sum))
        });
      }
    }
  }

  typeNumber(e) {
    return Utils.onlyNumber(e);
  }

  getObj() {
    return this.loanAgreementForm.value;
  }

  clickBack() {
    this.selfService.clickBack(this.idProcess);
  }

  save() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.loanAgreementForm.invalid) {
      this.msg.errorModal("กรุณากรอกข้อมูลให้ครบ");
      return;
    }
    //enable
    this.loanAgreementForm.enable();
    //set idProcess
    this.loanAgreementForm.patchValue({
      idProcess: this.idProcess
    });
    console.log("SAVE!!");
    console.log(this.loanAgreementForm.value);
    this.selfService.save(this.loanAgreementForm.value);
  }
}
