import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-tax-audit-reporting",
  templateUrl: "./tax-audit-reporting.component.html",
  styleUrls: ["./tax-audit-reporting.component.css"]
})
export class TaxAuditReportingComponent implements OnInit {
  typeDocs: String[];
  topics: String[][];
  topic: String[];

  selectDoc: String;
  selectTop: String;

  selectedDoc: String;
  selectedTop: String;

  sent: boolean;

  constructor() {
    // Mock Data
    this.typeDocs = ["บันทึกข้อความ", "หนังสือราชการ", "แบบ ตส."];
    this.topics = [
      [
        "บันทึกข้อความขอส่งกระดาษทำการคัดเลือกลาย",
        "บันทึกข้อความแจ้งจัดทำแผนการตรวจสอบภาษี",
        "บันทึกข้อความขอรายงานผลการตรวจสอบภาษี",
        "บันทึกข้อความขออนุมัติแผนการตรวจสอบภาษี",
        "บันทึกข้อความขออนุมัติออกตรวจสอบภาษี",
        "บันทึกข้อความบันทึกผลการตรวจอสอบภาษี"
      ],
      [
        "หนังสือราชการแจ้งการประเมินภาษีสรรพสามิต ภษ 02-08",
        "หนังสือราชการแจ้งการออกตรวจสอบภาษีไปยังภาค/พื้นที่",
        "หนังสือราชการแจ้งการออกตรวจสอบภาษีไปยังสถานประกอบการ"
      ],
      [
        "ตส. 01-01",
        "ตส. 01-02",
        "ตส. 01-03",
        "ตส. 01-04",
        "ตส. 01-05",
        "ตส. 01-07",
        "ตส. 01-08",
        "ตส. 01-10",
        "ตส. 01-10/1",
        "ตส. 01-11",
        "ตส. 01-13",
        "ตส. 01-14",
        "ตส. 01-14/1",
        "ตส. 01-14/2",
        "ตส. 01-15",
        "ตส. 01-16",
        "ตส. 01-17",
        "ตส. 01-17/1",
        "ตส. 01-18",
        "ตส. 01-19"
      ]
    ];
    this.topic = [];
    this.sent = false; // false
    this.selectedTop = ""; // ''
  }

  ngOnInit() {}

  onSelectDoc = event => {
    this.topic = this.topics[event.target.value];
    this.selectDoc = this.typeDocs[event.target.value];
  };

  onSelectTop = event => {
    this.selectTop = this.topic[event.target.value];
  };

  onSubmit = e => {
    e.preventDefault();
    // show form generate pdf
    this.sent = true;
    this.selectedTop = this.selectTop;
  };

  onDiscard = event => {
    // hide form generate pdf
    this.sent = event;
  };
}
