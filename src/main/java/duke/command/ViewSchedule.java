package duke.command;

import duke.task.*;

public class ViewSchedule extends Command {

    public ViewSchedule(boolean isExit, String input) { super(isExit, input);}
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 6) {
            throw new DukeException("OOPS!!! The date to search for cannot be empty.");
        }

        input = input.substring(5);

        if (taskList.getSize() == 0) {
            throw new DukeException("You have no tasks in your list");
        }

        boolean found = false;
        int start = 1;
        String schedule = "";
        schedule += "Here is your schedule for " + input +":\n";
        int i = 0;
        while (i < taskList.getSize()) {
            Task task = taskList.getTask(i);
            if (task.toString().contains(input)) {
                schedule += start + "." + task.toString() + "\n";
                start++;
                found = true;
            }
            i++;
        }
        if (found) {
            schedule = schedule.substring(0, schedule.length() - 1);
            ui.output = schedule;
        } else {
            ui.output = "There is no task available on this date.";
        }
    }
}
