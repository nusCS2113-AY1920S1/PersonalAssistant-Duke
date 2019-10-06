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
     * Attempts to write object list into the save file on the local computer.
     *
     * @param objectList The list of object to write.
     */
    public abstract void writeListToFile(ArrayList<T> objectList);


}
