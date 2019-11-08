package seedu.duke.task.storage;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.common.storage.StorageHelper;
import seedu.duke.task.TaskList;
import seedu.duke.task.command.TaskAddCommand;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.entity.Task;
import seedu.duke.ui.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Interacts with the file storing the task information.
 */
public class TaskStorage {
    /**
     * This function clears the content of the file and write all the information of the tasks in the task
     * list to that file. This file follows similar structure as the user input and can be used to
     * re-construct the task list later.
     *
     * @param taskList the list of task that is to be written to the file
     */
    public static void saveTasks(TaskList taskList) {
        try {
            Path path = StorageHelper.prepareDataPath("task.txt");
            StorageHelper.saveToFile(path, constructTaskListFileString(taskList));
        } catch (IOException e) {
            UI.getInstance().showError("Write to output file IO exception!");
        }
    }

    private static String constructTaskListFileString(TaskList taskList) {
        String content = "";
        for (Task task : taskList) {
            content += task.toFileString() + System.lineSeparator();
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
            Path taskPath = StorageHelper.prepareDataPath("task.txt");
            processTaskFile(StorageHelper.readLinesFromFile(taskPath));
            TaskList taskList = Model.getInstance().getTaskList();
            UI.getInstance().showMessage("Saved task file successfully loaded... => " + taskList.size());
            new TaskReminderCommand().execute(Model.getInstance());
            return taskList;
        } catch (FileNotFoundException e) {
            return new TaskList(); //it is acceptable if there is no save file
        } catch (IOException e) {
            UI.getInstance().showError("Read save file IO exception");
            return new TaskList();
        } catch (CommandParseHelper.CommandParseException e) {
            UI.getInstance().showError(e.getMessage());
            return new TaskList();
        }
    }

    private static void processTaskFile(List<String> lines) throws
            CommandParseHelper.CommandParseException {
        for (String line : lines) {
            processTaskString(line);
        }
    }

    private static void processTaskString(String line) throws CommandParseHelper.CommandParseException {
        if (!line.contains(" ")) {
            return;
        }
        boolean done = line.split(" ", 2)[0].equals("1");
        String commandString = line.split(" ", 2)[1];
        Command addCommand = CommandParseHelper.parseCommand("task " + commandString,
                CommandParseHelper.InputType.TASK);
        ((TaskAddCommand) addCommand).setDone(done);
        addCommand.setSilent();
        addCommand.execute(Model.getInstance());
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
