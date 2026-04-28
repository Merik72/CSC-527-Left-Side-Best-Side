package edu.southalabama.csc527.smallworld.model;

public class BlockedLocation {
	protected String f_placeName;
	protected String f_unlockerName;
	protected String f_blockedMsg = "";
	
	BlockedLocation(String placeName, String unlocker, String blockedMsg){
		f_placeName = placeName;
		f_unlockerName = unlocker;
		f_blockedMsg = blockedMsg;
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
}
