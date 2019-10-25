package spinbox.commands;

import spinbox.storage.Storage;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;
import spinbox.entities.items.tasks.Task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;


public class ReminderCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui, boolean gui) {
        List<Task> tasks = taskList.getList();
        TreeMap<String, List<Task>> byDate = new TreeMap<>();

        List<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("Here are your reminders:");

        for (Task task : tasks) {
            String date = "No start date";

            if (task.getDone()) {
                continue;
            }

            if (task.isSchedulable()) {

                LocalDateTime today = LocalDateTime.now();
                LocalDateTime startDate = ((Schedulable)task).getStartDate().getDateTime()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                date = startDate.toLocalDate().toString();

                if (today.compareTo(startDate) > 0) {
                    date = date.concat(" (Overdue)");
                } else if (today.toLocalDate().compareTo(startDate.toLocalDate()) == 0) {
                    date = date.concat(" (Today)");
                }

                if (byDate.containsKey(date)) {
                    byDate.get(date).add(task);
                } else {
                    byDate.put(date, new ArrayList<>());
                    byDate.get(date).add(task);
                }
            }
        }

        for (Map.Entry<String, List<Task>> entry : byDate.entrySet()) {
            String key = entry.getKey();

            formattedOutput.add("\n-----------------");
            formattedOutput.add(key);
            formattedOutput.add("-----------------");

            List<Task> currentTasks = entry.getValue();

            currentTasks.forEach((Task currentTask) -> {
                formattedOutput.add(currentTask.toString());
            });
        }

        return ui.showFormatted(formattedOutput);
    }
}
