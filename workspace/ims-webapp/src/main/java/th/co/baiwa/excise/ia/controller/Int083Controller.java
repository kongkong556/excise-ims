
package th.co.baiwa.excise.ia.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.common.bean.ResponseDataTable;
import th.co.baiwa.buckwaframework.preferences.persistence.entity.Message;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.excise.domain.DataTableRequest;
import th.co.baiwa.excise.domain.Int0801Vo;
import th.co.baiwa.excise.domain.Int0803Vo;
import th.co.baiwa.excise.domain.RiskFullDataVo;
import th.co.baiwa.excise.ia.persistence.entity.RiskAssExcAreaDtl;
import th.co.baiwa.excise.ia.persistence.entity.RiskAssExcAreaHdr;
import th.co.baiwa.excise.ia.persistence.entity.RiskAssOtherDtl;
import th.co.baiwa.excise.ia.service.RiskAssExcAreaService;
import th.co.baiwa.excise.ta.persistence.vo.Ope041Vo;
import th.co.baiwa.excise.upload.service.UploadFileExciseService;
import th.co.baiwa.excise.utils.BeanUtils;

@Controller
@RequestMapping("api/ia/int083")
public class Int083Controller {

	private Logger logger = LoggerFactory.getLogger(Int083Controller.class);

	private final String WS_SESSION_DATA = "WS_SESSION_DATA_INT08-3";
	
	@Autowired
	private RiskAssExcAreaService riskAssExcAreaService;
	
	@Autowired
	private UploadFileExciseService uploadFileExciseService;
	
	@Autowired
	public Int083Controller(RiskAssExcAreaService riskAssExcAreaService) {
		this.riskAssExcAreaService = riskAssExcAreaService;
	}

	@PostMapping("/addRiskAssExcAreaHdr")
	@ResponseBody
	public Message addRiskAssExcAreaHdr(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr) {
		logger.info("Add QtnReportHeader" + riskAssRiskWsHdr.getRiskHdrName());
		Message message =  null;
		if(BeanUtils.isNotEmpty(riskAssRiskWsHdr.getRiskHdrName())){
			message = riskAssExcAreaService.createRiskAssExcAreaHdrRepository(riskAssRiskWsHdr);
		}
		return message;
	}
	@PostMapping("/findRiskById")
	@ResponseBody
	public RiskAssExcAreaHdr findRiskById(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr) {
		logger.info("findRiskById" + riskAssRiskWsHdr.getRiskHrdId());
		return riskAssExcAreaService.findById(riskAssRiskWsHdr.getRiskHrdId());
	}
	
	@PostMapping("/searchRiskAssExcAreaHdr")
	@ResponseBody
	public ResponseDataTable<RiskAssExcAreaHdr> searchRiskAssExcAreaHdr(DataTableRequest dataTableRequest , RiskAssExcAreaHdr riskAssRiskWsHdr) {
		logger.info("queryQtnReportHeaderByCriteria");
		return riskAssExcAreaService.findByCriteriaForDatatable(riskAssRiskWsHdr, dataTableRequest);
	}
	
	
	@PostMapping("/deleteRiskAssExcAreaHdr")
	@ResponseBody
	public Message deleteRiskAssExcAreaHdr(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr) {
		logger.info("Delete QtnReportHeader" + riskAssRiskWsHdr.getRiskHdrName());
		Message message = riskAssExcAreaService.deleteRiskAssExcAreaHdrRepository(riskAssRiskWsHdr);
		return message;
	}

	@PostMapping("/dataTableWebService1")
	@ResponseBody
	public ResponseDataTable<RiskAssExcAreaDtl> dataTableWebService1(DataTableRequest dataTableRequest,RiskAssExcAreaHdr riskAssExcAreaHdr,HttpServletRequest httpServletRequest) {
		logger.info("dataTableWebService1");
		List<RiskAssExcAreaDtl> riskAssExcAreaDtlList = null;
		httpServletRequest.getSession().setAttribute(WS_SESSION_DATA , null);
		riskAssExcAreaDtlList = riskAssExcAreaService.findByGroupRiskHrdId(riskAssExcAreaHdr.getRiskHrdId());
		if(BeanUtils.isEmpty(riskAssExcAreaDtlList)) {
			riskAssExcAreaDtlList = riskAssExcAreaService.findRiskAssRiskDtlByWebService();
		}
		ResponseDataTable<RiskAssExcAreaDtl> responseDataTable = new ResponseDataTable<RiskAssExcAreaDtl>();
		httpServletRequest.getSession().setAttribute(WS_SESSION_DATA , riskAssExcAreaDtlList);
		responseDataTable.setData(riskAssExcAreaDtlList);
		responseDataTable.setRecordsTotal((int) riskAssExcAreaDtlList.size());
		responseDataTable.setRecordsFiltered((int) riskAssExcAreaDtlList.size());
		return responseDataTable;
	}
	
	
	@PostMapping("/createBudgetYear")
	@ResponseBody
	public Message createBuggetYear(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr) {
		logger.info("Add createBuggetYear" + riskAssRiskWsHdr.getBudgetYear());
		Message message =  null;
		if(BeanUtils.isNotEmpty(riskAssRiskWsHdr.getBudgetYear())){
			message = riskAssExcAreaService.createBuggetYear(riskAssRiskWsHdr);
		}
		return message;
	}
	
	
	@PostMapping("/updateRiskAssExcAreaHdr")
	@ResponseBody
	public Message updateRiskAssExcAreaHdr(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr,HttpServletRequest httpServletRequest) {
		Message message = null;
		logger.info("updateRiskAssExcAreaHdr" + riskAssRiskWsHdr.getRiskHrdId());
		try {
			riskAssExcAreaService.updateRiskAssExcAreaHdr(riskAssRiskWsHdr);
			@SuppressWarnings("unchecked")
			List<RiskAssExcAreaDtl> riskAssRiskWsDtlList = (List<RiskAssExcAreaDtl>) httpServletRequest.getSession().getAttribute(WS_SESSION_DATA);
			for (RiskAssExcAreaDtl riskAssRiskWsDtl : riskAssRiskWsDtlList) {
				riskAssRiskWsDtl.setRiskHrdId(riskAssRiskWsHdr.getRiskHrdId());
			}
			riskAssExcAreaService.updateRiskAssExcAreaDtl(riskAssRiskWsDtlList);
			message = ApplicationCache.getMessage("MSG_00002");
		} catch (Exception e) {
			e.printStackTrace();
			message = ApplicationCache.getMessage("MSG_00003");
		}
		
		
		return message;
	}
	
	
	
	@PostMapping("/saveRiskAssOther")
	@ResponseBody
	public Message saveRiskAssOther(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr) {
		Message message = null;
		logger.info("saveRiskAssOther" + riskAssRiskWsHdr.getRiskHrdId());
		try {
			
			riskAssExcAreaService.updateRiskAssExcAreaHdr(riskAssRiskWsHdr);
			message = ApplicationCache.getMessage("MSG_00002");
		} catch (Exception e) {
			message = ApplicationCache.getMessage("MSG_00003");
		}
		
		
		return message;
	}
	
	@PostMapping("/saveRiskAssDtlOther")
	@ResponseBody
	public Message saveRiskAssDtlOther(@RequestBody Int0803Vo int0803Vo) {
		Message message = null;
		logger.info("saveRiskAssDtlOther");
		try {
			riskAssExcAreaService.updateRiskAssOtherDtl(int0803Vo.getRiskAssExcOtherDtlList());
			message = ApplicationCache.getMessage("MSG_00002");
		} catch (Exception e) {
			e.printStackTrace();
			message = ApplicationCache.getMessage("MSG_00003");
		}
		
		return message;
	}
	
	@PostMapping("/findRiskOtherDtlByRiskHrdId")
	@ResponseBody
	public List<RiskAssOtherDtl> findRiskOtherDtlByRiskHrdId(@RequestBody RiskAssOtherDtl riskAssOtherDtl) {
		logger.info("findRiskOtherDtlByRiskHrdId" + riskAssOtherDtl.getRiskHrdId());
		return riskAssExcAreaService.findByRiskHrdId(riskAssOtherDtl.getRiskHrdId());
	}
	
	@PostMapping("excelINT081")
	@ResponseBody
	public List<RiskAssOtherDtl> excelINT081(@ModelAttribute Ope041Vo mainForm) throws Exception {
		List<RiskAssOtherDtl> excelData = new ArrayList<RiskAssOtherDtl>();
		if (mainForm.getFileExel() != null) {
			RiskAssOtherDtl row = new RiskAssOtherDtl();
			List<String[]> ListfileEx = uploadFileExciseService.readFileExcel(mainForm);
			for (int j = 1; j < ListfileEx.size(); j++) {
				String[] stringArr = ListfileEx.get(j);

				row = new RiskAssOtherDtl();
				for (int i = 0; i < stringArr.length; i++) {
					if (i == 0) {
						row.setRiskOtherDtlId(new Long(i + 1));
					} else if (i == 1) {
						row.setProjectBase(stringArr[i]);
					} else if (i == 2) {
						row.setDepartmentName(stringArr[i]);
					} else if (i == 3) {
						row.setRiskCost(new BigDecimal(stringArr[i]));
					}
				}
				excelData.add(row);
			}
		}
		return excelData;
	}
	
	@PostMapping("/findByBudgetYear")
	@ResponseBody
	public List<RiskAssExcAreaHdr> findByBudgetYear(@RequestBody RiskAssExcAreaHdr riskAssRiskWsHdr) {
		return riskAssExcAreaService.findByBudgetYear(riskAssRiskWsHdr.getBudgetYear());
	}
	
	@PostMapping("/searchFullRiskByBudgetYear")
	@ResponseBody
	public List<RiskFullDataVo> searchFullRiskByBudgetYear(@RequestBody Int0801Vo int0801Vo) {
		return riskAssExcAreaService.searchFullRiskByBudgetYear(int0801Vo.getBudgetYear(), int0801Vo.getRiskHrdNameList());
	}
	
	@PostMapping("/updateRiskPercent")
	@ResponseBody
	public List<RiskAssExcAreaHdr> updateRiskPercent(@RequestBody Int0803Vo  int0803Vo) {
		return riskAssExcAreaService.updatePercent(int0803Vo.getRiskAssExcAreaHdrList());
	}
	
	
	public RiskAssExcAreaService getRiskAssExcAreaService() {
		return riskAssExcAreaService;
	}

	public void setRiskAssExcAreaService(RiskAssExcAreaService riskAssExcAreaService) {
		this.riskAssExcAreaService = riskAssExcAreaService;
	}

	

}
