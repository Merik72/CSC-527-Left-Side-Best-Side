package edu.southalabama.csc527.smallworld.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Event {
    private String f_name;
    private String f_description;
    private String f_location;
    private ItemAction f_activationType;
    private Item f_activationItem;
    private Boolean f_triggered;
    private Boolean f_consumeItem;

    // holds items and rules in event for spawning when event triggered
    private List<Item> f_itemsToSpawn = new ArrayList<Item>();
    private List<BlockedLocation> f_rulesToUpdate= new ArrayList<BlockedLocation>();;
    
    // Placename : Description to update
    private HashMap<String, String> f_descriptionsToUpdate = new HashMap<String,String>();

    public Event(String name, Item activationItem, String location, ItemAction activationType, Boolean triggered, Boolean consumeItem, String description){
        f_name = name;
        f_description = description;
        f_location = location;
        f_activationType = activationType;
        f_activationItem = activationItem;
        f_triggered = triggered;
        f_consumeItem = consumeItem;
    }
    
    public Event(String name, String description, ItemAction activationType, Item activationItem, Boolean triggered, Boolean consumeItem, List<Item> itemsToSpawn, List<BlockedLocation> rulesToSpawn){
        f_name = name;
        f_description = description;
        f_activationType = activationType;
        f_activationItem = activationItem;
        f_triggered = triggered;
        f_consumeItem = consumeItem;
        f_itemsToSpawn = itemsToSpawn;
        f_rulesToUpdate = rulesToSpawn;
    }
    public String getName() {
    	return f_name;
    }

    public String getDescription() {
    	return f_description;
    }
    public String getLocation() {
    	return f_location;
    }
    public String getActivationItem() {
    	return f_activationItem.getName();
    }
    public ItemAction getActivationType() {
    	return f_activationType;
    }
    public boolean getTriggered() {
    	return f_triggered;
    }
    public boolean getConsumeItem() {
    	return f_consumeItem;
    }
    public void addDescription(String name, String desc) {
    	f_descriptionsToUpdate.put(name, desc);
    }
    public void addItemToSpawn(Item item) {
    	f_itemsToSpawn.add(item);
    }
    public void addRuleToUpdate(BlockedLocation add) {
    	f_rulesToUpdate.add(add);
    }

    public String trigger(World world, Player player){
        if (player.getInventory().getItems().contains(f_activationItem)){
            f_triggered = true;
            spawnItems(world);
            // updateRules(world); // setting self to triggered is sufficient
            return "Event " + f_name + " triggered!";
        }
        return "Event" + f_name + " failed to trigger because player did not have " + f_activationItem.getName() + " in inventory.";
    }

    private void spawnItems(World world){
        for(Item i : f_itemsToSpawn){
            world.createItem(i);
        }
    }

    /*
     * 
    private void updateRules(World world){
        for (BlockedLocation r : f_rulesToUpdate){
            world.updateBlockedLocation(r);
        }
    }
     */
}