package spinbox.containers.lists;

import spinbox.datapersistors.storage.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.exceptions.InputException;
import spinbox.entities.items.GradedComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GradeList extends SpinBoxList<GradedComponent> {
    private static final Logger LOGGER = Logger.getLogger(SpinBoxList.class.getName());
    private static final String LOG_LOAD_DATA = "Load data from local storage.";
    private static final String LOG_SAVE_DATA = "Saved data into local storage.";
    private static final String LOG_SORT_LIST = "Sorted list.";
    private static final String LOG_VIEW_LIST = "View list.";
    private static final String LOG_CONTAINS_KEYWORD = "Found list of items that contain keyword.";
    private static final String GRADE_LIST_FILE_NAME = "/grades.txt";
    private static final String VIEW_GRADE_LIST_HEADER = "Here are the graded components in your module:";
    private static final String GRADE_CONTAIN = "Here are the graded components that contain ";

    /**
     * Constructor for GradeList.
     * @param parentName The module code of the list.
     * @throws FileCreationException Error in creating the file to store data.
     */
    public GradeList(String parentName) throws FileCreationException {
        super(parentName);
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        localStorage = new Storage(DIRECTORY_NAME + this.getParentCode() + GRADE_LIST_FILE_NAME);
    }

    /**
     * A comparator to sort the grade components based on descending weight.
     */
    static class GradedComponentComparator implements Comparator<GradedComponent> {
        @Override
        public int compare(GradedComponent a, GradedComponent b) {
            return (a.getWeight() > b.getWeight()) ? -1 : 0;
        }
    }

    /**
     * Method that will sort the list according to descending weight.
     */
    @Override
    public void sort() {
        LOGGER.entering(getClass().getName(), "sort");
        list.sort(new GradedComponentComparator());
        LOGGER.fine(LOG_SORT_LIST);
        LOGGER.exiting(getClass().getName(), "sort");
    }

    /**
     * Populates data into the graded component list from the list's localStorage.
     * @throws DataReadWriteException loadData fail due to I/O Error..
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly.
     */
    @Override
    public void loadData() throws DataReadWriteException, CorruptedDataException {
        LOGGER.entering(getClass().getName(), "loadData");
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            GradedComponent gradedComponent = new GradedComponent();
            gradedComponent.fromStoredString(datum);
            this.addFromStorage(gradedComponent);
        }
        LOGGER.fine(LOG_LOAD_DATA);
        LOGGER.exiting(getClass().getName(), "loadData");
    }

    /**
     * Saves the current graded component list data into the list's localStorage.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    @Override
    public void saveData() throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "saveData");
        List<String> dataToSave = new ArrayList<>();
        for (GradedComponent gradedComponent: this.getList()) {
            dataToSave.add(gradedComponent.storeString());
        }
        localStorage.writeData(dataToSave);
        LOGGER.fine(LOG_SAVE_DATA);
        LOGGER.exiting(getClass().getName(), "saveData");
    }

    /**
     * Gives the list of graded components in string format.
     * @return Returns list of graded components that have been converted to string format.
     */
    @Override
    public List<String> viewList() {
        LOGGER.entering(getClass().getName(), "viewList");
        LOGGER.fine(LOG_VIEW_LIST);
        LOGGER.exiting(getClass().getName(), "viewList");
        return outputNumberedListWithHeader(VIEW_GRADE_LIST_HEADER, this.getList());
    }

    /**
     * Check the list for graded component with names that contain keyword.
     * @param keyword Keyword to be searched.
     * @return  A list of strings containing the the string form of graded
     *          components that contain keyword.
     */
    @Override
    public List<String> containsKeyword(String keyword) {
        LOGGER.entering(getClass().getName(), "containsKeyword");
        List<GradedComponent> contains = new ArrayList<>();
        for (GradedComponent gradedComponent : this.getList()) {
            if (gradedComponent.getName().toLowerCase().contains(keyword.toLowerCase())) {
                contains.add(gradedComponent);
            }
        }
        contains.sort(new GradedComponentComparator());

        LOGGER.fine(LOG_CONTAINS_KEYWORD);
        LOGGER.entering(getClass().getName(), "containsKeyword");
        return outputNumberedListWithHeader(GRADE_CONTAIN, contains);
    }

    /**
     * Updates the graded component at index to new values.
     * @param index Index of graded component to be updated.
     * @param yourScore The score received for the component.
     * @param maximumScore the maximum score received for the component.
     * @throws InputException Provided Index is out range.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    public void updateGradeWeightedScore(int index, double yourScore, double maximumScore) throws InputException,
            DataReadWriteException {
        LOGGER.entering(getClass().getName(), "updateGradeWeightedScore");
        this.get(index).updateWeightedScore(yourScore, maximumScore);
        this.saveData();
        LOGGER.exiting(getClass().getName(), "updateGradeWeightedScore");
    }

    /**
     * Updates the graded component at index to new values.
     * @param index Index of graded component to be updated.
     * @param weightedScore The weighted score of the component.
     * @throws DataReadWriteException saveData fail due to I/O Error..
     * @throws InvalidIndexException Provided Index is out range..
     */
    public void updateGradeWeightedScore(int index, double weightedScore) throws DataReadWriteException,
            InvalidIndexException {
        LOGGER.entering(getClass().getName(), "updateGradeWeightedScore");
        this.get(index).updateWeightedScore(weightedScore);
        this.saveData();
        LOGGER.exiting(getClass().getName(), "updateGradeWeightedScore");
    }
}
