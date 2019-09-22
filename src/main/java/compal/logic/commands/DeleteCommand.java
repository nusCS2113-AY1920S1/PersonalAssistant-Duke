package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.TaskList;

import java.util.Scanner;

public class DeleteCommand extends Command implements CommandParser {

    private TaskList taskList;

    public DeleteCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    @Override
    public void Command(String userIn) throws Duke.DukeException {
        //duke.ui.printg(userIn);
        Scanner scanner = new Scanner(userIn);
        String delete = scanner.next();
        String restOfInput = scanner.nextLine();


        int toRemove = Integer.parseInt(restOfInput.trim()) - 1;
        int maxLimit = taskList.arrlist.size();

        if (toRemove < 0 || toRemove >= maxLimit) {
            duke.ui.printg("RangeError: Invalid range detected for delete command");
            throw new Duke.DukeException("RangeError: Invalid range detected for delete command");
        }

        String removeDesc = taskList.arrlist.get(toRemove).toString();
        taskList.arrlist.remove(toRemove);
        duke.ui.printg("Noted. I've removed this task:");
        duke.ui.printg(removeDesc);
        duke.storage.saveCompal(taskList.arrlist);
        duke.ui.showSize();
        //duke.tasklist.deleteTask(userIn);
    }
}
