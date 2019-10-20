package Operations;

import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.Leave;
import Model_Classes.Meeting;
import Model_Classes.Task;
import java.util.Timer;

import java.util.Date;

public class TaskCreator {
    private Parser parser = new Parser();
    Timer timer = new Timer();

    public TaskCreator() {
    }

    public Task create(String input) {
        Priority priorityType;
        RecurrenceScheduleType recurrenceScheduleType;
        String assignee;

        // extract the description
        String[] descriptionArray = input.split("\\(");
        String[] descriptionArray2 = descriptionArray[1].trim().split("\\)");
        String description = descriptionArray2[0].trim();
        // extract the priority
        String[] priorityArray = input.split("\\*");
        if (priorityArray.length != 1) {
            String priority = priorityArray[1].trim();
            try {
                priorityType = Priority.valueOf(priority);
            } catch (IllegalArgumentException e) {
                System.out.println("There seems to some mistake in your priority entry, will be setting priority as low");
                priorityType = Priority.low;
            }
        } else {
            priorityType = Priority.low;
        }

        // extract the assignee
        String[] assigneeArray = input.split("@");
        if (assigneeArray.length != 1) {
            assignee = assigneeArray[1].trim();
        } else {
            assignee = "everyone";
        }

        // extract recurrence schedule
        String[] recurrenceArray = input.split("%");
        if (recurrenceArray.length != 1) {
            String recurrence = recurrenceArray[1].trim();
            try {
                recurrenceScheduleType = RecurrenceScheduleType.valueOf(recurrence);
            } catch (IllegalArgumentException e) {
                System.out.println("There seems to some mistake in your recurrence entry, will be setting recurrence as none");
                recurrenceScheduleType = RecurrenceScheduleType.none;
            }
        } else {
            recurrenceScheduleType = RecurrenceScheduleType.none;
        }
        // extract the Task Type
        String[] type = input.split("#");

        // extract the time
        String[] timeArray = input.split("&");

        //leave task type
        // need to fix date bug
        if(type[1].contains("leave")) {
            String timeFrom = timeArray[1].trim();
            String timeTo = timeArray[2].trim();
            Date from;
            try {
                from = parser.formatDate(timeFrom);
            } catch (RoomShareException e) {
                from = new Date();
            }
            Date to;
            try {
                to = parser.formatDate(timeTo);
            } catch (RoomShareException e) {
                to = new Date();
            }
            Leave createdTask = new Leave(description, from, to);
            createdTask.setPriority(priorityType);
            createdTask.setUser(assignee);
            createdTask.setRecurrenceSchedule(recurrenceScheduleType);
            return createdTask;
        }

        String[] durationArray = timeArray[1].split(" ");
        String[] durationLength = durationArray[1].split("-");
        TimeUnit unit;
        int duration;
        if (durationLength.length < 2) {
            // not a fixed duration task
            String time = timeArray[1].trim();
            // create the time
            Date by;
            try {
                by = parser.formatDate(time);
            } catch (RoomShareException e) {
                by = new Date();
            }
            if (type[1].contains("assignment")) {
                Assignment createdTask = new Assignment(description, by);
                createdTask.setPriority(priorityType);
                createdTask.setUser(assignee);
                createdTask.setRecurrenceSchedule(recurrenceScheduleType);
                return createdTask;
            } else {
                Meeting createdTask = new Meeting(description, by);
                createdTask.setPriority(priorityType);
                createdTask.setUser(assignee);
                createdTask.setRecurrenceSchedule(recurrenceScheduleType);
                return createdTask;
            }
        } else if(input.contains("\\^")){ //condition requires some fixing
            // fixed duration task
            // extract the fixed duration timing
            String[] fixedDurationArray = input.split("\\^");
            String[] tempArray = fixedDurationArray[1].split("\\s+");
            duration = Integer.parseInt(tempArray[0].trim());
            unit = TimeUnit.valueOf(tempArray[1].toLowerCase().trim());

            //reminder function
            if (durationArray[2].contains("r")) {
                TaskReminder taskReminder = new TaskReminder(description, duration);
                taskReminder.start();
            }
            Meeting meeting = new Meeting(description, duration, unit);
            meeting.setPriority(priorityType);
            meeting.setUser(assignee);
            meeting.setRecurrenceSchedule(recurrenceScheduleType);
            return meeting;
        } else {
            duration = Integer.parseInt(durationLength[1]) - Integer.parseInt(durationLength[0]);
            //need to change this section
            if(durationArray[2].contains("r")) {
                TaskReminder taskReminder = new TaskReminder(description, duration);
                taskReminder.start();
            }
            if((duration % 100) == 0) {
                duration = duration % 9;
                unit = TimeUnit.valueOf("hours");
                Meeting meeting = new Meeting(description, duration, unit);
                meeting.setPriority(priorityType);
                meeting.setUser(assignee);
                meeting.setRecurrenceSchedule(recurrenceScheduleType);
                return meeting;
            } else {
                unit = TimeUnit.valueOf("minutes");
                Meeting meeting = new Meeting(description, duration, unit);
                meeting.setPriority(priorityType);
                meeting.setUser(assignee);
                meeting.setRecurrenceSchedule(recurrenceScheduleType);
                return meeting;
            }
        }
    }
}