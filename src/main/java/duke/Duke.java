package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.task.Storage;
import duke.command.Ui;

import java.io.File;

public class Duke {
    private DukeContext ctx; //holds the tasklist, ui and storage classes

    /**
     * Creates a new Duke object, with an associated DukeContext.
     * @see DukeContext
     * @param filePath The path where the data file will be located.
     */
    private Duke(String filePath) {
        Ui ui = new Ui(System.in, System.out); //UI construction is safe, send welcome first
        ui.printWelcome();
        try {
            //construct tasklist from storage and ui
            ctx = new DukeContext(new Storage(filePath), ui);
        } catch (DukeFatalException excp) {
            excp.killProgram(ui); //standard exit on fatal exception
        }
        ctx.ui.printHello();
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

    public static void main(String[] argv) {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        new Duke("data" + File.separator + "tasks.tsv").run();
    }
}
