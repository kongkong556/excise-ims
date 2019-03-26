package th.go.excise.ims.ta.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import th.co.baiwa.buckwaframework.common.constant.ReportConstants.FILE_EXTENSION;
import th.co.baiwa.buckwaframework.common.constant.ReportConstants.PATH;
import th.co.baiwa.buckwaframework.common.constant.ReportConstants.REPORT_NAME;
import th.go.excise.ims.ta.vo.TaFormTS0115DtlVo;
import th.go.excise.ims.ta.vo.TaFormTS0115Vo;

public class TaFormTS0115ServiceTest {

	private static final String REPORT_FILE = PATH.TEST_PATH + REPORT_NAME.TA_FORM_TS01_15 + "." + FILE_EXTENSION.PDF;

	@Test
	public void test_generateReport() throws Exception {
		TaFormTS0115Service taFormTS0115Service = new TaFormTS0115Service();

		TaFormTS0115Vo formTS0115Vo = new TaFormTS0115Vo();
		formTS0115Vo.setOfficeName("กรมสรรพสามิต");
		formTS0115Vo.setDocDate(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 15))));
		formTS0115Vo.setOwnerFullName("นาย วิทยารัตน์ สุรบดีพงษ์ ");
		formTS0115Vo.setFactoryType("3");
		formTS0115Vo.setFactoryName("ร้าน หอมจันทร์ฟราแกรนซ์");
		formTS0115Vo.setNewRegId("2557026887");
		formTS0115Vo.setFacAddrNo("559/26");
		formTS0115Vo.setFacSoiName("-");
		formTS0115Vo.setFacThnName("-");
		formTS0115Vo.setFacTambolName("สันทราย");
		formTS0115Vo.setFacAmphurName("เมืองเชียงราย");
		formTS0115Vo.setFacProvinceName("เชียงราย");
		formTS0115Vo.setFacZipCode("57000");
		formTS0115Vo.setSignOwnerFullName("นาย วิทยารัตน์ สุรบดีพงษ์");
		formTS0115Vo.setSignOfficerFullName("นาย ธนากร  ใจดี");
		formTS0115Vo.setSignWitnessFullName1("นายเจริญพร  แก้วตา");
		formTS0115Vo.setSignWitnessFullName2("นายสุทธิ์สาร ดวงใจ");

		TaFormTS0115DtlVo formTS0115DtlVo = null;
		List<TaFormTS0115DtlVo> formTS0115DtlVoList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			formTS0115DtlVo = new TaFormTS0115DtlVo();
			formTS0115DtlVo.setRecDate(java.sql.Date.valueOf(LocalDate.from(ThaiBuddhistDate.of(2562, 3, 15))));
			formTS0115DtlVo.setDutyTypeText("น้ำหอม " + (i + 1));
			formTS0115DtlVo.setTaxAmt(new BigDecimal("22"));
			formTS0115DtlVo.setFineAmt(new BigDecimal("33"));
			formTS0115DtlVo.setExtraAmt(new BigDecimal("44"));
			formTS0115DtlVo.setMoiAmt(new BigDecimal("500"));
			formTS0115DtlVo.setSumTaxAmt(new BigDecimal("1000"));
			formTS0115DtlVoList.add(formTS0115DtlVo);
		}
		formTS0115Vo.setTaFormTS0115DtlVoList(formTS0115DtlVoList);

		byte[] reportFile = taFormTS0115Service.generateReport(formTS0115Vo);
		IOUtils.write(reportFile, new FileOutputStream(new File(REPORT_FILE)));
	}

}