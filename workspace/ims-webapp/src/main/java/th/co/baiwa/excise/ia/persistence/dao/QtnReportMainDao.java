package th.co.baiwa.excise.ia.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import th.co.baiwa.excise.ia.persistence.entity.qtn.QtnReportDetail;
import th.co.baiwa.excise.ia.persistence.entity.qtn.QtnReportMain;
import th.co.baiwa.excise.ia.persistence.vo.Int023Vo;
import th.co.baiwa.buckwaframework.common.util.BeanUtils; 
import th.co.baiwa.buckwaframework.common.util.OracleUtils;

@Repository
public class QtnReportMainDao {

	private static String _SQL = " SELECT M.* FROM IA_QTN_REPORT_MAIN M LEFT JOIN IA_QTN_REPORT_DETAIL D ON M.QTN_REPORT_MAN_ID = D.QTN_REPORT_MAN_ID WHERE 1=1 ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Int023Vo<QtnReportDetail>> findQtnReport(QtnReportMain qtn, int start, int length) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(_SQL);
		sql.append(" AND M.IS_DELETED = 'N' ");

		if (BeanUtils.isNotEmpty(qtn.getQtnReportHdrId())) {
			sql.append("AND M.QTN_REPORT_HDR_ID = ? ");
			paramList.add(qtn.getQtnReportHdrId());
		}
		String str = "";
		if (start == 0 && length == 0) {
			str = sql.toString();
		} else {
			str = OracleUtils.limitForDataTable(sql, start, length);
		}
		List<Int023Vo<QtnReportDetail>> qtnReportDetail = jdbcTemplate.query(str, paramList.toArray(), rowMapper);

		return qtnReportDetail;
	}

	public long countQtnReport(QtnReportMain qtn) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(_SQL);
		sql.append(" AND M.IS_DELETED = 'N' ");
		if (BeanUtils.isNotEmpty(qtn.getQtnReportHdrId())) {
			sql.append("AND M.QTN_REPORT_HDR_ID = ? ");
			paramList.add(qtn.getQtnReportHdrId());
		}
		long count = jdbcTemplate.queryForObject(OracleUtils.countForDatatable(sql.toString()), paramList.toArray(),
				Long.class);
		return count;
	}

	private RowMapper<Int023Vo<QtnReportDetail>> rowMapper = new RowMapper<Int023Vo<QtnReportDetail>>() {

		@Override
		public Int023Vo<QtnReportDetail> mapRow(ResultSet rs, int arg1) throws SQLException {
			Int023Vo<QtnReportDetail> vo = new Int023Vo<QtnReportDetail>();
			vo.setQtnReportHdrId(rs.getLong("QTN_REPORT_HDR_ID"));
			vo.setQtnReportManId(rs.getLong("QTN_REPORT_MAN_ID"));
			vo.setQtnMainDetail(rs.getString("QTN_MAIN_DETAIL"));
			vo.setCreatedBy(rs.getString("CREATED_BY"));
			vo.setCreatedDate(rs.getDate("CREATED_DATE"));
			vo.setUpdatedBy(rs.getString("UPDATED_BY"));
			vo.setUpdatedDate(rs.getDate("UPDATED_DATE"));
			vo.setIsDeleted(rs.getString("IS_DELETED"));
			vo.setVersion(rs.getInt("VERSION"));
			return vo;
		}

	};
}
