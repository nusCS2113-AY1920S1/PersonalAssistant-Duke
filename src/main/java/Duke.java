import duke.task.TaskList;
import duke.worker.Storage;
import duke.worker.Ui;

import java.util.ArrayList;
import java.util.List;

public class Duke {
    protected static Storage storage;
    protected static Ui ui;
    protected static TaskList taskList;

    /**
     * The Main method by which Duke will be launched.
     */
    public static void main(String[] args) {
        initialise();
        loadData();
        startUi();
        saveData();
    }

    public static void initialise() {
        storage = new Storage("savedData.txt");
        ui = new Ui();
    }

    public static void loadData() {
        taskList = storage.loadData();
    }

    public static void startUi() {
        ui.initialise(taskList);
    }

    public static void saveData() {
        storage.saveData(taskList);
    }
}
