package th.go.excise.ims.ia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.common.bean.DataTableAjax;
import th.go.excise.ims.ia.persistence.repository.jdbc.Int1102JdbcRepository;
import th.go.excise.ims.ia.vo.Int0301FormVo;
import th.go.excise.ims.ia.vo.Int1102FormVo;
import th.go.excise.ims.ia.vo.Int1102Vo;
import th.go.excise.ims.ia.vo.Int11Vo;

@Service
public class Int1102Service {
	
	@Autowired
	private Int1102JdbcRepository int1102JdbcRepository;
	
	public DataTableAjax<Int1102Vo> list(Int1102FormVo form) {
		List<Int1102Vo> dataList = int1102JdbcRepository.getData(form);
		
		DataTableAjax<Int1102Vo> dataTableAjax = new DataTableAjax<Int1102Vo>();
		dataTableAjax.setData(dataList);
		return dataTableAjax;
	}
	
	public DataTableAjax<Int11Vo> listConFol(Int1102FormVo form) {
		List<Int11Vo> dataList = int1102JdbcRepository.getDataConFol(form);		
		DataTableAjax<Int11Vo> dataTableAjax = new DataTableAjax<Int11Vo>();
		dataTableAjax.setData(dataList);
		return dataTableAjax;
	}
	
	public void updateCheckStatus(Int1102FormVo form) {
		int1102JdbcRepository.updateCheckStatus(form);
	}

	
	
	
	
}
