package th.co.baiwa.excise.ia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.preferences.persistence.entity.Lov;
import th.co.baiwa.buckwaframework.preferences.persistence.repository.LovRepository;
import th.co.baiwa.excise.domain.LabelValueBean;
import th.co.baiwa.excise.ia.persistence.dao.HeaderDetailDao;
import th.co.baiwa.excise.ia.persistence.vo.Int09212FormVo;

@Service
public class HeaderDetailService {

	@Autowired
	private HeaderDetailDao headerDetailDao;

	@Autowired
	private LovRepository lovRepository;

	private static final String TYPE_SECTOR_VALUE = "SECTOR_VALUE";

	public List<LabelValueBean> dropdownHeader(){
		
		List<LabelValueBean> lvList = new ArrayList<>();
		List<Lov> list = lovRepository.findByTypeAndLovIdMasterIsNull(TYPE_SECTOR_VALUE);
		
		for (Lov lov : list) {			
			LabelValueBean lv= new LabelValueBean(lov.getValue1(), Long.toString(lov.getLovId()));
			lvList.add(lv);
		}		
		return lvList;
	}

	public List<LabelValueBean> dropdownListType(Int09212FormVo formVo) {
		Lov lovOne = lovRepository.findOne(Long.valueOf(formVo.getLovIdMaster()));
		return headerDetailDao.drodownList(lovOne.getType(), formVo.getLovIdMaster());
	}

}
