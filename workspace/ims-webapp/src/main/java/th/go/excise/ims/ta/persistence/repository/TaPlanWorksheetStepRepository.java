package th.go.excise.ims.ta.persistence.repository;

import java.math.BigDecimal;

import th.co.baiwa.buckwaframework.common.persistence.repository.CommonJpaCrudRepository;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetStep;

public interface TaPlanWorksheetStepRepository extends CommonJpaCrudRepository<TaPlanWorksheetStep, BigDecimal>, TaPlanWorksheetStepRepositoryCustom {
	
}