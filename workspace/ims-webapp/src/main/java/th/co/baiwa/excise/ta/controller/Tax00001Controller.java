package th.co.baiwa.excise.ta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.common.bean.ResponseDataTable;
import th.co.baiwa.buckwaframework.preferences.persistence.entity.Message;
import th.co.baiwa.excise.domain.datatable.DataTableRequest;
import th.co.baiwa.excise.ta.persistence.entity.PlanTaxAudit;
import th.co.baiwa.excise.ta.persistence.vo.PlanTaxAuditVo;
import th.co.baiwa.excise.ta.service.PlanTaxAuditService;

@Controller
@RequestMapping("api/tax00001")
public class Tax00001Controller {
	private Logger logger = LoggerFactory.getLogger(Tax00001Controller.class);

	@Autowired
	private PlanTaxAuditService planTaxAuditService;

	@PostMapping("/createPlanTaxAudit")
	@ResponseBody
	public Message createPlanTaxAudit(@RequestBody PlanTaxAuditVo planTaxAuditVo) {
		logger.info("createPlanTaxAudit");
		return planTaxAuditService.createPlanTaxAuditRepository(planTaxAuditVo.getPlanTaxAudit(), planTaxAuditVo.getPlanCriteriaList());
	}

	@PostMapping("/editCriteria")
	@ResponseBody
	public Message editCriteria(@RequestBody PlanTaxAuditVo planTaxAuditVo) {
		logger.info("editCriteria");
		return planTaxAuditService.editCriteria(planTaxAuditVo.getPlanCriteriaList());
	}

	@PostMapping("/findPlanTaxAuditByBudgetYear")
	@ResponseBody
	public List<PlanTaxAudit> findPlanTaxAuditByBudgetYear(@RequestBody PlanTaxAuditVo planTaxAuditVo) {
		logger.info("findPlanTaxAuditByBudgetYear");
		return planTaxAuditService.findByBudgetYearOrderByTaPlanTaxAuditId(planTaxAuditVo.getBudgetYear());
	}
	@PostMapping("/deltePlanTaxAuditById")
	@ResponseBody
	public Message deltePlanTaxAuditById(@RequestBody PlanTaxAudit planTaxAudit) {
		logger.info("findPlanTaxAuditByBudgetYear");
		return planTaxAuditService.deltePlanTaxAuditById(planTaxAudit);
	}
	
	@PostMapping("/findPlanTaxAuditByBudgetYearForDataTable")
	@ResponseBody
	public ResponseDataTable<PlanTaxAudit> findPlanTaxAuditByBudgetYearForDataTable(DataTableRequest dataTableRequest,PlanTaxAuditVo planTaxAuditVo) {
		logger.info("findPlanTaxAuditByBudgetYearForDataTable");
		PlanTaxAudit plan = new PlanTaxAudit();
		plan.setBudgetYear(planTaxAuditVo.getBudgetYear());
		return planTaxAuditService.findByBudgetYearOrderByTaPlanTaxAuditIdForDataTable(plan , dataTableRequest);
	}
	
	@PostMapping("/findPlanTaxAuditAndPlanCriteriaListByTaPlanTaxAuditId")
	@ResponseBody
	public PlanTaxAuditVo findPlanTaxAuditAndPlanCriteriaListByTaPlanTaxAuditId(PlanTaxAudit planTaxAudit) {
		logger.info("findPlanTaxAuditAndPlanCriteriaListByTaPlanTaxAuditId");
		return planTaxAuditService.findPlanTaxAuditAndPlanCriteriaListByTaPlanTaxAuditId(planTaxAudit);
	}
	
	
	@PostMapping("/syncDataForPlanDetail")
	@ResponseBody
	public void syncDataForPlanDetail(@RequestBody PlanTaxAuditVo planTaxAuditVo) {
		logger.info("syncDataForPlanDetail syncDataForPlanDetail : {}" , planTaxAuditVo.getAnalysNumber() );
		planTaxAuditService.createNewPlanWorkSheetHeaderByAnalysNumber(planTaxAuditVo.getAnalysNumber());
	}
	
	

}