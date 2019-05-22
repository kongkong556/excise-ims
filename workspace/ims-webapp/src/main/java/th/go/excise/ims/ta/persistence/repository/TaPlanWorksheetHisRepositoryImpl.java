package th.go.excise.ims.ta.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.go.excise.ims.common.util.ExciseUtils;

public class TaPlanWorksheetHisRepositoryImpl implements TaPlanWorksheetHisRepositoryCustom {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	@Override
	public Map<String, String> findAuditPlanCodeByOfficeCodeAndBudgetYearList(String officeCode, List<String> budgetYearList) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();

		sql.append(" SELECT PLAN_HIS.BUDGET_YEAR ");
		sql.append("   ,PLAN_HIS.NEW_REG_ID ");
		sql.append("   ,PLAN_HIS.OFFICE_CODE ");
		sql.append("   ,PLAN_HIS.AUDIT_PLAN_CODE ");
		sql.append(" FROM TA_PLAN_WORKSHEET_HIS PLAN_HIS ");
		sql.append(" WHERE PLAN_HIS.OFFICE_CODE LIKE ? ");
		params.add(ExciseUtils.whereInLocalOfficeCode(officeCode));

		sql.append("   AND PLAN_HIS.BUDGET_YEAR IN (").append(StringUtils.repeat("?", ",", budgetYearList.size())).append(") ");
		params.addAll(budgetYearList);

		Map<String, String> resultMap = commonJdbcTemplate.query(sql.toString(), params.toArray(), new ResultSetExtractor<Map<String, String>>() {
			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> resultMap = new HashMap<>();
				while (rs.next()) {
					resultMap.put(rs.getString("BUDGET_YEAR") + rs.getString("NEW_REG_ID"), rs.getString("AUDIT_PLAN_CODE"));
				}
				return resultMap;
			}
		});

		return resultMap;
	}

	@Override
	public Map<String, String> findMaxTaxAuditYear() {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT NEW_REG_ID, MAX(BUDGET_YEAR) MAX_YEAR ");
		sql.append(" FROM TA_PLAN_WORKSHEET_HIS ");
		sql.append(" GROUP BY NEW_REG_ID ");

		Map<String, String> resultMap = commonJdbcTemplate.query(sql.toString(), new ResultSetExtractor<Map<String, String>>() {
			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> resultMap = new HashMap<>();
				while (rs.next()) {
					resultMap.put(rs.getString("NEW_REG_ID"), rs.getString("MAX_YEAR"));
				}
				return resultMap;
			}
		});

		return resultMap;
	}

}
