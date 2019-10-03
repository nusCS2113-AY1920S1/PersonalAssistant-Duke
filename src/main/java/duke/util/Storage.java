package duke.util;

import duke.exceptions.DukeInvalidTimeException;
import duke.exceptions.DukeInvalidTimePeriodException;
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Events;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Storage {

    /**
     * Path to storage data file.
     * Boolean flag to indicate if data file exists.
     *
     */
    private Path path;
    private Path dataPath;

    private boolean dataPathExists;
    private boolean fileExists;

    /**
     * Constructor for storage class.
     *
     */
    public Storage() {
        path = Paths.get("data/dukeData.text");
        dataPath = Paths.get("data/modsData.text");
        setDataPathExists();
        setFileExists();
    }

    /**
     * Reads the stored data file, if it exists
     * and returns the previously stored data as a TaskList.
     * @return TaskList of what was saved in the data file.
     */
    public List<Task> readData() throws DukeInvalidTimeException {
        List<Task> list = new ArrayList<>();
        List<String> lines = Collections.emptyList();

        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line:lines) {
            String[] hold = line.split(Pattern.quote("|"));
            switch (hold[0]) {
                case "E": {
                    Events tempEvents = new Events(hold[1], hold[3]);
                    if (hold[2].equals("1")) {
                        tempEvents.setTaskDone();
                    }
                    list.add(tempEvents);
                    break;
                }
                case "D": {
                    Deadline tempDeadline = new Deadline(hold[1], hold[3]);
                    if (hold[2].equals("1")) {
                        tempDeadline.setTaskDone();
                    }
                    list.add(tempDeadline);
                    break;
                }
                case "R": {
                    RecurringTask tempRecurringTask = new RecurringTask(hold[1], hold[3]);
                    if (hold[2].equals("1")) {
                        tempRecurringTask.setTaskDone();
                    }
                    list.add(tempRecurringTask);
                    break;
                }
                case "T": {
                    Todo tempTodo = new Todo(hold[1]);
                    if (hold[2].equals("1")) {
                        tempTodo.setTaskDone();
                    }
                    list.add(tempTodo);
                    break;
                }
                case "W": {
                    try {
                        DoWithin tempTodo = new DoWithin(hold[1], hold[3], hold[4]);
                        if (hold[2].equals("1")) {
                            tempTodo.setTaskDone();
                        }
                        list.add(tempTodo);
                        break;
                    } catch (DukeInvalidTimePeriodException ex) {
                        break;
                    }
                }
                case "F": {
                    try {
                        FixedDurationTasks tempFixedDuration = new FixedDurationTasks(hold[1], hold[3]);
                        if (hold[2].equals("1")) {
                            tempFixedDuration.setTaskDone();
                        }
                        list.add(tempFixedDuration);
                        break;
                    } catch (DukeInvalidTimeException ex) {
                        break;
                    }
                }
                default: {
                    break;
                }
            }
        }
        return list;
    }

    boolean getFileExits() {
        return fileExists;
    }

    boolean getDataPathExists() {
        return dataPathExists;
    }

    private void setFileExists() {
        fileExists = Files.isRegularFile(path);
    }

    private void setDataPathExists() {
        dataPathExists = Files.isRegularFile(dataPath);
    }


    /**
     * Writes current state of the taskList to data file. Creates the desired
     * file and sets fileExits to true afterwards.
     * @param taskList The current taskList being saved into text file.
     */
    public void writeData(List<Task> taskList) {
        List<String> stringListTask = new ArrayList<>();
        for (Task temp : taskList) {
            stringListTask.add(temp.writingFile());
        }
        try {
            if (fileExists) {
                Files.write(path, stringListTask, StandardCharsets.UTF_8);
            } else {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                setFileExists();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper function to write nusMods data to file.
     * @param data List of String of data from nusMods.
     */
    public void writeModsData(List<String> data) {
        try {
            if (dataPathExists) {
                Files.write(dataPath, data, StandardCharsets.UTF_8);
            } else {
                Files.createDirectories(dataPath.getParent());
                Files.createFile(dataPath);
                setDataPathExists();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
