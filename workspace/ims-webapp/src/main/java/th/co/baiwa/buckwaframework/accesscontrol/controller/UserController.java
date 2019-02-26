package th.co.baiwa.buckwaframework.accesscontrol.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.baiwa.buckwaframework.accesscontrol.persistence.entity.User;
import th.co.baiwa.buckwaframework.accesscontrol.service.UserService;
import th.co.baiwa.buckwaframework.accesscontrol.vo.UserFormVo;
import th.co.baiwa.buckwaframework.common.bean.DataTableAjax;

@Controller
@RequestMapping("/api/access-control/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/listUser")
	@ResponseBody
	public DataTableAjax<User> listUser(@RequestBody UserFormVo userFormVo) {
		logger.info("listUser");

		DataTableAjax<User> response = new DataTableAjax<>();
		try {
			response = userService.list(userFormVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
