package spinbox.containers.lists;

import spinbox.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.entities.items.GradedComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GradeList extends SpinBoxList<GradedComponent> {
    private static final String GRADE_LIST_FILE_NAME = "/grades.txt";

    public GradeList(String parentName) throws FileCreationException {
        super(parentName);
        localStorage = new Storage(DIRECTORY_NAME + this.getParentCode() + GRADE_LIST_FILE_NAME);
    }

    /**
     * Order the grade components based on descending weight.
     */
    static class GradedComponentComparator implements Comparator<GradedComponent> {
        @Override
        public int compare(GradedComponent a, GradedComponent b) {
            return (a.getWeight() > b.getWeight()) ? -1 : 0;
        }
    }

    @Override
    public void sort() {
        Collections.sort(list, new GradedComponentComparator());
    }

    @Override
    public void loadData() throws DataReadWriteException, CorruptedDataException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            this.add(new GradedComponent(datum));
        }
    }

    @Override
    public void saveData() throws DataReadWriteException {
        List<String> dataToSave = new ArrayList<>();
        for (GradedComponent gradedComponent: this.getList()) {
            dataToSave.add(gradedComponent.storeString());
        }
        localStorage.saveData(dataToSave);
    }

    @Override
    public List<String> viewList() {
        List<String> output = new ArrayList<>();
        output.add("Here are the graded components in your module:");
        for (int i = 0; i < list.size(); i++) {
            output.add((Integer.toString(i + 1) + ". " + list.get(i).toString()));
        }
        return output;
    }
}
