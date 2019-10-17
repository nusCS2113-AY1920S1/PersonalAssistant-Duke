package duke.util;

import duke.exceptions.ModInvalidTimeException;
import duke.exceptions.ModInvalidTimePeriodException;

import duke.modules.Cca;
import duke.modules.Deadline;
import duke.modules.DoWithin;
import duke.modules.Events;
import duke.modules.FixedDurationTask;
import duke.modules.RecurringTask;
import duke.modules.Task;
import duke.modules.Todo;
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

    private boolean dataPathExists = false;
    private boolean fileExists = false;

    /**
     * Default Constructor for storage class.
     *
     */
    public Storage() {
        path = Paths.get("data/dukeData.text");
        setFileExists();
    }

    /**
     * Overloaded Constructor for storage class, specifying the data path as String.
     */
    public Storage(String filePath) {
        dataPath = Paths.get(filePath);
        setDataPathExists();
    }

    public void setDataPath(Path dataPath) {
        this.dataPath = dataPath;
        setDataPathExists();
    }

    /**
     * Reads the stored data file, if it exists
     * and returns the previously stored data as a TaskList.
     * @return TaskList of what was saved in the data file.
     */
    public List<Task> readData() throws ModInvalidTimeException, ModInvalidTimePeriodException {
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
                    DoWithin tempTodo = new DoWithin(hold[1], hold[3], hold[4]);
                    if (hold[2].equals("1")) {
                        tempTodo.setTaskDone();
                    }
                    list.add(tempTodo);
                    break;
                }
                case "F": {
                    FixedDurationTask tempFixedDuration = new FixedDurationTask(hold[1], hold[3]);
                    if (hold[2].equals("1")) {
                        tempFixedDuration.setTaskDone();
                    }
                    list.add(tempFixedDuration);
                    break;
                }
                case "C": {
                    Cca tempTodo = new Cca(hold[1], hold[3], hold[4], hold[5]);
                    if (hold[2].equals("1")) {
                        tempTodo.setTaskDone();
                    }
                    list.add(tempTodo);
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return list;
    }

    public boolean getFileExits() {
        return fileExists;
    }

    public boolean getDataPathExists() {
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
            if (!fileExists) {
                makeFile(path);
                setFileExists();
            }
            Files.write(path, stringListTask, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeFile(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

    /**
     * Helper function to write nusMods data to file.
     * @param data List of String of data from nusMods.
     */
    public void writeModsData(List<String> data) {
        try {
            if (!dataPathExists) {
                makeFile(dataPath);
                setDataPathExists();
            }
            Files.write(dataPath, data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
