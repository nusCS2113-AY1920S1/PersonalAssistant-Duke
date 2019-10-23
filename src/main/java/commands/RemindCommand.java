package commands;

import java.util.ArrayList;

import core.Ui;
import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;

public class RemindCommand extends Command {
    public String line;

    public RemindCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        Ui.print("Feature coming soon!");
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
