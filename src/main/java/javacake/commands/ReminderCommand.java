package javacake.commands;

import javacake.ProgressStack;
import javacake.storage.Profile;
import javacake.ui.Ui;
import javacake.storage.Storage;
import javacake.tasks.Task;

import java.util.ArrayList;

public class ReminderCommand extends Command {
    public ReminderCommand() {
        type = CmdType.REMIND;
    }

    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) {
        ArrayList<Task> deadlineList = new ArrayList<>();
        /*for (Task task : progressStack.getData()) {
            if (task.getTaskType() == Task.TaskType.DEADLINE) {
                deadlineList.add(task);
            }
        }*/
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
        return "";
    }
}
