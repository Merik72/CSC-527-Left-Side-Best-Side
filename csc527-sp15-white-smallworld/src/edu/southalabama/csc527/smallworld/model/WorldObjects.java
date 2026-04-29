package edu.southalabama.csc527.smallworld.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WorldObjects<T> {
    private HashMap<String, T> f_keyToValue = new HashMap<>();

    /**
     * Returns <code>true</code> if the specified name is used for a
     * {@link Place} within this world, <code>false</code> otherwise. Names
     * are non-case sensitive, so "NAME" is considered the same name as "nAmE".
     * In addition, the namespace of the world is shared across all
     * {@link Place} instances.
     *
     * @param name
     *            the non-null non-case sensitive name to check.
     * @return <code>true</code> if the specified name is used for a
     *         {@link Place} within this world, <code>false</code> otherwise.
     */
    public boolean isNameUsed(String name) {
        assert (name != null);
        return f_keyToValue.containsKey(name.toUpperCase());
    }

    /**
     * Gets the appropriate {@link Place} instance with the specified name.
     *
     * @param name
     *            the non-null non-case sensitive name of the desired
     *            {@link Place} instance.
     * @return the appropriate {@link Place} instance, or <code>null</code> if
     *         the specified name does not exist.
     */
    public T getObjectByName(String name) {
        assert (name != null);
        return f_keyToValue.get(name.toUpperCase());
    }

    /**
     * Returns a copy of all the Places in this world.
     *
     * @return a copy of the set of all Places in this world.
     */
    public Set<T> getObjects() {
        return new HashSet<>(f_keyToValue.values());
    }

    public void addObject(String name, T object) {
        f_keyToValue.put(name.toUpperCase(), object);
    }

    public void removeObject(String name) {
        f_keyToValue.remove(name);
    }
}
