package jp.enpit.cloud.ritesavre.view;

import java.util.List;

public class ComponentInfo {
	String component;
	List<TicketType> ticketTypeList;
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public List<TicketType> getTicketTypeList() {
		return ticketTypeList;
	}
	public void setTicketTypeList(List<TicketType> ticketTypeList) {
		this.ticketTypeList = ticketTypeList;
	}
	
	

}
