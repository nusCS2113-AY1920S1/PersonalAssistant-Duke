package duke.command;

import duke.recipebook.dishlist;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;


import java.text.SimpleDateFormat;
import java.util.Date;

public class FindToday extends Command {
    private Date today = new Date();
    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private String TodayDate = simpleDateFormat.format(today);

    @Override
    public boolean isExit() {
        return false;
    }

    public void execute(dishlist dish1, TaskList taskList, Ui ui, Storage storage) {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList.getAllTasks()) {
            if (task.getDescription().contains(TodayDate)) {
                sb.append("\t ").append(i++).append(".").append(task.toString());
                sb.append(System.lineSeparator());
            }
        }
        if (sb.length() == 0) {
            System.out.println("No ingredients for today!");
        } else {
            System.out.println("\t Here are the ingredients for today");
        }
        sb.setLength(sb.length() - 1);// to remove the last new line
        System.out.println(sb.toString());
    }
}

