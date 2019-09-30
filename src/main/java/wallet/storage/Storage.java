package wallet.storage;

import java.util.ArrayList;

public abstract class Storage<T> {

    /**
     * Attempts to load the save file on the local computer and populate the list with its data.
     *
     * @return The list loaded from save file.
     */
    public abstract ArrayList<T> loadFile();

    /**
     * Attempts to write newly added object into the save file on the local computer.
     *
     * @param object The new object that is added.
     */
    public abstract void writeToFile(T object);

    /**
     * Updates the save file on the local computer with the edited values of the object.
     *
     * @param object  The object to be modified.
     * @param index The index of the object in the list.
     */
    public abstract void updateToFile(T object, int index);

    /**
     * Updates the save file on the local computer when an object is deleted.
     *
     * @param objectList The list to update.
     * @param index The index of the object in the list to be deleted.
     */
    public abstract void removeFromFile(ArrayList<T> objectList, int index);
}
