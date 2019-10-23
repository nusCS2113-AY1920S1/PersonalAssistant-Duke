package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.DukeException;
import duke.task.DoAfter;
import duke.task.Recurring;
import duke.task.Deadline;
import duke.task.Tasks;
import duke.task.Event;
import duke.task.ToDo;
import duke.task.FixedDuration;
import duke.task.Period;
/**
 * This class deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {

    private static String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Extracts the tasks from duke.txt file when user opens the programme.
     * Returns back the list of tasks.
     */
    public List<Tasks> getTasksFromDatabase() throws DukeException {
        List<Tasks> userToDoListTask = new ArrayList<>();
        List<String> userToDoListString = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                userToDoListString.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw DukeException.FILE_NOT_FOUND;
        }
        List<String> store = new ArrayList<>();
        for (int i = 0; i < userToDoListString.size(); i += 1) {
            String line = userToDoListString.get(i);
            if (line.trim().isEmpty()) { //ignore empty lines
                continue;
            } else {
                String[] arr = line.split("\\|");
                String type = arr[0].strip();
                String done = arr[1].strip();
                String taskMessage = arr[2].strip();

                Tasks tasks;
                if (type.equals("T")) {
                    tasks = new ToDo(taskMessage, "T");
                } else if (type.equals("D")) {
                    tasks = new Deadline(taskMessage, "D", arr[3].strip());
                } else if (type.equals("E")) {
                    tasks = new Event(taskMessage, "E", arr[3].strip(), arr[4].strip());
                } else if (type.equals("?][E")) {
                    tasks = new Event(taskMessage, "?][E", arr[3].strip(), arr[4].strip());
                } else if (type.equals("A")) {
                    tasks = new DoAfter(taskMessage, "A", arr[3].strip());
                } else if (type.equals("R")){
                    tasks = new Recurring(taskMessage, "R", arr[3].strip());
                } else if (type.equals("F")) {
                    tasks = new FixedDuration(taskMessage, "F", arr[3].strip());
                } else {
                    tasks = new Period(taskMessage, "P", arr[3].strip(), arr[4].strip());
                }
                if (done.equals("✓")) {
                    tasks.setDone(true);
                } else {
                    tasks.setDone(false);
                }
                userToDoListTask.add(tasks); //convert the line to a duke.task and add to the list
            }
        }

        return userToDoListTask;
    }

    /**
     * Saves tasks into duke.txt file.
     */
    public static void saveTask(List<Tasks> userToDoList) throws DukeException {
        File f = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(f);
            for (Tasks task : userToDoList) {
                String taskType = task.getType();
                String line;
                if (taskType == "T") {
                    line = "T | " + task.getStatusIcon() + " | " + task.getDescription();
                } else if (taskType == "D") {
                    line = "D | " + task.getStatusIcon() + " | "
                        + task.getDescription() + " | " + ((Deadline) task).getDate().getStartDateStr();
                } else if (taskType == "E") {
                    line = "E | " + task.getStatusIcon() + " | "
                        + task.getDescription() + " | " + ((Event) task).getDate().getStartDateStr()
                        + " | " + ((Event) task).getDate().getEndDateStr();
                } else if (taskType == "?][E") {
                    line = "?][E | " + task.getStatusIcon() + " | "
                        + task.getDescription() + " | " + ((Event) task).getDate().getStartDateStr()
                        + " | " + ((Event) task).getDate().getEndDateStr();
                } else if (taskType == "R") {
                    line = "R | " + task.getStatusIcon() + " | "
                        + task.getDescription() + " | " + ((Recurring) task).getRecur();
                } else if (taskType == "A") {
                    line = "A | " + task.getStatusIcon() + " | "
                            + task.getDescription() + " | " + ((DoAfter) task).getAfter();
                } else if (taskType == "F") {
                    line = "F | " + task.getStatusIcon() + " | "
                        + task.getDescription() + " | " + ((FixedDuration) task).getDuration();
                } else {
                    line = "P | " + task.getStatusIcon() + " | "
                        + task.getDescription() + " | " + ((Period) task).getDate().getStartDateStr() + " | "
                        + ((Period)task).getDate().getEndDateStr();
                }
                fileWriter.write(line + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

}