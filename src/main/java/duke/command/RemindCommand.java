package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RemindCommand extends Command {

    public RemindCommand() {

    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            int num = 1;
            LocalDate date = LocalDate.now();
            LocalDate date1 = date.plusDays(5);

            Date startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (taskList.size() == 0) {
                System.out.println("You have no upcoming events/deadlines/todos");
            } else {
                System.out.println("These are your Tasks in the next 5 days");
                for(int a = 0; a < taskList.size(); a++) {
                    Date taskDate = taskList.getTask(a).getCurrentDate();

                    if((isWithinRange(taskDate, endDate, startDate))) {
                        System.out.println(num + ": " + taskList.getTask(a).toString());
                        num++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isWithinRange(Date taskDate, Date endDate, Date startDate) {
        return taskDate.before(endDate) && taskDate.after(startDate);
    }
}
