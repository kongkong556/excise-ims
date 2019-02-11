package th.go.excise.ims.ta.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.go.excise.ims.ta.persistence.repository.TaxOperatorRepository;
import th.go.excise.ims.ta.vo.TaxOperatorVo;
import th.go.excise.ims.ta.vo.TaxOperatorVo.TaxPay;

@Service
public class TaxOperatorService {

	@Autowired
	private TaxOperatorRepository taxOperatorRepository;

	public List<TaxOperatorVo> getOperator(TaxOperatorVo.TaxOperatorFormVo formVo) {
		
		formVo.setDateStart("201501");
		formVo.setDateEnd("201702");
		
		List<TaxOperatorVo> operator = this.taxOperatorRepository.getOperator(formVo);
		for (TaxOperatorVo taxOperatorVo : operator) {
			
			formVo.setNewRegId(taxOperatorVo.getNewRegId());
			List<String> years = this.taxOperatorRepository.getYearTax(formVo);
			taxOperatorVo.setTaxYear(years);
			
			List<TaxPay> monthsAndAmount = this.taxOperatorRepository.getMonthTax(formVo);
	
			// loop add month
			int countTaxMonth = 1;
			for (TaxPay taxPay : monthsAndAmount) {	
					taxOperatorVo.getTaxMonth().add(taxPay.getMonth());		
					
					if(taxPay.getTaxAmount() == null) {
						taxPay.setTaxAmount(new BigDecimal(0));
						taxOperatorVo.setCountTaxPayOfMonth(taxOperatorVo.getCountTaxPayOfMonth()+countTaxMonth);;
						countTaxMonth++;
					}
					
					taxOperatorVo.getTaxAmount().add(taxPay.getTaxAmount());				
			}
		}
		
		//summaryTaxAmount
		for (TaxOperatorVo taxOperatorVo : operator) {
			
			List<BigDecimal> taxAmounts = taxOperatorVo.getTaxAmount();
			int size = taxAmounts.size();
			int before = size/2;
			int countBefore = 0;
			for (BigDecimal tax : taxAmounts) {
				countBefore++;
				if (countBefore < before) {
					taxOperatorVo.setTaxAmountBefore(taxOperatorVo.getTaxAmountBefore().add(tax));
					continue;
					
				}else{
					taxOperatorVo.setTaxAmountAfter(taxOperatorVo.getTaxAmountAfter().add(tax));
				}				
			}
			
			BigDecimal sub = taxOperatorVo.getTaxAmountAfter().subtract(taxOperatorVo.getTaxAmountBefore());
			BigDecimal multipy = sub.multiply(new BigDecimal(100));
			BigDecimal result = new BigDecimal(0);
			if (taxOperatorVo.getTaxAmountBefore().compareTo(BigDecimal.ZERO)==0 ) {
				 result = null;
			}else{
				 result = multipy.divide(taxOperatorVo.getTaxAmountBefore(), 2, RoundingMode.HALF_UP);
			}
			
			taxOperatorVo.setDiffTaxAmount(result);
		}
		return operator;
	}
	
}
