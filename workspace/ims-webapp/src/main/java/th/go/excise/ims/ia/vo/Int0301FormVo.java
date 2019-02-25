package th.go.excise.ims.ia.vo;

import java.math.BigDecimal;

import th.go.excise.ims.ia.persistence.entity.IaRiskFactorsConfig;

public class Int0301FormVo {

	private BigDecimal id;
	private String budgetYear;
	private String factorsLevel;
	private BigDecimal inspectionWork;
	
	private BigDecimal idMaster;
	
	private IaRiskFactorsConfig iaRiskFactorsConfig;	
	
	private String startDate; 
	private String endDate; 
	
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getBudgetYear() {
		return budgetYear;
	}
	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}
	public String getFactorsLevel() {
		return factorsLevel;
	}
	public void setFactorsLevel(String factorsLevel) {
		this.factorsLevel = factorsLevel;
	}
	public BigDecimal getInspectionWork() {
		return inspectionWork;
	}
	public void setInspectionWork(BigDecimal inspectionWork) {
		this.inspectionWork = inspectionWork;
	}
	public BigDecimal getIdMaster() {
		return idMaster;
	}
	public void setIdMaster(BigDecimal idMaster) {
		this.idMaster = idMaster;
	}
	public IaRiskFactorsConfig getIaRiskFactorsConfig() {
		return iaRiskFactorsConfig;
	}
	public void setIaRiskFactorsConfig(IaRiskFactorsConfig iaRiskFactorsConfig) {
		this.iaRiskFactorsConfig = iaRiskFactorsConfig;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}