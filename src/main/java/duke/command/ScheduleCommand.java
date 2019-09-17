package duke.command;

import duke.*;
import duke.task.Deadline;

public class ScheduleCommand extends Command {
    private String line;
    TaskList list;

    /**
     * Constructor for Schedule Command
     */
    public ScheduleCommand(String line) {
        // Constructor
        super();
        this.line = line;
    }

    /**
     * Checks TaskList for Tasks associated to indicated date
     * @param arr ArrayList of Task objects
     * @param date to display schedule for
     * @return ArrayList of Task objects with indicated date
     */
    public TaskList ScheduleByDate(TaskList arr, String date) {
        // TODO check for invalid format of date
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i) instanceof Deadline) {
                Deadline d = (Deadline) arr.getTask(i);
                if (d.getBy().equals(date)) {
                    list.addTask(arr.getTask(i));
                }
            }
            // TODO double check date/time format of Event
//            else if (arr.getTask(i) instanceof Event) {
//                Event e = (Event) arr.getTask(i);
//                if (e.getStartTiming() == date) {
//                    list.add(arr.getTask(i));
//                }
//            }
        }
        return list;
    }

    public void execute(TaskList arr, Ui ui, Storage storage){
        ScheduleByDate(arr, line);
        ui.showLine();
        System.out.println("\t Here are your tasks for" + line + ": ");
        for (int i = 0; i < arr.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + list.getTask(i));
        }
        ui.showLine();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}