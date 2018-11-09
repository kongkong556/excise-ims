package th.co.baiwa.excise.ta.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import th.co.baiwa.excise.ta.persistence.vo.Ope041DataTable;
import th.co.baiwa.excise.ta.persistence.vo.Ope043DataTable;
import th.co.baiwa.excise.ta.persistence.vo.Ope044Vo;
import th.co.baiwa.excise.ta.persistence.vo.Ope046Vo;
import th.co.baiwa.excise.ta.persistence.vo.Ope048Vo;

@Service
public class Ope04ExcelService {
	private CellStyle thStyle;
	private CellStyle cellCenter;
	private CellStyle cellRight;
	private CellStyle cellLeft;

	private CellStyle topCenter;
	private CellStyle topRight;
	private CellStyle topLeft;
	private Font fontHeader;

	private XSSFWorkbook setUpExcel() {
		XSSFWorkbook workbook = new XSSFWorkbook();

		thStyle = workbook.createCellStyle();
		thStyle.setAlignment(HorizontalAlignment.CENTER);
		thStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		thStyle.setBorderBottom(BorderStyle.THIN);
		thStyle.setBorderLeft(BorderStyle.THIN);
		thStyle.setBorderRight(BorderStyle.THIN);
		thStyle.setBorderTop(BorderStyle.THIN);
		thStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		thStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cellCenter = workbook.createCellStyle();
		cellCenter.setAlignment(HorizontalAlignment.CENTER);
		cellCenter.setBorderBottom(BorderStyle.THIN);
		cellCenter.setBorderLeft(BorderStyle.THIN);
		cellCenter.setBorderRight(BorderStyle.THIN);
		cellCenter.setBorderTop(BorderStyle.THIN);

		cellRight = workbook.createCellStyle();
		cellRight.setAlignment(HorizontalAlignment.RIGHT);
		cellRight.setBorderBottom(BorderStyle.THIN);
		cellRight.setBorderLeft(BorderStyle.THIN);
		cellRight.setBorderRight(BorderStyle.THIN);
		cellRight.setBorderTop(BorderStyle.THIN);

		cellLeft = workbook.createCellStyle();
		cellLeft.setAlignment(HorizontalAlignment.LEFT);
		cellLeft.setBorderBottom(BorderStyle.THIN);
		cellLeft.setBorderLeft(BorderStyle.THIN);
		cellLeft.setBorderRight(BorderStyle.THIN);
		cellLeft.setBorderTop(BorderStyle.THIN);

		fontHeader = workbook.createFont();
		fontHeader.setBold(true);

		topCenter = workbook.createCellStyle();
		topCenter.setAlignment(HorizontalAlignment.CENTER);
		topCenter.setFont(fontHeader);

		topRight = workbook.createCellStyle();
		topRight.setAlignment(HorizontalAlignment.RIGHT);

		topLeft = workbook.createCellStyle();
		topLeft.setAlignment(HorizontalAlignment.LEFT);
		return workbook;
	}

	public ByteArrayOutputStream exportOpe041(List<Ope041DataTable> dataList) throws IOException {
		/* create spreadsheet */
		XSSFWorkbook workbook = setUpExcel();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);
		System.out.println("Creating excel");
		row = sheet.createRow(0);

		/* Header */
		String[] tbTH1 = { "ลำดับ", "รายการ", "ใบกำกับภาษี", "บัญชีประจำวัน", "งบเดือน", "จำนวนรับน้ำมัน", };
		for (cellNum = 0; cellNum < tbTH1.length; cellNum++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(tbTH1[cellNum]);
			cell.setCellStyle(thStyle);
		}
		;

		/* set sheet */
		for (int i = 2; i <= 5; i++) {
			if (i != 1) {
				sheet.setColumnWidth(i, 76 * 50);
			}
		}
		sheet.setColumnWidth(0, 76 * 35);
		sheet.setColumnWidth(1, 76 * 150);

		/* Detail */
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (Ope041DataTable data : dataList) {
			row = sheet.createRow(rowNum);
			// No.
			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			// Product
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getProduct());
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(thStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			no++;
			rowNum++;
			cellNum = 0;
		}

		/* EndDetail */

		/* set write */
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		return outByteStream;
	}

	public ByteArrayOutputStream exportOpe042(List<Ope041DataTable> dataList) throws IOException {
		/* create spreadsheet */
		XSSFWorkbook workbook = setUpExcel();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);
		System.out.println("Creating excel");
		row = sheet.createRow(0);

		/* Header */
		String[] tbTH1 = { "ลำดับ", "รายการ", "ใบเบิกวัตถุดิบ", "บัญชีประจำวัน", "งบเดือน", "จำนวนรับน้ำมัน", };
		for (cellNum = 0; cellNum < tbTH1.length; cellNum++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(tbTH1[cellNum]);
			cell.setCellStyle(thStyle);
		}
		;

		/* set sheet */
		for (int i = 2; i <= 5; i++) {
			sheet.setColumnWidth(i, 76 * 50);
		}
		sheet.setColumnWidth(0, 76 * 35);
		sheet.setColumnWidth(1, 76 * 150);

		/* Detail */
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (Ope041DataTable data : dataList) {
			row = sheet.createRow(rowNum);
			// No.
			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			// Product
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getProduct());
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(thStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			no++;
			rowNum++;
			cellNum = 0;
		}

		/* EndDetail */

		/* set write */
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		return outByteStream;
	}

	public ByteArrayOutputStream exportOpe043(List<Ope043DataTable> dataList) throws IOException {
		/* create spreadsheet */
		XSSFWorkbook workbook = setUpExcel();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);
		System.out.println("Creating excel");
		row = sheet.createRow(0);

		/* Header */
		String[] tbTH1 = { "ลำดับ", "รายการ", "รับชื่อฟิลด์จาก Excel 1", "รับชื่อฟิลด์จาก Excel 2",
				"ยอดคงเหลือจากการตรวจนับ" };
		for (cellNum = 0; cellNum < tbTH1.length; cellNum++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(tbTH1[cellNum]);
			cell.setCellStyle(thStyle);
		}
		;

		/* set sheet */
		sheet.setColumnWidth(0, 76 * 35);
		sheet.setColumnWidth(1, 76 * 150);

		for (int i = 2; i <= 4; i++) {
			sheet.setColumnWidth(i, 76 * 100);
		}

		/* Detail */
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (Ope043DataTable data : dataList) {
			row = sheet.createRow(rowNum);
			// No.
			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			// Order
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getOrder());
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(thStyle);
			cellNum++;

			no++;
			rowNum++;
			cellNum = 0;
		}

		/* EndDetail */

		/* set write */
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		return outByteStream;
	}

	public ByteArrayOutputStream exportOpo044(List<Ope044Vo> dataList) throws IOException {
		/* create spreadsheet */
		XSSFWorkbook workbook = setUpExcel();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);
		System.out.println("Creating excel");
		row = sheet.createRow(0);

		/* Header */
		String[] tbTH1 = { "ลำดับ", "รายการ", "ปริมาณรับจากการผลิต ตามงบเดือน", "ปริมาณรับจากการผลิต แบบ ภส.07-02",
				"ใบรับสินค้าสำเร็จรูป", "ราคาจากการตรวจสอบ", "ผลต่างคู่ข้อมูล", };
		for (cellNum = 0; cellNum < tbTH1.length; cellNum++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(tbTH1[cellNum]);
			cell.setCellStyle(thStyle);
		}
		;

		/* set sheet */
		sheet.setColumnWidth(0, 76 * 35);
		sheet.setColumnWidth(1, 76 * 200);

		for (int i = 2; i <= 6; i++) {
			sheet.setColumnWidth(i, 76 * 100);
		}

		/* Detail */
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (Ope044Vo data : dataList) {
			row = sheet.createRow(rowNum);
			// No.
			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			// Order
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getOrder());
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			no++;
			rowNum++;
			cellNum = 0;
		}

		/* EndDetail */

		/* set write */
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		return outByteStream;
	}

	public ByteArrayOutputStream exportOpo046(List<Ope046Vo> dataList) throws IOException {
		/* create spreadsheet */
		XSSFWorkbook workbook = setUpExcel();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);
		System.out.println("Creating excel");
		row = sheet.createRow(0);

		/* Header */
		String[] tbTH1 = { "ลำดับ", "รายการ", "เลขที่ใบเสร็จ", "จำนวนภาษี", "ปริมาณ", "ภาษีต่อหน่วย" };
		for (cellNum = 0; cellNum < tbTH1.length; cellNum++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(tbTH1[cellNum]);
			cell.setCellStyle(thStyle);
		}
		;

		/* set sheet */
		sheet.setColumnWidth(0, 76 * 35);
		sheet.setColumnWidth(1, 76 * 200);

		for (int i = 2; i <= 5; i++) {
			sheet.setColumnWidth(i, 76 * 100);
		}

		/* Detail */
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (Ope046Vo data : dataList) {
			row = sheet.createRow(rowNum);
			// No.
			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			// Order
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getTaExciseAcc0502DtlList());
			cell.setCellStyle(cellLeft);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			no++;
			rowNum++;
			cellNum = 0;
		}

		/* EndDetail */

		/* set write */
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		return outByteStream;
	}

	public ByteArrayOutputStream exportOpo048(List<Ope048Vo> dataList) throws IOException {
		/* create spreadsheet */
		XSSFWorkbook workbook = setUpExcel();
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);
		System.out.println("Creating excel");
		row = sheet.createRow(0);

		/* Header */
		String[] tbTH1 = { "ลำดับ", "รหัสรายการ", "รายการ", "ราคาสินค้าตามแบบแจ้ง",
				"ราคาจากข้อมูลภายนอก", "ราคาต่อหน่วยตามประกาศกรมสรรพสามิต", "ราคาจากการตรวจสอบ", };
		for (cellNum = 0; cellNum < tbTH1.length; cellNum++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(tbTH1[cellNum]);
			cell.setCellStyle(thStyle);
		}
		;

		/* set sheet */
		sheet.setColumnWidth(0, 76 * 35);
		sheet.setColumnWidth(1, 76 * 35);
		sheet.setColumnWidth(2, 76 * 200);

		for (int i = 3; i <= 6; i++) {
			sheet.setColumnWidth(i, 76 * 120);
		}

		/* Detail */
		rowNum = 1;
		cellNum = 0;
		int no = 1;
		for (Ope048Vo data : dataList) {
			row = sheet.createRow(rowNum);
			// No.
			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellValue(no);
			cell.setCellStyle(cellCenter);
			cellNum++;

			// Order
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getTaExciseAcc0307List());
			cell.setCellStyle(cellLeft);
			cellNum++;
			
			cell = row.createCell(cellNum);
			cell.setCellStyle(thStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(cellCenter);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(thStyle);
			cellNum++;

			cell = row.createCell(cellNum);
			cell.setCellStyle(thStyle);
			cellNum++;


			no++;
			rowNum++;
			cellNum = 0;
		}

		/* EndDetail */

		/* set write */
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		return outByteStream;
	}

}
