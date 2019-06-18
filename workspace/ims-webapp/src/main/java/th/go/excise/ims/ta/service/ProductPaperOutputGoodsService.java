package th.go.excise.ims.ta.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.go.excise.ims.common.util.ExcelUtils;
import th.go.excise.ims.ta.persistence.entity.TaPaperPr06D;
import th.go.excise.ims.ta.persistence.repository.TaPaperPr06DRepository;
import th.go.excise.ims.ta.persistence.repository.TaPaperPr06HRepository;
import th.go.excise.ims.ta.vo.ProductPaperFormVo;
import th.go.excise.ims.ta.vo.ProductPaperOutputGoodsVo;
import th.go.excise.ims.ws.persistence.repository.WsOasfri0100DRepository;

@Service
public class ProductPaperOutputGoodsService extends AbstractProductPaperService<ProductPaperOutputGoodsVo> {

	private static final Logger logger = LoggerFactory.getLogger(ProductPaperOutputGoodsService.class);

	private static final String PRODUCT_PAPER_OUTPUT_GOODS = "ตรวจสอบการจ่ายสินค้าสำเร็จรูป";

	@Autowired
	private TaPaperPr06HRepository taPaperPr06HRepository;
	@Autowired
	private TaPaperPr06DRepository taPaperPr06DRepository;
	@Autowired
	private WsOasfri0100DRepository wsOasfri0100DRepository;

	@Override
	protected Logger getLogger() {
		return logger;
	}
	
	@Override
	protected List<ProductPaperOutputGoodsVo> inquiryByWs(ProductPaperFormVo formVo) {
		logger.info("inquiryByWs");
		String desc = "ตรวจสอบการจ่ายสินค้าสำเร็จรูป";
		List<ProductPaperOutputGoodsVo> datalist = new ArrayList<ProductPaperOutputGoodsVo>();
		ProductPaperOutputGoodsVo data = null;
		for (int i = 0; i < 5; i++) {
			data = new ProductPaperOutputGoodsVo();
			data.setGoodsDesc(desc + (i + 1));
			data.setOutputGoodsQty("");
			data.setOutputDailyAccountQty("");
			data.setOutputMonthStatementQty("");
			data.setAuditQty("");
			data.setTaxGoodsQty("");
			data.setDiffQty("");
			datalist.add(data);
		}
		return datalist;
	}

	@Override
	protected List<ProductPaperOutputGoodsVo> inquiryByPaperPrNumber(ProductPaperFormVo formVo) {
		logger.info("inquiryByPaperPrNumber paperPrNumber={}", formVo.getPaperPrNumber());
		
		List<TaPaperPr06D> entityList = taPaperPr06DRepository.findByPaperPrNumber(formVo.getPaperPrNumber());
		List<ProductPaperOutputGoodsVo> voList = new ArrayList<>();
		ProductPaperOutputGoodsVo vo = null;
		for (TaPaperPr06D entity : entityList) {
			vo = new ProductPaperOutputGoodsVo();
			vo.setGoodsDesc(entity.getGoodsDesc());
			vo.setOutputGoodsQty(entity.getOutputGoodsQty() != null ? entity.getOutputGoodsQty().toString() : NO_VALUE);
			vo.setOutputDailyAccountQty(entity.getOutputDailyAccountQty() != null ? entity.getOutputDailyAccountQty().toString() : NO_VALUE);
			vo.setOutputMonthStatementQty(entity.getOutputMonthStatementQty() != null ? entity.getOutputMonthStatementQty().toString() : NO_VALUE);
			vo.setAuditQty(entity.getAuditQty() != null ? entity.getAuditQty().toString() : NO_VALUE);
			vo.setTaxGoodsQty(entity.getTaxGoodsQty() != null ? entity.getTaxGoodsQty().toString() : NO_VALUE);
			vo.setDiffQty(entity.getDiffQty() != null ? entity.getDiffQty().toString() : NO_VALUE);
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	protected byte[] exportData(List<ProductPaperOutputGoodsVo> voList, String exportType) {
		logger.info("exportData");
		
		// create spreadsheet
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(PRODUCT_PAPER_OUTPUT_GOODS);
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);

		// call style from utils
		CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle bgKeyIn = ExcelUtils.createThColorStyle(workbook, new XSSFColor(new java.awt.Color(91, 241, 218)));
		CellStyle bgCal = ExcelUtils.createThColorStyle(workbook, new XSSFColor(new java.awt.Color(251, 189, 8)));
		CellStyle thColor = ExcelUtils.createThColorStyle(workbook, new XSSFColor(new java.awt.Color(24, 75, 125)));
		CellStyle cellCenter = ExcelUtils.createCenterCellStyle(workbook);
		CellStyle cellRightBgStyle = ExcelUtils.createCellColorStyle(workbook, new XSSFColor(new java.awt.Color(192, 192, 192)), HorizontalAlignment.RIGHT, VerticalAlignment.TOP);
		CellStyle cellLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle cellRight = ExcelUtils.createRightCellStyle(workbook);

		// tbTH
		String[] tbTH = { "ลำดับ", "รายการ", "ปริมาณจ่ายสินค้าสำเร็จรูป" + "\n" + "ในใบกำกับภาษีขาย",
				"ปริมาณจ่ายสินค้าสำเร็จรูป" + "\n" + " (ภส. ๐๗-๐๒)",
				"ปริมาณจ่ายสินค้าสำเร็จรูป " + "\n" + "จากงบเดือน(ภส. ๐๗-๐๔)",
				"ปริมาณที่ได้จากการตรวจสอบ" + "\n" + "(1)", "ปริมาณจ่ายสินค้าสำเร็จรูป " + "\n" + "(ภส. ๐๓-๐๗ (2))",
				"ผลต่าง" + "\n" + "(1-2)"};
		for (int i = 0; i < tbTH.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(tbTH[i]);
			if (i > 1 && i < 4) {
				cell.setCellStyle(bgKeyIn);				
			} else if (i == 7) {
				cell.setCellStyle(bgCal);
			} else {
				cell.setCellStyle(thStyle);
			}

		}

		// width
		for (int i = 0; i < 8; i++) {
			if (i > 1) {
				sheet.setColumnWidth(i, 76 * 80);
			} else if (i == 1) {
				sheet.setColumnWidth(i, 76 * 150);
			}
		}

		// set data
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (ProductPaperOutputGoodsVo vo : voList) {
			row = sheet.createRow(rowNum);

			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getGoodsDesc());
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getOutputGoodsQty());
			cell.setCellStyle(cellRight);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getOutputDailyAccountQty());
			cell.setCellStyle(cellRight);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getOutputMonthStatementQty());
			cell.setCellStyle(cellRightBgStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getAuditQty());
			cell.setCellStyle(cellRightBgStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getTaxGoodsQty());
			cell.setCellStyle(cellRightBgStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(vo.getDiffQty());
			cell.setCellStyle(cellRightBgStyle);
			cellNum++;

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

	@Override
	public List<ProductPaperOutputGoodsVo> upload(ProductPaperFormVo formVo) {
		logger.info("readFileProductPaperInputGoods");
		logger.info("fileName " + formVo.getFile().getOriginalFilename());
		logger.info("type " + formVo.getFile().getContentType());

		List<ProductPaperOutputGoodsVo> dataList = new ArrayList<>();
		ProductPaperOutputGoodsVo data = null;

		try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(formVo.getFile().getBytes()))) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				data = new ProductPaperOutputGoodsVo();
				// Skip on first row
				if (row.getRowNum() == 0) {
					continue;
				}
				for (Cell cell : row) {

					if (cell.getColumnIndex() == 0) {
						// Column No.
						continue;
					} else if (cell.getColumnIndex() == 1) {
						// GoodsDesc
						data.setGoodsDesc(ExcelUtils.getCellValueAsString(cell));
					} else if (cell.getColumnIndex() == 2) {
						// OutputGoodsQty
						data.setOutputGoodsQty(ExcelUtils.getCellValueAsString(cell));
					} else if (cell.getColumnIndex() == 3) {
						// OutputDailyAccountQty
						data.setOutputDailyAccountQty(ExcelUtils.getCellValueAsString(cell));
					} else if (cell.getColumnIndex() == 4) {
						// OutputMonthStatementQty
						data.setOutputMonthStatementQty(ExcelUtils.getCellValueAsString(cell));
					} else if (cell.getColumnIndex() == 5) {
						// AuditQty
						data.setAuditQty(ExcelUtils.getCellValueAsString(cell));
					} else if (cell.getColumnIndex() == 6) {
						// TaxGoodsQty
						data.setTaxGoodsQty(ExcelUtils.getCellValueAsString(cell));
					} else if (cell.getColumnIndex() == 7) {
						// DiffQty
						data.setDiffQty(ExcelUtils.getCellValueAsString(cell));
					}
				}
				dataList.add(data);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return dataList;
	}

	@Override
	public void save(ProductPaperFormVo formVo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPaperPrNumberList(ProductPaperFormVo formVo) {
		return taPaperPr06HRepository.findPaperPrNumberByAuditPlanCode(formVo.getAuditPlanCode());
	}

}
