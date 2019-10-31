package moomoo.command.category;

import java.time.LocalDate;

import moomoo.command.Command;
import moomoo.command.NotificationCommand;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.ScheduleList;
import moomoo.task.category.Expenditure;

public class AddExpenditureCommand extends Command {

    private String categoryName;
    private String expenditureName;
    private double amount;
    private LocalDate date;

    /**
     * Command that takes in the amount of money spent and the expenditure name and date under which category from the
     * Parser as strings to be converted.
     * @param expenditureName Name of the expenditure spent on. (e.g. Chicken Rice)
     * @param amount          Amount spend on the expenditure.
     * @param date            Date of the spending.
     * @param categoryName    Category that the expenditure is being spent on. (e.g. Food)
     */
    public AddExpenditureCommand(String expenditureName, double amount, LocalDate date, String categoryName) {
        super(false, "");
        this.categoryName = categoryName;
        this.expenditureName = expenditureName;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        Category cat = categoryList.get(categoryName);
        Expenditure newExpenditure = new Expenditure(expenditureName, amount, date);
        cat.add(newExpenditure);
        NotificationCommand alert = new NotificationCommand(categoryName, cat.getTotal());
        alert.execute(calendar, budget, categoryList, category, ui, storage);
        storage.saveExpenditureToFile(newExpenditure, categoryName);
        ui.showNewExpenditureMessage(expenditureName, categoryName);
    }
}

