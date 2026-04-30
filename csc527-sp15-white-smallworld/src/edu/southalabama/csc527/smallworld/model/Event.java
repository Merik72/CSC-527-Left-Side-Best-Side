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
    
    private Boolean f_retriggerable = false;

    // holds items and rules in event for spawning when event triggered
    private List<Item> f_itemsToSpawn = new ArrayList<>();
    private List<LocationRule> f_rulesToUpdate= new ArrayList<>();
    // Placename : Description to update
    private HashMap<String, String> f_descriptionsToUpdate = new HashMap<>();
    private List<Event> f_eventsToUpdate = new ArrayList<>();

    public Event(String name, Item activationItem, String location, ItemAction activationType, Boolean triggered, Boolean consumeItem, String description){
        f_name = name;
        f_description = description;
        f_location = location;
        f_activationType = activationType;
        f_activationItem = activationItem;
        f_triggered = triggered;
        f_consumeItem = consumeItem;
        f_retriggerable = false;
    }
    
    public Event(String name, String description, ItemAction activationType, Item activationItem, Boolean triggered, Boolean consumeItem, List<Item> itemsToSpawn, List<LocationRule> rulesToSpawn){
        f_name = name;
        f_description = description;
        f_activationType = activationType;
        f_activationItem = activationItem;
        f_triggered = triggered;
        f_consumeItem = consumeItem;
        f_itemsToSpawn = itemsToSpawn;
        f_rulesToUpdate = rulesToSpawn;
        f_retriggerable = false;
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
    public Item getActivationItem() {
    	return f_activationItem;
    }
    public void setActivationItem(Item item) {
    	f_activationItem = item;
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
    public void addRuleToUpdate(LocationRule add) {
    	f_rulesToUpdate.add(add);
    }
    public void addEventToUpdate(Event event) {
    	f_eventsToUpdate.add(event);
    }
    public List<Item> getItemsToSpawn(){
    	return f_itemsToSpawn;
    }
    public HashMap<String, String> getDescriptionsToUpdate() {
    	return f_descriptionsToUpdate;
    }
    public List<LocationRule> getRulesToUpdate() {
    	return f_rulesToUpdate;
    }
    public List<Event> getEventsToUpdate() {
    	return f_eventsToUpdate;
    }

    public String trigger(World world){
        if(!f_retriggerable) f_triggered = true;
    	if(f_consumeItem) {
    		world.consumeItem(f_activationItem);
        	// f_activationItem.setLocation("Nowhere");
        }
    	
    	spawnItems(world);
        updateEvents(world);
        updateDescriptions(world);
            // updateRules(world); // setting self to triggered is sufficient
        // Events must be phrased as a verb phrase conjugated in the second person perspective
        return "You " + f_name + ".";
        //}
        //return "Event" + f_name + " failed to trigger because player did not have " + f_activationItem.getName() + " in inventory.";
        // updateRules(world); // setting self to triggered is sufficient
    }

    public Boolean contditionsMet(ItemAction action, Item item, Place location){
        return f_activationType.equals(action)
                && f_activationItem.equals(item)
                && f_location.equals(location.getName())
                && !f_triggered;
    }

    private void spawnItems(World world){
    	String message = "";
        for(Item i : new ArrayList<>(f_itemsToSpawn)){
            world.addItem(i);
            if(i.getLocation().equalsIgnoreCase("player")) {
            	world.addToMessage("You got " + i.getArticle() + " " + i.getName());
            }
            else if(!(world.getPlayer().getLocation().getName().equals(i.getLocation())))
            		message = message.concat(world.getPlace(i.getLocation()).getArticle() + " " + i.getLocation());
        }
        if(!(message.equals("") || f_retriggerable || (f_itemsToSpawn.isEmpty()))) {
        	world.addToMessage("You feel like something appeared in "+message);
        }
    }
    private void updateEvents(World world) {
    	for(Event e : new ArrayList<>(f_eventsToUpdate)) {
    		world.createEvent(e);
    	}
    }
    private void updateDescriptions(World world){
    	String message = "";
        for(var my_p : new HashMap<>(f_descriptionsToUpdate).entrySet()){
            Place p = world.getPlace(my_p.getKey());
            if(p != null) p.setDescription(my_p.getValue());
            if(!world.getPlayer().getLocation().getName().equals(p.getName()))
            	message = message.concat("\n" + p.getArticle() + " " + p.getName());
        }
	    if(!(message.equals("") || f_retriggerable || (f_descriptionsToUpdate.size() == 0))) {
        	world.addToMessage("You feel like something changed in " + message);
	    }
    }
    
    public void setRetriggerable(boolean val) {
    	f_retriggerable = val;
    }
    public boolean getRetriggerable() {
    	return f_retriggerable;
    }

   /* private void updateRules(World world){
        for (LocationRule r : f_rulesToUpdate){
            world.updateLocationRule(r);
        }
    }*/
}