import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a storage used to load {@link Task}s from a text {@link File} and store {@link Task}s in it
 */
public class Storage {
    private Path path;
    private List<String> contentSoFar;
    private List<Task> tasks;

    public Storage(String filePath) {
        path = Paths.get(filePath);
        tasks = new ArrayList<>();
    }

    /**
     * Returns an {@link ArrayList} of {@link Task}s read from the text file indicated by the {@link Path}
     * @return List</Task> a list of all the tasks read from the hard disc
     * @throws DukeException
     */
    public List<Task> load() throws DukeException {

        try {
            contentSoFar = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));// getting the data from the hard disk until now
        } catch (IOException e) {
            File file = new File("data\\tasks.txt");
            try {
                File folder = new File("data");
                folder.mkdir();
                file.createNewFile();
                contentSoFar = new ArrayList<>();
            } catch (IOException ex) {
                throw new DukeException("Could not create a tasks.txt file in the specified directory");
            }

        }
        for (String next : contentSoFar) {
            String[] words = next.split("\\|");// splitting each line to extract the task type - words[0], done or not words-[1], description- words[2] and possibly due date words[3]
            switch (words[0]) {
                case "T":
                    tasks.add(new Todo(words[2]));
                    if (words[1].equals("1"))
                        tasks.get(tasks.size() - 1).markAsDone();
                    break;
                case "D":
                    tasks.add(new Deadline(words[2], words[3]));
                    if (words[1].equals("1"))
                        tasks.get(tasks.size() - 1).markAsDone();
                    break;
                default:
                    tasks.add(new Event(words[2], words[3]));
                    if (words[1].equals("1"))
                        tasks.get(tasks.size() - 1).markAsDone();
                    break;
            }
        }
        return tasks;
    }

    /**
     * Returns the {@link Path} that holds the directory used for I/O
     * @return Path specifies the directory of the text {@link File} used for writing or reading
     */
    public Path getPath() {
        return path;
    }

    /**
     * Used to change the content in the text file stored in the hard disc so that it matches the content in the current {@link TaskList}, namely, it changes the specific {@link Task} indicated by the taskNb
     * @param taskNb positive integer indicating the number of the {@link Task} in the {@link TaskList} to be updated
     * @throws DukeException if the taskNb is invalid or there was an I/O Exception
     */
    public void changeContent(int taskNb) throws DukeException {
        if(taskNb<0)
            throw new DukeException("The task number should be positive, task number entered was: "+ taskNb);
        try {
            contentSoFar = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            contentSoFar.set(taskNb, tasks.get(taskNb).printInFile()); // changing the file content
            Files.write(path, contentSoFar, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException("Error while reading/writing in the duke.txt file");
        }
    }

    /**
     * Used to add a {@link Task} by writing to the text {@link File} on the hard disc
     * @param task {@link Task} to be written
     * @throws IOException
     */
    public void addCommandInFile(String task) throws IOException {
        contentSoFar = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        contentSoFar.add(task);
        Files.write(path, contentSoFar, StandardCharsets.UTF_8);
    }

}
