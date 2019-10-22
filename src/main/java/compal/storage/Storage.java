package compal.storage;

//@@author jaedonkey
/**
 * API of the Storage component.
 */
public interface Storage<T> {
    String saveFilePath = null;

    T loadData();

    void saveData(T arrlist);


}
