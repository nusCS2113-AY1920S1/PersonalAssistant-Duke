package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.util.Scanner;

public class DoneCommand extends Command implements CommandParser {

    private TaskList taskList;

    public DoneCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    @Override
    public void Command(String userIn) {

        Scanner scanner = new Scanner(userIn);
        String delete = scanner.next();
        String restOfInput = scanner.nextLine();

        int toMark = Integer.parseInt(restOfInput.trim()) - 1;
        taskList.arrlist.get(toMark).markAsDone();
        String desc = taskList.arrlist.get(toMark).toString();
        duke.ui.printg("Nice! I've marked this task as done: \n" + desc);
        //duke.tasklist.taskDone(userIn);
    }
}
