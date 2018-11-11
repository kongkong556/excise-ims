package th.co.baiwa.excise.ta.persistence.repository;

import java.math.BigDecimal;

import th.co.baiwa.buckwaframework.common.persistence.repository.CommonJpaCrudRepository;
import th.co.baiwa.excise.ta.persistence.entity.TaxAuditResult;

public interface TaxAuditResultRepository extends CommonJpaCrudRepository<TaxAuditResult, BigDecimal> {

}
