import { Component, OnInit } from "@angular/core";
import {
  Router,
  NavigationEnd,
  NavigationCancel,
  NavigationStart
} from "@angular/router";

// services
import { AuthService } from "./buckwaframework/common/services/auth.service";
import { TranslateService } from "./buckwaframework/common/services/translate.service";

// models
import { User } from "./buckwaframework/common/models/user";

declare var jQuery: any;
declare var $: any;

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements OnInit {
  user: User;
  loading: boolean;
  constructor(
    private authService: AuthService,
    private router: Router,
    private translateService: TranslateService
  ) { }

  ngOnInit(): void {
    this.translateService.use("th");
    this.user = this.authService.getUser();
  }

  logout() {
    this.authService.logout();
  }

  ngAfterViewInit() {
    $(".ui.modal.condition").modal({
      centered: false
    });

    $(".dropdown").dropdown();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.loading = true;
      } else if (
        event instanceof NavigationEnd ||
        event instanceof NavigationCancel
      ) {
        this.loading = false;
      }
    });
  }

  changeToEnglish() {
    this.translateService.use("en");
  }

  changeToThai() {
    this.translateService.use("th");
  }
}
