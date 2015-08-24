package jp.enpit.cloud.ritesavre.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.Ticket;
import jp.enpit.cloud.ritesavre.model.TicketsByOwner;
import jp.enpit.cloud.ritesavre.model.TracModel;
import jp.enpit.cloud.ritesavre.util.TracDBUtils;
import jp.enpit.cloud.ritesavre.view.AssignmentConstraintsEntity;
import jp.enpit.cloud.ritesavre.view.AssignmentEntity;

/**
 * tracのDBを見に行き，fixedで終わっているチケットの分類及びユーザを計測して返す
 * @author igaki
 *
 */
public class DisplayAssignmentListController {
	private Logger logger;

	public DisplayAssignmentListController(){
		logger = Logger.getLogger(getClass().getName());
	}

	public AssignmentConstraintsEntity execute(String project){
		logger.info("DisplayAssignmentListController.execute");
		Connection conn = null;
		List<TicketsByOwner> tboList = new ArrayList<TicketsByOwner>();

		try {
			conn = TracDBUtils.getConnection(project);
			TracModel trac = new TracModel(conn);

			/**
			 * Ticketに登録されているownerのリストを取得
			 */
			List<String> owners = trac.getOwnerList();

			for(String s: owners){
				TicketsByOwner tbo = trac.getCurrentTicketsByOwnerWithTotal(s);
				tboList.add(tbo);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return calcAssignment(tboList);


	}

	private AssignmentConstraintsEntity calcAssignment(List<TicketsByOwner> tboList){
		AssignmentConstraintsEntity asc = new AssignmentConstraintsEntity();
		List<AssignmentEntity> asEntityList = new ArrayList<AssignmentEntity>();

		for(TicketsByOwner t: tboList){
			AssignmentEntity asEntity = new AssignmentEntity();
			asEntity.setOwner(t.getOwner());
			asEntity.setCreateSourceNum(t.getCreateSource().size());
			asEntity.setCreateUTestNum(t.getCreateUTest().size());
			asEntity.setCreateITestNum(t.getCreateITest().size());

			asEntity.setReviewNum(t.getReview().size());
			asEntity.setiTestNum(t.getiTest().size());

			asEntity.setDebugSourceNum(t.getDebugSource().size());
			asEntity.setDebugUTestNum(t.getDebugUTest().size());
			asEntity.setDebugITestNum(t.getDebugITest().size());

			asEntity.setOtherTaskNum(t.getOtherTask().size());

			asEntity.setCreateSourceTimeNum(this.calcTimeSum(t.getCreateSource()));
			asEntity.setCreateUTestTimeNum(this.calcTimeSum(t.getCreateUTest()));
			asEntity.setCreateITestTimeNum(this.calcTimeSum(t.getCreateITest()));

			asEntity.setReviewTimeNum(this.calcTimeSum(t.getReview()));
			asEntity.setiTestTimeNum(this.calcTimeSum(t.getiTest()));

			asEntity.setDebugSourceTimeNum(this.calcTimeSum(t.getDebugSource()));
			asEntity.setDebugUTestTimeNum(this.calcTimeSum(t.getDebugUTest()));
			asEntity.setDebugITestTimeNum(this.calcTimeSum(t.getDebugITest()));

			asEntity.setOtherTaskTimeNum(this.calcTimeSum(t.getOtherTask()));

			
			asEntityList.add(asEntity);


			asc.setCreateSourceSum(asc.getCreateSourceSum()+asEntity.getCreateSourceNum());
			asc.setCreateUTestSum(asc.getCreateUTestSum()+asEntity.getCreateUTestNum());
			asc.setCreateITestSum(asc.getCreateITestSum()+asEntity.getCreateITestNum());

			asc.setReviewSum(asc.getReviewSum()+asEntity.getReviewNum());
			asc.setiTestSum(asc.getiTestSum()+asEntity.getiTestNum());

			asc.setDebugSourceSum(asc.getDebugSourceSum()+asEntity.getDebugSourceNum());
			asc.setDebugUTestSum(asc.getDebugUTestSum()+asEntity.getDebugUTestNum());
			asc.setDebugITestSum(asc.getCreateITestSum()+asEntity.getDebugITestNum());
			asc.setOtherTaskSum(asc.getOtherTaskSum()+asEntity.getOtherTaskNum());

		}
		asc.setAsEntityList(asEntityList);

		return asc;

	}
	private double calcTimeSum(List<Ticket> ticketList){
		double timeSum=0;
		for(Ticket t: ticketList){
			timeSum = timeSum + t.getTotalhours();
		}
		
		return timeSum;
	}

}
