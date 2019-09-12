package Duke.Util;

import Duke.Tasks.Deadline;
import Duke.Tasks.Events;
import Duke.Tasks.Task;
import Duke.Tasks.Todo;

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
     * Path to storage data file
     * Boolean flag to indicate if data file exists
     *
     */
    private Path path;
    private boolean fileExists;

    public Storage() {
        path = Paths.get("data/dukeData.text");
        fileExists = Files.isRegularFile(path);
    }

    boolean getFileExits() {
        return fileExists;
    }

    private void setFileExists() {
        fileExists = Files.isRegularFile(path);
    }

    /**
     * Writes current state of the taskList to data file. Creates the desired
     * file and sets fileExits t
     * @param taskList The current taskList being saved into text file
     */
    public void writeData(List<Task> taskList) {
        List<String> store = new ArrayList<>();
        for (Task temp : taskList) {
            store.add(temp.writingFile());
        }
        try {
            if (!fileExists) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                setFileExists();
            }
            Files.write(path, store, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the stored data file, if it exists
     * and returns the previously stored data as a TaskList
     * @return List<Task> Updated state of taskList
     */
    List<Task> readData() {
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
                case "E":
                    Events t_events = new Events(hold[1], hold[3]);
                    if (hold[2].equals("1")) {
                        t_events.setTaskDone();
                    }
                    list.add(t_events);
                    break;
                case "D":
                    Deadline t_deadline = new Deadline(hold[1], hold[3]);
                    if (hold[2].equals("1")) {
                        t_deadline.setTaskDone();
                    }
                    list.add(t_deadline);
                    break;
                case "T":
                    Todo t_todo = new Todo(hold[1]);
                    if (hold[2].equals("1")) {
                        t_todo.setTaskDone();
                    }
                    list.add(t_todo);
                    break;
            }
        }
        return list;
    }

}
