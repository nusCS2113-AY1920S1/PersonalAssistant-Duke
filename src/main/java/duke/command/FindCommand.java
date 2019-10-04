package duke.command;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    private List<String> splitInput;

    public FindCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage)throws DukeException {
        if (splitInput.size() == 1) {
            throw new DukeException(" Please enter a keyword after join");
        }
        String keyword = String.join("", splitInput.subList(1, splitInput.size()));
        ArrayList<Task> containsKeyword = new ArrayList<Task>();
        for (int i = 0; i < tasks.numTasks(); i++) {
            if (tasks.getTask(i).checkKeyword(keyword)) {
                containsKeyword.add(tasks.getTask(i));
            }
        }
        ui.keywordPrint(containsKeyword);
    }
}
