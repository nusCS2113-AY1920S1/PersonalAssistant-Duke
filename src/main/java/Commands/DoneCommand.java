package Commands;

import Tasks.*;
import ControlPanel.*;

public class DoneCommand extends Command {

    private int serialNo;

    public DoneCommand(int index){
        serialNo = index;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (serialNo > tasks.lengthOfList()) {
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
        tasks.markDoneATask(serialNo-1);
        storage.writeTheFile(tasks.getCheckList());
        System.out.println(" Nice! I've marked this task as done:\n");
        System.out.println("   [✓] " + tasks.getTask(serialNo-1).getDescription() + "\n");
    }
}
