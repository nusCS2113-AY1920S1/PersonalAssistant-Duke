package models.temp.commands;

import models.temp.tasks.TaskList;

public interface ICommand {
    void execute(TaskList taskList);
}
