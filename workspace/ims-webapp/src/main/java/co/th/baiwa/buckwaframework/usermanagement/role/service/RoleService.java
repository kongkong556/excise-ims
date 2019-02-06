package co.th.baiwa.buckwaframework.usermanagement.role.service;

import java.util.List;

import co.th.baiwa.buckwaframework.security.util.UserLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.th.baiwa.buckwaframework.usermanagement.role.dao.RoleDao;
import co.th.baiwa.buckwaframework.usermanagement.role.domain.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public List<Role> findAll() {
		List<Role> role = roleDao.findAll();
		return role;
	}

	public void update(Role role){
		roleDao.update(role);
	}

	public void create(Role role){
		String createBy = UserLoginUtils.getCurrentUsername();
		roleDao.create(role, createBy);
	}
}