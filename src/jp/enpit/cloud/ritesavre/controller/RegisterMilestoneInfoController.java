package jp.enpit.cloud.ritesavre.controller;

import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.MilestoneModel;
import jp.enpit.cloud.ritesavre.view.MilestoneInfoForm;

public class RegisterMilestoneInfoController {
	private Logger logger;

	public RegisterMilestoneInfoController(){
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * 
	 * @param msif
	 */
	public void execute(MilestoneInfoForm msif){
		logger.info("RegisterMileStoneStartDateTime.execute");
		MilestoneModel msm = new MilestoneModel();
		//msm.registerMilestoneDateTime(msif.getMilestone(), msif.getMilestoneStart());
		msm.registerMilestone(msif.getMilestone(), msif.getProject(),msif.getMilestoneStart());

	}

}
