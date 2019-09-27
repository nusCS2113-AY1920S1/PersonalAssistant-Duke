package javacake.commands;

import javacake.Profile;
import javacake.Storage;
import javacake.TaskList;
import javacake.Ui;
import javacake.tasks.Task;

import java.util.ArrayList;

public class ReminderCommand extends Command {
    public ReminderCommand() {
        type = CmdType.REMIND;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Profile profile) {
        ArrayList<Task> deadlineList = new ArrayList<>();
        for (Task task : tasks.getData()) {
            if (task.getTaskType() == Task.TaskType.DEADLINE) {
                deadlineList.add(task);
            }
        }
        ViewScheduleCommand.sortTasksByDate(deadlineList);
        int idx = 1;
        if (deadlineList.size() > 0) {
            ui.showMessage("Here are the upcoming Deadlines:");
            for (Task task : deadlineList) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(idx++).append(".");
                stringBuilder.append(task.getFullString());
                ui.showMessage(stringBuilder.toString());
            }
        } else {
            ui.showError("Empty List!");
        }
    }
}
