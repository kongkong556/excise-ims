package th.go.excise.ims.ia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.common.bean.ResponseData;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_MESSAGE;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_STATUS;
import th.go.excise.ims.ia.service.Int0609Service;
import th.go.excise.ims.ia.vo.Int0609Vo;
import th.go.excise.ims.ia.vo.SearchVo;

@Controller
@RequestMapping("/api/ia/int06/09")
public class Int0609Controller {

	@Autowired
	private Int0609Service int0609Service;

	@PostMapping("/search")
	@ResponseBody
	public ResponseData<Int0609Vo> findTabs(@RequestBody SearchVo request) {
		ResponseData<Int0609Vo> response = new ResponseData<Int0609Vo>();
		try {
			response.setData(int0609Service.search(request));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}

}
