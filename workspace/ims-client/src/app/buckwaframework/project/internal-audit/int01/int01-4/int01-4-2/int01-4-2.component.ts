import { Component, OnInit } from "@angular/core";

declare var $: any;
@Component({
  selector: "app-int01-4-2",
  templateUrl: "./int01-4-2.component.html",
  styleUrls: ["./int01-4-2.component.css"]
})
export class Int0142Component implements OnInit {
  selectZone: any[];
  selectArea: any[];

  constructor() {}

  ngOnInit() {
    $(".ui.dropdown").dropdown();
    $(".ui.dropdown.ai").css("width", "100%");
    this.selectZone = [
      { value: "สำนักงานสรรพสามิตภาคที่ 1" },
      { value: "สำนักงานสรรพสามิตภาคที่ 2" },
      { value: "สำนักงานสรรพสามิตภาคที่ 3" },
      { value: "สำนักงานสรรพสามิตภาคที่ 4" },
      { value: "สำนักงานสรรพสามิตภาคที่ 5" },
      { value: "สำนักงานสรรพสามิตภาคที่ 6" },
      { value: "สำนักงานสรรพสามิตภาคที่ 7" }
    ];

    this.selectArea = [
      { value: "สำนักงานสรรพสามิตพื้นที่สมุทรสาคร" },
      { value: "สำนักงานสรรพสามิตพื้นที่สมุทรสงคราม" },
      { value: "สำนักงานสรรพสามิตพื้นที่สมุทรปราการ" }
    ];
  }

  ngAfterViewInit() {
    //$("#selectZone").dropdown();
    //$("#selectArea").dropdown();
  }
}
