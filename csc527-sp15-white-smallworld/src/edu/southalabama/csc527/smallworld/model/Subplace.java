package edu.southalabama.csc527.smallworld.model;

// Stub?
public class Subplace {
	// This field is the name of a place, but
	// It doesn't take a NAME_TAG, it appears as text?
	private String f_name;
	private boolean f_neededToEnter;
	private String f_blockedMsg = "";
	// This is bad and ugly idk
	private Integer f_takePoints = null;
	private Integer f_dropPoints = null;
	
	public Subplace(){
		f_neededToEnter = true;
		f_blockedMsg = "You're mom is so fat she blocked the way.";
		f_name = "filler!";
		f_takePoints = 531;
		f_dropPoints = 8008;
	}
	
	/*
	public Subplace(String name, String neededToEnter, String blockedMsg, String takePoints,  String dropPoints) {
		f_name = name;
	}
	*/
	public Subplace(String name){
		f_name = name;
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
