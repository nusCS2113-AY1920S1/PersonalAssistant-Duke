package Modes;

import Enums.RecurTaskType;
import Operations.Parser;
import Operations.RecurHandler;
import Operations.TaskList;
import Operations.Ui;

public class RecurringMode {
    private TaskList taskList;
    private Ui ui = new Ui();
    private Parser parser = new Parser();
    private RecurHandler recurHandler;
    public RecurringMode(TaskList taskList) {
        this.taskList = taskList;
    }
    public void run() {
        boolean isExit = false;
        recurHandler = new RecurHandler(taskList);
        ui.promptRecurringActions();
        while (!isExit) {
            String temp = parser.getCommand();
            RecurTaskType recurType;
            try {
                recurType = RecurTaskType.valueOf(temp);
            } catch (IllegalArgumentException e) {
                recurType = RecurTaskType.others;
            }
            switch (recurType) {
                case list:
                    recurHandler.listRecurring();
                    break;
                case find:
                    recurHandler.findRecurring(parser.getKey());
                    break;
                case exit:
                    isExit = true;
                    ui.showExit();
                    break;
                case add:
                    recurHandler.addBasedOnOperation();
                    break;
                default:
                    ui.showCommandError();
                    break;
            }
        }
    }
}
