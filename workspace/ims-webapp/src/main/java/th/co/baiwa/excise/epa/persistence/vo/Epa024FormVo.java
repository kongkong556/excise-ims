package th.co.baiwa.excise.epa.persistence.vo;

import th.co.baiwa.buckwaframework.common.bean.DataTableRequest;

public class Epa024FormVo extends DataTableRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1656037470892031340L;
	private String exciseId;
	private String searchFlag;

	public String getExciseId() {
		return exciseId;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setExciseId(String exciseId) {
		this.exciseId = exciseId;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

}
