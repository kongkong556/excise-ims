package th.go.excise.ims.scheduler.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.security.constant.SecurityConstants.SYSTEM_USER;
import th.go.excise.ims.ws.client.pcc.common.exception.PccRestfulException;
import th.go.excise.ims.ws.client.pcc.regfri4000.model.RegDuty;
import th.go.excise.ims.ws.client.pcc.regfri4000.model.RegMaster60;
import th.go.excise.ims.ws.client.pcc.regfri4000.model.RequestData;
import th.go.excise.ims.ws.client.pcc.regfri4000.service.RegFri4000Service;
import th.go.excise.ims.ws.persistence.entity.WsRegfri4000;
import th.go.excise.ims.ws.persistence.entity.WsRegfri4000Duty;
import th.go.excise.ims.ws.persistence.repository.WsRegfri4000DutyRepository;
import th.go.excise.ims.ws.persistence.repository.WsRegfri4000Repository;

@Service
public class SyncWsRegfri4000Service {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncWsRegfri4000Service.class);
	
	private final int WS_DATA_SIZE = 500;
	
	@Autowired
	private RegFri4000Service regFri4000Service;
	
	@Autowired
	private WsRegfri4000Repository wsRegfri4000Repository;
	
	@Autowired
	private WsRegfri4000DutyRepository wsRegfri4000DutyRepository;
	
	@Transactional(rollbackOn = {Exception.class})
	public void syncData() throws PccRestfulException {
		logger.info("syncData Regfri4000 Start");
		long start = System.currentTimeMillis();
		
		RequestData requestData = new RequestData();
		requestData.setType("1");
		requestData.setNid("");
		requestData.setNewregId("");
		requestData.setHomeOfficeId("");
		requestData.setActive("1");
		requestData.setDataPerPage(String.valueOf(WS_DATA_SIZE));
		int indexPage = 0;
		
		List<RegMaster60> regMaster60List = null;
		WsRegfri4000 regfri4000 = null;
		List<WsRegfri4000> regfri4000List = new ArrayList<>();
		WsRegfri4000Duty regfri4000Duty = null;
		List<WsRegfri4000Duty> regfri4000DutyList = new ArrayList<>();
		do {
			indexPage++;
			requestData.setPageNo(String.valueOf(indexPage));
			regMaster60List = regFri4000Service.execute(requestData).getRegMaster60List();
			if (regMaster60List != null && regMaster60List.size() > 0) {
				logger.info("Restful Post to Regfri4000 Response size: {}", regMaster60List.size());
				regfri4000List = new ArrayList<>();
				for (RegMaster60 regMaster60 : regMaster60List) {
					regfri4000 = new WsRegfri4000();
					regfri4000.setNewRegId(regMaster60.getNewregId());
					regfri4000.setCusId(regMaster60.getCusId());
					regfri4000.setCusFullname(regMaster60.getCusFullname());
					regfri4000.setCusAddress(buildCusAddress(regMaster60));
					regfri4000.setCusTelno(regMaster60.getCusTelno());
					regfri4000.setCusEmail(regMaster60.getCusEmail());
					regfri4000.setCusUrl(regMaster60.getCusUrl());
					regfri4000.setFacId(regMaster60.getFacId());
					regfri4000.setFacFullname(regMaster60.getFacFullname());
					regfri4000.setFacAddress(buildFacAddress(regMaster60));
					regfri4000.setFacTelno(regMaster60.getFacTelno());
					regfri4000.setFacEmail(regMaster60.getFacEmail());
					regfri4000.setFacUrl(regMaster60.getFacUrl());
					regfri4000.setFacType(null); // TODO Assign value
					regfri4000.setRegId(null); // TODO Assign value
					regfri4000.setRegStatus(null); // TODO Assign value
					regfri4000.setRegDate(null); // TODO Assign value
					regfri4000.setRegCapital(null); // TODO Assign value
					regfri4000.setOfficeCode(regMaster60.getOffcode());
					regfri4000.setActiveFlag(regMaster60.getActiveFlag());
					regfri4000.setCreatedBy(SYSTEM_USER.BATCH);
					regfri4000.setCreatedDate(LocalDateTime.now());
					regfri4000.setUpdatedBy(SYSTEM_USER.BATCH);
					regfri4000.setUpdatedDate(LocalDateTime.now());
					if (regMaster60.getRegDutyList() != null && regMaster60.getRegDutyList().size() > 0) {
						regfri4000.setDutyCode(regMaster60.getRegDutyList().get(0).getGroupId());
						for (RegDuty regDuty : regMaster60.getRegDutyList()) {
							regfri4000Duty = new WsRegfri4000Duty();
							regfri4000Duty.setNewRegId(regMaster60.getNewregId());
							regfri4000Duty.setGroupId(regDuty.getGroupId());
							regfri4000Duty.setGroupName(regDuty.getGroupName());
							regfri4000Duty.setCreatedBy(SYSTEM_USER.BATCH);
							regfri4000Duty.setCreatedDate(LocalDateTime.now());
							regfri4000Duty.setUpdatedBy(SYSTEM_USER.BATCH);
							regfri4000Duty.setUpdatedDate(LocalDateTime.now());
							regfri4000DutyList.add(regfri4000Duty);
						}
					}
					regfri4000List.add(regfri4000);
				}
			} else {
				logger.info("ws 4000 no response data", regMaster60List.size());
			}
		} while (regMaster60List.size() == WS_DATA_SIZE);
		
		wsRegfri4000Repository.queryUpdateIsDeletedY();
		wsRegfri4000Repository.batchUpdate(regfri4000List);
		logger.info("Batch Update WS_REGFRI4000 Success");
		
		wsRegfri4000DutyRepository.queryUpdateIsDeletedY();
		wsRegfri4000DutyRepository.batchUpdate(regfri4000DutyList);
		logger.info("Batch Update WS_REGFRI4000_DUTY Success");
		
		long end = System.currentTimeMillis();
		logger.info("syncData Regfri4000 Success, using {} seconds", (float) (end - start) / 1000F);
	}
	
	private String buildCusAddress(RegMaster60 regMaster60) {
		StringBuilder address = new StringBuilder(regMaster60.getCusAddrno());
		if (regMaster60.getCusBuildname() != null) {
			address.append(" ").append(regMaster60.getCusBuildname());
		}
		if (regMaster60.getCusFloorno() != null) {
			address.append(" ").append(regMaster60.getCusFloorno());
		}
		if (regMaster60.getCusRoomno() != null) {
			address.append(" ").append(regMaster60.getCusRoomno());
		}
		if (regMaster60.getCusMoono() != null) {
			address.append(" ").append(regMaster60.getCusMoono());
		}
		if (regMaster60.getCusVillage() != null) {
			address.append(" ").append(regMaster60.getCusVillage());
		}
		if (regMaster60.getCusSoiname() != null) {
			address.append(" ").append(regMaster60.getCusSoiname());
		}
		if (regMaster60.getCusThnname() != null) {
			address.append(" ").append(regMaster60.getCusThnname());
		}
		if (regMaster60.getCusTambolname() != null) {
			address.append(" ").append(regMaster60.getCusTambolname());
		}
		if (regMaster60.getCusAmphurname() != null) {
			address.append(" ").append(regMaster60.getCusAmphurname());
		}
		if (regMaster60.getCusProvincename() != null) {
			address.append(" ").append(regMaster60.getCusProvincename());
		}
		if (regMaster60.getCusZipcode() != null) {
			address.append(" ").append(regMaster60.getCusZipcode());
		}
		
		return address.toString();
	}

	private String buildFacAddress(RegMaster60 regMaster60) {
		StringBuilder address = new StringBuilder(regMaster60.getFacAddrno());
		if (regMaster60.getFacBuildname() != null) {
			address.append(" ").append(regMaster60.getFacBuildname());
		}
		if (regMaster60.getFacFloorno() != null) {
			address.append(" ").append(regMaster60.getFacFloorno());
		}
		if (regMaster60.getFacRoomno() != null) {
			address.append(" ").append(regMaster60.getFacRoomno());
		}
		if (regMaster60.getFacMoono() != null) {
			address.append(" ").append(regMaster60.getFacMoono());
		}
		if (regMaster60.getFacVillage() != null) {
			address.append(" ").append(regMaster60.getFacVillage());
		}
		if (regMaster60.getFacSoiname() != null) {
			address.append(" ").append(regMaster60.getFacSoiname());
		}
		if (regMaster60.getFacThnname() != null) {
			address.append(" ").append(regMaster60.getFacThnname());
		}
		if (regMaster60.getFacTambolname() != null) {
			address.append(" ").append(regMaster60.getFacTambolname());
		}
		if (regMaster60.getFacAmphurname() != null) {
			address.append(" ").append(regMaster60.getFacAmphurname());
		}
		if (regMaster60.getFacProvincename() != null) {
			address.append(" ").append(regMaster60.getFacProvincename());
		}
		if (regMaster60.getFacZipcode() != null) {
			address.append(" ").append(regMaster60.getFacZipcode());
		}
		
		return address.toString();
	}
	
}
