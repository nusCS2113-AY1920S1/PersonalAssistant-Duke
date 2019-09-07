package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.services.task.TaskList;

public abstract class Command {

    /**
     * Create new command.
     */
    Command() {
    }

    /**
     * Execute command action.
     * @param taskList list of tasks.
     */
    public abstract void execute(TaskList taskList);
}
