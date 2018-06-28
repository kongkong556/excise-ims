import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";

import { Int0151Component } from './int01-5-1/int01-5-1.component';

import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../../../common/services';

const routes: Routes = [
    { path: '', component: Int0151Component, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ],
  declarations: [
    Int0151Component
  ],
  exports: [RouterModule]
})
export class Int015Module { }