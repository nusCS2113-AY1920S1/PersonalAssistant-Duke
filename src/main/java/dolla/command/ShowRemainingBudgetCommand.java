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

    private RecordList entryList;
    private RecordList limitList;
    private double budget;
    private double expenses;

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
        try {
            entryList = dollaData.getRecordListObj(MODE_ENTRY);
            limitList = dollaData.getRecordListObj(MODE_LIMIT);
            findBudget();
            if (budget == 0) {
                throw new DollaException(getCommandInfo());
            } else {
                findTotalExpenses();
                processRemainingBudget();
            }
        } catch (DollaException e) {
            DollaException.noExistingBudget(duration);
        }
    }

    private void processRemainingBudget() {
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

    private void findBudget() {
        for (int i = 0; i < limitList.size(); i++) {
            Record limit = limitList.getFromList(i);
            String limitType = limit.getType();
            String limitDuration = limit.getDuration();
            if (budgetIsFound(limitType, limitDuration)) {
                budget = limit.getAmount();
                break;
            }
        }
    }

    private Boolean budgetIsFound(String limitType, String limitDuration) {
        return (limitType.equals(LIMIT_TYPE_B) && limitDuration.equals(duration));
    }

    private void findTotalExpenses() {
        if (duration.equals(LIMIT_DURATION_D)) {
            expenses = getDailyExpense();
        } else if (duration.equals(LIMIT_DURATION_W)) {
            expenses = getWeeklyExpense();
        } else {
            expenses = getMonthlyExpense();
        }
    }

    private double getDailyExpense() {
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

    private double getWeeklyExpense() {
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

    private double getMonthlyExpense() {
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
        return LIMIT_COMMAND_REMAINING + SPACE + duration;
    }
}