package eggventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import eggventory.exceptions.BadInputException;
import eggventory.items.Task;
import eggventory.commands.AddCommand;
import eggventory.enums.CommandType;
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

        TaskList savedList = new TaskList();
        File f = new File(filePath); //Create a File for the given file path

        try {
            Scanner s = new Scanner(f); //Create a Scanner using the File as the source

            while (s.hasNext()) {
                String itemRaw = s.nextLine();
                String[] item = itemRaw.split("/", 4);
                AddCommand cmd = null;

                switch (item[0]) {
                case "T":
                    cmd = new AddCommand(CommandType.TODO, item[2], "");
                    break;

                case "D":
                    cmd = new AddCommand(CommandType.DEADLINE, item[2],item[3]);
                    break;

                case "E":
                    cmd = new AddCommand(CommandType.EVENT, item[2],item[3]);
                    break;

                default:
                    //TODO: throw an exception
                    System.out.println("An exception will be thrown here eventually.");
                    break;
                }

                if (cmd != null) {
                    cmd.execute(savedList);
                }

                // Saved item is a completed task
                if (item[1].equals("1")) {
                    savedList.getTask(savedList.getSize() - 1).markAsDone();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        } catch (BadInputException e) {
            System.out.println("Save file format wrong. Please fix it manually or use a new list.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }

        return savedList; //Returns a TaskList.
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
