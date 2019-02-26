package th.go.excise.ims.ia.persistence.repository.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.go.excise.ims.ia.vo.Int020301DataVo;
import th.go.excise.ims.ia.vo.Int020301HeaderVo;
import th.go.excise.ims.ia.vo.Int020301InfoVo;

@Repository
public class Int020301JdbcRepository {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public List<Int020301HeaderVo> findHeaderByIdSide(BigDecimal idSide, String budgetYear) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT IQS.SIDE_NAME FROM IA_QUESTIONNAIRE_HDR IQH ");
		sqlBuilder.append(" INNER JOIN IA_QUESTIONNAIRE_SIDE IQS ");
		sqlBuilder.append(" ON IQH.ID = IQS.ID_HEAD WHERE 1=1 AND IQH.IS_DELETED = 'N' AND IQS.IS_DELETED = 'N' ");
		sqlBuilder.append(" AND IQH.ID = ? ");
		sqlBuilder.append(" AND IQH.BUDGET_YEAR = ? ");
		List<Object> params = new ArrayList<>();
		params.add(idSide);
		params.add(budgetYear);
		List<Int020301HeaderVo> data = commonJdbcTemplate.query(sqlBuilder.toString(), params.toArray(), headerRowMapper);
		return data;
	}
	
	private RowMapper<Int020301HeaderVo> headerRowMapper = new RowMapper<Int020301HeaderVo>() {
		@Override
		public Int020301HeaderVo mapRow(ResultSet rs, int arg1) throws SQLException {
			Int020301HeaderVo vo = new Int020301HeaderVo();
			vo.setName(rs.getString("SIDE_NAME"));
			return vo;
		}
	};
	
	public List<Int020301InfoVo> findInfoByIdSide(BigDecimal idSide, String budgetYear) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select distinct qme.OFFICE_CODE as OFFICE_CODE, qme.UPDATED_DATE as UPDATED_DATE, qme.STATUS as STATUS from IA_QUESTIONNAIRE_HDR qhr ");
		sqlBuilder.append(" inner join IA_QUESTIONNAIRE_SIDE qse on qse.ID_HEAD = qhr.ID ");
		sqlBuilder.append(" inner join IA_QUESTIONNAIRE_SIDE_DTL qdl on qdl.ID_SIDE = qse.ID ");
		sqlBuilder.append(" inner join IA_QUESTIONNAIRE_MADE qme on qme.ID_SIDE_DTL = qdl.ID ");
		sqlBuilder.append(" where qhr.BUDGET_YEAR = ? and qhr.ID = ? AND qhr.IS_DELETED = 'N' AND qme.IS_DELETED = 'N' AND qdl.IS_DELETED = 'N' ");
		List<Object> params = new ArrayList<>();
		params.add(budgetYear);
		params.add(idSide);
		List<Int020301InfoVo> data = commonJdbcTemplate.query(sqlBuilder.toString(), params.toArray(), infoRowMapper);
		return data;
	}
	
	private RowMapper<Int020301InfoVo> infoRowMapper = new RowMapper<Int020301InfoVo>() {
		@Override
		public Int020301InfoVo mapRow(ResultSet rs, int arg1) throws SQLException {
			Int020301InfoVo vo = new Int020301InfoVo();
			vo.setSectorName(rs.getString("OFFICE_CODE"));
			vo.setAreaName(rs.getString("OFFICE_CODE"));
			vo.setSentDate(rs.getDate("UPDATED_DATE"));
			vo.setStatusText(rs.getString("STATUS"));
			vo.setStatus(rs.getString("STATUS"));
			return vo;
		}
	};
	
	public List<Int020301DataVo> findDataByIdSide(BigDecimal idSide, String budgetYear, String officeCode) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT QSR.ID AS ID, ");
		sqlBuilder.append(" (SELECT COUNT(1) FROM IA_QUESTIONNAIRE_SIDE_DTL QDL ");
		sqlBuilder.append(" INNER JOIN IA_QUESTIONNAIRE_MADE QME ON QME.ID_SIDE_DTL = QDL.ID AND QME.OFFICE_CODE = ? ");
		sqlBuilder.append(" WHERE QME.IS_DELETED = 'N' AND QME.CHECK_FLAG = 'T' ");
		sqlBuilder.append(" AND QDL.ID_SIDE = QSR.ID GROUP BY QME.CHECK_FLAG) AS ACCEPT, ");
		sqlBuilder.append(" (SELECT COUNT(1) FROM IA_QUESTIONNAIRE_SIDE_DTL QDL2 ");
		sqlBuilder.append(" INNER JOIN IA_QUESTIONNAIRE_MADE QME2 ON QME2.ID_SIDE_DTL = QDL2.ID AND QME2.OFFICE_CODE = ? ");
		sqlBuilder.append(" WHERE QME2.IS_DELETED = 'N' AND (QME2.CHECK_FLAG = 'F' OR QME2.CHECK_FLAG IS NULL) ");
		sqlBuilder.append(" AND QDL2.ID_SIDE = QSR.ID GROUP BY QME2.CHECK_FLAG) AS DECLINE ");
		sqlBuilder.append(" FROM IA_QUESTIONNAIRE_HDR QHR ");
		sqlBuilder.append(" INNER JOIN IA_QUESTIONNAIRE_SIDE QSR ON QSR.ID_HEAD = QHR.ID ");
		sqlBuilder.append(" WHERE 1=1 AND QHR.IS_DELETED = 'N' AND QHR.ID = ? AND QHR.BUDGET_YEAR = ? GROUP BY QSR.ID ");
		List<Object> params = new ArrayList<>();
		params.add(officeCode);
		params.add(officeCode);
		params.add(idSide);
		params.add(budgetYear);
		List<Int020301DataVo> data = commonJdbcTemplate.query(sqlBuilder.toString(), params.toArray(), dataRowMapper);
		return data;
	}
	
	private RowMapper<Int020301DataVo> dataRowMapper = new RowMapper<Int020301DataVo>() {
		@Override
		public Int020301DataVo mapRow(ResultSet rs, int arg1) throws SQLException {
			Int020301DataVo vo = new Int020301DataVo();
			vo.setAcceptValue(rs.getBigDecimal("ACCEPT"));
			vo.setDeclineValue(rs.getBigDecimal("DECLINE"));
			return vo;
		}
	};
	
}