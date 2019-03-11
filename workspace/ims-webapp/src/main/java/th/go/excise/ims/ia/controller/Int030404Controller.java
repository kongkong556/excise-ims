package th.go.excise.ims.ia.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.common.bean.DataTableAjax;
import th.go.excise.ims.ia.service.Int030404Service;
import th.go.excise.ims.ia.vo.Int030404FormVo;
import th.go.excise.ims.ia.vo.Int030404Vo;

@Controller
@RequestMapping("/api/ia/int03/04/04")
public class Int030404Controller {
	@Autowired
	private Int030404Service int030404Service;
	
	private Logger logger = LoggerFactory.getLogger(Int030404Controller.class);
	
	@PostMapping("/projectEfficiencyList")
	@ResponseBody
	public DataTableAjax<Int030404Vo> systemUnworkingList(@RequestBody Int030404FormVo form) {
		DataTableAjax<Int030404Vo> response = new DataTableAjax<Int030404Vo>();
		List<Int030404Vo> projectEfficiencyList = new ArrayList<Int030404Vo>();

		try {
			projectEfficiencyList = int030404Service.projectEfficiencyList(form);
			response.setData(projectEfficiencyList);

		} catch (Exception e) {
			logger.error("Int030404Controller projectEfficiencyList : ", e);
		}
		return response;
	}
	
}