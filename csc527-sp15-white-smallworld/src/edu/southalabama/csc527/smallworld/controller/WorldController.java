package edu.southalabama.csc527.smallworld.controller;


import edu.southalabama.csc527.smallworld.model.*;
import edu.southalabama.csc527.smallworld.persistence.WorldPersistence;
import java.io.File;
import java.util.List;

/**
 * This class is responsible for executing a user's commands. In the
 * Model/View/Controller paradigm, a {@link WorldController} instance is the
 * controller for one {@link World} instance which is the model. It receives
 * commands from a user interface, makes the corresponding changes to its
 * associated {@link World}, and triggers notification to the {@link World}'s
 * observers.
 * 
 * @see World
 */
public final class WorldController {
	/**
	 * The world associated with this controller. It must be non-null, but may
	 * be changed via the {@link #setWorld(World)} method.
	 */
	private World f_world;

	/**
	 * Creates a new instance of <code>WorldController</code> for the default
	 * world.
	 */
	public WorldController() {
		this(WorldPersistence.DEFAULT_WORLD);
	}

	/**
	 * Creates a new instance of <code>WorldController</code> for the
	 * specified world file, which it loads.
	 * 
	 * @param fileName
	 *            the world file to load
	 */
	public WorldController(String fileName) {
		assert (fileName != null);
		try {
			f_world = WorldPersistence.loadWorld(fileName);
		} catch (IllegalStateException e) {
			// World file failed to load, so create a trivial but valid world
			f_world = new World();
			reportException(e);
		}
	}

	/**
	 * Gets the world (i.e., model) associated with this controller.
	 * 
	 * @return the world associated with this controller.
	 */
	public World getWorld() {
		return f_world;
	}

	/**
	 * Loads a previously saved {@link World} from a file.
	 * 
	 * @param fileName
	 *            The name of the file to load
	 */
	public void loadWorld(String fileName) {
		assert (fileName != null);
		File file = new File(fileName);
		World newWorld;
		try {
			newWorld = WorldPersistence.loadWorld(file);
			setWorld(newWorld);
			f_world.addToMessage("File \"" + file.getAbsolutePath()
					+ "\" loaded.");
			f_world.addToMessage();
		} catch (IllegalStateException e) {
			reportException(e);
			f_world.addToMessage("File \"" + file.getAbsolutePath()
					+ "\" failed to load: ");
			f_world.addToMessage("Keeping current world");
		}
		f_world.turnOver();
	}

	/**
	 * Notifies the world that the user wants to quit the game.
	 */
	public void quit() {
		f_world.addToMessage("Bye!");
		f_world.setGameOver();
		f_world.turnOver();
	}

	/**
	 * Saves the current state of the {@link World} to a file. This world can be
	 * loaded and game play resumed using {@link #loadWorld(String)}.
	 * 
	 * @param fileName
	 *            The name of the file to create
	 */
	public void saveWorld(String fileName) {
		assert (fileName != null);
		File file = new File(fileName);
		try {
			WorldPersistence.saveWorld(f_world, file);
			f_world.addToMessage("Save file \"" + file.getAbsolutePath()
					+ "\" created.");
		} catch (IllegalStateException e) {
			reportException(e);
			f_world.addToMessage("Save to file \"" + file.getAbsolutePath()
					+ "\" FAILED.");
			f_world.addToMessage("You will not be able to load this world");
		}
		f_world.turnOver();
	}

	/**
	 * Moves the player in the direction indicated.
	 * 
	 * @param direction
	 *            The direction the user wants the player to travel.
	 */
	public void travel(Direction direction) {
		assert direction != null;
		Player player = f_world.getPlayer();
		Place playerLocation = player.getLocation();
		if (playerLocation.isTravelAllowedToward(direction)) {
			Place newPlayerLocation = playerLocation
					.getTravelDestinationToward(direction);

			/*
			 * Check that the player holds every item whose subplace marks the
			 * destination as neededToEnter. If any required item is missing,
			 * block travel and show that item's blocked message.
			 */
			String blockedMessage = getBlockedAndBlockedMessage(player, newPlayerLocation);
			if (blockedMessage != null) {
				f_world.addToMessage(blockedMessage);
				f_world.turnOver();
				return;
			}

			if (newPlayerLocation.arrivalWinsGame()) {
				f_world.addToMessage("Game Finished!");
				f_world.setGameOver();
			}
			/*
			 * Move the player
			 */
			player.setLocation(newPlayerLocation);
			
		} else {
			/*
			 * Travel is not allowed from the player's location in the specified
			 * direction.
			 */
			f_world.addToMessage("Sorry, you can't move "
					+ direction.toString().toLowerCase() + " from here.");
			// addShortLocationDescription("You are at");
		}
		f_world.turnOver();
	}

	/**
	 * Checks whether the player is carrying all items required to enter the
	 * specified destination place.
	 *
	 * @param player
	 *            the player attempting to travel.
	 * @param destination
	 *            the place the player wishes to enter.
	 * @return the blocked message of the first missing required item, or
	 *         {@code null} if the player has all required items (or none are
	 *         required).
	 */
	private String getBlockedAndBlockedMessage(Player player, Place destination) {
		Inventory playerInventory = player.getInventory();

		String resultMessage = null;

		for (List<BlockedLocation> ruleList : f_world.getLocationRules().getObjects()) {
			for (BlockedLocation rule : ruleList) {
				if (!rule.getNeededToEnter()) continue;
				if (!rule.getPlaceName().equalsIgnoreCase(destination.getName())) continue;

				String unlockerName = rule.getUnlockerName();
				String msg;
				// There is an unlocker, it is an item, the player doesn't have it
				if (unlockerName != null && f_world.getItem(unlockerName) != null && playerInventory.getItem(unlockerName) == null) {
					
					msg = rule.getBlockedMsg();

					resultMessage = (msg != null && !msg.isEmpty())
							? msg
							: "You need the " + unlockerName +
							" to enter " + destination.getName() + ".";
				}
				else {	
					Event e = f_world.getEvent(unlockerName);
					// Unlocker is an event and the event isn't triggered
					if(unlockerName != null && e != null && e.getTriggered() == false) {
						
						msg = rule.getBlockedMsg();
						
						resultMessage = (msg != null && !msg.isEmpty())
								? msg
								: "You need to" + unlockerName + "before you can enter" + destination.getName() + ".";
					}
				}
			}
		}
		return resultMessage;
	}


	/**
	 * @param e
	 */
	private void reportException(Throwable e) {
		StringBuilder s = new StringBuilder();
		s.append(e.getMessage());
		if (e.getCause() != null)
			s.append(": " + e.getCause().getMessage());
		f_world.addToMessage(s.toString());
	}

	/**
	 * Sets the world (i.e., model) associated with this controller. The set of
	 * observers of the old world are setup to observer the new world.
	 * 
	 * @param world
	 *            a non-null game world.
	 */
	public void setWorld(World world) {
		assert (world != null);
		/*
		 * Transfer all observers of the old world to the new world.
		 */
		for (IWorldObserver o : f_world.getObservers()) {
			world.addObserver(o);
		}
		f_world = world;
	}

	/**
	 * Removes the specified item from the player's location and places
	 * it in the player's inventory.
	 *
	 * @param itemName
	 * the item name to take.
	 */
	private void take(String itemName) {
		Player player = f_world.getPlayer();
		Place currentLocation = player.getLocation();
		Inventory currentPlayerInv = player.getInventory();
		Inventory currentLocationInv = currentLocation.getInventory();
		Item item = currentLocationInv.getItem(itemName);

		if (item == null) {
			f_world.addToMessage("There is no item named \"" + itemName + "\" to pickup.");
			return;
		}

		currentLocationInv.removeItem(item);

		currentPlayerInv.addItem(item);
		item.setLocation("Player");
		f_world.addToMessage("\"" + itemName + "\" has been added to your inventory.");

		player.addPoints(item.getTakePoints());

		for(var locationRules : f_world.getLocationRules().getObjects()) {
			for(var locationRule : locationRules) {
				if(!locationRule.getClass().equals(LocationRule.class)) {
					continue;
				}
				LocationRule r;
				r = (LocationRule)locationRule;
				if(locationRule.getPlaceName().equals(currentLocation.getName())){
					player.addPoints(r.getTakePoints());
					r.setTakePoints(0);
				}
			}
		}
		item.setTakePoints(0);

		String response = eventTriggerCheck(ItemAction.TAKE, item);
		if (!(response == null)){
			f_world.addToMessage(response);
		}
	}

	public void takeOne(String itemName) {
		take(itemName);
		f_world.turnOver();
	}
	
	public void takeAll() {
		var currentPlaceInv = f_world.getPlayer().getLocation().getInventory();
		var items = currentPlaceInv.getItems();

		if (items.isEmpty()) {
			f_world.addToMessage("There are no items to pick up.");
			return;
		}

		for(Item item : items) {
			take(item.getName());
		}
		f_world.turnOver();
	}

	/**
	 * Removes all items from the player's location and places them in the
	 * player's inventory.
	 */

	/**
	 * Drops the specified item from the player's inventory.
	 *
	 * @param itemName
	 * the item name to drop.
	 */
	public void drop(String itemName) {
		Player player = f_world.getPlayer();
		Place currentLocation = player.getLocation();
		Inventory currentPlayerInv = player.getInventory();
		Inventory currentLocationInv = currentLocation.getInventory();
		Item item = currentPlayerInv.getItem(itemName);

		if (item == null) {
			f_world.addToMessage("There is no item named \"" + itemName + "\" in your inventory.");
			f_world.turnOver();
			return;
		}

		item.setLocation(currentLocation.getName());
		currentLocationInv.addItem(item);

		currentPlayerInv.removeItem(item);
		f_world.addToMessage("\"" + itemName + "\" has been dropped from your inventory.");

		player.addPoints(item.getDropPoints());
		for(var place : f_world.getLocationRules().getObjects()) {
			for(var locationRule : place) {
				if(!locationRule.getClass().equals(LocationRule.class)) {
					continue;
				}
				LocationRule r;
				r = (LocationRule)locationRule;
				if(locationRule.getPlaceName().equals(currentLocation.getName())){
					player.addPoints(r.getDropPoints());
					r.setDropPoints(0);
				}
			}
		}
		item.setDropPoints(0);

		String response = eventTriggerCheck(ItemAction.DROP, item);
		if (!(response == null)){
			f_world.addToMessage(response);
		}

		f_world.turnOver();
	}

	public void use(String itemName) {
		Item item = f_world.getPlayer().getInventory().getItem(itemName);

		if (item == null) {
			f_world.addToMessage("You are not holding an item named \"" + itemName + "\".");
			f_world.turnOver();
			return;
		}

		String response = eventTriggerCheck(ItemAction.USE, item);
		if (!(response == null)){
			f_world.addToMessage(response);
		} else {
			f_world.addToMessage("Nothing happened...");
		}
		f_world.turnOver();
	}

	/**
	 * Examines the items in the player's inventory.
	 */
	public void inventory() {
		Inventory currentInventory = f_world.getPlayer().getInventory();

		if (currentInventory.getItems().isEmpty()) {
			f_world.addToMessage("You are not carrying any items.");
		} else {
			f_world.addToMessage("You are carrying:\n" + currentInventory);
		}
		f_world.turnOver();
	}

	/**
	 * Returns the player's current point total and adds to the message.
	 */
	public void printPoints(){
		int currentPoints = f_world.getPlayer().getPoints();
		f_world.addToMessage("You have " + currentPoints + " points.");
		f_world.turnOver();
	}

	private String eventTriggerCheck(ItemAction action, Item item){
		var events = f_world.getEvents().getObjects();

		for (Event e : events){
			if (e.contditionsMet(action, item, f_world.getPlayer().getLocation())){
				e.trigger(f_world);
				return e.getDescription();
			}
		}

		return null;
	}
}
