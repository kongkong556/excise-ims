import { File } from './../../../../../../common/models/file';
import { MessageBarService, AjaxService } from 'app/buckwaframework/common/services';
import { TableReq } from './../../../../../../common/models/table';
import { Component, OnInit } from '@angular/core';

import { TextDateTH, formatter } from '../../../../../../common/helper/datepicker';
declare var jQuery: any;
declare var $: any;

@Component({
  selector: 'app-int05-1-1-3',
  templateUrl: './int05-1-1-3.component.html',
  styleUrls: ['./int05-1-1-3.component.css']
})
export class Int05113Component implements OnInit {

  formModal: FormModal = new FormModal();
  data: FormModal[];
  stampType : any;
  stampGenre :any;
  table: any;
  randomNumber: number;
  file :File[];
  constructor(
    private message: MessageBarService,
    private ajax: AjaxService,    
  ) {
    this.data = []
    this.randomNumber = 0;
    this.stampType = null;
    this.stampGenre = null;
    this.file = new Array<File>();
  }

  ngOnInit() {
    this.calenda();
    this.dataTable();    
    this.stampTypeList();
  }
  ngAfterViewInit() {
    this.callDropdown();
  }

  checkBlank=(e)=>{
    if(e == null || e=="") return true;
  }
  restoreDropdown(){
    $("#stampType").dropdown('restore defaults');
    $("#stampBrand").dropdown('restore defaults');
    $("#status").dropdown('restore defaults');
  }
  callDropdown(){
    $("#stampType").dropdown().css('width','100%');
    $("#stampBrand").dropdown().css('width','100%');
    $("#status").dropdown().css('width','100%');
  }
  stampTypeList=()=> {
    let url = "ia/int05113/stamTypes";
    this.ajax.get(url,res=>{
      this.stampType = res.json();
    })
  }
  stampGenreList=(e)=>{
    if(!this.checkBlank(e.target.value)){
      $("#stampBrand").dropdown('restore defaults');
      console.log("id : "+e.target.value);
      
      let url = "ia/int05113/stamGenre/"+e.target.value;
      this.ajax.get(url,res=>{
        console.log(res.json());
        this.stampGenre = res.json();
      })
    }   
  }
  onUpload=(event)=>{

    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();

      reader.onload = (e: any) => {
        const { name, type } = event.target.files[0];
        const f = {
          name: name,
          type: type,
          value: e.target.result
        };
        
        this.file.push(f);
        console.log(this.file);
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }
  calenda = () => {
    $("#dateF").calendar({
      maxDate: new Date(),
      endCalendar: $("#dateTo"),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    });
    $("#dateT").calendar({
      maxDate: new Date(),
      startCalendar: $("#dateForm"),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    });
    $("#dateOfPayForm").calendar({
      maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    }, 'blur');
    $("#dateWithdrawStampForm").calendar({
      maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    });
    $("#dateDeliverStampForm").calendar({
      maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    });
    $("#fivePartDateForm").calendar({
      maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    });
    $("#stampCheckDateForm").calendar({
      maxDate: new Date(),
      type: "date",
      text: TextDateTH,
      formatter: formatter()
    });
  }

  onAdd = () => {
    if(this.checkBlank($("#dateOfPay").val())){
      this.message.alert("กรุณาระบุ วันที่รับ - จ่าย","แจ้งเตือน");
      return false;
    }
    if(this.checkBlank($("#status").val())){
      this.message.alert("กรุณาระบุ วันที่รับ - จ่าย","แจ้งเตือน");
      return false;
    }
    if(this.checkBlank($("#departmentName").val())){
      this.message.alert("กรุณาระบุ หน่วยงาน / ผู้ประกอบการ","แจ้งเตือน");
      return false;
    }
    if(this.checkBlank($("#stampType").val())){
      this.message.alert("กรุณาระบุ ประเภทแสตมป์","แจ้งเตือน");
      return false;
    }
    if(this.checkBlank($("#stampBrand").val())){
      this.message.alert("กรุณาระบุ ชนิดแสตมป์","แจ้งเตือน");
      return false;
    }

    if ($("#edit").val() == "edit" && $("#idEdit").val()!="") {
      
      const index = this.data.findIndex(obj => obj.idRandom == $("#idEdit").val());
      this.data[index] = this.formModal;
      $("#edit").val("");
      $("#idEdit").val("");
    } else {

      this.formModal.idRandom = this.randomNumber++;
      this.formModal.stampType = ($("#stampType option:selected").text()=="กรุณาเลือก" ? "":$("#stampType option:selected").text());
      this.formModal.stampBrand = ($("#stampBrand option:selected").text()=="กรุณาเลือก" ? "":$("#stampBrand option:selected").text())
      this.formModal.file = this.file;
      this.data.push(this.formModal);
    }
    this.table.clear().draw();
    this.table.rows.add(this.data); // Add new data
    this.table.columns.adjust().draw(); // Redraw the DataTable
    this.message.successModal("ทำรายสำเร็จ", "แจ้งเตือน");
    this.formModal = new FormModal();
    this.restoreDropdown();
  }

  onSave = () => {
    this.message.comfirm((res) => {
      if (res) {
        let url = 'ia/int05113/save';    
        this.ajax.post(url, JSON.stringify(this.data),
          res => {
            this.message.successModal("ทำรายสำเร็จ", "แจ้งเตือน");
            this.data = [];
          }, error => {
            this.message.errorModal("ทำรายไม่สำเร็จ", "แจ้งเตือน");
          });
      }
    }, "", "ยืนยันการทำรายการ");
  }

  dataTable = () => {
    this.table = $("#dataTable").DataTable({
      "serverSide": false,
      "searching": false,
      "ordering": false,
      "processing": true,
      "scrollX": true,
      "data": this.data,
      "columns": [
        {
          "data": "dateOfPay",
          "render": function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
          "className": "ui center aligned"
        }, {
          "data": "dateOfPay"
        }, {
          "data": "status",
          "className": "ui center aligned",
        }, {
          "data": "departmentName",
          "className": "ui center aligned"
        }, {
          "data": "bookNumberWithdrawStamp",
          "className": "ui center aligned"
        }, {
          "data": "dateWithdrawStamp",
          "className": "ui center aligned"
        }, {
          "data": "bookNumberDeliverStamp",
          "className": "ui center aligned"
        }, {
          "data": "dateDeliverStamp",
          "className": "ui center aligned"
        }, {
          "data": "fivePartNumber",
          "className": "ui center aligned"
        }, {
          "data": "fivePartDate",
          "className": "ui center aligned"
        }, {
          "data": "stampCheckDate",
          "className": "ui center aligned"
        }, {
          "data": "stampChecker",
          "className": "ui center aligned"
        }, {
          "data": "stampBrand",
          "className": "ui center aligned"
        }, {
          "data": "numberOfBook",
          "className": "ui center aligned"
        }, {
          "data": "numberOfStamp",
          "className": "ui center aligned"
        }, {
          "data": "valueOfStampPrinted",
          "className": "ui center aligned"
        }, {
          "data": "sumOfValue",
          "className": "ui center aligned"
        }, {
          "data": "serialNumber",
          "className": "ui center aligned"
        }, {
          "data": "note",
          "className": "ui center aligned"
        }, {
          "data": "note",
          "render": function (data, type, row) {
            var btn = '';
            btn += '<button class="ui mini yellow button btn-edit">แก้ไข</button>';
            btn += '<button class="ui mini red button btn-delete">ลบ</button>';
            return btn;
          },
          "className": "ui center aligned"
        }
      ]
    });
    this.table.clear().draw();
    this.table.rows.add(this.data); // Add new data
    this.table.columns.adjust().draw(); // Redraw the DataTable\

    this.table.on('click', 'tbody tr button.btn-edit', (e) => {
      var closestRow = $(e.target).closest('tr');
      var data = this.table.row(closestRow).data();      
      setTimeout(() => {        
        this.formModal.dateOfPay = data.dateOfPay;
        this.formModal.bookNumberDeliverStamp = data.bookNumberDeliverStamp;
        this.formModal.bookNumberWithdrawStamp = data.bookNumberWithdrawStamp;
        this.formModal.createdDate = data.createdDate;
        this.formModal.dateDeliverStamp = data.dateDeliverStamp;
        this.formModal.dateWithdrawStamp = data.dateWithdrawStamp;
        this.formModal.departmentName = data.departmentName;
        this.formModal.exciseDepartment = data.exciseDepartment;
        this.formModal.exciseDistrict = data.exciseDistrict;
        this.formModal.exciseRegion = data.exciseRegion;
        this.formModal.fivePartDate = data.fivePartDate;
        this.formModal.fivePartNumber = data.fivePartNumber;
        this.formModal.note = data.note;
        this.formModal.numberOfBook = data.numberOfBook;
        this.formModal.numberOfStamp = data.numberOfStamp;
        this.formModal.serialNumber = data.serialNumber;
        this.formModal.stampBrand = data.stampBrand;
        this.formModal.stampCheckDate = data.stampCheckDate;
        this.formModal.stampChecker = data.stampChecker;
        this.formModal.stampCodeEnd = data.stampCodeEnd;
        this.formModal.stampCodeStart = data.stampCodeStart;
        this.formModal.stampType = data.stampType;
        this.formModal.status = data.status;
        this.formModal.sumOfValue = data.sumOfValue;
        this.formModal.taxStamp = data.taxStamp;
        this.formModal.valueOfStampPrinted = data.valueOfStampPrinted;
        this.formModal.workSheetDetailId = data.workSheetDetailId;
        this.formModal.fileName = data.fileName;
        $("#edit").val("edit");
        $("#idEdit").val(data.idRandom);
      }, 50);

    });
    this.table.on('click', 'tbody tr button.btn-delete', (e) => {
      var closestRow = $(e.target).closest('tr');
      var data = this.table.row(closestRow).data();
      this.message.comfirm((res) => {
        if (res) {
          let index = this.data.findIndex(obj => obj.idRandom == data.idRandom);

          this.data.splice(index, 1);

          this.table.clear().draw();
          this.table.rows.add(this.data); // Add new data
          this.table.columns.adjust().draw(); // Redraw the DataTable
        }
      }, "", "ยืนยันการลบ");

    });
  }

}
class FormModal {
  workSheetDetailId: string = null;
  exciseDepartment: string = null;
  exciseRegion: string = null;
  exciseDistrict: string = null;
  dateOfPay: string = null;
  status: string = null;
  departmentName: string = null;
  bookNumberWithdrawStamp: string = null;
  dateWithdrawStamp: string = null;
  bookNumberDeliverStamp: string = null;
  dateDeliverStamp: string = null;
  fivePartNumber: string = null;
  fivePartDate: string = null;
  stampCheckDate: string = null;
  stampChecker: string = null;
  stampType: string = null;
  stampBrand: string = null;
  numberOfBook: string = null;
  numberOfStamp: string = null;
  valueOfStampPrinted: string = null;
  sumOfValue: string = null;
  serialNumber: string = null;
  taxStamp: string = null;
  stampCodeStart: string = null;
  stampCodeEnd: string = null;
  note: string = null;
  createdDate: string = null;
  fileName: string = null;
  idRandom: number = 0;
  file : File[];
}
