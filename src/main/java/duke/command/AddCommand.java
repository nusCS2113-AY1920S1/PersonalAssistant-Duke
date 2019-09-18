package duke.command;

import duke.extensions.AbnormalityChecker;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Recurring;
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
    boolean isRecurring = false;

    public AddCommand(String description, String taskType) {
        this.taskType = taskType;
        this.description = description;
        checkForFlag();
    }

    private void checkForFlag() {
        String flagArray[] = description.split("-");
        if (flagArray.length != 1) {
            switch (flagArray[1].charAt(0)) {
                case 'r':
                    isRecurring = true;
            }
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
        if (isRecurring) {
            tasks.add(new Recurring(description, taskType, tasks, ui, storage));
            return;
        }
        switch (taskType) {
            case "todo":
                tasks.add(new ToDo(description));
                break;
            case "deadline":
                String[] dInfo = description.split(" /by ");
                SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyyy HHmm");
                Date by = dFormat.parse(dInfo[1]);
                tasks.add(new Deadline(dInfo[0], by));
                break;
            case "event":
                String[] eInfo = description.split(" /at ");
                SimpleDateFormat eFormat = new SimpleDateFormat("ddMMyyyy HHmm");
                Date at = eFormat.parse(eInfo[1]);
                Event newEvent = new Event(eInfo[0], at);
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