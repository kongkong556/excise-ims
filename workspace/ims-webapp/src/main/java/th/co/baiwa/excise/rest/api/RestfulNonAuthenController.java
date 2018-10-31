package th.co.baiwa.excise.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.excise.ta.persistence.vo.ResVo;
import th.co.baiwa.excise.ta.service.PlanWorksheetHeaderService;

@Controller
@RequestMapping("api/preferences/restful")
public class RestfulNonAuthenController {

	private static final Logger logger = LoggerFactory.getLogger(RestfulNonAuthenController.class);

	@Autowired
	private PlanWorksheetHeaderService planWorksheetHeaderService;

	@GetMapping("/summaryInvestigate")
	@ResponseBody
	public ResVo summaryInvestigate() {
		logger.info("reportSector");
		ResVo resVo = planWorksheetHeaderService.reportSendLineStatus();
		logger.info("resVo : "+ resVo.toString());
		return resVo;
	}
	@GetMapping("/summaryInvestigateFull")
	@ResponseBody
	public ResVo summaryInvestigateFull() {
		logger.info("reportSector");
		ResVo resVo = planWorksheetHeaderService.summaryInvestigateFull();
		logger.info("resVo : "+ resVo.toString());
		return resVo;
	}
}
