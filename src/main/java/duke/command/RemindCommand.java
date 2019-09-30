package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RemindCommand extends Command{

    public RemindCommand() {

    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException{
        try {
            int num = 1;
            LocalDate date = LocalDate.now();
            LocalDate endDate = date.plusDays(5);

            Date StartDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date EndDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if(taskList.size() == 0) {
                System.out.println("You have no upcoming events/deadlines/todos");
            }
            else {
                System.out.println("These are your Tasks in the next 5 days");
                for(int a = 0; a < taskList.size(); a++) {
                    Date TaskDate = taskList.getTask(a).getCurrentDate();

                    if((isWithinRange(TaskDate, EndDate, StartDate))) {
                        System.out.println(num + ": " + taskList.getTask(a).toString());
                        num++;
                    }
                }
            }
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean isWithinRange(Date TaskDate, Date EndDate, Date StartDate) {
        return TaskDate.before(EndDate) && TaskDate.after(StartDate);
    }
}
