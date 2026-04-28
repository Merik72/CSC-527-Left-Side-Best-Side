package edu.southalabama.csc527.smallworld.model;

import java.util.List;

public class Event {
    private String f_name;
    private String f_description;
    private String f_activationType;
    private Item f_activationItem;
    private Boolean f_triggered;
    private Boolean f_consumeItem;

    // holds items and rules in event for spawning when event triggered
    private List<Item> f_itemsToSpawn;
    private List<LocationRule> f_rulesToSpawn;

    public Event(String name, String description, String activationType, Item activationItem, Boolean triggered, Boolean consumeItem, List<Item> itemsToSpawn, List<LocationRule> rulesToSpawn){
        f_name = name;
        f_description = description;
        f_activationType = activationType;
        f_activationItem = activationItem;
        f_triggered = triggered;
        f_consumeItem = consumeItem;
        f_itemsToSpawn = itemsToSpawn;
        f_rulesToSpawn = rulesToSpawn;
    }



    public String trigger(World world, Player player){
        if (player.getInventory().getItems().contains(f_activationItem)){
            f_triggered = true;
            spawnItems(world);
            spawnRules(world);
            return "Event " + f_name + " triggered!";
        }
        return "Event" + f_name + " failed to trigger because player did not have " + f_activationItem.getName() + " in inventory.";
    }

    private void spawnItems(World world){
        for(Item i : f_itemsToSpawn){
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
        for (LocationRule r : f_rulesToSpawn){
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
}