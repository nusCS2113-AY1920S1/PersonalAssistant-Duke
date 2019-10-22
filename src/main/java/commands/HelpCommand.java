package commands;

import members.Member;
import core.Ui;
import tasks.Task;
import utils.CommandResult;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;

/**
 * This class is to handle "add" command
 */
public class HelpCommand extends Command {
    private String content;

    /**
     * This is a class for command of HELP, which displays a list of usable commands.
     */
    public HelpCommand() {
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        return new CommandResult("Available Commands\n"
                + "1. add\n"
                + "2. list\n"
                + "3. done\n"
                + "4. bye\n"
                + "5. delete\n"
                + "6. find\n"
                + "7. recurring\n"
                + "8. snooze\n"
                + "9. schedule\n"
                + "10. check\n"
                + "11. link\n"
                + "12. unlink\n"
                + "13. remove");
    }
}
