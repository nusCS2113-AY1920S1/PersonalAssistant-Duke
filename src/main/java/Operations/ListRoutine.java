package Operations;

import CustomExceptions.RoomShareException;
import Model_Classes.ProgressBar;

public class ListRoutine {
    private TaskList taskList;
    private Ui ui = new Ui();
    public ListRoutine(TaskList taskList) {
        this.taskList = taskList;
    }

    public void list() {
        ui.showList();
        try {
            taskList.list();
        } catch (RoomShareException e) {
            ui.showError(e);
        }
        ProgressBar progressBar = new ProgressBar(taskList.getSize(), taskList.getDoneSize());
        ui.showBar(progressBar.showBar());
    }
}
