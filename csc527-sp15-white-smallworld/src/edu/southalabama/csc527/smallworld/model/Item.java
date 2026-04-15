package edu.southalabama.csc527.smallworld.model;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

// This is a stub.
public class Item extends WorldElement {
	private String f_name;
	private String f_article;
	private String f_location;
	private int f_takePoints;
	private int f_dropPoints;
	private final Map<String, Subplace> f_keyToSubplace = new HashMap<String, Subplace>();
	
	Item(){
		f_name = "master sword";
		f_article = "THE";
		f_location = "Sacred Grove";
		f_takePoints = 1000000;
		f_dropPoints = -9999;
	}
	
	Item(String name, String article, String location, int takePoints, int dropPoints){
		f_name = name;
		f_article = article;
		f_location = location;
		f_takePoints = takePoints;
		f_dropPoints = dropPoints;
	}
	public Subplace getSubplaceByName(String name) {
		assert (name != null);
		return f_keyToSubplace.get(name.toUpperCase());
	}
	public int getTakePoints() {
		return f_takePoints;
	}
	public int getDropPoints() {
		return f_dropPoints;
	}
	public String getName() {
		return f_name;
	}
	public String getArticle() {
		return f_article;
	}
	public String getLocation() {
		return f_location;
	}
	
	// I don't know where this should go, but 
	// Because the Item makes the Subplace class
	// And needs the Subplace's attributes,
	// I'm letting Item make its Subplaces
	public Set<Subplace> getSubplaces() {
		return new HashSet<Subplace>(f_keyToSubplace.values());
	}
	public Subplace getSubplace(String name) {
		assert (name != null);
		Subplace result = getSubplaceByName(name);
		if (result instanceof Subplace)
			return (Subplace) result;
		else
			return null;
	}
	// This feels very inelegant, but breaking the types of subplaces into
	// Two types: Points and Keys, seems overly specified?
	public Subplace createSubplace(String name, String neededToEnter, String blockedMsg, String takePoints, String dropPoints){
		assert (name != null);
		Subplace newSubplace = new Subplace(name);
		if(neededToEnter != null) 
			newSubplace.setNeededToEnter(true);
		if(blockedMsg != null) 
			newSubplace.setBlockedMsg(blockedMsg);
		if(takePoints != null) 
			newSubplace.setTakePoints(takePoints);
		if(dropPoints != null) 
			newSubplace.setTakePoints(takePoints);
		f_keyToSubplace.put(name.toUpperCase(), newSubplace);
		return newSubplace;
	}
}