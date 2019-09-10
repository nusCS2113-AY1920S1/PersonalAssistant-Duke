import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Converts the file containing a text version of the stored tasks into an
 * array of Task instances representing the Tasks created thus far.
 */

public class Storage {
    protected ArrayList<Task> items = new ArrayList<Task>();
    protected File file;

    /**
     * Constructor for the Storage class. Accesses the file path and runs the method
     * readFromFile() to convert the text representation of tasks into an actual array of Tasks.
     * @param filename the file path where the text version of tasks are stored.
     */
    public Storage(String filename) throws FileNotFoundException, ParseException {
        file = new File(filename);
        readFromFile();
    }

    /**
     * Obtains the variable file and converts the text representation of tasks created thus far
     * into actual instances of Task, and saves it to the ArrayList items.
     * @throws FileNotFoundException if file path does not exist
     * @throws ParseException if any saved data is un-parsable
     */
    public void readFromFile() throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split("`");
            boolean isDone = (Integer.parseInt(line[2]) == 1);
            if (line[0].equals("T")) {
                Todo newTodo = new Todo(line[1], isDone);
                items.add(newTodo);
            }
            else if (line[0].equals("D")) {
                Deadline newDeadline = new Deadline(line[1], line[3], isDone);
                items.add(newDeadline);
            }
            else if (line[0].equals("E")) {
                Event newEvent = new Event(line[1], line[3], isDone);
                items.add(newEvent);
            }
        }
    }

    /**
     * Takes in the array of Tasks thus far, converts it to text format and saves it
     * in the provided file path.
     * @param tasks the tasks created thus far.
     * @throws ParseException if any Task data is un-parsable
     * @throws IOException if there is an error in writing data to the file.
     */
    public void saveToFile(ArrayList<Task> tasks) throws ParseException, IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, false));
        for (int i = 0; i < tasks.size(); i++) {
            Task thisTask = tasks.get(i);
            String line = thisTask.getType() + "`" + thisTask.getDescription() + "`";
            line += (thisTask.getDoneStatus() == true ? 1 : 0);
            if (thisTask.getType() == 'D' || thisTask.getType() == 'E') {
                line += "`";
                line += thisTask.getDateToSave();
            }
            fileWriter.write(line);
            fileWriter.newLine();
        }
        fileWriter.close();
    }

    /**
     * Returns array of Tasks that was created from text format.
     * @return the aforementioned array.
     */
    public ArrayList<Task> getItems() {
        return items;
    }
}