package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditCommand extends Command {
	ArrayList<String> information;
	String keyword;

	public EditCommand(String keyword, String... info) {
		this.keyword = keyword;
		information = new ArrayList<>();
		for (String s : info) {
			information.add(s);
		}
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
		switch(keyword) {
			case "priority":
				int index = Integer.parseInt(information.get(0)) - 1;
				Task t = tasks.get(index);
				int level = Integer.parseInt(information.get(1));
				t.setPriority(level);
				break;
			default:
				throw new DukeException("Sorry I don't know what that means :-(");
		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}
