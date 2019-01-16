package th.co.baiwa.excise.ia.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.baiwa.buckwaframework.preferences.persistence.entity.Lov;
import th.co.baiwa.buckwaframework.preferences.persistence.repository.LovRepository;
import th.co.baiwa.buckwaframework.security.util.UserLoginUtils;
import th.co.baiwa.excise.constant.DateConstant;
import th.co.baiwa.buckwaframework.common.bean.DataTableAjax;
import th.co.baiwa.excise.ia.persistence.dao.CheckStampBranchDao;
import th.co.baiwa.excise.ia.persistence.entity.IaStampDetail;
import th.co.baiwa.excise.ia.persistence.entity.IaStampFile;
import th.co.baiwa.excise.ia.persistence.repository.IaStamDetailRepository;
import th.co.baiwa.excise.ia.persistence.repository.IaStampFileRepository;
import th.co.baiwa.excise.ia.persistence.vo.Int05111FormVo;
import th.co.baiwa.excise.ia.persistence.vo.Int05111Vo;
import th.co.baiwa.excise.ia.persistence.vo.Int0511Vo;

@Service
public class Int05111Service {

	@Autowired
	private CheckStampBranchDao checkStampBranchDao;

	@Autowired
	private IaStamDetailRepository iaStamDetailRepository;

	@Autowired
	private IaStampFileRepository iaStampFileRepository;

	@Autowired
	private LovRepository lovRepository;

	public DataTableAjax<Int05111Vo> findAll(Int05111FormVo formVo) {
		
		DataTableAjax<Int05111Vo> dataTableAjax = new DataTableAjax<>();
		if ("TRUE".equalsIgnoreCase(formVo.getSearchFlag())) {
			
			String officeCode = mappingOfficeCode(formVo);
			formVo.setOfficeCode(officeCode);
			formVo.setDateForm(DateConstant.convertStrDDMMYYYYToStrYYYYMMDD(formVo.getDateForm()));
			formVo.setDateTo(DateConstant.convertStrDDMMYYYYToStrYYYYMMDD(formVo.getDateTo()));
			List<Int05111Vo> list = checkStampBranchDao.findAll(formVo);
			Long count = checkStampBranchDao.count(formVo);			
			
			dataTableAjax.setDraw(formVo.getDraw() + 1);
			dataTableAjax.setRecordsTotal(count);
			dataTableAjax.setRecordsFiltered(count);
			dataTableAjax.setData(list);
		}

		return dataTableAjax;
	}

	public List<Lov> sector() {
		List<Lov> lov = lovRepository.findByTypeAndLovIdMasterIsNullOrderBySubType("SECTOR_VALUE");
		return lov;
	}

	public List<Lov> area(Long idMaster) {
        return lovRepository.findByLovIdMasterOrderBySubType(idMaster);
    }
	
	public List<Lov> branch(Long idMaster) {
        return lovRepository.findByLovIdMasterOrderBySubType(idMaster);
    }


	@Transactional
	public void save(Int05111FormVo formVo) {

		Int0511Vo form = formVo.getData();
		IaStampDetail entity = iaStamDetailRepository.findOne(Long.valueOf(form.getWorkSheetDetailId()));

		/* officeCode */
		String officeCode = UserLoginUtils.getCurrentUserBean().getOfficeId();
		entity.setOfficeCode(officeCode);
		Lov lov = lovRepository.findBySubType(officeCode);
		entity.setOfficeDesc(lov.getSubTypeDescription());
	
		entity.setDateOfPay(DateConstant.convertStrDDMMYYYYToDate(form.getDateOfPay()));
		entity.setStatus(form.getStatus());
		entity.setBookNumberWithdrawStamp(form.getBookNumberWithdrawStamp());
		entity.setDateWithdrawStamp(DateConstant.convertStrDDMMYYYYToDate(form.getDateWithdrawStamp()));
		entity.setBookNumberDeliverStamp(form.getBookNumberDeliverStamp());
		entity.setDateDeliverStamp(DateConstant.convertStrDDMMYYYYToDate(form.getDateDeliverStamp()));
		entity.setFivePartNumber(form.getFivePartNumber());
		entity.setFivePartDate(DateConstant.convertStrDDMMYYYYToDate(form.getFivePartDate()));
		entity.setStampCheckDate(DateConstant.convertStrDDMMYYYYToDate(form.getStampCheckDate()));
		entity.setStampChecker(form.getStampChecker());
		entity.setStampType(form.getStampType());
		entity.setStampBrand(form.getStampBrand());
		entity.setNumberOfBook(form.getNumberOfBook());
		entity.setNumberOfStamp(form.getNumberOfStamp());
		entity.setValueOfStampPrinted(form.getValueOfStampPrinted());
		entity.setSumOfValue(form.getSumOfValue());
		entity.setTaxStamp(form.getTaxStamp());
		entity.setStampCodeStart(form.getStampCodeStart());
		entity.setStampCodeEnd(form.getStampCodeEnd());
		entity.setNote(form.getNote());
		entity.setCreatedDate(DateConstant.convertStrDDMMYYYYToDate(form.getCreatedDate()));
		iaStamDetailRepository.save(entity);
	}

	public void delete(Int05111FormVo formvo) {

		IaStampDetail entity = iaStamDetailRepository.findOne(Long.valueOf(formvo.getData().getWorkSheetDetailId()));
		iaStamDetailRepository.delete(entity);
	}

	public List<String> listFile(String id) {
		List<String> fileName = new ArrayList<>();
		List<IaStampFile> list = iaStampFileRepository.findByDetailId(id);
		for (IaStampFile iaStampFile : list) {
			fileName.add(iaStampFile.getFileName());
		}
		return fileName;
	}
	
	public List<Lov> status(){
		return lovRepository.findByType("STAMP");
	}
	
	public String mappingOfficeCode(Int05111FormVo formVo) {
		Lov sectors = new Lov();
		Lov areas = new Lov();
		Lov branchs = new Lov();
		
		if (StringUtils.isNotBlank(formVo.getSector())) {
			sectors = lovRepository.findByLovId(Long.valueOf(formVo.getSector()));
			
			if (StringUtils.isNotBlank(formVo.getArea())) {
				areas = lovRepository.findByLovId(Long.valueOf(formVo.getArea()));
				
				if (StringUtils.isNotBlank(formVo.getBranch())) {
					branchs = lovRepository.findByLovId(Long.valueOf(formVo.getBranch()));
				}
			}
		}
		
		StringBuilder officeCode = new StringBuilder("");
		
		if (sectors != null) {
			if(StringUtils.isNoneBlank(sectors.getSubType())) {
				officeCode.append(sectors.getSubType());
			}
		}
		if (areas != null) {
			if(StringUtils.isNoneBlank(areas.getSubType())) {
				officeCode.append(areas.getSubType());
			}
		}
		if (branchs != null) {
			if(StringUtils.isNoneBlank(branchs.getSubType())) {
				officeCode.append(branchs.getSubType());
			}
		}
				
		return officeCode.toString();
	}
}

