package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.gui.Gui;
import duke.task.Storage;

/**
 * GUI version of Duke.
 */
public class DukeGui {
    private DukeCore core; //holds the tasklist, ui and storage classes

    private boolean exitFlag;

    /**
     * Creates a new Duke object (GUI version), with an associated DukeCore.
     *
     * @param gui      Graphical User Interface (GUI) object used by Duke
     * @param filePath The path where the data file will be located.
     * @see DukeCore
     */
    public DukeGui(Gui gui, String filePath) {
        try {
            //construct tasklist from storage and ui
            core = new DukeCore(new Storage(filePath), gui);
            String reminderStr = core.taskList.listReminders().replace(System.lineSeparator(),
                    System.lineSeparator() + "  ");
            core.ui.print("Here are your reminders:" + reminderStr);
        } catch (DukeFatalException excp) {
            excp.killProgram(gui); //standard exit on fatal exception
        } catch (DukeException excp) {
            core.ui.print("You have no reminders!");
        }
        core.ui.printHello();
    }

    /**
     * Responds to the user message.
     */
    public void respond() {
        try {
            Command c = core.ui.parseCommand();
            c.execute(core);
        } catch (DukeException excp) {
            core.ui.printError(excp);
        }
    }
}
