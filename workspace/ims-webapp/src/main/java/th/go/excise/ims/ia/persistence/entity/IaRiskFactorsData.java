
package th.go.excise.ims.ia.persistence.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import th.co.baiwa.buckwaframework.common.persistence.entity.BaseEntity;

@Entity
@Table(name = "IA_RISK_FACTORS_DATA")
public class IaRiskFactorsData
    extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IA_RISK_FACTORS_DATA_GEN")
    @SequenceGenerator(name = "IA_RISK_FACTORS_DATA_GEN", sequenceName = "IA_RISK_FACTORS_DATA_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "ID_FACTORS")
    private BigDecimal idFactors;
    @Column(name = "BUDGET_YEAR")
    private String budgetYear;
    @Column(name = "PROJECT")
    private String project;
    @Column(name = "INSPECTION_WORK")
    private BigDecimal inspectionWork;
    @Column(name = "EXCISE_CODE")
    private String exciseCode;
    @Column(name = "SECTOR")
    private String sector;
    @Column(name = "AREA")
    private String area;
    @Column(name = "RISK_COST")
    private String riskCost;
    @Column(name = "RISK_RATE")
    private String riskRate;
    @Column(name = "RISK_STEP")
    private String riskStep;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getIdFactors() {
        return idFactors;
    }

    public void setIdFactors(BigDecimal idFactors) {
        this.idFactors = idFactors;
    }

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public BigDecimal getInspectionWork() {
        return inspectionWork;
    }

    public void setInspectionWork(BigDecimal inspectionWork) {
        this.inspectionWork = inspectionWork;
    }

    public String getExciseCode() {
        return exciseCode;
    }

    public void setExciseCode(String exciseCode) {
        this.exciseCode = exciseCode;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRiskCost() {
        return riskCost;
    }

    public void setRiskCost(String riskCost) {
        this.riskCost = riskCost;
    }

    public String getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(String riskRate) {
        this.riskRate = riskRate;
    }

    public String getRiskStep() {
        return riskStep;
    }

    public void setRiskStep(String riskStep) {
        this.riskStep = riskStep;
    }

}