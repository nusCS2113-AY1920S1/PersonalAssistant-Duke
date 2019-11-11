package moomoo.stubs;

import moomoo.command.NotificationCommand;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.Storage;

import java.time.LocalDate;

public class AddExpenditureCommandStub extends AddExpenditureCommand {
    private String catName;
    private String expName;
    private double amount;
    private LocalDate date;

    /**
     * Command that takes in the amount of money spent and the expenditure name and date under which category from the
     * Parser as strings to be converted.
     *
     * @param expenditureName Name of the expenditure spent on. (e.g. Chicken Rice)
     * @param amount          Amount spend on the expenditure.
     * @param date            Date of the spending.
     * @param categoryName    Category that the expenditure is being spent on. (e.g. Food)
     */
    public AddExpenditureCommandStub(String expenditureName, double amount, LocalDate date, String categoryName) {
        super(expenditureName, amount, date, categoryName);
        this.catName = categoryName;
        this.expName = expenditureName;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        Category cat = categoryList.get(catName);
        Expenditure newExpenditure = new Expenditure(expName, amount, date, catName);
        cat.add(newExpenditure);
        NotificationCommand alert = new NotificationCommand(catName, cat.getTotal());
        alert.execute(calendar, budget, categoryList, storage);
        String alertMessage = alert.getMesage();
        Ui.setOutput(alertMessage);
    }
}
