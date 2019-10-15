package javacake.commands;

import javacake.ProgressStack;
import javacake.Profile;
import javacake.Ui;
import javacake.Storage;

public class ExitCommand extends Command {
    public ExitCommand() {
        type = CmdType.EXIT;
    }

    /**
     * Executes exiting the program.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @return
     */
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) {
        return "Bye. Hope to see you again soon!";
    }
}
