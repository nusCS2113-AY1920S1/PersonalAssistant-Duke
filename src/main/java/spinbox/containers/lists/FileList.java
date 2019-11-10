package spinbox.containers.lists;

import spinbox.datapersistors.storage.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.entities.items.File;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileList extends SpinBoxList<File> {
    private static final Logger LOGGER = Logger.getLogger(SpinBoxList.class.getName());
    private static final String LOG_LOAD_DATA = "Load data from local storage.";
    private static final String LOG_SAVE_DATA = "Saved data into local storage.";
    private static final String LOG_SORT_LIST = "Sorted list.";
    private static final String LOG_VIEW_LIST = "View list.";
    private static final String LOG_CONTAINS_KEYWORD = "Found list of items that contain keyword.";
    private static final String FILE_LIST_FILE_NAME = "/files.txt";
    private static final String VIEW_FILE_LIST_HEADER = "Here are the files in your module:";
    private static final String FILES_CONTAIN = "Here are the files that contain ";

    /**
     * Constructor for FileList.
     * @param parentName The module code of the list.
     * @throws FileCreationException Error in creating the file to store data.
     */
    public FileList(String parentName) throws FileCreationException {
        super(parentName);
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        localStorage = new Storage(DIRECTORY_NAME + this.getParentCode() + FILE_LIST_FILE_NAME);
    }

    /**
     * Order the files according to name in alphabetical order.
     */
    static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File a, File b) {
            return a.getName().compareToIgnoreCase(b.getName());
        }
    }

    /**
     * Method that will sort the FileList according to alphabetical order.
     */
    public void sort() {
        LOGGER.entering(getClass().getName(), "sort");
        list.sort(new FileComparator());
        LOGGER.fine(LOG_SORT_LIST);
        LOGGER.exiting(getClass().getName(), "sort");
    }

    /**
     * Populates data into the file list from the list's localStorage.
     * @throws DataReadWriteException loadData fail due to I/O Error..
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly.
     */
    @Override
    public void loadData() throws DataReadWriteException, CorruptedDataException {
        LOGGER.entering(getClass().getName(), "loadData");
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            File file = new File();
            file.fromStoredString(datum);
            this.addFromStorage(file);
        }
        LOGGER.fine(LOG_LOAD_DATA);
        LOGGER.exiting(getClass().getName(), "loadData");
    }

    /**
     * Saves the current file list data into the list's localStorage.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    @Override
    public void saveData() throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "saveData");
        List<String> dataToSave = new ArrayList<>();
        for (File file: this.getList()) {
            dataToSave.add(file.storeString());
        }

        localStorage.writeData(dataToSave);
        LOGGER.fine(LOG_SAVE_DATA);
        LOGGER.exiting(getClass().getName(), "saveData");
    }

    /**
     * Gives the list of files in string format.
     * @return Returns list of files that have been converted to string format.
     */
    @Override
    public List<String> viewList() {
        LOGGER.entering(getClass().getName(), "viewList");
        LOGGER.fine(LOG_VIEW_LIST);
        LOGGER.exiting(getClass().getName(), "viewList");
        return outputNumberedListWithHeader(VIEW_FILE_LIST_HEADER, this.getList());
    }

    /**
     * Check the list for files with names that contain keyword.
     * @param keyword Keyword to be searched.
     * @return  A list of strings containing the the string form of files that contain keyword.
     */
    @Override
    public List<String> containsKeyword(String keyword) {
        LOGGER.entering(getClass().getName(), "containsKeyword");
        List<File> contains = new ArrayList<>();
        for (File file : this.getList()) {
            if (file.getName().toLowerCase().contains(keyword.toLowerCase())) {
                contains.add(file);
            }
        }

        contains.sort(new FileComparator());

        LOGGER.fine(LOG_CONTAINS_KEYWORD);
        LOGGER.entering(getClass().getName(), "containsKeyword");
        return outputNumberedListWithHeader(FILES_CONTAIN + keyword, contains);
    }
}
