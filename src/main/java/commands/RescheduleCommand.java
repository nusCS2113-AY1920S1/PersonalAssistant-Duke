package commands;

import controlpanel.Ui;
import controlpanel.Parser;
import controlpanel.Storage;
import controlpanel.DukeException;
import tasks.TaskList;

import java.text.ParseException;

/**
 * The class which can edit the type and the date of the task.
 */
public class RescheduleCommand extends Command {

    private String cmd;

    /**
     * The constructor.
     * @param inputString the original command typed in by user.
     */
    public RescheduleCommand(String inputString) {
        cmd = inputString;
    }

    /**
     * To show whether this command should be the last one.
     * @return whether this command should be the last one.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Run this command. Delete the original task first then add the new one.
     * @param tasks the object controls the task list.
     * @param ui user interface.
     * @param storage the object saves or reads the file.
     * @throws DukeException check if any exception is caught.
     * @throws ParseException check if any exception is caught.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, ParseException {
        String[] words = cmd.split(" ");
        final String deleteCommand = "delete " + words[2];
        int serialNo = Integer.parseInt(words[2]);
        words[0] = "";
        words[2] = tasks.getTask(serialNo - 1).getDescription();

        String newAddCommand = words[1];
        for (int i = 2; i < words.length; i++) {
            newAddCommand = newAddCommand + " " + words[i];
        }
        Command delete = Parser.parse(deleteCommand);
        delete.execute(tasks, ui, storage);
        Command add = Parser.parse(newAddCommand);
        add.execute(tasks, ui, storage);
    }
}
