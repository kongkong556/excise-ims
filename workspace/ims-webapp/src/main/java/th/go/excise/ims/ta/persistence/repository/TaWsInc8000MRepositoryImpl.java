package th.go.excise.ims.ta.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.go.excise.ims.ta.persistence.entity.TaWsInc8000M;

public class TaWsInc8000MRepositoryImpl implements TaWsInc8000MCustom {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	@Override
	public Map<String, List<TaWsInc8000M>> findAllTaWsInc8000MSet( String startMonth, String endMonth) {
		StringBuilder sql = new StringBuilder(" SELECT * FROM ( ");
		sql.append(" SELECT I.*, I.TAX_YEAR || DECODE(LENGTH(I.TAX_MONTH), 2 ,I.TAX_MONTH , '0'||I.TAX_MONTH) YEAR_MONTH ");
		sql.append(" FROM TA_WS_INC8000_M I ");
		sql.append(" ) INC ");
		sql.append(" WHERE INC.IS_DELETED = 'N' ");
		sql.append(" AND INC.YEAR_MONTH >= ? ");
		sql.append(" AND INC.YEAR_MONTH <= ? ");
		sql.append(" ORDER BY INC.YEAR_MONTH ");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(startMonth);
		paramList.add(endMonth);
		Map<String, List<TaWsInc8000M>> map8000m = commonJdbcTemplate.query(sql.toString(),paramList.toArray(), new ResultSetExtractor<Map<String, List<TaWsInc8000M>>>() {

			public Map<String, List<TaWsInc8000M>> extractData(ResultSet rs) throws SQLException, DataAccessException {

				Map<String, List<TaWsInc8000M>> mapData = new HashMap<>();
				while (rs.next()) {
					List<TaWsInc8000M> list = mapData.get(rs.getString(TaWsInc8000M.Field.NEW_REG_ID));
					if(list == null) {
						list  = new ArrayList<>();
					}
					TaWsInc8000M taWsInc8000M = new TaWsInc8000M();
					taWsInc8000M.setNewRegId(rs.getString(TaWsInc8000M.Field.WS_INC8000_M_ID));
					taWsInc8000M.setTaxAmount(rs.getBigDecimal(TaWsInc8000M.Field.TAX_AMOUNT));
					taWsInc8000M.setTaxYear(rs.getString(TaWsInc8000M.Field.TAX_YEAR));
					taWsInc8000M.setTaxMonth(rs.getString(TaWsInc8000M.Field.TAX_MONTH));
					list.add(taWsInc8000M);
					mapData.put(rs.getString(TaWsInc8000M.Field.NEW_REG_ID), list);
				}
				return mapData;
			}
		});

		return map8000m;
	}

}
