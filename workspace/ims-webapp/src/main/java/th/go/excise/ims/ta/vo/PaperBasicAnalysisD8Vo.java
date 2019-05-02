package th.go.excise.ims.ta.vo;

import java.math.BigDecimal;

public class PaperBasicAnalysisD8Vo {
	private String paperBaNo;
	private String recNo;
	private String taxMonth;
	private BigDecimal incomeLastYearAmt;
	private BigDecimal incomeCurrentYearAmt;
	private BigDecimal diffIncomeAmt;
	private BigDecimal diffIncomePnt;

	public String getPaperBaNo() {
		return paperBaNo;
	}

	public void setPaperBaNo(String paperBaNo) {
		this.paperBaNo = paperBaNo;
	}

	public String getRecNo() {
		return recNo;
	}

	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}

	public String getTaxMonth() {
		return taxMonth;
	}

	public void setTaxMonth(String taxMonth) {
		this.taxMonth = taxMonth;
	}

	public BigDecimal getIncomeLastYearAmt() {
		return incomeLastYearAmt;
	}

	public void setIncomeLastYearAmt(BigDecimal incomeLastYearAmt) {
		this.incomeLastYearAmt = incomeLastYearAmt;
	}

	public BigDecimal getIncomeCurrentYearAmt() {
		return incomeCurrentYearAmt;
	}

	public void setIncomeCurrentYearAmt(BigDecimal incomeCurrentYearAmt) {
		this.incomeCurrentYearAmt = incomeCurrentYearAmt;
	}

	public BigDecimal getDiffIncomeAmt() {
		return diffIncomeAmt;
	}

	public void setDiffIncomeAmt(BigDecimal diffIncomeAmt) {
		this.diffIncomeAmt = diffIncomeAmt;
	}

	public BigDecimal getDiffIncomePnt() {
		return diffIncomePnt;
	}

	public void setDiffIncomePnt(BigDecimal diffIncomePnt) {
		this.diffIncomePnt = diffIncomePnt;
	}

}