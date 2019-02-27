package th.go.excise.ims.ta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import th.co.baiwa.buckwaframework.common.bean.ResponseData;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant;
import th.co.baiwa.buckwaframework.common.constant.ProjectConstant.RESPONSE_STATUS;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetDtl;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetHdr;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetSend;
import th.go.excise.ims.ta.service.DraftWorksheetService;
import th.go.excise.ims.ta.service.PlanWorkSheetService;
import th.go.excise.ims.ta.service.TaWorksheetHdrService;
import th.go.excise.ims.ta.service.TaxOperatorService;
import th.go.excise.ims.ta.vo.*;

import java.util.List;

@Controller
@RequestMapping("/api/ta/tax-operator")
public class TaxOperatorController {

    private static final Logger logger = LoggerFactory.getLogger(TaxOperatorController.class);

    @Autowired
    private TaxOperatorService taxOperatorService;

    @Autowired
    private DraftWorksheetService draftWorksheetService;

    @Autowired
    private TaWorksheetHdrService taWorksheetHdrService;

    @Autowired
    private PlanWorkSheetService planWorkSheetService;

    // TODO worksheet header
    @PostMapping("/")
    @ResponseBody
    public ResponseData<TaxOperatorVo> getOperator(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<TaxOperatorVo> response = new ResponseData<>();

        try {
            response.setData(taxOperatorService.getOperator(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @GetMapping("/find-all-analysis-number")
    @ResponseBody
    public ResponseData<List<String>> findAllAnalysisNumber() {
        ResponseData<List<String>> response = new ResponseData<>();

        try {
            response.setData(taxOperatorService.findAllAnalysisNumber());
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @PostMapping("/find-cond-group-dtl")
    @ResponseBody
    public ResponseData<List<CondGroupVo>> findCondGroupDtl(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<List<CondGroupVo>> response = new ResponseData<>();

        try {
            response.setData(taxOperatorService.findCondGroupDtl(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @PostMapping("/get-month-start")
    @ResponseBody
    public ResponseData<YearMonthVo> getMonthStart(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<YearMonthVo> response = new ResponseData<>();

        try {
            response.setData(taxOperatorService.monthStart(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @PostMapping("/prepare-tax-grouping")
    @ResponseBody
    public ResponseData<?> prepareTaxGrouping(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<List<CondGroupVo>> response = new ResponseData<>();

        try {
            taWorksheetHdrService.addTaWorksheetHdrByCondition(formVo.getDraftNumber(), formVo.getBudgetYear());
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    // TODO DRAFT

    @PostMapping("/preview-data")
    @ResponseBody
    public ResponseData<TaxOperatorVo> previewData(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<TaxOperatorVo> response = new ResponseData<>();

        try {
            response.setData(draftWorksheetService.getPreviewData(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }


    @PostMapping("/get-month-start-draft")
    @ResponseBody
    public ResponseData<YearMonthVo> getMonthStartDraft(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<YearMonthVo> response = new ResponseData<>();

        try {
            response.setData(draftWorksheetService.monthStartDraft(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @PostMapping("/draft")
    @ResponseBody
    public ResponseData<TaxOperatorVo> getOperatorDraft(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<TaxOperatorVo> response = new ResponseData<>();

        try {
            response.setData(taxOperatorService.getOperatorDraft(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @GetMapping("/find-all-analysis-number-draft")
    @ResponseBody
    public ResponseData<List<String>> findAllAnalysisNumberDraft() {
        ResponseData<List<String>> response = new ResponseData<>();

        try {
            response.setData(draftWorksheetService.findAllDraftNumber());
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    @PostMapping("/save-draft")
    @ResponseBody
    public ResponseData<?> saveDraft(@RequestBody TaxOperatorFormVo formVo) {
        ResponseData<YearMonthVo> response = new ResponseData<>();

        try {
            draftWorksheetService.saveDraft(formVo);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
        }

        return response;
    }

    // TODO PLAN
    @PostMapping("/save-plan-work-sheet-hdr")
    @ResponseBody
    public ResponseData<?> savePlanWorkSheetHdr(@RequestBody PlanWorkSheetVo formVo) {
        ResponseData<?> response = new ResponseData<>();

        try {
            planWorkSheetService.savePlanWorkSheetHdr(formVo);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }

    @PostMapping("/find-one-budget-plan-header")
    @ResponseBody
    public ResponseData<TaPlanWorksheetHdr> CheckBudgetPlanHeader(@RequestBody PlanWorkSheetVo formVo) {
        ResponseData<TaPlanWorksheetHdr> response = new ResponseData<>();

        try {
            response.setData(planWorkSheetService.checkBudgetPlanHeader(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }

    @PostMapping("/save-plan-work-sheet-dtl")
    @ResponseBody
    public ResponseData<?> savePlanWorkSheetDtl(@RequestBody PlanWorkSheetVo formVo) {
        ResponseData<?> response = new ResponseData<>();

        try {
            planWorkSheetService.savePlanWorkSheetDtl(formVo);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.SUCCESS_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.SAVE.FAILED_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }

    @PostMapping("/find-plan-worksheet-dtl")
    @ResponseBody
    public ResponseData<List<TaPlanWorksheetDtl>> findPlanWorkSheetDtl(@RequestBody PlanWorkSheetVo formVo){
        ResponseData<List<TaPlanWorksheetDtl>> response = new ResponseData<>();

        try {
            response.setData(planWorkSheetService.findPlanWorkSheetDtl(formVo));
            response.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
            response.setStatus(RESPONSE_STATUS.FAILED);
        }
        return response;
    }
    
    @PostMapping("/get-plan-ws-send")
    @ResponseBody
    public ResponseData<List<TaPlanWorksheetSend>> getPlanWorkSheetSend(@RequestBody TaPlanWorksheetSend form) {
    	ResponseData<List<TaPlanWorksheetSend>> responseData = new ResponseData<>();
    	try {
			responseData.setData(planWorkSheetService.getPlanWorkSheetSend(form));
			responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setMessage(ApplicationCache.getMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500_CODE).getMessageTh());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
    	return responseData;
    }
}
