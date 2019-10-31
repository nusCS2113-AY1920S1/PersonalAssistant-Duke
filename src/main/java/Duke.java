import executor.task.TaskList;
import storage.StorageTask;
import storage.StorageWallet;
import ui.Ui;

public class Duke {
    protected static StorageTask storetask;
    protected static StorageWallet storewallet;
    protected static Ui ui;
    protected static TaskList taskList;

    /**
     * The Main method by which Duke will be launched.
     */
    public static void main(String[] args) {
        initialise();
    }

    private static void initialise() {
        ui = new Ui("savedTask.txt", "savedWallet.txt");
        ui.initialise();
    }
}
