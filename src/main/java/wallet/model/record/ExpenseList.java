package wallet.model.record;

import wallet.logic.command.ListCommand;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ExpenseList Class that maintains a list of Expense objects.
 */
public class ExpenseList {
    private boolean isModified = false;
    private ArrayList<Expense> expenseList;

    /**
     * Constructs a new ExpenseList object.
     */
    public ExpenseList() {
        this.expenseList = new ArrayList<Expense>();
    }

    /**
     * Constructs a new ExpenseList object with populated list.
     */
    public ExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Returns true if list is modified, else false.
     */
    public boolean getIsModified() {
        return isModified;
    }

    /**
     * Sets status of whether list is modified.
     */
    public void setModified(boolean modified) {
        isModified = modified;
    }

    /**
     * Returns the list of expenses.
     *
     * @return The list of expenses.
     */
    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    /**
     * Sets the list of expenses.
     *
     * @param expenseList The list of expenses.
     */
    public void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Add the given expense into the expenseList.
     *
     * @param e The expense to be added.
     */
    public void addExpense(Expense e) {
        e.setId(getLargestId(this.expenseList) + 1);
        expenseList.add(e);
    }

    /**
     * Retrieve the expense at the given index of the expenseList.
     *
     * @param index The index of the expense in the expenseList.
     * @return The expense at the given index.
     */
    public Expense getExpense(int index) {
        return expenseList.get(index);
    }

    /**
     * Modify the value of the expense at the given index in the expenseList.
     *
     * @param index The index of the expense in the list.
     * @param e     The expense object with modified values.
     */
    public void editExpense(int index, Expense e) {
        expenseList.set(index, e);
    }

    /**
     * Returns the index of the Expense object.
     *
     * @param e The Expense object.
     * @return integer index.
     */
    public int findExpenseIndex(Expense e) {
        return expenseList.indexOf(e);
    }

    /**
     * Returns the size of the expenseList.
     *
     * @return integer size of the expenseList.
     */
    public int getSize() {
        return expenseList.size();
    }

    //@@author matthewng1996
    /**
     * Returns the monthly expenses.
     * @param month month of user expenses.
     * @param year year of user expenses.
     * @return sum of expenses in a given month and year.
     */
    public double getMonthExpenses(int month, int year) {
        int expenseMonth;
        int expenseYear;
        double totalExpenses = 0;

        for (Expense e : this.expenseList) {
            LocalDate expenseDate = e.getDate();
            expenseMonth = expenseDate.getMonthValue();
            expenseYear = expenseDate.getYear();
            if (expenseMonth == month && expenseYear == year) {
                totalExpenses += e.getAmount();
            }
        }

        return totalExpenses;
    }

    //@@author
    /**
     * Lists all expenses in the expense list.
     */
    public void listExpenseList() {
        int counter = 1;
        System.out.println(ListCommand.MESSAGE_LIST_EXPENSES);
        for (Expense e : this.expenseList) {
            System.out.println(counter + ". " + e.toString());
            counter++;
        }
    }

    /**
     * Lists all recurring expenses in the expense list.
     */
    public void listRecurringExpense() {
        int counter = 1;
        System.out.println(ListCommand.MESSAGE_LIST_RECURRING_EXPENSES);
        for (Expense e : this.expenseList) {
            if (e.isRecurring()) {
                System.out.println(counter + ". " + e.toString());
                counter++;
            }
        }
    }

    /**
     * Returns the largest id.
     *
     * @param expenseList The list of expenses.
     * @return The largest id.
     */
    public int getLargestId(ArrayList<Expense> expenseList) {
        int max = 0;
        for (Expense expense : expenseList) {
            if (expense.getId() > max) {
                max = expense.getId();
            }
        }
        return max;
    }

    /**
     * Deletes an expense using its id.
     * @param id The id of the expense to delete.
     * @return
     */
    public Expense deleteExpense(int id) {
        int index = findIndexWithId(id);
        if (index >= 0) {
            Expense expense = getExpense(index);
            expenseList.remove(index);
            return expense;
        }
        return null;
    }

    /**
     * Finds and returns expense index using its id.
     * @param id The id of the expense to find.
     * @return
     */
    public int findIndexWithId(int id) {
        int index = 0;
        for (Expense e : this.expenseList) {
            if (e.getId() == id) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Finds and returns the Expense object using its id.
     * @param id The id of the expense object.
     * @return The expense object.
     */
    public Expense findExpenseWithId(int id) {
        for (Expense e : this.expenseList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
