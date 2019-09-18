package models.commands;

import models.tasks.Deadline;
import models.tasks.TaskList;
import models.tasks.ToDos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RescheduleCommand implements ICommand{
    private int indexOfTask;
    private String newDateTime;

    public RescheduleCommand(String input) {
        //correct format:  reschedule <indexOfTask> <new date and time>
        String[] allArgs = input.split(" ", 3);
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        this.indexOfTask = Integer.parseInt(allArgs[1]);

        Date date;
        String formattedDate;

        try {
            // Correct format as 2 December 2019 6 PM
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            date = formatter.parse(allArgs[3]);

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
        if (!(taskList.getTask(indexOfTask) instanceof ToDos)) {
            taskList.getTask(indexOfTask).setDateTime(this.newDateTime);
        }
    }
}
