package th.go.excise.ims.ta.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.common.constant.CommonConstants.FLAG;
import th.co.baiwa.buckwaframework.preferences.constant.ParameterConstants.PARAM_GROUP;
import th.co.baiwa.buckwaframework.security.util.UserLoginUtils;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.buckwaframework.support.domain.ParamInfo;
import th.go.excise.ims.common.constant.ProjectConstants.TA_MAS_COND_MAIN_TYPE;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainDtl;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainHdr;
import th.go.excise.ims.ta.persistence.repository.TaMasCondMainDtlRepository;
import th.go.excise.ims.ta.persistence.repository.TaMasCondMainHdrRepository;
import th.go.excise.ims.ta.vo.ConditionMessageVo;
import th.go.excise.ims.ta.vo.MasterConditionMainHdrDtlVo;

@Service
public class MasterConditionMainService {

	@Autowired
	private TaMasCondMainHdrRepository taMasCondMainHdrRepository;

	@Autowired
	private TaMasCondMainDtlRepository taMasCondMainDtlRepository;
	
	@Autowired
	private MasterConditionSequenceService masterConditionSequenceService;
	
	public void insertCondMainHdr(TaMasCondMainHdr form) {
		TaMasCondMainHdr hdr = new TaMasCondMainHdr();
		hdr.setOfficeCode(UserLoginUtils.getCurrentUserBean().getOfficeCode());
		hdr.setBudgetYear(form.getBudgetYear());
		hdr.setCondNumber(masterConditionSequenceService.getConditionNumber(UserLoginUtils.getCurrentUserBean().getOfficeCode(), form.getBudgetYear()));
		hdr.setCondGroupDesc(form.getCondGroupDesc());
		hdr.setMonthNum(form.getMonthNum());
		hdr.setCondGroupNum(form.getCondGroupNum());
		hdr.setNewFacFlag(form.getNewFacFlag());
		taMasCondMainHdrRepository.save(hdr);
	}
	
	public void updateCondMainHdr(TaMasCondMainHdr form) {
		TaMasCondMainHdr hdr = taMasCondMainHdrRepository.findByCondNumber(form.getCondNumber());
		hdr.setOfficeCode(UserLoginUtils.getCurrentUserBean().getOfficeCode());
		hdr.setBudgetYear(form.getBudgetYear());
		hdr.setCondGroupDesc(form.getCondGroupDesc());
		hdr.setMonthNum(form.getMonthNum());
		hdr.setCondGroupNum(form.getCondGroupNum());
		hdr.setNewFacFlag(form.getNewFacFlag());
		taMasCondMainHdrRepository.save(hdr);
	}

	public void insertCondMainDtl(MasterConditionMainHdrDtlVo formVo) {
		TaMasCondMainDtl dtl = null;
		List<TaMasCondMainDtl> dtlList = new ArrayList<>();
		TaMasCondMainHdr header = formVo.getHeader();
		if (header.getBudgetYear() != null) {
			for (TaMasCondMainDtl obj : formVo.getDetail()) {
				dtl = new TaMasCondMainDtl();
				dtl.setBudgetYear(header.getBudgetYear());
				dtl.setCondGroup(obj.getCondGroup());
				dtl.setTaxMonthStart(obj.getTaxMonthStart());
				dtl.setTaxMonthEnd(obj.getTaxMonthEnd());
				dtl.setRangeStart(obj.getRangeStart());
				dtl.setRangeEnd(obj.getRangeEnd());
				dtl.setRiskLevel(obj.getRiskLevel());
				dtl.setCondType("T");
				dtlList.add(dtl);
			}
			dtl = new TaMasCondMainDtl();
			dtl.setBudgetYear(header.getBudgetYear());
			dtl.setCondGroup(String.valueOf(formVo.getDetail().size() + 1));
			dtl.setCondType(TA_MAS_COND_MAIN_TYPE.OTHER);
			dtlList.add(dtl);
			taMasCondMainDtlRepository.saveAll(dtlList);
		}
	}

	public void updateCondMainDtl(MasterConditionMainHdrDtlVo formVo) {
		TaMasCondMainDtl dtl = null;
		List<TaMasCondMainDtl> dtlList = new ArrayList<>();
		TaMasCondMainHdr header = formVo.getHeader();
		if (header.getBudgetYear() != null) {
			List<TaMasCondMainDtl> list = taMasCondMainDtlRepository.findByBudgetYearAndCondNumberAndCondType(formVo.getHeader().getBudgetYear(), formVo.getHeader().getCondNumber(), TA_MAS_COND_MAIN_TYPE.TAX);
//			List<TaMasCondDtlTax> listY = taMasCondDtlTaxRepository.findByBudgetYearAndIsDeleted(formVo.getHeader().getBudgetYear(), FLAG.Y_FLAG);

			Collections.sort(list, new Comparator<TaMasCondMainDtl>() {
				public int compare(TaMasCondMainDtl dtlTax, TaMasCondMainDtl dtlTax2) {
					int condGroup = dtlTax.getCondGroup().compareTo(dtlTax2.getCondGroup());
					if (condGroup == 0) {
						return condGroup;
					}
					return  Long.valueOf(dtlTax.getCondGroup()) > Long.valueOf(dtlTax2.getCondGroup()) ? 1 : Long.valueOf(dtlTax.getCondGroup()) < Long.valueOf(dtlTax2.getCondGroup()) ? -1 : 0;
				}
			});

			if (list.size() - formVo.getDetail().size() > 0) {
				boolean checkDelete = true;
				for (TaMasCondMainDtl obj : list) {
					checkDelete = true;
					for (TaMasCondMainDtl ob : formVo.getDetail()) {
						if (ob.getMasCondMainDtlId() == obj.getMasCondMainDtlId()) {
							checkDelete = false;
							break;
						}
					}
					if (checkDelete) {
						dtl = taMasCondMainDtlRepository.findById(obj.getMasCondMainDtlId()).get();
						dtl.setIsDeleted(FLAG.Y_FLAG);
						dtlList.add(dtl);
					}
				}
				taMasCondMainDtlRepository.saveAll(dtlList);
			} 
//			else if (list.size() - formVo.getDetail().size() < 0) {
//				for (TaMasCondDtlTax obj : listY) {
//					dtl = taMasCondDtlTaxRepository.findById(obj.getCondDtlTaxId()).get(); 
//					dtl.setIsDeleted(FLAG.N_FLAG);
//
//					dtlList.add(dtl);
//				}
//				taMasCondDtlTaxRepository.saveAll(dtlList);
//			}
			
			dtlList = new ArrayList<>();
			for (TaMasCondMainDtl obj : formVo.getDetail()) {
				if (obj.getMasCondMainDtlId() == null) {
					dtl = new TaMasCondMainDtl();
					dtl.setBudgetYear(header.getBudgetYear());
					dtl.setCondGroup(obj.getCondGroup());
					dtl.setTaxMonthStart(obj.getTaxMonthStart());
					dtl.setTaxMonthEnd(obj.getTaxMonthEnd());
					dtl.setRangeStart(obj.getRangeStart());
					dtl.setRangeEnd(obj.getRangeEnd());
					dtl.setRiskLevel(obj.getRiskLevel());
					dtl.setCondType(TA_MAS_COND_MAIN_TYPE.TAX);

				} else {
					dtl = taMasCondMainDtlRepository.findById(obj.getMasCondMainDtlId()).get();
					dtl.setBudgetYear(header.getBudgetYear());
					dtl.setCondGroup(obj.getCondGroup());
					dtl.setTaxMonthStart(obj.getTaxMonthStart());
					dtl.setTaxMonthEnd(obj.getTaxMonthEnd());
					dtl.setRangeStart(obj.getRangeStart());
					dtl.setRangeEnd(obj.getRangeEnd());
					dtl.setRiskLevel(obj.getRiskLevel());
				}

				dtlList.add(dtl);

			}
			dtl = taMasCondMainDtlRepository.findByBudgetYearAndCondNumberAndCondType(formVo.getHeader().getBudgetYear(), dtl.getCondNumber(), TA_MAS_COND_MAIN_TYPE.OTHER).get(0);
			dtl.setCondGroup(String.valueOf(formVo.getDetail().size() + 1));
			dtlList.add(dtl);
			taMasCondMainDtlRepository.saveAll(dtlList);
		}
	}

	public TaMasCondMainHdr findHdr(TaMasCondMainHdr hdr) {
		TaMasCondMainHdr budgetYear = new TaMasCondMainHdr();
		budgetYear = taMasCondMainHdrRepository.findByBudgetYearAndCondNumber(hdr.getBudgetYear(), hdr.getCondNumber());
		return budgetYear;
	}
	
	public List<TaMasCondMainHdr> findHdrAll(TaMasCondMainHdr hdr) {
		List<TaMasCondMainHdr> budgetYear = new ArrayList<>();
		budgetYear = taMasCondMainHdrRepository.findByBudgetYear(hdr.getBudgetYear());
		return budgetYear;
	}

	public List<TaMasCondMainDtl> findDtl(TaMasCondMainDtl dtl) {
		List<TaMasCondMainDtl> budgetYear = new ArrayList<TaMasCondMainDtl>();
		budgetYear = taMasCondMainDtlRepository.findByBudgetYearAndCondNumberAndCondType(dtl.getBudgetYear(), dtl.getCondNumber(), TA_MAS_COND_MAIN_TYPE.TAX);
		return budgetYear;
	}

	public List<TaMasCondMainHdr> findAllHdr() {
		List<TaMasCondMainHdr> list = taMasCondMainHdrRepository.findAll();
		return list;
	}

	public ConditionMessageVo conditionMessage() {
		ConditionMessageVo msgVo = new ConditionMessageVo();
		msgVo.setMsgMonth1(ApplicationCache.getParamInfoByCode(PARAM_GROUP.TA_MAS_COND_MAIN_DESC, "MONTH1").getValue1());
		msgVo.setMsgMonth2(ApplicationCache.getParamInfoByCode(PARAM_GROUP.TA_MAS_COND_MAIN_DESC, "MONTH2").getValue1());
		msgVo.setMsgTax1(ApplicationCache.getParamInfoByCode(PARAM_GROUP.TA_MAS_COND_MAIN_DESC, "TAX1").getValue1());
		msgVo.setMsgTax2(ApplicationCache.getParamInfoByCode(PARAM_GROUP.TA_MAS_COND_MAIN_DESC, "TAX2").getValue1());
		msgVo.setMsgTax3(ApplicationCache.getParamInfoByCode(PARAM_GROUP.TA_MAS_COND_MAIN_DESC, "TAX3").getValue1());
		return msgVo;
	}
	
	public List<ParamInfo> getMainCondRange() {
		List<ParamInfo> list = ApplicationCache.getParamInfoListByGroupCode("TA_MAIN_COND_RANGE");
		return list;
	}

}
