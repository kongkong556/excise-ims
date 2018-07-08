package th.co.baiwa.excise.ta.persistence.entity;

import java.math.BigDecimal;

import th.co.baiwa.excise.domain.BaseEntity;

public class PdtDrawingWsHeader extends BaseEntity {
	private BigDecimal taPdtDrawingWsHeaderId;
	private BigDecimal taxPlanId;
	private String exciseId;
	private String taxationId;
	private BigDecimal taAnalysisId;
	private String startDate;
	private String endDate;
	private String pdtType;
	private String subPdtType;

	public BigDecimal getTaPdtDrawingWsHeaderId() {
		return taPdtDrawingWsHeaderId;
	}

	public void setTaPdtDrawingWsHeaderId(BigDecimal taPdtDrawingWsHeaderId) {
		this.taPdtDrawingWsHeaderId = taPdtDrawingWsHeaderId;
	}

	public BigDecimal getTaxPlanId() {
		return taxPlanId;
	}

	public void setTaxPlanId(BigDecimal taxPlanId) {
		this.taxPlanId = taxPlanId;
	}

	public String getExciseId() {
		return exciseId;
	}

	public void setExciseId(String exciseId) {
		this.exciseId = exciseId;
	}

	public String getTaxationId() {
		return taxationId;
	}

	public void setTaxationId(String taxationId) {
		this.taxationId = taxationId;
	}

	public BigDecimal getTaAnalysisId() {
		return taAnalysisId;
	}

	public void setTaAnalysisId(BigDecimal taAnalysisId) {
		this.taAnalysisId = taAnalysisId;
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

	public String getPdtType() {
		return pdtType;
	}

	public void setPdtType(String pdtType) {
		this.pdtType = pdtType;
	}

	public String getSubPdtType() {
		return subPdtType;
	}

	public void setSubPdtType(String subPdtType) {
		this.subPdtType = subPdtType;
	}

}
