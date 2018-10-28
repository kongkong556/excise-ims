import { Component, OnInit } from '@angular/core';
import { TextDateTH, DecimalFormat, digit } from '../../../../common/helper';
import { AjaxService, MessageBarService, AuthService } from '../../../../common/services';
import { BreadCrumb } from 'models/index';
import { formatter } from 'helpers/datepicker';

declare var $: any;
@Component({
  selector: 'cop06-3',
  templateUrl: './cop06-3.component.html',
  styleUrls: ['./cop06-3.component.css']
})
export class Cop063Component implements OnInit {

  breadcrumb: BreadCrumb[] = [
    { label: "ตรวจปฏิบัติการ", route: "#" },
    { label: 'รายงานการตรวจปฏิบัติการ', route: '#' },
    { label: 'รายงานการตรวจปฏิบัติการรับสินค้าสำเร็จรูป', route: '#' },
  ]
  obj: Data;
  exciseId: any;
  exciseIdArr: any;
  firstDataList: any;
  startDate: any;
  endDate: any;
  MonthDataList: any;
  fileExel: File[];
  row: any;
  diff: any;
  monthRecieveArr: any;
  showDt: any;
  startDateSplit: any;
  endDateSplit: any;
  dataTB: any;
  dataHeader: any;
  emptyColumn: any;
  allData: any;

  constructor(
    private ajax: AjaxService,
    private authService: AuthService,
    private messageBarService: MessageBarService
  ) {
    this.exciseIdArr = "";
    this.firstDataList = {
      companyName: "",
      productType: ""
    };
    this.obj = new Data();

    this.startDateSplit = "";
    this.endDateSplit = "";
    this.emptyColumn = "";
    this.row = [];
    this.fileExel = new Array<File>(); // initial file array
  }

  ngOnInit() {
    this.authService.reRenderVersionProgram('COP-06300');

    $(".ui.dropdown").dropdown();
    $(".ui.dropdown.cop06-3").css("width", "100%");

    $("#date").calendar({
      type: "year",
      text: TextDateTH,
      formatter: formatter("ป"),   
      onChange: (date, text) => {
        console.log("fiscalYear",text);
      }
    });

    $("#calendarFront").calendar({
      endCalendar: $("#calendarLast"),
      maxDate: new Date(),
      type: "month",
      text: TextDateTH,
      formatter: {
        header: function (date, mode, settings) {
          //return a string to show on the header for the given 'date' and 'mode'
          return date.getFullYear() + 543;
        },
        date: function (date, settings) {
          if (!date) {
            return "";
          }
          const month = date.getMonth();
          const year = date.getFullYear() + 543;
          return TextDateTH.months[month] + " " + year;
        }
      }
    });

    $("#calendarLast").calendar({
      startCalendar: $("#calendarFront"),
      maxDate: new Date(),
      type: "month",
      text: TextDateTH,
      formatter: {
        header: function (date, mode, settings) {
          //return a string to show on the header for the given 'date' and 'mode'
          return date.getFullYear() + 543;
        },
        date: function (date, settings) {
          if (!date) {
            return "";
          }
          const month = date.getMonth();
          const year = date.getFullYear() + 543;
          return TextDateTH.months[month] + " " + year;
        }
      }
    });

    // get exciseId in select option
    const URL = "combobox/controller/getExciseId";
    this.ajax.post(URL, {}, res => {
      this.exciseIdArr = res.json();
      this.exciseId = this.exciseIdArr[0];

      // get exciseId in input box
      const URL =
        AjaxService.CONTEXT_PATH + "/filter/exise/getDataExciseIdList";
      $.post(URL, { exciseId: this.exciseId }, res => {
        this.obj = res[0];
        this.firstDataList = res[0];
      });
    });
  }

  ngAfterViewInit(): void {
    $("#showData").hide();
  }

  ngOnDestroy(): void { }

  changeExiseId = () => {
    this.exciseId = (<HTMLInputElement>(
      document.getElementById("exciseId")
    )).value;
    const URL = AjaxService.CONTEXT_PATH + "/filter/exise/getDataExciseIdList";
    $.post(URL, { exciseId: this.exciseId }, res => {
      this.obj = res[0];
    });
  };

  clearAll = () => {
    $("#showData").hide();
    // this.showDt.fnClearTable();
    this.showDt.clear().draw();
  };

  onUpload = (event: any) => {
    // Prevent actual form submission
    event.preventDefault();

    this.dataTB = [];
    for (var i = 0; i < this.showDt.data().length; i++) {
      this.dataTB.push(this.showDt.data()[i]);
    }

    //send form data
    const form = $("#upload-form")[0];
    let formBody = new FormData(form);
    for (var i = 0; i < this.showDt.data().length; i++) {
      formBody.append("product" + (i + 1), this.dataTB[i].product);
      formBody.append("monthRecieve" + (i + 1), this.dataTB[i].monthRecieve);
    }

    let url = "/upload/excel";
    this.ajax.upload(
      url,
      formBody,
      res => {
        this.row = res.json();
        this.emptyColumn = this.row.data[0].columnName;
        this.allData = [];
        for (i = 0; i < this.row.data.length; i++) {
          this.allData.push(this.row.data[i]);
        }

        if (this.showDt != null && this.showDt != undefined) {
          this.showDt.destroy();
        }

     
        this.messageBarService.successModal("อัพโหลดข้อมูลสำเร็จ", "สำเร็จ");
      },
      err => {
        this.messageBarService.errorModal(
          "ไม่สามารถอัพโหลดข้อมูลได้",
          "เกิดข้อผิดพลาด"
        );
      }
    );
  };

  onChangeUpload = (event: any) => {
    if (event.target.files && event.target.files.length > 0) {
      let reader = new FileReader();

      reader.onload = (e: any) => {
        const f = {
          name: event.target.files[0].name,
          type: event.target.files[0].type,
          value: e.target.result
        };
        this.fileExel = [f];
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  };

  onClickShow = e => {
    $("#showData").show();
    // Prevent actual form submission
    e.preventDefault();
    this.startDate = e.target["startDate"].value;
    this.endDate = e.target["endDate"].value;

    //set for [(NgModel)]
    this.obj.startDate = this.startDate;
    this.obj.endDate = this.endDate;

    //change formatter first date input value
    const date_str1 = this.startDate.split(" ");
    date_str1[0] = digit(TextDateTH.months.indexOf(date_str1[0]) + 1);
    this.startDateSplit = date_str1[0] + "/" + date_str1[1];

    //change formatter end date input value
    const date_str2 = this.endDate.split(" ");
    date_str2[0] = digit(TextDateTH.months.indexOf(date_str2[0]) + 1);
    this.endDateSplit = date_str2[0] + "/" + date_str2[1];

    if (this.showDt != null && this.showDt != undefined) {
      this.showDt.destroy();
    }
    this.initDatatable();
  };

  initDatatable(): void {
    const URL = AjaxService.CONTEXT_PATH + "/upload/excel";
    this.showDt = $("#showDt").DataTableTh({
      lengthChange: false,
          searching: false,
          ordering: false,
          pageLength: 10,
          processing: true,
          serverSide: false,
          scrollX: true,
          paging: false,
    });
  }

  saveTable = () => {
    this.dataTB = [];
    this.dataTB.push({
      exciseId: this.exciseId,
      analysNumber: this.obj.analysNumber,
      startDate: this.startDateSplit,
      endDate: this.endDateSplit
    });

    for (var i = 0; i < this.showDt.data().length; i++) {
      this.dataTB.push(this.showDt.data()[i]);
    }

    // this.dataHeader = [];
    // this.dataHeader.push({
    //   exciseId: this.exciseId,
    //   analysNumber: this.obj.analysNumber,
    //   startDate: this.startDateSplit,
    //   endDate: this.endDateSplit
    // });

    console.log(this.dataTB);

    const URL = "/ope/SaveTable";
    this.ajax.post(
      URL,
      JSON.stringify(this.dataTB),
      res => {
        this.messageBarService.successModal("บันทึกข้อมูลสำเร็จ", "สำเร็จ");
        // this.ajax.post(
        //   URL,
        //   JSON.stringify(this.dataHeader),
        //   res => {
        //     this.messageBarService.successModal("บันทึกข้อมูลสำเร็จ", "สำเร็จ");
        //   },
        //   err => {
        //     this.messageBarService.errorModal(
        //       "ไม่สามารถบันทึกข้อมูลได้",
        //       "เกิดข้อผิดพลาด"
        //     );
        //   }
        // );
      },
      err => {
        this.messageBarService.errorModal(
          "ไม่สามารถบันทึกข้อมูลได้",
          "เกิดข้อผิดพลาด"
        );
      }
    );
  };

  DF(what) {
    const df = new DecimalFormat("###,###");
    return df.format(what);
  }

  toFixed(x) {
    if (Math.abs(x) < 1.0) {
      var e = parseInt(x.toString().split("e-")[1]);
      if (e) {
        x *= Math.pow(10, e - 1);
        x = "0." + new Array(e).join("0") + x.toString().substring(2);
      }
    } else {
      var e = parseInt(x.toString().split("+")[1]);
      if (e > 20) {
        e -= 20;
        x /= Math.pow(10, e);
        x += new Array(e + 1).join("0");
      }
    }
    return x;
  }
}

class File {
  [x: string]: any;
  name: string;
  type: string;
  value: any;
}

class Data {
  companyName: any = "";
  startDate: any = "";
  endDate: any = "";
  analysNumber: any = "";
  startDateSplit: any = "";
  endDateSplit: any = "";
}
