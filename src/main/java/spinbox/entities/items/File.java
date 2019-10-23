package spinbox.entities.items;

import spinbox.exceptions.CorruptedDataException;

public class File extends Item {
    private static final String CORRUPTED_FILES_DATA = "Corrupted files data.";
    private static final String DELIMITER_FILTER = " \\| ";

    /**
     * This constructor is used for recreation of SpinBox.Tasks.FileTask from storage.
     * @param done 1 if task has been mark completed, 0 otherwise.
     * @param fileName the name of the file.
     */
    public File(int done, String fileName) {
        super(fileName);
        this.updateDone(done == 1);
    }

    /**
     * Parses a string extracted from storage back into a File object.
     * @param fromStorage This String is provided directly from the localStorage instance.
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly.
     */
    public File(String fromStorage) throws CorruptedDataException {
        super();
        try {
            String[] components = fromStorage.split(DELIMITER_FILTER);
            this.updateDone(Integer.parseInt(components[0]) == 1);
            this.setName(components[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CorruptedDataException();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String storeString() {
        return super.storeString();
    }
}
