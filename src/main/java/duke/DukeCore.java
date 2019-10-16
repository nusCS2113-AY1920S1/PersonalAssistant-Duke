package duke;

import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import duke.gui.Gui;
import duke.task.PatientMap;
import duke.task.Storage;
import duke.task.TaskList;

public class DukeCore {
    public final Storage storage;
    public final Gui ui;
    public TaskList taskList;
    public PatientMap patientMap;

    /**
     * Create new DukeCore, generating taskList from the provided Ui and Storage objects.
     *
     * @param storage Storage object to use in this context.
     * @param ui      Ui object to use in this context.
     * @throws DukeFatalException If unable to setup data file.
     * @see Gui
     * @see TaskList
     * @see Storage
     */
    public DukeCore(Storage storage, Gui ui) throws DukeFatalException {
        this.storage = storage;
        this.ui = ui;

        try {
            taskList = new TaskList(storage);
        } catch (DukeResetException excp) {
            String resetStr;
            ui.printError(excp);

            while (true) { //wait for user to respond
                // TODO: Read user input
                // resetStr = ui.readLine();

                resetStr = "Y";
                if (resetStr.length() > 0) {
                    resetStr = resetStr.substring(0, 1); //extract first char
                    if (resetStr.equalsIgnoreCase("y")) {
                        storage.writeTaskFile(""); //write empty string to data file
                        taskList = new TaskList();
                        ui.print("Your data has been reset!");
                        break;
                    } else if (resetStr.equalsIgnoreCase("n")) {
                        ui.closeUi();
                        //System.exit(0);
                    }
                }
            }
        } catch (DukeFatalException excp) {
            excp.killProgram(ui);
        }
    }
}
