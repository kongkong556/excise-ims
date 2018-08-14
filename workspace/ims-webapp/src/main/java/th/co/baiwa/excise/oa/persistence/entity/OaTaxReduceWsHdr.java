package th.co.baiwa.excise.oa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import th.co.baiwa.buckwaframework.common.persistence.entity.BaseEntity;


@Entity
@Table(name="OA_TAX_REDUCE_WS_HDR")
public class OaTaxReduceWsHdr extends BaseEntity  {

	private static final long serialVersionUID = 2468961707117236221L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OA_TAX_REDUCE_WS_HDR_GEN")
	@SequenceGenerator(name = "OA_TAX_REDUCE_WS_HDR_GEN", sequenceName = "OA_TAX_REDUCE_WS_HDR_SEQ", allocationSize = 1)
	@Column(name="TAX_REDUCE_WS_HDR_ID")
	private Long taxReduceWsHdrId;
	
	@Column(name="ANALYSIS_ID")
	private Long analysisId;

	@Column(name="END_DATE")
	private String endDate;
	
	@Column(name="EXCISE_ID")
	private String exciseId;

	@Column(name="OPER_PLAN_ID")
	private Long operPlanId;
	
	@Column(name="PRODUCT_TYPE")
	private String productType;
	
	@Column(name="START_DATE")
	private String startDate;
	
	@Column(name="SUB_PRODUCT_TYPE")
	private String subProductType;
	
	@Column(name="TAXATION_ID")
	private String taxationId;

	public Long getTaxReduceWsHdrId() {
		return taxReduceWsHdrId;
	}

	public void setTaxReduceWsHdrId(Long taxReduceWsHdrId) {
		this.taxReduceWsHdrId = taxReduceWsHdrId;
	}

	public Long getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getExciseId() {
		return exciseId;
	}

	public void setExciseId(String exciseId) {
		this.exciseId = exciseId;
	}

	public Long getOperPlanId() {
		return operPlanId;
	}

	public void setOperPlanId(Long operPlanId) {
		this.operPlanId = operPlanId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getTaxationId() {
		return taxationId;
	}

	public void setTaxationId(String taxationId) {
		this.taxationId = taxationId;
	}
	
	

}