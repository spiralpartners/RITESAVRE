package jp.enpit.cloud.ritesavre.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.enpit.cloud.ritesavre.model.TracModel;
import jp.enpit.cloud.ritesavre.util.TracDBUtils;
import jp.enpit.cloud.ritesavre.view.EstimateActualSum;
import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.TaskEstimation;
import jp.enpit.cloud.ritesavre.view.TaskEstimationEntity;

/**
 * milestoneごとのタスクごと見積時間予測精度の取得
 * @author igaki
 *
 */
public class DisplayEstimationAccuracyController {
	public List<TaskEstimation> execute(MilestoneForm msf){
		TaskEstimationEntity tee = new TaskEstimationEntity();
		List<TaskEstimation> taskList = new ArrayList<TaskEstimation>();
		Connection conn = null;
		try {
			conn = TracDBUtils.getConnection(msf.getProject());
			TracModel trac = new TracModel(conn);
			taskList = trac.getTaskEffortAndTotalHours(msf.getMilestone());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return taskList;
	}

	public EstimateActualSum execute2(MilestoneForm msf){
		EstimateActualSum eas = null;
		Connection conn = null;
		try {
			conn = TracDBUtils.getConnection(msf.getProject());
			TracModel trac = new TracModel(conn);
			eas = trac.getEstimateTimeSum(msf.getMilestone());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eas;
	}

}
