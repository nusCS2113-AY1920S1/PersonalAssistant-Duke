package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.task.Deadline;

public class ScheduleCommand extends Command {
    private String date;

    /**
     * Constructor for ScheduleCommand.
     *
     * @param date String containing date.
     */
    public ScheduleCommand(String date) {
        super();
        this.date = date;
    }


    /**
     * Checks TaskList for Tasks associated to indicated date.
     *
     * @param arr  ArrayList of Task objects.
     * @param date to display schedule for.
     * @return ArrayList of Task objects associated to given date.
     */
    public TaskList scheduleByDate(TaskList arr, String date) {
        TaskList scheduledTasks = new TaskList();
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i) instanceof Deadline) {
                Deadline d = (Deadline) arr.getTask(i);
                if (d.getBy().equals(date)) {
                    scheduledTasks.addTask(arr.getTask(i));
                }
            }
        }
        return scheduledTasks;
    }


    /**
     * Executes Schedule Command.
     *
     * @param arr     ArrayList of Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        TaskList scheduledTasks = scheduleByDate(arr, this.date);
        ui.printScheduledTasks(scheduledTasks, this.date);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}