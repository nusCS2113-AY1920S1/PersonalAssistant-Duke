package duke.lists;

import java.util.ArrayList;
import java.util.List;

public abstract class SpinBoxList<T> {
    protected List<T> list;

    /**
     * Constructor.
     */
    public SpinBoxList() {
        list = new ArrayList<T>();
    }

    /**
     * Constructor if already have list.
     * @param list list to be made into SpinBoxList.
     */
    public SpinBoxList(List<T> list) {
        this.list = list;
    }

    /**
     * Add element into list.
     * @param element to be added.
     * @return added element.
     */
    public T add(T element) {
        list.add(element);
        return element;
    }

    /**
     * Remove element at index from list.
     * @param index index of element.
     * @return element removed.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public T remove(int index) throws IndexOutOfBoundsException {
        return list.remove(index);
    }

    /**
     * Return SpinBoxList as list.
     * @return list.
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Return element at index.
     * @param index index of element.
     * @return element at index.
     */
    public T get(int index) {
        return list.get(index);
    }

    /**
     * Mark the element.
     * @param index index of element to be marked.
     * @return element marked.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public abstract T mark(int index) throws IndexOutOfBoundsException;

    /**
     * Replace element at index with element.
     * @param index index of element to be replaced.
     * @param element new element to be inserted.
     * @return old element.
     */
    public T replace(int index, T element) {
        list.set(index, element);
        return element;
    }

    /**
     * Sort the list.
     */
    public abstract void sort();
}
