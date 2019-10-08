package duke;

import duke.command.Command;
import duke.command.Ui;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.task.Storage;

import java.io.File;

public class Duke {
    private DukeCore core; //holds the tasklist, ui and storage classes

    /**
     * Creates a new Duke object, with an associated DukeCore.
     *
     * @param filePath The path where the data file will be located.
     * @see DukeCore
     */
    private Duke(String filePath) {
        Ui ui = new Ui(System.in, System.out); //UI construction is safe, send welcome first
        ui.printWelcome();

        try {
            //construct tasklist from storage and ui
            core = new DukeCore(new Storage(filePath), ui);
            String reminderStr = core.taskList.listReminders().replace(System.lineSeparator(),
                    System.lineSeparator() + "  ");
            core.ui.print("Here are your reminders:" + reminderStr);
            core.ui.printHello();
        } catch (DukeFatalException excp) {
            excp.killProgram(ui); //standard exit on fatal exception
        } catch (DukeException excp) {
            core.ui.print("You have no reminders.");
        }
    }

    /**
     * Continually extracts and executes commands from user input.
     */
    private void run() {
        while (true) {
            try {
                Command c = core.ui.parseCommand();
                c.execute(core);
            } catch (DukeException excp) {
                core.ui.printError(excp);
            }
        }
    }

    /**
     * Setup directory for Duke.
     */
    public static void main(String[] argv) {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        new Duke("data" + File.separator + "tasks.tsv").run();
    }
}
