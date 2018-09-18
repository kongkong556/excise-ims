package th.co.baiwa.excise.ia.persistence.entity;

import th.co.baiwa.buckwaframework.common.persistence.entity.BaseEntity;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "IA_STAMP_TYPE")
public class IaTaxReceipt extends BaseEntity {

	private static final long serialVersionUID = -1413517835451948526L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IA_TAX_RECEIPT_GEN")
	@SequenceGenerator(name = "IA_TAX_RECEIPT_GEN", sequenceName = "IA_TAX_RECEIPT_SEQ", allocationSize = 1)

	@Column(name = "TAX_RECIPT_ID")
	private Long taxReciptId;

	@Column(name = "RECEIPT_DATE")
	private String receiptDate;

	@Column(name = "DEPOSIT_DATE")
	private String depositDate;

	@Column(name = "SEND_DATE")
	private String sendDate;

	@Column(name = "INCOME_NAME")
	private String incomeName;

	@Column(name = "RECEIPT_NO")
	private String receiptNo;

	@Column(name = "NET_TAX_AMOUNT")
	private BigDecimal netTaxAmount;

	@Column(name = "NET_LOC_AMOUNT")
	private BigDecimal netLocAmount;

	@Column(name = "LOC_OTH_AMOUNT")
	private BigDecimal locOthAmount;

	@Column(name = "LOC_EXP_AMOUNT")
	private BigDecimal locExpAmount;

	@Column(name = "OLDER_FUND_AMOUNT")
	private BigDecimal olderFundAmount;

	@Column(name = "TPBS_FUND_AMOUNT")
	private BigDecimal tpbsFundAmount;

	@Column(name = "SEND_AMOUNT")
	private BigDecimal sendAmount;

	@Column(name = "STAMP_AMOUNT")
	private BigDecimal stampAmount;

	@Column(name = "CUSTOM_AMOUNT")
	private BigDecimal customAmount;

	public Long getTaxReciptId() {
		return taxReciptId;
	}

	public void setTaxReciptId(Long taxReciptId) {
		this.taxReciptId = taxReciptId;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getIncomeName() {
		return incomeName;
	}

	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BigDecimal getNetTaxAmount() {
		return netTaxAmount;
	}

	public void setNetTaxAmount(BigDecimal netTaxAmount) {
		this.netTaxAmount = netTaxAmount;
	}

	public BigDecimal getNetLocAmount() {
		return netLocAmount;
	}

	public void setNetLocAmount(BigDecimal netLocAmount) {
		this.netLocAmount = netLocAmount;
	}

	public BigDecimal getLocOthAmount() {
		return locOthAmount;
	}

	public void setLocOthAmount(BigDecimal locOthAmount) {
		this.locOthAmount = locOthAmount;
	}

	public BigDecimal getLocExpAmount() {
		return locExpAmount;
	}

	public void setLocExpAmount(BigDecimal locExpAmount) {
		this.locExpAmount = locExpAmount;
	}

	public BigDecimal getOlderFundAmount() {
		return olderFundAmount;
	}

	public void setOlderFundAmount(BigDecimal olderFundAmount) {
		this.olderFundAmount = olderFundAmount;
	}

	public BigDecimal getTpbsFundAmount() {
		return tpbsFundAmount;
	}

	public void setTpbsFundAmount(BigDecimal tpbsFundAmount) {
		this.tpbsFundAmount = tpbsFundAmount;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public BigDecimal getStampAmount() {
		return stampAmount;
	}

	public void setStampAmount(BigDecimal stampAmount) {
		this.stampAmount = stampAmount;
	}

	public BigDecimal getCustomAmount() {
		return customAmount;
	}

	public void setCustomAmount(BigDecimal customAmount) {
		this.customAmount = customAmount;
	}

}
