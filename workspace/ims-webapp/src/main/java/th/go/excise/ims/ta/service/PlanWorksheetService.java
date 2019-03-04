package th.go.excise.ims.ta.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import th.co.baiwa.buckwaframework.common.bean.DataTableAjax;
import th.co.baiwa.buckwaframework.common.constant.CommonConstants.FLAG;
import th.co.baiwa.buckwaframework.security.util.UserLoginUtils;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.buckwaframework.support.domain.ExciseDept;
import th.co.baiwa.buckwaframework.support.domain.ParamInfo;
import th.go.excise.ims.common.constant.ProjectConstants.EXCISE_OFFICE_CODE;
import th.go.excise.ims.common.util.ExciseUtils;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetDtl;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetHdr;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetSend;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetDtl;
import th.go.excise.ims.ta.persistence.repository.TaPlanWorksheetDtlRepository;
import th.go.excise.ims.ta.persistence.repository.TaPlanWorksheetHdrRepository;
import th.go.excise.ims.ta.persistence.repository.TaPlanWorksheetSendRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetDtlRepository;
import th.go.excise.ims.ta.vo.PlanWorksheetDatatableVo;
import th.go.excise.ims.ta.vo.PlanWorksheetStatus;
import th.go.excise.ims.ta.vo.PlanWorksheetVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanWorksheetService {

    private static final Logger logger = LoggerFactory.getLogger(PlanWorksheetService.class);

    @Autowired
    private WorksheetSequenceService worksheetSequenceService;

    @Autowired
    private TaWorksheetDtlRepository taWorksheetDtlRepository;

    @Autowired
    private TaPlanWorksheetHdrRepository taPlanWorksheetHdrRepository;
    @Autowired
    private TaPlanWorksheetDtlRepository taPlanWorksheetDtlRepository;

    @Autowired
    private TaPlanWorksheetSendRepository taPlanWorksheetSendRepository;

    @Autowired
    private TaPlanWorksheetHdrRepository planWorksheetHdrRepository;

    public TaPlanWorksheetHdr getPlanWorksheetHdr(PlanWorksheetVo formVo) {
        logger.info("getPlanWorksheetHdr budgetYear={}", formVo.getBudgetYear());
        return taPlanWorksheetHdrRepository.findByBudgetYear(formVo.getBudgetYear());
    }

    public void savePlanWorksheetHdr(PlanWorksheetVo formVo) {
        String planNumber = worksheetSequenceService.getPlanNumber(UserLoginUtils.getCurrentUserBean().getOfficeCode(), formVo.getBudgetYear());
        logger.info("savePlanWorksheetHdr planNumber={}", planNumber);

        TaPlanWorksheetHdr planHdr = new TaPlanWorksheetHdr();
        planHdr.setAnalysisNumber(formVo.getAnalysisNumber());
        planHdr.setBudgetYear(formVo.getBudgetYear());
        planHdr.setPlanNumber(planNumber);
        planHdr.setSendAllFlag(formVo.getSendAllFlag());
        taPlanWorksheetHdrRepository.save(planHdr);

        // Add more logic for support Send All and Send Hierarchy
        List<TaPlanWorksheetSend> planSendList = new ArrayList<>();
        TaPlanWorksheetSend planSend = null;
        if (FLAG.Y_FLAG.equals(formVo.getSendAllFlag())) {
            List<ExciseDept> sectorList = ApplicationCache.getExciseSectorList();
            List<ExciseDept> areaList = null;
            // Sector
            for (ExciseDept sector : sectorList) {
                planSend = new TaPlanWorksheetSend();
                planSend.setBudgetYear(formVo.getBudgetYear());
                planSend.setPlanNumber(planNumber);
                planSend.setOfficeCode(sector.getOfficeCode());
                planSend.setSendDate(LocalDate.now());
                planSendList.add(planSend);
                // Area
                areaList = ApplicationCache.getExciseAreaList(sector.getOfficeCode());
                for (ExciseDept area : areaList) {
                    planSend = new TaPlanWorksheetSend();
                    planSend.setBudgetYear(formVo.getBudgetYear());
                    planSend.setPlanNumber(planNumber);
                    planSend.setOfficeCode(area.getOfficeCode());
                    planSend.setSendDate(LocalDate.now());
                    planSendList.add(planSend);
                }
            }
        } else {
            planSend = new TaPlanWorksheetSend();
            planSend.setBudgetYear(formVo.getBudgetYear());
            planSend.setPlanNumber(planNumber);
            planSend.setOfficeCode(EXCISE_OFFICE_CODE.CENTRAL);
            planSend.setSendDate(LocalDate.now());
            planSendList.add(planSend);
        }
        taPlanWorksheetSendRepository.saveAll(planSendList);
    }

    @Transactional
    public void savePlanWorksheetDtl(PlanWorksheetVo formVo) {
        logger.info("savePlanWorkSheetDtl formVo={}", ToStringBuilder.reflectionToString(formVo, ToStringStyle.JSON_STYLE));

        String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeCode();

        List<String> planNewRegIdList = taPlanWorksheetDtlRepository.findByPlanNumberAndOfficeCodeWithoutIsDeletedFlag(formVo.getPlanNumber(), officeCode);
        List<String> planNewRegIdFlagNList = taPlanWorksheetDtlRepository.findByPlanNumberAndOfficeCodeWithoutIsDeletedFlagN(formVo.getPlanNumber(), officeCode);
        List<String> selectNewRegIdList = formVo.getIds();

        // Keep New and Delete newRegId list
        TaPlanWorksheetDtl planDtl = null;
        List<String> insertNewRegIdList = new ArrayList<>();
        List<String> updateNewRegIdList = new ArrayList<>();

        // Loop selectNewRegIdList for Insert NewRegId
        for (String selNewRegId : selectNewRegIdList) {
            if (!planNewRegIdList.contains(selNewRegId)) {
                insertNewRegIdList.add(selNewRegId);
            }
        }

        // Loop planWorksheetDtlNewRegIdList for Update NewRegId
        for (String planRewRegId : planNewRegIdList) {
            if (!selectNewRegIdList.contains(planRewRegId)) {
                updateNewRegIdList.add(planRewRegId);
            }
        }

        // Insert newRegId to planWorksheetDtl
        if (CollectionUtils.isNotEmpty(insertNewRegIdList)) {
            for (String newRegId : insertNewRegIdList) {
                planDtl = new TaPlanWorksheetDtl();
                planDtl.setPlanNumber(formVo.getPlanNumber());
                planDtl.setAnalysisNumber(formVo.getAnalysisNumber());
                planDtl.setOfficeCode(officeCode);
                planDtl.setNewRegId(newRegId);
                planDtl.setAuditStatus("I"); // FIXME
                taPlanWorksheetDtlRepository.save(planDtl);
                
                updateFlagTaWorksheetDtl(officeCode, FLAG.Y_FLAG, newRegId, LocalDate.now(), formVo);
            }
        }

        // Update newRegId from planWorksheetDtl
        String isDeletedPlanDtl = null;
        String selFlag = null;
        LocalDate selDate = null;
        if (CollectionUtils.isNotEmpty(updateNewRegIdList)) {
            for (String newRegId : updateNewRegIdList) {
                if (selectNewRegIdList.contains(newRegId)) {
                    isDeletedPlanDtl = FLAG.N_FLAG;
                    selFlag = FLAG.Y_FLAG;
                    selDate = LocalDate.now();

                } else {
                    isDeletedPlanDtl = FLAG.Y_FLAG;
                    selFlag = null;
                    selDate = null;
                }
                taPlanWorksheetDtlRepository.updateIsDeletedByPlanNumberAndNewRegId(isDeletedPlanDtl, formVo.getPlanNumber(), newRegId);
                
                updateFlagTaWorksheetDtl(officeCode, selFlag, newRegId, selDate, formVo);
            }
        } else {

            for (String selecNewRegtId : selectNewRegIdList) {

                if (!planNewRegIdFlagNList.contains(selecNewRegtId)) {
                    isDeletedPlanDtl = FLAG.N_FLAG;
                    selFlag = FLAG.Y_FLAG;
                    selDate = LocalDate.now();
                    taPlanWorksheetDtlRepository.updateIsDeletedByPlanNumberAndNewRegId(isDeletedPlanDtl, formVo.getPlanNumber(), selecNewRegtId);
                    
                    updateFlagTaWorksheetDtl(officeCode, selFlag, selecNewRegtId, selDate, formVo);
                }
            }
        }
    }

    private void updateFlagTaWorksheetDtl(String officeCode, String selFlag, String newRegtId, LocalDate selDate, PlanWorksheetVo formVo) {
        TaWorksheetDtl worksheetDtl = taWorksheetDtlRepository.findByAnalysisNumberAndNewRegId(formVo.getAnalysisNumber(), newRegtId);
        if (ExciseUtils.isCentral(officeCode)) {
            worksheetDtl.setCentralSelFlag(selFlag);
            worksheetDtl.setCentralSelDate(selDate);
            worksheetDtl.setCentralSelOfficeCode(officeCode);
        } else if (ExciseUtils.isSector(officeCode)) {
            worksheetDtl.setSectorSelFlag(selFlag);
            worksheetDtl.setSectorSelDate(selDate);
            worksheetDtl.setSectorSelOfficeCode(officeCode);
        } else if (ExciseUtils.isArea(officeCode)) {
            worksheetDtl.setAreaSelFlag(selFlag);
            worksheetDtl.setAreaSelDate(selDate);
            worksheetDtl.setAreaSelOfficeCode(officeCode);
        }
        taWorksheetDtlRepository.save(worksheetDtl);
    }

    public List<TaPlanWorksheetDtl> findPlanWorksheetDtl(PlanWorksheetVo formVo) {
        String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeCode();
        logger.info("findPlanWorkSheetDtl planNumber={}, officeCode={}", formVo.getPlanNumber(), officeCode);
        List<TaPlanWorksheetDtl> list = taPlanWorksheetDtlRepository.findByPlanNumberAndOfficeCode(formVo.getPlanNumber(), officeCode);
        return list;
    }

    public DataTableAjax<PlanWorksheetDatatableVo> planDtlDatatable(PlanWorksheetVo formVo) {
        formVo.setOfficeCode(UserLoginUtils.getCurrentUserBean().getOfficeCode());

        DataTableAjax<PlanWorksheetDatatableVo> dataTableAjax = new DataTableAjax<>();
        dataTableAjax.setData(taPlanWorksheetDtlRepository.findByCriteria(formVo));
        dataTableAjax.setDraw(formVo.getDraw() + 1);
        int count = taPlanWorksheetDtlRepository.countByCriteria(formVo).intValue();
        dataTableAjax.setRecordsFiltered(count);
        dataTableAjax.setRecordsTotal(count);

        return dataTableAjax;
    }

    public Boolean checkSubmitDatePlanWorksheetSend(PlanWorksheetVo formVo) {
        TaPlanWorksheetSend planSend = taPlanWorksheetSendRepository.findByPlanNumberAndOfficeCodeAndSubmitDateIsNull(formVo.getPlanNumber(), UserLoginUtils.getCurrentUserBean().getOfficeCode());
        if (planSend != null) {
            return false;
        }
        return true;
    }

    @Transactional
    public void deletePlanWorksheetDlt(String id) {
        logger.info("deletePlanWorksheetDlt by ID : {}", id);
        String budgetYear = ExciseUtils.getCurrentBudgetYear();
        TaPlanWorksheetHdr planHdr = taPlanWorksheetHdrRepository.findByBudgetYear(budgetYear);
        if (planHdr != null) {
            taPlanWorksheetDtlRepository.deleteByPlanNumberAndNewRegId(planHdr.getPlanNumber(), id);
        }
    }

    public void savePlanWorksheetSend(PlanWorksheetVo formVo) {
        String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeCode();
        formVo.setOfficeCode(officeCode);
        TaPlanWorksheetSend planSend = taPlanWorksheetSendRepository.findByPlanNumberAndOfficeCode(formVo.getPlanNumber(), officeCode);
        Long count = taPlanWorksheetDtlRepository.countByCriteria(formVo);
        planSend.setFacInNum(new Integer(count.intValue()));
        planSend.setSubmitDate(LocalDate.now());
        taPlanWorksheetSendRepository.save(planSend);

        TaPlanWorksheetHdr planHdr = this.taPlanWorksheetHdrRepository.findByPlanNumber(formVo.getPlanNumber());

        if (FLAG.N_FLAG.equalsIgnoreCase(planHdr.getSendAllFlag())) {

            if (ExciseUtils.isCentral(officeCode)) {
                List<ExciseDept> sectorList = ApplicationCache.getExciseSectorList();
                for (ExciseDept sector : sectorList) {
                    if (!officeCode.equals(sector.getOfficeCode())) {
                        saveObjectTaPlanWorksheetSend(formVo, sector.getOfficeCode());
                    }
                }
                return;
            }
            if (ExciseUtils.isSector(officeCode)) {
                List<ExciseDept> areaList = ApplicationCache.getExciseAreaList(officeCode);

                for (ExciseDept area : areaList) {
                    saveObjectTaPlanWorksheetSend(formVo, area.getOfficeCode());
                }
                return;
            }
        }
    }

    private void saveObjectTaPlanWorksheetSend(PlanWorksheetVo formVo, String officeCode) {
        TaPlanWorksheetSend planSendStampDate = new TaPlanWorksheetSend();
        planSendStampDate.setBudgetYear(formVo.getBudgetYear());
        planSendStampDate.setPlanNumber(formVo.getPlanNumber());
        planSendStampDate.setOfficeCode(officeCode);
        planSendStampDate.setSendDate(LocalDate.now());
        taPlanWorksheetSendRepository.save(planSendStampDate);
    }

    public PlanWorksheetStatus getPlanHeaderStatus(PlanWorksheetVo formVo) {
        PlanWorksheetStatus status = new PlanWorksheetStatus();
        TaPlanWorksheetHdr planWsHdr = planWorksheetHdrRepository.findByBudgetYear(formVo.getBudgetYear());

        if (planWsHdr != null) {
            ParamInfo statusDesc = ApplicationCache.getParamInfoByCode("TA_PLAN_STATUS", planWsHdr.getPlanStatus());
            if (statusDesc != null) {
                status.setPlanStatus(statusDesc.getParamCode());
                status.setApprovalComment(planWsHdr.getApprovalComment());
                status.setApprovedComment(planWsHdr.getApprovedComment());
                status.setPlanStatusDesc(statusDesc.getValue1());
            }
        }

        return status;
    }

    public void insertComment(TaPlanWorksheetHdr form) {
        TaPlanWorksheetHdr comment = planWorksheetHdrRepository.findByBudgetYear(form.getBudgetYear());
        if (StringUtils.isEmpty(form.getApprovedComment())) {
            comment.setApprovalBy(UserLoginUtils.getCurrentUsername());
            comment.setApprovalDate(LocalDateTime.now());
            comment.setApprovalComment(form.getApprovalComment());
            comment.setPlanStatus("W");
        } else {
            comment.setApprovedBy(UserLoginUtils.getCurrentUsername());
            comment.setApprovedDate(LocalDateTime.now());
            comment.setApprovedComment(form.getApprovedComment());
            comment.setPlanStatus("P");
        }
        planWorksheetHdrRepository.save(comment);
    }

}
