package spinbox.lists;

import spinbox.Storage;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;
import spinbox.items.File;
import spinbox.items.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileList extends SpinBoxList<File> {
    private static final String FILE_LIST_FILE_NAME = "/files.txt";

    public FileList(String parentName) throws FileCreationException {
        super(parentName);
        localStorage = new Storage(DIRECTORY_NAME + this.getParentCode() + FILE_LIST_FILE_NAME);
    }

    /**
     * Does not order the files at the moment as not sure how to order yet.
     */
    static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File a, File b) {
            return -1;
        }
    }

    public void sort() {
        Collections.sort(list, new FileComparator());
    }

    @Override
    public void loadData() throws StorageException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            this.add(new File(datum));
        }
    }

    @Override
    public void saveData() throws StorageException {
        List<String> dataToSave = new ArrayList<>();
        for (File file: this.getList()) {
            dataToSave.add(file.storeString());
        }
        localStorage.saveData(dataToSave);
    }
}
