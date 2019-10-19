package javacake.storage;

import javacake.Parser;
import javacake.exceptions.DukeException;
import javacake.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Storage {
    private int stringBuffer = 7;
    private ArrayList<Task> data = new ArrayList<>();
    private String filepath;
    private TaskType dataType;

    public enum TaskType {
        TODO, DEADLINE, TODO_DAILY, TODO_WEEKLY, TODO_MONTHLY
    }

    public Storage() {

    }

    /**
     * Initialises the 'data' based on previous data
     * from filepath.
     * @param filepath The storage path of the saved data
     * @throws DukeException Exception when file is not found
     */
    public Storage(String filepath) throws DukeException {
        this.filepath = filepath;
        try {
            File file = new File(filepath);
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                file.createNewFile();
            } catch (IOException e) {
                throw new DukeException("Failed to create new file");
            }

            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "|");
                int count = 1;
                StringBuilder finalOutput = new StringBuilder();
                String currStr;
                boolean isChecked = false;

                while (stringTokenizer.hasMoreTokens()) {
                    currStr = stringTokenizer.nextToken();
                    if (count == 1) {
                        switch (currStr) {
                        case "T":
                            finalOutput = new StringBuilder("todo ");
                            this.dataType = TaskType.TODO;
                            break;
                        case "D":
                            finalOutput = new StringBuilder("deadline ");
                            this.dataType = TaskType.DEADLINE;
                            break;
                        case "d":
                            finalOutput = new StringBuilder("todo ");
                            this.dataType = TaskType.TODO_DAILY;
                            break;
                        case "w":
                            finalOutput = new StringBuilder("todo ");
                            this.dataType = TaskType.TODO_WEEKLY;
                            break;
                        case "m":
                            finalOutput = new StringBuilder(("todo "));
                            this.dataType = TaskType.TODO_MONTHLY;
                            break;
                        default:
                        }
                    } else if (count == 2 && currStr.equals("✓")) {
                        isChecked = true;
                    } else if (count == 3) {
                        finalOutput.append(currStr);
                    } else if (count == 4) {
                        switch (this.dataType) {
                        case DEADLINE:
                            finalOutput.append(" /by ").append(currStr);
                            break;
                        case TODO_DAILY:
                            finalOutput.append(" /daily ").append(currStr);
                            break;
                        case TODO_WEEKLY:
                            finalOutput.append(" /weekly ").append(currStr);
                            break;
                        case TODO_MONTHLY:
                            finalOutput.append(" /monthly ").append(currStr);
                            break;
                        default:
                        }
                    }
                    count++;
                }

                switch (this.dataType) {
                case TODO:
                    if (!isChecked) {
                        Parser.runTodo(data, finalOutput.toString(), Parser.TaskState.NOT_DONE);
                    } else {
                        Parser.runTodo(data, finalOutput.toString(), Parser.TaskState.DONE);
                    }
                    break;
                case DEADLINE:
                    if (!isChecked) {
                        Parser.runDeadline(data, finalOutput.toString(), Parser.TaskState.NOT_DONE);
                    } else {
                        Parser.runDeadline(data, finalOutput.toString(), Parser.TaskState.DONE);
                    }
                    break;
                case TODO_DAILY:
                    if (!isChecked) {
                        Parser.runRecurring(data, finalOutput.toString(), Parser.TaskState.NOT_DONE, "daily");
                    } else {
                        Parser.runRecurring(data, finalOutput.toString(), Parser.TaskState.DONE, "daily");
                    }
                    break;
                case TODO_WEEKLY:
                    if (!isChecked) {
                        Parser.runRecurring(data, finalOutput.toString(), Parser.TaskState.NOT_DONE, "weekly");
                    } else {
                        Parser.runRecurring(data, finalOutput.toString(), Parser.TaskState.DONE, "weekly");
                    }
                    break;
                case TODO_MONTHLY:
                    if (!isChecked) {
                        Parser.runRecurring(data, finalOutput.toString(), Parser.TaskState.NOT_DONE, "monthly");
                    } else {
                        Parser.runRecurring(data, finalOutput.toString(), Parser.TaskState.DONE, "monthly");
                    }
                    break;
                default:
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new DukeException("Failed to close reader");
        }
    }

    /**
     * Loads the currently initialised ArrayList of Tasks.
     * @return ArrayList of Tasks that has been initialised
     */
    public ArrayList<Task> load() {
        return data;
    }

    /**
     * Writes current taskList onto the save file.
     * @param tasks ArrayList of Tasks needing to be written
     *              onto the save file
     * @throws DukeException when no file is found
     */
    public void write(ArrayList<Task> tasks) throws DukeException {
        try {
            PrintWriter out = new PrintWriter(filepath);
            for (Task task : tasks) {
                String st1;
                st1 = task.toString().substring(1, 2);
                String st4 = null;
                //Appends extra task details for all task types excent 'Todo'
                if (st1.equals("D") || st1.equals("E") || st1.equals("d") || st1.equals("m") || st1.equals("w")) {
                    st4 = task.getExtra();
                }
                String st2;
                st2 = task.getStatusIcon();
                String st3;
                st3 = task.toString().substring(3);
                if (st4 != null && st3.contains(st4)) {
                    st3 = st3.replace(st4, "");
                    //STRING_BUFFER removes the " (by: )" / " (at: )" from st3
                    st3 = st3.substring(0, st3.length() - stringBuffer);
                }
                StringBuilder str = new StringBuilder();
                str.append(st1);
                str.append("|");
                str.append(st2);
                str.append("|");
                str.append(st3);
                if (st4 != null) {
                    str.append("|");
                    str.append(st4);
                }
                out.println(str.toString());
            }
            out.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("No file found");
        }
    }
}
