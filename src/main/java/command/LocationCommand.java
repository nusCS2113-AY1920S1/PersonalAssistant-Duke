package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import javax.xml.stream.Location;

public class LocationCommand extends Command{
    private int indexOfTask;
    private String locationOfTask;

    public LocationCommand(int indexOfTask, String userInputLocation) {
        this.indexOfTask = indexOfTask;
        this.locationOfTask = userInputLocation;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask >= 0 && indexOfTask <= (tasks.getSize() - 1)) {
            Task taskWithLocation = tasks.addLocation(indexOfTask, locationOfTask);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. Your task location has been added:" + "\n " + taskWithLocation.toString());
        } else {
            throw new DukeException(DukeException.taskDoesNotExist());
        }
    }
}
