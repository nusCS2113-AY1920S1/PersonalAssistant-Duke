package dolla.command;

import dolla.exception.DollaException;

import dolla.model.DollaData;
import dolla.model.Record;
import dolla.model.RecordList;

import dolla.parser.ParserStringList;
import dolla.ui.LimitUi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.LocalDate.now;

//@@author Weng-Kexin
public class ShowRemainingBudgetCommand extends Command implements ParserStringList {

    private int one = 1;
    private String duration;
    private LocalDate dateToday = now();
    private LocalDate mondayDate = dateToday;
    private LocalDate sundayDate = dateToday;

    private YearMonth month = YearMonth.from(dateToday);
    private LocalDate startOfMonth = month.atDay(one);
    private LocalDate endOfMonth = month.atEndOfMonth();

    /**
     * Instantiates a new ShowRemainingBudgetCommand.
     * @param duration Duration of the budget to show.
     */
    public ShowRemainingBudgetCommand(String duration) {
        this.duration = duration;
        getDateRanges();
    }

    @Override
    public void execute(DollaData dollaData) {
        RecordList entryList = dollaData.getRecordListObj(MODE_ENTRY);
        RecordList limitList = dollaData.getRecordListObj(MODE_LIMIT);
        double budget = getBudget(limitList);
        try {
            if (budget == 0) {
                throw new DollaException(getCommandInfo());
            } else {
                double expenses = getTotalExpenses(entryList);
                processRemainingBudget(budget, expenses);
            }
        } catch (DollaException e) {
            DollaException.noExistingBudget(duration);
        }
    }

    private void processRemainingBudget(double budget, double expenses) {
        double remainingBudget = budget - expenses;
        if (remainingBudget == 0) {
            LimitUi.reachedBudgetPrinter(duration);
        } else if (remainingBudget < 0) {
            remainingBudget = Math.abs(remainingBudget);
            LimitUi.exceededBudgetPrinter(remainingBudget, duration);
        } else {
            LimitUi.remainingBudgetPrinter(remainingBudget, budget, expenses, duration);
        }
    }

    private double getBudget(RecordList limitList) {
        for (int i = 0; i < limitList.size(); i++) {
            Record limit = limitList.getFromList(i);
            String limitType = limit.getType();
            String limitDuration = limit.getDuration();
            if (budgetIsFound(limitType, limitDuration)) {
                return limit.getAmount();
            }
        }
        return 0;
    }

    private Boolean budgetIsFound(String limitType, String limitDuration) {
        return (limitType.equals(LIMIT_TYPE_B) && limitDuration.equals(duration));
    }

    private double getTotalExpenses(RecordList entryList) {
        if (duration.equals(LIMIT_DURATION_D)) {
            return getDailyExpense(entryList);
        } else if (duration.equals(LIMIT_DURATION_W)) {
            return getWeeklyExpense(entryList);
        } else {
            return getMonthlyExpense(entryList);
        }
    }

    private double getDailyExpense(RecordList entryList) {
        double expenses = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Record entry = entryList.getFromList(i);
            LocalDate entryDate = entry.getDate();
            if (entryDate.isEqual(dateToday)) {
                expenses += entry.getAmount();
            }
        }
        return expenses;
    }

    private double getWeeklyExpense(RecordList entryList) {
        double expenses = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Record entry = entryList.getFromList(i);
            LocalDate entryDate = entry.getDate();
            if (withinRange(entryDate, mondayDate, sundayDate)) {
                expenses += entry.getAmount();
            }
        }
        return expenses;
    }

    private double getMonthlyExpense(RecordList entryList) {
        double expenses = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Record entry = entryList.getFromList(i);
            LocalDate entryDate = entry.getDate();
            if (withinRange(entryDate, startOfMonth, endOfMonth)) {
                expenses += entry.getAmount();
            }
        }
        return expenses;
    }

    private Boolean withinRange(LocalDate currDate, LocalDate startDate, LocalDate endDate) {
        return (currDate.isAfter(startDate) && currDate.isBefore(endDate));
    }

    private void getDateRanges() {
        while (mondayDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            mondayDate = mondayDate.minusDays(one);
        }

        while (sundayDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sundayDate = sundayDate.plusDays(one);
        }

        mondayDate = mondayDate.minusDays(one);
        sundayDate = sundayDate.plusDays(one);
        startOfMonth = startOfMonth.minusDays(one);
        endOfMonth = endOfMonth.plusDays(one);
    }

    @Override
    public String getCommandInfo() {
        return MODE_LIMIT;
    }
}