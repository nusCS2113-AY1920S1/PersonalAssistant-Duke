package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.services.task.TaskList;

public class ListCommand extends Command {

    /**
     * Create delete command.
     */
    public ListCommand() {
    }

    /**
     * Execute command.
     * @param taskList list of tasks.
     */
    @Override
    public void execute(TaskList taskList) {
        taskList.showAll();
    }
}
