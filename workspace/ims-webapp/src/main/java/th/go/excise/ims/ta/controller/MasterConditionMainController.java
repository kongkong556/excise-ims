package th.go.excise.ims.ta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.common.bean.ResponseData;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_STATUS;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.buckwaframework.support.domain.ParamInfo;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainDtl;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainHdr;
import th.go.excise.ims.ta.service.MasterConditionMainService;
import th.go.excise.ims.ta.vo.ConditionMessageVo;
import th.go.excise.ims.ta.vo.MasterConditionMainHdrDtlVo;

@Controller
@RequestMapping("/api/ta/master-condition-main")
public class MasterConditionMainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterConditionMainController.class);

    @Autowired
    private MasterConditionMainService masterConditionService;

    @PostMapping("/create-hdr")
    @ResponseBody
    public ResponseData<TaMasCondMainHdr> insertCondMainHdr(@RequestBody TaMasCondMainHdr form) {
    	ResponseData<TaMasCondMainHdr> response = new ResponseData<TaMasCondMainHdr>();
    	try {
            masterConditionService.insertCondMainHdr(form);
            response.setData(null);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
        	response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }
    
    @PostMapping("/update-hdr")
    @ResponseBody
    public ResponseData<TaMasCondMainHdr> updateCondMainHdr(@RequestBody TaMasCondMainHdr form) {
    	ResponseData<TaMasCondMainHdr> response = new ResponseData<TaMasCondMainHdr>();
    	try {
            masterConditionService.updateCondMainHdr(form);
            response.setData(null);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
        	response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }
    
    @PostMapping("/create-dtl")
    @ResponseBody
    public ResponseData<List<MasterConditionMainHdrDtlVo>> insertCondMainDtl(@RequestBody MasterConditionMainHdrDtlVo formVo) {
        ResponseData<List<MasterConditionMainHdrDtlVo>> responseData = new ResponseData<List<MasterConditionMainHdrDtlVo>>();
        try {
            masterConditionService.insertCondMainDtl(formVo);
            responseData.setData(null);
            responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @PostMapping("/update-dtl")
    @ResponseBody
    public ResponseData<List<MasterConditionMainHdrDtlVo>> updateCondMainDtl(@RequestBody MasterConditionMainHdrDtlVo formVo) {
        ResponseData<List<MasterConditionMainHdrDtlVo>> responseData = new ResponseData<List<MasterConditionMainHdrDtlVo>>();
        try {
            masterConditionService.updateCondMainDtl(formVo);
            responseData.setData(null);
            responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @PostMapping("/find-hdr")
    @ResponseBody
    public ResponseData<TaMasCondMainHdr> findHdr(@RequestBody TaMasCondMainHdr formVo) {
        ResponseData<TaMasCondMainHdr> responseData = new ResponseData<TaMasCondMainHdr>();
        try {
            responseData.setData(masterConditionService.findHdr(formVo));
            responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
        	responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }
    
    @PostMapping("/find-hdr-all")
    @ResponseBody
    public ResponseData<List<TaMasCondMainHdr>> findHdrAll(@RequestBody TaMasCondMainHdr formVo) {
        ResponseData<List<TaMasCondMainHdr>> responseData = new ResponseData<List<TaMasCondMainHdr>>();
        try {
            responseData.setData(masterConditionService.findHdrAll(formVo));
            responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
        	responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @PostMapping("/find-dtl")
    @ResponseBody
    public ResponseData<List<TaMasCondMainDtl>> findDtl(@RequestBody TaMasCondMainDtl formVo) {
        ResponseData<List<TaMasCondMainDtl>> responseData = new ResponseData<List<TaMasCondMainDtl>>();
        try {
            responseData.setData(masterConditionService.findDtl(formVo));
            responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
        	responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @GetMapping("/find-allhdr")
    @ResponseBody
    public ResponseData<List<TaMasCondMainHdr>> findAllHdr() {
        ResponseData<List<TaMasCondMainHdr>> responseData = new ResponseData<List<TaMasCondMainHdr>>();
        try {
            responseData.setData(masterConditionService.findAllHdr());
            responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
        	responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @GetMapping("/condition-message")
    @ResponseBody
    public ResponseData<ConditionMessageVo> conditionMessage() {
        ResponseData<ConditionMessageVo> response = new ResponseData<>();
        try{
            response.setData(this.masterConditionService.conditionMessage());
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        }catch (Exception e){
        	logger.error(e.getMessage(), e);
			response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }
    
    @GetMapping("/get-main-cond-range")
    @ResponseBody
    public ResponseData<List<ParamInfo>> getMainCondRange() {
    	ResponseData<List<ParamInfo>> response = new ResponseData<>();
        try{
            response.setData(masterConditionService.getMainCondRange());
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        }catch (Exception e){
        	logger.error(e.getMessage(), e);
			response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }

}
