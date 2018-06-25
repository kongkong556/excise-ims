import { Component, OnInit } from '@angular/core';
import { TravelCostHeader, TravelCostDetail } from '../../../../common/models';
import { AjaxService } from '../../../../common/services';
import { Prices } from '../../../../common/helper/travel';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { TextDateTH, formatter } from '../../../../common/helper/datepicker';
declare var $: any;
@Component({
  selector: 'app-int09-1-1',
  templateUrl: './int09-1-1.component.html',
  styleUrls: ['./int09-1-1.component.css']
})
export class Int0911Component implements OnInit {

  public status: string;
  public id: number;

  public hdr: TravelCostHeader;
  detail: TravelCostDetail[];
  data: TravelCostDetail;

  typeDocs: string[];
  topics: string[][];
  topic: string[];

  selectDoc: string;
  selectTop: string;

  selectedDoc: string;
  selectedTop: string;
  sent: boolean;



  constructor(private ajax: AjaxService ,  private router: Router) {
  
    this.status = 'create';
    this.hdr = new TravelCostHeader();
    this.detail = new Array<TravelCostDetail>();
    this.data = new TravelCostDetail();
    this.typeDocs = [
      'ทั่วไป',
      'วิชาการ',
      'อำนวยการ',
      'บริหาร',
    ];
    this.topics = [
      [
        'ปฏิบัติงาน',
        'ชำนาญงาน',
        'อาวุโส',
        'ทักษะพิเศษ',

      ],
      [
        'ปฏิบัติการ',
        'ชำนาญการ',
        'ชำนาญการพิเศษ',
        'เชี่ยวชาญ',
        'ทรงคุณวุฒิ',
      ],
      [

        'ระดับต้น',
        'ระดับสูง',

      ], [

        'ระดับต้น',
        'ระดับสูง',

      ]
    ];
    this.topic = [];
    this.sent = false; // false
    this.selectedTop = ''; // ''
    this.hdr.startDate = null;
    this.hdr.endDate = null;
  }

  ngOnInit() {
    $('.ui.radio.checkbox').checkbox();
    $('#example2').calendar({
      endCalendar: $('#example3'),
      maxDate: new Date(),
      type: 'date',
      text: TextDateTH,
      formatter: formatter(),
      onChange: function(date) {
        if ($('#startDate').val() !== null) {
          var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds 
          var nd2 = $('#endDate').val().split('/');
          var st1 = [
            date.getDate(),
            date.getMonth() + 1,
            date.getFullYear() + 543
          ];
          var firstDate = new Date(st1[2], st1[1], st1[0]);
          var secondDate = new Date(nd2[2], nd2[1], nd2[0]);
          var rs = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay)));
          $('#allowanceDate').attr('max', rs);
          $('#rentDate').attr('max', rs);
        }
      }
    });
    $('#example3').calendar({
      startCalendar: $('#example2'),
      maxDate: new Date(),
      type: 'date',
      text: TextDateTH,
      formatter: formatter(),
      onChange: function(date) {
        if ($('#startDate').val() !== null) {
          var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds 
          var st1 = $('#startDate').val().split('/');
          var nd2 = [
            date.getDate(),
            date.getMonth() + 1,
            date.getFullYear() + 543
          ];
          var firstDate = new Date(st1[2], st1[1], st1[0]);
          var secondDate = new Date(nd2[2], nd2[1], nd2[0]);
          var rs = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay)));
          $('#allowanceDate').attr('max', rs);
          $('#rentDate').attr('max', rs - 1);
        }
      }
    });
  }

  onSelectDoc = event => {
    this.topic = this.topics[event.target.value];
    this.selectDoc = this.typeDocs[event.target.value];
  };

  onSelectTop = event => {
    this.selectTop = this.topic[event.target.value];
  };

  onSubmit = () => {
    // show form generate pdf
    this.sent = true;
    this.selectedTop = this.selectTop;
    const {
      workSheetHeaderName,
      departmentName,
      startDate,
      endDate,
      description
    } = this.hdr;
    var data: TravelCostHeader = {
      workSheetHeaderId: 0,
      workSheetHeaderName: workSheetHeaderName,
      departmentName: departmentName,
      startDate: startDate,
      endDate: endDate,
      description: description,
      createdBy: '',
      createdDatetime: null,
      updateBy: '',
      updateDatetime: null,
      Detail: this.detail
    };

    const URL = "ia/int09/create";
    var router = this.router;
    this.ajax.post(URL, data, function (res) {      
      console.log(res.json());
      router.navigate(['/int09-1']);
    });

  };

  maxDate(startDate, endDate) {
    var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds 
    var st1 = startDate.split('/');
    var nd2 = endDate.split('/');
    var firstDate = new Date(st1[2], st1[1], st1[0]);
    var secondDate = new Date(nd2[2], nd2[1], nd2[0]);

    return Math.round(Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay)));
  }

  getPrice(index, what) {
    const { category, degree, allowanceDate, rentDate } = this.detail[index];
    const num = what === 'allowance' ? allowanceDate : rentDate;
    return Prices(category, degree, what) * num;
  }

  totalCost(index) {
    const { restType, travelCost, otherCost } = this.detail[index];
    let total:number = 0;
    total += this.getPrice(index, 'allowance');
    total += this.getPrice(index, restType);
    total += parseFloat(travelCost.toString());
    total += parseFloat(otherCost.toString());
    return total;
  }

  selectedBox(e, index) {
    this.detail[index].checked = e.target.checked;
  }

  manageDate(e) {
    e.preventDefault();

    // extract constructs
    const {
      name,
      lastName,
      position,
      category,
      degree,
      allowanceDate,
      rentDate,
      restType,
      travelCost,
      otherCost,
      note
    } = e.target;

    // binding data
    const data: TravelCostDetail = new TravelCostDetail();
    data.name = name.value;
    data.lastName = lastName.value;
    data.position = position.value;
    data.category = category.value;
    data.degree = degree.value;
    data.allowanceDate = allowanceDate.value;
    data.rentDate = rentDate.value;
    data.restType = restType.value;
    data.travelCost = travelCost.value;
    data.otherCost = otherCost.value;
    data.note = note.value;

    name.value = '';
    lastName.value = '';
    position.value = '';
    category.value = '';
    degree.value = '';
    allowanceDate.value = '';
    rentDate.value = '';
    restType.value = '';
    travelCost.value = '';
    otherCost.value = '';
    note.value = '';

    if (this.status === 'create') {
      this.detail.push(data);
      this.clearData();
    } else {
      this.detail[this.id] = data;
      this.status = 'edit';
    }
  }

  deleteData() {
    this.detail = this.detail.filter(obj => {
      return !obj.checked;
    });
  }

  editData(index: number) {

    // change `status` and `id`
    this.id = index;
    this.status = 'edit';

    const data: any = this.detail[index];
    this.data = new TravelCostDetail();
    this.data = data;

  }

  clearData() {
    if (this.status === 'create'){
      this.data = new TravelCostDetail();
    } else {
      this.data = new TravelCostDetail();
      this.status = 'create';
    }
  }

}
