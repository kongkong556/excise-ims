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
@Table(name = "IA_RISK_FACTORS")
public class IaRiskFactors extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IA_RISK_FACTORS_GEN")
	@SequenceGenerator(name = "IA_RISK_FACTORS_GEN", sequenceName = "IA_RISK_FACTORS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private BigDecimal id;
	@Column(name = "RISK_FACTORS")
	private String riskFactors;
	@Column(name = "BUDGET_YEAR")
	private String budgetYear;
	@Column(name = "SIDE")
	private String side;
	@Column(name = "FACTORS_LOW")
	private String factorsLow;
	@Column(name = "FACTORS_MEDIUM")
	private String factorsMedium;
	@Column(name = "FACTORS_HIGH")
	private String factorsHigh;
	@Column(name = "STATUS_SCREEN")
	private String statusScreen;
	@Column(name = "DATE_CRITERIA")
	private String dateCriteria;
	@Column(name = "INSPECTION_WORK")
	private BigDecimal inspectionWork;

	private String createdDateDesc;

	private String updateDateDesc;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getRiskFactors() {
		return riskFactors;
	}

	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}

	public String getBudgetYear() {
		return budgetYear;
	}

	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getFactorsLow() {
		return factorsLow;
	}

	public void setFactorsLow(String factorsLow) {
		this.factorsLow = factorsLow;
	}

	public String getFactorsMedium() {
		return factorsMedium;
	}

	public void setFactorsMedium(String factorsMedium) {
		this.factorsMedium = factorsMedium;
	}

	public String getFactorsHigh() {
		return factorsHigh;
	}

	public void setFactorsHigh(String factorsHigh) {
		this.factorsHigh = factorsHigh;
	}

	public String getStatusScreen() {
		return statusScreen;
	}

	public void setStatusScreen(String statusScreen) {
		this.statusScreen = statusScreen;
	}

	public String getDateCriteria() {
		return dateCriteria;
	}

	public void setDateCriteria(String dateCriteria) {
		this.dateCriteria = dateCriteria;
	}

	public BigDecimal getInspectionWork() {
		return inspectionWork;
	}

	public void setInspectionWork(BigDecimal inspectionWork) {
		this.inspectionWork = inspectionWork;
	}

	public String getCreatedDateDesc() {
		return createdDateDesc;
	}

	public void setCreatedDateDesc(String createdDateDesc) {
		this.createdDateDesc = createdDateDesc;
	}

	public String getUpdateDateDesc() {
		return updateDateDesc;
	}

	public void setUpdateDateDesc(String updateDateDesc) {
		this.updateDateDesc = updateDateDesc;
	}

}
