package th.co.baiwa.excise.ta.persistence.vo;

import th.co.baiwa.excise.domain.datatable.DataTableRequest;

public class Ope0451FormVo extends DataTableRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3729650633950935894L;
	private String startDate;
	private String endDate;
	private String exciseId;
	private String searchFlag;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

}
