package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;
import java.text.ParseException;

import static duke.common.Messages.COMMAND_SNOOZE;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.MESSAGE_FOLLOWUP_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_LIST;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EVENT;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_DATE;
import static duke.common.Messages.ERROR_MESSAGE_DEADLINE;

import java.text.SimpleDateFormat;

public class SnoozeCommand extends Command {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy HHmm");

    /**
     * Constructor for class SnoozeCommand.
     * @param userInput String containing input command from user
     */
    public SnoozeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Validate that user inputs an integer value for the index.
     * @param input String containing integer input from user for the index
     * @return true if the user inputs an integer and false otherwise
     */
    private static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Processes the delete command to delete task in the task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     *                      or user inputs an invalid index or the list of tasks is empty
     */

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInput.trim().equals(COMMAND_SNOOZE)) {
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(6) == ' ') {
            String description = userInput.trim().split("\\s", 2)[1];
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > taskList.getSize() || index <= 0) {
                    if (taskList.getSize() == 0) {
                        throw new DukeException(ERROR_MESSAGE_EMPTY_LIST);
                    } else {
                        throw new DukeException(ERROR_MESSAGE_INVALID_INDEX + taskList.getSize() + ".");
                    }
                } else {
                    taskList.snoozeTask(index);
                    if (description.contains(" /by ")) {
                        String details = taskList.getTask(index - 1).getDescription();
                        String date = description.trim().split(" /by ", 2)[1];
                        if (details == null || date == null) {
                            throw new DukeException(ERROR_MESSAGE_DEADLINE);
                        } else {
                            if (isParseDate(date)) {
                                taskList.addDeadlineTask(details, date);
                                storage.saveFile(taskList);
                            } else {
                                throw new DukeException(ERROR_MESSAGE_INVALID_DATE);
                            }
                        }
                    } else if (description.contains(" /at ")) {
                        String details = description.trim().split(" /at ", 2)[0];
                        String date = description.trim().split(" /at ", 2)[1];
                        if (details == null || date == null) {
                            throw new DukeException(ERROR_MESSAGE_EVENT);
                        } else {
                            taskList.addEventTask(details, date);
                            storage.saveFile(taskList);
                        }
                    } else {
                        description = userInput.trim().split("\\s", 2)[1];
                        taskList.addTodoTask(description);
                        storage.saveFile(taskList);
                    }
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    /**
     * Validate the format of the input date.
     * @param dateStr String containing user input date and time
     * @return true if user input the correct format for the date and time and false otherwise
     */
    private static boolean isParseDate(String dateStr) {
        try {
            simpleDateFormat.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}