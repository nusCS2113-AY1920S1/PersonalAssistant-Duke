package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.Budget;
import wallet.model.record.BudgetList;
import java.text.DateFormatSymbols;

/**
 * The SetCommand Class deals with the 'set' command.
 */
public class SetBudgetCommand extends Command {
    public static final String COMMAND_WORD = "budget";
    public static final String MESSAGE_SET_BUDGET = " dollars is the budget set for ";
    public static final String MESSAGE_NOTE = "Note that to update your budget, "
            + "simply set the budget for the same month and year again.";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " $100 01/2019"
            + "\nExample: " + COMMAND_WORD + " $0 01/2019 " + "(To remove your budget for the month/year)";
    public static final String MESSAGE_EDIT_BUDGET = "You successfully edited your budget for ";
    public static final String MESSAGE_REMOVE_BUDGET = "You have successfully removed your budget for ";
    public static final String MESSAGE_NO_BUDGET_TO_REMOVE = "There is no budget for removal";
    public static final String MESSAGE_NO_NEGATIVE_BUDGET = "Budget must be more than or equals to zero";

    private Budget budget = null;

    public SetBudgetCommand(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean execute(Wallet wallet) {
        try {
            if (budget.getAmount() < 0) {
                System.out.println(MESSAGE_NO_NEGATIVE_BUDGET);
                System.out.println(MESSAGE_USAGE);
                return false;
            }
            if (budget != null) {
                BudgetList budgetList = wallet.getBudgetList();
                if (budgetList.getBudgetList().size() == 0 && budget.getAmount() == 0) {
                    System.out.println(MESSAGE_NO_BUDGET_TO_REMOVE);
                    return false;
                } else if (budgetList.getBudgetList().size() != 0 && budget.getAmount() == 0) {
                    int index = checkDuplicates(wallet.getBudgetList());
                    if (index != -1) {
                        wallet.getBudgetList().getBudgetList().remove(index);
                        updateSaveFile(wallet);
                        System.out.println(MESSAGE_REMOVE_BUDGET
                                + new DateFormatSymbols().getMonths()[budget.getMonth() - 1]
                                + budget.getYear());
                    } else {
                        System.out.println(MESSAGE_NO_BUDGET_TO_REMOVE);
                    }
                    return false;
                } else if (budgetList.getBudgetList().size() != 0 && budget.getAmount() > 0) {
                    int index = checkDuplicates(wallet.getBudgetList());
                    if (index != -1) {
                        wallet.getBudgetList().editBudget(index, budget);
                    } else {
                        wallet.getBudgetList().addBudget(budget);
                    }
                    System.out.println(budget.getAmount() + MESSAGE_SET_BUDGET
                            + new DateFormatSymbols().getMonths()[budget.getMonth() - 1] + " " + budget.getYear());
                    updateSaveFile(wallet);
                    if (index != -1) {
                        System.out.println(MESSAGE_EDIT_BUDGET
                                + new DateFormatSymbols().getMonths()[budget.getMonth() - 1] + " " + budget.getYear());
                    }
                    return false;
                }
                wallet.getBudgetList().addBudget(budget);
                System.out.println(budget.getAmount() + MESSAGE_SET_BUDGET
                        + new DateFormatSymbols().getMonths()[budget.getMonth() - 1] + " " + budget.getYear());
                updateSaveFile(wallet);
                System.out.println(MESSAGE_NOTE);
            }
        } catch (Exception e) {
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
    
    private int checkDuplicates(BudgetList budgetList) {
        for (int i = 0; i < budgetList.getBudgetList().size(); i++) {
            if (budget.getMonth() == budgetList.getBudgetList().get(i).getMonth()
                    && budget.getYear() == budgetList.getBudgetList().get(i).getYear()) {
                return i;
            }
        }
        return -1;
    }

    private void updateSaveFile(Wallet wallet) {
        wallet.getBudgetList().setModified(true);
    }
}

