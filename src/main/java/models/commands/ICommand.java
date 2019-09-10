package models.commands;

import models.tasks.TaskList;

public interface ICommand {
    void execute(TaskList taskList);
}
