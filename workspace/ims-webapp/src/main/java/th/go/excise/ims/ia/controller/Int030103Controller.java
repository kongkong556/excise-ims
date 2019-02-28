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
import th.co.baiwa.buckwaframework.common.bean.ResponseData;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_STATUS;
import th.go.excise.ims.ia.persistence.entity.IaRiskFactorsConfigAll;
import th.go.excise.ims.ia.service.Int030103Service;
import th.go.excise.ims.ia.vo.Int0301FormVo;
import th.go.excise.ims.ia.vo.Int0301Vo;

@Controller
@RequestMapping("/api/ia/int03/01/03")
public class Int030103Controller {
	
	private Logger logger = LoggerFactory.getLogger(Int030103Controller.class);

	@Autowired
	private Int030103Service int030103Service;
	
	@PostMapping("/listConfigAll")
	@ResponseBody
	public DataTableAjax<IaRiskFactorsConfigAll> listConfigAll(@RequestBody IaRiskFactorsConfigAll form) {
		DataTableAjax<IaRiskFactorsConfigAll> response = new DataTableAjax<IaRiskFactorsConfigAll>();
		List<IaRiskFactorsConfigAll> iaRiskFactorsConfigAll = new ArrayList<IaRiskFactorsConfigAll>();
		try {	
			iaRiskFactorsConfigAll = int030103Service.listConfigAll(form);
			response.setData(iaRiskFactorsConfigAll);
		} catch (Exception e) {
			logger.error("Int030103Controller : " , e);
		}
		return response;
	}
	
	
	@PostMapping("/updatePercent")
	@ResponseBody
	public ResponseData<String> updatePercent(@RequestBody Int0301FormVo form) {
		ResponseData<String> response = new ResponseData<String>();
		try {	
			int030103Service.updatePercent(form);
			response.setData("SUCCESS");
			response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);

		} catch (Exception e) {
			logger.error("Int030103Controller updatePercent : ", e);
			response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	

	
	
}
