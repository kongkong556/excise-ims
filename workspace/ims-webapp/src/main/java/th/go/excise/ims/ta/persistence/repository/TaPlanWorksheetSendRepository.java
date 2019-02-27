package th.go.excise.ims.ta.persistence.repository;

import java.util.List;

import th.co.baiwa.buckwaframework.common.persistence.repository.CommonJpaCrudRepository;
import th.go.excise.ims.ta.persistence.entity.TaPlanWorksheetSend;

public interface TaPlanWorksheetSendRepository extends CommonJpaCrudRepository<TaPlanWorksheetSend, Long> {

	public List<TaPlanWorksheetSend> findByBudgetYear(String budgetYear);
}