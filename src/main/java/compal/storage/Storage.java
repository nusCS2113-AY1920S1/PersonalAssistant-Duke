package compal.storage;

import compal.tasks.Task;

import java.util.ArrayList;

/**
 * API of the Storage component.
 */
public interface Storage {


    ArrayList<Task> loadCompal();

    String getUserName();

    void saveCompal(ArrayList<Task> tasks);

    void saveString(String toSave, String filePath);

    void storeUserName(String name);


}
