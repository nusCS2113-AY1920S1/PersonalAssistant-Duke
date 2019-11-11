package duke.command;

import duke.enums.Numbers;
import duke.storage.Storage;
import duke.task.BudgetList;
import duke.task.TaskList;
import duke.ui.Ui;


//@@author maxxyx96

/**
 * Represents a command to undo the last budget.
 */
public class UndoBudgetCommand extends Command {

    protected Ui ui = new Ui();
    protected BudgetList budgetList;

    /**
     * Command that allows the user to Undo the budget that he/she currently has.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     */
    public UndoBudgetCommand(BudgetList budgetList) {
        this.budgetList = budgetList;
    }


    /**
     * Executes a command with task list and ui (GUI).
     *
     *
     * @param items The task list that contains a list of tasks.
     * @param ui    To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        try {
            int indexToBeUndone = budgetList.getSize() - Numbers.ONE.value;

            if (indexToBeUndone != Numbers.ZERO.value) {
                budgetList.undoLastBudget(indexToBeUndone);
                float newBudget = budgetList.getBudget();
                return ui.showUndoneBudgetGui(Float.toString(newBudget));
            } else {
                return ui.showBudgetUndoErrorGui();
            }
        } catch (Exception e) {
            return ui.showBudgetUndoErrorGui();
        }
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (not used)
     *
     * @param items   The task list that contains a list of tasks.
     * @param ui      To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {

    }
}
//@@author