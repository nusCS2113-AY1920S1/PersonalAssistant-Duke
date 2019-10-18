package compal.storage;

import compal.model.tasks.Task;

import java.util.ArrayList;

/**
 * API of the Storage component.
 */
public interface Storage<T> {
    String saveFilePath = null;

    T loadData();

    void saveData(T arrlist);


}
