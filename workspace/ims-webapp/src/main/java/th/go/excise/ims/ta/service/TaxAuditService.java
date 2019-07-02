package th.go.excise.ims.ta.service;

import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.common.bean.DataTableAjax;
import th.co.baiwa.buckwaframework.common.util.ConvertDateUtils;
import th.co.baiwa.buckwaframework.preferences.constant.ParameterConstants;
import th.co.baiwa.buckwaframework.preferences.constant.ParameterConstants.PARAM_GROUP;
import th.co.baiwa.buckwaframework.security.util.UserLoginUtils;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.buckwaframework.support.domain.ParamInfo;
import th.go.excise.ims.common.util.ExciseUtils;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetDtl;
import th.go.excise.ims.ta.persistence.repository.TaPlanWorksheetDtlRepository;
import th.go.excise.ims.ta.persistence.repository.TaWsReg4000Repository;
import th.go.excise.ims.ta.vo.AuditCalendarCheckboxVo;
import th.go.excise.ims.ta.vo.AuditCalendarCriteriaFormVo;
import th.go.excise.ims.ta.vo.AuditStepFormVo;
import th.go.excise.ims.ta.vo.OutsidePlanFormVo;
import th.go.excise.ims.ta.vo.OutsidePlanVo;
import th.go.excise.ims.ta.vo.PlanWorksheetDtlVo;
import th.go.excise.ims.ta.vo.TaxAuditDetailFormVo;
import th.go.excise.ims.ta.vo.TaxAuditDetailVo;
import th.go.excise.ims.ta.vo.WsRegfri4000FormVo;
import th.go.excise.ims.ws.client.pcc.common.exception.PccRestfulException;
import th.go.excise.ims.ws.client.pcc.regfri4000.model.RegMaster60;
import th.go.excise.ims.ws.client.pcc.regfri4000.model.RequestData;
import th.go.excise.ims.ws.client.pcc.regfri4000.service.RegFri4000Service;

@Service
public class TaxAuditService {

	private static final Logger logger = LoggerFactory.getLogger(TaxAuditService.class);

	@Autowired
	private TaPlanWorksheetDtlRepository taPlanWorksheetDtlRepository;

	@Autowired
	private TaWsReg4000Repository taWsReg4000Repository;
	
	@Autowired
	private RegFri4000Service regFri4000Service;

	public DataTableAjax<OutsidePlanVo> outsidePlan(OutsidePlanFormVo formVo) {

		if (StringUtils.isNotEmpty(formVo.getOfficeCode())) {
			if (ExciseUtils.isCentral(formVo.getOfficeCode())) {
				formVo.setOfficeCode("%");
			}else if (ExciseUtils.isSector(formVo.getOfficeCode())) {
				formVo.setOfficeCode(formVo.getOfficeCode().substring(0, 2) +"%");
			}else {
				formVo.setOfficeCode(formVo.getOfficeCode());
			}
		}else {
			formVo.setOfficeCode(UserLoginUtils.getCurrentUserBean().getOfficeCode());
			String whereOfficeCode = ExciseUtils.whereInLocalOfficeCode(UserLoginUtils.getCurrentUserBean().getOfficeCode());
			formVo.setOfficeCode(whereOfficeCode);
		}

		DataTableAjax<OutsidePlanVo> dataTableAjax = new DataTableAjax<>();
		dataTableAjax.setData(taWsReg4000Repository.outsidePlan(formVo));
		dataTableAjax.setDraw(formVo.getDraw() + 1);
		int count = taWsReg4000Repository.countOutsidePlan(formVo).intValue();
		dataTableAjax.setRecordsFiltered(count);
		dataTableAjax.setRecordsTotal(count);

		return dataTableAjax;
	}

	public List<ParamInfo> getAuditType(AuditCalendarCheckboxVo form) {
		List<ParamInfo> auditType = new ArrayList<>();
		auditType = ApplicationCache.getParamInfoListByGroupCode(ParameterConstants.PARAM_GROUP.TA_AUDIT_TYPE);
		return auditType;
	}

	public List<ParamInfo> getAuditStatus(AuditCalendarCheckboxVo form) {
		List<ParamInfo> auditStatus = new ArrayList<>();
		auditStatus = ApplicationCache.getParamInfoListByGroupCode(ParameterConstants.PARAM_GROUP.TA_AUDIT_STATUS);
		return auditStatus;
	}

	public List<PlanWorksheetDtlVo> getPlanWsDtl(AuditCalendarCriteriaFormVo formVo) {
		List<PlanWorksheetDtlVo> planWsDtl = new ArrayList<>();
		planWsDtl = taPlanWorksheetDtlRepository.findByCriteria(formVo);
		return planWsDtl;
	}

	public WsRegfri4000FormVo getOperatorDetails(String newRegId) throws Exception {
		logger.info("getOperatorDetails newRegId={}", newRegId);
		
		RequestData requestData = new RequestData();
		requestData.setType("2");
		requestData.setNid("");
		requestData.setNewregId(newRegId);
		requestData.setHomeOfficeId("");
		requestData.setActive("1");
		requestData.setPageNo("1");
		requestData.setDataPerPage("1");
		
		WsRegfri4000FormVo formVo = null;
		try {
			List<RegMaster60> regMaster60List = regFri4000Service.execute(requestData).getRegMaster60List();
			formVo = new WsRegfri4000FormVo();
			RegMaster60 regMaster60 = null;
			if (regMaster60List != null && regMaster60List.size() > 0) {
				regMaster60 = regMaster60List.get(0);
				BeanUtils.copyProperties(formVo, regMaster60);
				formVo.setNewRegId(regMaster60.getNewregId());
				formVo.setCustomerAddress(ExciseUtils.buildCusAddress(regMaster60));
				formVo.setFacAddress(ExciseUtils.buildFacAddress(regMaster60));
				formVo.setFactoryType(ExciseUtils.getFactoryType(formVo.getNewRegId()));
				if (StringUtils.isNotEmpty(formVo.getFactoryType())) {
					formVo.setFactoryTypeText(ApplicationCache.getParamInfoByCode(PARAM_GROUP.EXCISE_FACTORY_TYPE, formVo.getFactoryType()).getValue2());
				}
			}
		} catch (PccRestfulException e) {
			logger.warn("Now Found in WsRegfri4000");
			formVo = taWsReg4000Repository.findByNewRegId(newRegId);
			if (formVo == null) {
				throw new PccRestfulException("NewRegId=" + newRegId + " Not Found");
			}
		}
		String secDesc = ApplicationCache.getExciseDepartment(formVo.getOffcode().substring(0, 2) + "0000").getDeptShortName();
		String areaDesc = ApplicationCache.getExciseDepartment(formVo.getOffcode().substring(0, 4) + "00").getDeptShortName();
		formVo.setSecDesc(secDesc);
		formVo.setAreaDesc(areaDesc);
		return formVo;
	}
	
	public TaxAuditDetailVo getOperatorDetailsByAuditPlanCode(TaxAuditDetailFormVo formVo) {
		logger.info("getOperatorDetailsByAuditPlanCode auditPlanCode={}", formVo.getAuditPlanCode());
		
		TaPlanWorksheetDtl planDtl = taPlanWorksheetDtlRepository.findByAuditPlanCode(formVo.getAuditPlanCode());
		
		TaxAuditDetailVo vo = new TaxAuditDetailVo();
		vo.setOfficeCode(planDtl.getOfficeCode());
		vo.setBudgetYear(planDtl.getBudgetYear());
		vo.setPlanNumber(planDtl.getPlanNumber());
		vo.setNewRegId(planDtl.getNewRegId());
		vo.setSystemType(planDtl.getSystemType());
		vo.setPlanType(planDtl.getPlanType());
		vo.setAuditStatus(planDtl.getAuditStatus());
		vo.setAuditType(planDtl.getAuditType());
		vo.setAuditTypeDesc(planDtl.getAuditType() != null ? ApplicationCache.getParamInfoByCode(PARAM_GROUP.TA_AUDIT_TYPE, planDtl.getAuditType()).getValue1() : "");
		vo.setAuditStartDate(planDtl.getAuditStartDate() != null ? ThaiBuddhistDate.from(planDtl.getAuditStartDate()).format(DateTimeFormatter.ofPattern(ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH)) : "");
		vo.setAuditEndDate(planDtl.getAuditEndDate() != null ? ThaiBuddhistDate.from(planDtl.getAuditEndDate()).format(DateTimeFormatter.ofPattern(ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH)) : "");
		vo.setAuditPlanCode(planDtl.getAuditPlanCode());
		vo.setAuSubdeptCode(planDtl.getAuSubdeptCode());
		vo.setAuJobResp(planDtl.getAuJobResp());
		
		try {
			vo.setWsRegfri4000Vo(getOperatorDetails(planDtl.getNewRegId()));
		} catch (Exception e) {
			logger.warn(e.getMessage());
			vo.setWsRegfri4000Vo(new WsRegfri4000FormVo());
		}
		
		return vo;
	}

	public void savePlanWsDtl(AuditStepFormVo formVo) {
		logger.info("savePlanWsDtl auditPlanCode={}", formVo.getAuditPlanCode());
		
		TaPlanWorksheetDtl planDtl = taPlanWorksheetDtlRepository.findByAuditPlanCode(formVo.getAuditPlanCode());
		planDtl.setAuditType(formVo.getAuditType());
		planDtl.setAuditStartDate(ConvertDateUtils.parseStringToLocalDate(formVo.getAuditStartDate(), ConvertDateUtils.DD_MM_YYYY));
		planDtl.setAuditEndDate(ConvertDateUtils.parseStringToLocalDate(formVo.getAuditEndDate(), ConvertDateUtils.DD_MM_YYYY));
		
		taPlanWorksheetDtlRepository.save(planDtl);
	}
	
	public List<ParamInfo> getRegStatus() {
		logger.info("getRegStatus");
		List<ParamInfo> regStatusList = ApplicationCache.getParamInfoListByGroupCode(PARAM_GROUP.TA_REG_STATUS);
		return regStatusList;
	}
}
