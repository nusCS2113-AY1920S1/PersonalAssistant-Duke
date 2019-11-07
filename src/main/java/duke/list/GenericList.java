package duke.list;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class GenericList<T> {
    protected List<T> genList;

    /**
     * The constructor method(1) for TaskList.
     */
    public GenericList(List<T> genList) {
        this.genList = genList;
    }

    /**
     * The constructor method(2) for TaskList.
     */
    public GenericList() {
        this.genList = new ArrayList<>();
    }

    /**
     * Adds a task to the {@link GenericList}.
     *
     *
     * @param entry {@link T} to be added to the list
     */
    public void addEntry(T entry) {
        genList.add(entry);
    }

    /**
     * Returns the number of {@link Ingredient}s in the {@link IngredientsList} so far.
     *
     * @return an integer indicating the size of the list of {@link Ingredient}s stored
     */
    public int size() {
        return genList.size();
    }


    /**
     * Returns the {@link Ingredient} at the position indicated by the taskNb.
     *
     * @param taskNb the position of the {@link Ingredient} requested in the {@link IngredientsList}
     * @return the requested {@link Ingredient}
     */
    public T getEntry(int taskNb) {
        return genList.get(taskNb);
    }


    /**
     * Returns a list of all the {@link Ingredient}s in the {@link IngredientsList}.
     *
     * @return {@link ArrayList} of {@link Ingredient}
     */
    public List<T> getAllEntries() {
        return genList;
    }

    /**
     * Returns the removed {@link Ingredient} from position taskNb in the {@link IngredientsList}.
     *
     * @param taskNb the position of the {@link Ingredient} to be removed from the {@link IngredientsList}
     * @return Task the task that was removed
     */
    public T removeEntry(int taskNb) {
        return genList.remove(taskNb);
    }

    public boolean removeEntry(T entry) throws DukeException {
        return genList.remove(entry);
    }

    public boolean isEmpty() {
        return genList.isEmpty();
    }

    public T getEntry(T entry) {
        for (T e : genList)
            if (e.equals(entry))
                return e;
        return null;
    }


    public void sort(Comparator comparator) {
        genList.sort(comparator);
    }

    public void clearList() {
        genList.clear();
    }
}
