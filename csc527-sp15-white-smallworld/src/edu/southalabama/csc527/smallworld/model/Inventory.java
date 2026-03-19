package edu.southalabama.csc527.smallworld.model;

import java.util.List;

public class Inventory {
    private List<Item> f_items;

    public List<Item> getItems(){ return f_items; }

    public void addItem(Item item){ f_items.add(item); }

    public void removeItem(Item item) {
        f_items.removeIf(i -> i.getName().equalsIgnoreCase(item.getName()));
    }
}