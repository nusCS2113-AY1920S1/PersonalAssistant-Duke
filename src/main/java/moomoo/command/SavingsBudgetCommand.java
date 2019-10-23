package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * View the savings for respective categories.
 */
public class SavingsBudgetCommand extends Command {
    private ArrayList<String> categories;
    private LocalDate start;
    private LocalDate end;
    private DecimalFormat df;

    /**
     * Initializes the Savings Command.
     * @param isExit Boolean variable to determine if program should exit.
     * @param categories List of categories to view savings for.
     * @param startPeriod Start period consisting of month and year.
     * @param endPeriod End period consisting of month and year.
     */
    public SavingsBudgetCommand(boolean isExit, ArrayList<String> categories,
                                LocalDate startPeriod, LocalDate endPeriod) {
        super(isExit, "");
        this.categories = categories;
        this.start = startPeriod;
        this.end = endPeriod;
        this.df = new DecimalFormat("#.00");

    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        String outputValue = "";
        double totalSavings = 0.0;
        if (categories.size() == 0) {
            for (int i = 0; i < catList.getCategoryList().size(); ++i) {
                categories.add(catList.getCategoryList().get(i).toString());
            }
        }

        for (String iteratorCategory : categories) {
            iteratorCategory = iteratorCategory.toLowerCase();
            Category currentCategory = catList.returnCategory(iteratorCategory);

            if (currentCategory == null) {
                outputValue += iteratorCategory + " category does not exist."
                        + " Please create it first.\n";
                continue;
            }
            if (budget.getBudgetFromCategory(iteratorCategory.toLowerCase()) == 0) {
                outputValue += "The budget for " + iteratorCategory + " does not exist."
                        + " Please set it using budget set.\n";
                continue;
            }

            if (end == null) {
                double savings = budget.getBudgetFromCategory(iteratorCategory)
                        - currentCategory.getCategoryTotalPerMonthYear(start.getMonthValue(), start.getYear());

                totalSavings += savings;
                if (savings > 0) {
                    outputValue += "Your savings for " + iteratorCategory + " for " + start.getMonth()
                            + " " + start.getYear() + " is: $"
                            + df.format(savings)  + "\n";
                } else if (savings == 0) {
                    outputValue += "Your savings for " + iteratorCategory + " for " + start.getMonth()
                            + " " + start.getYear() + " is: $0\n";
                } else {
                    outputValue += "You have overspent your budget for " + iteratorCategory + " for " + start.getMonth()
                            + " " + start.getYear() + " by $" + df.format(Math.abs(savings)) + "\n";
                }
            } else {
                int numberOfMonths = end.getMonthValue() - start.getMonthValue() + 1;
                int numberOfYears = end.getYear() - start.getYear();
                double totalExpenditure = 0;

                if (numberOfYears > 0) {
                    int startMonthValue = start.getMonthValue();
                    int endMonthValue = 12;
                    int yearValue = start.getYear();
                    for (int i = 0; i < numberOfYears; ++i) {
                        for (int x = startMonthValue; x < endMonthValue; ++x) {
                            if (budget.getBudgetFromCategory(currentCategory.toString()) != 0) {
                                totalExpenditure += currentCategory.getCategoryTotalPerMonthYear(x, yearValue);
                            }
                        }
                        startMonthValue = 1;
                        if (numberOfYears - i == 1) {
                            endMonthValue = end.getMonthValue();
                        }
                        yearValue += 1;
                    }
                } else {
                    for (int i = start.getMonthValue(); i < end.getMonthValue() + 1; ++i) {
                        if (budget.getBudgetFromCategory(currentCategory.toString()) != 0) {
                            totalExpenditure += currentCategory.getCategoryTotalPerMonthYear(i, start.getYear());
                        }
                    }
                }

                double savings = (numberOfMonths * budget.getBudgetFromCategory(iteratorCategory))
                        - totalExpenditure;
                totalSavings += savings;

                if (savings >= 0) {
                    outputValue += "Your savings for " + iteratorCategory + " from " + start.getMonth() + " "
                            + start.getYear() + " to "
                            + end.getMonth() + " " + end.getYear() + " is: $"
                            + df.format(savings)  + "\n";
                } else {
                    outputValue += "You have overspent for your budget for " + iteratorCategory + " from "
                            + start.getMonth() + " " + start.getYear() + " to "
                            + end.getMonth() + " " + end.getYear() + " by: $" + df.format(Math.abs(savings)) + "\n";
                }
            }
        }
        if (totalSavings >= 0) {
            outputValue += "Your total savings: $" + df.format(totalSavings) + "\n";
        } else {
            outputValue += "You have overspent your total budget by: $" + df.format(Math.abs(totalSavings)) + "\n";
        }
        ui.setOutput(outputValue);
    }
}
