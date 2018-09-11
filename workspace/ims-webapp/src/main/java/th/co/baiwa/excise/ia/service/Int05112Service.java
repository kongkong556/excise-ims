package th.co.baiwa.excise.ia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.excise.constant.DateConstant;
import th.co.baiwa.excise.domain.datatable.DataTableAjax;
import th.co.baiwa.excise.ia.persistence.entity.IaStamGenre;
import th.co.baiwa.excise.ia.persistence.entity.IaStamType;
import th.co.baiwa.excise.ia.persistence.entity.IaStampDetail;
import th.co.baiwa.excise.ia.persistence.repository.IaStamDetailRepository;
import th.co.baiwa.excise.ia.persistence.repository.IaStamGenreRepository;
import th.co.baiwa.excise.ia.persistence.repository.IaStamTypeRepository;
import th.co.baiwa.excise.ia.persistence.vo.Int05112Vo;

@Service
public class Int05112Service {

	@Autowired
	private IaStamTypeRepository iaStamTypeRepository;

	@Autowired
	private IaStamGenreRepository genreRepository;
	
	@Autowired
	private IaStamDetailRepository iaStamDetailRepository;

	private final String PAY="จ่าย";
	public DataTableAjax<Int05112Vo> findAll(Int05112Vo req) {
		
		List<Int05112Vo> list = new ArrayList<>();
		addHeader(list);
		addColum(list);
		DataTableAjax<Int05112Vo> dataTableAjax = new DataTableAjax<>();
		
		dataTableAjax.setRecordsTotal(Long.valueOf(list.size()));
		dataTableAjax.setRecordsFiltered(Long.valueOf(list.size()));
		dataTableAjax.setData(list);

		return dataTableAjax;
	}
	
	public List<Int05112Vo> addHeader(List<Int05112Vo> list) {
		
		List<IaStamType> stampType = iaStamTypeRepository.findAll();
		if (!stampType.isEmpty()) {
			for (IaStamType type : stampType) {
				
				Int05112Vo voType = new Int05112Vo();
				voType.setOrder(String.valueOf(type.getStampTypeId()));
				voType.setStampType(type.getStampTypeName());				
				list.add(voType);
				
				List<IaStamGenre> stampGenre = genreRepository.findByStampTypeId(String.valueOf(type.getStampTypeId()));
				if (!stampGenre.isEmpty()) {
					int count = 1;
					for (IaStamGenre genre : stampGenre) {
						Int05112Vo vo = new Int05112Vo();
						
						vo.setColumnId(String.valueOf(genre.getStampGenreId()));
						vo.setOrder(String.valueOf(type.getStampTypeId())+"."+count++);
						vo.setStampType(genre.getStampGenreName());
						list.add(vo);
					}
				}
				
			}	
		}			
		return list;
	}
	
	public void addColum(List<Int05112Vo> list) {		
		
		List<IaStampDetail> detailList = iaStamDetailRepository.findAll();
				
		if (!detailList.isEmpty()) {
			for (IaStampDetail detail : detailList) {
				int index = 0;
				for (Int05112Vo vo : list) {
					if (vo.getColumnId()!=null) {
						if (detail.getStampBrandId().equals(Long.valueOf(vo.getColumnId()))) {							
							break;
						}
					}					
					index++;					
				}
				
				/*set data*/
				Int05112Vo result = list.get(index);
				String payOfDate = DateConstant.convertDateToStr(detail.getDateOfPay(), "MM");
				checkMonth(result, detail.getStatus(), payOfDate);							
								
				/*receive & pay of year*/
				BigDecimal moneyOfYear = detail.getNumberOfStamp().multiply(detail.getValueOfStampPrinted());
				if (PAY.equals(detail.getStatus())) {
					result.setSummaryYearPay("");
					result.setSummaryYearMoneyPay(result.getSummaryYearMoneyPay().add(moneyOfYear));
				}else {
					result.setSummaryYearRecieve("");
					result.setSummaryYearMoneyRecieve(result.getSummaryYearMoneyRecieve().add(moneyOfYear));
				}										
			}
		}
		
	}
	
	public void checkMonth(Int05112Vo vo, String status,String month) {
		if("01".equals(month)) {
			if (PAY.equals(status)) {
				vo.setJanuaryPay(status);				
				return;
			}else {
				vo.setJanuaryRecieve(status);
				return;
			}			
		}
		if("02".equals(month)) {
			if (PAY.equals(status)) {
				vo.setFebruaryPay(status);
				return;
			}else {
				vo.setFebruaryPay(status);
				return;
			}
		}
		if("03".equals(month)) {
			if (PAY.equals(status)) {
				vo.setMarchPay(status);
				return;
			}else {
				vo.setMarchRecieve(status);
				return;
			}
		}
		if("04".equals(month)) {
			if (PAY.equals(status)) {
				vo.setAprilPay(status);
				return;
			}else {
				vo.setAprilRecieve(status);
				return;
			}
		}
		if("05".equals(month)) {
			if (PAY.equals(status)) {
				vo.setMayPay(status);
				return;
			}else {
				vo.setMayRecieve(status);
				return;
			}
		}
		if("06".equals(month)) {
			if (PAY.equals(status)) {
				vo.setJunePay(status);
				return;
			}else {
				vo.setJuneRecieve(status);
				return;
			}
		}
		if("07".equals(month)) {
			if (PAY.equals(status)) {
				vo.setJulyPay(status);
				return;
			}else {
				vo.setJulyRecieve(status);
				return;
			}
		}
		if("08".equals(month)) {
			if (PAY.equals(status)) {
				vo.setAugustPay(status);
				return;
			}else {
				vo.setAugustRecieve(status);
				return;
			}
		}
		if("09".equals(month)) {
			if (PAY.equals(status)) {
				vo.setSeptemberPay(status);
				return;
			}else {
				vo.setSeptemberRecieve(status);
				return;
			}
		}
		if("10".equals(month)) {
			if (PAY.equals(status)) {
				vo.setOctoberPay(status);
				return;
			}else {
				vo.setOctoberRecieve(status);
				return;
			}
		}
		if("11".equals(month)) {
			if (PAY.equals(status)) {
				vo.setNovemberPay(status);
				return;
			}else {
				vo.setNovemberRecieve(status);
				return;
			}
		}
		if("12".equals(month)) {
			if (PAY.equals(status)) {
				vo.setDecemberPay(status);
				return;
			}else {
				vo.setDecemberRecieve(status);
				return;
			}
		}
	}
	
	
}
