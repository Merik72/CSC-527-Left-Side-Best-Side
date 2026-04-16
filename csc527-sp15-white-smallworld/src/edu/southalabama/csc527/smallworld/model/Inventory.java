package edu.southalabama.csc527.smallworld.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Inventory {
	private Map<String, Item> f_keyToItem = new HashMap<>();

	public void addItem(Item item) {
		f_keyToItem.put(item.getName().toUpperCase(), item);
	}

	// should solve the Get Inventory issue?
	public HashSet<Item> getItems() {
		return new HashSet<>(f_keyToItem.values());
	}

    public Item getItem(String name) {
        return f_keyToItem.get(name.toUpperCase());
    }

	public void removeItem(Item item) {
		f_keyToItem.remove(item.getName().toUpperCase());
	}

    @Override
    public String toString() {
        String items = "";
        for (Item i : f_keyToItem.values()) {
            items = items.concat("\"" + i.getName() + "\"\n");
        }
        return items;
    }
}