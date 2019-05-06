
package th.go.excise.ims.ia.persistence.entity;

import java.math.BigDecimal;

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
@Table(name = "IA_AUDIT_WORKING_D1")
public class IaAuditWorkingD1 extends BaseEntity {

	
	private static final long serialVersionUID = -420703161456605623L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IA_AUDIT_WORKING_D1_GEN")
	@SequenceGenerator(name = "IA_AUDIT_WORKING_D1_GEN", sequenceName = "IA_AUDIT_WORKING_D1_SEQ", allocationSize = 1)
	@Column(name = "AUDIT_WORKING_NO")
	private Long auditWorkingNo;
	@Column(name = "AU_OFFICE_CODE")
	private String auOfficeCode;
	@Column(name = "AU_WORKING_MONTH")
	private String auWorkingMonth;
	@Column(name = "AU_RESULT_SEQ")
	private BigDecimal auResultSeq;
	@Column(name = "PERSONAL_ID")
	private String personalId;
	@Column(name = "PERSON_NAME")
	private String personName;
	@Column(name = "PERSON_POS_CODE")
	private String personPosCode;
	@Column(name = "PERSON_POS_NAME")
	private String personPosName;
	@Column(name = "WORK_OUT_DAY")
	private BigDecimal workOutDay;
	@Column(name = "ALLOWANCES_RATE")
	private BigDecimal allowancesRate;
	@Column(name = "ALLOWANCES_CAL_AMT")
	private BigDecimal allowancesCalAmt;
	@Column(name = "ALLOWANCES_PET_AMT")
	private BigDecimal allowancesPetAmt;
	@Column(name = "RESULT_ALLOWANCE_FLAG")
	private String resultAllowanceFlag;
	@Column(name = "RESULT_ACCOM_FEE_FLAG")
	private String resultAccomFeeFlag;
	@Column(name = "RESULT_TRANSPORT_FLAG")
	private String resultTransportFlag;
	@Column(name = "AU_WORKING_REMARKS")
	private String auWorkingRemarks;

	public Long getAuditWorkingNo() {
		return auditWorkingNo;
	}

	public void setAuditWorkingNo(Long auditWorkingNo) {
		this.auditWorkingNo = auditWorkingNo;
	}

	public String getAuOfficeCode() {
		return auOfficeCode;
	}

	public void setAuOfficeCode(String auOfficeCode) {
		this.auOfficeCode = auOfficeCode;
	}

	public String getAuWorkingMonth() {
		return auWorkingMonth;
	}

	public void setAuWorkingMonth(String auWorkingMonth) {
		this.auWorkingMonth = auWorkingMonth;
	}

	public BigDecimal getAuResultSeq() {
		return auResultSeq;
	}

	public void setAuResultSeq(BigDecimal auResultSeq) {
		this.auResultSeq = auResultSeq;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonPosCode() {
		return personPosCode;
	}

	public void setPersonPosCode(String personPosCode) {
		this.personPosCode = personPosCode;
	}

	public String getPersonPosName() {
		return personPosName;
	}

	public void setPersonPosName(String personPosName) {
		this.personPosName = personPosName;
	}

	public BigDecimal getWorkOutDay() {
		return workOutDay;
	}

	public void setWorkOutDay(BigDecimal workOutDay) {
		this.workOutDay = workOutDay;
	}

	public BigDecimal getAllowancesRate() {
		return allowancesRate;
	}

	public void setAllowancesRate(BigDecimal allowancesRate) {
		this.allowancesRate = allowancesRate;
	}

	public BigDecimal getAllowancesCalAmt() {
		return allowancesCalAmt;
	}

	public void setAllowancesCalAmt(BigDecimal allowancesCalAmt) {
		this.allowancesCalAmt = allowancesCalAmt;
	}

	public BigDecimal getAllowancesPetAmt() {
		return allowancesPetAmt;
	}

	public void setAllowancesPetAmt(BigDecimal allowancesPetAmt) {
		this.allowancesPetAmt = allowancesPetAmt;
	}

	public String getResultAllowanceFlag() {
		return resultAllowanceFlag;
	}

	public void setResultAllowanceFlag(String resultAllowanceFlag) {
		this.resultAllowanceFlag = resultAllowanceFlag;
	}

	public String getResultAccomFeeFlag() {
		return resultAccomFeeFlag;
	}

	public void setResultAccomFeeFlag(String resultAccomFeeFlag) {
		this.resultAccomFeeFlag = resultAccomFeeFlag;
	}

	public String getResultTransportFlag() {
		return resultTransportFlag;
	}

	public void setResultTransportFlag(String resultTransportFlag) {
		this.resultTransportFlag = resultTransportFlag;
	}

	public String getAuWorkingRemarks() {
		return auWorkingRemarks;
	}

	public void setAuWorkingRemarks(String auWorkingRemarks) {
		this.auWorkingRemarks = auWorkingRemarks;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}