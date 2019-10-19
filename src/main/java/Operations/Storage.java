package Operations;

import CustomExceptions.RoomShareException;
import Enums.*;
import Model_Classes.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Performs storage operations such as writing and reading from a .txt file
 */
public class Storage {
    private Parser parser;

    /**
     * Constructor for the Storage class
     */
    public Storage() {
    }

    /**
     * Returns an ArrayList of Tasks from a .txt file.
     * Extracts the relevant information from the data.txt file in Duke to create the tasks.
     * Populates an ArrayList with these created tasks.
     *
     * @return taskArrayList An ArrayList of Tasks that is created from the .txt file.
     * @throws RoomShareException If the file has mistakes in formatting. Creates and empty task list instead and returns the empty list.
     */
    public ArrayList<Task> loadFile(String fileName) throws RoomShareException {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = "";
            ArrayList<String> tempList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                tempList.add(line);
            }
            parser = new Parser();
            for (String list : tempList) {
                String[] temp = list.split("#");
                SaveType type;
                Priority priority;
                String description = temp[3];
                String rawDate = temp[4];
                String user = temp[6];
                boolean isFixedDuration = false;
                String duration = "";
                TimeUnit unit = TimeUnit.hours;
                Date by = new Date();
                if (temp.length != 9) {
                    try {
                        by = parser.formatDate(rawDate);
                    } catch (RoomShareException e) {
                        System.out.println("error in loading file: date format error");
                    }
                } else {
                    // fixed duration task
                    isFixedDuration = true;
                    duration = temp[7];
                    unit = TimeUnit.valueOf(temp[8]);
                }
                try {
                    priority = Priority.valueOf(temp[2]);
                } catch (IllegalArgumentException e) {
                    priority = Priority.low;
                }
                boolean done = temp[1].equals("y");
                try {
                    type = SaveType.valueOf(temp[0]);
                } catch (IllegalArgumentException e) {
                    type = SaveType.empty;
                }
                if (temp[5].isEmpty()) {
                    // no recurring type
                    if (type.equals(SaveType.A)) {
                        // Assignment type
                        if (isFixedDuration) {
                            // fixed duration assignment
                            Assignment assignment = new Assignment(description, duration, unit);
                            assignment.setPriority(priority);
                            assignment.setUser(user);
                            assignment.setRecurrenceSchedule(RecurrenceScheduleType.none);
                            assignment.setDone(done);
                            taskArrayList.add(assignment);
                        } else {
                            // regular assignment
                            Assignment assignment = new Assignment(description, by);
                            assignment.setPriority(priority);
                            assignment.setUser(user);
                            assignment.setRecurrenceSchedule(RecurrenceScheduleType.none);
                            assignment.setDone(done);
                            taskArrayList.add(assignment);
                        }
                    } else {
                        if (isFixedDuration) {
                            Meeting meeting = new Meeting(description, duration, unit);
                            meeting.setPriority(priority);
                            meeting.setUser(user);
                            meeting.setRecurrenceSchedule(RecurrenceScheduleType.none);
                            meeting.setDone(done);
                            taskArrayList.add(meeting);
                        } else {
                            Meeting meeting = new Meeting(description, by);
                            meeting.setRecurrenceSchedule(RecurrenceScheduleType.none);
                            meeting.setPriority(priority);
                            meeting.setUser(user);
                            meeting.setDone(done);
                            taskArrayList.add(meeting);
                        }
                    }
                } else {
                    // recurring type task
                    if (type.equals(SaveType.A)) {
                        if (isFixedDuration) {
                            Assignment assignment = new Assignment(description, duration, unit);
                            RecurrenceScheduleType recurrenceScheduleType = RecurrenceScheduleType.valueOf(temp[5]);
                            assignment.setRecurrenceSchedule(recurrenceScheduleType);
                            assignment.setDone(done);
                            assignment.setUser(user);
                            assignment.setPriority(priority);
                            taskArrayList.add(assignment);
                        } else {
                            Assignment assignment = new Assignment(description, by);
                            RecurrenceScheduleType recurrenceScheduleType = RecurrenceScheduleType.valueOf(temp[5]);
                            assignment.setRecurrenceSchedule(recurrenceScheduleType);
                            assignment.setDone(done);
                            assignment.setUser(user);
                            assignment.setPriority(priority);
                            taskArrayList.add(assignment);
                        }
                    } else {
                        if (isFixedDuration) {
                            Meeting meeting = new Meeting(description, duration, unit);
                            RecurrenceScheduleType recurrenceScheduleType = RecurrenceScheduleType.valueOf(temp[5]);
                            meeting.setRecurrenceSchedule(recurrenceScheduleType);
                            meeting.setDone(done);
                            meeting.setUser(user);
                            meeting.setPriority(priority);
                            taskArrayList.add(meeting);
                        } else {
                            Meeting meeting = new Meeting(description, by);
                            RecurrenceScheduleType recurrenceScheduleType = RecurrenceScheduleType.valueOf(temp[5]);
                            meeting.setRecurrenceSchedule(recurrenceScheduleType);
                            meeting.setDone(done);
                            meeting.setUser(user);
                            meeting.setPriority(priority);
                            taskArrayList.add(meeting);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
        return (taskArrayList);
    }

    /**
     * Rewrites the data.txt file with a task list.
     * Formats all task information into a style that the loadFile() method is able to understand
     * Writes all the formatted information into a data.txt file for storage
     * Will not write any information if the there are mistakes in the ArrayList information.
     *
     * @param list ArrayList of Tasks to be stored on data.txt
     * @throws RoomShareException If there are parsing errors in the ArrayList.
     */
    public void writeFile(ArrayList<Task> list, String fileName) throws RoomShareException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Task s : list) {
                String out = "";
                String done = s.getDone() ? "y" : "n";
                String type = String.valueOf(s.toString().charAt(1));
                String priority = s.getPriority().name();
                String description = s.getDescription();
                String assignee = s.getUser();
                if (s instanceof Assignment) {
                        String time = convertForStorage(s);
                        out = type + "#" + done + "#" + priority + "#" + description + "#" + time + assignee;
                } else if (s instanceof Meeting){
                    if (((Meeting) s).isFixedDuration()) {
                        String duration = ((Meeting) s).getDuration();
                        String unit = ((Meeting) s).getTimeUnit().toString();
                        String recurrence = s.getRecurrenceSchedule().toString();
                        out = type + "#" + done + "#" + priority + "#" + description + "#"
                                + "F" + "#" + recurrence + "#" + assignee+ "#" + duration + "#" + unit;
                    } else {
                        String time = convertForStorage(s);
                        out = type + "#" + done + "#" + priority + "#" + description + "#" + time + assignee;
                    }
                }
                writer.write(out);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
    }

    /**
     * Extracts and converts all the information in the task object for storage
     * will format the time information for deadline and event tasks
     * Additional formatting will be done for recurring tasks to include recurrence schedule
     * returns a string with all the relevant information.
     * @param task task object to be converted
     * @return time A String containing all the relevant information
     * @throws RoomShareException If there is any error in parsing the Date information.
     */
    String convertForStorage(Task task) throws RoomShareException {
        try {
            String time = "";
            String[] prelimSplit = task.toString().split("\\(");
            String[] tempString = prelimSplit[2].split("\\s+");
            String year = tempString[6].substring(0, tempString[6].length() - 1);
            Date date = new SimpleDateFormat("MMM").parse(tempString[2]);
            DateFormat dateFormat = new SimpleDateFormat("MM");
            String month = dateFormat.format(date);
            String[] timeArray = tempString[4].split(":", 3);
            String day = tempString[3];
            String recurrence = task.getRecurrenceSchedule().toString();
            time = day + "/" + month + "/" + year + " " + timeArray[0] + ":" + timeArray[1] + '#' + recurrence +"#";
            if (task instanceof FixedDuration) {
                String[] durationArray = prelimSplit[3].split(":");
                String temp = durationArray[1].trim();
                String[] tempDuration = temp.split("\\s+");
                time = time + tempDuration[0].trim() + "#" +
                        tempDuration[1].trim().substring(0, tempDuration[1].length() - 1) + "#" + recurrence + "#";
            }
            return time;
        } catch (ParseException e) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
    }

}
