package edu.southalabama.csc527.smallworld.model;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class Item {
	private String f_name;
	private String f_article;
	private String f_location;
	private int f_takePoints;
	private int f_dropPoints;
	private final Map<String, LocationRule> f_keyToSubplace = new HashMap<>();
	
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
	public LocationRule getSubplaceByName(String name) {
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
	public void setLocation(String location) {
		f_location = location;
	}
	
	// I don't know where this should go, but 
	// Because the Item makes the Subplace class
	// And needs the Subplace's attributes,
	// I'm letting Item make its Subplaces
	public Set<LocationRule> getSubplaces() {
		return new HashSet<LocationRule>(f_keyToSubplace.values());
	}

	public LocationRule getLocationRule(String name) {
		assert (name != null);
		LocationRule result = getSubplaceByName(name);
		if (result instanceof LocationRule)
			return (LocationRule) result;
		else
			return null;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Item && ((Item) obj).getName().equals(f_name);
	}
}