package th.go.excise.ims.ta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.baiwa.buckwaframework.common.bean.BusinessException;
import th.co.baiwa.buckwaframework.common.util.ConvertDateUtils;
import th.co.baiwa.buckwaframework.preferences.constant.MessageConstants;
import th.go.excise.ims.ta.persistence.repository.jdbc.TaWorksheetCondDtlTaxJdbcRepository;
import th.go.excise.ims.ta.persistence.repository.jdbc.TaWorksheetCondHdrJdbcRepository;
import th.go.excise.ims.ta.persistence.repository.jdbc.TaxOperatorJdbcRepository;
import th.go.excise.ims.ta.vo.CondGroupVo;
import th.go.excise.ims.ta.vo.TaxOperatorFormVo;
import th.go.excise.ims.ta.vo.TaxOperatorVo;
import th.go.excise.ims.ta.vo.TaxOperatorVo.TaxOperatorVoList;
import th.go.excise.ims.ta.vo.YearMonthVo;

import java.util.Date;
import java.util.List;

@Service
public class TaxOperatorService {

    @Autowired
    private TaxOperatorJdbcRepository taxOperatorRepository;

    @Autowired
    private TaWorksheetCondHdrJdbcRepository taWorksheetCondHdrJdbcRepository;

    @Autowired
    private TaWorksheetCondDtlTaxJdbcRepository taWorksheetCondDtlTaxJdbcRepository;

    public TaxOperatorVo getOperator(TaxOperatorFormVo formVo) throws BusinessException {
        List<String> listCondGroups = this.taxOperatorRepository.listCondGroups(formVo.getAnalysisNumber());
        List<TaxOperatorVoList> list = this.taxOperatorRepository.getTaxOperator(formVo.getAnalysisNumber());
        if (list.isEmpty()) throw new BusinessException(MessageConstants.TA.NOT_DATA_CODE,MessageConstants.TA.NOT_DATA );
        TaxOperatorVo vo = new TaxOperatorVo();
        vo.setCondGroups(listCondGroups);
        vo.setDatas(list);
        return vo;
    }

    public List<String> findAllAnalysisNumber(){
        return this.taWorksheetCondHdrJdbcRepository.findAllAnalysisNumber();
    }

    public YearMonthVo monthStart(TaxOperatorFormVo formVo) {
        YearMonthVo obj = this.taWorksheetCondHdrJdbcRepository.monthStart(formVo.getAnalysisNumber());

        Date ymStart = ConvertDateUtils.parseStringToDate(obj.getYearMonthStart(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
        Date ymEnd = ConvertDateUtils.parseStringToDate(obj.getYearMonthEnd(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
        String ymStartStr = ConvertDateUtils.formatDateToString(ymStart, ConvertDateUtils.MM_YYYY, ConvertDateUtils.LOCAL_TH);
        String ymEndStr = ConvertDateUtils.formatDateToString(ymEnd, ConvertDateUtils.MM_YYYY, ConvertDateUtils.LOCAL_TH);

        obj.setYearMonthStart(ymStartStr);
        obj.setYearMonthEnd(ymEndStr);
        return obj;
    }

    public List<CondGroupVo> findCondGroupDtl(TaxOperatorFormVo formVo){
        return this.taWorksheetCondDtlTaxJdbcRepository.findCondGroupDtl(formVo.getAnalysisNumber());
        }
        }
