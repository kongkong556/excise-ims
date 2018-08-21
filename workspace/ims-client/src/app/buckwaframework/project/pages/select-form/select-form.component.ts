import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

// services
import { MessageBarService } from '../../../common/services/message-bar.service';

// models
import { Message } from '../../../common/models/message';

declare var jQuery: any;
declare var $: any;

@Component({
    selector: 'select-form',
    templateUrl: 'select-form.component.html',
    styleUrls: ['select-form.component.css']
})
export class SelectFormComponent implements OnInit {
    obj: Data;
    private showSubMenuMat: boolean = false;
    private showSubMenuIns: boolean = false;
    private showSubMenuTax: boolean = false;
    private coordinate: String;
    private routerOpe051: String;

    constructor(
        private messageBarService: MessageBarService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.obj = new Data();
    }

    ngOnInit(): void {
        this.coordinate = this.route.snapshot.params['coordinate'];
        if (this.coordinate == "สนามกอล์ฟ") {
            this.routerOpe051 = "/ope05-1-1";
        } else {
            this.routerOpe051 = "/ope05-1";
        }
    }

    ngAfterViewInit() {
        // $('.dropdown').dropdown();
    }

    clickMenuMat() {
        this.showSubMenuMat = true;
        this.showSubMenuIns = false;
        this.showSubMenuTax = false;
    }

    clickMenuIns() {
        this.showSubMenuMat = false;
        this.showSubMenuIns = true;
        this.showSubMenuTax = false;
    }

    clickMenuTax() {
        this.showSubMenuMat = false;
        this.showSubMenuIns = false;
        this.showSubMenuTax = true;
    }
}
class Data {
    companyName: any = "";
    startDate: any = "";
    endDate: any = "";
    analysNumber: any = "";
    startDateSplit: any = "";
    endDateSplit: any = "";
}
