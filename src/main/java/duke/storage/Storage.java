package duke.storage;

import java.io.*;
import java.util.ArrayList;

import duke.exceptions.DukeException;
import duke.tasks.*;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from and write to
 * @author Ivan Andika Lie
 */
public class Storage {
    private String line = null;
    private File file = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;


    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     *     to be added in that TaskList
     * @return the ArrayList of task loaded from the file
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public ArrayList<Task> load(Schedule schedule) throws DukeException {
        ArrayList<Task> tasks = new ArrayList<>();
        String sep = System.getProperty("file.separator");
        file = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                            + sep + "Data" + sep + "duke.txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            throw new DukeException("Unable to access file");
        }
        try {
            while((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, tasks, schedule);
        }
        bufferedReader.close();
        } catch(FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
        return tasks;
    }

    /**
     * This function acts as a line by line parser from the text file which is used to load a particular type of task
     * @param line the line input from the input file
     * @param tasks the task arraylist that will store the tasks from the input file
     */
    private static void loadFile(String line, ArrayList<Task> tasks, Schedule schedule) {
        String[] splitLine = line.split(" \\| ");
        String taskType = splitLine[0];
        String subtypes = splitLine[1];
        boolean isDone = splitLine[2].equals("1");
        String description = splitLine[3];

        String timeFrame = "";
        if (taskType.equals("D") || taskType.equals("E")) {
            timeFrame = splitLine[4];
        }
        if (taskType.equals("T")) {
            if (subtypes.trim().length() == 0) {
                loadToDo(tasks, isDone, subtypes, description);
            }
            if (subtypes.contains("P")) {
                loadToDo(tasks, isDone, subtypes, description, splitLine[4], splitLine[5]);
            }
            if (subtypes.contains("F")) {
                loadToDo(tasks, isDone, subtypes, description, splitLine[4]);
            }
        } else if (taskType.equals("D")) {
            loadDeadline(tasks, description, timeFrame, isDone, schedule);
        } else if (taskType.equals("E")) {
            loadEvent(tasks, description, timeFrame, isDone, schedule);
        }

    }

    /**
     * This function will load a todo line and push it to the task arraylist
     * @param tasks the task arraylist that will store the tasks from the input file
     * @param description the task specified
     * @param isDone whether the todo task is done
     */
    //TODO: make such that the loadFile only need to call one function only
    private static void loadToDo(ArrayList<Task> tasks, boolean isDone, String Subtypes, String...description) {
        if (Subtypes.trim().length() == 0) {
            ToDo newToDo = new ToDo(description[0]);
            if (isDone) {
                newToDo.markAsDone();
            }
            tasks.add(newToDo);
        }
        if (Subtypes.contains("P")) {
            ToDo newToDo = new ToDo(description[0], description[1], description[2]);
            if (isDone) {
                newToDo.markAsDone();
            }
            tasks.add(newToDo);
        }
        if (Subtypes.contains("F")) {
            ToDo newToDo = new ToDo(description[0], description[1]);
            if (isDone) {
                newToDo.markAsDone();
            }
            tasks.add(newToDo);
        }
    }

    /** This function will load a deadline line and push it to the task arraylist
     * @param tasks the task arraylist that will store the tasks from the input file
     * @param description the task specified
     * @param by the deadline of the deadline task
     * @param isDone whether the deadline task is done
     */
    private static void loadDeadline(ArrayList<Task> tasks, String description, String by, boolean isDone, Schedule schedule) {
        boolean toAdd;
        Deadline newDeadline = new Deadline(description, by);
        if (isDone) {
            newDeadline.markAsDone();
        }
        toAdd = schedule.update(newDeadline);
        if (toAdd) {
            tasks.add(newDeadline);
        }
    }

    /**
     * This function will load a event line and push it to the task arraylist
     * @param tasks the task arraylist that will store the tasks from the input file
     * @param description the event specified
     * @param duration the duration of the event
     * @param isDone
     */
    private static void loadEvent(ArrayList<Task> tasks, String description, String duration, boolean isDone, Schedule schedule) {
        boolean toAdd;
        Event newEvent = new Event(description, duration);
        if (isDone) {
            newEvent.markAsDone();
        }
        toAdd = schedule.update(newEvent);
        if (toAdd) {
            tasks.add(newEvent);
        }
    }

    /**
     * This is a function that will update the input/output file from the current arraylist of tasks
     * @param tasks the task arraylist that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(ArrayList<Task> tasks) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < tasks.size(); i++) {
                Task currentTask = tasks.get(i);
                String currentLine = currentTask.toString();
                if (i > 0) {
                    bufferedWriter.newLine();
                }
                String status = "0";
                if (currentTask.getisDone()) {
                    status = "1";
                }
                String Subtypes = currentTask.getSubtype();
                bufferedWriter.write(currentTask.getType() + " | " + Subtypes + " | "
                        + status + " | " + currentTask.getDescription());
                if ((currentTask.getType()).equals("T")) {
                    if (Subtypes.contains("P")) {
                        String data[] = currentLine.split("From: ", 2);
                        String timeFrame[] = data[1].split(" to ", 2);
                        bufferedWriter.write(" | " + timeFrame[0] + " | " + timeFrame[1].substring(0, timeFrame[1].length() - 1));
                    }
                    if (Subtypes.contains("F")) {
                        String timeFrame = (currentLine.split("needs: ", 2))[1];
                        bufferedWriter.write(" | " + timeFrame.substring(0, timeFrame.length() - 1));
                    }
                }
                if ((currentTask.getType()).equals("E")) {
                    String timeFrame = (currentLine.split("at: ", 2))[1];
                    bufferedWriter.write(" | " + timeFrame.substring(0, timeFrame.length() - 1));
                }
                else if ((currentTask.getType()).equals("D")) {
                    String timeFrame = (currentLine.split("by: ", 2))[1];
                    bufferedWriter.write(" | " + timeFrame.substring(0, timeFrame.length() - 1));
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
