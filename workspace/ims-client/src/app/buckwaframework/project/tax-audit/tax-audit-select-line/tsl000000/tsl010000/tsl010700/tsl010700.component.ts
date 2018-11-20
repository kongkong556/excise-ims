import { Component, OnInit } from '@angular/core';
import { BreadCrumb } from 'models/breadcrumb';
import { AjaxService } from 'services/ajax.service';
import { Router, ActivatedRoute } from '@angular/router';
import { IaService } from 'services/ia.service';
import { AuthService } from 'services/auth.service';
import { MessageBarService } from 'services/index';
import * as moment from 'moment';
import { TextDateTH } from 'helpers/index';
declare var $: any;

@Component({
  selector: 'app-tsl010700',
  templateUrl: './tsl010700.component.html',
  styleUrls: ['./tsl010700.component.css']
})
export class Tsl010700Component implements OnInit {

  breadcrumb: BreadCrumb[] = [
    { label: 'ตรวจสอบภาษี', route: '#' },
    { label: 'การคัดเลือกราย', route: '#' },
    { label: 'ตารางผลการคัดเลือกรายประจำปี', route: '#' },
    { label: 'ตรวจสอบข้อมูลด้านภาษีสรรพสามิต', route: '#' },
  ];

  searchFlag: string = "FALSE";
  entrepreneurTable: any;
  getRawTable: any;
  payRawTable: any;
  dataRecord: any;
  obj: dataTax;
  dateCalendar: string = "";
  id: any;

  constructor(
    private dataService: IaService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private message: MessageBarService,
    private ajax: AjaxService,
  ) {
    this.dataRecord = this.dataService.getData();
    //console.log("dataRecord", this.dataRecord);
    this.dateCalendar = this.route.snapshot.queryParams['dateCalendar'];
    this.searchFlag = this.route.snapshot.queryParams['searchFlag'];
    this.obj = new dataTax();
  }

  ngOnInit() {
    this.authService.reRenderVersionProgram('tsl010700').then(user => {
      this.obj.officer = user.fullName;
    });
    
  }



  onReport() {
    this.obj.exciseArea =   $("#exciseArea").val();
    this.obj.exciseSubArea =   $("#exciseSubArea").val();
    this.obj.exciseId =   $("#exciseId").val();
    this.obj.companyName =   $("#companyName").val();
    this.obj.product =   $("#product").val();
    this.obj.riskTypeDesc =   $("#riskTypeDesc").val();
    this.obj.dateCalendar =   $("#dateCalendar").val();
    this.obj.companyAddress =   $("#companyAddress").val();

    // this.obj.resultGetRaw =   $("#resultGetRaw").val();
    // this.obj.resultPayRaw =   $("#resultPayRaw").val();
    // this.obj.receiptInvoiceRaw =   $("#receiptInvoiceRaw").val();
    // this.obj.payInvoiceRaw =   $("#payInvoiceRaw").val();

    //console.log(this.obj);
     
    const URL = "exciseTax/report/updateFlag";
    this.message.comfirm(confirm => {
      if (confirm) {
        const URL = "exciseTax/report/updateFlag";
        this.ajax.post(URL, parseInt(this.dataRecord.taYearPlanId) || 0, response => {
          this.exportPdf();
          this.message.successModal(response.json().messageTh);
          this.router.navigate(["/tax-audit-select-line/tsl0106-00"]);
        }).catch(err => {
          this.message.errorModal("ไม่สามารถทำการบันทึกได้");
          console.error(err);
        });
      }
    }, "ยืนยันการบันทึกข้อมูล");

  }

  exportPdf() {
    var form = document.createElement("form");
    form.method = "POST";
    //form.action = AjaxService.CONTEXT_PATH + "exciseOperation/report/pdf/operation/checkExciseOperation";
    form.action = AjaxService.CONTEXT_PATH + "exciseTax/report/pdf/tax/checkExciseTax";
    form.style.display = "none";
    form.target = "_blank"    
    var jsonInput = document.createElement("input");
    jsonInput.name = "json";
    jsonInput.value = JSON.stringify(this.obj);
    form.appendChild(jsonInput);
    document.body.appendChild(form);
    form.submit();
  }

  ngAfterViewInit() {
    this.initGetRawDatatable();
    this.initPayRawDatatable();
    this.initGetProductDatatable();
    this.initPayProductDatatable();
  }

  initGetRawDatatable = () => {
    const URL = AjaxService.CONTEXT_PATH + "taxAudit/checkDisplay/search";
    this.getRawTable = $("#tableGetRawMaterial").DataTableTh({
      serverSide: true,
      searching: false,
      processing: true,
      ordering: false,
      scrollX: true,
      ajax: {
        type: "POST",
        url: URL,
        contentType: "application/json",
        data: (d) => {
          return JSON.stringify($.extend({}, d, {
            "exciseId": this.dataRecord.exciseId,
            "dateCalendar": this.dateCalendar,
            "searchFlag": this.searchFlag
          }));
        }
      },
      columns: [
        {
          className: "ui center aligned",
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          }
        }, {
          data: "list",
          className: "ui left aligned"
        }, {
          data: "unit",
          className: "ui left aligned"
        }, {
          data: "stock",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "receive",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "receiveTotal",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }
      ],
    });
  };

  initPayRawDatatable = () => {
    const URL = AjaxService.CONTEXT_PATH + "taxAudit/checkDisplay/search";
    this.payRawTable = $("#tablePayRawMaterial").DataTableTh({
      serverSide: true,
      searching: false,
      processing: true,
      ordering: false,
      scrollX: true,
      ajax: {
        type: "POST",
        url: URL,
        contentType: "application/json",
        data: (d) => {
          return JSON.stringify($.extend({}, d, {
            "exciseId": this.dataRecord.exciseId,
            "dateCalendar": this.dateCalendar,
            "searchFlag": this.searchFlag
          }));
        }
      },
      columns: [
        {
          className: "ui center aligned",
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          }
        }, {
          data: "list",
          className: "ui left aligned"
        }, {
          data: "unit",
          className: "ui left aligned"
        }, {
          data: "productInline",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "productOutline",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "corrupt",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "other",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }
      ],
    });
  };


  initGetProductDatatable = () => {
    const URL = AjaxService.CONTEXT_PATH + "cop/cop092/product/list";
    this.getRawTable = $("#tableReceiveProduct").DataTableTh({
      serverSide: true,
      searching: false,
      processing: true,
      ordering: false,
      scrollX: true,
      ajax: {
        type: "POST",
        url: URL,
        contentType: "application/json",
        data: (d) => {
          return JSON.stringify($.extend({}, d, {
            "excise": this.dataRecord.exciseId,
            "monthBuget": this.dateCalendar,
            "searchFlag": this.searchFlag
          }));
        }
      },
      columns: [
        {
          className: "ui center aligned",
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          }
        }, {
          data: "list",
          className: "ui left aligned"
        }, {
          data: "unit",
          className: "ui left aligned"
        }, {
          data: "stock",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        },
        {
          data: "getPro",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        },
        {
          data: "receive",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        },
        {
          data: "other",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "receiveTotal",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }
      ],
    });
  };

  initPayProductDatatable = () => {
    const URL = AjaxService.CONTEXT_PATH + "cop/cop092/product/list";
    this.payRawTable = $("#tablePayProduct").DataTableTh({
      serverSide: true,
      searching: false,
      processing: true,
      ordering: false,
      scrollX: true,
      ajax: {
        type: "POST",
        url: URL,
        contentType: "application/json",
        data: (d) => {
          return JSON.stringify($.extend({}, d, {
            "excise": this.dataRecord.exciseId,
            "monthBuget": this.dateCalendar,
            "searchFlag": this.searchFlag
          }));
        }
      },
      columns: [
        {
          className: "ui center aligned",
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          }
        }, {
          data: "list",
          className: "ui left aligned"
        }, {
          data: "unit",
          className: "ui left aligned"
        }, {
          data: "domDist",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "foreignSale",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "inHouseUse",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "warehouse",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "corrupt",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "other1",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }, {
          data: "total",
          className: "ui right aligned",
          render: function (data, type, row) {
            if ($.trim(data) == "") {
              return "-";
            }
            return data;
          }
        }
      ],
    });
  };

  onClickBack() {
    this.router.navigate(["/tax-audit-select-line/tsl0106-00"]);
  }







}

class dataTax {
  exciseArea: string;
  exciseSubArea: string;
  exciseId: string;
  companyName: string;
  product: string;
  riskTypeDesc: string;
  dateCalendar: string;
  companyAddress: string;
  //user login
  officer: string;
  
  //radio1
  resultGetRaw:string;
  resultGetRawBox:string;
  //radio2
  resultPayRaw:string;
  resultPayRawBox:string;
  //radio3
  receiptInvoiceBox:string;
  receiptInvoiceRaw:string;
  //radio4
  payInvoiceBox:string;
  payInvoiceRaw:string;

  

} 