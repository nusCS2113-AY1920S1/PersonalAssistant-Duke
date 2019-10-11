package Modes;

import Enums.HelpType;
import Operations.Parser;
import Operations.Ui;

public class HelpMode {
    private Ui ui = new Ui();
    private Parser parser = new Parser();

    public HelpMode() {
    }

    public void run() {
        ui.help();
        boolean isExit = false;
        while (!isExit) {
            String temp = parser.getCommandLine();
            HelpType helpType;
            try {
                helpType = HelpType.valueOf(temp);
            } catch (IllegalArgumentException e) {
               helpType = HelpType.others;
            }
            switch (helpType) {
                case bye:
                    ui.explainBye();
                    break;
                case done:
                    ui.explainDone();
                    break;
                case find:
                    ui.explainFind();
                    break;
                case list:
                    ui.explainList();
                    break;
                case todo:
                    ui.explainToDo();
                    break;
                case event:
                    ui.explainEvent();
                    break;
                case deadline:
                    ui.explainDeadline();
                    break;
                case recur:
                    ui.explainRecur();
                    break;
                case delete:
                    ui.explainDelete();
                    break;
                case snooze:
                    ui.explainSnooze();
                    break;
                case reorder:
                    ui.explainReorder();
                    break;
                case priority:
                    ui.explainPriority();
                    break;
                case exit:
                    isExit = true;
                    System.out.println("You have quit help mode!");
                    break;
                default:
                    ui.showCommandError();
                    break;
            }
        }
    }
}
