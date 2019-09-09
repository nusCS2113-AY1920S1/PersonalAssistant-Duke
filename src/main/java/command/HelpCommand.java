package command;

import ui.Ui;
import util.Storage;
import task.TaskList;

public class HelpCommand implements Command{

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		ui.showHelp();
	}
}