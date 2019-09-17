package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.services.task.TaskList;

public class RemindersCommand extends Command {
    /**
     * Input by user.
     */
    private String userInput;

    /**
     * Command.
     */
    private String command;

    /**
     * Number of tasks.
     */
    private int size;

    /**
     * Create add command.
     * @param userInput input by user.
     * @param command type of command.
     * @param size number of tasks.
     */
    public RemindersCommand(String userInput, String command, int size) {
        this.userInput = userInput;
        this.command = command;
        this.size = size;
    }

    @Override
    public void execute(TaskList taskList) {
        taskList.showSortedReminders();
    }
}
