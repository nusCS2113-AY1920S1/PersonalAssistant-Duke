package commands;

import members.Member;
import core.Ui;
import tasks.Task;
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
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        Ui.print("Available Commands\n"
                + "1. add\n"
                + "2. find\n"
                + "3. done\n"
                + "4. delete\n"
                + "5. snooze\n"
                + "6. recurring\n"

                + "7. remove\n"
                + "8. member\n"
                + "9. link\n"
                + "10. unlink\n"

                + "11. list\n"
                + "12. schedule\n"
                + "13. check\n"
                + "14. bye\n");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
