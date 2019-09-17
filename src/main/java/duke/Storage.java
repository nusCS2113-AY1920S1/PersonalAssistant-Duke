package duke;

import duke.tasks.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Storage {
    private ArrayList<Task> data = new ArrayList<>();
    private String filepath;

    /**
     * Initialises the 'data' based on previous data
     * from filepath.
     * @param filepath The storage path of the saved data
     * @throws DukeException Exception when file is not found
     */
    public Storage(String filepath) throws DukeException {
        this.filepath = filepath;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "|");
                int count = 1;
                StringBuilder finalOutput = new StringBuilder();
                String currStr;
                boolean isChecked = false;
                int cmdState = 0;

                while (stringTokenizer.hasMoreTokens()) {
                    currStr = stringTokenizer.nextToken();
                    if (count == 1) {
                        switch (currStr) {
                        case "T":
                            finalOutput = new StringBuilder("todo ");
                            cmdState = 1;
                            break;
                        case "D":
                            finalOutput = new StringBuilder("deadline ");
                            cmdState = 2;
                            break;
                        case "E":
                            finalOutput = new StringBuilder("event ");
                            cmdState = 3;
                            break;
                        default:
                        }
                    } else if (count == 2) {
                        if (currStr.equals("✓")) {
                            isChecked = true;
                        }
                    } else if (count == 3) {
                        finalOutput.append(currStr);
                    } else if (count == 4) {
                        switch (cmdState) {
                        case 2:
                            finalOutput.append(" /by ").append(currStr);
                            break;
                        case 3:
                            finalOutput.append(" /at ").append(currStr);
                            break;
                        default:
                        }
                    }
                    count++;
                }
                switch (cmdState) {
                case 1:
                    if (!isChecked) {
                        Parser.runTodo(data, finalOutput.toString(), 1);
                    } else {
                        Parser.runTodo(data, finalOutput.toString(), 2);
                    }
                    break;
                case 2:
                    if (!isChecked) {
                        Parser.runDeadline(data, finalOutput.toString(), 1);
                    } else {
                        Parser.runDeadline(data, finalOutput.toString(), 2);
                    }
                    break;
                case 3:
                    if (!isChecked) {
                        Parser.runEvent(data, finalOutput.toString(), 1);
                    } else {
                        Parser.runEvent(data, finalOutput.toString(), 2);
                    }
                    break;
                default:
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("File not found!");
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
                if (st1.equals("D") || st1.equals("E")) {
                    st4 = task.getExtra();
                }
                String st2;
                st2 = task.getStatusIcon();
                String st3;
                st3 = task.toString().substring(3);
                if (st4 != null && st3.contains(st4)) {
                    st3 = st3.replace(st4, "");
                    st3 = st3.substring(0, st3.length() - 7);
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
