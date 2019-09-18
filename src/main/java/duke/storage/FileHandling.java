package duke.storage;

import duke.exceptions.DukeException;
import duke.tasks.Deadline;
import duke.tasks.DoAfter;
import duke.tasks.Event;
import duke.tasks.FixedDuration;
import duke.tasks.Task;
import duke.tasks.Todo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandling {
    private String file;

    public FileHandling(String file) {
        this.file = file;
    }

    private void loadTask(List<String> columns,String input,
                          ArrayList<Task> initialData) throws DukeException {
        int k = 0;
        switch (columns.get(0)) {
        case "T":
            initialData.add(new Todo(columns.get(2)));
            break;
        case "E":
            initialData.add(new Event(columns.get(2), columns.get(3)));
            break;
        case "D":
            initialData.add(new Deadline(columns.get(2), columns.get(3)));
            break;
        case "A":
            initialData.add(new DoAfter(columns.get(2), columns.get(3)));
            break;
        case "F":
            initialData.add(new FixedDuration(columns.get(2),columns.get(3)));
            break;
        default:
            System.out.println("\n     There is an invalid entry in the file. This entry will "
                    + "not be copied to the list:");
            System.out.println("     " + input);
            k = 1;
        }
        if (k == 0) {
            if (columns.get(1).equals("1")) {
                initialData.get(initialData.size() - 1).markAsDone();
            }
        }
    }

    /**
     * This function handles loading data from the file.
     * @return a list that stores the tasks loaded from the file.
     * @throws DukeException when there are errors while handling the file.
     */
    public ArrayList<Task> retrieveData() throws DukeException {

        try {
            FileReader readFile = new FileReader(this.file);
            BufferedReader read = new BufferedReader(readFile);
            ArrayList<Task> initialData = new ArrayList<>();
            String input;
            while ((input = read.readLine()) != null) {
                List<String> columns = Arrays.asList(input.split("\\|"));
                loadTask(columns,input,initialData);
            }
            return initialData;
        } catch (FileNotFoundException obj) {
            throw new DukeException(" Invalid file name/file path. File not found."
                    + "Will make a new file ...");
        } catch (IOException obj) {
            throw new DukeException(" Error while reading data from the file. Will continue "
                   + "with empty list");
        } catch (ArrayIndexOutOfBoundsException obj) {
            throw new DukeException(" Index out of bounds. Probably due to invalid format of storing"
                    + "the tasks");
        }
    }

    /**
     * This function is responsible for saving data from the list into the file.
     * @param storeDataInFile list of tasks that are to be stored in the file.
     * @throws DukeException when there are errors while loading data into the file.
     */
    public void saveData(ArrayList<Task> storeDataInFile) throws DukeException {

        try {
            FileWriter writin = new FileWriter(this.file);
            BufferedWriter outData = new BufferedWriter(writin);
            for (int i = 0; i < storeDataInFile.size(); i++) {
                outData.write(storeDataInFile.get(i).fileOutFormat());
                outData.newLine();
            }
            outData.close();
        } catch (IOException obj) {
            throw new DukeException(" Error occurred while writing data to the file " + obj);
        }
    }
}

