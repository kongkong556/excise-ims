package th.go.excise.ims.ia.persistence.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.co.baiwa.buckwaframework.common.util.ConvertDateUtils;
import th.co.baiwa.buckwaframework.common.util.LocalDateTimeConverter;
import th.go.excise.ims.ia.persistence.entity.IaRiskFactors;
import th.go.excise.ims.ia.persistence.entity.IaRiskFactorsConfig;
import th.go.excise.ims.ia.vo.Int0301FormVo;
import th.go.excise.ims.ia.vo.Int0301Vo;

@Repository
public class Int0301JdbcRepository {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	private final String SQL_IA_RISK_FACTORS = " SELECT a.* , " + " b.ID AS ID_CONFIG, " + " b.*"
			+ "FROM IA_RISK_FACTORS a " + "LEFT JOIN IA_RISK_FACTORS_CONFIG b "
			+ "ON a.ID = b.ID_FACTORS WHERE a.INSPECTION_WORK = ? AND a.IS_DELETED = 'N' ";

	public List<Int0301Vo> list(Int0301FormVo form) {
		List<Int0301Vo> int0301VoList = new ArrayList<Int0301Vo>();

		StringBuilder sql = new StringBuilder(SQL_IA_RISK_FACTORS);
		List<Object> params = new ArrayList<Object>();

		params.add(form.getInspectionWork());

		if (StringUtils.isNotBlank(form.getBudgetYear())) {
			sql.append(" AND BUDGET_YEAR = ? ");
			params.add(form.getBudgetYear());
		}
		int0301VoList = commonJdbcTemplate.query(sql.toString(), params.toArray(), listRowmapper);
		return int0301VoList;
	}

	private RowMapper<Int0301Vo> listRowmapper = new RowMapper<Int0301Vo>() {
		@Override
		public Int0301Vo mapRow(ResultSet rs, int arg1) throws SQLException {
			Int0301Vo vo = new Int0301Vo();
			IaRiskFactors irf = new IaRiskFactors();
			IaRiskFactorsConfig irfc = new IaRiskFactorsConfig();

//			setentityIaRiskFactors

			irf.setId(rs.getBigDecimal("ID"));
			irf.setRiskFactors(rs.getString("RISK_FACTORS"));
			irf.setBudgetYear(rs.getString("BUDGET_YEAR"));
			irf.setSide(rs.getString("SIDE"));
			irf.setStatusScreen(rs.getString("STATUS_SCREEN"));
			irf.setDateCriteria(rs.getString("DATE_CRITERIA"));
			irf.setInspectionWork(rs.getBigDecimal("INSPECTION_WORK"));
			irf.setCreatedBy(rs.getString("CREATED_BY"));
			irf.setIdMaster(rs.getBigDecimal("ID_MASTER"));

			// vo.setCreatedDateDesc(ConvertDateUtils.formatDateToString(rs
			// .getDate("CREATED_DATE"), ConvertDateUtils.DD_MM_YYYY,
			// ConvertDateUtils.LOCAL_TH));
			LocalDateTime createdDate = LocalDateTimeConverter
					.convertToEntityAttribute(rs.getTimestamp("CREATED_DATE"));
			irf.setCreatedDate(createdDate);

			irf.setUpdatedBy(rs.getString("UPDATED_BY"));

			LocalDateTime updatedDate = LocalDateTimeConverter
					.convertToEntityAttribute(rs.getTimestamp("UPDATED_DATE"));
			irf.setUpdatedDate(updatedDate);

			vo.setCreatedDateDesc(ConvertDateUtils.formatDateToString(rs.getDate("CREATED_DATE"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			vo.setUpdateDateDesc(ConvertDateUtils.formatDateToString(rs.getDate("UPDATED_DATE"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			vo.setIaRiskFactors(irf);

			// vo.setUpdateDateDesc(ConvertDateUtils.formatDateToString(rs
			// .getDate("UPDATED_DATE"), ConvertDateUtils.DD_MM_YYYY,
			// ConvertDateUtils.LOCAL_TH));
//			setentityIaRiskFactorsConfig		

			irfc.setId(rs.getBigDecimal("ID_CONFIG"));
			irfc.setIdFactors(rs.getBigDecimal("ID_FACTORS"));
			irfc.setFactorsLevel(rs.getBigDecimal("FACTORS_LEVEL"));
			vo.setIaRiskFactorsConfig(irfc);
			return vo;
		}
	};

	public void saveRiskFactorsLevel(Int0301FormVo form) {
		StringBuilder sql = new StringBuilder();
		sql.append("   UPDATE IA_RISK_FACTORS_CONFIG C                       ");
		sql.append("   SET C.FACTORS_LEVEL   = ? ,                           ");
		sql.append("   C.START_DATE          = null,                         ");
		sql.append("   C.END_DATE            = null,                         ");
		sql.append("   C.INFO_USED_RISK      = null,                         ");
		sql.append("   C.INFO_USED_RISK_DESC = null,                         ");
		sql.append("   C.VERYLOW             = null,                         ");
		sql.append("   C.VERYLOW_START       = null,                         ");
		sql.append("   C.VERYLOW_END         = null,                         ");
		sql.append("   C.VERYLOW_RATING      = null,                         ");
		sql.append("   C.VERYLOW_COLOR       = null,                         ");
		sql.append("   C.VERYLOW_CONDITION   = null,                         ");
		sql.append("   C.LOW                 = null,                         ");
		sql.append("   C.LOW_START           = null,                         ");
		sql.append("   C.LOW_END             = null,                         ");
		sql.append("   C.LOW_RATING          = null,                         ");
		sql.append("   C.LOW_COLOR           = null,                         ");
		sql.append("   C.LOW_CONDITION       = null,                         ");
		sql.append("   C.MEDIUM              = null,                         ");
		sql.append("   C.MEDIUM_START        = null,                         ");
		sql.append("   C.MEDIUM_END          = null,                         ");
		sql.append("   C.MEDIUM_RATING       = null,                         ");
		sql.append("   C.MEDIUM_COLOR        = null,                         ");
		sql.append("   C.MEDIUM_CONDITION    = null,                         ");
		sql.append("   C.HIGH                = null,                         ");
		sql.append("   C.HIGH_START          = null,                         ");
		sql.append("   C.HIGH_END            = null,                         ");
		sql.append("   C.HIGH_RATING         = null,                         ");
		sql.append("   C.HIGH_COLOR          = null,                         ");
		sql.append("   C.HIGH_CONDITION      = null,                         ");
		sql.append("   C.VERYHIGH            = null,                         ");
		sql.append("   C.VERYHIGH_START      = null,                         ");
		sql.append("   C.VERYHIGH_END        = null,                         ");
		sql.append("   C.VERYHIGH_RATING     = null,                         ");
		sql.append("   C.VERYHIGH_COLOR      = null,                         ");
		sql.append("   C.VERYHIGH_CONDITION  = null                          ");
		sql.append("   WHERE C.ID IN (SELECT B.ID                              ");
		sql.append("                 FROM IA_RISK_FACTORS A                  ");
		sql.append("                 INNER JOIN IA_RISK_FACTORS_CONFIG B     ");
		sql.append("                 ON A.ID = B.ID_FACTORS                  ");
		sql.append("                 WHERE A.BUDGET_YEAR = ? )    ");
		
		commonJdbcTemplate.update(sql.toString(), new Object[] { form.getFactorsLevel(), form.getBudgetYear() });
		
		
	}
}

