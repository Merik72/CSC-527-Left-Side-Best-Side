package edu.southalabama.csc527.smallworld.model;

import java.util.List;

public class Item {
    private String f_name;
    private String f_description;
    private Place f_place;
    private String f_targetLocationName;
    private List<Integer> f_pointValues;

    Item(String name, String description, String targetLocationName, List<Integer> pointValues){
        assert(name != null);
        assert(description != null);
        this.f_name = name;
        this.f_description = description;
        this.f_targetLocationName = targetLocationName;
        this.f_pointValues = pointValues;
    }

    public Place getPlace() { return f_place; }

    public String getName(){ return f_name; }

    public int calculatePointValue(Place place, ItemAction itemAction) {
        int points = 0;
        switch (itemAction) {
            case PICKUP -> points += this.f_pointValues.get(0);
            case DROP -> points += this.f_pointValues.get(1);
        }
        if (place.getName().equals(this.f_targetLocationName)) {
            points += this.f_pointValues.get(2);
        }
        return points;
    }
}