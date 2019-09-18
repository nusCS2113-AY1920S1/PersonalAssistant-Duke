package Model_Classes;

import CustomExceptions.DukeException;

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
     * @return taskArrayList An ArrayList of Tasks that is created from the .txt file.
     * @throws DukeException If the file has mistakes in formatting. Creates and empty task list instead and returns the empty list.
     */
    public ArrayList<Task> loadFile() throws DukeException {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
            String line = "";
            ArrayList<String> tempList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                tempList.add(line);
            }
            parser = new Parser();
            for (String list : tempList) {
                String[] temp = list.split("#", 4);
                switch (temp[0]) {
                    case "T":
                        ToDo tempToDo = new ToDo(temp[2]);
                        if (temp[1].equals("y")) {
                            tempToDo.setDone();
                        }
                        taskArrayList.add(tempToDo);
                        break;
                    case "E":
                        Date by = parser.formatDate(temp[3]);
                        Event tempEvent = new Event(temp[2], by);
                        if (temp[1].equals("y")) {
                            tempEvent.setDone();
                        }
                        taskArrayList.add(tempEvent);
                        break;
                    case "D":
                        Date deadlineBy = parser.formatDate(temp[3]);
                        Deadline tempDeadline = new Deadline(temp[2], deadlineBy);
                        if (temp[1].equals("y")) {
                            tempDeadline.setDone();
                        }
                        taskArrayList.add(tempDeadline);
                        break;
                }
            }
        } catch (IOException e) {
            throw new DukeException();
        }
        return (taskArrayList);
    }

    /**
     * Rewrites the data.txt file with a task list.
     * Formats all task information into a style that the loadFile() method is able to understand
     * Writes all the formatted information into a data.txt file for storage
     * Will not write any information if the there are mistakes in the ArrayList information.
     * @param list ArrayList of Tasks to be stored on data.txt
     * @throws DukeException If there are parsing errors in the ArrayList.
     */
    public void writeFile(ArrayList<Task> list) throws DukeException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            for (Task s : list) {
                String done = s.getDone() ? "y" : "n";
                String type = String.valueOf(s.toString().charAt(1));
                String description = s.getDescription();
                String[] tempString = s.toString().split("\\s+");
                String time = "";
                if (!type.equals("T")) {
                    tempString[8] = tempString[8].substring(0, tempString[8].length()-1);
                    Date date = new SimpleDateFormat("MMM").parse(tempString[4]);
                    DateFormat dateFormat = new SimpleDateFormat("MM");
                    String dateOut = dateFormat.format(date);
                    String[] timeArray = tempString[6].split(":", 3);
                    time = tempString[5] + "/" + dateOut + "/" + tempString[8] + " " + timeArray[0] + ":" + timeArray[1];
                }
                String out = type + "#" + done + "#" + description + "#" + time;
                writer.write(out);
                writer.newLine();
            }
            writer.close();
        } catch (IOException | ParseException e) {
            throw new DukeException();
        }
    }
}
