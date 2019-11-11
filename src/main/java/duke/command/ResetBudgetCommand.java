package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.BudgetList;


//@@author maxxyx96

/**
 * Represents a command to reset the budget amount.
 */
public class ResetBudgetCommand extends Command {
    protected BudgetList budgetList;
    protected Ui ui = new Ui();
    protected float amount;
    private static final float MAX_BUDGET = 999999;


    /**
     * Obtaining the parameters of budget from budget corner.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     * @param amount The amount to be updated.
     */
    public ResetBudgetCommand(BudgetList budgetList, float amount) {
        this.budgetList = budgetList;
        this.amount = amount;
    }

    /**
     * Checks if the budget exceeds the limits of what was intended for.
     *
     * @param amount the amount to be checked.
     * @return Returns true if the amount is within limit, false otherwise.
     */
    public boolean isExceedLimit(float amount) {
        return (amount < -MAX_BUDGET || amount > MAX_BUDGET);
    }

    /**
     * Executes a command with task list and ui (GUI).
     * (not used)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui    To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        if (!isExceedLimit(amount)) {
            Float previousBudget = budgetList.getBudget();
            budgetList.resetBudget(amount);
            return ui.showResetBudgetGui(previousBudget) + "\n" + ui.showBudgetGui(budgetList.getBudget());
        } else {
            return ui.showBudgetExceededLimitMessageGui();
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