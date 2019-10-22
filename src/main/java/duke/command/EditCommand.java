package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditCommand extends Command {
	String filter;
	String command;

	public EditCommand(String filter, String command) {
		this.filter = filter;
		this.command = command;
	}

	public EditCommand(String command) {
		this("", command);
	}

	//Takes in a string of all the parameters after the edit command
	public HashMap<String, String> getParameters(String rawParameters) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		String[] splitParameters = rawParameters.split("-");
		for(int i = 0; i < splitParameters.length; i++) {
			String[] parameterDetailPair = splitParameters[i].split(" ");
			String p = parameterDetailPair[0];
			String d = parameterDetailPair[1];
			parameters.put(p,d);
		}
		return parameters;
	}

	//Get the index number
	public int getIndexFromCommand(String editCommand) {
		String[] temp = editCommand.split(" ");
		int indexNo = Integer.parseInt(temp[1]);
		return indexNo;
	}

	public int getIndexFromTaskList(String filter, int filteredListIndex, TaskList tasks) {
		int actualIndex = -1;
		int filteredListCounter = 0;
		ArrayList<Task> tempTaskList = new ArrayList<>(); //To search and put the task list here, need to record their index though
		//Implement filter here to search. Search filter then add the task list?

		if (filter == "") {
			actualIndex = filteredListIndex;
		} else {
			tempTaskList = tasks.getList();
			for(int i = 0; i < tempTaskList.size(); i++) {
//				if (filter == tempTaskList.get(i).getfilter()) {
//					filterCounter++;
//				}
				if (filteredListCounter == filteredListIndex) {
					actualIndex = i;
				}
			}
		}
		return actualIndex; //TODO add exception to throw if not found ):
	}

	//method to get the task, replace it with new parameters then replace it.....

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
//		switch(keyword) {
//			case "priority":
//				int index = Integer.parseInt(information.get(0)) - 1;
//				Task t = tasks.get(index);
//				int level = Integer.parseInt(information.get(1));
//				t.setPriority(level);
//				break;
//			default:
//				throw new DukeException("Sorry I don't know what that means :-(");
//		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}
