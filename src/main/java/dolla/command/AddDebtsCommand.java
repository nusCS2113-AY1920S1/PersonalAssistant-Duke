package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.action.redo;
import dolla.action.undo;
import dolla.task.Debt;


public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private int prevPosition;
    private static int undoFlag = 0;

    public AddDebtsCommand(String type, String name, double amount, String description, int prePosition) { //prePosition is -1 by default
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.prevPosition = prePosition;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount, description);
        index = dollaData.getLogList("debt").size();
        if(prevPosition != -1) { //an undo input
            dollaData.addToPrevPosition("debt", newDebt, prevPosition);
            redo.removeCommand("debt", prevPosition);
            System.out.println(prevPosition);
            prevPosition = -1;
        } else { //normal input
            dollaData.addToLogList("debt", newDebt);

//            index = dollaData.getLogList("debt").size();
            undo.removeCommand("debt",index);
            redo.clearRedo();
//            redo.setRedoFlag(0);
        }
        Ui.echoAddDebt(newDebt);
    }
}
