package command;

import task.TaskList;
import ui.Ui;
import util.Storage;
import exception.DukeException;

public interface Command {

	boolean isExit();

	void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;
}