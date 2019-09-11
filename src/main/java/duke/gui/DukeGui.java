package duke.gui;

import duke.exception.DukeException;
import duke.task.Storage;
import duke.task.TaskList;

public class DukeGui {
    private Storage storage;
    private TaskList tasks;
    private Gui gui;

    private boolean exitFlag;

    public DukeGui(Gui gui, String filePath) {
        this.gui = gui;
        gui.showWelcomeMessage();

        try {
            storage = new Storage(filePath);
            // TODO: Duke GUI
            // tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            gui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }

        exitFlag = false;
    }

    public void respond(String userMessage) {
        if (exitFlag) return;

        // TODO: Duke GUI
        // try {
        //    Command command = Parser.parse(userMessage);
        //    command.execute(tasks, gui, storage);
        //    exitFlag = command.isExit();
        //    if (exitFlag) gui.exit();
        // } catch (DukeException e) {
        //    gui.showError(e.getMessage());
        // }
    }
}