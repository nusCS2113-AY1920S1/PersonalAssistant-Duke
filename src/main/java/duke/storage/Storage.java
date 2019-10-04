package duke.storage;

import duke.exception.DukeException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the duke.storage of duke.Duke.
 * Stores the content of ExpenseList of duke.Duke in a text file.
 */
public class Storage {
    private File file;

    /**
     * Constructs the duke.storage with given filePath of text file storing the data.
     *
     * @param filePath The filePath of the duke.storage text file.
     */
    public Storage(String filePath) {
        file = new File(filePath);
    }

    /**
     * Creates new duke.storage file if no file exists yet.
     * Reads all strings representing expensesList in duke.storage format in the file and
     * returns the duke.list of strings.
     * Works as the parameter of the constructor of {@code ExpenseList}.
     *
     * @return List of information of stored expensesList in duke.storage format.
     * @throws DukeException If errors occur in loading process.
     */
    public ArrayList<String> load() throws DukeException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ArrayList<String> taskStrings = new ArrayList<String>(100);
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                taskStrings.add(fileReader.nextLine());
            }
            return taskStrings;
        } catch (IOException e) {
            throw new DukeException("☹ OOPS!!! Errors occurred in loading process.");
        }
    }

    /**
     * Updates the duke.storage text file by replacing original content
     * with the given duke.list of strings representing expensesList.
     *
     * @param taskStrings List of strings representing expensesList in duke.storage format.
     * @throws DukeException If errors occur when updating duke.storage.
     */
    public void update(ArrayList<String> taskStrings) throws DukeException {
        try {
            file.delete();
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            for (String line : taskStrings) {
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException("☹ OOPS!!! Errors occurred when updating duke.storage.");
        }
    }

}
