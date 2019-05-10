package th.go.excise.ims.ia.persistence.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.co.baiwa.buckwaframework.common.util.LocalDateTimeConverter;
import th.go.excise.ims.ia.vo.Int0602FormVo;
import th.go.excise.ims.ws.persistence.entity.WsIncfri8020Inc;
import th.go.excise.ims.ws.persistence.entity.WsLicfri6010;

@Repository
public class Int0602JdbcRepository {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public void findByCriteria(Int0602FormVo vo) {
		List<Object> paramList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * ");
		sql.append(" FROM WS_LICFRI6010 WS ");
		sql.append(" WHERE WS.IS_DELETED = 'N' ");

		if (StringUtils.isNotBlank(vo.getOfficeCode())) {
			sql.append(" AND WS.OFFCODE LIKE '100300' ");
			paramList.add(vo.getOfficeCode());
		}
		if (StringUtils.isNotBlank(vo.getLicDateFrom())) {
			sql.append(" AND WS.LIC_DATE > ? ");
			paramList.add(vo.getLicDateFrom());
		}
		if (StringUtils.isNotBlank(vo.getLicDateTo())) {
			sql.append(" AND WS.LIC_DATE < ? ");
			paramList.add(vo.getLicDateTo());
		}

		sql.append(" ORDER BY LIC_NO ");
		commonJdbcTemplate.query(sql.toString(),paramList.toArray() ,tab1RowMapper );

	}
	
	private RowMapper<WsLicfri6010> tab1RowMapper = new RowMapper<WsLicfri6010>() {
		@Override
		public WsLicfri6010 mapRow(ResultSet rs, int rowNum) throws SQLException {
			WsLicfri6010 vo = new WsLicfri6010();
			

			vo.setIsDeleted(rs.getString("IS_DELETED"));
			vo.setVersion(rs.getInt("VERSION"));
			vo.setCreatedBy(rs.getString("CREATED_BY"));
			vo.setCreatedDate(LocalDateTimeConverter.convertToEntityAttribute(rs.getTimestamp("CREATED_DATE")));
			vo.setUpdatedBy(rs.getString("UPDATED_BY"));
			vo.setUpdatedDate(LocalDateTimeConverter.convertToEntityAttribute(rs.getTimestamp("UPDATED_DATE")));

			return vo;
		}
	};

}
