package edu.southalabama.csc527.smallworld.model;

// Stub?
public class Subplace {
	private boolean f_neededToEnter;
	private String f_blockedMsg = "";
	private String f_location;
	// This is bad and ugly idk
	private Integer f_takePoints = null;
	private Integer f_dropPoints = null;
	
	// This field is the name of a place, but
	// It doesn't take a NAME_TAG, it appears as text?
	private String f_name;
	Subplace(String name){
		setPlaceName(name);
	}
	Subplace(){
		setPlaceName("filler!");
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
	public String getLocation() {
		return f_location;
	}
	public void setLocation(String f_location) {
		this.f_location = f_location;
	}
	public Integer getTakePoints() {
		return f_takePoints;
	}
	public void setTakePoints(String f_takePoints) {
		this.f_takePoints = Integer.valueOf(f_takePoints);
	}
	public Integer getDropPoints() {
		return f_dropPoints;
	}
	public void setDropPoints(String f_dropPoints) {
		this.f_dropPoints = Integer.valueOf(f_dropPoints);
	}
	public String getName() {
		return f_name;
	}
	public void setPlaceName(String f_placeName) {
		this.f_name = f_placeName;
	}
	
}
