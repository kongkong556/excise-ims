package th.go.excise.ims.ia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.go.excise.ims.ia.persistence.entity.IaQuestionnaireSide;
import th.go.excise.ims.ia.persistence.entity.IaQuestionnaireSideDtl;
import th.go.excise.ims.ia.persistence.repository.IaQuestionnaireSideDtlRepository;
import th.go.excise.ims.ia.persistence.repository.IaQuestionnaireSideRepository;
import th.go.excise.ims.ia.persistence.repository.jdbc.IaQuestionnaireSideJdbcRepository;
import th.go.excise.ims.ia.vo.Int020101NameVo;
import th.go.excise.ims.ia.vo.Int020101Vo;
import th.go.excise.ims.ia.vo.Int020101YearVo;

@Service
public class Int020101Service {

	private static final Logger logger = LoggerFactory.getLogger(Int020101Service.class);

	@Autowired
	private IaQuestionnaireSideJdbcRepository iaQtnSideJdbcRep;

	@Autowired
	private IaQuestionnaireSideRepository iaQtnSideRep;

	@Autowired
	private IaQuestionnaireSideDtlRepository iaQtnSideDtlRep;

	public List<Int020101Vo> findAll() {
		return iaQtnSideJdbcRep.findAll();
	}

	public List<Int020101Vo> findByIdHead(String idHeadStr) {
		BigDecimal idHead = new BigDecimal(idHeadStr);
		return iaQtnSideJdbcRep.findByIdHead(idHead);
	}

	public List<Int020101YearVo> findByUsername(String username) {
		return iaQtnSideJdbcRep.findByUsername(username);
	}

	public List<Int020101NameVo> findByYearAndUsername(String year, String username) {
		return iaQtnSideJdbcRep.findByYearAndUsername(year, username);
	}

	public IaQuestionnaireSide save(IaQuestionnaireSide request) {
		return iaQtnSideRep.save(request);
	}

	public List<IaQuestionnaireSide> saveAll(List<IaQuestionnaireSide> request) {
		// array of old id
		List<BigDecimal> ids = new ArrayList<>();
		for (IaQuestionnaireSide req : request) {
			ids.add(req.getId()); // add idSide
			req.setId(null); // remove old id
		}

		// Saved
		List<IaQuestionnaireSide> newSides = (List<IaQuestionnaireSide>) iaQtnSideRep.saveAll(request);
		logger.debug("Int020101Service::saveAll => SAVED");

		// array of new id
		List<BigDecimal> idsNew = new ArrayList<>();
		for (IaQuestionnaireSide newSide : newSides) {
			idsNew.add(newSide.getId()); // add idSide
		}
		List<IaQuestionnaireSideDtl> qtnDtls = iaQtnSideJdbcRep.findBySideIds(ids);
		for (int i = 0; i < qtnDtls.toArray().length; i++) {
			for (int j = 0; j < ids.toArray().length; j++) {
				if (ids.get(j).compareTo(qtnDtls.get(i).getIdSide()) == 0) {
					qtnDtls.get(i).setIdSide(idsNew.get(j));
				}
			}
		}
		iaQtnSideDtlRep.saveAll(qtnDtls);
		return newSides;
	}

	public IaQuestionnaireSide update(String idStr, IaQuestionnaireSide request) {
		BigDecimal id = new BigDecimal(idStr);
		IaQuestionnaireSide data = iaQtnSideJdbcRep.findOne(id);
		data.setSideName(request.getSideName());
		return iaQtnSideRep.save(data);
	}

	public IaQuestionnaireSide delete(String idStr) {
		BigDecimal id = new BigDecimal(idStr);
		IaQuestionnaireSide data = iaQtnSideJdbcRep.findOne(id);
		iaQtnSideRep.deleteById(id);
		return data;
	}

	public IaQuestionnaireSide findOne(String idStr) {
		BigDecimal id = new BigDecimal(idStr);
		return iaQtnSideJdbcRep.findOne(id);
	}

}