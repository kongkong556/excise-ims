package th.go.excise.ims.ta.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.baiwa.buckwaframework.common.constant.CommonConstants.FLAG;
import th.co.baiwa.buckwaframework.common.persistence.repository.CommonJpaCrudRepository;
import th.go.excise.ims.ta.persistence.entity.TaMasCondMainHdr;

public interface TaMasCondMainHdrRepository extends CommonJpaCrudRepository<TaMasCondMainHdr, Long> {
	
	@Query("select e from #{#entityName} e where e.isDeleted = '" + FLAG.N_FLAG + "' and e.budgetYear = :budgetYear and e.officeCode = :officeCode")
	public List<TaMasCondMainHdr> findByBudgetYearAndOfficeCode(@Param("budgetYear") String budgetYear, @Param("officeCode") String officeCode);
	
	public TaMasCondMainHdr findByBudgetYearAndCondNumberAndOfficeCode(String budgetYear, String condNumber, String officeCode);
	
	@Query("select e from #{#entityName} e where e.isDeleted = '" + FLAG.N_FLAG + "' and e.condNumber = :condNumber")
	public TaMasCondMainHdr findByCondNumber(@Param("condNumber") String condNumber);
	
}
