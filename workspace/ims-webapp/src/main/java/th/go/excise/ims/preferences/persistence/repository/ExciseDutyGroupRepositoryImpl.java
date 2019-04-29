package th.go.excise.ims.preferences.persistence.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import th.co.baiwa.buckwaframework.common.persistence.jdbc.CommonJdbcTemplate;
import th.co.baiwa.buckwaframework.security.constant.SecurityConstants.SYSTEM_USER;
import th.go.excise.ims.ws.client.pcc.inquirydutygroup.model.DutyGroup;

public class ExciseDutyGroupRepositoryImpl implements ExciseDutyGroupRepositoryCustom {
	
	private static final Logger logger = LoggerFactory.getLogger(ExciseDutyGroupRepositoryImpl.class);
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	@Override
	public void batchUpdate(List<DutyGroup> dutyGroupList) {
		logger.info("batchUpdate");
		
		final int BATCH_SIZE = 1000;
		
		List<String> insertColumnNames = new ArrayList<>(Arrays.asList(
			"EDG.DUTY_GROUP_ID",
			"EDG.DUTY_GROUP_CODE",
			"EDG.DUTY_GROUP_NAME",
			"EDG.DUTY_GROUP_STATUS",
			"EDG.SUP_DUTY_GROUP_CODE",
			"EDG.REG_STATUS",
			"EDG.BEGIN_DATE",
			"EDG.CREATED_BY",
			"EDG.CREATED_DATE"
		));
		
		StringBuilder sql = new StringBuilder();
		sql.append(" MERGE INTO EXCISE_DUTY_GROUP EDG ");
		sql.append(" USING DUAL ");
		sql.append(" ON (EDG.DUTY_GROUP_CODE = ?) ");
		sql.append(" WHEN MATCHED THEN ");
		sql.append("   UPDATE SET ");
		sql.append("     EDG.DUTY_GROUP_NAME = ?, ");
		sql.append("     EDG.DUTY_GROUP_STATUS = ?, ");
		sql.append("     EDG.SUP_DUTY_GROUP_CODE = ?, ");
		sql.append("     EDG.REG_STATUS = ?, ");
		sql.append("     EDG.IS_DELETED = 'N', ");
		sql.append("     EDG.UPDATED_BY = ?, ");
		sql.append("     EDG.UPDATED_DATE = ? ");
		sql.append(" WHEN NOT MATCHED THEN ");
		sql.append("   INSERT (" + org.springframework.util.StringUtils.collectionToDelimitedString(insertColumnNames, ",") + ") ");
		sql.append("   VALUES (EXCISE_DUTY_GROUP_SEQ.NEXTVAL" + org.apache.commons.lang3.StringUtils.repeat(",?", insertColumnNames.size() - 1) + ") ");
		
		commonJdbcTemplate.batchUpdate(sql.toString(), dutyGroupList, BATCH_SIZE, new ParameterizedPreparedStatementSetter<DutyGroup>() {
			public void setValues(PreparedStatement ps, DutyGroup dutyGroup) throws SQLException {
				List<Object> paramList = new ArrayList<Object>();
				// Using Condition
				paramList.add(dutyGroup.getGroupId());
				// Update Statement
				paramList.add(dutyGroup.getGroupName());
				paramList.add(dutyGroup.getGroupStatus());
				paramList.add(dutyGroup.getSupGroupId());
				paramList.add(dutyGroup.getRegStatus());
				paramList.add(SYSTEM_USER.BATCH);
				paramList.add(LocalDateTime.now());
				// Insert Statement
				paramList.add(dutyGroup.getGroupId());
				paramList.add(dutyGroup.getGroupName());
				paramList.add(dutyGroup.getGroupStatus());
				paramList.add(dutyGroup.getSupGroupId());
				paramList.add(dutyGroup.getRegStatus());
				paramList.add(LocalDate.parse(dutyGroup.getBeginDate(), DateTimeFormatter.BASIC_ISO_DATE));
				paramList.add(SYSTEM_USER.BATCH);
				paramList.add(LocalDateTime.now());
				commonJdbcTemplate.preparePs(ps, paramList.toArray());
			}
		});
	}

}
