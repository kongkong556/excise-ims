import { Component, OnInit } from "@angular/core";
declare var $: any;
@Component({
  selector: "int01-1-1",
  templateUrl: "./int01-1-1.component.html"
})
export class Int0111Component implements OnInit {
  private selectedProduct: string = "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก ";
  private productList: any[];
  constructor() {}

  ngOnInit() {
    $(".ui.dropdown").dropdown();
    $(".ui.dropdown.ai").css("width", "100%");
    this.productList = [
      { value: "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก " },
      { value: "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก " },
      { value: "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก " },
      { value: "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก " },
      { value: "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก " },
      { value: "สำนักงานสรรพสามิตพื้นที่เมืองพิษณุโลก " }
    ];
  }
  popupEditData() {
    $("#modalEditData").modal("show");
    $("#modalInt062").modal("show");
    $("#selectTrading").show();
  }

  closePopupEdit() {
    $("#selectTrading").show();
    $("#modalEditData").modal("hide");
    $("#modalInt062").modal("hide");
  }
}
