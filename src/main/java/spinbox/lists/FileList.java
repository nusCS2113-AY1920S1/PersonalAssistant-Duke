package spinbox.lists;

import spinbox.items.File;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileList extends SpinBoxList<File> {
    public FileList() {
        super();
    }

    public FileList(List<File> files) {
        super(files);
    }

    /**
     * Mark the file at index as downloaded.
     * @param index index of element to be marked.
     * @return task that was marked as downloaded.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public File mark(int index) throws IndexOutOfBoundsException {
        list.get(index).markDone();
        return list.get(index);
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
}
