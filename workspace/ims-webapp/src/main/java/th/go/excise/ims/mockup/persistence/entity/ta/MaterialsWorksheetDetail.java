package th.go.excise.ims.mockup.persistence.entity.ta;

import java.math.BigDecimal;
import java.util.Date;

public class MaterialsWorksheetDetail {
    private BigDecimal materialsWsDtlId;
    private BigDecimal materialsWsHeaderId;
    private String materialsWsDtlNo;
    private String materialsWsDtlOrder;
    private String materialsWsDtlBalance;
    private String materialsWsDtlCounting;
    private String createdBy;
    private Date createdDatetime;
    private String updateBy;
    private Date updateDatetime;

    public BigDecimal getMaterialsWsDtlId() {
        return materialsWsDtlId;
    }

    public void setMaterialsWsDtlId(BigDecimal materialsWsDtlId) {
        this.materialsWsDtlId = materialsWsDtlId;
    }

    public BigDecimal getMaterialsWsHeaderId() {
        return materialsWsHeaderId;
    }

    public void setMaterialsWsHeaderId(BigDecimal materialsWsHeaderId) {
        this.materialsWsHeaderId = materialsWsHeaderId;
    }

    public String getMaterialsWsDtlNo() {
        return materialsWsDtlNo;
    }

    public void setMaterialsWsDtlNo(String materialsWsDtlNo) {
        this.materialsWsDtlNo = materialsWsDtlNo;
    }

    public String getMaterialsWsDtlOrder() {
        return materialsWsDtlOrder;
    }

    public void setMaterialsWsDtlOrder(String materialsWsDtlOrder) {
        this.materialsWsDtlOrder = materialsWsDtlOrder;
    }

    public String getMaterialsWsDtlBalance() {
        return materialsWsDtlBalance;
    }

    public void setMaterialsWsDtlBalance(String materialsWsDtlBalance) {
        this.materialsWsDtlBalance = materialsWsDtlBalance;
    }

    public String getMaterialsWsDtlCounting() {
        return materialsWsDtlCounting;
    }

    public void setMaterialsWsDtlCounting(String materialsWsDtlCounting) {
        this.materialsWsDtlCounting = materialsWsDtlCounting;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
