package th.co.baiwa.excise.report.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInputItem;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleExporterInputItem;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import th.co.baiwa.buckwaframework.common.util.ReportUtils;
import th.co.baiwa.buckwaframework.preferences.persistence.entity.Message;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.excise.report.bean.ReportJsonBean;
import th.co.baiwa.excise.ta.persistence.entity.TaYearPlanReport;
import th.co.baiwa.excise.ta.persistence.entity.YearPlan;
import th.co.baiwa.excise.ta.persistence.repository.TaYearPlanReportRepository;
import th.co.baiwa.excise.ta.persistence.repository.YearPlanRepository;
import th.co.baiwa.buckwaframework.common.util.BeanUtils; 

@Service
public class TaxAuditReportService {

	@Autowired
	private YearPlanRepository yearPlanRepository;

	@Autowired
	private TaYearPlanReportRepository taYearPlanReportRepository;

	public Message updateFlag(BigDecimal id) {
		Message message = null;
		try {
			if (id.intValue() != 0) {
				YearPlan data = yearPlanRepository.findOne(id);
				data.setFlag("2");
				data.setStatus("2293");
				yearPlanRepository.save(data);
				message = ApplicationCache.getMessage("MSG_00002");
			} else {
				message = ApplicationCache.getMessage("MSG_00003");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = ApplicationCache.getMessage("MSG_00003");
		}
		return message;
	}

	/*
	 * @SuppressWarnings("unchecked") public byte[] exciseTaxToPDF(ReportJsonBean
	 * reportJsonBean) throws IOException, JRException {
	 * 
	 * 
	 * Gson gson = new Gson(); Map<String, Object> params = new HashMap<String,
	 * Object>(); params = (Map<String, Object>)
	 * gson.fromJson(reportJsonBean.getJson(), params.getClass());
	 * 
	 * Date reqDate = new Date();
	 * 
	 * params.put("date", DateFormatUtils.format(reqDate, "dd /  MMMM  / yyyy", new
	 * Locale("th", "TH")));
	 * 
	 * JasperPrint jasperPrint = ReportUtils.exportReport("TaxAuditForm001", params,
	 * new JREmptyDataSource());
	 * 
	 * byte[] reportFile = JasperExportManager.exportReportToPdf(jasperPrint);
	 * 
	 * ReportUtils.closeResourceFileInputStream(params); return reportFile; }
	 */

	@SuppressWarnings("unchecked")
	public byte[] exciseTaxToPDF(ReportJsonBean reportJsonBean) throws IOException, JRException {

		Gson gson = new Gson();
		Map<String, Object> params = new HashMap<String, Object>();
		params = (Map<String, Object>) gson.fromJson(reportJsonBean.getJson(), params.getClass());

		params.put("date", DateFormatUtils.format(new Date(), "dd /  MMMM  / yyyy", new Locale("th", "TH")));

		JasperPrint jasperPrint1 = ReportUtils.exportReport("TaxAuditForm001", params, new JREmptyDataSource());
		JasperPrint jasperPrint2 = ReportUtils.exportReport("TaxAuditForm002", params, new JREmptyDataSource());

		List<ExporterInputItem> items = new ArrayList<ExporterInputItem>();
		items.add(new SimpleExporterInputItem(jasperPrint1));
		items.add(new SimpleExporterInputItem(jasperPrint2));

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(items));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		exporter.exportReport();
		byte[] content = outputStream.toByteArray();

		ReportUtils.closeResourceFileInputStream(params);

		return content;
	}

	public void saveTaYearPlanReport(TaYearPlanReport data) {
		TaYearPlanReport checkReport = null;
		try {
			checkReport = taYearPlanReportRepository.findByYearPlanId(data.getYearPlanId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (BeanUtils.isNotEmpty(checkReport)) {
			checkReport.setExciseArea(data.getExciseArea());
			checkReport.setExciseSubArea(data.getExciseSubArea());
			checkReport.setExciseId(data.getExciseId());
			checkReport.setCompanyName(data.getCompanyName());
			checkReport.setProduct(data.getProduct());
			checkReport.setRiskTypeDesc(data.getRiskTypeDesc());
			checkReport.setDateCalendar(data.getDateCalendar());
			checkReport.setCompanyAddress(data.getCompanyAddress());

			checkReport.setResultGetRaw(data.getResultGetRaw());
			checkReport.setResultGetRawBox(data.getResultGetRawBox());

			checkReport.setResultPayRaw(data.getResultPayRaw());
			checkReport.setResultPayRawBox(data.getResultPayRawBox());

			checkReport.setReceiptInvoiceRaw(data.getReceiptInvoiceRaw());
			checkReport.setReceiptInvoiceBox(data.getReceiptInvoiceBox());

			checkReport.setPayInvoiceRaw(data.getPayInvoiceRaw());
			checkReport.setPayInvoiceBox(data.getPayInvoiceBox());

			checkReport.setOfficer(data.getOfficer());

			taYearPlanReportRepository.save(checkReport);
		} else {
			taYearPlanReportRepository.save(data);
		}

	}

	public TaYearPlanReport dataReport(Long id) {
		TaYearPlanReport report = taYearPlanReportRepository.findByYearPlanId(id);
		return report;
	}

}
