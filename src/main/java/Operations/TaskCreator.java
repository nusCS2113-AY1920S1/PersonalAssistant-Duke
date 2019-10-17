package Operations;

import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Model_Classes.Assignment;
import Model_Classes.Meeting;
import Model_Classes.Task;

import java.util.Date;

public class TaskCreator {
    private Parser parser = new Parser();
    public TaskCreator() {
    }

    public Task create(String input) {
        Priority priorityType;
        RecurrenceScheduleType recurrenceScheduleType;
        String assignee;
        // extract the description
        String[] descriptionArray = input.split("\\(");
        String[] descriptionArray2 = descriptionArray[1].trim().split("\\)");
        String description = descriptionArray2[0];
        // extract the priority
        String[] priorityArray = input.split("\\*");
        if (priorityArray.length != 1) {
            String priority = priorityArray[1];
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
            assignee = assigneeArray[1];
        } else {
            assignee = null;
        }
        // extract the time
        String[] timeArray = input.split("&");
        String time = timeArray[1];
        // extract recurrence schedule
        String[] recurrenceArray = input.split("%");
        if (recurrenceArray.length != 1) {
            String recurrence = recurrenceArray[1];
            try {
                recurrenceScheduleType = RecurrenceScheduleType.valueOf(recurrence);
            } catch (IllegalArgumentException e) {
                System.out.println("There seems to some mistake in your recurrence entry, will be setting recurrence as none");
                recurrenceScheduleType = RecurrenceScheduleType.none;
            }
        } else {
            recurrenceScheduleType = RecurrenceScheduleType.none;
        }
        // create the time
        Date by;
        try {
            by = parser.formatDate(time);
        } catch (RoomShareException e) {
            by = new Date();
        }
        // extract the Task Type
        String[] type = input.split("#");
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
    }

    public Task createWithInformation(String type, String description, Date by,
                                      Priority priority, String user, RecurrenceScheduleType recurrenceScheduleType) {
        if (type.equals("A")) {
            Assignment assignment = new Assignment(description, by);
            assignment.setUser(user);
            assignment.setPriority(priority);
            assignment.setRecurrenceSchedule(recurrenceScheduleType);
            return assignment;
        } else {
            Meeting meeting = new Meeting(description, by);
            meeting.setUser(user);
            meeting.setPriority(priority);
            meeting.setRecurrenceSchedule(recurrenceScheduleType);
            return meeting;
        }
    }
}
