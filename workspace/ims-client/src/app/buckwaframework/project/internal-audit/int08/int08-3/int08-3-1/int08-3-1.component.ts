import { Component, OnInit, AfterViewInit } from "@angular/core";
import { Location } from "@angular/common";
import { AjaxService } from "../../../../../common/services/ajax.service";
import { MessageBarService } from "../../../../../common/services/message-bar.service";
import { Router, ActivatedRoute, Params } from "@angular/router";

import { TextDateTH, formatter } from '../../../../../common/helper/datepicker';
declare var $: any;
@Component({
  selector: "int08-3-1",
  templateUrl: "./int08-3-1.component.html",
  styleUrls: ["./int08-3-1.component.css"]
})
export class Int0831Component implements OnInit {
  showData: boolean = false;
  data: String[];
  budgetYear: any;
  riskList: any[];
  datatable: any;
  isSearch: any = false;
  riskYear: any;
  wsRiskList: any[];
  selectRiskSearch: any;
  dataTableF1: any;
  dataTable_int08_3_6: any;
  dataTable_int08_3_7: any;
  dataTable_int08_3_8: any;
  dataTable_int08_3_9: any;
  dataTable_int08_3_10: any;
  riskAssRiskWsHdr: any;
  condition: any;
  riskHrdId: any;
  dataTableF2: any;
  pageList: any;
  pageShowPageIndex: any;
  dataTableList: RiskData[] = [];
  constructor(private router: Router,
    private ajax: AjaxService,
    private route: ActivatedRoute,
    private messageBarService: MessageBarService,
    private _location: Location) {
    this.data = [
      "ประเมินความเสี่ยงโครงการ - งบประมาณ",
      "ประเมินความเสี่ยงโครงการ - ประสิทธิภาพ",
      "ประเมินความเสี่ยงโครงการ - รวม",
      "ประเมินความเสี่ยงสารสนเทศ - จำนวนครั้งใช้งานไม่ได้",
      "ประเมินความเสี่ยงสารสนเทศ - ร้อยละความเห็น",
      "ประเมินความเสี่ยงภาคพื้นที่ - ความถี่การตรวจ",
      "ประเมินความเสี่ยงภาคพื้นที่ - ผลการจัดเก็บรายได้",
      "ประเมินความเสี่ยงภาคพื้นที่ - ผลการปราบปราม (ค่าปรับ)",
      "ประเมินความเสี่ยงภาคพื้นที่ - ผลการปราบปราม (คดี)",
      "ประเมินความเสี่ยงภาคพื้นที่ - ผลการปราบปราม (รวม)",
      "ประเมินความเสี่ยงภาคพื้นที่ - การเงินและบัญชี",
      "ประเมินความเสี่ยงภาคพื้นที่ - ควบคุมภายใน",
      "ประเมินความเสี่ยงภาคพื้นที่ - รวม"
    ];
  }

  ngOnInit() {
    $(".ui.dropdown").dropdown();
    $(".ui.dropdown.ai").css("width", "100%");
    this.budgetYear = this.route.snapshot.queryParams["budgetYear"];
    if (this.budgetYear == null || this.budgetYear == undefined) {
      this.budgetYear == 'xxxx';
    } else {
      this.initDatatable();
      this.changeYear(this.budgetYear);
    }
    $('#year').calendar({
      type: 'year',
      text: TextDateTH,
      formatter: formatter('ป'),
      onChange: (date) => {
        //console.log(date.getFullYear())
        this.changeYear(date.getFullYear() + 543);
      }
    });
    this.wsRiskList = ["ปัจจัยเสี่ยงความถี่การเข้าตรวจสอบ", "ปัจจัยเสี่ยงผลการจัดเก็บรายได้", "ปัจจัยเสี่ยงผลการปราบปรามด้านค่าปรับคดี", "ปัจจัยเสี่ยงผลการปราบปรามด้านจำนวนคดี", "ปัจจัยเสี่ยงการเงินและบัญชี", "ปัจจัยเสี่ยงระบบการควบคุมภายใน", "ปัจจัยเสี่ยงการส่งเงินเกิน 3 วัน", "ปัจจัยเสี่ยงแบบสอบทานระบบการควบคุมภายใน"];
    this.pageList = ["int08-3-3", "int08-3-6", "int08-3-7", "int08-3-8", "int08-3-9", "int08-3-9", "int08-3-9", "int08-3-10"];

  }


  uploadData() {
    //this.showData = true;

  }

  changeYear(year) {
    this.budgetYear = year;
    //console.log(this.budgetYear);
    const URL = "combobox/controller/findByBudgetYear";
    this.ajax.post(URL, { budgetYear: this.budgetYear }, res => {

      this.riskList = res.json();
      //console.log(this.riskList);


    });
  }


  createBudgetYear() {
    this.budgetYear = $('#budgetYear').val().trim();
    if (this.budgetYear != null && this.budgetYear != undefined && this.budgetYear != '') {
      //console.log(this.budgetYear);
      const URL = "ia/int083/createBudgetYear";

      this.ajax.post(URL, { budgetYear: this.budgetYear }, res => {
        // var message = res.json();
        // this.messageBarService.successModal(message.messageTh, "สำเร็จ");

        this.router.navigate(["/int08/3/2"], {
          queryParams: { budgetYear: this.budgetYear }
        });
      }, errRes => {
        var message = errRes.json();
        this.messageBarService.errorModal(message.messageTh);

      });
    } else {
      this.messageBarService.errorModal('กรุณาเลือก ปีงบประมาณ');
    }
  }

  searchDataTable() {
    //console.log(this.selectRiskSearch);
    this.budgetYear = $('#budgetYear').val().trim();
    if (this.budgetYear != null && this.budgetYear != undefined && this.budgetYear != '') {
      this.isSearch = true;
      this.initDatatable();
    } else {
      this.messageBarService.errorModal('กรุณาเลือก ปีงบประมาณ');
    }
  }


  initDatatable(): void {
    //console.log(55);
    if (this.datatable != null) {
      this.datatable.destroy();
    }
    const URL = AjaxService.CONTEXT_PATH + "ia/int083/searchRiskAssExcAreaHdr";
    //console.log(URL);
    this.datatable = $("#dataTable").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: true,
      paging: false,
      ajax: {
        type: "POST",
        url: URL,
        data: { budgetYear: this.budgetYear, active: 'Y', riskHdrName: this.selectRiskSearch }
      },
      columns: [

        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "riskHdrName" },
        { data: "riskHrdPaperName" },
        { data: "budgetYear" },

        {
          render: function (data, type, row, meta) {
            //console.log("data :", row.createdDate)
            if (row.createdDate != null && row.createdDate != undefined && row.createdDate != '') {
              var dateTime = new Date(row.createdDate).toLocaleString("th-TH");
              return dateTime.split(' ')[0];
            } else {
              return row.createdDate;
            }
          },
          className: "center"
        },
        { data: "createdBy" },


        {
          data: "riskHdrId",
          render: function () {
            return '<button type="button" class="ui mini button primary dtl" ><i class="table icon"></i> รายละเอียด</button>'
              + '<button type="button" class="ui mini button primary export"><i class="print icon"></i> Export</button>';
          }
        }
      ],
      columnDefs: [
        { targets: [0, 3, 4, 5, 6], className: "center aligned" }
      ],
      rowCallback: (row, data, index) => {
        $("td > .dtl", row).bind("click", () => {
          var pageIndex = this.wsRiskList.indexOf(data.riskHdrName.trim());
          if (pageIndex >= 0) {
            this.riskHrdId = data.riskHrdId;
            this.renderForm1(data.riskHrdId, this.pageList[pageIndex]);
          } else {
            this.riskHrdId = data.riskHrdId;
            this.renderForm2(data.riskHrdId);
          }


        })
        $("td > .export", row).bind("click", () => {


        });
      }
    });
  }

  renderForm1(riskHrdId, pageIndex) {
    this.pageShowPageIndex = pageIndex;
    //console.log("pageShowPageIndex", this.pageShowPageIndex);
    //console.log("riskHrdId", riskHrdId);
    const URLHRD = "ia/int083/findRiskById";
    this.ajax.post(URLHRD, { riskHrdId: riskHrdId }, res => {
      this.riskAssRiskWsHdr = res.json();
      const URL = "ia/condition/findConditionByParentId";
      var riskType = 'OTHER';
      var riskConditionPage = 'int08-3-4';
      if (this.pageShowPageIndex == 'int08-3-3' || this.pageShowPageIndex == 'int08-3-6' || this.pageShowPageIndex == 'int08-3-7' || this.pageShowPageIndex == 'int08-3-8') {
        riskType = 'MAIN';
        riskConditionPage = this.pageShowPageIndex;
      } else if (this.pageShowPageIndex == 'int08-3-10') {
        riskType = 'QTN_MASTER';
        riskConditionPage = 'int02-2';
      }
      this.ajax.post(URL, { parentId: riskHrdId, riskType: riskType, page: riskConditionPage }, res => {
        this.condition = res.json();
        //console.log("condition", this.condition);

        if (this.pageShowPageIndex == 'int08-3-3') {
          this.initDatatableINT08_3_3();
        } else if (this.pageShowPageIndex == 'int08-3-6') {
          this.initDatatableINT08_3_6();
        } else if (this.pageShowPageIndex == 'int08-3-7') {
          this.initDatatableINT08_3_7();
        } else if (this.pageShowPageIndex == 'int08-3-8') {
          this.initDatatableINT08_3_8();
        } else if (this.pageShowPageIndex == 'int08-3-9') {

          let url = "ia/int083/findRiskOtherDtlByRiskHrdId";
          this.ajax.post(url, { riskHrdId: this.riskHrdId }, res => {
            // this.riskDataList
            var jsonObjList = res.json();
            //console.log("jsonObjList", jsonObjList)
            this.dataTableList = [];
            for (let index = 0; index < jsonObjList.length; index++) {
              var element = jsonObjList[index];
              //console.log(element);
              var riskData = new RiskData();
              riskData.riskOtherDtlId = element.riskOtherDtlId;
              riskData.riskHrdId = element.riskHrdId;
              riskData.departmentName = element.departmentName;

              riskData.riskCost = element.riskCost;
              riskData.rl = element.rl;
              riskData.valueTranslation = element.valueTranslation;
              riskData.isDeleted = 'N';
              riskData.color = element.color;
              //console.log(riskData);
              this.dataTableList.push(riskData);

            }
            this.initDatatableINT08_3_9();
          }, errRes => {
            //console.log("error", errRes);
          });
        } else if (this.pageShowPageIndex == 'int08-3-10') {
          let url = "ia/int083/findQtnData";
          this.ajax.post(url, { budgetYear: this.budgetYear, riskHrdId: this.riskHrdId }, res => {
            // this.riskDataList
            var jsonObjList = res.json();
            this.dataTableList = [];
            for (let index = 0; index < jsonObjList.length; index++) {
              var element = jsonObjList[index];
              var riskData = new RiskData();
              riskData.riskOtherDtlId = element.riskOtherDtlId;
              riskData.riskHrdId = element.riskHrdId;
              riskData.departmentName = element.departmentName;
              riskData.riskCost = element.riskCost;
              riskData.rl = element.rl;
              riskData.valueTranslation = element.valueTranslation;
              riskData.isDeleted = 'N';
              riskData.color = element.color;
              if (riskData.riskHrdId != this.riskHrdId) {
                riskData.riskHrdId = this.riskHrdId;
              }
              this.dataTableList.push(riskData);

            }
            //console.log(jsonObjList[0].other);
            this.ajax.post(URL, { parentId: jsonObjList[0].other, riskType: riskType, page: riskConditionPage }, conditionRes => {
              this.condition = conditionRes.json();
              //console.log(this.condition)

            });
            this.initDatatableINT08_3_10();
          }, errRes => {
            //console.log("error", errRes);
          });
        }

      });

    });

  }

  initDatatableINT08_3_3(): void {
    if (this.dataTableF1 != null) {
      this.dataTableF1.destroy();
    }
    const URL = AjaxService.CONTEXT_PATH + "ia/int083/dataTableWebService1";
    //console.log(URL);
    this.dataTableF1 = $("#dataTableF1").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: false,
      paging: true,
      ajax: {
        type: "POST",
        url: URL,
        data: { riskHrdId: this.riskHrdId }
      },
      columns: [

        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "departmentName" },
        { data: "checkOutDate" },
        { data: "closeDate" },
        { data: "years" },

        { data: "rl" },
        { data: "valueTranslation" }

      ], createdRow: function (row, data, dataIndex) {
        //console.log("row");
        //console.log("data", data.valueTranslation);
        //console.log("dataIndex", dataIndex);
        if (data.color == 'แดง') {
          $(row).find('td:eq(5)').addClass('red');
          $(row).find('td:eq(6)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(5)').addClass('green');
          $(row).find('td:eq(6)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(5)').addClass('yellow');
          $(row).find('td:eq(6)').addClass('yellow');
        }

      },
      columnDefs: [
        { targets: [0, 2, 3, 4, 5, 6], className: "center aligned" },

        { targets: [1], className: "left aligned" }
      ]

    });
  }


  initDatatableINT08_3_6(): void {

    if (this.dataTable_int08_3_6 != null) {
      this.dataTable_int08_3_6.destroy();
    }

    const URL = AjaxService.CONTEXT_PATH + "ia/int083/dataTableWebService2";
    //console.log(URL);
    this.dataTable_int08_3_6 = $("#dataTable_int08_3_6").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: true,
      paging: false,
      ajax: {
        type: "POST",
        url: URL,
        data: { riskHrdId: this.riskHrdId }
      },
      columns: [

        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "departmentName" },
        { data: "resultsIncome", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "budgetIncome", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "budgetDiff", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "percenDiff", render: $.fn.dataTable.render.number(',', '.', 2, '') },

        { data: "rl" },
        { data: "valueTranslation" }

      ], createdRow: function (row, data, dataIndex) {
        //console.log("row");
        //console.log("data", data.valueTranslation);
        //console.log("dataIndex", dataIndex);
        if (data.color == 'แดง') {
          $(row).find('td:eq(6)').addClass('red');
          $(row).find('td:eq(7)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(6)').addClass('green');
          $(row).find('td:eq(7)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(6)').addClass('yellow');
          $(row).find('td:eq(7)').addClass('yellow');
        }

      },
      columnDefs: [
        { targets: [0, 6, 7], className: "center aligned" },
        { targets: [1], className: "left aligned" },
        { targets: [2, 3, 4, 5], className: "right aligned" }

      ]

    });
  }


  initDatatableINT08_3_7(): void {

    if (this.dataTable_int08_3_7 != null) {
      this.dataTable_int08_3_7.destroy();
    }

    const URL = AjaxService.CONTEXT_PATH + "ia/int083/dataTableWebService3";
    //console.log(URL);
    this.dataTable_int08_3_7 = $("#dataTable_int08_3_7").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: true,
      paging: false,
      ajax: {
        type: "POST",
        url: URL,
        data: { riskHrdId: this.riskHrdId }
      },
      columns: [

        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "departmentName" },
        { data: "resultsIncome", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "budgetIncome", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "budgetDiff", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "percenDiff", render: $.fn.dataTable.render.number(',', '.', 2, '') },

        { data: "rl" },
        { data: "valueTranslation" }

      ], createdRow: function (row, data, dataIndex) {
        //console.log("row");
        //console.log("data", data.valueTranslation);
        //console.log("dataIndex", dataIndex);
        if (data.color == 'แดง') {
          $(row).find('td:eq(6)').addClass('red');
          $(row).find('td:eq(7)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(6)').addClass('green');
          $(row).find('td:eq(7)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(6)').addClass('yellow');
          $(row).find('td:eq(7)').addClass('yellow');
        }

      },
      columnDefs: [
        { targets: [0, 6, 7], className: "center aligned" },
        { targets: [1], className: "left aligned" },
        { targets: [2, 3, 4, 5], className: "right aligned" }

      ]

    });
  }


  initDatatableINT08_3_8(): void {

    if (this.dataTable_int08_3_8 != null) {
      this.dataTable_int08_3_8.destroy();
    }

    const URL = AjaxService.CONTEXT_PATH + "ia/int083/dataTableWebService4";
    //console.log(URL);
    this.dataTable_int08_3_8 = $("#dataTable_int08_3_8").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: true,
      paging: false,
      ajax: {
        type: "POST",
        url: URL,
        data: { riskHrdId: this.riskHrdId }
      },
      columns: [

        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "departmentName" },
        { data: "resultsIncome", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "budgetIncome", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "budgetDiff", render: $.fn.dataTable.render.number(',', '.', 2, '') },
        { data: "percenDiff", render: $.fn.dataTable.render.number(',', '.', 2, '') },

        { data: "rl" },
        { data: "valueTranslation" }

      ], createdRow: function (row, data, dataIndex) {
        //console.log("row");
        //console.log("data", data.valueTranslation);
        //console.log("dataIndex", dataIndex);
        if (data.color == 'แดง') {
          $(row).find('td:eq(6)').addClass('red');
          $(row).find('td:eq(7)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(6)').addClass('green');
          $(row).find('td:eq(7)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(6)').addClass('yellow');
          $(row).find('td:eq(7)').addClass('yellow');
        }

      },
      columnDefs: [
        { targets: [0, 6, 7], className: "center aligned" },
        { targets: [1], className: "left aligned" },
        { targets: [2, 3, 4, 5], className: "right aligned" }

      ]

    });
  }


  initDatatableINT08_3_9(): void {
    //console.log("initDatatableINT08_3_9");
    if (this.dataTable_int08_3_9 != null) {
      this.dataTable_int08_3_9.destroy();
    }
    //console.log(this.dataTableList);
    this.dataTable_int08_3_9 = $("#dataTable_int08_3_9").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: false,
      paging: false,
      data: this.dataTableList,
      columns: [
        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "departmentName" },
        { data: "riskCost", className: "right" },
        { data: "rl", className: "center" },
        { data: "valueTranslation", className: "center" }

      ], createdRow: function (row, data, dataIndex) {

        if (data.color == 'แดง') {
          $(row).find('td:eq(3)').addClass('red');
          $(row).find('td:eq(4)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(3)').addClass('green');
          $(row).find('td:eq(4)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(3)').addClass('yellow');
          $(row).find('td:eq(4)').addClass('yellow');
        }

      }


    });
  }


  initDatatableINT08_3_10(): void {
    //console.log("initDatatableINT08_3_10");
    if (this.dataTable_int08_3_10 != null) {
      this.dataTable_int08_3_10.destroy();
    }
    //console.log(this.dataTableList);
    this.dataTable_int08_3_10 = $("#dataTable_int08_3_10").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: false,
      paging: false,
      data: this.dataTableList,
      columns: [
        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "departmentName" },
        { data: "riskCost", className: "right" },
        { data: "rl", className: "center" },
        { data: "valueTranslation", className: "center" }

      ], createdRow: function (row, data, dataIndex) {

        if (data.color == 'แดง') {
          $(row).find('td:eq(3)').addClass('red');
          $(row).find('td:eq(4)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(3)').addClass('green');
          $(row).find('td:eq(4)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(3)').addClass('yellow');
          $(row).find('td:eq(4)').addClass('yellow');
        }

      }


    });
  }

  initDatatableF2(): void {
    if (this.dataTableF2 != null) {
      this.dataTableF2.destroy();
    }
    const URL = AjaxService.CONTEXT_PATH + "ia/int083/findRiskAssExcOtherDtlByHeaderId";
    //console.log(URL);
    this.dataTableF2 = $("#dataTableF2").DataTable({
      lengthChange: false,
      searching: false,
      ordering: false,
      pageLength: 10,
      processing: true,
      serverSide: false,
      paging: true,
      ajax: {
        type: "POST",
        url: URL,
        data: { riskHrdId: this.riskHrdId }
      },
      columns: [

        {
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          className: "center"
        },
        { data: "projectBase" },
        { data: "departmentName" },
        { data: "riskCost" },
        { data: "rl" },
        { data: "valueTranslation" }

      ], createdRow: function (row, data, dataIndex) {
        //console.log("row");
        //console.log("data", data.color);
        //console.log("dataIndex", dataIndex);
        if (data.color == 'แดง') {
          $(row).find('td:eq(4)').addClass('red');
          $(row).find('td:eq(5)').addClass('red');
        } else if (data.color == 'เขียว') {
          $(row).find('td:eq(4)').addClass('green');
          $(row).find('td:eq(5)').addClass('green');
        } else if (data.color == 'เหลือง') {
          $(row).find('td:eq(4)').addClass('yellow');
          $(row).find('td:eq(5)').addClass('yellow');
        }

      },
      columnDefs: [
        { targets: [0, 2, 4, 5], className: "center aligned" },
        { targets: [3], className: "right aligned" },
        { targets: [1], className: "left aligned" }
      ]

    });
  }

  renderForm2(riskHrdId) {
    this.pageShowPageIndex = 'int08-3-4';
    //console.log("riskHrdId", riskHrdId);
    const URLHRD = "ia/int083/findRiskById";
    this.ajax.post(URLHRD, { riskHrdId: riskHrdId }, res => {
      this.riskAssRiskWsHdr = res.json();
      //console.log("riskAssRiskWsHdr", this.riskAssRiskWsHdr);
      const URL = "ia/condition/findConditionByParentId";
      this.ajax.post(URL, { parentId: riskHrdId, riskType: 'OTHER', page: 'int08-3-4' }, res => {
        this.condition = res.json();
        //console.log("condition", this.condition);
        this.initDatatableF2();
      });
    });
  }

  clearData() {
    this.pageShowPageIndex = undefined;
  }

  changebudgetYear = event => {

    //console.log(event)

  }

  popupEditData() {
    $("#select1").show();
    $("#select2").show();
    $("#select3").show();
    $("#modalInt0811").modal("show");
  }

  closePopupEdit() {
    $("#select1").hide();
    $("#select2").hide();
    $("#select3").hide();
    $("#modalInt0811").modal("hide");
  }


}

class RiskData {
  departmentName: any = '';

  riskHrdId: any = 0;
  riskCost: any = '';
  rl: any = '';
  color: ''
  valueTranslation: any = '';
  riskOtherDtlId: any = 0;
  isDeleted: any = '';
}

