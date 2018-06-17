package th.go.excise.ims.mockup.domain.ia;

import java.math.BigDecimal;
import java.util.Date;

public class TravelCostWorkSheetHeader {
	private BigDecimal workSheetHeaderId;
	private String workSheetHeaderName;
	private String departmentName;
	private Date startDate;
	private Date endDate;
	private String description;
	private String createdBy;
	private Date createdDatetime;
	private String updateBy;
	private Date updateDatetime;
	public BigDecimal getWorkSheetHeaderId() {
		return workSheetHeaderId;
	}
	public void setWorkSheetHeaderId(BigDecimal workSheetHeaderId) {
		this.workSheetHeaderId = workSheetHeaderId;
	}
	public String getWorkSheetHeaderName() {
		return workSheetHeaderName;
	}
	public void setWorkSheetHeaderName(String workSheetHeaderName) {
		this.workSheetHeaderName = workSheetHeaderName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDatetime() {
		return createdDatetime;
	}
	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

}

