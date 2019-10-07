package models.temp.commands;

import models.temp.tasks.TaskList;
import models.temp.tasks.ToDos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RescheduleCommand implements ICommand {
    /**
     * Class representing the Reschedule Command.
     */
    private int indexOfTask;
    private String newDateTime;

    /**
     * Constructor of Reschedule Command.
     * Parses out the index of task to be rescheduled and the new date and time
     * @param input : text input in the following format: reschedule indexOfTask newDateTime
     */
    public RescheduleCommand(String input) {
        //correct format:  reschedule <indexOfTask> <new date and time>
        String[] allArgs = input.split(" ", 3);
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        this.indexOfTask = Integer.parseInt(allArgs[1]) - 1;

        Date date;
        String formattedDate;

        try {
            // Correct format as 2 December 2019 6 PM
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            date = formatter.parse(allArgs[2]);

            String hour = new SimpleDateFormat("hh").format(date);
            String min = new SimpleDateFormat("mm").format(date);
            String periodMarker = new SimpleDateFormat("a").format(date);
            String formattedTime = hour + "." + min + " " + periodMarker;

            formattedDate = new SimpleDateFormat("d MMMM yyyy").format(date) + " " + formattedTime;

            this.newDateTime = formattedDate;

        } catch (ParseException e) {
            // Invalid Date and Time, revert back to lazyTiming
            this.newDateTime = allArgs[2];
        }
    }

    @Override
    public void execute(TaskList taskList) {
        /**
         * Edits the date stored in the specified task.
         */
        if (!(taskList.getTask(indexOfTask) instanceof ToDos)) {
            taskList.getTask(indexOfTask).setDateTime(this.newDateTime);
            System.out.println("\tNoted. I've rescheduled this task to your indicated timing:");
            System.out.print("\t  ");
            System.out.println("[" + taskList.getTask(indexOfTask).getStatusIcon() + "] "
                                + taskList.getTask(indexOfTask).getDescription());
            System.out.println();
        } else {
            System.out.println("☹ OOPS!!! There is no scheduled timing for this task!.");
        }

    }
}
