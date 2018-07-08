package th.co.baiwa.excise.ta.persistence.entity;

import java.math.BigDecimal;

import th.co.baiwa.excise.domain.BaseEntity;

public class TaxAuditResWsDtl extends BaseEntity {
	private BigDecimal taTaxAuditResWsDtlId;
	private BigDecimal taTaxAudResWsHeaderId;
	private String month;
	private String taTaxAudResWsDtlOrd;
	private String value;
	private String price;
	private String cost;
	private String taxPercentage;
	private String fineTax;
	private String fine;
	private String extraMoney;
	private String localTax;

	public BigDecimal getTaTaxAuditResWsDtlId() {
		return taTaxAuditResWsDtlId;
	}

	public void setTaTaxAuditResWsDtlId(BigDecimal taTaxAuditResWsDtlId) {
		this.taTaxAuditResWsDtlId = taTaxAuditResWsDtlId;
	}

	public BigDecimal getTaTaxAudResWsHeaderId() {
		return taTaxAudResWsHeaderId;
	}

	public void setTaTaxAudResWsHeaderId(BigDecimal taTaxAudResWsHeaderId) {
		this.taTaxAudResWsHeaderId = taTaxAudResWsHeaderId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTaTaxAudResWsDtlOrd() {
		return taTaxAudResWsDtlOrd;
	}

	public void setTaTaxAudResWsDtlOrd(String taTaxAudResWsDtlOrd) {
		this.taTaxAudResWsDtlOrd = taTaxAudResWsDtlOrd;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(String taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public String getFineTax() {
		return fineTax;
	}

	public void setFineTax(String fineTax) {
		this.fineTax = fineTax;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public String getExtraMoney() {
		return extraMoney;
	}

	public void setExtraMoney(String extraMoney) {
		this.extraMoney = extraMoney;
	}

	public String getLocalTax() {
		return localTax;
	}

	public void setLocalTax(String localTax) {
		this.localTax = localTax;
	}
}
