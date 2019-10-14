package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_INVALID_DATE;

/**
 * Executes user command "findfreeslot".
 */


public class FindFreeSlotCommand extends Command implements CommandParser{


    private TaskList taskList;

    /**
     * Constructs a Command object.
     *
     * @param d Compal object
     */


    public FindFreeSlotCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if(!scanner.hasNext()) {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }

        String restOfInput = scanner.nextLine();
        String date = getDate(restOfInput);
        String hour = getHour(restOfInput);
        String min = getMin(restOfInput);
        int duration = Integer.parseInt(hour) * 60 + Integer.parseInt(min);

        if(!isValidDate(date)) {
            compal.ui.printg(MESSAGE_INVALID_DATE);
            throw new Compal.DukeException(MESSAGE_INVALID_DATE);
        }

        ArrayList<Task> tasks = new ArrayList<Task>();
        for (Task task : taskList.arrlist) {
            if(task.getStringDate().equals(date) && task.getStringStartTime()!="-" && task.getStringEndTime()!="-") {
                tasks.add(task);
            }
        }

        if(tasks.isEmpty()) {
            compal.ui.printg("You are free the whole day!");
        }

        ArrayList<String> listOfTimeSlots = new ArrayList<String>();
        String startTime;
        String endTime;
        int arraySize = tasks.size();
        for (int i = 0; i <= arraySize; i++) {
            if (i == 0) {
                startTime = "0000";
            } else {
                startTime = tasks.get(i - 1).getStringEndTime();
            }

            if(i == arraySize) {
                endTime = "2400";
            } else {
                endTime = tasks.get(i).getStringStartTime();
            }

            if (calculateDuration(startTime, endTime) >= duration) {
                String time = startTime.concat(endTime);
                listOfTimeSlots.add(time);
            }
        }
        int numberOfSlots = listOfTimeSlots.size();
        printTimeSlot(numberOfSlots, listOfTimeSlots);
    }

    public int calculateDuration (String startTime, String endTime) {
        int start = Integer.parseInt(startTime);
        int end = Integer.parseInt(endTime);
        int startMin = start % 100;
        int startHour = start / 100;
        int endMin = end % 100;
        int endHour = end / 100;
        int durationMin;
        int durationHour;
        if (endMin < startMin) {
            durationMin = endMin + 60 - startMin;
            durationHour = endHour - 1 - startHour;
        } else {
            durationMin = endMin - startMin;
            durationHour = endHour - startHour;
        }
        return durationHour * 60 + durationMin;
    }

    public void printTimeSlot (int numberOfSlots, ArrayList<String> timeSlots) {
        if (numberOfSlots == 0) {
            compal.ui.printg("You do not have any free time slots!");
        } else {
            compal.ui.printg("Here are the free time slots: \n");
            for (int i = 0; i < numberOfSlots; i++) {
                String startTime = timeSlots.get(i).substring(0, 4);
                String endTime = timeSlots.get(i).substring(4);
                compal.ui.printg((i + 1) + ". " + startTime + " to " + endTime);
            }
        }
    }
}
