package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ViewCommand extends Command implements CommandParser {

    private TaskList taskList;

    public ViewCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    @Override
    public void Command(String userIn) throws Duke.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        String dateInput = scanner.next();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        String formatDate = null;
        try {
            date = formatter.parse(dateInput);
            formatDate = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (taskList.arrlist.isEmpty()) {
            duke.ui.printg("No task.");
        }

        Boolean isEmpty = true;

        for (Task task : taskList.arrlist) {
            Date convertDate = task.getDate();
            String compareDate = formatter.format(convertDate);
            if (compareDate.matches(formatDate)) {
                if (isEmpty) {
                    duke.ui.printg("On " + formatDate + " you have the task below:");
                }
                duke.ui.printg(task.toString());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            duke.ui.printg("No task found on " + formatDate);
        }
    }
}
