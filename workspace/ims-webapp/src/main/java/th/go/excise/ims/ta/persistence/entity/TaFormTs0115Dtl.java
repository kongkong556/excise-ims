package th.go.excise.ims.ta.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import th.co.baiwa.buckwaframework.common.persistence.entity.BaseEntity;

@Entity
@Table(name = "TA_FORM_TS0115_DTL")
public class TaFormTs0115Dtl extends BaseEntity {

	private static final long serialVersionUID = -6966818938917536700L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TA_FORM_TS0115_DTL_GEN")
	@SequenceGenerator(name = "TA_FORM_TS0115_DTL_GEN", sequenceName = "TA_FORM_TS0115_DTL_SEQ", allocationSize = 1)
	@Column(name = "FORM_TS0115_DTL_ID")
	private Long formTs0115DtlId;
	@Column(name = "OFFICE_CODE")
	private String officeCode;
	@Column(name = "BUDGET_YEAR")
	private String budgetYear;
	@Column(name = "FORM_TS_NUMBER")
	private String formTsNumber;
	@Column(name = "NEW_REG_ID")
	private String newRegId;
	@Column(name = "REC_NO")
	private String recNo;
	@Column(name = "REC_DATE")
	private Date recDate;
	@Column(name = "DUTY_TYPE_TEXT")
	private String dutyTypeText;
	@Column(name = "TAX_AMT")
	private BigDecimal taxAmt;
	@Column(name = "FINE_AMT")
	private BigDecimal fineAmt;
	@Column(name = "EXTRA_AMT")
	private BigDecimal extraAmt;
	@Column(name = "MOI_AMT")
	private BigDecimal moiAmt;
	@Column(name = "SUM_TAX_AMT")
	private BigDecimal sumTaxAmt;

	public Long getFormTs0115DtlId() {
		return formTs0115DtlId;
	}

	public void setFormTs0115DtlId(Long formTs0115DtlId) {
		this.formTs0115DtlId = formTs0115DtlId;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getBudgetYear() {
		return budgetYear;
	}

	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}

	public String getFormTsNumber() {
		return formTsNumber;
	}

	public void setFormTsNumber(String formTsNumber) {
		this.formTsNumber = formTsNumber;
	}

	public String getNewRegId() {
		return newRegId;
	}

	public void setNewRegId(String newRegId) {
		this.newRegId = newRegId;
	}

	public String getRecNo() {
		return recNo;
	}

	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	public String getDutyTypeText() {
		return dutyTypeText;
	}

	public void setDutyTypeText(String dutyTypeText) {
		this.dutyTypeText = dutyTypeText;
	}

	public BigDecimal getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(BigDecimal taxAmt) {
		this.taxAmt = taxAmt;
	}

	public BigDecimal getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(BigDecimal fineAmt) {
		this.fineAmt = fineAmt;
	}

	public BigDecimal getExtraAmt() {
		return extraAmt;
	}

	public void setExtraAmt(BigDecimal extraAmt) {
		this.extraAmt = extraAmt;
	}

	public BigDecimal getMoiAmt() {
		return moiAmt;
	}

	public void setMoiAmt(BigDecimal moiAmt) {
		this.moiAmt = moiAmt;
	}

	public BigDecimal getSumTaxAmt() {
		return sumTaxAmt;
	}

	public void setSumTaxAmt(BigDecimal sumTaxAmt) {
		this.sumTaxAmt = sumTaxAmt;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
