package th.go.excise.ims.ia.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.common.util.ConvertDateUtils;
import th.go.excise.ims.common.util.ExcelUtils;
import th.go.excise.ims.ia.persistence.entity.IaAuditPmResult;
import th.go.excise.ims.ia.persistence.repository.IaAuditPmResultRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.Int1306JdbcRepository;
import th.go.excise.ims.ia.util.ExciseDepartmentUtil;
import th.go.excise.ims.ia.vo.ExciseDepartmentVo;
import th.go.excise.ims.ia.vo.IaAuditPmResultVo;
import th.go.excise.ims.ia.vo.Int1306DataVo;
import th.go.excise.ims.ia.vo.Int1306FormVo;
import th.go.excise.ims.ia.vo.Int1306Vo;

@Service
public class Int1306Service {

	public static final String PM_ASSESS_AND_PY1_Y = "เพียงพอ เหมาะสม";
	public static final String PM_ASSESS_AND_PY1_N = "ไม่เพียงพอ";
	public static final String PM_QT_Y = "มีการดำเนินการจริง";
	public static final String PM_QT_N = "ไม่มี/ไม่เพียงพอ";
	public static final String PM_PY2_Y1 = "กิจกรรมครบ";
	public static final String PM_PY2_N1 = "กิจกรรมครบไม่ครบ";
	public static final String PM_PY2_Y2 = "เพียงพอ เหมาะสม";
	public static final String PM_PY2_N2 = "ไม่เพียงพอ/ไม่บรรลุวัตถุประสงค์";
	public static final String PM_COMMIT_Y = "มี/ร่วมกันประเมิน";
	public static final String PM_COMMIT_N = "ไม่มี/ไม่ได้ร่วมกันประเมิน";

	private static final Logger logger = LoggerFactory.getLogger(Int1306Service.class);

	@Autowired
	private Int1306JdbcRepository int1306JdbcRepository;

	@Autowired
	private IaAuditPmResultRepository iaAuditPmResultRepository;

	@Autowired
	private IaCommonService iaCommonService;

	public Int1306Vo findCriteria(Int1306FormVo request) {
		Int1306Vo response = null;
		try {
			Int1306DataVo data1 = int1306JdbcRepository.findIaAuditPmassessHByCriteria(request);
			Int1306DataVo data2 = int1306JdbcRepository.findIaAuditPmQtHByCriteria(request);
			Int1306DataVo data3 = int1306JdbcRepository.findIaAuditPy1HCriteria(request);
			Int1306DataVo data4 = int1306JdbcRepository.findIaAuditPy2HCriteria(request);
			Int1306DataVo data5 = int1306JdbcRepository.findIaAuditPmCommitHCriteria(request);
			List<Int1306DataVo> int1306List = new ArrayList<Int1306DataVo>();
			int1306List.add(data1);
			int1306List.add(data2);
			int1306List.add(data3);
			int1306List.add(data4);
			int1306List.add(data5);
			// set response
			response = new Int1306Vo();
			response.setAuditPmassessNo(data1.getAuditNo());
			response.setAuditPmqtNo(data2.getAuditNo());
			response.setAuditPy1No(data3.getAuditNo());
			response.setAuditPy2No(data4.getAuditNo());
			response.setAuditPmcommitNo(data5.getAuditNo());
			response.setDataList(int1306List);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return response;
	}

	public List<IaAuditPmResultVo> findAuditPmResultList() {
		List<IaAuditPmResult> auditPmResultsList = iaAuditPmResultRepository.findIaAuditPmResultAllDataActive();

		IaAuditPmResultVo pmResultVo = null;
		List<IaAuditPmResultVo> pmResultVoList = new ArrayList<>();
		for (IaAuditPmResult data : auditPmResultsList) {
			pmResultVo = new IaAuditPmResultVo();
			try {
				pmResultVo.setAuditPmresultSeq(data.getAuditPmresultSeq());
				pmResultVo.setOfficeCode(data.getOfficeCode());
				pmResultVo.setAuditDateFrom(ConvertDateUtils.formatDateToString(data.getAuditDateFrom(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
				pmResultVo.setAuditDateTo(ConvertDateUtils.formatDateToString(data.getAuditDateTo(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
				pmResultVo.setAuditPmresultNo(data.getAuditPmresultNo());
				pmResultVo.setBudgetYear(data.getBudgetYear());
				pmResultVo.setAuditPmassessNo(data.getAuditPmassessNo());
				pmResultVo.setAuditPmqtNo(data.getAuditPmqtNo());
				pmResultVo.setAuditPy1No(data.getAuditPy1No());
				pmResultVo.setAuditPy2No(data.getAuditPy2No());
				pmResultVo.setAuditPmcommitNo(data.getAuditPmcommitNo());
				pmResultVo.setDepAuditingSuggestion(data.getDepAuditingSuggestion());
				pmResultVo.setAuditSummary(data.getAuditSummary());
				pmResultVo.setAuditSuggestion(data.getAuditSuggestion());
				pmResultVo.setPersonAudity(data.getPersonAudity());
				pmResultVo.setPersonAudityPosition(data.getPersonAudityPosition());
				pmResultVo.setAuditer1(data.getAuditer1());
				pmResultVo.setAuditer1AudityPosition(data.getAuditer1AudityPosition());
				pmResultVo.setAuditer2(data.getAuditer2());
				pmResultVo.setAudite2AudityPosition(data.getAudite2AudityPosition());

				pmResultVoList.add(pmResultVo);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}
		return pmResultVoList;
	}

	public IaAuditPmResultVo save(IaAuditPmResultVo vo) {
		logger.info("saveLicListService : {} ", vo.getAuditPmresultNo());
		IaAuditPmResult pmResult = null;
		try {

			if (StringUtils.isNotBlank(vo.getAuditPmresultNo())) {
				pmResult = iaAuditPmResultRepository.findByAuditPmresultNo(vo.getAuditPmresultNo());
				pmResult.setDepAuditingSuggestion(vo.getDepAuditingSuggestion());
				pmResult.setAuditSummary(vo.getAuditSummary());
				pmResult.setAuditSuggestion(vo.getAuditSuggestion());
				pmResult.setPersonAudity(vo.getPersonAudity());
				pmResult.setPersonAudityPosition(vo.getPersonAudityPosition());
				pmResult.setAuditer1(vo.getAuditer1());
				pmResult.setAuditer1AudityPosition(vo.getAuditer1AudityPosition());
				pmResult.setAuditer2(vo.getAuditer2());
				pmResult.setAudite2AudityPosition(vo.getAudite2AudityPosition());

				pmResult = iaAuditPmResultRepository.save(pmResult);
				vo.setAuditPmresultNo(pmResult.getAuditPmresultNo());
			} else {
				pmResult = new IaAuditPmResult();
				pmResult.setOfficeCode(vo.getOfficeCode());
				pmResult.setAuditDateFrom(ConvertDateUtils.parseStringToDate(vo.getAuditDateFrom(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
				pmResult.setAuditDateTo(ConvertDateUtils.parseStringToDate(vo.getAuditDateTo(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
				pmResult.setAuditPmresultNo(iaCommonService.autoGetRunAuditNoBySeqName("PMR", vo.getOfficeCode(), "AUDIT_PMRESULT_NO_SEQ", 8));
				pmResult.setBudgetYear(vo.getBudgetYear());
				pmResult.setAuditPmassessNo(vo.getAuditPmassessNo());
				pmResult.setAuditPmqtNo(vo.getAuditPmqtNo());
				pmResult.setAuditPy1No(vo.getAuditPy1No());
				pmResult.setAuditPy2No(vo.getAuditPy2No());
				pmResult.setAuditPmcommitNo(vo.getAuditPmcommitNo());
				pmResult.setDepAuditingSuggestion(vo.getDepAuditingSuggestion());
				pmResult.setAuditSummary(vo.getAuditSummary());
				pmResult.setAuditSuggestion(vo.getAuditSuggestion());
				pmResult.setPersonAudity(vo.getPersonAudity());
				pmResult.setPersonAudityPosition(vo.getPersonAudityPosition());
				pmResult.setAuditer1(vo.getAuditer1());
				pmResult.setAuditer1AudityPosition(vo.getAuditer1AudityPosition());
				pmResult.setAuditer2(vo.getAuditer2());
				pmResult.setAudite2AudityPosition(vo.getAudite2AudityPosition());

				pmResult = iaAuditPmResultRepository.save(pmResult);
				vo.setAuditPmresultNo(pmResult.getAuditPmresultNo());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return vo;
	}

	public IaAuditPmResultVo findByAuditPmResultNo(String auditPmresultNo) {
		IaAuditPmResultVo data = null;
		IaAuditPmResult h = null;
		ExciseDepartmentVo excise = null;
		h = iaAuditPmResultRepository.findByAuditPmresultNo(auditPmresultNo);
		try {
			data = new IaAuditPmResultVo();
			data.setOfficeCode(h.getOfficeCode());
			data.setAuditDateFrom(ConvertDateUtils.formatDateToString(h.getAuditDateFrom(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			data.setAuditDateTo(ConvertDateUtils.formatDateToString(h.getAuditDateTo(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			data.setAuditPmresultNo(h.getAuditPmresultNo());
			data.setBudgetYear(h.getBudgetYear());
			data.setAuditPmassessNo(h.getAuditPmassessNo());
			data.setAuditPmqtNo(h.getAuditPmqtNo());
			data.setAuditPy1No(h.getAuditPy1No());
			data.setAuditPy2No(h.getAuditPy2No());
			data.setAuditPmcommitNo(h.getAuditPmcommitNo());
			data.setDepAuditingSuggestion(h.getDepAuditingSuggestion());
			data.setAuditSummary(h.getAuditSummary());
			data.setAuditSuggestion(h.getAuditSuggestion());
			data.setPersonAudity(h.getPersonAudity());
			data.setPersonAudityPosition(h.getPersonAudityPosition());
			data.setAuditer1(h.getAuditer1());
			data.setAuditer1AudityPosition(h.getAuditer1AudityPosition());
			data.setAuditer2(h.getAuditer2());
			data.setAudite2AudityPosition(h.getAudite2AudityPosition());

			excise = ExciseDepartmentUtil.getExciseDepartmentFull(h.getOfficeCode());
			data.setArea(excise.getArea());
			data.setSector(excise.getSector());
			data.setBranch(excise.getBranch());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return data;
	}

	public byte[] export(String auditLicdupNo) {

		/* create spreadsheet */
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("ประเมินการจัดวางระบบ");
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);

		/* call style from utils */
		CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle cellCenter = ExcelUtils.createCenterCellStyle(workbook);
		CellStyle cellLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle wrapText = ExcelUtils.createWrapTextStyle(workbook);

		/* tbTH */
		String[] tbTH = { "ลำดับ", "แบบรายการ/แนวทางการประเมิน", "ผลการสอบทาน", "ข้อเสนอแนะ", "สรุปผลการตรวจสอบ" };
		for (int i = 0; i < tbTH.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(tbTH[i]);
			cell.setCellStyle(thStyle);
		}

		int colIndex = 0;
		sheet.setColumnWidth(colIndex++, 10 * 256);
		sheet.setColumnWidth(colIndex++, 50 * 256);
		sheet.setColumnWidth(colIndex++, 50 * 256);
		sheet.setColumnWidth(colIndex++, 50 * 256);
		sheet.setColumnWidth(colIndex++, 50 * 256);

		IaAuditPmResult h = iaAuditPmResultRepository.findByAuditPmresultNo(auditLicdupNo);
		Int1306FormVo formVo = new Int1306FormVo();
		formVo.setOfficeCode(h.getOfficeCode());
		formVo.setBudgetYear(h.getBudgetYear());
		formVo.setAuditPmassessNo(h.getAuditPmassessNo());
		formVo.setAuditPmqtNo(h.getAuditPmqtNo());
		formVo.setAuditPy1No(h.getAuditPy1No());
		formVo.setAuditPy2No(h.getAuditPy2No());
		formVo.setAuditPmcommitNo(h.getAuditPmcommitNo());
		List<Int1306DataVo> dataList = new ArrayList<>();

		if (findCriteria(formVo).getDataList() != null && findCriteria(formVo).getDataList().size() > 0) {
			dataList = findCriteria(formVo).getDataList();
		}

		/* set data */
		rowNum = 1;
		cellNum = 0;
		int no = 1;

		for (Int1306DataVo data : dataList) {
			row = sheet.createRow(rowNum);

			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			if (StringUtils.isNotBlank(data.getTopic())) {
				cell.setCellValue(data.getTopic());
			} else {
				cell.setCellValue("-");
			}
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			if (StringUtils.isNotBlank(data.getEvident())) {
				cell.setCellValue(data.getEvident());
			} else {
				cell.setCellValue("-");
			}
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			if (StringUtils.isNotBlank(data.getSuggestion())) {
				cell.setCellValue(data.getSuggestion());
			} else {
				cell.setCellValue("-");
			}
			cell.setCellStyle(cellLeft);
			cellNum++;

			if (StringUtils.isNotBlank(data.getType())) {
				if ("PM_ASSESS".equals(data.getType()) || "PM_PY1".equals(data.getType())) {
					cell = row.createCell(cellNum);
					if ("Y".equals(data.getResult())) {
						cell.setCellValue(PM_ASSESS_AND_PY1_Y);
					} else if ("N".equals(data.getResult())) {
						cell.setCellValue(PM_ASSESS_AND_PY1_N);
					} else {
						cell.setCellValue("-");
					}
					cell.setCellStyle(cellLeft);
					cellNum++;
				} else if ("PM_QT".equals(data.getType())) {
					cell = row.createCell(cellNum);
					if ("Y".equals(data.getResult())) {
						cell.setCellValue(PM_QT_Y);
					} else if ("N".equals(data.getResult())) {
						cell.setCellValue(PM_QT_N);
					} else {
						cell.setCellValue("-");
					}
					cell.setCellStyle(cellLeft);
					cellNum++;
				} else if ("PM_PY2".equals(data.getType())) {
					cell = row.createCell(cellNum);

					if ("N".equals(data.getResult()) && "N".equals(data.getResult2())) {
						cell.setCellValue(PM_PY2_N1 + "\n\n\n" + PM_PY2_N2);
					} else if ("N".equals(data.getResult()) && "Y".equals(data.getResult2())) {
						cell.setCellValue(PM_PY2_N1 + "\n\n\n" + PM_PY2_Y2);
					} else if ("Y".equals(data.getResult()) && "N".equals(data.getResult2())) {
						cell.setCellValue(PM_PY2_Y1 + "\n\n\n" + PM_PY2_N2);
					} else if ("Y".equals(data.getResult()) && "Y".equals(data.getResult2())) {
						cell.setCellValue(PM_PY2_Y1 + "\n\n\n" + PM_PY2_Y2);
					} else if (StringUtils.isBlank(data.getResult()) && "Y".equals(data.getResult2())) {
						cell.setCellValue("-" + "\n\n\n" + PM_PY2_Y2);
					} else if ("Y".equals(data.getResult()) && StringUtils.isBlank(data.getResult2())) {
						cell.setCellValue(PM_PY2_Y1 + "\n\n\n" + "-");
					} else if (StringUtils.isBlank(data.getResult()) && "N".equals(data.getResult2())) {
						cell.setCellValue("-" + "\n\n\n" + PM_PY2_N2);
					} else if ("N".equals(data.getResult()) && StringUtils.isBlank(data.getResult2())) {
						cell.setCellValue(PM_PY2_N1 + "\n\n\n" + "-");
					} else {
						cell.setCellValue("-" + "\n\n\n" + "-");
					}
					cell.setCellStyle(cellLeft);
					cellNum++;

				} else if ("PM_COMMIT".equals(data.getType())) {
					cell = row.createCell(cellNum);
					if ("Y".equals(data.getResult())) {
						cell.setCellValue(PM_COMMIT_Y);
					} else if ("N".equals(data.getResult())) {
						cell.setCellValue(PM_COMMIT_N);
					} else {
						cell.setCellValue("-");
					}
					cell.setCellStyle(cellLeft);
					cellNum++;
				} else {
					cell = row.createCell(cellNum);
					cell.setCellValue("-");
					cell.setCellStyle(cellLeft);
					cellNum++;
				}

			} else {
				cell = row.createCell(cellNum);
				cell.setCellValue("-");
				cell.setCellStyle(cellLeft);
				cellNum++;
			}

			no++;
			rowNum++;
			cellNum = 0;
		}

		// set output
		byte[] content = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			workbook.write(outputStream);
			content = outputStream.toByteArray();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return content;
	}
}
