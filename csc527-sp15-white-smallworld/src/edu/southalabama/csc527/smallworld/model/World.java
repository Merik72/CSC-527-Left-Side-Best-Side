package edu.southalabama.csc527.smallworld.model;

import java.util.*;

/**
 * The simulated world in which simulated players lead their short, simulated
 * lives. In the Model/View/Controller paradigm, this is the model. It is an
 * aggregate that contains everything that exists in the world. It also acts as
 * the subject in the Observer pattern, so that observers (e.g., views) can stay
 * current when the world changes in some interesting way.
 * <p>
 * A newly constructed world contains a {@link Player} and a nowhere
 * {@link Place}. The player is located at the nowhere place. The world, via
 * method calls, may then be mutated into something more interesting. This
 * approach ensures that even a newly constructed world is trivially playable.
 * 
 */
public final class World {
	private final WorldObjects<Place> f_places = new WorldObjects<>();
	private final WorldObjects<Item> f_items = new WorldObjects<>();
	private final WorldObjects<List<BlockedLocation>> f_entryRestrictions = new WorldObjects<>();
	private final WorldObjects<Event> f_events = new WorldObjects<>();
	
	/**
	 * A place that always exists in every world. It represents a thing being
	 * nowhere.
	 */
	private final Place f_nowhere = createPlace("Very Remote Place", "a",
			"You are in a very remote place.");

	/**
	 * Gets the {@link Place} representing nowhere. This place always exists in
	 * every world.
	 * 
	 * @return the place representing nowhere.
	 */
	public Place getNowherePlace() {
		return f_nowhere;
	}

	/**
	 * A {@link Player} that is controlled by, and represents, the user of the
	 * game.
	 */
	private final Player f_player = new Player(this);

	/**
	 * Constructs a new instance of the world class containing a single player
	 * and the nowhere place. The player is located at the nowhere place.
	 * 
	 * @see #getNowherePlace()
	 */
	public World() {
		clearMessage();
	}

	/**
	 * Gets a reference to the sole player interacting with this world.
	 * 
	 * @return the sole player within this world.
	 */
	public Player getPlayer() {
		return f_player;
	}

	/**
	 * Gets the appropriate {@link Place} instance with the specified name.
	 * 
	 * @param name
	 *            the non-null non-case sensitive name of the desired
	 *            {@link Place} instance.
	 * @return the appropriate {@link Place} instance, or <code>null</code> if
	 *         the specified name does not exist or is not of the {@link Place}
	 *         type.
	 */
	public Event getEvent(String name) {
		return f_events.getObjectByName(name);
	}
	// In a big data setting, you'd probably want to like
	// Hook up an observer to the observe the f_Events list?
	// Be able to sync
	/*public void triggerEvent(ItemAction activationType, Item activationItem, String location) {
		for(var event : f_events.getObjects()) {
			if(event.getActivationItem().equals(activationItem.getName()) && activationType.equals(event.getActivationType()) && location.equals(event.getLocation())) {
				event.trigger(this, f_player);
				return;
			}
		}
	}*/
	public WorldObjects<Event> getEvents() {
		return f_events;
	}
	public Place getPlace(String name) {
		assert (name != null);
        return f_places.getObjectByName(name);
	}

	public WorldObjects<Place> getPlaces() {
		return f_places;
	}
	
	public Item getItem(String name) {
		assert (name != null);
        return f_items.getObjectByName(name);
	}

	public WorldObjects<Item> getItems() {
		return f_items;
	}

	public List<BlockedLocation> getLocationRule(String placeName) {
		assert (placeName != null);
        return f_entryRestrictions.getObjectByName(placeName);
	}

	public WorldObjects<List<BlockedLocation>> getLocationRules() {
		return f_entryRestrictions;
	}

	/**
	 * Constructs a new place within this world.
	 * 
	 * @param name
	 *            a non-null unique name for the instance. The uniqueness of the
	 *            name can't be dependent upon case, e.g., "Hall" is considered
	 *            the same as "hall".
	 * @param article
	 *            the appropriate non-null indefinite article with which to
	 *            prefix the name so as to form a proper short description,
	 *            e.g., "the" or "a".
	 * @param description
	 *            a long, possibly mult-line, non-null description of this
	 *            thing.
	 * @param arrivalWinsGame
	 * 			  a value which is Y if the player wins by navigating here
	 * @return the new {@link Place} instance.
	 * @throws IllegalStateException
	 *             if the specified name already exists within this world.
	 */
	public Place createPlace(String name, String article, String description, String arrivalWinsGame) {
		assert (name != null);
		assert (article != null);
		assert (description != null);
		if (f_places.isNameUsed(name)) {
			throw new IllegalStateException(
					"Construction of a new place named \""
							+ name
							+ "\" failed because the specified name already exists");
		}
		Place newPlace = new Place(this, name, article, description);
		if(arrivalWinsGame != null) {
			newPlace.setArrivalWinsGame(arrivalWinsGame.equals("Y"));
		}
		f_places.addObject(name.toUpperCase(), newPlace);
		return newPlace;
	}
	/**
	 * Constructs a new place within this world.
	 * 
	 * @param name
	 *            a non-null unique name for the instance. The uniqueness of the
	 *            name can't be dependent upon case, e.g., "Hall" is considered
	 *            the same as "hall".
	 * @param article
	 *            the appropriate non-null indefinite article with which to
	 *            prefix the name so as to form a proper short description,
	 *            e.g., "the" or "a".
	 * @param description
	 *            a long, possibly mult-line, non-null description of this
	 *            thing.
	 * @return the new {@link Place} instance.
	 * @throws IllegalStateException
	 *             if the specified name already exists within this world.
	 */
	public Place createPlace(String name, String article, String description) {
		assert (name != null);
		assert (article != null);
		assert (description != null);
		if (f_places.isNameUsed(name)) {
			throw new IllegalStateException(
					"Construction of a new place named \""
							+ name
							+ "\" failed because the specified name already exists");
		}
		Place newPlace = new Place(this, name, article, description);
		f_places.addObject(name.toUpperCase(), newPlace);
		return newPlace;
	}
	
	
	
	public Event createEvent(String name, String activationItem, String location, String activationType, String triggered, String consumeItem, String description) {
		Item newItem = this.getItem(activationItem);
		ItemAction type;
		switch (activationType.toUpperCase()) {
			case "TAKE":
				type = ItemAction.TAKE;
				break;
			case "DROP":
				type = ItemAction.DROP;
				break;
			case "USE":
				type = ItemAction.USE;
				break;
			default:
				throw new IllegalStateException("Invalid activation type: " + activationType);
		}

		Event newEvent = new Event(name, newItem, location, type, (triggered.equals("Y") ? true : false), (consumeItem.equals("Y") ? true : false), description);
		f_events.addObject(name.toUpperCase(), newEvent);
		return newEvent;
	}
	
	public Item createItem(String name, String article, String location, String takePoints, String dropPoints) {
		assert (name != null);
		assert (article!= null);
		assert (location != null);
		assert (takePoints != null);
		assert (dropPoints != null);
		// Is it okay for there to be duplicate names?
		if (f_items.isNameUsed(name)) {
			throw new IllegalStateException(
					"Construction of a new item named \""
							+ name
							+ "\" failed because the specified name already exists");
		}
		// Does an item need a world?
		Item newItem = new Item(name, article, location, Integer.valueOf(takePoints),Integer.valueOf(dropPoints));
		f_items.addObject(name.toUpperCase(), newItem);
		return newItem;
	}
	
	public Item createItem(Item item) {
		// Does an item need a world?
		f_items.addObject(item.getName().toUpperCase(), item);
		return item;
	}

	public BlockedLocation createLocationRule(String placeName, String itemName, String neededToEnter, String blockedMsg, String takePoints, String dropPoints) {
		assert (placeName != null);
		assert (itemName != null);
		
		// Location Rules do not need points.
		// assert (takePoints != null);
		// assert (dropPoints != null);
		if (f_items.isNameUsed(placeName)) {
			throw new IllegalStateException(
					"Construction of a new entryRestriction named \""
							+ placeName
							+ "\" failed because the specified name already exists");
		}

		boolean neededToEnterBool = false;
		if(neededToEnter != null) {
			neededToEnterBool = neededToEnter.equals("Y");
		}

		BlockedLocation newRestriction = new LocationRule(placeName, itemName, neededToEnterBool, blockedMsg, parseInteger(takePoints), parseInteger(dropPoints));
		if (f_entryRestrictions.isNameUsed(placeName)) {
			f_entryRestrictions.getObjectByName(placeName).add(newRestriction);
		} else {
			List<BlockedLocation> list = new ArrayList<>();
			list.add(newRestriction);
			f_entryRestrictions.addObject(newRestriction.getPlaceName(), list);
		}
		return newRestriction;
	}
	
	public BlockedLocation createLocationRule(BlockedLocation newRestriction) {
		String placeName = newRestriction.getPlaceName();
		if (f_entryRestrictions.isNameUsed(placeName)) {
			f_entryRestrictions.getObjectByName(placeName).add(newRestriction);
		} else {
			List<BlockedLocation> list = new ArrayList<>();
			list.add(newRestriction);
			f_entryRestrictions.addObject(newRestriction.getPlaceName(), list);
		}
		return newRestriction;
	}

	public static Integer parseInteger(String value) {
		if (value == null || value.isEmpty()) return 0;

		try {
			return Integer.valueOf(value.trim());
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Invalid integer value: " + value);
		}
	}


	/**
	 * A non-null mutable string message.
	 */
	private StringBuilder f_message;

	/**
	 * A String holding the operating system-specific line separator obtained
	 * from the system properties.
	 */
	public static String LINESEP = System.getProperty("line.separator");

	/**
	 * Returns the current message associated with this world.
	 * 
	 * @return The world's current message.
	 */
	public String getMessage() {
		return f_message.toString();
	}

	/**
	 * Sets the current message associated with this world to the specified
	 * message. Any previous contents are lost.
	 * 
	 * @param message
	 *            a message.
	 */
	public void setMessage(String message) {
		clearMessage();
		addToMessage(message);
	}

	/**
	 * Appends a message to the world's current message.
	 * 
	 * @param message
	 *            The new message to add to the world's current message.
	 */
	public void addToMessage(String message) {
		if (message == null)
			return;
		f_message.append(message + LINESEP);
	}

	/**
	 * Appends a blank line to the world's current message.
	 */
	public void addToMessage() {
		f_message.append(LINESEP);
	}

	private void clearMessage() {
		f_message = new StringBuilder();
	}

	/**
	 * The set of observers for this world. Notified when this world has changed
	 * in some interesting way.
	 * 
	 * @see #addObserver(IWorldObserver)
	 * @see #removeObserver(IWorldObserver)
	 * @see #getObservers()
	 * @see #notifyObservers()
	 */
	private final Set<IWorldObserver> f_observers = new HashSet<IWorldObserver>();

	/**
	 * Gets a copy of the set of all observers of this world.
	 * 
	 * @return a copy of the set of all observers of this world.
	 */
	public Set<IWorldObserver> getObservers() {
		return new HashSet<IWorldObserver>(f_observers); // defensive copy
	}

	/**
	 * Adds an observer to be notified when the world has changed in some
	 * interesting way.
	 * 
	 * @param observer
	 *            the object to notify of changes to this world.
	 */
	public void addObserver(IWorldObserver observer) {
		if (observer == null)
			return;
		f_observers.add(observer);
	}

	/**
	 * Removes an observer from this world. Has no effect if the specified
	 * observer was not previously added as an observer.
	 * 
	 * @param observer
	 *            the object to stop notifying of changes to this world.
	 */
	public void removeObserver(IWorldObserver observer) {
		if (observer == null)
			return;
		f_observers.remove(observer);
	}

	/**
	 * Directs that the player's current turn is complete and that an update
	 * notification should be sent to any observing views. This method erases
	 * the world's current message after view notification is completed.
	 */
	public void turnOver() {
		notifyObservers();
		clearMessage();
	}

	/**
	 * Notifies all observers that the world has changed in some interesting
	 * way.
	 */
	private void notifyObservers() {
		for (IWorldObserver observer : f_observers) {
			observer.update(this);
		}
	}

	/**
	 * Flags if the game is over or not. Its value is <code>true</code> if the
	 * game is over, <code>false</code> otherwise.
	 * 
	 * @see #isGameOver()
	 * @see #setGameOver()
	 */
	private boolean f_gameOver = false;

	/**
	 * Reports if the game is over or not.
	 * 
	 * @return <code>true</code> if the game is over, <code>false</code>
	 *         otherwise.
	 */
	public boolean isGameOver() {
		return f_gameOver;
	}

	/**
	 * Notifies this world that the game is over. Will cause all subsequent
	 * calls to {@link #isGameOver()} to return <code>true</code>.
	 */
	public void setGameOver() {
		f_gameOver = true;
	}
}
