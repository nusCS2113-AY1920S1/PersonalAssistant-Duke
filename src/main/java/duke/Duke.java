package duke;

import duke.command.Command;
import duke.command.Ui;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.task.Storage;

import java.io.File;

public class Duke {
    private DukeContext ctx; //holds the tasklist, ui and storage classes

    /**
     * Creates a new Duke object, with an associated DukeContext.
     *
     * @param filePath The path where the data file will be located.
     * @see DukeContext
     */
    private Duke(String filePath) {
        Ui ui = new Ui(System.in, System.out); //UI construction is safe, send welcome first
        ui.printWelcome();

        try {
            //construct tasklist from storage and ui
            ctx = new DukeContext(new Storage(filePath), ui);
            ctx.ui.print("Here are your reminders:" + ctx.taskList.listReminders());
            ctx.ui.printHello();
        } catch (DukeFatalException excp) {
            excp.killProgram(ui); //standard exit on fatal exception
        } catch (DukeException excp) {
            ctx.ui.print("You have no reminders.");
        }
    }

    /**
     * Continually extracts and executes commands from user input.
     */
    private void run() {
        while (true) {
            try {
                Command c = ctx.ui.parseCommand();
                c.execute(ctx);
            } catch (DukeException excp) {
                ctx.ui.printError(excp);
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
