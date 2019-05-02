package th.go.excise.ims.ia.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.go.excise.ims.ia.persistence.entity.IaEmpWorkingDtl;
import th.go.excise.ims.ia.vo.IaEmpWorkingDtlSaveVo;

public class IaEmpWorkingDtlRepositoryImpl implements IaEmpWorkingDtlRepositoryCustom {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	private void buildByMonthQuery(StringBuilder sql, List<Object> params, IaEmpWorkingDtlSaveVo formVo) {
		sql.append(" SELECT * ");
		sql.append(" FROM IA_EMP_WORKING_DTL ");
		sql.append(" WHERE IS_DELETED = 'N' ");
		sql.append(" AND EXTRACT(MONTH FROM TO_DATE(WORKING_DATE) ) = EXTRACT(MONTH FROM TO_DATE(?,'DD/MM/YYYY') ) ");
		sql.append(" AND EXTRACT(YEAR FROM TO_DATE(WORKING_DATE) ) = EXTRACT(YEAR FROM TO_DATE(?,'DD/MM/YYYY') ) ");

		params.add(formVo.getWorkingDate());
		params.add(formVo.getWorkingDate());
	}

	@Override
	public List<IaEmpWorkingDtl> findByMonth(IaEmpWorkingDtlSaveVo formVo) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		buildByMonthQuery(sql, params, formVo);

		return commonJdbcTemplate.query(sql.toString(), params.toArray(), findByMonthRowMapper);
	}

	protected RowMapper<IaEmpWorkingDtl> findByMonthRowMapper = new RowMapper<IaEmpWorkingDtl>() {
		@Override
		public IaEmpWorkingDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
			IaEmpWorkingDtl vo = new IaEmpWorkingDtl();
			vo.setIaEmpWorkingDtlSeq(rs.getLong("IA_EMP_WORKING_DTL_SEQ"));
			vo.setUserLogin(rs.getString("USER_LOGIN"));
			vo.setUserName(rs.getString("USER_NAME"));
			vo.setUserPosition(rs.getString("USER_POSITION"));
			vo.setUserOffcode(rs.getString("USER_OFFCODE"));
			vo.setWorkingDate(rs.getDate("WORKING_DATE"));
			vo.setWorkingFlag(rs.getString("WORKING_FLAG"));
			vo.setWorkingDesc(rs.getString("WORKING_DESC"));
			vo.setWorkingRemark(rs.getString("WORKING_REMARK"));
			vo.setReimburseExpFlag(rs.getString("REIMBURSE_EXP_FLAG"));
			return vo;
		}
	};

}
