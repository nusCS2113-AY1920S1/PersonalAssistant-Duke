package commands;

import members.Member;
import tasks.Task;
import utils.CommandResult;
import utils.DukeException;
import utils.Storage;
import core.Ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SnoozeCommand extends Command {
    private String line;

    /**
     * This is a class for command SNOOZE, which changes a task to a different date.
     * @param line the TASKNAME to another time
     */
    public SnoozeCommand(String line) {
        this.line = line;
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
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
            storage.storeTaskList(tasks);
            return new CommandResult("Nice! I've changed the time of this task to " + date);
        } catch (Exception e) {
            throw new DukeException("Task not found");
        }
    }
}