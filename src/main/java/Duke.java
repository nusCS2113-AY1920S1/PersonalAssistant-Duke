import executor.task.TaskList;
import storage.Storage;
import ui.Ui;

public class Duke {
    protected static Storage storage;
    protected static Ui ui;
    protected static TaskList taskList;

    /**
     * The Main method by which Duke will be launched.
     */
    public static void main(String[] args) {
        initialise();
    }

    private static void initialise() {
        ui = new Ui("savedData.txt");
        ui.initialise();
    }
}
