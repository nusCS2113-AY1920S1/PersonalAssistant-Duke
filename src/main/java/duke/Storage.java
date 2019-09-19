package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import duke.items.Task;
import duke.items.Todo;
import duke.items.Deadline;
import duke.items.Event;
/**
 * Handles reading and writing the tasklist to file.
 */

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Converts save file details into Tasks.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> savedList = new ArrayList<>();

        File f = new File(filePath); //Create a File for the given file path

        try {
            Scanner s = new Scanner(f); //Create a Scanner using the File as the source
            while (s.hasNext()) {
                String itemRaw = s.nextLine();
                String[] item = itemRaw.split("/", 4);

                switch (item[0]) {
                case "T":
                    savedList.add(new Todo(item[2]));

                    break;
                case "D":
                    savedList.add(new Deadline(item[2], item[3]));

                    break;
                case "E":
                    savedList.add(new Event(item[2], item[3]));

                    break;
                default:
                    //TODO: throw an exception
                    System.out.println("An exception will be thrown here eventually.");
                    break;
                }

                if (item[1].equals("1")) {
                    savedList.get(savedList.size() - 1).markAsDone();  //Refers to the last added item.
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        }

        return savedList; //Returns an array of Task objects
    }

    private void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Saves existing TaskList to a text file.
     */
    public void save(ArrayList<Task> taskList) {
        StringBuilder tasksToSave = new StringBuilder();
        int max = taskList.size();
        for (int i = 0; i < max; i++) { //index starts from 0.
            tasksToSave.append(taskList.get(i).saveDetailsString()).append(System.lineSeparator());
        }

        String taskListToSave = tasksToSave.toString();
        try {
            writeToFile(taskListToSave);
        } catch (IOException e) {
            System.out.println("Something went wrong saving the file :(");
        }
    }
}
