import { Injectable } from '@angular/core';

import { Utils } from 'helpers/utils';
import { MessageBarService } from 'services/message-bar.service';
import { AjaxService } from 'services/ajax.service';


declare var $: any;

@Injectable()
export class Opeo46Service {

    // ==> params
    table: any;
    dataRespon: any;
    searchFlag: String = "FALSE";
    fileName: any;
    dataExcel: any = null;
    constructor(
        private ajax: AjaxService,
        private message: MessageBarService
    ) { }

    findExciseId = (): Promise<any> => {
        let url = "ta/opo046/exciseidList";
        return new Promise((resolve, reject) => {
            this.ajax.get(url, res => {
                resolve(res.json())
            })
        });
    }

    findByExciseId = (exciseId): Promise<any> => {
        let url = "ta/opo046/findByExciseId";
        return new Promise((resolve, reject) => {
            this.ajax.post(url, JSON.stringify(exciseId), res => {
                resolve(res.json())

            })
        });
    }

    onChangeUpload = async (event: any) => {
        if (event.target.files && event.target.files.length > 0) {
            let reader = new FileReader();
            reader.onload = (e: any) => {
                const f = {
                    name: event.target.files[0].name,
                    type: event.target.files[0].type,
                    value: e.target.result
                };
                this.fileName = [f];
                console.log(this.fileName);
            };
            reader.readAsDataURL(event.target.files[0]);
        }
    };

    upload = (form: any) => {
        let url = "ta/opo046/upload";
        this.ajax.upload(url, form, success => {
            this.dataExcel = success.json();
            if (Utils.isNull(this.dataExcel)) {
                this.message.errorModal('ไม่สามารถอัปโหลดไฟล์');
            }
        });
    }

    search = () => {
        this.searchFlag = "TRUE";

        if (this.table != null) {
            this.table.destroy();
        }
        this.dataTable();
        // $("#dataTable").DataTableTh().ajax.reload();
    }
    claer = () => {
        this.searchFlag = "FALSE";
        $("#exciseId").dropdown('restore defaults');
        $("#dataFrom").val('');
        $("#dateTo").val('');
        $("#fileName").val('');
        this.dataExcel = null;
        if (this.table != null) {
            this.table.destroy();
        }
        this.dataTable();
    }

    save = () => {
        let data = this.table.data();
        let url = "ta/opo046/save";
        
        let list = [];
        for(let i=0 ;i<data.length;i++) {  
            let row = this.table.row(i).data();
            console.log(row);
            list.push(row);
        }
        console.log(list);
        if (data.length == 0) {
            this.message.alert("ไม่มีข้อมูล");
            return;
        }
        this.message.comfirm((res) => {
            if (res) {
                 this.ajax.post(url, list, res => { 
                    this.message.successModal("บันทึก");
                },err=>{
                    this.message.errorModal("บันทึกไม่สำเร็จ");
                });
            }
        }, "", "ยืนยันการลบ");
    }
    dataTable = () => {
        this.table = $("#dataTable").DataTableTh({
            "serverSide": false,
            "processing": true,
            "scrollX": true,
            "paging": true,
            "ajax": {
                "url": '/ims-webapp/api/ta/opo046/findAll',
                "contentType": "application/json",
                "type": "POST",
                "data": (d) => {
                    return JSON.stringify($.extend({}, d, {
                        "exciseId": $("#exciseId").val(),
                        "searchFlag": this.searchFlag,
                        "dataExcel": this.dataExcel
                    }));

                },
            },
            "columns": [
                {
                    "data": "taExciseAcc0502DtlId",
                    "render": function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    },
                    "className": "ui center aligned"
                }, {
                    "data": "taExciseAcc0502DtlList",
                    "className": "ui left aligned"
                },
                {
                    "data": "taxAmount",
                    "className": "ui right aligned"
                },
                {
                    "data": "amount",
                    "className": "ui right aligned"
                },
                {
                    "data": "taxPerAmount",
                    "className": "ui right aligned",
                    "render": (data) => {
                        return Utils.moneyFormatDecimal(data);
                    }
                },
                {
                    "data": "receiptNumber",
                    "className": "ui center aligned"
                },
                {
                    "data": "taxNumber",
                    "className": "ui center aligned"
                },
                {
                    "data": "volume",
                    "className": "ui center aligned"
                },
                {
                    "data": "unit",
                    "className": "ui right aligned",
                    "render": (data) => {
                        return Utils.moneyFormatDecimal(data);
                    }
                },
                {
                    "data": "unit",
                    "className": "ui right aligned",
                    "render": (data, type, row, ) => {
                        if (Utils.isNotNull(row.unit)) {
                            let diff = row.taxPerAmount - row.unit;
                            return Utils.moneyFormatDecimal(diff);
                        }
                        return "";
                    }
                },
            ]
        });
        // this.table.clear().draw();
        // this.table.rows.add(this.data); // Add new data
        // this.table.columns.adjust().draw(); // Redraw the DataTable\


    }
}