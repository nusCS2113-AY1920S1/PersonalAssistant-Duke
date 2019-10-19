package Operations;

import Enums.TaskType;

public class Help {
    private Ui ui = new Ui();
    public Help() {
    }

    public void showHelp(String keyword) {
        TaskType taskType;
        try {
            taskType = TaskType.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            taskType = TaskType.others;
        }
        switch (taskType) {
            case add:
                ui.helpAdd();
                break;
            case delete:
                ui.helpDelete();
                break;
        }
    }

    public void helpCommandList() {
        ui.helpList();
    }
}
