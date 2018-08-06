package th.co.baiwa.excise.ia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.preferences.persistence.entity.Message;
import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.excise.ia.persistence.entity.RiskAssRiskWsHdr;
import th.co.baiwa.excise.ia.persistence.repository.RiskAssRiskWsHdrRepository;
import th.co.baiwa.excise.utils.BeanUtils;

@Service
public class RiskAssRiskWsHdrService {
private static final Logger logger = LoggerFactory.getLogger(RiskAssRiskWsHdrService.class);
	
	private final RiskAssRiskWsHdrRepository riskAssRiskWsHdrRepository;
	
	@Autowired
	public RiskAssRiskWsHdrService(RiskAssRiskWsHdrRepository riskAssRiskWsHdrRepository) {
		this.riskAssRiskWsHdrRepository = riskAssRiskWsHdrRepository;
	}
	
	public Message createRiskAssRiskWsHdrRepository(RiskAssRiskWsHdr riskAssRiskWsHdr) {
		logger.info("Risk Name : "+ riskAssRiskWsHdr.getRiskHdrName());
		Message message = null;
		List<RiskAssRiskWsHdr> riskWsHdrList = riskAssRiskWsHdrRepository.findByCriteria(riskAssRiskWsHdr);
		if(BeanUtils.isNotEmpty(riskWsHdrList) && riskWsHdrList.size() == 0) {
			RiskAssRiskWsHdr riskWsHdr = riskAssRiskWsHdrRepository.save(riskAssRiskWsHdr);
			if(BeanUtils.isNotEmpty(riskWsHdr.getRiskHrdId())) {
				message = ApplicationCache.getMessage("MSG_00002");
			}else {
				message = ApplicationCache.getMessage("MSG_00003");
			}
		}else {
			message = ApplicationCache.getMessage("MSG_00004");
		}
		return message;
	}
}
