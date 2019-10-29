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
            case list:
                ui.helperList();
                break;
            case done:
                ui.helpDone();
                break;
            case restore:
                ui.helpRestore();
                break;
            case find:
                ui.helpFind();
                break;
            case priority:
                ui.helpPriority();
                break;
            case snooze:
                ui.helpSnooze();
                break;
            case reorder:
                ui.helpReorder();
                break;
            case subtask:
                ui.helpSubtask();
                break;
            case update:
                ui.helpUpdate();
                break;
            case sort:
                ui.helpSort();
                break;
            case log:
                ui.helpLog();
                break;
        }
    }

    public void helpCommandList() {
        ui.helpList();
    }
}
