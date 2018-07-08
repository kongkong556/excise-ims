package th.co.baiwa.excise.ia.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import th.co.baiwa.excise.ta.persistence.entity.ExciseTaxReceive;

@Repository
public class ExciseTaxReceiveDao {

	private Logger logger = LoggerFactory.getLogger(ExciseTaxReceiveDao.class);

	@Autowired
	private JdbcTemplate JdbcTemplate;

	private final String sqlTaExciseId = " select * from ta_excise_tax_receive where TA_EXCISE_ID =  ?  ";

	public List<ExciseTaxReceive> queryByExciseId(String register) {

		List<ExciseTaxReceive> list = JdbcTemplate.query(sqlTaExciseId, new Object[] { register }, exciseRegisttionRowmapper);

		return list;
	}

	public List<ExciseTaxReceive> queryByExciseTaxReceiveAndFilterDataSelection(String register, Date date, int month ) {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(" SELECT H.TA_EXCISE_TAX_RECEIVE_MONTH ,H.TA_EXCISE_ID ,SUM( TO_NUMBER(trim(H.TA_EXCISE_TAX_RECEIVE_AMOUNT), '999999999999999.99')) as TA_EXCISE_TAX_RECEIVE_AMOUNT from ta_excise_tax_receive H ");
		
		sql.append(" where H.TA_EXCISE_ID =  ? ");
		objList.add(register);
		
		
		sql.append("and H.TA_EXCISE_TAX_RECEIVE_MONTH in ( ");
		sql.append(" Select  REPLACE(TO_CHAR( add_MONTHS(  ?, LEVEL-?  )  , 'MON yy', 'NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI'), '  ', ' ' ) Month_AFTER FROM dual CONNECT BY LEVEL <= ? ");
		sql.append(" ) ");
		sql.append(" GROUP BY H.TA_EXCISE_TAX_RECEIVE_MONTH ,H.TA_EXCISE_ID  ");
		objList.add(date);
		objList.add(month);
		objList.add(month);

		List<ExciseTaxReceive> list = JdbcTemplate.query(sql.toString(), objList.toArray(), exciseRegisttionRowmapper);

		return list;
	}

	private RowMapper<ExciseTaxReceive> exciseRegisttionRowmapper = new RowMapper<ExciseTaxReceive>() {

		@Override
		public ExciseTaxReceive mapRow(ResultSet rs, int arg1) throws SQLException {
			ExciseTaxReceive vo = new ExciseTaxReceive();
			// vo.setExciseTaxReceiveId(rs.getInt("TA_EXCISE_TAX_RECEIVE_ID"));
			vo.setExciseId(rs.getString("TA_EXCISE_ID"));
			vo.setExciseTaxReceiveMonth(rs.getString("TA_EXCISE_TAX_RECEIVE_MONTH"));
			vo.setExciseTaxReceiveAmount(rs.getString("TA_EXCISE_TAX_RECEIVE_AMOUNT"));
			// vo.setCreatedBy(rs.getString("CREATED_BY"));
			// vo.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ?
			// rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null);
			// vo.setUpdateBy(rs.getString("UPDATED_BY"));
			// vo.setUpdateDate(rs.getTimestamp("UPDATED_DATE") != null ?
			// rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);

			return vo;

		}

	};
	
	
	
	public List<String> queryMonthShotName(Date date, int month) {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" Select  REPLACE(TO_CHAR( add_MONTHS(  ?, LEVEL-?  )  , 'MON yy', 'NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI'), '  ', ' ' ) Month_AFTER FROM dual CONNECT BY LEVEL <= ? ");
		
		objList.add(date);
		objList.add(month);
		objList.add(month);

		List<String> monthShotName = JdbcTemplate.query(sql.toString() , objList.toArray() , new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		return monthShotName;
	}

}
