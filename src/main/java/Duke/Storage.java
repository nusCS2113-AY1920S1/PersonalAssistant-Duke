package Duke;

import Duke.Tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Storage {
    private static ArrayList<Task> data = new ArrayList<>();

    /**
     * Initialises the 'data' based on previous data
     * from filepath
     * @param filepath The storage path of the saved data
     * @throws DukeException
     */
    public Storage (String filepath) throws DukeException {
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
                        }
                    }
                    count++;
                }
                switch (cmdState) {
                    case 1:
                        if (!isChecked)
                            Parser.runTodo(data, finalOutput.toString(), 1);
                        else
                            Parser.runTodo(data, finalOutput.toString(), 2);
                        break;
                    case 2:
                        if (!isChecked)
                            Parser.runDeadline(data, finalOutput.toString(), 1);
                        else
                            Parser.runDeadline(data, finalOutput.toString(), 2);
                        break;
                    case 3:
                        if (!isChecked)
                            Parser.runEvent(data, finalOutput.toString(), 1);
                        else
                            Parser.runEvent(data, finalOutput.toString(), 2);
                        break;
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
     * Loads the currently initialised ArrayList of Tasks
     * @return ArrayList of Tasks that has been initialised
     */
    public ArrayList<Task> load() {
        return data;
    }

    /**
     * Writes current taskList onto the save file
     * @param tasks ArrayList of Tasks needing to be written
     *              onto the save file
     * @throws DukeException
     */
    public void write(ArrayList<Task> tasks) throws DukeException {
        try {
            PrintWriter out = new PrintWriter("./data/saved_data.txt");
            for (Task task : tasks) {
                StringBuilder str = new StringBuilder();
                String st1, st2, st3, st4 = null;
                st1 = task.toString().substring(1, 2);
                if (st1.equals("D") || st1.equals("E")) {
                    st4 = task.getExtra();
                }
                st2 = task.getStatusIcon();
                st3 = task.toString().substring(3);
                if (st4 != null && st3.contains(st4)) {
                    st3 = st3.replace(st4, "");
                    st3 = st3.substring(0, st3.length() - 7);
                }
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
