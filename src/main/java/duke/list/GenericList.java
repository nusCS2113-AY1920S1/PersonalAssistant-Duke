package duke.list;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Represents an abstract class, namely a Generic List, used to store different types of entries
 * @author Sara Djambazovska
 */
public abstract class GenericList<T> {
    protected List<T> genList;

    /**
     * Constructor of the class {@link GenericList}
     * Creates a new {@link GenericList} from the {@link List} passed as a parameter
     * @param genList the {@link List} linked to the generic list
     */
    public GenericList(List<T> genList) {
        this.genList = genList;
    }
    
    /**
     * Empty Constructor of the class {@link GenericList}
     * Creates a new {@link GenericList} with an empty list of entries
     */
    public GenericList() {
        this.genList = new ArrayList<>();
    }

    /**
     * Adds an entry to the {@link GenericList}.
     * @param entry {@link T} to be added to the list
     */
    public void addEntry(T entry) throws DukeException {
        genList.add(entry);
    }

    /**
     * Returns the number of entries in the {@link GenericList} so far.
     * @return an integer indicating the size of the list of entries stored
     */
    public int size() {
        return genList.size();
    }
    
    /**
     * Returns the entry at the position indicated by the index.
     * @param index the position of the entry requested in the {@link GenericList}
     * @return the requested entry
     */
    public T getEntry(int index) {
        return genList.get(index);
    }


    /**
     * Returns a list of all the entries in the {@link GenericList}
     *
     * @return {@link ArrayList} of generic entries
     */
    public List<T> getAllEntries() {
        return genList;
    }

    /**
     * Returns the removed entry from position index in the {@link GenericList}
     *
     * @param index the position of the entry to be removed from the {@link GenericList}
     * @return T the entry that was removed
     */
    public T removeEntry(int index) {
        return genList.remove(index);
    }
    /**
     * Returns true if the entry was successfully removed from the {@link GenericList}
     *
     * @param entry the entry to be removed from the {@link GenericList}
     * @return true if removal was successful
     */
    public boolean removeEntry(T entry) throws DukeException {
        return genList.remove(entry);
    }
    /**
     * Returns true if the {@link GenericList} has no entries, is empty
     *
     * @return true if there are no entries stored in the generic list
     */
    public boolean isEmpty() {
        return genList.isEmpty();
    }
    /**
     * Returns the entry of the {@link GenericList} that equals the entry passed as a parameter
     *
     * @return the entry from the generic list, that is still linked to the list, so it can be modified
     */
    public T getEntry(T entry) throws DukeException {
        for (T e : genList)
            if (e.equals(entry))
                return e;
        return null;
    }

    /**
     * Removes all entries from the {@link GenericList}, namely calling isEmpty() on the list after will result to true
     */
    public void clearList() {
        genList.clear();
    }
}
