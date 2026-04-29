package edu.southalabama.csc527.smallworld.model;

public class LocationRule extends BlockedLocation {
	private Integer f_takePoints = 0;
	private Integer f_dropPoints = 0;

	LocationRule(String placeName, String itemName, boolean neededToEnter, String blockedMsg, Integer takePoints, Integer dropPoints){
		super(placeName, itemName, blockedMsg);
		f_neededToEnter = neededToEnter;
		f_blockedMsg = blockedMsg;
		f_takePoints = takePoints;
		f_dropPoints = dropPoints;
	}
	public LocationRule(LocationRule l) {
		super(l.f_placeName, l.f_unlockerName, l.f_blockedMsg);
		f_neededToEnter = l.f_neededToEnter;
		f_blockedMsg = l.f_blockedMsg;
		f_takePoints = l.f_takePoints;
		f_dropPoints = l.f_dropPoints;
	}
	public LocationRule(String placeName, String itemName, String neededToEnter, String blockedMsg, String takePoints, String dropPoints) {
		super(placeName, itemName, blockedMsg);
		f_neededToEnter = (neededToEnter != null ? (neededToEnter.equals("Y") ? true : false) : false);
		f_blockedMsg = blockedMsg;
		f_takePoints = World.parseInteger(takePoints);
		f_dropPoints = World.parseInteger(dropPoints);
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
