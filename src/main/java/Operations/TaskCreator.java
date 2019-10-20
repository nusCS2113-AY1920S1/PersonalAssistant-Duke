package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.Meeting;
import Model_Classes.Task;
import java.util.Timer;

import java.util.Date;

public class TaskCreator {
    private Parser parser = new Parser();
    Timer timer = new Timer();

    public TaskCreator() {
    }

    public Task create(String input) throws RoomShareException {
        // extract the Task Type
        String[] typeArray = input.split("#");
        String type;
        if (typeArray.length != 1)
            type = typeArray[1];
        else
            throw new RoomShareException(ExceptionType.emptyTaskType);

        // extract the priority
        String[] priorityArray = input.split("\\*");
        Priority priority;
        if (priorityArray.length != 1) {
            String inputPriority = priorityArray[1].trim();
            try {
                priority = Priority.valueOf(inputPriority);
            } catch (IllegalArgumentException e) {
                System.out.println("There seems to some mistake in your priority entry, will be setting priority as low");
                priority = Priority.low;
            }
        } else {
            priority = Priority.low;
        }

        // extract the description
        String[] descriptionArray = input.split("\\(");
        String description;
        if (descriptionArray.length != 1) {
            String[] descriptionArray2 = descriptionArray[1].trim().split("\\)");
            if (descriptionArray2.length != 1)
                description = descriptionArray2[0].trim();
            else
                throw new RoomShareException(ExceptionType.emptyDescription);
        }
        else
            throw new RoomShareException(ExceptionType.emptyDescription);

        // extract date
        String dateArray[] = input.split("&");
        Date date;
        if (dateArray.length != 1) {
            String dateInput = dateArray[1].trim();
            try {
                date = new Parser().formatDate(dateInput);
            } catch (RoomShareException e) {
                System.out.println("Wrong date format, date is set default to current date");
                date = new Date();
            }
        } else
            throw new RoomShareException(ExceptionType.emptyDate);

        // extract the assignee
        String[] assigneeArray = input.split("@");
        String assignee;
        if (assigneeArray.length != 1) {
            assignee = assigneeArray[1].trim();
        } else {
            assignee = "everyone";
        }

        // extract recurrence schedule
        String[] recurrenceArray = input.split("%");
        RecurrenceScheduleType recurrence;
        if (recurrenceArray.length != 1) {
            String inputRecurrence = recurrenceArray[1].trim();
            try {
                recurrence = RecurrenceScheduleType.valueOf(inputRecurrence);
            } catch (IllegalArgumentException e) {
                System.out.println("There seems to some mistake in your recurrence entry, will be setting recurrence as none");
                recurrence = RecurrenceScheduleType.none;
            }
        } else {
            recurrence = RecurrenceScheduleType.none;
        }

        //extract duration
        String[] durationArray = input.split("\\^");
        int duration;
        TimeUnit unit;
        if (durationArray.length != 1) {
            try {
                String[] inputDuration = durationArray[1].split(" ");
                duration = Integer.parseInt(inputDuration[0].trim());
                unit = TimeUnit.valueOf(inputDuration[1].trim());
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println("There's a problem with the duration you've specified, default to no duration");
                duration = 0;
                unit = TimeUnit.unDefined;
            }
        }
        else {
            duration = 0;
            unit = TimeUnit.unDefined;
        }

        if (type.contains("assignment")) {
            Assignment assignment = new Assignment(description,date);
            assignment.setPriority(priority);
            assignment.setAssignee(assignee);
            assignment.setRecurrenceSchedule(recurrence);
            return assignment;
        } else if (type.contains("meeting")) {
            if (unit.equals(TimeUnit.unDefined)) {
                // duration was not specified or not correctly input
                if( !CheckAnomaly.checkTime(date) ) {
                    Meeting meeting = new Meeting(description,date);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    return meeting;
                }
            }
            else {
                if( !CheckAnomaly.checkTime(date, duration, unit) ) {
                    Meeting meeting = new Meeting(description, date, duration, unit);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    return meeting;
                }
            }
        } else throw new RoomShareException(ExceptionType.wrongTaskType);
    }
}