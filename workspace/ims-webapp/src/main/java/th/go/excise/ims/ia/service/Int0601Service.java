
package th.go.excise.ims.ia.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import th.co.baiwa.buckwaframework.common.constant.CommonConstants.FLAG;
import th.go.excise.ims.ia.persistence.entity.IaAuditIncD1;
import th.go.excise.ims.ia.persistence.entity.IaAuditIncD3;
import th.go.excise.ims.ia.persistence.entity.IaAuditIncH;
import th.go.excise.ims.ia.persistence.repository.IaAuditIncD1Repository;
import th.go.excise.ims.ia.persistence.repository.IaAuditIncD2Repository;
import th.go.excise.ims.ia.persistence.repository.IaAuditIncD3Repository;
import th.go.excise.ims.ia.persistence.repository.IaAuditIncHRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.Int0601JdbcRepository;
import th.go.excise.ims.ia.vo.IaAuditIncD2DatatableDtlVo;
import th.go.excise.ims.ia.vo.IaAuditIncD2Vo;
import th.go.excise.ims.ia.vo.Int0601RequestVo;
import th.go.excise.ims.ia.vo.Int0601SaveVo;
import th.go.excise.ims.ws.persistence.entity.WsIncfri8020Inc;

@Service
public class Int0601Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Int0601Service.class);
	
	@Autowired
	private Int0601JdbcRepository int0601JdbcRepository;
	
	@Autowired
	private IaAuditIncHRepository iaAuditIncHRepository;

	@Autowired
	private IaAuditIncD1Repository iaAuditIncD1Repository;
	
	@Autowired
	private IaAuditIncD2Repository iaAuditIncD2Repository;
	
	@Autowired
	private IaAuditIncD3Repository iaAuditIncD3Repository;

	public List<WsIncfri8020Inc> findTab1ByCriteria(Int0601RequestVo int0601Vo){
		logger.info("findByCriterai");
		return int0601JdbcRepository.findByCriteria(int0601Vo);
	}
	
	public IaAuditIncH createIaAuditInc(Int0601SaveVo vo) {
		logger.info("insert IaAuditIncH");
		IaAuditIncH iaAuditIncH = vo.getIaAuditIncH();
		String auditIncNo = "";
		if (iaAuditIncH != null && iaAuditIncH.getAuditIncSeq() != null) {

			iaAuditIncH = iaAuditIncHRepository.findById(vo.getIaAuditIncH().getAuditIncSeq()).get();
			auditIncNo = iaAuditIncH.getAuditIncNo();
		} else {
			auditIncNo = iaAuditIncH.getOfficeCode() + "/" + iaAuditIncHRepository.generateAuditIncNo();
			iaAuditIncH.setAuditIncNo(auditIncNo);
			iaAuditIncH = iaAuditIncHRepository.save(iaAuditIncH);
		}
		if (iaAuditIncH.getAuditIncSeq() != null) {
			logger.info("insert IaAuditIncH Completed ");
			if (vo.getIaAuditIncD1List() != null && vo.getIaAuditIncD1List().size() > 0) {
				logger.info("insert Drtail : 1 ");
				iaAuditIncD1Repository.batchInsert(vo.getIaAuditIncD1List(), auditIncNo);
			}
			if (vo.getIaAuditIncD2List() != null && vo.getIaAuditIncD2List().size() > 0) {
				logger.info("insert Drtail : 2 ");
				iaAuditIncD2Repository.batchInsert(vo.getIaAuditIncD2List());
			}
			if (vo.getIaAuditIncD3List() != null && vo.getIaAuditIncD3List().size() > 0) {
				logger.info("insert Drtail : 3 ");
				iaAuditIncD3Repository.batchInsert(vo.getIaAuditIncD3List());
			}
		} else {
			logger.info("insert IaAuditIncH incomplet ");
		}

		return iaAuditIncH;
	}
	
	public List<IaAuditIncH> findAllIaAuditIncH(){
		return iaAuditIncHRepository.findByIsDeletedOrderByAuditIncNoAsc(FLAG.N_FLAG);
	}
	
	public List<IaAuditIncD2Vo> findIaAuditIncD2ByCriteria(Int0601RequestVo criteria){
		return int0601JdbcRepository.findDataTab2(criteria);
	}
	
	public List<IaAuditIncD1> findIaAuditIncD1ByAuditIncNo(String  auditIncNo){
		return iaAuditIncD1Repository.findByAuditIncNoOrderByReceiptNo(auditIncNo);
	} 
	
	public List<IaAuditIncD3> findIaAuditIncD3ByCriteria(Int0601RequestVo criteria){
		return int0601JdbcRepository.findDataTab3(criteria);
	}
	public IaAuditIncD2DatatableDtlVo findTab2Dtl(Int0601RequestVo criteria){
		IaAuditIncD2DatatableDtlVo iaAuditIncD2DatatableDtlVo = new IaAuditIncD2DatatableDtlVo();
		List<WsIncfri8020Inc> wsIncfri8020IncList = int0601JdbcRepository.findByCriteria(criteria);
		BigDecimal sumAmt = BigDecimal.ZERO;
		for (WsIncfri8020Inc wsIncfri8020Inc : wsIncfri8020IncList) {
			sumAmt.add(wsIncfri8020Inc.getNetLocAmt());
		}
		iaAuditIncD2DatatableDtlVo.setWsIncfri8020Inc(wsIncfri8020IncList);
		iaAuditIncD2DatatableDtlVo.setSumAmt(sumAmt);
		return iaAuditIncD2DatatableDtlVo;
	}
	
	
	
	
	
}
