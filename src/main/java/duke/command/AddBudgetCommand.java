package duke.command;

import duke.storage.Storage;
import duke.task.BudgetList;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author maxxyx96
public class AddBudgetCommand extends Command {
    protected BudgetList budgetList;
    protected Ui ui = new Ui();
    protected float amount;
    protected String remark;
    private static final String NO_DESCRIPTION = "No Description";
    private static final float MAX_BUDGET = 999999;

    /**
     * Adds the amount specified into the budgetList.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     * @param amount amount to be updated in the user's budget.
     * @param remark Some description.
     */
    public AddBudgetCommand(BudgetList budgetList, float amount, String remark) {
        this.budgetList = budgetList;
        this.amount = amount;
        this.remark = remark;
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
     * Executes the command to add a certain amount to the existing budget.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String beforeBudgetAdd = ui.showAddBudgetGui(amount, budgetList.getBudget());
        if (!isExceedLimit(amount)) {
            if (remark.equals("")) {
                remark = NO_DESCRIPTION;
            }
            budgetList.addToBudget(Float.toString(amount), remark);
            return beforeBudgetAdd + "\n" + ui.showBudgetGui(budgetList.getBudget()) + "\n"
                    + ui.showRemarkGui(remark);
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