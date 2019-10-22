package seedu.duke.task;

import seedu.duke.Duke;
import seedu.duke.CommandParser;
import seedu.duke.common.command.Command;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.entity.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

/**
 * Interacts with the file storing the task information.
 */
public class TaskStorage {
    private static String getSaveFileDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "task.txt";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "task.txt";
        } else {
            dir = "." + File.separator + "task.txt";
        }
        return dir;
    }

    /**
     * This function clears the content of the file and write all the information of the tasks in the task
     * list to that file. This file follows similar structure as the user input and can be used to
     * re-construct the task list later.
     *
     * @param taskList the list of task that is to be written to the file
     */
    public static void saveTasks(TaskList taskList) {
        FileOutputStream out;
        try {
            String dir = getSaveFileDir();
            File file = new File(dir);
            file.createNewFile();
            out = new FileOutputStream(file, false);
            out.write(constructTaskListFileString(taskList).getBytes());
            out.close();
        } catch (IOException e) {
            Duke.getUI().showError("Write to output file IO exception!");
        }
    }

    private static String constructTaskListFileString(TaskList taskList) {
        String content = "";
        for (Task task : taskList) {
            content += task.toFileString() + "\n";
        }
        return content;
    }

    /**
     * This function reads the file that is previously saved to re-construct and return the task list from the
     * file information. Note: if any error occurs during the reading or parsing of the file, an empty task
     * list will always be returned for the integrity of data.
     *
     * @return task list re-constructed from the save file
     */
    public static TaskList readTaskFromFile() {
        try {
            String dir = getSaveFileDir();
            File file = new File(dir);
            FileInputStream in = new FileInputStream(file);
            Scanner scanner = new Scanner(in);
            ArrayList<Boolean> doneList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                processTaskFileString(scanner, doneList);
            }
            TaskList taskList = Duke.getModel().getTaskList();
            applyDoneList(doneList, taskList);
            Duke.getModel().updateGuiTaskList();
            Duke.getUI().showMessage("Saved task file successfully loaded... => " + taskList.size());
            in.close();
            new TaskReminderCommand().execute();
            return taskList;
        } catch (FileNotFoundException e) {
            return new TaskList(); //it is acceptable if there is no save file
        } catch (IOException e) {
            Duke.getUI().showError("Read save file IO exception");
            return new TaskList();
        } catch (StorageException | CommandParser.UserInputException e) {
            Duke.getUI().showError(e.getMessage());
            return new TaskList();
        }
    }

    private static void processTaskFileString(Scanner scanner, ArrayList<Boolean> doneList)
            throws StorageException, CommandParser.UserInputException {
        String input = scanner.nextLine();
        prepareDoneList(doneList, input);
        input = input.split(" ", 2)[1];
        Command addCommand = CommandParser.parseCommand("task " + input);
        addCommand.setSilent();
        addCommand.execute();
    }

    private static void prepareDoneList(ArrayList<Boolean> doneList, String input) throws StorageException {
        if (input.startsWith("1")) {
            doneList.add(true);
        } else if (input.startsWith("0")) {
            doneList.add(false);
        } else {
            throw new StorageException("Invalid Save File!");
        }
    }

    private static void applyDoneList(ArrayList<Boolean> doneList, TaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            if (doneList.get(i)) {
                taskList.get(i).markDone();
            }
        }
    }

    /**
     * Exception that belongs to the process of storing and reading of file.
     */
    public static class StorageException extends Exception {
        private String msg;

        /**
         * Instantiates storage exception with a message, which can be later displayed by the UI.
         *
         * @param msg the message of the exception that can be displayed by UI
         */
        public StorageException(String msg) {
            super();
            this.msg = msg;
        }

        /**
         * Converts the exception to string by returning its message.
         *
         * @return message of the exception.
         */
        @Override
        public String getMessage() {
            return msg;
        }
    }
}
