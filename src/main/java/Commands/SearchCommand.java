package Commands;

import Tasks.*;
import ControlPanel.*;

public class SearchCommand extends Command {

    private String keyword;

    public SearchCommand(String string){
        keyword = string;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        int count = 1;
        for (int i = 1;i <= tasks.lengthOfList();i++) {
            if (tasks.getTask(i-1).getDescription().contains(keyword)) {
                System.out.println(" " + count++ + "." + tasks.getTask(i - 1).toString() + "\n");

            }
        };
    }
}
