package th.go.excise.ims.ia.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.support.ApplicationCache;
import th.co.baiwa.buckwaframework.support.domain.ParamInfo;
import th.go.excise.ims.ia.constant.IaConstants;
import th.go.excise.ims.ia.persistence.entity.IaQuestionnaireHdr;
import th.go.excise.ims.ia.persistence.repository.IaQuestionnaireHdrRepository;
import th.go.excise.ims.ia.persistence.repository.IaQuestionnaireMadeHdrRepository;
import th.go.excise.ims.ia.persistence.repository.IaQuestionnaireMadeRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.IaQuestionnaireHdrJdbcRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.IaQuestionnaireMadeHdrJdbcRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.IaQuestionnaireMadeJdbcRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.IaQuestionnaireSideJdbcRepository;

@Service
public class QuestionnaireService {

	private static final Logger logger = LoggerFactory.getLogger(QuestionnaireService.class);

//	****************** JDBC ****************** 
	@Autowired
	private IaQuestionnaireHdrJdbcRepository iaQuestionnaireHdrJdbcRepository;
	
	@Autowired
	private IaQuestionnaireMadeHdrJdbcRepository iaQuestionnaireMadeHdrJdbcRepository;
	
	@Autowired
	private IaQuestionnaireMadeJdbcRepository iaQuestionnaireMadeJdbcRepository;
	
	@Autowired
	private IaQuestionnaireSideJdbcRepository iaQuestionnaireSideJdbcRepository;


//	****************** JPA ****************** 
	@Autowired
	private IaQuestionnaireHdrRepository iaQuestionnaireHdrRepository;
	
	@Autowired
	private IaQuestionnaireMadeHdrRepository iaQuestionnaireMadeHdrRepository;
	
	@Autowired
	private IaQuestionnaireMadeRepository iaQuestionnaireMadeRepository;

	
	public BigDecimal updateStatusIaQuestionnaire(BigDecimal idHdr,String status) {
		
		iaQuestionnaireHdrJdbcRepository.updateStatus(idHdr, status);
//		iaQuestionnaireMadeHdrJdbcRepository.updateStatus(idHdr, status);
		
		
		return idHdr;
	}
	
	public BigDecimal downStatusIaQuestionnaire(BigDecimal idHdr) {
		
		List<ParamInfo> paramInfoList = ApplicationCache.getParamInfoListByGroupCode(IaConstants.QUESTIONNAIRE_STATUS.PARAM_GROUP_CODE);
		IaQuestionnaireHdr iaQuestionnaireHdr = iaQuestionnaireHdrRepository.findById(idHdr).get();
	
		for (ParamInfo paramInfo : paramInfoList) {
			
			if(paramInfo.getParamCode().equals(iaQuestionnaireHdr.getStatus())) {
				
				for (ParamInfo paramInfo2 : paramInfoList) {
					
					if(paramInfo.getSortingOrder()-1 == paramInfo2.getSortingOrder()) {
						
						iaQuestionnaireHdrJdbcRepository.updateStatus(idHdr, paramInfo2.getParamCode());
						if(IaConstants.QUESTIONNAIRE_STATUS.STATUS_4_CODE.equals(paramInfo.getSortingOrder().toString())) {
//							iaQuestionnaireMadeJdbcRepository
							
						}
//						iaQuestionnaireMadeHdrJdbcRepository.updateStatus(idHdr, paramInfo2.getParamCode());
					}
				}
			}
		}
		
		
		return idHdr;
	}
	
	public BigDecimal updateStatusIaQuestionnaireAutomatic(BigDecimal idHdr) {
	
		Integer countSide = iaQuestionnaireSideJdbcRepository.checkCountSide(idHdr);
		Integer countSideDtl = iaQuestionnaireSideJdbcRepository.checkCountSideDtl(idHdr);
		
//		*********** ID_HDR Check Table Side !=0 And Table SideDTL Count != 0 *********** 
		if(countSideDtl==0&&countSide!=0) {
			String status = IaConstants.QUESTIONNAIRE_STATUS.STATUS_2_CODE;
			updateStatusIaQuestionnaire(idHdr, status);
		}else {
			String status = IaConstants.QUESTIONNAIRE_STATUS.STATUS_1_CODE;
			updateStatusIaQuestionnaire(idHdr, status);
		}
		
		return idHdr;
	}
	
	

}