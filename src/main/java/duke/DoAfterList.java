package duke;

import java.util.ArrayList;
import java.util.Collection;

public class DoAfterList {
    private static ArrayList<Integer> list = new ArrayList<>();

    /**
     * Returns the integer value of the DoAfter List at index.
     * @param index the index of the integer value we want to retrieve.
     * @return the integer value of the DoAfter List at index.
     */
    public static int get(int index) {
        return list.get(index);
    }

    /**
     * Adds an element to the DoAfter list.
     * @param number The integer to be added to the DoAfter List.
     */
    public static void add(int number) {
        list.add(number);
    }

    /**
     * Removes an integer from the list.
     * @param number The integer in the list that is to be deleted from the list.
     */
    public static void remove(int number) {
        list.remove(Integer.valueOf(number));
    }

    /**
     * Removes from the DoAfter list all of its elements contained in the specified collection.
     * @param c The collection of integers.
     */
    public static void removeAll(Collection<Integer> c) {
        list.removeAll(c);
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index The specified position where the element will be replaced.
     * @param value The value that will replace the element.
     */
    public static void set(int index, int value) {
        list.set(index, value);
    }

    /**
     * Returns true if the List contains the specified element. Otherwise, returns false.
     * @param number The integer, the specified element to be checked.
     * @return A boolean depending on whether the specified element exist in the list.
     */
    public static boolean contains(int number) {
        if (list.contains(number)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the current size of the DoAfter list.
     * @return The current size of the task list.
     */
    public static int getSize() {
        return list.size();
    }
}
