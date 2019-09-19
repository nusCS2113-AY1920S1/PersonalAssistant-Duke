package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import duke.exceptions.BadInputException;
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
    public TaskList load() {
        ArrayList<Task> savedList = new ArrayList<Task>();
        int listIndex = 0;
        File f = new File(filePath); //Create a File for the given file path

        try {
            Scanner s = new Scanner(f); //Create a Scanner using the File as the source

            while (s.hasNext()) {
                String itemRaw = s.nextLine();
                String[] item = itemRaw.split("/", 5);

                switch (item[1]) {
                case "T":
                    savedList.add(new Todo(Integer.parseInt(item[0]), item[3]));

                    break;
                case "D":
                    savedList.add(new Deadline(Integer.parseInt(item[0]), item[3], item[4]));

                    break;
                case "E":
                    savedList.add(new Event(Integer.parseInt(item[0]), item[3], item[4]));

                    break;
                default:
                    //TODO: throw an exception
                    System.out.println("An exception will be thrown here eventually.");
                    break;
                }

                if (item[2].equals("1")) {
                    savedList.get(savedList.size() - 1).markAsDone();
                }
            }
            listIndex = savedList.get(savedList.size() - 1).getTaskIndex() + 1;
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        } catch (BadInputException e) {
            System.out.println("Save file format wrong. Please fix it manually or use a new list.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }

        return new TaskList(savedList, listIndex); //Returns a TaskList.
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
