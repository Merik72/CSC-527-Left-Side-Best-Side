package edu.southalabama.csc527.smallworld.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> f_items;

    Inventory(){
        f_items = new ArrayList<>();
    }

    public List<Item> getItems(){ return f_items; }

    public void addItem(Item item){ f_items.add(item); }

    public void removeItem(Item item) {
        f_items.removeIf(i -> i.getName().equalsIgnoreCase(item.getName()));
    }

    @Override
    public String toString() {
        String items = "";
        if (!f_items.isEmpty()){
            for (Item i : f_items) {
                items.concat(i.getName() + "\n");
            }
        }
        return items;
    }
}