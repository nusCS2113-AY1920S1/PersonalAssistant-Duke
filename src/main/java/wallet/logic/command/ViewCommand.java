//@@author matthewng1996

package wallet.logic.command;

import wallet.exception.WrongParameterFormat;
import wallet.model.Wallet;
import wallet.model.record.Budget;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.ui.Ui;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_EMPTY_BUDGET = "There is no budget set for ";
    public static final String MESSAGE_VIEW_BUDGET = "This is the budget set for ";
    public static final String MESSAGE_REMAINING_BUDGET = "This is the budget left for ";
    public static final String MESSAGE_VIEW_STATS = "This is the statistics for ";
    public static final String MESSAGE_STATS_CATEGORY = "These are the expenses for ";
    public static final String MESSAGE_NICE_TRY_MONTH = "Nice try, but month runs from 1 to 12 :)";
    public static final String MESSAGE_NICE_TRY_YEAR = "zero or negative years does not exist.";
    public static final String MESSAGE_NO_EXPENSE_IN_MONTH = "There are no expenses in this month.";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " budget 01/2019"
            + "\nExample: " + COMMAND_WORD + " stats"
            + "\nExample: " + COMMAND_WORD + " stats 01/2019";

    public String[] type;

    public ViewCommand(String... type) {
        this.type = type;
    }

    @Override
    public boolean execute(Wallet wallet) throws NumberFormatException {
        Ui ui = new Ui();
        if (type.length == 2) {
            String[] monthYear = type[1].split("/", 2);
            try {
                int month = Integer.parseInt(monthYear[0].trim());
                if (month <= 0 || month > 12) {
                    throw new WrongParameterFormat(MESSAGE_NICE_TRY_MONTH);
                }
                int year = Integer.parseInt(monthYear[1].trim());
                if (year <= 0) {
                    throw new WrongParameterFormat(MESSAGE_NICE_TRY_YEAR);
                }

                if (type[0].equals("budget")) {
                    for (Budget b : wallet.getBudgetList().getBudgetList()) {
                        if (b.getMonth() == month && b.getYear() == year) {
                            System.out.println(MESSAGE_VIEW_BUDGET
                                    + new DateFormatSymbols().getMonths()[b.getMonth() - 1] + " " + b.getYear());
                            System.out.println("$" + b.getAmount());

                            double remainingBudget = b.getAmount()
                                    - wallet.getExpenseList().getMonthExpenses(b.getMonth(), b.getYear());
                            System.out.println(MESSAGE_REMAINING_BUDGET
                                    + new DateFormatSymbols().getMonths()[b.getMonth() - 1] + " " + b.getYear());
                            System.out.println("$" + remainingBudget);
                            return false;
                        }
                    }
                    System.out.println(MESSAGE_EMPTY_BUDGET
                            +  new DateFormatSymbols().getMonths()[month - 1] + " " + year);
                } else if (type[0].equals("stats")) {
                    //@@author kyang96
                    HashMap<Category, ArrayList<Expense>> categoryMap
                            = getCategoryMap(wallet.getExpenseList().getExpenseList(), month, year);
                    if (categoryMap.isEmpty()) {
                        System.out.println(MESSAGE_NO_EXPENSE_IN_MONTH);
                        return false;
                    }
                    System.out.println(MESSAGE_VIEW_STATS
                            + new DateFormatSymbols().getMonths()[month - 1]
                            + " " + year);
                    double total = wallet.getExpenseList().getMonthExpenses(month, year);
                    double budget = wallet.getBudgetList().getBudget(month, year);
                    ui.drawBarChart(total, budget);
                    for (Category category : Category.values()) {
                        ArrayList<Expense> expenseList = categoryMap.get(category);
                        if (expenseList != null) {
                            System.out.println(MESSAGE_STATS_CATEGORY + category);
                            Ui.printExpenseTable(expenseList);
                        }
                    }
                    //@@author matthewng1996
                    ArrayList<Expense> expenseList = new ArrayList<Expense>();
                    for (Expense e : wallet.getExpenseList().getExpenseList()) {
                        if (e.getDate().getMonthValue() == month && e.getDate().getYear() == year) {
                            expenseList.add(e);
                        }
                    }
                    ui.drawPieChart(expenseList);
                }
            } catch (NumberFormatException err) {
                throw new WrongParameterFormat("There is an error in your parameters. "
                        + MESSAGE_NICE_TRY_MONTH + " and "
                        + MESSAGE_NICE_TRY_YEAR);
            }
        } else if (type.length == 1 && type[0].equals("stats")) {
            ArrayList<Expense> expenseList = wallet.getExpenseList().getExpenseList();
            ui.drawPieChart(expenseList);
            return false;
        } else {
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }

    //@@author kyang96
    /**
     * Generate a HashMap containing all expenses of a certain month in each category.
     * @param expenseList The entire list of expenses.
     * @param month The month to filter.
     * @param year The year to filter.
     */
    public HashMap<Category, ArrayList<Expense>> getCategoryMap(ArrayList<Expense> expenseList, int month, int year) {
        HashMap<Category, ArrayList<Expense>> categoryMap = new HashMap<>();
        for (Expense expense : expenseList) {
            if (expense.getDate().getMonthValue() == month && expense.getDate().getYear() == year) {
                switch (expense.getCategory()) {
                case FOOD:
                    if (categoryMap.get(Category.FOOD) == null) {
                        categoryMap.put(Category.FOOD, new ArrayList<Expense>());
                    }
                    categoryMap.get(Category.FOOD).add(expense);
                    break;
                case BILLS:
                    if (categoryMap.get(Category.BILLS) == null) {
                        categoryMap.put(Category.BILLS, new ArrayList<Expense>());
                    }
                    categoryMap.get(Category.BILLS).add(expense);
                    break;
                case TRANSPORT:
                    if (categoryMap.get(Category.TRANSPORT) == null) {
                        categoryMap.put(Category.TRANSPORT, new ArrayList<Expense>());
                    }
                    categoryMap.get(Category.TRANSPORT).add(expense);
                    break;
                case SHOPPING:
                    if (categoryMap.get(Category.SHOPPING) == null) {
                        categoryMap.put(Category.SHOPPING, new ArrayList<Expense>());
                    }
                    categoryMap.get(Category.SHOPPING).add(expense);
                    break;
                case OTHERS:
                    if (categoryMap.get(Category.OTHERS) == null) {
                        categoryMap.put(Category.OTHERS, new ArrayList<Expense>());
                    }
                    categoryMap.get(Category.OTHERS).add(expense);
                    break;
                default:
                }
            }
        }
        return categoryMap;
    }
}
