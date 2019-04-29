package th.go.excise.ims.preferences.persistence.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.co.baiwa.buckwaframework.security.constant.SecurityConstants.SYSTEM_USER;
import th.go.excise.ims.ws.client.pcc.inquiryhospital.model.Hospital;

public class ExciseHospitalRepositoryImpl implements ExciseHospitalRepositoryCustom{
	 private static final Logger logger = LoggerFactory.getLogger(ExciseHolidayRepositoryImpl.class);
		
		@Autowired
		private CommonJdbcTemplate commonJdbcTemplate;
		
		@Override
		public void batchUpdate(List<Hospital> hospitalList) {
			logger.info("batchUpdate");
			
			final int BATCH_SIZE = 1000;
			
			List<String> insertColumnNames = new ArrayList<>(Arrays.asList(
				"EH.HOLIDAY_ID",
				"EH.HOSP_CODE",
				"EH.HOSP_TYPE",
				"EH.HOSP_CATE",
				"EH.HOSP_NAME",
				"EH.ADDRNO",
				"EH.THNNAME",
				"EH.TAMBOL_CODE",
				"EH.ZIPCODE",
				"EH.BEGIN_DATE",
				"EH.BEGIN_DATE",
				"EH.CREATED_BY",
				"EH.CREATED_DATE"
			));
			
			StringBuilder sql = new StringBuilder();
			sql.append(" MERGE INTO EXCISE_HOSPITAL EH ");
			sql.append(" USING DUAL ");
			sql.append(" ON (EH.HOSP_CODE = ?) ");
			sql.append(" WHEN MATCHED THEN ");
			sql.append("   UPDATE SET ");
			sql.append("     EH.HOSP_TYPE = ? , ");
			sql.append("     EH.HOSP_CATE = ? , ");
			sql.append("     EH.HOSP_NAME = ? , ");
			sql.append("     EH.ADDRNO = ? , ");
			sql.append("     EH.THNNAME = ? , ");
			sql.append("     EH.TAMBOL_CODE = ? , ");
			sql.append("     EH.ZIPCODE = ? , ");
			sql.append("     EH.IS_DELETED = 'N', ");
			sql.append("     EH.UPDATED_BY = ?, ");
			sql.append("     EH.UPDATED_DATE = ? ");
			sql.append(" WHEN NOT MATCHED THEN ");
			sql.append("   INSERT (" + org.springframework.util.StringUtils.collectionToDelimitedString(insertColumnNames, ",") + ") ");
			sql.append("   VALUES (EXCISE_BANK_SEQ.NEXTVAL" + org.apache.commons.lang3.StringUtils.repeat(",?", insertColumnNames.size() - 1) + ") ");
			
			commonJdbcTemplate.batchUpdate(sql.toString(), hospitalList, BATCH_SIZE, new ParameterizedPreparedStatementSetter<Hospital>() {
				public void setValues(PreparedStatement ps, Hospital hospital) throws SQLException {
					List<Object> paramList = new ArrayList<Object>();
					// Using Condition
					paramList.add(hospital.getHospCode());
					// Update Statement
					paramList.add(hospital.getHospType());
					paramList.add(hospital.getHospCate());
					paramList.add(hospital.getHospName());
					paramList.add(hospital.getAddrno());
					paramList.add(hospital.getThnname());
					paramList.add(hospital.getTambolCode());
					paramList.add(hospital.getZIPCODE());
			
					paramList.add(SYSTEM_USER.BATCH);
					paramList.add(LocalDateTime.now());
					// Insert Statement
				
					paramList.add(SYSTEM_USER.BATCH);
					paramList.add(LocalDateTime.now());
					commonJdbcTemplate.preparePs(ps, paramList.toArray());
				}
			});
		}

}
