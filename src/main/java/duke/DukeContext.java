package duke;

import duke.command.Ui;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import duke.task.Storage;
import duke.task.TaskList;

public class DukeContext {
    public final Storage storage;
    public final Ui ui;
    public TaskList taskList;

    /**
     * Create new DukeContext, generating taskList from the provided Ui and Storage objects.
     * @param _storage Storage object to use in this context.
     * @param _ui Ui object to use in this context.
     * @see Ui
     * @see TaskList
     * @see Storage
     * @throws DukeFatalException If unable to setup data file.
     */
    public DukeContext(Storage _storage, Ui _ui) throws DukeFatalException {
        storage = _storage;
        ui = _ui;

        try {
            taskList = new TaskList(storage);
        } catch (DukeResetException excp) {
            String resetStr;
            ui.printError(excp);

            while (true) { //wait for user to respond
                resetStr = ui.readLine();
                if (resetStr.length() > 0) {
                    resetStr = resetStr.substring(0, 1); //extract first char
                    if (resetStr.equalsIgnoreCase("y")) {
                        storage.writeTaskFile(""); //write empty string to data file
                        taskList = new TaskList();
                        ui.print("Your data has been reset!");
                        break;
                    } else if (resetStr.equalsIgnoreCase("n")) {
                        ui.closeUi();
                        System.exit(0);
                    }
                }
            }
        } catch (DukeFatalException excp) {
            excp.killProgram(ui);
        }
    }
}
