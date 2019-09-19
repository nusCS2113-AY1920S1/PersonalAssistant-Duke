package duke.command;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.worker.Ui;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CommandReminder extends Command {
    //private String[] reminders;
    protected Date currentDate = Calendar.getInstance().getTime();

    public CommandReminder() {
        this.currentDate.setTime(0);
    }

    @Override
    public void execute(TaskList taskList) {
        try {
            for (Task task : taskList.getList()) {
                Date dateCopy = task.getDatetime();
                if (dateCopy != null) {
                    dateCopy.setTime(0);
                    if (dateCopy.equals(this.currentDate)) {
                        Ui.dukeSays(task.genTaskDesc());
                        Ui.printSeparator();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("sorry");
        }
    }
}
