package th.co.baiwa.excise.report.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import th.co.baiwa.buckwaframework.common.constant.ReportConstants.PATH;
import th.co.baiwa.buckwaframework.common.util.ReportUtils;
import th.co.baiwa.buckwaframework.common.util.ThaiNumberUtils;
import th.co.baiwa.excise.report.bean.ExampleBean;
import th.co.baiwa.excise.report.controller.ReportController;
import th.co.baiwa.excise.utils.BeanUtils;
import th.co.baiwa.excise.report.bean.ContractBean;

@Service
public class ReportService {

	private static String PATH_EXPORT = "c:/reports/";

	private Logger logger = LoggerFactory.getLogger(ReportService.class);

	public byte[] exampleToPDF(Integer num) throws IOException, JRException {
		String reportName = "Example";

		Map<String, Object> params = new HashMap<>();
		params.put("sendTo", "ผมชื่อ เฟรม แฟน น้องมุ๊ก ข้อความทดสอบ");
		params.put("logo", ReportUtils.getResourceFile(PATH.IMAGE_PATH, "garuda-emblem.jpg"));
		List<ExampleBean> exampleList = new ArrayList<>();
		ExampleBean exBean = null;
		for (int i = 0; i < num; i++) {
			exBean = new ExampleBean();
			exBean.setData1(String.valueOf(i + 1));
			exBean.setData2(new BigDecimal(i + 1));
			exBean.setData3(new Date());
			exampleList.add(exBean);
		}

		JasperPrint jasperPrint = ReportUtils.exportReport(reportName, params,
				new JRBeanCollectionDataSource(exampleList)); // JRBeanCollectionDataSource(exampleList)
		// JasperPrint jasperPrint = ReportUtils.exportReport(jasperFile, params, new
		// JREmptyDataSource());

		byte[] reportFile = JasperExportManager.exportReportToPdf(jasperPrint);
		IOUtils.write(reportFile, new FileOutputStream(new File(PATH_EXPORT + "Example.pdf"))); // /tmp/excise/ims/report/

		ReportUtils.closeResourceFileInputStream(params);

		return reportFile;
	}

	public byte[] objectToPDF(String name, String param) throws IOException, JRException {

		Gson gson = new Gson();
		Map<String, Object> params = new HashMap<String, Object>();
		params = (Map<String, Object>) gson.fromJson(param, params.getClass());
		List<Object> bean = new ArrayList<Object>();
		if (BeanUtils.isNotEmpty(params.get("Bean"))) {
			bean = (List<Object>) params.get("Bean");
		}

		JRDataSource dataSource = null;

		if (BeanUtils.isNotEmpty(params.get("logo"))) {
			String logo = params.get("logo").toString();
			params.remove("logo");
			params.put("logo", ReportUtils.getResourceFile(PATH.IMAGE_PATH, logo));
		}

		if (BeanUtils.isEmpty(bean)) {
			dataSource = new JREmptyDataSource();
		} else {
			dataSource = new JRBeanCollectionDataSource(bean);
		}

		JasperPrint jasperPrint = ReportUtils.exportReport(name, params, dataSource); // JRBeanCollectionDataSource(exampleList)
		// JasperPrint jasperPrint = ReportUtils.exportReport(jasperFile, params, new
		// JREmptyDataSource());

		byte[] reportFile = JasperExportManager.exportReportToPdf(jasperPrint);
		IOUtils.write(reportFile, new FileOutputStream(new File(PATH_EXPORT + name + ".pdf"))); // /tmp/excise/ims/report/

		ReportUtils.closeResourceFileInputStream(params);

		return reportFile;
	}

	public byte[] objectToPDF(String name, Map<String, Object> params, List<Object> bean)
			throws IOException, JRException {

		JRDataSource dataSource = null;

		if (BeanUtils.isNotEmpty(params.get("logo"))) {
			String logo = params.get("logo").toString();
			params.remove("logo");
			params.put("logo", ReportUtils.getResourceFile(PATH.IMAGE_PATH, logo));
		}

		if (BeanUtils.isEmpty(bean)) {
			dataSource = new JREmptyDataSource();
		} else {
			dataSource = new JRBeanCollectionDataSource(bean);
		}

		JasperPrint jasperPrint = ReportUtils.exportReport(name, params, dataSource); // JRBeanCollectionDataSource(exampleList)
		// JasperPrint jasperPrint = ReportUtils.exportReport(jasperFile, params, new
		// JREmptyDataSource());

		byte[] reportFile = JasperExportManager.exportReportToPdf(jasperPrint);
		IOUtils.write(reportFile, new FileOutputStream(new File(PATH_EXPORT + name + ".pdf"))); // /tmp/excise/ims/report/

		ReportUtils.closeResourceFileInputStream(params);

		return reportFile;
	}
}
