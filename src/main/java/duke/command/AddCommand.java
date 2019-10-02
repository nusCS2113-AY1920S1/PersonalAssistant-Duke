package duke.command;

import duke.extensions.AbnormalityChecker;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.ToDo;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * duke.command.AddCommand that deals with the adding of new duke.task.Task objects to the duke.tasklist.TaskList
 */
public class AddCommand extends Command {
    String description;
    String taskType;
    String recurringPeriod;

    public AddCommand(String description, String taskType, String recurringPeriod) {
        this.taskType = taskType;
        this.description = description;
        this.recurringPeriod = recurringPeriod;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
        switch (taskType) {
            case "todo":
                tasks.add(new ToDo(description, recurringPeriod));
                break;
            case "deadline":
                String[] dInfo = description.split(" /by ");
                SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyyy HHmm");
                Date by = dFormat.parse(dInfo[1]);
                tasks.add(new Deadline(dInfo[0], by, recurringPeriod));
                break;
            case "event":
                String[] eInfo = description.split(" /at ");
                SimpleDateFormat eFormat = new SimpleDateFormat("ddMMyyyy HHmm");
                Date at = eFormat.parse(eInfo[1]);
                Event newEvent = new Event(eInfo[0], at, recurringPeriod);
                AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
                if (abnormalityChecker.checkEventClash(newEvent)) {
                    System.out.println("There is a clash with another event at the same time");
                } else {
                    tasks.add(newEvent);
                }
                break;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}