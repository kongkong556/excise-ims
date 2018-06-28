import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";

import { Int082Component } from './int08-2.component';

import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../../../common/services';
import { Int0821Component } from './int08-2-1/int08-2-1.component';

const routes: Routes = [
    { path: '', component: Int082Component, canActivate: [AuthGuard] },
    { path: '1', component: Int0821Component, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ],
  declarations: [
    Int082Component,
    Int0821Component
  ],
  exports: [RouterModule]
})
export class Int082Module { }