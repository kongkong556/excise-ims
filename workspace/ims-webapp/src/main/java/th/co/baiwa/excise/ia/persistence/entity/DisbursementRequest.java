package th.co.baiwa.excise.ia.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import th.co.baiwa.buckwaframework.common.persistence.entity.BaseEntity;

/**
 * The persistent class for the IA_DISBURSEMENT_REQUEST database table.
 * 
 */
@Entity
@Table(name = "IA_DISBURSEMENT_REQUEST")
public class DisbursementRequest extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3405632618162697522L;

	@Id
	@SequenceGenerator(name = "IA_DISBURSEMENT_REQUEST_ID_GENERATOR", sequenceName = "IA_DISBURSEMENT_REQUEST_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IA_DISBURSEMENT_REQUEST_ID_GENERATOR")
	private BigDecimal id;

	private String affiliation;

	@Column(name = "AFFILIATION_PAY")
	private String affiliationPay;

	private BigDecimal amount;

	@Column(name = "AMOUNT_PAY")
	private BigDecimal amountPay;

	@Column(name = "BILL_LADING")
	private String billLading;

	@Column(name = "BILL_PAY")
	private String billPay;

	@Column(name = "CREATED_DATE_PAY")
	private Date createdDatePay;

	@Column(name = "\"POSITION\"")
	private String position;

	@Column(name = "POSITION_PAY")
	private String positionPay;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY_PAY")
	private String createdByPay;
	
	@Column(name = "REQUEST_TYPE")
	private String requestType;
	
	
	@Transient
	private String createdDateStr;
	
	@Transient
	private String createdDatePayStr;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getAffiliationPay() {
		return affiliationPay;
	}

	public void setAffiliationPay(String affiliationPay) {
		this.affiliationPay = affiliationPay;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountPay() {
		return amountPay;
	}

	public void setAmountPay(BigDecimal amountPay) {
		this.amountPay = amountPay;
	}

	public String getBillLading() {
		return billLading;
	}

	public void setBillLading(String billLading) {
		this.billLading = billLading;
	}

	public String getBillPay() {
		return billPay;
	}

	public void setBillPay(String billPay) {
		this.billPay = billPay;
	}

	public Date getCreatedDatePay() {
		return createdDatePay;
	}

	public void setCreatedDatePay(Date createdDatePay) {
		this.createdDatePay = createdDatePay;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionPay() {
		return positionPay;
	}

	public void setPositionPay(String positionPay) {
		this.positionPay = positionPay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getCreatedByPay() {
		return createdByPay;
	}

	public void setCreatedByPay(String createdByPay) {
		this.createdByPay = createdByPay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCreatedDatePayStr() {
		return createdDatePayStr;
	}

	public void setCreatedDatePayStr(String createdDatePayStr) {
		this.createdDatePayStr = createdDatePayStr;
	}
	
	
	
	

}