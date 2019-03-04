package th.go.excise.ims.ta.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import th.co.baiwa.buckwaframework.common.persistence.entity.BaseEntity;

@Entity
@Table(name = "TA_WORKSHEET_DTL")
public class TaWorksheetDtl extends BaseEntity {

	private static final long serialVersionUID = 6358831230496999116L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TA_WORKSHEET_DTL_GEN")
	@SequenceGenerator(name = "TA_WORKSHEET_DTL_GEN", sequenceName = "TA_WORKSHEET_DTL_SEQ", allocationSize = 1)
	@Column(name = "WORKSHEET_DTL_ID")
	private Long worksheetDtlId;
	@Column(name = "ANALYSIS_NUMBER")
	private String analysisNumber;
	@Column(name = "NEW_REG_ID")
	private String newRegId;
	@Column(name = "COND_MAIN_GRP")
	private String condMainGrp;
	@Column(name = "CENTRAL_SEL_FLAG")
	private String centralSelFlag;
	@Column(name = "CENTRAL_SEL_DATE")
	private LocalDate centralSelDate;
	@Column(name = "CENTRAL_SEL_OFFICE_CODE")
	private String centralSelOfficeCode;
	@Column(name = "SECTOR_SEL_FLAG")
	private String sectorSelFlag;
	@Column(name = "SECTOR_SEL_DATE")
	private LocalDate sectorSelDate;
	@Column(name = "SECTOR_SEL_OFFICE_CODE")
	private String sectorSelOfficeCode;
	@Column(name = "AREA_SEL_FLAG")
	private String areaSelFlag;
	@Column(name = "AREA_SEL_DATE")
	private LocalDate areaSelDate;
	@Column(name = "AREA_SEL_OFFICE_CODE")
	private String areaSelOfficeCode;

	public Long getWorksheetDtlId() {
		return worksheetDtlId;
	}

	public void setWorksheetDtlId(Long worksheetDtlId) {
		this.worksheetDtlId = worksheetDtlId;
	}

	public String getAnalysisNumber() {
		return analysisNumber;
	}

	public void setAnalysisNumber(String analysisNumber) {
		this.analysisNumber = analysisNumber;
	}

	public String getNewRegId() {
		return newRegId;
	}

	public void setNewRegId(String newRegId) {
		this.newRegId = newRegId;
	}

	public String getCondMainGrp() {
		return condMainGrp;
	}

	public void setCondMainGrp(String condMainGrp) {
		this.condMainGrp = condMainGrp;
	}

	public String getCentralSelFlag() {
		return centralSelFlag;
	}

	public void setCentralSelFlag(String centralSelFlag) {
		this.centralSelFlag = centralSelFlag;
	}

	public LocalDate getCentralSelDate() {
		return centralSelDate;
	}

	public void setCentralSelDate(LocalDate centralSelDate) {
		this.centralSelDate = centralSelDate;
	}

	public String getCentralSelOfficeCode() {
		return centralSelOfficeCode;
	}

	public void setCentralSelOfficeCode(String centralSelOfficeCode) {
		this.centralSelOfficeCode = centralSelOfficeCode;
	}

	public String getSectorSelFlag() {
		return sectorSelFlag;
	}

	public void setSectorSelFlag(String sectorSelFlag) {
		this.sectorSelFlag = sectorSelFlag;
	}

	public LocalDate getSectorSelDate() {
		return sectorSelDate;
	}

	public void setSectorSelDate(LocalDate sectorSelDate) {
		this.sectorSelDate = sectorSelDate;
	}

	public String getSectorSelOfficeCode() {
		return sectorSelOfficeCode;
	}

	public void setSectorSelOfficeCode(String sectorSelOfficeCode) {
		this.sectorSelOfficeCode = sectorSelOfficeCode;
	}

	public String getAreaSelFlag() {
		return areaSelFlag;
	}

	public void setAreaSelFlag(String areaSelFlag) {
		this.areaSelFlag = areaSelFlag;
	}

	public LocalDate getAreaSelDate() {
		return areaSelDate;
	}

	public void setAreaSelDate(LocalDate areaSelDate) {
		this.areaSelDate = areaSelDate;
	}

	public String getAreaSelOfficeCode() {
		return areaSelOfficeCode;
	}

	public void setAreaSelOfficeCode(String areaSelOfficeCode) {
		this.areaSelOfficeCode = areaSelOfficeCode;
	}

}
