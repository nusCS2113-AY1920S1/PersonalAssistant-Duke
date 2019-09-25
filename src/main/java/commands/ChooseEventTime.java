package commands;

import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import tasks.TaskList;

import java.text.ParseException;


public class ChooseEventTime extends Command {
    private int serialNo;
    private int choice;

    /**
     * The constructor initialise a ChooseEventTime command.
     * @param command the content of the original command input by the user.
     */
    public ChooseEventTime(String command) {
        command = command.replaceFirst("choose ", "");
        String[] splitStr = command.split(" ");
        serialNo = Integer.parseInt(splitStr[0]);
        choice = Integer.parseInt(splitStr[1]);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, ParseException {
        if (serialNo > tasks.lengthOfList()) {
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
        tasks.getCheckList().get(serialNo - 1).chooseDate(choice - 1); // chooses one choice
        ui.appendToOutput(" Noted. The following timing has been chosen:\n");
        ui.appendToOutput(" " + tasks.getTask(serialNo - 1).toString() + "\n");
        storage.writeTheFile(tasks.getCheckList());
    }
}
