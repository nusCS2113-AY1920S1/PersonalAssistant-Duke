package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles reading and writing tasks from and to disk.
 */
public class Storage {
    private final String path;

    public Storage(String path) {
        this.path = path;
    }

    /**
     * Reads tasks from this instance's path, one task per line during startup of Duke to
     * reload last saved state.
     *
     * @return An ArrayList of strings, each string representing a task.
     * @throws FileNotFoundException If the file does not exist or is otherwise inaccessible.
     */
    public ArrayList<String> readFile() throws FileNotFoundException {
        ArrayList<String> out = new ArrayList<>();
        File f = new File(path);
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            out.add(sc.nextLine());
        }
        return out;
    }

    /**
     * Writes the given string representations of tasks to this instance's path.
     * Happens when a modification to any task occurs.
     *
     * @param lines The lines to be written, produced by TaskList's export() method.
     * @throws IOException If an error occurs while writing the tasks.
     */
    public void writeFile(ArrayList<String> lines) throws IOException {
        new File("data/").mkdirs(); //creates directory if it does not exist
        FileWriter writer = new FileWriter(path);
        for (String line : lines) {
            writer.write(line + "\n");
        }
        writer.close();
    }

}
