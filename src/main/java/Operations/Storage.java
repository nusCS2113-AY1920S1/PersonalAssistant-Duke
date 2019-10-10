package Operations;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Enums.SaveType;
import Model_Classes.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.Types.NULL;

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
     * @throws DukeException If the file has mistakes in formatting. Creates and empty task list instead and returns the empty list.
     */
    public ArrayList<Task> loadFile(String fileName) throws DukeException {
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
                try {
                    type = SaveType.valueOf(temp[0]);
                } catch (IllegalArgumentException e) {
                    type = SaveType.empty;
                }
                if (list.contains("#week") || list.contains("#day") || list.contains("#month")) {
                    switch (type) {
                        case T:
                            RecurringToDo tempToDo = new RecurringToDo(temp[2], temp[3]);
                            if (temp[1].equals("y")) {
                                tempToDo.setDone();
                            }
                            taskArrayList.add(tempToDo);
                            break;

                        case E:
                            Date by = parser.formatDate(temp[3]);
                            RecurringEvent tempEvent = new RecurringEvent(temp[2], by, temp[4]);
                            if (temp[1].equals("y")) {
                                tempEvent.setDone();
                            }
                            taskArrayList.add(tempEvent);
                            break;
                        case D:
                            Date deadlineBy = parser.formatDate(temp[3]);
                            RecurringDeadline tempDeadline = new RecurringDeadline(temp[2], deadlineBy, temp[4]);
                            if (temp[1].equals("y")) {
                                tempDeadline.setDone();
                            }
                            taskArrayList.add(tempDeadline);
                            break;
                    }
                } else {
                    switch (type) {
                        case T:
                            ToDo tempToDo = new ToDo(temp[2]);
                            if (temp[1].equals("y")) {
                                tempToDo.setDone();
                            }
                            taskArrayList.add(tempToDo);
                            break;
                        case E:
                            Date by = parser.formatDate(temp[3]);
                            if( temp.length == 5 ){
                                FixedDuration tempEvent = new FixedDuration(temp[2], by, Integer.parseInt(temp[4]));
                                if (temp[1].equals("y")) {
                                    tempEvent.setDone();
                                }
                                taskArrayList.add(tempEvent);
                            } else {
                                Event tempEvent = new Event(temp[2], by);
                                if (temp[1].equals("y")) {
                                    tempEvent.setDone();
                                }
                                taskArrayList.add(tempEvent);
                            }

                            break;
                        case D:
                            Date deadlineBy = parser.formatDate(temp[3]);
                            Deadline tempDeadline = new Deadline(temp[2], deadlineBy);
                            if (temp[1].equals("y")) {
                                tempDeadline.setDone();
                            }
                            taskArrayList.add(tempDeadline);
                            break;
                        default:
                            throw new DukeException(ExceptionType.wrongFormat);
                    }
                }
            }
        } catch (IOException e) {
            throw new DukeException(ExceptionType.wrongFormat);
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
     * @throws DukeException If there are parsing errors in the ArrayList.
     */
    public void writeFile(ArrayList<Task> list, String fileName) throws DukeException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Task s : list) {
                String done = s.getDone() ? "y" : "n";
                String type = String.valueOf(s.toString().charAt(1));
                String description = s.getDescription();
                String time = convertForStorage(s);
                if( s instanceof FixedDuration ){
                    String duration = Integer.toString(((FixedDuration) s).getDuration());
                    String out = type + "#" + done + "#" + description + "#" + time + "#" + duration;
                    writer.write(out);
                } else {
                    String out = type + "#" + done + "#" + description + "#" + time;
                    writer.write(out);
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException(ExceptionType.wrongFormat);
        }
    }

    /**
     * Extracts and converts all the information in the task object for storage
     * will format the time information for deadline and event tasks
     * Additional formatting will be done for recurring tasks to include recurrence schedule
     * returns a string with all the relevant information.
     * @param task task object to be converted
     * @return time A String containing all the relevant information
     * @throws DukeException If there is any error in parsing the Date information.
     */
    String convertForStorage(Task task) throws DukeException {
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
            throw new DukeException(ExceptionType.wrongFormat);
        }
    }
}
