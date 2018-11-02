package th.co.baiwa.excise.epa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import th.co.baiwa.excise.constant.ExciseConstants;
import th.co.baiwa.excise.domain.datatable.DataTableAjax;
import th.co.baiwa.excise.epa.persistence.vo.Epa021FormVo;
import th.co.baiwa.excise.epa.persistence.vo.Epa021Vo;

@Service
public class Epa021Service {

	public DataTableAjax<Epa021Vo> search(Epa021FormVo epa021FormVo) {
		DataTableAjax<Epa021Vo> dataTableAjax = new DataTableAjax<Epa021Vo>();
		List<Epa021Vo> list = new ArrayList<>();
		
		if (ExciseConstants.SEARCH_FLAG.TRUE.equalsIgnoreCase(epa021FormVo.getSearchFlag())) {
			
			for (int i = 0; i < 4; i++) {
				Epa021Vo vo = new Epa021Vo();
				vo.setExciseName("name");
				vo.setDestination("destination");
				vo.setDateDestination("date destination");
				list.add(vo);
			}
			
//			dataTableAjax.setDraw(epa021FormVo.getDraw() + 1);
//			dataTableAjax.setRecordsTotal(count);
//			dataTableAjax.setRecordsFiltered(count);
			dataTableAjax.setData(list);
		}
		
		return dataTableAjax;
	}

}
