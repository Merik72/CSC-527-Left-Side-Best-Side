package edu.southalabama.csc527.smallworld.model;

public class LocationRule{
	private Integer f_takePoints = 0;
	private Integer f_dropPoints = 0;
	protected String f_placeName;
	protected String f_unlockerName;
	protected String f_blockedMsg = "";
	protected Boolean f_neededToEnter = true;
	
	public LocationRule(String placeName, String unlocker, String blockedMsg) {
		f_placeName = placeName;
		f_unlockerName = unlocker;
		f_blockedMsg = blockedMsg;
	}
	LocationRule(String placeName, String unlocker, boolean neededToEnter, String blockedMsg, Integer takePoints, Integer dropPoints){
		f_placeName = placeName;
		f_unlockerName = unlocker;
		f_blockedMsg = blockedMsg;
		f_neededToEnter = neededToEnter;
		f_blockedMsg = blockedMsg;
		f_takePoints = takePoints;
		f_dropPoints = dropPoints;
	}
	public LocationRule(LocationRule l) {
		f_placeName = l.f_placeName;
		f_unlockerName = l.f_unlockerName;
		f_blockedMsg = l.f_blockedMsg;
		f_neededToEnter = l.f_neededToEnter;
		f_blockedMsg = l.f_blockedMsg;
		f_takePoints = l.f_takePoints;
		f_dropPoints = l.f_dropPoints;
	}
	public LocationRule(String placeName, String unlocker, String neededToEnter, String blockedMsg, String takePoints, String dropPoints) {
		f_placeName = placeName;
		f_unlockerName = unlocker;
		f_blockedMsg = blockedMsg;
		f_neededToEnter = (neededToEnter != null ? (neededToEnter.equals("Y") ? true : false) : false);
		f_blockedMsg = blockedMsg;
		f_takePoints = World.parseInteger(takePoints);
		f_dropPoints = World.parseInteger(dropPoints);
	}


	public String getPlaceName() {
		return f_placeName;
	}
	public void setPlaceName(String placeName) {
		f_placeName = placeName;
	}
	public String getUnlockerName() {
		return f_unlockerName;
	}
	public void setUnlockerName(String unlockerName) {
		f_unlockerName = unlockerName;
	}
	public String getBlockedMsg() {
		return f_blockedMsg;
	}
	public void setBlockedMsg(String blockedMsg) {
		f_blockedMsg = blockedMsg;
	}	
	public boolean getNeededToEnter() {
		return f_neededToEnter;
	}
	public String getItemNeededName() {
		return f_unlockerName;
	}
	public void setNeededToEnter(boolean f_neededToEnter) {
		this.f_neededToEnter = f_neededToEnter;
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
