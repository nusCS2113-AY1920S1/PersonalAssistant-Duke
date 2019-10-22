package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import java.util.ArrayList;
import java.util.Optional;

public class EditCommand extends Command {
    Optional<String> filter;
    String command;

    public EditCommand(Optional<String> filter, String command) {
        this.filter = filter;
        this.command = command;
    }

    //Takes in a string of all the parameters after the edit command
    public String[] getParameters(String rawParameters) throws DukeException {
        String[] splitParameters = rawParameters.split("-");
        if (splitParameters.length == 1) {
            throw new DukeException("Please enter something for me to edit!");
        }
        return splitParameters;
    }

    //Get the index number
    public int getIndexFromCommand(String editCommand) throws DukeException {
        String[] temp = editCommand.split(" ");
        try {
            int indexNo = Integer.parseInt(temp[0]) - 1;
            return indexNo;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index");
        }
    }

    public int getIndexFromTaskList(Optional<String> filter, int filteredListIndex, TaskList tasks) throws DukeException {
        int actualIndex = 0;
        boolean isIndexFound = false;
        ArrayList<Task> tempTaskList = tasks.getList(); //different kind of search? & convoluted way of search?
        if (filteredListIndex > tempTaskList.size()) {
            isIndexFound = false;
        } else if (filter.isPresent()) {
            int filteredListCounter = 1;
            for (int i = 0; i < tempTaskList.size(); i++) {
                if (filteredListCounter == filteredListIndex) {
                    isIndexFound = true;
                    actualIndex = i;
                }
				if (filter == tempTaskList.get(i).getFilter()) {
					filteredListCounter++;
				}
            }
        } else {
        	isIndexFound = true;
            actualIndex = filteredListIndex;
        }

        if (isIndexFound) {
            return actualIndex;
        } else {
        	throw new DukeException("Please enter a valid index");
		}
    }

    public String[] getKeywordAndEditField(String param) throws DukeException {
        String[] keywordAndEditArray = param.split(" ");
        if (keywordAndEditArray.length == 1) {
            throw new DukeException("Please enter the edit you wish to make");
        } else {
            return keywordAndEditArray;
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            String[] parameters = getParameters(command);
            int commandIndex = getIndexFromCommand(command);
            int taskListIndex = getIndexFromTaskList(filter, commandIndex, tasks);
            for (int i = 1; i < parameters.length; i++) {
                String[] p = getKeywordAndEditField(parameters[i]);
                String keyword = p[0];
                String editField = p[1];
                Task t = tasks.get(taskListIndex);
                switch (keyword) {
                    case "description":
                        t.setDescription(editField);
                        break;
                    case "priority":
                        int priorityLevel = Integer.parseInt(editField);
                        t.setPriority(priorityLevel);
                        break;
                    default:
                        throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what field you are trying to edit!");
                }
            }
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
