package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FreeCommand extends Command {


    /**
     * Constructs a {@code FreeCommand} object with commandType.
     */
    public FreeCommand() {
        super(null, null, null, null);
    }

    /**
     * Iterates through current task list and finds a day with the specific free time slot.
     *
     * @param tasks   The taskList of Duke.
     * @param ui      The ui of Duke.
     * @param storage The storage of Duke.
     */
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know your slot timing");
        }

        String[] freeSlot = commandParams.getMainParam().split(" ");
        int duration = Integer.parseInt(freeSlot[0]);

        if (freeSlot[1] == "mins") {
            duration *= 60000;
        } else {
            duration += 3600000;
        }

        Date today = Calendar.getInstance().getTime();

        List<Task> taskList = tasks.getTasks();

        //Store nearest free date
        int nearest = 0;

        List<Task> eventList = new ArrayList<>();
        for (Task task : taskList) {
            if (task instanceof Event) {
                if (((Event) task).getEnd().after(today)) {
                    eventList.add(task);
                }
            }
        }

        Collections.sort(eventList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return ((Event) o1).getStart().compareTo(((Event) o2).getStart());
            }
        });

        for (int i = 0; i < eventList.size(); i++) {
            Event currTask = (Event) eventList.get(i);
            Event nextTask = (Event) eventList.get(i + 1);
            if (nextTask.getStart().getTime() - currTask.getEnd().getTime() > duration) {
                nearest = nextTask.getStart().getDay();
                break;
            }
        }

        ui.println("Your nearest free day is " + nearest);
    }
}

