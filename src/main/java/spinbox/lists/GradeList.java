package spinbox.lists;

import spinbox.Storage;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.StorageException;
import spinbox.items.GradedComponent;

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
    public void loadData() throws StorageException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            this.add(new GradedComponent(datum));
        }
    }

    @Override
    public void saveData() throws StorageException {
        List<String> dataToSave = new ArrayList<>();
        for (GradedComponent gradedComponent: this.getList()) {
            dataToSave.add(gradedComponent.storeString());
        }
        localStorage.saveData(dataToSave);
    }
}
