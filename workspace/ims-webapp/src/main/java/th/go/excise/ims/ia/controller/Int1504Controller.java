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
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_MESSAGE;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_STATUS;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.go.excise.ims.ia.service.Int1504Service;
import th.go.excise.ims.ia.vo.ExciseOrgGfmisVo;
import th.go.excise.ims.ia.vo.Int1504FormVo;
import th.go.excise.ims.preferences.persistence.entity.ExciseOrgGfmis;

@Controller
@RequestMapping("/api/ia/int15/04")
public class Int1504Controller {
	
	private Logger logger = LoggerFactory.getLogger(Int1504Controller.class);
	
	@Autowired
	private Int1504Service int1504Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<ExciseOrgGfmis> listData() {
		DataTableAjax<ExciseOrgGfmis> response = new DataTableAjax<ExciseOrgGfmis>();
		List<ExciseOrgGfmis> dataList = new ArrayList<ExciseOrgGfmis>();
		try {
			dataList = int1504Service.listData();
			response.setData(dataList);
		} catch (Exception e) {
			logger.error("Int1503Controller : ", e);
		}
		return response;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<ExciseOrgGfmisVo> save(@RequestBody Int1504FormVo request) {
		ResponseData<ExciseOrgGfmisVo> response = new ResponseData<ExciseOrgGfmisVo>();
		try {
			response.setData(int1504Service.saveOrgGfmis(request));
			response.setMessage(ApplicationCache.getMessage(RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(ApplicationCache.getMessage(RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	

}
