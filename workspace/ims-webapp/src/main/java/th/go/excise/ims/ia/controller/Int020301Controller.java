package th.go.excise.ims.ia.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.common.bean.ResponseData;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_MESSAGE;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_STATUS;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.go.excise.ims.ia.service.Int020301Service;
import th.go.excise.ims.ia.vo.Int020301HeaderVo;
import th.go.excise.ims.ia.vo.Int020301InfoVo;

@Controller
@RequestMapping("/api/ia/int02/03/01")
public class Int020301Controller {

	private static final Logger logger = LoggerFactory.getLogger(Int020301Controller.class);

	@Autowired
	private Int020301Service int020301Service;

	@GetMapping("/header/{idSide}/{budgetYear}")
	@ResponseBody
	public ResponseData<List<Int020301HeaderVo>> findHeaderByIdHead(@PathVariable("idSide") String idSideStr,
			@PathVariable("budgetYear") String budgetYear) {
		ResponseData<List<Int020301HeaderVo>> responseData = new ResponseData<List<Int020301HeaderVo>>();
		List<Int020301HeaderVo> data = new ArrayList<>();
		try {
			data = int020301Service.findHeaderByIdSide(idSideStr, budgetYear);
			responseData.setData(data);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Int0203Controller::findByIdHead ", e);
			responseData.setMessage(ApplicationCache.getMessage(RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/info/{idHdr}/{budgetYear}")
	@ResponseBody
	public ResponseData<List<Int020301InfoVo>> findInfoByIdHead(@PathVariable("idHdr") String idHdrStr,
			@PathVariable("budgetYear") String budgetYear) {
		ResponseData<List<Int020301InfoVo>> responseData = new ResponseData<List<Int020301InfoVo>>();
		List<Int020301InfoVo> data = new ArrayList<>();
		try {
			data = int020301Service.findInfoByIdHdr(idHdrStr, budgetYear);
			responseData.setData(data);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Int0203Controller::findByIdHead ", e);
			responseData.setMessage(ApplicationCache.getMessage(RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/export/excel/{idHdr}/{budgetYear}")
	public void export(@PathVariable("idHdr") String idHdrStr, @PathVariable("budgetYear") String budgetYear, HttpServletResponse response) throws Exception {
		// set fileName
		String fileName = URLEncoder.encode("สรุปผลแบบสอบถามระบบการควบคุมภายใน", "UTF-8");

		// write it as an excel attachment
		ByteArrayOutputStream outByteStream = int020301Service.exportInt020301(idHdrStr, budgetYear);
		byte[] outArray = outByteStream.toByteArray();
		response.setContentType("application/octet-stream");
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		outStream.close();
	}

}
