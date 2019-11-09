package moomoo.command.category;

import java.time.LocalDate;

import moomoo.command.Command;
import moomoo.command.NotificationCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.ExpenditureStorage;
import moomoo.feature.storage.Storage;

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
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        Category cat = categoryList.get(categoryName);
        if (cat == null) {
            throw new MooMooException("Sorry I could not find a category named " + categoryName);
        }

        Expenditure newExpenditure = new Expenditure(expenditureName, amount, date, categoryName);
        cat.add(newExpenditure);
        NotificationCommand alert = new NotificationCommand(categoryName, cat.getTotal());
        alert.execute(calendar, budget, categoryList, storage);
        ExpenditureStorage.saveToFile(newExpenditure.toString());
        Ui.showExpenditureMessage("Expenditure named : " + expenditureName,
                "Added to category : " + categoryName);
    }
}

