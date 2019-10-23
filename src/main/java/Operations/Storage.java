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
                String[] temp = list.split("#", 0);

                // Identify type of task
                String scanType = temp[0].trim();
                SaveType type;
                try {
                    type = SaveType.valueOf(scanType);
                } catch (IllegalArgumentException e) {
                    type = SaveType.empty;
                }

                String scanDone = temp[1].trim();
                boolean done = scanDone.equals("y");

                String scanPriority = temp[2].trim();
                Priority priority;
                try {
                    priority = Priority.valueOf(scanPriority);
                } catch (IllegalArgumentException e) {
                    priority = Priority.low;
                }

                String scanDescription = temp[3].trim();
                String description = scanDescription;

                String scanDate = temp[4].trim();
                Date date = new Date();
                try {
                    date = parser.formatDate(scanDate);
                } catch (RoomShareException e) {
                    System.out.println("error in loading file: date format error");
                }

                String scanRecurrence = temp[5].trim();
                RecurrenceScheduleType recurrence = RecurrenceScheduleType.valueOf(scanRecurrence);

                String scanUser = temp[6].trim();
                String user = scanUser;

                String scanIsFixedDuration = temp[7].trim();
                boolean isFixedDuration = scanIsFixedDuration.equals("F");

                String scanDuration = temp[8].trim();
                int duration = Integer.parseInt(scanDuration);

                String scanUnit = temp[9].trim();
                TimeUnit unit = TimeUnit.valueOf(scanUnit);
                String scanSubTask = "";
                if (temp.length > 10) {
                    scanSubTask = temp[10].trim();
                }

                if (type.equals(SaveType.A)) {
                    // Assignment type
                    Assignment assignment = new Assignment(description, date);
                    assignment.setPriority(priority);
                    assignment.setAssignee(user);
                    assignment.setRecurrenceSchedule(recurrence);
                    assignment.setDone(done);
                    if (!scanSubTask.equals("")) assignment.setSubTasks(scanSubTask);
                    taskArrayList.add(assignment);
                } else {
                    //Meeting type
                    if (isFixedDuration) {
                        Meeting meeting = new Meeting(description, date, duration, unit);
                        meeting.setPriority(priority);
                        meeting.setAssignee(user);
                        meeting.setRecurrenceSchedule(recurrence);
                        meeting.setDone(done);
                        taskArrayList.add(meeting);
                    } else {
                        Meeting meeting = new Meeting(description, date);
                        meeting.setRecurrenceSchedule(recurrence);
                        meeting.setPriority(priority);
                        meeting.setAssignee(user);
                        meeting.setDone(done);
                        taskArrayList.add(meeting);
                    }
                }
            }
        } catch (IOException | IndexOutOfBoundsException e) {
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
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fw);
            for (Task s : list) {
                String out = "";
                String type = String.valueOf(s.toString().charAt(1));
                String isDone = s.getDone() ? "y" : "n";
                String priority = s.getPriority().name();
                String description = s.getDescription();
                String date = convertForStorage(s);
                String recurrence = s.getRecurrenceSchedule().toString();
                String user = s.getUser();
                if (s instanceof Assignment) {
                        out =   type + "#" +
                                isDone + "#" +
                                priority + "#" +
                                description + "#" +
                                date + "#" +
                                recurrence + "#" +
                                user + "#" +
                                "N" + "#" +
                                "0" + "#" +
                                "unDefined" + "#";
                        // Saves sub-tasks
                        if( !(((Assignment) s).getSubTasks() == null ) ) {
                            ArrayList<String> subTasks =  ((Assignment) s).getSubTasks();
                            for( String subTask : subTasks ) {
                                out += subTask + ",";
                            }
                        }
                        out += "#";
                } else if (s instanceof Meeting){
                    if (((Meeting) s).isFixedDuration()) {
                        String duration = ((Meeting) s).getDuration();
                        String unit = ((Meeting) s).getTimeUnit().toString();
                        out =   type + "#" +
                                isDone + "#" +
                                priority + "#" +
                                description + "#" +
                                date + "#" +
                                recurrence + "#" +
                                user+ "#" +
                                "F" + "#" +
                                duration + "#" +
                                unit + "#"
                                + "#";
                    } else {
                        out =   type + "#" +
                                isDone + "#" +
                                priority + "#" +
                                description + "#" +
                                date + "#" +
                                recurrence + "#" +
                                user + "#" +
                                "N" + "#" +
                                "0" + "#" +
                                "unDefined" + "#"
                                + "#";
                    }
                }
                writer.write(out );
                writer.newLine();
            }
            writer.close();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.writeError);
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
            time = day + "/" + month + "/" + year + " " + timeArray[0] + ":" + timeArray[1];
            return time;
        } catch (ParseException e) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
    }

}
