package edu.southalabama.csc527.smallworld.model;

public class LocationRule {
	private final String f_placeName;
	private final String f_itemNeededName;
	private boolean f_neededToEnter;
	private String f_blockedMsg = "";
	private Integer f_takePoints = 0;
	private Integer f_dropPoints = 0;

	LocationRule(String placeName, String itemName, boolean neededToEnter, String blockedMsg, Integer takePoints, Integer dropPoints){
        f_itemNeededName = itemName;
		f_placeName = placeName;
		f_neededToEnter = neededToEnter;
		f_blockedMsg = blockedMsg;
		f_takePoints = takePoints;
		f_dropPoints = dropPoints;
	}

	public String getPlaceName() {
		return f_placeName;
	}
	public String getItemNeededName() {
		return f_itemNeededName;
	}
	public boolean getNeededToEnter() {
		return f_neededToEnter;
	}
	public void setNeededToEnter(boolean f_neededToEnter) {
		this.f_neededToEnter = f_neededToEnter;
	}
	public String getBlockedMsg() {
		return f_blockedMsg;
	}
	public void setBlockedMsg(String f_blockedMsg) {
		this.f_blockedMsg = f_blockedMsg;
	}
	public Integer getTakePoints() {
		return f_takePoints;
	}
	public void setTakePoints(int points) {
		f_takePoints = points;
	}
	public Integer getDropPoints() {
		return f_dropPoints;
	}
	public void setDropPoints(int points) {
		f_dropPoints = points;
	}
}
