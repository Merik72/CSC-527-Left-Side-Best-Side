package edu.southalabama.csc527.smallworld.model;

import java.util.List;

enum ActionType {
    PICKUP, DROP
}

public class Item {
    private String name;
    private String description;
    private String targetLocationName;
    private List<Integer> pointValues;

    Item(String name, String description, String targetLocationName, List<Integer> pointValues){
        assert(name != null);
        assert(description != null);
        this.name = name;
        this.description = description;
        this.targetLocationName = targetLocationName;
        this.pointValues = pointValues;
    }

    public void pickupItem(Player player){
        player.getInventory().add(this);
        player.addExperience(calculatePointValue(player, ActionType.PICKUP));
    }

    public void dropItem(Player player){
        player.getInventory().remove(this);
        player.addExperience(calculatePointValue(player, ActionType.DROP));
    }

    private int calculatePointValue(Player player, ActionType actionType) {
        int points = 0;
        switch (actionType) {
            case PICKUP -> points += this.pointValues.get(0);
            case DROP -> points += this.pointValues.get(1);
        }
        if (player.getLocation().getName().equals(this.targetLocationName)) {
            points += this.pointValues.get(2);
        }
        return points;
    }
}