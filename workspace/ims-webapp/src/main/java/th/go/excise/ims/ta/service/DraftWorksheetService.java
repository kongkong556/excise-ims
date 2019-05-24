package th.go.excise.ims.ta.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.common.constant.CommonConstants;
import th.co.baiwa.buckwaframework.common.util.ConvertDateUtils;
import th.co.baiwa.buckwaframework.common.util.LocalDateUtils;
import th.co.baiwa.buckwaframework.common.util.NumberUtils;
import th.co.baiwa.buckwaframework.preferences.constant.ParameterConstants.PARAM_GROUP;
import th.co.baiwa.buckwaframework.preferences.constant.ParameterConstants.TA_CONFIG;
import th.co.baiwa.buckwaframework.preferences.persistence.entity.ParameterInfo;
import th.co.baiwa.buckwaframework.preferences.persistence.repository.ParameterInfoRepository;
import th.co.baiwa.buckwaframework.security.util.UserLoginUtils;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.go.excise.ims.common.constant.ProjectConstants.TAX_COMPARE_TYPE;
import th.go.excise.ims.common.constant.ProjectConstants.TA_WORKSHEET_STATUS;
import th.go.excise.ims.common.util.ExciseUtils;
import th.go.excise.ims.preferences.vo.ExciseDepartment;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainDtl;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainHdr;
import th.go.excise.ims.ta.persistence.entity.TaMasCondSubCapital;
import th.go.excise.ims.ta.persistence.entity.TaMasCondSubNoAudit;
import th.go.excise.ims.ta.persistence.entity.TaMasCondSubRisk;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetCondMainDtl;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetCondMainHdr;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetCondSubCapital;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetCondSubNoAudit;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetCondSubRisk;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetDtl;
import th.go.excise.ims.ta.persistence.entity.TaWorksheetHdr;
import th.go.excise.ims.ta.persistence.entity.TaWsReg4000;
import th.go.excise.ims.ta.persistence.repository.TaMasCondMainDtlRepository;
import th.go.excise.ims.ta.persistence.repository.TaMasCondMainHdrRepository;
import th.go.excise.ims.ta.persistence.repository.TaMasCondSubCapitalRepository;
import th.go.excise.ims.ta.persistence.repository.TaMasCondSubNoAuditRepository;
import th.go.excise.ims.ta.persistence.repository.TaMasCondSubRiskRepository;
import th.go.excise.ims.ta.persistence.repository.TaPlanWorksheetHisRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetCondMainDtlRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetCondMainHdrRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetCondSubCapitalRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetCondSubNoAuditRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetCondSubRiskRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetDtlRepository;
import th.go.excise.ims.ta.persistence.repository.TaWorksheetHdrRepository;
import th.go.excise.ims.ta.persistence.repository.TaWsInc8000MRepository;
import th.go.excise.ims.ta.persistence.repository.TaWsReg4000Repository;
import th.go.excise.ims.ta.util.TaxAuditUtils;
import th.go.excise.ims.ta.vo.TaxOperatorDetailVo;
import th.go.excise.ims.ta.vo.TaxOperatorFormVo;
import th.go.excise.ims.ta.vo.TaxOperatorVo;
import th.go.excise.ims.ta.vo.YearMonthVo;

@Service
public class DraftWorksheetService {

	private static final Logger logger = LoggerFactory.getLogger(DraftWorksheetService.class);

	private static final String NO_TAX_AMOUNT = "-";

	@Autowired
	private WorksheetSequenceService worksheetSequenceService;

	@Autowired
	private TaWsReg4000Repository taWsReg4000Repository;
	@Autowired
	private TaWsInc8000MRepository taWsInc8000MRepository;
	@Autowired
	private TaPlanWorksheetHisRepository taPlanWorksheetHisRepository;

	@Autowired
	private TaMasCondMainHdrRepository taMasCondMainHdrRepository;
	@Autowired
	private TaMasCondMainDtlRepository taMasCondMainDtlRepository;
	@Autowired
	private TaMasCondSubCapitalRepository taMasCondSubCapitalRepository;
	@Autowired
	private TaMasCondSubRiskRepository taMasCondSubRiskRepository;
	@Autowired
	private TaMasCondSubNoAuditRepository taMasCondSubNoAuditRepository;

	@Autowired
	private TaWorksheetCondMainHdrRepository taWorksheetCondMainHdrRepository;
	@Autowired
	private TaWorksheetCondMainDtlRepository taWorksheetCondMainDtlRepository;
	@Autowired
	private TaWorksheetCondSubCapitalRepository taWorksheetCondSubCapitalRepository;
	@Autowired
	private TaWorksheetCondSubRiskRepository taWorksheetCondSubRiskRepository;
	@Autowired
	private TaWorksheetCondSubNoAuditRepository taWorksheetCondSubNoAuditRepository;

	@Autowired
	private TaWorksheetHdrRepository taWorksheetHdrRepository;
	@Autowired
	private TaWorksheetDtlRepository taWorksheetDtlRepository;

	public TaxOperatorVo getPreviewData(TaxOperatorFormVo formVo) {
		TaxOperatorVo vo = new TaxOperatorVo();
		try {
			List<TaxOperatorDetailVo> taxOperatorDetailVoList = prepareTaxOperatorDetailVoList(formVo);
			vo.setDatas(TaxAuditUtils.prepareTaxOperatorDatatable(taxOperatorDetailVoList, formVo));
			vo.setCount(taWsReg4000Repository.countByCriteriaDuty(formVo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return vo;
	}

	public List<TaxOperatorDetailVo> prepareTaxOperatorDetailVoList(TaxOperatorFormVo formVo) {
		logger.info("prepareTaxOperatorDetailVoList startDate={}, endDate={}, dateRange={}", formVo.getDateStart(), formVo.getDateEnd(), formVo.getDateRange());
		
		final int MAX_MONTH = 36; // 3 years
		String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeCode();
		String budgetYear = formVo.getBudgetYear();
		String compareType = (taMasCondMainHdrRepository.findByOfficeCodeAndBudgetYearAndCondNumber(officeCode, budgetYear, formVo.getCondNumber())).getCompType();
		formVo.setOfficeCode(officeCode);
		
		String ymStartReg4000 = null;
		String ymEndReg4000 = null;
		String ymStartInc8000M = null;
		String ymEndInc8000M = null;
		List<LocalDate> subLocalDateList1 = new ArrayList<>();
		List<LocalDate> subLocalDateList2 = new ArrayList<>();
		if (TAX_COMPARE_TYPE.HALF.equals(compareType)) {
			int dateRange = (formVo.getDateRange() / 2) - 1;
			LocalDate localDateG1Start = LocalDate.from(ThaiBuddhistDate.of(Integer.parseInt(formVo.getDateStart().split("/")[1]), Integer.parseInt(formVo.getDateStart().split("/")[0]), 1));
			LocalDate localDateG1End = LocalDate.from(ThaiBuddhistDate.of(Integer.parseInt(formVo.getDateEnd().split("/")[1]), Integer.parseInt(formVo.getDateEnd().split("/")[0]), 1));
			ymStartReg4000 = localDateG1Start.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			ymEndReg4000 = localDateG1End.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			LocalDate localDateG2Start = localDateG1End.plus(1, ChronoUnit.MONTHS);
			LocalDate localDateG2End = localDateG2Start.plus(dateRange, ChronoUnit.MONTHS);
			ymStartInc8000M = localDateG1Start.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			ymEndInc8000M = localDateG2End.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			subLocalDateList1.addAll(LocalDateUtils.getLocalDateRange(localDateG1Start, localDateG1End));
			subLocalDateList2.addAll(LocalDateUtils.getLocalDateRange(localDateG2Start, localDateG2End));
		} else {
			LocalDate localDateG2Start = LocalDate.from(ThaiBuddhistDate.of(Integer.parseInt(formVo.getDateStart().split("/")[1]), Integer.parseInt(formVo.getDateStart().split("/")[0]), 1));
			LocalDate localDateG2End = LocalDate.from(ThaiBuddhistDate.of(Integer.parseInt(formVo.getDateEnd().split("/")[1]), Integer.parseInt(formVo.getDateEnd().split("/")[0]), 1));
			ymStartReg4000 = localDateG2Start.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			ymEndReg4000 = localDateG2End.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			LocalDate localDateG1Start = localDateG2Start.minus(1, ChronoUnit.YEARS);
			LocalDate localDateG1End = localDateG2End.minus(1, ChronoUnit.YEARS);
			ymStartInc8000M = localDateG1Start.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			ymEndInc8000M = localDateG2End.format(DateTimeFormatter.ofPattern(ConvertDateUtils.YYYYMM));
			subLocalDateList1.addAll(LocalDateUtils.getLocalDateRange(localDateG1Start, localDateG1End));
			subLocalDateList2.addAll(LocalDateUtils.getLocalDateRange(localDateG2Start, localDateG2End));
		}
		logger.debug("taxCompareType={}, ymStartReg4000={}, ymEndReg4000={}, ymStartInc8000M={}, ymEndInc8000M={}", compareType, ymStartReg4000, ymEndReg4000, ymStartInc8000M, ymEndInc8000M);
		logger.debug("subLocalDateList1.size()={}, subLocalDateList1={}", subLocalDateList1.size(), org.springframework.util.StringUtils.collectionToCommaDelimitedString(subLocalDateList1));
		logger.debug("subLocalDateList2.size()={}, subLocalDateList2={}", subLocalDateList2.size(), org.springframework.util.StringUtils.collectionToCommaDelimitedString(subLocalDateList2));
		
		Map<String, String> auditPlanMap = new HashMap<>();
		int lastYear1 = 0;
		int lastYear2 = 0;
		int lastYear3 = 0;
		if (StringUtils.isNotBlank(budgetYear)) {
			List<String> budgetYearList = new ArrayList<>();
			lastYear1 = Integer.valueOf(budgetYear) - 1;
			lastYear2 = Integer.valueOf(budgetYear) - 2;
			lastYear3 = Integer.valueOf(budgetYear) - 3;
			budgetYearList.add(String.valueOf(lastYear1));
			budgetYearList.add(String.valueOf(lastYear2));
			budgetYearList.add(String.valueOf(lastYear3));
			auditPlanMap = taPlanWorksheetHisRepository.findAuditPlanCodeByOfficeCodeAndBudgetYearList(officeCode, budgetYearList);
		}

		Map<String, String> maxYearMap = taPlanWorksheetHisRepository.findMaxTaxAuditYear();

		List<TaWsReg4000> wsReg4000List = taWsReg4000Repository.findByCriteriaDuty(formVo, ymStartReg4000, ymEndReg4000);
		Map<String, BigDecimal> incomeMap = null;
		BigDecimal sumTaxAmtG1 = null;
		BigDecimal sumTaxAmtG2 = null;
		BigDecimal taxAmtChnPnt = null;
		ExciseDepartment exciseDeptSector = null;
		ExciseDepartment exciseDeptArea = null;
		String taxAmount = null;
		List<Double> taxAmountList = null;

		TaxOperatorDetailVo detailVo = null;
		List<TaxOperatorDetailVo> detailVoList = new ArrayList<>();
		String tmpYearMonth = null;
		BigDecimal tmpTaxAmount = null;
		for (TaWsReg4000 wsReg4000 : wsReg4000List) {
			//logger.debug("wsReg4000.newRegId={}", wsReg4000.getNewRegId());

			int countTaxMonthNo = 0;
			int countG1 = 0;
			int countG2 = 0;
			sumTaxAmtG1 = BigDecimal.ZERO;
			sumTaxAmtG2 = BigDecimal.ZERO;
			taxAmountList = new ArrayList<>();

			detailVo = new TaxOperatorDetailVo();

			detailVo.setLastAuditYear(maxYearMap.get(wsReg4000.getNewRegId()));

			detailVo.setDutyCode(wsReg4000.getDutyCode());
			detailVo.setDutyName(ExciseUtils.getDutyDesc(wsReg4000.getDutyCode()));
			detailVo.setNewRegId(wsReg4000.getNewRegId());
			detailVo.setCusFullname(wsReg4000.getCusFullname());
			detailVo.setFacFullname(wsReg4000.getFacFullname());
			detailVo.setFacAddress(wsReg4000.getFacAddress());
			detailVo.setOfficeCode(wsReg4000.getOfficeCode());
			detailVo.setRegStatus(wsReg4000.getRegStatusDesc() + " " + ConvertDateUtils.formatLocalDateToString(wsReg4000.getRegDate(), "dd/MM/yy", ConvertDateUtils.LOCAL_TH));
			detailVo.setRegCapital(wsReg4000.getRegCapital());
			detailVo.setTaxAuditLast1(auditPlanMap.get(String.valueOf(lastYear1) + wsReg4000.getNewRegId()));
			detailVo.setTaxAuditLast2(auditPlanMap.get(String.valueOf(lastYear2) + wsReg4000.getNewRegId()));
			detailVo.setTaxAuditLast3(auditPlanMap.get(String.valueOf(lastYear3) + wsReg4000.getNewRegId()));
			exciseDeptSector = ApplicationCache.getExciseDepartment(wsReg4000.getOfficeCode().substring(0, 2) + "0000");

			if (exciseDeptSector != null) {
				detailVo.setSecCode(exciseDeptSector.getOfficeCode());
				detailVo.setSecDesc(exciseDeptSector.getDeptShortName());
			}
			exciseDeptArea = ApplicationCache.getExciseDepartment(wsReg4000.getOfficeCode().substring(0, 4) + "00");

			if (exciseDeptArea != null) {
				detailVo.setAreaCode(exciseDeptArea.getOfficeCode());
				detailVo.setAreaDesc(exciseDeptArea.getDeptShortName());
			}
			
			incomeMap = taWsInc8000MRepository.findByMonthRangeDuty(wsReg4000.getNewRegId(), wsReg4000.getDutyCode(), ymStartInc8000M, ymEndInc8000M);
			if (incomeMap == null) {
				// Set Default Value
				taxAmount = NO_TAX_AMOUNT;
				for (int i = 1; i <= MAX_MONTH; i++) {
					setTaxAmount(detailVo, "G1M" + i, taxAmount);
				}
				for (int i = 1; i <= MAX_MONTH; i++) {
					setTaxAmount(detailVo, "G2M" + i, taxAmount);
				}
				detailVo.setSumTaxAmtG1(taxAmount);
				detailVo.setSumTaxAmtG2(taxAmount);
				detailVo.setTaxMonthNo(String.valueOf(BigDecimal.ZERO));
				detailVo.setTaxAmtChnPnt(taxAmount);
				detailVo.setTaxAmtSd(taxAmount);
				detailVo.setTaxAmtMean(taxAmount);
				detailVo.setTaxAmtMaxPnt(taxAmount);
				detailVo.setTaxAmtMinPnt(taxAmount);
				detailVoList.add(detailVo);
				continue;
			}

			// Group 1
			for (LocalDate localDate : subLocalDateList1) {
				tmpYearMonth = String.valueOf(localDate.getYear()) + StringUtils.leftPad(String.valueOf(localDate.getMonthValue()), 2, "0");
				tmpTaxAmount = incomeMap.get(tmpYearMonth);
				if (tmpTaxAmount != null) {
					taxAmount = tmpTaxAmount.toString();
					taxAmountList.add(tmpTaxAmount.doubleValue());
					sumTaxAmtG1 = sumTaxAmtG1.add(tmpTaxAmount);
					countTaxMonthNo++;
				} else {
					taxAmount = NO_TAX_AMOUNT;
				}
				countG1++;
				setTaxAmount(detailVo, "G1M" + countG1, taxAmount);
			}
			// Group 2
			for (LocalDate localDate : subLocalDateList2) {
				tmpYearMonth = String.valueOf(localDate.getYear()) + StringUtils.leftPad(String.valueOf(localDate.getMonthValue()), 2, "0");
				tmpTaxAmount = incomeMap.get(tmpYearMonth);
				if (tmpTaxAmount != null) {
					taxAmount = tmpTaxAmount.toString();
					taxAmountList.add(tmpTaxAmount.doubleValue());
					sumTaxAmtG2 = sumTaxAmtG2.add(tmpTaxAmount);
					countTaxMonthNo++;
				} else {
					taxAmount = NO_TAX_AMOUNT;
				}
				countG2++;
				setTaxAmount(detailVo, "G2M" + countG2, taxAmount);
			}

			detailVo.setSumTaxAmtG1((sumTaxAmtG1.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
			detailVo.setSumTaxAmtG2((sumTaxAmtG2.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
			detailVo.setTaxMonthNo(String.valueOf(countTaxMonthNo));

			// Calculate Percentage
			if ((sumTaxAmtG2.compareTo(BigDecimal.ZERO) == 0) && (sumTaxAmtG1.compareTo(BigDecimal.ZERO) == 0)) {
				taxAmtChnPnt = BigDecimal.ZERO;
			} else {
				taxAmtChnPnt = (sumTaxAmtG2.subtract(sumTaxAmtG1)).multiply(new BigDecimal("100")).divide(NumberUtils.ZeroToOne(sumTaxAmtG1), 2, BigDecimal.ROUND_HALF_UP);
			}
			detailVo.setTaxAmtChnPnt(taxAmtChnPnt.toString());

			// Calculate S.D.
			calculateSD(detailVo, taxAmountList);

			// Find DutyCode Description
			detailVo.setDutyName(ExciseUtils.getDutyDesc(wsReg4000.getDutyCode()));

			detailVoList.add(detailVo);
		}

		return detailVoList;
	}
	
	private void setTaxAmount(TaxOperatorDetailVo detailVo, String groupMonthNo, String taxAmount) {
		try {
			Method method = TaxOperatorDetailVo.class.getDeclaredMethod("setTaxAmt" + groupMonthNo, String.class);
			method.invoke(detailVo, taxAmount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void calculateSD(TaxOperatorDetailVo detailVo, List<Double> taxAmountList) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");

		double[] taxAmounts = taxAmountList.stream().mapToDouble(d -> d).toArray();
		double mean = StatUtils.mean(taxAmounts);
		if (!Double.isNaN(mean)) {
			detailVo.setTaxAmtMean(decimalFormat.format(mean));
		} else {
			detailVo.setTaxAmtMean(NO_TAX_AMOUNT);
		}

		StandardDeviation standardDeviation = new StandardDeviation();
		double sd = standardDeviation.evaluate(taxAmounts, mean);
		if (!Double.isNaN(sd)) {
			detailVo.setTaxAmtSd(decimalFormat.format(sd));
		} else {
			detailVo.setTaxAmtSd(NO_TAX_AMOUNT);
		}

		double max = StatUtils.max(taxAmounts);
		double taxAmtMaxPnt = ((max - mean) / mean) * 100;
		if (!Double.isNaN(taxAmtMaxPnt)) {
			detailVo.setTaxAmtMaxPnt(decimalFormat.format(taxAmtMaxPnt));
		} else {
			detailVo.setTaxAmtMaxPnt(NO_TAX_AMOUNT);
		}

		double min = StatUtils.min(taxAmounts);
		double taxAmtMinPnt = ((min - mean) / mean) * 100;
		if (!Double.isNaN(taxAmtMaxPnt)) {
			detailVo.setTaxAmtMaxPnt(decimalFormat.format(taxAmtMinPnt));
		} else {
			detailVo.setTaxAmtMaxPnt(NO_TAX_AMOUNT);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveDraftWorksheet(TaxOperatorFormVo formVo) {
		String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeCode();
		String budgetYear = ExciseUtils.getCurrentBudgetYear();
		String analysisNumber = worksheetSequenceService.getAnalysisNumber(officeCode, budgetYear);
		logger.info("saveDraftWorksheet officeCode={}, budgetYear={}, condNumber={}, analysisNumber={}", officeCode, budgetYear, formVo.getCondNumber(), analysisNumber);

		formVo.setBudgetYear(ExciseUtils.getCurrentBudgetYear());
		String dateStart = ConvertDateUtils.formatDateToString(ConvertDateUtils.parseStringToDate(formVo.getDateStart(), ConvertDateUtils.MM_YYYY, ConvertDateUtils.LOCAL_TH), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
		String dateEnd = ConvertDateUtils.formatDateToString(ConvertDateUtils.parseStringToDate(formVo.getDateEnd(), ConvertDateUtils.MM_YYYY, ConvertDateUtils.LOCAL_TH), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);

		// ==> Save WorksheetMainCondHdr
		TaMasCondMainHdr masCondMainHdr = taMasCondMainHdrRepository.findByCondNumber(formVo.getCondNumber());
		TaWorksheetCondMainHdr conMainHdr = new TaWorksheetCondMainHdr();
		conMainHdr.setAnalysisNumber(analysisNumber);
		conMainHdr.setCondGroupDesc(masCondMainHdr.getCondGroupDesc());
		conMainHdr.setMonthNum(masCondMainHdr.getMonthNum());
		conMainHdr.setYearMonthStart(dateStart);
		conMainHdr.setYearMonthEnd(dateEnd);
		conMainHdr.setCondGroupNum(String.valueOf(masCondMainHdr.getCondGroupNum()));
		conMainHdr.setNewFacFlag(masCondMainHdr.getNewFacFlag());
		conMainHdr.setCompType(masCondMainHdr.getCompType());
		taWorksheetCondMainHdrRepository.save(conMainHdr);

		// ==> Save WorksheetMainCondDtl
		List<TaMasCondMainDtl> masCondMainDtlList = taMasCondMainDtlRepository.findByCondNumber(formVo.getCondNumber());
		List<TaWorksheetCondMainDtl> condMainDtlList = new ArrayList<>();
		TaWorksheetCondMainDtl condMainDtl = null;
		for (TaMasCondMainDtl masCondMainDtl : masCondMainDtlList) {
			condMainDtl = new TaWorksheetCondMainDtl();
			condMainDtl.setAnalysisNumber(analysisNumber);
			condMainDtl.setCondGroup(masCondMainDtl.getCondGroup());
			condMainDtl.setTaxFreqType(masCondMainDtl.getTaxFreqType());
			condMainDtl.setTaxMonthStart(masCondMainDtl.getTaxMonthStart());
			condMainDtl.setTaxMonthEnd(masCondMainDtl.getTaxMonthEnd());
			condMainDtl.setRangeTypeStart(masCondMainDtl.getRangeTypeStart());
			condMainDtl.setRangeStart(masCondMainDtl.getRangeStart());
			condMainDtl.setRangeTypeEnd(masCondMainDtl.getRangeTypeEnd());
			condMainDtl.setRangeEnd(masCondMainDtl.getRangeEnd());
			condMainDtl.setRiskLevel(masCondMainDtl.getRiskLevel());
			condMainDtl.setCondType(masCondMainDtl.getCondType());
			condMainDtl.setCondDtlDesc(masCondMainDtl.getCondDtlDesc());
			condMainDtlList.add(condMainDtl);
		}
		taWorksheetCondMainDtlRepository.saveAll(condMainDtlList);

		// ==> Save WorksheetCondSubCapital
		if (StringUtils.isNotBlank(formVo.getCondSub1())) {
			List<TaMasCondSubCapital> masCondSubCapitalList = taMasCondSubCapitalRepository.findByOfficeCodeAndBudgetYear(officeCode, budgetYear);
			for (TaMasCondSubCapital masCondSubCapital : masCondSubCapitalList) {
				TaWorksheetCondSubCapital worksheetCondSubCapital = new TaWorksheetCondSubCapital();
				worksheetCondSubCapital.setAnalysisNumber(analysisNumber);
				worksheetCondSubCapital.setDutyCode(masCondSubCapital.getDutyCode());
				worksheetCondSubCapital.setHugeCapitalAmount(masCondSubCapital.getHugeCapitalAmount());
				worksheetCondSubCapital.setLargeCapitalAmount(masCondSubCapital.getLargeCapitalAmount());
				worksheetCondSubCapital.setMediumCapitalAmount(masCondSubCapital.getMediumCapitalAmount());
				worksheetCondSubCapital.setSmallCapitalAmount(masCondSubCapital.getSmallCapitalAmount());
				taWorksheetCondSubCapitalRepository.save(worksheetCondSubCapital);
			}
		}

		// ==> Save WorksheetCondSubRisk
		if (StringUtils.isNotBlank(formVo.getCondSub2())) {
			List<TaMasCondSubRisk> masCondSubRiskList = taMasCondSubRiskRepository.findByBudgetYearAndOfficeCode(budgetYear, officeCode);
			TaWorksheetCondSubRisk worksheetCondSubRisk = null;
			for (TaMasCondSubRisk masCondSubRisk : masCondSubRiskList) {
				worksheetCondSubRisk = new TaWorksheetCondSubRisk();
				worksheetCondSubRisk.setAnalysisNumber(analysisNumber);
				worksheetCondSubRisk.setDutyCode(masCondSubRisk.getDutyCode());
				worksheetCondSubRisk.setRiskLevel(masCondSubRisk.getRiskLevel());
				taWorksheetCondSubRiskRepository.save(worksheetCondSubRisk);
			}
		}

		// ==> Save WorksheetCondSubNoAudit
		//if (StringUtils.isNotBlank(formVo.getCondSub3())) {
			TaMasCondSubNoAudit masCondSubNoAudit = taMasCondSubNoAuditRepository.findByBudgetYearAndOfficeCode(budgetYear, officeCode);
			if(masCondSubNoAudit!=null) {
				
				TaWorksheetCondSubNoAudit worksheetCondSubNoAudit = new TaWorksheetCondSubNoAudit();
				worksheetCondSubNoAudit.setAnalysisNumber(analysisNumber);
				worksheetCondSubNoAudit.setNoTaxAuditYearNum(masCondSubNoAudit.getNoTaxAuditYearNum());
				taWorksheetCondSubNoAuditRepository.save(worksheetCondSubNoAudit);
			}
		//}

		// ==> Save WorksheetHdr
		TaWorksheetHdr worksheetHdr = new TaWorksheetHdr();
		worksheetHdr.setOfficeCode(officeCode);
		worksheetHdr.setAnalysisNumber(analysisNumber);
		worksheetHdr.setBudgetYear(budgetYear);
		worksheetHdr.setWorksheetStatus(TA_WORKSHEET_STATUS.DRAFT);
		if (StringUtils.isNotBlank(formVo.getCondSub1())) {
			worksheetHdr.setCondSubCapitalFlag(CommonConstants.FLAG.Y_FLAG);
		}
		if (StringUtils.isNotBlank(formVo.getCondSub2())) {
			worksheetHdr.setCondSubRiskFlag(CommonConstants.FLAG.Y_FLAG);
		}
		if (StringUtils.isNotBlank(formVo.getCondSub3())) {
			worksheetHdr.setCondSubNoAuditFlag(CommonConstants.FLAG.Y_FLAG);
		}
		taWorksheetHdrRepository.save(worksheetHdr);

		// ==> Save WorksheetDtl
		List<TaxOperatorDetailVo> detailVoList = prepareTaxOperatorDetailVoList(formVo);
		List<TaWorksheetDtl> worksheetfDtlList = new ArrayList<>();
		TaWorksheetDtl worksheetDtl = new TaWorksheetDtl();

		for (TaxOperatorDetailVo detailVo : detailVoList) {
			worksheetDtl = new TaWorksheetDtl();
			worksheetDtl.setAnalysisNumber(analysisNumber);
			worksheetDtl.setNewRegId(detailVo.getNewRegId());

			worksheetDtl.setSumTaxAmtG1(NO_TAX_AMOUNT.equals(detailVo.getSumTaxAmtG1()) ? null : detailVo.getSumTaxAmtG1());
			worksheetDtl.setSumTaxAmtG2(NO_TAX_AMOUNT.equals(detailVo.getSumTaxAmtG2()) ? null : detailVo.getSumTaxAmtG2());
			worksheetDtl.setTaxAmtChnPnt(NO_TAX_AMOUNT.equals(detailVo.getTaxAmtChnPnt()) ? null : detailVo.getTaxAmtChnPnt());
			worksheetDtl.setTaxMonthNo(detailVo.getTaxMonthNo());

			worksheetDtl.setTaxAuditLast1(detailVo.getTaxAuditLast1());
			worksheetDtl.setTaxAuditLast2(detailVo.getTaxAuditLast2());
			worksheetDtl.setTaxAuditLast3(detailVo.getTaxAuditLast3());

			worksheetDtl.setTaxAmtG1M1(detailVo.getTaxAmtG1M1());
			worksheetDtl.setTaxAmtG1M2(detailVo.getTaxAmtG1M2());
			worksheetDtl.setTaxAmtG1M3(detailVo.getTaxAmtG1M3());
			worksheetDtl.setTaxAmtG1M4(detailVo.getTaxAmtG1M4());
			worksheetDtl.setTaxAmtG1M5(detailVo.getTaxAmtG1M5());
			worksheetDtl.setTaxAmtG1M6(detailVo.getTaxAmtG1M6());
			worksheetDtl.setTaxAmtG1M7(detailVo.getTaxAmtG1M7());
			worksheetDtl.setTaxAmtG1M8(detailVo.getTaxAmtG1M8());
			worksheetDtl.setTaxAmtG1M9(detailVo.getTaxAmtG1M9());
			worksheetDtl.setTaxAmtG1M10(detailVo.getTaxAmtG1M10());
			worksheetDtl.setTaxAmtG1M11(detailVo.getTaxAmtG1M11());
			worksheetDtl.setTaxAmtG1M12(detailVo.getTaxAmtG1M12());
			worksheetDtl.setTaxAmtG1M13(detailVo.getTaxAmtG1M13());
			worksheetDtl.setTaxAmtG1M14(detailVo.getTaxAmtG1M14());
			worksheetDtl.setTaxAmtG1M15(detailVo.getTaxAmtG1M15());
			worksheetDtl.setTaxAmtG1M16(detailVo.getTaxAmtG1M16());
			worksheetDtl.setTaxAmtG1M17(detailVo.getTaxAmtG1M17());
			worksheetDtl.setTaxAmtG1M18(detailVo.getTaxAmtG1M18());

			worksheetDtl.setTaxAmtG2M1(detailVo.getTaxAmtG2M1());
			worksheetDtl.setTaxAmtG2M2(detailVo.getTaxAmtG2M2());
			worksheetDtl.setTaxAmtG2M3(detailVo.getTaxAmtG2M3());
			worksheetDtl.setTaxAmtG2M4(detailVo.getTaxAmtG2M4());
			worksheetDtl.setTaxAmtG2M5(detailVo.getTaxAmtG2M5());
			worksheetDtl.setTaxAmtG2M6(detailVo.getTaxAmtG2M6());
			worksheetDtl.setTaxAmtG2M7(detailVo.getTaxAmtG2M7());
			worksheetDtl.setTaxAmtG2M8(detailVo.getTaxAmtG2M8());
			worksheetDtl.setTaxAmtG2M9(detailVo.getTaxAmtG2M9());
			worksheetDtl.setTaxAmtG2M10(detailVo.getTaxAmtG2M10());
			worksheetDtl.setTaxAmtG2M11(detailVo.getTaxAmtG2M11());
			worksheetDtl.setTaxAmtG2M12(detailVo.getTaxAmtG2M12());
			worksheetDtl.setTaxAmtG2M13(detailVo.getTaxAmtG2M13());
			worksheetDtl.setTaxAmtG2M14(detailVo.getTaxAmtG2M14());
			worksheetDtl.setTaxAmtG2M15(detailVo.getTaxAmtG2M15());
			worksheetDtl.setTaxAmtG2M16(detailVo.getTaxAmtG2M16());
			worksheetDtl.setTaxAmtG2M17(detailVo.getTaxAmtG2M17());
			worksheetDtl.setTaxAmtG2M18(detailVo.getTaxAmtG2M18());

			worksheetDtl.setTaxAmtSd(NO_TAX_AMOUNT.equals(detailVo.getTaxAmtSd()) ? null : detailVo.getTaxAmtSd());
			worksheetDtl.setTaxAmtMean(NO_TAX_AMOUNT.equals(detailVo.getTaxAmtMean()) ? null : detailVo.getTaxAmtMean());
			worksheetDtl.setTaxAmtMaxPnt(NO_TAX_AMOUNT.equals(detailVo.getTaxAmtMaxPnt()) ? null : detailVo.getTaxAmtMaxPnt());
			worksheetDtl.setTaxAmtMinPnt(NO_TAX_AMOUNT.equals(detailVo.getTaxAmtMinPnt()) ? null : detailVo.getTaxAmtMinPnt());

			worksheetDtl.setCreatedBy(UserLoginUtils.getCurrentUsername());
			worksheetDtl.setCreatedDate(LocalDateTime.now());

			worksheetDtl.setLastAuditYear(detailVo.getLastAuditYear());

			worksheetfDtlList.add(worksheetDtl);
		}

		taWorksheetDtlRepository.batchInsert(worksheetfDtlList);
	}

	public List<String> findAllAnalysisNumber(TaxOperatorFormVo formVo) {
		String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeCode();
		String budgetYear = formVo.getBudgetYear();
		if (StringUtils.isEmpty(budgetYear)) {
			budgetYear = ExciseUtils.getCurrentBudgetYear();
		}
		logger.info("findAllDraftNumber officeCode={}, budgetYear={}", officeCode, budgetYear);

		return taWorksheetHdrRepository.findAllAnalysisNumberByOfficeCodeAndBudgetYear(officeCode, budgetYear);
	}

	public YearMonthVo getMonthStart(TaxOperatorFormVo formVo) {
		logger.info("getMonthStart draftNumber = {}", formVo.getDraftNumber());

		YearMonthVo ymVo = taWorksheetCondMainHdrRepository.findMonthStartByAnalysisNumber(formVo.getDraftNumber());

		String ymStart = ConvertDateUtils.formatDateToString(ConvertDateUtils.parseStringToDate(ymVo.getYearMonthStart(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN), ConvertDateUtils.MM_YYYY, ConvertDateUtils.LOCAL_TH);
		String ymEnd = ConvertDateUtils.formatDateToString(ConvertDateUtils.parseStringToDate(ymVo.getYearMonthEnd(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN), ConvertDateUtils.MM_YYYY, ConvertDateUtils.LOCAL_TH);

		ymVo.setYearMonthStart(ymStart);
		ymVo.setYearMonthEnd(ymEnd);

		return ymVo;
	}

	public TaxOperatorVo getDraftWorksheet(TaxOperatorFormVo formVo) {
		formVo.setAnalysisNumber(formVo.getDraftNumber());
		logger.info("getDraftWorksheet analysisNumber = {}", formVo.getAnalysisNumber());

		TaxOperatorVo vo = new TaxOperatorVo();
		if (StringUtils.isNotEmpty(formVo.getDraftNumber())) {
			List<TaxOperatorDetailVo> draftDtlList = taWorksheetDtlRepository.findByCriteria(formVo);
			vo.setDatas(TaxAuditUtils.prepareTaxOperatorDatatable(draftDtlList, formVo));
			vo.setCount(taWorksheetDtlRepository.countByCriteria(formVo));
		} else {
			vo.setDatas(new ArrayList<>());
			vo.setCount(0L);
		}

		return vo;
	}

}
