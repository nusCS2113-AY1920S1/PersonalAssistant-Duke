package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Deadline;

public class ScheduleCommand extends Command {
    private String line;
    TaskList list;


    /**
     * Constructor for ScheduleCommand.
     * @param line String containing date.
     */
    public ScheduleCommand(String line) {
        // Constructor
        super();
        this.line = line;
    }


    /**
     * Checks TaskList for Tasks associated to indicated date.
     * @param arr Arraylist of Task objects.
     * @param date to display schedule for.
     * @return ArrayList of Task objects associated to given date.
     */
    public TaskList scheduleByDate(TaskList arr, String date) {
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i) instanceof Deadline) {
                Deadline d = (Deadline) arr.getTask(i);
                if (d.getBy().equals(date)) {
                    list.addTask(arr.getTask(i));
                }
            }
        }
        return list;
    }


    /**
     * Execute Schedule Command.
     * @param arr ArrayList of Task objects.
     * @param ui      Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *                *         objects to harddisk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        list = scheduleByDate(arr, line);
        ui.showLine();
        System.out.println("\t Here are your tasks for" + line + ": ");
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + list.getTask(i));
        }
        ui.showLine();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}