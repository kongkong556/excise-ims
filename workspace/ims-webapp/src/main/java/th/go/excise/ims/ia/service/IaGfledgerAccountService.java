package th.go.excise.ims.ia.service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import th.co.baiwa.buckwaframework.common.util.ConvertDateUtils;
import th.co.baiwa.buckwaframework.common.util.NumberUtils;
import th.go.excise.ims.common.util.ExcelUtils;
import th.go.excise.ims.ia.persistence.entity.IaGfledgerAccount;
import th.go.excise.ims.ia.persistence.repository.IaGfledgerAccountRepository;

@Service
public class IaGfledgerAccountService {

	@Autowired
	private IaGfledgerAccountRepository iaGfledgerAccountRepository;

	private final String KEY_FILTER[] = { "เลขที่บัญชี G/L", "รหัสหน่วยงาน", "ประเภท", "*" };

	public void addDataByExcel(MultipartFile file) throws Exception {
		List<IaGfledgerAccount> iaGfledgerAccountList = new ArrayList<>();
		IaGfledgerAccount iaGfledgerAccount = new IaGfledgerAccount();
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()));
		String valueExc = "";
		Sheet sheet = workbook.getSheetAt(0);
		String glAccNo = "";
		String depCode = "";
		for (Row r : sheet) {
			iaGfledgerAccount = new IaGfledgerAccount();
			try {
				
				String val;
				for (Cell c : r) {
					valueExc = ExcelUtils.getCellValueAsString(c) + " : " + c.getColumnIndex();
					val = ExcelUtils.getCellValueAsString(c);
					if (StringUtils.isNoneBlank(val)) {
						if (StringUtils.trim(val).equals(KEY_FILTER[0]) && c.getColumnIndex() == 0) {
							glAccNo = ExcelUtils.getCellValueAsString(r.getCell(5));
						} else if (StringUtils.trim(val).equals(KEY_FILTER[1]) && c.getColumnIndex() == 0) {
							depCode = ExcelUtils.getCellValueAsString(r.getCell(5));
						} else if (ExcelUtils.getCellValueAsString(r.getCell(1)) == null && ExcelUtils.getCellValueAsString(r.getCell(2)) != null && !StringUtils.trim(ExcelUtils.getCellValueAsString(r.getCell(2))).equals(KEY_FILTER[2])) {
							switch (c.getColumnIndex()) {
							case 2:
								iaGfledgerAccount.setType(val);
								break;
							case 3:
								iaGfledgerAccount.setPeriod(NumberUtils.toBigDecimal(val));
								break;
							case 4:
								iaGfledgerAccount.setDocDate(ConvertDateUtils.parseStringToDate(val, ConvertDateUtils.DD_MM_YYYY_DOT));
								break;
							case 6:
								iaGfledgerAccount.setPostingDate(ConvertDateUtils.parseStringToDate(val, ConvertDateUtils.DD_MM_YYYY_DOT));
								break;
							case 8:
								iaGfledgerAccount.setDocNo(val);
								break;
							case 9:
								iaGfledgerAccount.setRefCode(val);
								break;
							case 10:
								iaGfledgerAccount.setCurrAmt(NumberUtils.toBigDecimal(val));
								break;
							case 11:
								iaGfledgerAccount.setPkCode(val);
								break;
							case 12:
								iaGfledgerAccount.setRorKor(val);
								break;
							case 13:
								iaGfledgerAccount.setDeterminaton(val);
								break;
							case 14:
								iaGfledgerAccount.setMsg(val);
								break;
							case 15:
								iaGfledgerAccount.setKeyRef3(val);
								break;
							case 16:
								iaGfledgerAccount.setKeyRef1(val);
								break;
							case 17:
								iaGfledgerAccount.setKeyRef2(val);
								break;
							case 18:
								iaGfledgerAccount.setHlodingTaxes(NumberUtils.toBigDecimal(val));
								break;
							case 19:
								iaGfledgerAccount.setDepositAcc(val);
								break;
							case 20:
								iaGfledgerAccount.setAccType(val);
								break;
							case 21:
								iaGfledgerAccount.setCostCenter(val);
								break;
							case 22:
								iaGfledgerAccount.setDeptDisb(val);
								break;
							case 23:
								iaGfledgerAccount.setClrngDoc(val);
								break;
								

							default:
								break;
							}
						}
					}
				}
				if (StringUtils.isNoneBlank(iaGfledgerAccount.getDocNo())) {
					iaGfledgerAccount.setGlAccNo(glAccNo);
					iaGfledgerAccount.setDepCode(depCode);
					iaGfledgerAccountList.add(iaGfledgerAccount);
				}
			} catch (Exception e) {

			}
		}

		try {
			iaGfledgerAccountRepository.insertBatch(iaGfledgerAccountList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
