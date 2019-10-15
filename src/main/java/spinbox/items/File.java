package spinbox.items;

public class File extends Item {
    public File(String fileName) {
        super(fileName);
    }

    /**
     * This constructor is used for recreation of SpinBox.Tasks.FileTask from storage.
     * @param done 1 if task has been mark completed, 0 otherwise.
     * @param fileName the name of the file.
     */
    public File(int done, String fileName) {
        super(fileName);
        this.setDone(done == 1);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String storeString() {
        return "FILE | " + super.storeString();
    }
}
