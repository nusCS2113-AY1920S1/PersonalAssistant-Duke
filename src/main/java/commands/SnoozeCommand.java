package commands;

import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SnoozeCommand extends Command {
    private String line;

    /**
     * This is a class for command DONE, which mark one task in the task list as done.
     * @param line the serial number of task to be marked as done
     */
    public SnoozeCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        try {
            String[]arrOfStr = line.split(" to ",2);
            String name = arrOfStr[0];
            String date = arrOfStr[1];

            Date date1 = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(date);

            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getDescription().equals(name)) {
                    tasks.get(i).setTime(date1);
                }
            }
            storage.store(tasks);
            Ui.print("Nice! I've changed the time of this task to " + date);
        } catch (Exception e) {
            throw new DukeException("Task not found");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}