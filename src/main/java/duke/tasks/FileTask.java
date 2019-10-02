package duke.tasks;

public class FileTask extends Task {
    public FileTask(String fileName) {
        super(fileName);
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.FileTask from storage.
     * @param done 1 if task has been mark completed, 0 otherwise.
     * @param fileName the name of the file.
     */
    public FileTask(int done, String fileName) {
        super(fileName);
        this.isDone = (done == 1);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String storeString() {
        return "FILE | " + super.storeString();
    }

    @Override
    String getStartDateString() {
        return null;
    }

    @Override
    String getEndDateString() {
        return null;
    }
}
