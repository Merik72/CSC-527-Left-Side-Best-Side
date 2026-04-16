package edu.southalabama.csc527.smallworld.controller;


import java.io.File;

import edu.southalabama.csc527.smallworld.model.*;
import edu.southalabama.csc527.smallworld.persistence.WorldPersistence;

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
			String blockedMessage = getBlockedMessage(player, newPlayerLocation);
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
	private String getBlockedMessage(Player player, Place destination) {
		Inventory playerInventory = player.getInventory();

		for (LocationRule rule : f_world.getLocationRules().getObjects()) {

			if (!rule.getNeededToEnter()) continue;
			if (!rule.getPlaceName().equalsIgnoreCase(destination.getName())) continue;

			String requiredItemName = rule.getItemNeededName();

			if (requiredItemName != null &&
					playerInventory.getItem(requiredItemName) == null) {

				String msg = rule.getBlockedMsg();

				return (msg != null && !msg.isEmpty())
						? msg
						: "You need the " + requiredItemName +
						" to enter " + destination.getName() + ".";
			}
		}

		return null;
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
	 * @param item
	 * the item to take.
	 */
	public void take(Item item) {
		Player player = f_world.getPlayer();
		Place currentLocation = player.getLocation();

		item.setLocation("Player");
		currentLocation.getInventory().removeItem(item);
		player.getInventory().addItem(item);
		player.addPoints(item.getTakePoints());
	}

	/**
	 * Removes all items from the player's location and places them in the
	 * player's inventory.
	 */

	/**
	 * Drops the specified item from the player's inventory.
	 *
	 * @param item
	 * the item to drop.
	 */
	public void drop(Item item) {
		Player player = f_world.getPlayer();
		Place currentLocation = player.getLocation();

		item.setLocation(currentLocation.getName());
		currentLocation.getInventory().addItem(item);
		player.getInventory().removeItem(item);
		player.addPoints(item.getDropPoints());
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
}
