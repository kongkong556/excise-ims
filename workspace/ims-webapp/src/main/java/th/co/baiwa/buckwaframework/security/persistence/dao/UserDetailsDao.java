package th.co.baiwa.buckwaframework.security.persistence.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import th.co.baiwa.buckwaframework.common.constant.CommonConstants.FLAG;
import th.co.baiwa.buckwaframework.common.persistence.dao.CommonJdbcDao;
import th.co.baiwa.buckwaframework.security.persistence.mapper.GrantedAuthorityRowMapper;

@Repository("userDetailsDao")
public class UserDetailsDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsDao.class);
	
	@Autowired
	private CommonJdbcDao commonJdbcDao;
	
	public List<GrantedAuthority> findGrantedRoleByUserId(Long userId) {
		logger.debug("findGrantedRoleByUserId userId={}", userId);
		
		String sql =
			" SELECT ar.role_code AS authority_code " +
			" FROM adm_user_role aur " +
			" INNER JOIN adm_role ar ON ar.role_id = aur.role_id " +
			"   AND ar.is_deleted = ? " +
			" WHERE aur.is_deleted = ? " +
			"   AND aur.user_id = ? ";
		
		return commonJdbcDao.executeQuery(sql,
			new Object[] {
				FLAG.N_FLAG,
				FLAG.N_FLAG,
				userId
			},
			GrantedAuthorityRowMapper.getInstance()
		);
	}
	
	public List<GrantedAuthority> findGrantedOperationByUserId(Long userId) {
		logger.debug("findGrantedOperationByUserId userId={}", userId);
		
		String sql =
			" SELECT ao.operation_code AS authority_code " +
			" FROM adm_user_operation auo " +
			" INNER JOIN adm_operation ao ON ao.operation_id = auo.operation_id " +
			"   AND ao.is_deleted = ? " +
			" WHERE auo.is_deleted = ? " +
			"   AND auo.user_id = ? ";
		
		return commonJdbcDao.executeQuery(sql,
			new Object[] {
				FLAG.N_FLAG,
				FLAG.N_FLAG,
				userId
			},
			GrantedAuthorityRowMapper.getInstance()
		);
	}
	
}
