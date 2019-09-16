package duke.gui;

import duke.exception.DukeException;
import duke.task.Storage;
import duke.task.TaskList;

/**
 * GUI version of Duke.
 */
public class DukeGui {
    private Storage storage;
    private TaskList tasks;
    private Gui gui;

    private boolean exitFlag;

    /**
     * Creates a new Duke object (GUI version).
     *
     * @param gui      Graphical User Interface (GUI) object used by Duke
     * @param filePath Path at which to look for or create the data file
     */
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

    /**
     * Responds to the user message.
     *
     * @param userMessage String message entered by the user
     */
    public void respond(String userMessage) {
        if (exitFlag) {
            return;
        }

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