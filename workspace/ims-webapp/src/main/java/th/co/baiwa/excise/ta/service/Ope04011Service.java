package th.co.baiwa.excise.ta.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.excise.domain.LabelValueBean;
import th.co.baiwa.excise.ta.persistence.dao.ReportCheckDao;
import th.co.baiwa.excise.ta.persistence.entity.TaxAuditResult;
import th.co.baiwa.excise.ta.persistence.repository.TaxAuditResultRepository;
import th.co.baiwa.excise.ta.persistence.vo.Ope04011FormVo;

@Service
public class Ope04011Service {

	@Autowired
	private TaxAuditResultRepository auditResultRepository;
	
	private final String PRODUCT = "สินค้า";
	private final String SERVICE = "บริการ";
	private final String PRODUCT_IMPORT = "สินค้านำเข้า";
	
	private final String PRODUCT_CODE = "1";
	private final String SERVICE_CODE = "2";
	private final String PRODUCT_IMPORT_CODE = "3";
	@Autowired
	private ReportCheckDao reportCheckDao;

	public List<LabelValueBean> findExciseId() {
		List<LabelValueBean> dataList = reportCheckDao.findExciseId();
		return dataList;
	}

	public Ope04011FormVo findByExciseId(Ope04011FormVo formVo) {
		
		 List<Ope04011FormVo> forms = reportCheckDao.findByExciseId(formVo);
		Ope04011FormVo form = forms.get(0);
		if (PRODUCT_CODE.equals(form.getType())) form.setType(PRODUCT);
		if (SERVICE_CODE.equals(form.getType())) form.setType(SERVICE);
		if (PRODUCT_IMPORT_CODE.equals(form.getType())) form.setType(PRODUCT_IMPORT);
		
		return form;
	}
	
	public void save(Ope04011FormVo formVo){
		TaxAuditResult entity = new TaxAuditResult();
		entity.setExciseId(formVo.getExciseId());
		entity.setAnalysNumber(formVo.getAnlysisNumber());
		entity.setCompanyName(formVo.getCompanyName());
		entity.setAddedTax(new BigDecimal(formVo.getAddedTax()));
		entity.setFine(new BigDecimal(formVo.getFine()));
		entity.setExtraMoney(new BigDecimal(formVo.getExtraMoney()));
		
		auditResultRepository.save(entity);
	}

}