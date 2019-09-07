package Commands;

import Tasks.*;
import ControlPanel.*;

public class DeleteCommand extends Command {

    private int serialNo;

    public DeleteCommand(int index){
        serialNo = index;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (serialNo > tasks.lengthOfList()){
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
        System.out.println(" Noted. I've removed this task:\n");
        System.out.println("  " + tasks.getTask(serialNo-1).toString() + "\n");
        System.out.println(" Now you have " + (tasks.lengthOfList()-1) + " tasks in the list.");
        tasks.removeTask(serialNo-1);
        storage.writeTheFile(tasks.getCheckList());
    }
}
