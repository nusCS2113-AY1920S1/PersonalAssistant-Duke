package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * One of the B-Extensions.
 * @author 9hafidz6
 */
public class RemindCommand extends Cmd<Task> {

    public RemindCommand() {
        //An empty constructor method
    }

    @Override
    public void execute(GenericList<Task> taskList, Ui ui, Storage storage) throws DukeException {
        int num = 1;
        LocalDate date = LocalDate.now();
        LocalDate date1 = date.plusDays(5);

        Date startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (taskList.size() == 0) {
            System.out.println("You have no upcoming events/deadlines/todos");
        } else {
            System.out.println("These are your Tasks in the next 5 days");
            for (int a = 0; a < taskList.size(); a++) {
                Date taskDate = ((Task)taskList.getEntry(a)).getCurrentDate();

                if ((isWithinRange(taskDate, endDate, startDate))) {
                    System.out.println(num + ": " + taskList.getEntry(a).toString());
                    num++;
                }
            }
        }
    }

    private boolean isWithinRange(Date taskDate, Date endDate, Date startDate) {
        return taskDate.before(endDate) && taskDate.after(startDate);
    }
}
