package command;

import ui.Ui;
import util.Storage;
import task.TaskList;;

public class ExitCommand implements Command{

	@Override
	public boolean isExit() {
		return true;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		ui.showExit();
	}
}