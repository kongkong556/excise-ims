package th.co.baiwa.excise.ia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.baiwa.buckwaframework.common.bean.ResponseDataTable;
import th.co.baiwa.buckwaframework.preferences.persistence.dao.SEQDao;
import th.co.baiwa.buckwaframework.security.util.UserLoginUtils;
import th.co.baiwa.excise.domain.DataTableRequest;
import th.co.baiwa.excise.domain.ia.Int023MappingVO;
import th.co.baiwa.excise.ia.persistence.dao.QuestionnaireDetailDao;
import th.co.baiwa.excise.ia.persistence.dao.QuestionnaireMainDao;
import th.co.baiwa.excise.ia.persistence.dao.QuestionnaireMinorDao;
import th.co.baiwa.excise.ia.persistence.entity.QuestionnaireDetail;
import th.co.baiwa.excise.ia.persistence.entity.QuestionnaireMain;
import th.co.baiwa.excise.ia.persistence.entity.QuestionnaireMinor;
import th.co.baiwa.excise.ia.persistence.vo.Int023Vo;

@Service
public class QuestionnaireDetailService {
	private Logger logger = LoggerFactory.getLogger(QuestionnaireDetailService.class);
	
	@Autowired
	private QuestionnaireDetailDao questionnaireDetailDao;
	
	@Autowired
	private QuestionnaireMainDao qtnMainDao;
	
	@Autowired
	private QuestionnaireMinorDao qtnMinorDao;

	@Autowired
	private SEQDao seqDao;
	
	public ResponseDataTable<Int023Vo<QuestionnaireMinor>> findByCriteria(DataTableRequest req) {
		ResponseDataTable<Int023Vo<QuestionnaireMinor>> resp = new ResponseDataTable<Int023Vo<QuestionnaireMinor>>();
		List<Int023Vo<QuestionnaireMinor>> int023 = new ArrayList<>();
		List<QuestionnaireMain> main = qtnMainDao.findForInt023(req.getStart(), req.getLength());
		logger.info(" start: {} length: {} mainSize: {}", req.getStart(), req.getLength(), main.size());
		Long oldId = 0L;
		for(QuestionnaireMain ma: main) {
			if (ma.getQtnMainDetailId() != oldId) {
				logger.info("{}", oldId);
				oldId = ma.getQtnMainDetailId();
				Int023Vo<QuestionnaireMinor> in023 = new Int023Vo<QuestionnaireMinor>();
				QuestionnaireMinor min = new QuestionnaireMinor();
				List<QuestionnaireMinor> minor = new ArrayList<QuestionnaireMinor>();
				min.setMainId(oldId);
				minor = qtnMinorDao.findByCriteria(min, 0, 0);
				in023.setQtnReportManId(ma.getQtnMainDetailId());
				in023.setQtnMainDetail(ma.getQtnMainDetail());
				in023.setDetail(minor);
				int023.add(in023);
			}
		}
		resp.setData(int023);
		return resp;
	}

	public List<QuestionnaireDetail> findByCriteria(QuestionnaireDetail QuestionnaireDetail) {
		return questionnaireDetailDao.findByCriteria(QuestionnaireDetail);
	}

	public Integer createQuestionnaireDetail(Int023MappingVO int023MappingVO) {
		logger.info("createQuestionnaireDetail");
		Date processDate = new Date();
		String userlogin = UserLoginUtils.getCurrentUsername();
		int countInsert = 0;
		List<QuestionnaireDetail> questionnaireDetailList = new ArrayList<QuestionnaireDetail>();
		QuestionnaireDetail questionnaireDetail = new QuestionnaireDetail();
		BigDecimal detailId = seqDao.autoNumberRunningBySeqName("IA_QTN_DETAIL_SEQ");
		logger.info("Questionnaire Id : "+ detailId);

		questionnaireDetail.setQtnDetailId(detailId);
		questionnaireDetail.setHeaderCode("H001");
		questionnaireDetail.setQtnMainDetail(int023MappingVO.getMainDetail());
		questionnaireDetail.setVersion(1);
		questionnaireDetail.setIsDeleted("N");
		questionnaireDetail.setCreatedBy(userlogin);
		questionnaireDetail.setCreatedDate(processDate);
		questionnaireDetail.setUpdatedBy(userlogin);
		questionnaireDetail.setUpdatedDate(processDate);
		questionnaireDetailList.add(questionnaireDetail);

		for (String minorDetail : int023MappingVO.getMinorDetail()) {
			questionnaireDetail = new QuestionnaireDetail();
			questionnaireDetail.setQtnDetailId(seqDao.autoNumberRunningBySeqName("IA_QTN_DETAIL_SEQ"));
			questionnaireDetail.setMasterId(detailId);
			questionnaireDetail.setHeaderCode("FIXED HEADER_CODE");
			questionnaireDetail.setQtnMainDetail(minorDetail);
			questionnaireDetail.setVersion(1);
			questionnaireDetail.setIsDeleted("N");
			questionnaireDetail.setCreatedBy(userlogin);
			questionnaireDetail.setCreatedDate(processDate);
			questionnaireDetail.setUpdatedBy(userlogin);
			questionnaireDetail.setUpdatedDate(processDate);
			questionnaireDetailList.add(questionnaireDetail);
		}
		countInsert = questionnaireDetailDao.createQuestionnaireDetail(questionnaireDetailList);
		logger.info(" countInsert : "+ countInsert);
		return countInsert;
	}

	
	public ResponseDataTable<QuestionnaireDetail> findByCriteriaForDatatable(QuestionnaireDetail questionnaireDetail , DataTableRequest dataTableRequest) {
		ResponseDataTable<QuestionnaireDetail> responseDataTable = new ResponseDataTable<QuestionnaireDetail>();
		List<QuestionnaireDetail> questionnaireDetailList = questionnaireDetailDao.findByCriteriaDataTable(questionnaireDetail, dataTableRequest.getStart().intValue(), dataTableRequest.getLength().intValue());
		responseDataTable.setDraw(dataTableRequest.getDraw().intValue() + 1);
		long count = questionnaireDetailDao.countQuestionnaireDetail(questionnaireDetail);
		responseDataTable.setData(questionnaireDetailList);
		responseDataTable.setRecordsTotal((int) count);
		responseDataTable.setRecordsFiltered((int) count);
		return responseDataTable;
	}
}
