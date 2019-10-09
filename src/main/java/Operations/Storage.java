package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.Priority;
import Enums.SaveType;
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
                Priority priority = Priority.valueOf(temp[2]);
                boolean done = temp[1].equals("y");
                try {
                    type = SaveType.valueOf(temp[0]);
                } catch (IllegalArgumentException e) {
                    type = SaveType.empty;
                }
                if (list.contains("#week") || list.contains("#day") || list.contains("#month")) {
                    switch (type) {
                        case T:
                            RecurringToDo tempToDo = new RecurringToDo(temp[3], temp[4], done, priority);
                            taskArrayList.add(tempToDo);
                            break;

                        case E:
                            Date by = parser.formatDate(temp[4]);
                            RecurringEvent tempEvent = new RecurringEvent(temp[3], by, temp[5], done, priority);
                            taskArrayList.add(tempEvent);
                            break;
                        case D:
                            Date deadlineBy = parser.formatDate(temp[4]);
                            RecurringDeadline tempDeadline = new RecurringDeadline(temp[3], deadlineBy, temp[5], done, priority);
                            taskArrayList.add(tempDeadline);
                            break;
                    }
                } else {
                    switch (type) {
                        case T:
                            ToDo tempToDo = new ToDo(temp[3], done, priority);
                            taskArrayList.add(tempToDo);
                            break;
                        case E:
                            Date by = parser.formatDate(temp[4]);
                            if( temp.length == 6 ){
                                FixedDuration tempEvent = new FixedDuration(temp[3], by, Integer.parseInt(temp[5]), done, priority);
                                taskArrayList.add(tempEvent);
                            } else {
                                Event tempEvent = new Event(temp[3], by, done, priority);
                                taskArrayList.add(tempEvent);
                            }

                            break;
                        case D:
                            Date deadlineBy = parser.formatDate(temp[4]);
                            Deadline tempDeadline = new Deadline(temp[3], deadlineBy, done, priority);
                            taskArrayList.add(tempDeadline);
                            break;
                        default:
                            throw new RoomShareException(ExceptionType.wrongFormat);
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
                String done = s.getDone() ? "y" : "n";
                String type = String.valueOf(s.toString().charAt(1));
                String priority = s.getPriority().name();
                String description = s.getDescription();
                String time = convertForStorage(s);
                if( s instanceof FixedDuration ){
                    String duration = Integer.toString(((FixedDuration) s).getDuration());
                    String out = type + "#" + done + "#" + priority + "#" + description + "#" + time + "#" + duration;
                    writer.write(out);
                } else {
                    String out = type + "#" + done + "#" + priority + "#" + description + "#" + time;
                    writer.write(out);
                }
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
            String type = String.valueOf(task.toString().charAt(1));
            String time = "";
            if (!task.toString().contains("(R:")) {
                String[] tempString = task.toString().split("\\s+");
                if (!type.equals("T")) {
                    tempString[8] = tempString[8].substring(0, tempString[8].length() - 1);
                    Date date = new SimpleDateFormat("MMM").parse(tempString[4]);
                    DateFormat dateFormat = new SimpleDateFormat("MM");
                    String dateOut = dateFormat.format(date);
                    String[] timeArray = tempString[6].split(":", 3);
                    if( tempString.length == 14 ){
                        time = tempString[5] + "/" + dateOut + "/" + tempString[8] + " " + timeArray[0] + ":" + timeArray[1];
                    } else {
                        time = tempString[5] + "/" + dateOut + "/" + tempString[8] + " " + timeArray[0] + ":" + timeArray[1];
                    }
                }
            } else {
                String[] tempString = task.toString().split("\\s+");
                if (!type.equals("T")) {
                    tempString[8] = tempString[8].substring(0, tempString[8].length() - 1);
                    Date date = new SimpleDateFormat("MMM").parse(tempString[4]);
                    DateFormat dateFormat = new SimpleDateFormat("MM");
                    String dateOut = dateFormat.format(date);
                    String[] timeArray = tempString[6].split(":", 3);
                    String recurringFrame = tempString[10].substring(0, tempString[10].length() - 1);
                     time = tempString[5] + "/" + dateOut + "/" + tempString[8] + " " + timeArray[0] + ":" + timeArray[1] + "#" + recurringFrame;
                } else {
                    time = tempString[3].substring(0, tempString[3].length() - 1);
                }
            }
            return time;
        } catch (ParseException e) {
            throw new RoomShareException(ExceptionType.wrongFormat);
        }
    }
}
