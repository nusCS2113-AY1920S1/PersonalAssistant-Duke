package Operations;

import CustomExceptions.DuplicateException;
import CustomExceptions.RoomShareException;
import CustomExceptions.TimeClashException;
import Enums.ExceptionType;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Model_Classes.*;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Timer;
import java.util.Date;

public class TaskCreator {
    public static final String UPDATED_DESCRIPTION_ERROR = "There is a formatting error in your updated description";
    public static final String DURATION_FORMAT_ERROR = "There's a problem with the duration you've specified, default to no duration";
    public static final String RECURRENCE_FORMAT_ERROR = "There seems to some mistake in your recurrence entry, will be setting recurrence as none";
    public static final String DATE_FORMAT_ERROR = "Wrong date format, date is set default to current date";
    public static final String STARTING_DATE_FORMAT_ERROR = "Wrong date format, starting date is set default to current date";
    public static final String ENDING_DATE_FORMAT_ERROR = "Wrong date format, ending date is set default to current date";
    public static final String UPDATED_DATE_ERROR = "Please check the updated date of your task";
    private Parser parser;
    Timer timer;

    /**
     * Constructor for a TaskCreator
     */
    public TaskCreator() {
        parser = new Parser();
        timer = new Timer();
    }

    /**
     * Extract the task type from the user's input
     * @param input user's input
     * @return the task type
     * @throws RoomShareException when the task type is invalid
     */
    public String extractType(String input) throws RoomShareException {
        String[] typeArray = input.split("#");
        String type;
        if (typeArray.length != 1)
            type = typeArray[1].toLowerCase();
        else
            throw new RoomShareException(ExceptionType.emptyTaskType);

        return type;
    }

    /**
     * Extract the description of a task from user's input
     * @param input user's input
     * @return the description of the task
     * @throws RoomShareException when there's no description detected
     */
    public String extractDescription(String input) throws RoomShareException {
        String[] descriptionArray = input.split("\\(");
        String description;
        if (descriptionArray.length != 1) {
            String[] descriptionArray2 = descriptionArray[1].trim().split("\\)");
            description = descriptionArray2[0].trim();
        } else
            throw new RoomShareException(ExceptionType.emptyDescription);
        if (hasSpecialCharacters(description)) {
            throw new RoomShareException(ExceptionType.invalidInputString);
        }
        return description;
    }

    /**
     * Extract the priority of a task from user's input
     * @param input user's input
     * @return the priority of the task
     */
    public Priority extractPriority(String input) {
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

        return priority;
    }

    /**
     * Extract the date and time of a task from user's input
     * @param input user's input
     * @return the date and time of the task
     * @throws RoomShareException when there is no date and time detected or the format of date and time is invalid
     */
    public ArrayList<Date> extractDate(String input) throws RoomShareException {
        // counts the number of '&' tags to determine if the user input a single date or double dates
        int count = 0;
        char[] inputAsChar = input.toCharArray();
        for (char c: inputAsChar) {
            if (c == '&') {
                count++;
            }
        }

        String[] dateArray = input.trim().split("&");
        ArrayList<Date> dates = new ArrayList<>();
        Date currentDate = new Date();
        if (count > 0) {
            if (count <= 2) {
                String dateInput = dateArray[1].trim();
                Date date;
                try {
                    date = parser.formatDate(dateInput);
                    if (date.before(currentDate)) {
                        // the input date is before the current date
                        throw new RoomShareException(ExceptionType.invalidDateError);
                    }
                    dates.add(date);
                } catch (ArrayIndexOutOfBoundsException a) {
                    throw new RoomShareException(ExceptionType.invalidDateError);
                }
            } else {
                String fromInput = dateArray[1].trim();
                String toInput = dateArray[2].trim();
                Date from = new Date();
                Date to = new Date();

                try {
                    from = parser.formatDate(fromInput);
                    dates.add(from);
                } catch (RoomShareException e) {
                    System.out.println(STARTING_DATE_FORMAT_ERROR);
                    dates.add(currentDate);
                }
                try {
                    to = parser.formatDate(toInput);
                    dates.add(to);
                } catch (RoomShareException e) {
                    System.out.println(ENDING_DATE_FORMAT_ERROR);
                }
                if (from.before(currentDate)) {
                    // input date is before the current date
                    throw new RoomShareException(ExceptionType.invalidDateError);
                }
                if (to.before(from)) {
                    // the date is before the current date or is before the starting
                    // date of the leave
                    throw new RoomShareException(ExceptionType.invalidDateRange);
                }
            }
        } else
            throw new RoomShareException(ExceptionType.emptyDate);

        return dates;
    }

    /**
     * Extract the assignee of a task from user's input
     * @param input user's input
     * @return the name of the assignee
     */
    public String extractAssignee(String input) throws RoomShareException{
        String[] assigneeArray = input.split("@");
        String assignee;
        if (assigneeArray.length != 1) {
            assignee = assigneeArray[1].trim();
        } else {
            assignee = "everyone";
        }
        // check for special characters
        if (hasSpecialCharacters(assignee)) {
            throw new RoomShareException(ExceptionType.invalidInputString);
        }
        return assignee;
    }

    /**
     * Extract the recurrence schedule of task from user's input
     * @param input user's input
     * @return the recurrence schedule of the task
     */
    public RecurrenceScheduleType extractRecurrence(String input) {
        String[] recurrenceArray = input.split("%");
        RecurrenceScheduleType recurrence;
        if (recurrenceArray.length != 1) {
            try {
                String inputRecurrence = recurrenceArray[1].trim();
                recurrence = RecurrenceScheduleType.valueOf(inputRecurrence);
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.out.println(RECURRENCE_FORMAT_ERROR);
                recurrence = RecurrenceScheduleType.none;
            }
        } else {
            recurrence = RecurrenceScheduleType.none;
        }

        return  recurrence;
    }

    /**
     * Extract the duration of a task from user's input
     * @param input user's input
     * @return the amount of time and unit of the duration as a Pair<Integer,TimeUnit>
     */
    public Pair<Integer, TimeUnit> extractDuration(String input) throws RoomShareException {
        String[] durationArray = input.split("\\^");
        int duration;
        TimeUnit unit;
        if (durationArray.length != 1) {
            try {
                String[] inputDuration = durationArray[1].split(" ");
                duration = Integer.parseInt(inputDuration[0].trim());
                unit = TimeUnit.valueOf(inputDuration[1].trim());
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println(DURATION_FORMAT_ERROR);
                duration = 0;
                unit = TimeUnit.unDefined;
            }
        } else {
            duration = 0;
            unit = TimeUnit.unDefined;
        }

        if (duration < 0)
            throw new RoomShareException(ExceptionType.negativeTimeAmount);
        return new Pair<>(duration,unit);
    }

    public boolean hasSpecialCharacters(String input) {
        boolean isInvalid = false;
        if (input.contains("#")) {
            isInvalid = true;
        } else if (input.contains("@")) {
            isInvalid = true;
        } else if (input.contains("!")) {
            isInvalid = true;
        } else if (input.contains("*")) {
            isInvalid = true;
        } else if (input.contains("^")) {
            isInvalid = true;
        } else if (input.contains("%")) {
            isInvalid = true;
        } else if (input.contains("&")) {
            isInvalid = true;
        } else if (input.contains("(")) {
            isInvalid = true;
        }
        return isInvalid;
    }

    /**
     * Extract the reminder flag of a task from user's input
     * @param input user's input
     * @return the reminder flag of the task
     */
    public boolean extractReminder(String input) {
        String[] reminderArray = input.split("!");
        if (reminderArray.length != 1) {
            if(reminderArray[1].contains("R"))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    /**
     * Create a new task based on the description the user key in
     * @param input the description of the task
     * @return a new Task object created based on the description
     * @throws RoomShareException when there are some formatting errors
     */
    public Task create(String input) throws RoomShareException, DuplicateException, TimeClashException {
        // extract the Task Type
        String type = this.extractType(input);

        // extract the priority
        Priority priority = this.extractPriority(input);

        // extract the description
        String description = this.extractDescription(input);

        // check for duplicates and time clashes
        int duplicateCheck;
        int timeClashCheck;

        // extract date
        ArrayList<Date> dates = this.extractDate(input);
        Date date = new Date();
        Date from = new Date();
        Date to = new Date();

        if (dates.size() == 1) {
            date = dates.get(0);
        } else {
            from = dates.get(0);
            to = dates.get(1);
        }

        // extract the assignee
        String assignee = this.extractAssignee(input);

        // extract recurrence schedule
        RecurrenceScheduleType recurrence = this.extractRecurrence(input);

        //extract duration
        Pair<Integer, TimeUnit> durationAndUnit = this.extractDuration(input);
        int duration = durationAndUnit.getKey();

        TimeUnit unit = durationAndUnit.getValue();

        //extract reminder
        boolean remind = this.extractReminder(input);

        if (type.equals("assignment")) {
            Assignment assignment = new Assignment(description, date);
            assignment.setPriority(priority);
            assignment.setAssignee(assignee);
            assignment.setRecurrenceSchedule(recurrence);
            if(remind) {
                TaskReminder taskReminder = new TaskReminder(description, duration);
                taskReminder.start();
            }
            duplicateCheck = CheckAnomaly.checkDuplicate(assignment);
            if (duplicateCheck == -1) {
                return assignment;
            } else {
                throw new DuplicateException(duplicateCheck);
            }
        } else if (type.equals("leave")) {
            String user;
            String[] leaveUserArray = input.split("@");
            if (leaveUserArray.length != 1) {
                user = leaveUserArray[1].trim();
            } else
                throw new RoomShareException(ExceptionType.emptyUser);
            Leave leave = new Leave(description, user, from, to);
            leave.setPriority(priority);
            leave.setRecurrenceSchedule(recurrence);
            duplicateCheck = CheckAnomaly.checkDuplicate(leave);
            if (duplicateCheck == -1) {
                return leave;
            } else {
                throw new DuplicateException(duplicateCheck);
            }
        } else if (type.equals("meeting")) {
            if (remind) {
                if (unit.equals(TimeUnit.unDefined)) {
                    // duration was not specified or not correctly input
                    Meeting meeting = new Meeting(description, date);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    TaskReminder taskReminder = new TaskReminder(description, duration);
                    taskReminder.start();
                    duplicateCheck = CheckAnomaly.checkDuplicate(meeting);
                    if (duplicateCheck == -1) {
                        timeClashCheck = CheckAnomaly.checkTimeClash(meeting);
                        if (timeClashCheck == -1) {
                            return meeting;
                        } else {
                            throw new TimeClashException(timeClashCheck);
                        }
                    } else {
                        throw new DuplicateException(duplicateCheck);
                    }

                } else {
                    Meeting meeting = new Meeting(description, date, duration, unit);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    TaskReminder taskReminder = new TaskReminder(description, duration);
                    taskReminder.start();
                    duplicateCheck = CheckAnomaly.checkDuplicate(meeting);
                    if (duplicateCheck == -1) {
                        timeClashCheck = CheckAnomaly.checkTimeClash(meeting);
                        if (timeClashCheck == -1) {
                            return meeting;
                        } else {
                            throw new TimeClashException(timeClashCheck);
                        }
                    } else {
                        throw new DuplicateException(duplicateCheck);
                    }
                }
            } else {
                if (unit.equals(TimeUnit.unDefined)) {
                    // duration was not specified or not correctly input
                    Meeting meeting = new Meeting(description, date);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    duplicateCheck = CheckAnomaly.checkDuplicate(meeting);
                    if (duplicateCheck == -1) {
                        timeClashCheck = CheckAnomaly.checkTimeClash(meeting);
                        if (timeClashCheck == -1) {
                            return meeting;
                        } else {
                            throw new TimeClashException(timeClashCheck);
                        }
                    } else {
                        throw new DuplicateException(duplicateCheck);
                    }
                } else {
                    Meeting meeting = new Meeting(description, date, duration, unit);
                    meeting.setPriority(priority);
                    meeting.setAssignee(assignee);
                    meeting.setRecurrenceSchedule(recurrence);
                    duplicateCheck = CheckAnomaly.checkDuplicate(meeting);
                    if (duplicateCheck == -1) {
                        timeClashCheck = CheckAnomaly.checkTimeClash(meeting);
                        if (timeClashCheck == -1) {
                            return meeting;
                        } else {
                            throw new TimeClashException(timeClashCheck);
                        }
                    } else {
                        throw new DuplicateException(duplicateCheck);
                    }
                }
            }
        } else {
            throw new RoomShareException(ExceptionType.wrongTaskType);
        }
    }

    /**
     * Update a task from the task list according to the user's input
     * @param input user's input
     * @param oldTask the task to be updated
     */
    public void updateTask(String input, Task oldTask) throws RoomShareException {
        try {
            if (input.contains("(") && input.contains(")")) {
                String description = this.extractDescription(input);
                oldTask.setDescription(description);
            }
        } catch (RoomShareException e) {
            System.out.println(UPDATED_DESCRIPTION_ERROR);
        }

        if (input.contains("&")) {
            ArrayList<Date> dates = extractDate(input);
            if (oldTask instanceof Leave && dates.size() == 2) {
                Leave oldLeave = (Leave) oldTask;
                Date start = dates.get(0);
                Date end = dates.get(1);
                oldLeave.setDate(start);
                oldLeave.setStartDate(start);
                oldLeave.setEndDate(end);
            } else {
                Date date = dates.get(0);
                if (oldTask instanceof Leave) {
                    Leave oldLeave = (Leave)oldTask;
                    oldLeave.setEndDate(date);
                } else {
                    oldTask.setDate(date);
                }
            }
        }

        if (input.contains("*")) {
            Priority priority = this.extractPriority(input);
            oldTask.setPriority(priority);
        }

        if (input.contains("@")) {
            String assignee = null;
            try {
                assignee = this.extractAssignee(input);
            } catch (RoomShareException e) {
                assignee = "everyone";
            }
            oldTask.setAssignee(assignee);
        }

        if (input.contains("^") && oldTask instanceof Meeting) {
            Pair<Integer, TimeUnit> durationAndUnit = this.extractDuration(input);
            int duration = durationAndUnit.getKey();
            TimeUnit unit = durationAndUnit.getValue();
            Meeting oldMeeting = (Meeting) oldTask;
            oldMeeting.setDuration(duration,unit);
        }

        if (input.contains("%")) {
            RecurrenceScheduleType recurrence = this.extractRecurrence(input);
            oldTask.setRecurrenceSchedule(recurrence);
        }
    }

    /**
     * Updates the date of the overdue task
     *
     * @param input user's input of the date
     * @param overdueTask the task which date needs to be updated
     */
    public void rescheduleTask(String input, Task overdueTask) throws RoomShareException {
        ArrayList<Date> dates = this.extractDate(input);
        if (overdueTask instanceof Leave && dates.size() == 2) {
            Leave oldLeave = (Leave) overdueTask;
            Date start = dates.get(0);
            Date end = dates.get(1);
            oldLeave.setDate(start);
            oldLeave.setStartDate(start);
            oldLeave.setEndDate(end);
        } else {
            Date date = dates.get(0);
            overdueTask.setDate(date);
        }
    }
}