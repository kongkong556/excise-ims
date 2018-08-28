package th.co.baiwa.excise.oa.persistence.entity;

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
@Table(name="OA_PLAN_WS_DTL_TEMP")
public class OaPlanWsDtlTemp extends BaseEntity {
	
	private static final long serialVersionUID = 8658950432183321160L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OA_PLAN_WS_DTL_TEMP_GEN")
	@SequenceGenerator(name = "OA_PLAN_WS_DTL_TEMP_GEN", sequenceName = "OA_PLAN_WS_DTL_TEMP_SEQ", allocationSize = 1)
	@Column(name="WS_DTL_TEMP_ID")
	private Long wsDtlTempId;
	
	@Column(name="WS_HDR_TEMP_ID")
	private Long wsHdrTempId;
	
	@Column(name="MONTH")
	private String month;
	
	@Column(name="YEAR")
	private String year;
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Column(name="TOTAL_AMOUNT")
	private BigDecimal totalAmount;
	
	@Column(name="PERCENTAGE")
	private BigDecimal percentage;
	
	@Column(name="TOTAL_MONTH")
	private Long totalMonth;

	public Long getWsDtlTempId() {
		return wsDtlTempId;
	}

	public void setWsDtlTempId(Long wsDtlTempId) {
		this.wsDtlTempId = wsDtlTempId;
	}

	public Long getWsHdrTempId() {
		return wsHdrTempId;
	}

	public void setWsHdrTempId(Long wsHdrTempId) {
		this.wsHdrTempId = wsHdrTempId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public Long getTotalMonth() {
		return totalMonth;
	}

	public void setTotalMonth(Long totalMonth) {
		this.totalMonth = totalMonth;
	}	
	
	


}