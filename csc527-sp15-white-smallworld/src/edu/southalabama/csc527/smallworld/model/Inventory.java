package edu.southalabama.csc527.smallworld.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Inventory {
	private Map<String, Item> f_keyToItem = new HashMap<String, Item>();
	// private String f_location;
	
	public void addItem(Item item) {
		f_keyToItem.put(item.getName().toUpperCase(), item);
	}
	
	// should solve the Get Inventory issue?
	public HashSet<Item> getItems() {
		return new HashSet<Item>(f_keyToItem.values());
	}
	
	public void removeItem(Item item) {
		// Stub
	}
}