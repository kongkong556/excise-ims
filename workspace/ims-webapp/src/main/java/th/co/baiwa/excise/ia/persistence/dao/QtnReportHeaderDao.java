package th.co.baiwa.excise.ia.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import th.co.baiwa.excise.ia.persistence.entity.QtnReportHeader;
import th.co.baiwa.excise.utils.BeanUtils;

@Repository
public class QtnReportHeaderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private String sqlTemplate = " SELECT * FROM IA_QTN_REPORT_HEADER H WHERE 1 = 1 ";
	public List<QtnReportHeader> findByCriteria(QtnReportHeader qtnReportHeader) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(sqlTemplate); 
		if(BeanUtils.isNotEmpty(qtnReportHeader.getQtnReportHdrId())) {
			sql.append("AND H.QTN_REPORT_HDR_ID = ? ");
			paramList.add(qtnReportHeader.getQtnReportHdrId());
		}
		
		if(BeanUtils.isNotEmpty(qtnReportHeader.getQtnReportHdrName())) {
			sql.append("AND H.QTN_REPORT_HDR_NAME = ? ");
			paramList.add(qtnReportHeader.getQtnReportHdrName());
		}
		
		List<QtnReportHeader> qtnReportHeaderList = jdbcTemplate.query(sql.toString(), paramList.toArray(),rowMapper );
		
		return qtnReportHeaderList;
	}
	
	private RowMapper<QtnReportHeader> rowMapper = new RowMapper<QtnReportHeader>() {

		@Override
		public QtnReportHeader mapRow(ResultSet rs, int arg1) throws SQLException {
			QtnReportHeader vo = new QtnReportHeader();
			vo.setQtnReportHdrId(rs.getBigDecimal("QTN_REPORT_HDR_ID"));
			vo.setQtnReportHdrName(rs.getString("QTN_REPORT_HDR_NAME"));
			vo.setCreator(rs.getString("CREATOR"));
			vo.setCreatedBy(rs.getString("CREATED_BY"));
			vo.setCreatedDatetime(rs.getDate("CREATED_DATETIME"));
			vo.setUpdateBy(rs.getString("UPDATE_BY"));
			vo.setUpdateDatetime(rs.getDate("UPDATE_DATETIME"));
			
			return vo;

		}

	};
	
	public Integer CreateQtnReportHeader(QtnReportHeader qtnReportHeader){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "INSERT INTO IA_QTN_REPORT_HEADER (QTN_REPORT_HDR_ID,QTN_REPORT_HDR_NAME,CREATOR,CREATED_BY,CREATED_DATETIME,UPDATE_BY,UPDATE_DATETIME) VALUES (IA_QTN_REPORT_HEADER_SEQ.nextval,?,?,?,?,?,?)";
		paramList.add(qtnReportHeader.getQtnReportHdrName());
		paramList.add(qtnReportHeader.getCreator());
		paramList.add(qtnReportHeader.getCreatedBy());
		paramList.add(qtnReportHeader.getCreatedDatetime());
		paramList.add(qtnReportHeader.getUpdateBy());
		paramList.add(qtnReportHeader.getUpdateDatetime());
		return jdbcTemplate.update(sql.toString(), paramList.toArray());
		
		
		
	}
	
}
