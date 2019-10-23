package duke.storage;

import duke.exception.DukeException;
import duke.task.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage extends Storage<Task> {
    /**
     * The constructor method for Storage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     */
    public TaskStorage(String fp) {
        super(fp);
    }

    @Override
    List<Task> generate() throws DukeException {
        for (String next : contentSoFar) {
            //splitting each line to extract the task:
            //type - words[0], done or not - words[1], description - words[2], and more.
            String[] words = next.split("\\|");
            switch (words[0]) {
                case "T":
                    entries.add(new Todo(words[2]));
                    if (words[1].equals("1")) {
                        entries.get(entries.size() - 1).markAsDone();
                    }
                    break;
                case "D":
                    entries.add(new Deadline(words[2], words[3]));
                    if (words[1].equals("1")) {
                        entries.get(entries.size() - 1).markAsDone();
                    }
                    break;
                case "E":
                    entries.add(new Event(words[2], words[3]));
                    if (words[1].equals("1")) {
                        entries.get(entries.size() - 1).markAsDone();
                    }
                    break;
                case "P":
                    entries.add(new DoWithinPeriodTasks(words[2], words[3], words[4]));
                    if (words[1].equals("1")) {
                        entries.get(entries.size() - 1).markAsDone();
                    }
                    break;
                default:
                    throw new DukeException("Error in extracting tasks from file.");
            }
        }
        return entries;
    }



}

