package th.go.excise.ims.ta.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import th.co.baiwa.buckwaframework.common.constant.ReportConstants.FILE_EXTENSION;
import th.co.baiwa.buckwaframework.common.constant.ReportConstants.PATH;
import th.co.baiwa.buckwaframework.common.constant.ReportConstants.REPORT_NAME;
import th.go.excise.ims.ta.vo.TaFormTS01171Vo;

public class TaFormTS01171ServiceTest {
	
	private static final String REPORT_FILE = PATH.TEST_PATH + "%s" + "." + FILE_EXTENSION.PDF;

	@Test
	public void test_generateReport() throws Exception {
		TaFormTS01171Service taFormTS01171Service = new TaFormTS01171Service();

		TaFormTS01171Vo formTS01171Vo = new TaFormTS01171Vo();
		formTS01171Vo.setFormTsNumber("001403");
		formTS01171Vo.setBookNumber1("กด 3434");
		formTS01171Vo.setBookNumber2("25623");
		formTS01171Vo.setDocTopic("หัวข้อของเอกสาร ดูหน่อยนะ");
		formTS01171Vo.setDocDate(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 4))));
		formTS01171Vo.setDocDear("");
		formTS01171Vo.setFactoryName("");
		formTS01171Vo.setNewRegId("");
		formTS01171Vo.setFactoryType("");
		formTS01171Vo.setFacAddrNo("");
		formTS01171Vo.setFacMooNo("");
		formTS01171Vo.setFacSoiName("");
		formTS01171Vo.setFacThnName("");
		formTS01171Vo.setFacTambolName("");
		formTS01171Vo.setFacAmphurName("");
		formTS01171Vo.setFacProvinceName("");
		formTS01171Vo.setFacZipCode("");
		formTS01171Vo.setBookType("");
		formTS01171Vo.setRefBookNumber1("");
		formTS01171Vo.setRefBookNumber2("");
		formTS01171Vo.setRefDocDate(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 5))));
		formTS01171Vo.setAuditDateStart(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 6))));
		formTS01171Vo.setAuditDateEnd(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 7))));
		formTS01171Vo.setFactDesc("");
		formTS01171Vo.setLawDesc("");
		formTS01171Vo.setFactoryName2("");
		formTS01171Vo.setTaxAmt(new BigDecimal("987654"));
		formTS01171Vo.setFineAmt(new BigDecimal("0"));
		formTS01171Vo.setExtraAmt(null);
		formTS01171Vo.setExciseTaxAmt(new BigDecimal("12121"));
		formTS01171Vo.setMoiAmt(new BigDecimal("23232"));
		formTS01171Vo.setSumAllTaxAmt(new BigDecimal("454545"));
		formTS01171Vo.setExtraDate(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 8))));
		formTS01171Vo.setOfficeDest("");
		formTS01171Vo.setOfficeDate(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 9))));
		formTS01171Vo.setOfficeTime("");
		formTS01171Vo.setSignOfficerFullName("");
		formTS01171Vo.setSignOfficerPosition("");
		formTS01171Vo.setOfficeName("");
		formTS01171Vo.setOffficePhone("");
		formTS01171Vo.setHeadOfficerFullName("");
		
		byte[] reportFile = taFormTS01171Service.generateReport(formTS01171Vo);
		IOUtils.write(reportFile, new FileOutputStream(new File(String.format(REPORT_FILE, REPORT_NAME.TA_FORM_TS01_17_1))));
	}
	
}
