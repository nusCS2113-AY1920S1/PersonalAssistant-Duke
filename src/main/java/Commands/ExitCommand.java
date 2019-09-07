package Commands;

import Tasks.*;
import ControlPanel.*;

public class ExitCommand extends Command {

    public ExitCommand(){

    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("     Bye. Hope to see you again soon!\n");
    }


}
