package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Model_Classes.*;

import java.util.Timer;

import java.util.Date;

public class TaskCreator {
    private Parser parser = new Parser();
    Timer timer = new Timer();

    public TaskCreator() {
    }

    public Task create(String input) throws RoomShareException {
        // extract the Task Type
        Parser parser = new Parser();
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
            description = descriptionArray2[0].trim();
        } else
            throw new RoomShareException(ExceptionType.emptyDescription);

        // extract date
        String[] dateArray = input.split("&");
        Date date = new Date();
        Date from = new Date();
        Date to = new Date();
        if (dateArray.length != 1) {
            if (dateArray.length <= 2) {
                String dateInput = dateArray[1].trim();
                try {
                    if (parser.formatDateCustom_1(dateInput) != null)
                        date = parser.formatDateCustom_1(dateInput);
                    else if (parser.formatDateCustom_2(dateInput) != null)
                        date = parser.formatDateCustom_2(dateInput);
                    else
                        date = parser.formatDate(dateInput);
                } catch (RoomShareException e) {
                    System.out.println("Wrong date format, date is set default to current date");
                    date = new Date();
                }
            } else {
                String fromInput = dateArray[1].trim();
                String toInput = dateArray[2].trim();
                try {
                    from = new Parser().formatDate(fromInput);
                } catch (RoomShareException e) {
                    System.out.println("Wrong date format, date is set default to current date");
                    from = new Date();
                }
                try {
                    to = new Parser().formatDate(toInput);
                } catch (RoomShareException e) {
                    System.out.println("Wrong date format, date is set default to current date");
                    to = new Date();
                }
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
        } else {
            duration = 0;
            unit = TimeUnit.unDefined;
        }

        //extract reminder
        String[] reminderArray = input.split("!");
        boolean remind;
        if (reminderArray.length != 1) {
            if(reminderArray[1].contains("R")) {
                remind =true;
            } else  {
                remind = false;
            }
        } else {
            remind = false;
        }

        if (type.contains("assignment")) {
            Assignment assignment = new Assignment(description, date);
            assignment.setPriority(priority);
            assignment.setAssignee(assignee);
            assignment.setRecurrenceSchedule(recurrence);
            return assignment;
        } else if (type.contains("leave")) {
            String user;
            String[] leaveUserArray = input.split("@");
            if (leaveUserArray.length != 1) {
                user = leaveUserArray[1].trim();
            } else
                throw new RoomShareException(ExceptionType.emptyUser);
            Leave leave = new Leave(description, user, from, to);
            leave.setPriority(priority);
            leave.setRecurrenceSchedule(recurrence);
            return leave;
        } else if (type.contains("meeting")) {
            if (remind) {
                if (unit.equals(TimeUnit.unDefined)) {
                    // duration was not specified or not correctly input
                    Meeting meeting = new Meeting(description, date);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    TaskReminder taskReminder = new TaskReminder(description, duration);
                    taskReminder.start();
                    return meeting;
                } else {
                    Meeting meeting = new Meeting(description, date, duration, unit);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    TaskReminder taskReminder = new TaskReminder(description, duration);
                    taskReminder.start();
                    return meeting;
                }
            } else {
                if (unit.equals(TimeUnit.unDefined)) {
                    // duration was not specified or not correctly input
                    Meeting meeting = new Meeting(description, date);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    return meeting;
                } else {
                    Meeting meeting = new Meeting(description, date, duration, unit);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    return meeting;
                }
            }
        } else {
            throw new RoomShareException(ExceptionType.wrongTaskType);
        }
    }
}