package edu.southalabama.csc527.smallworld.model;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class Event {
    private String f_name;
    private Item f_activationItem;
    private Place f_location;
    private String f_activationType;
    private Boolean f_triggered;
    private Boolean f_consumeItem;
    private String f_description;

    // holds items and rules in event for spawning when event triggered
    private List<Item> f_spawnableItems;
    private List<LocationRule> f_spawnableLocationRules;
    private HashMap<Place, String> f_alternatePlaceDescriptions;

    public Event(
            String name,
            Item activationItem,
            Place location,
            String activationType,
            Boolean triggered,
            Boolean consumeItem,
            String description,
            List<Item> spawnableItems,
            List<LocationRule> spawnableLocationRules,
            HashMap<Place,String> alternatePlaceDescriptions
    ){
        f_name = name;
        f_activationItem = activationItem;
        f_location = location;
        f_activationType = activationType;
        f_triggered = triggered;
        f_consumeItem = consumeItem;
        f_description = description;
        f_spawnableItems = spawnableItems;
        f_spawnableLocationRules = spawnableLocationRules;
        f_alternatePlaceDescriptions = alternatePlaceDescriptions;
    }

    public String trigger(World world, Player player){
        if (player.getInventory().getItems().contains(f_activationItem)){
            f_triggered = true;
            spawnItems(world);
            spawnRules(world);
            swapPlaceDescription(world);
            return "Event " + f_name + " triggered!";
        }
        return "Event" + f_name + " failed to trigger because player did not have " + f_activationItem.getName() + " in inventory.";
    }

    private void spawnItems(World world){
        for(Item i : f_spawnableItems){
            world.createItem(
                    i.getName(),
                    i.getArticle(),
                    i.getLocation(),
                    i.getTakePoints().toString(),
                    i.getDropPoints().toString()
            );
        }
    }

    private void spawnRules(World world){
        for (LocationRule r : f_spawnableLocationRules){
            String neededToEnter = r.getNeededToEnter() ? "Y" : "N";
            world.createLocationRule(
                    r.getPlaceName(),
                    r.getItemNeededName(),
                    neededToEnter,
                    r.getBlockedMsg(),
                    r.getTakePoints().toString(),
                    r.getDropPoints().toString()
            );
        }
    }

    private void swapPlaceDescription(World world) {
        for (Place place : world.getPlaces().getObjects()) {
            if (f_alternatePlaceDescriptions.containsKey(place)) {
                place.setDescription(f_alternatePlaceDescriptions.get(place));
            }
        }
    }
}