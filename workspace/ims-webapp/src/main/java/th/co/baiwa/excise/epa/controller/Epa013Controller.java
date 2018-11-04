package th.co.baiwa.excise.epa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import th.co.baiwa.excise.domain.datatable.DataTableAjax;
import th.co.baiwa.excise.epa.persistence.vo.Epa011DtlVo;
import th.co.baiwa.excise.epa.persistence.vo.Epa011FormVo;
import th.co.baiwa.excise.epa.persistence.vo.Epa011Vo;
import th.co.baiwa.excise.epa.persistence.vo.InvhdrFormVo;
import th.co.baiwa.excise.epa.service.Epa011Service;

@Controller
@RequestMapping("api/epa/epa013")
public class Epa013Controller {

	@Autowired
	private Epa011Service epa011Service;
	
	@PostMapping("/search")
	@ResponseBody
	public DataTableAjax<Epa011Vo> search(@RequestBody Epa011FormVo epa011FormVo) {
		return epa011Service.search(epa011FormVo);
	}

	@PostMapping("/searchDetail")
	@ResponseBody
	public DataTableAjax<Epa011DtlVo> searchDetail(@RequestBody Epa011FormVo epa011FormVo) {
		return epa011Service.searchDetail(epa011FormVo);
	}
	
	
	@PostMapping("/getDetail")
	@ResponseBody
	public Epa011Vo getDetail(@RequestBody Epa011FormVo epa011FormVo) {
		return epa011Service.getDetail(epa011FormVo);
	}
	
	@PostMapping("/getInvDetail")
	@ResponseBody
	public InvhdrFormVo getInvDetail(@RequestBody InvhdrFormVo InvhdrFormVo) {
		 return epa011Service.getInvDetail(InvhdrFormVo);
	}
	
	@PostMapping("/saveInv")
	@ResponseBody
	public InvhdrFormVo saveInv(@RequestBody InvhdrFormVo invhdrFormVo) {
		epa011Service.saveInv(invhdrFormVo);
		return invhdrFormVo;
	}
	
}
