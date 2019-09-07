package Commands;

import Tasks.*;
import ControlPanel.*;

public class ViewCommand extends Command{

    public ViewCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        for (int i = 1; i <= tasks.lengthOfList();i++) {
            System.out.println(" " + i + "." + tasks.getTask(i-1).toString() + "\n");
        }
    }
}
