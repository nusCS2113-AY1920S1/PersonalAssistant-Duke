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
public class ShowRemainingLimitCommand extends Command implements ParserStringList {

    private String duration;
    private String limitType;
    private String entryType;

    private RecordList entryList;
    private RecordList limitList;

    private double limits;
    private double entries;

    private int one = 1;
    private LocalDate dateToday = now();
    private LocalDate mondayDate = dateToday;
    private LocalDate sundayDate = dateToday;
    private YearMonth month = YearMonth.from(dateToday);
    private LocalDate startOfMonth = month.atDay(one);
    private LocalDate endOfMonth = month.atEndOfMonth();

    /**
     * Instantiates a new ShowRemainingLimitCommand.
     * @param duration Duration of the limit to show.
     * @param type     Type of limit to show.
     */
    public ShowRemainingLimitCommand(String duration, String type) {
        this.duration = duration;
        findEntryAndLimitType(type);
        getDateRanges();
    }

    @Override
    public void execute(DollaData dollaData) {
        try {
            entryList = dollaData.getRecordListObj(MODE_ENTRY);
            limitList = dollaData.getRecordListObj(MODE_LIMIT);
            findLimit();
            findTotalEntries();
            if (limits == 0) {
                throw new DollaException(DollaException.noExistingLimit(limitType));
            } else {
                processRemainingLimit();
            }
        } catch (DollaException e) {
            getCommandInfo();
        }
    }

    private void processRemainingLimit() {
        if (limitType.equals(LIMIT_TYPE_B)) {
            processRemainingBudget();
        } else {
            processRemainingSaving();
        }
    }

    private void processRemainingSaving() {
        double remainingSaving = limits - entries;
        if (remainingSaving == limits) {
            LimitUi.noSavingsPrinter();
        } else if (remainingSaving > 0) {
            LimitUi.remainingSavingPrinter(limits, remainingSaving, duration);
        } else if (remainingSaving == 0) {
            LimitUi.reachedSavingPrinter(duration);
        } else {
            remainingSaving = Math.abs(remainingSaving);
            LimitUi.exceededSavingPrinter(duration, remainingSaving);
        }
    }

    private void processRemainingBudget() {
        double remainingBudget = limits - entries;
        if (remainingBudget == 0) {
            LimitUi.reachedBudgetPrinter(duration);
        } else if (remainingBudget < 0) {
            remainingBudget = Math.abs(remainingBudget);
            LimitUi.exceededBudgetPrinter(remainingBudget, duration);
        } else {
            LimitUi.remainingBudgetPrinter(remainingBudget, limits, entries, duration);
        }
    }

    private void findLimit() {
        for (int i = 0; i < limitList.size(); i++) {
            Record limit = limitList.getFromList(i);
            String limitType = limit.getType();
            String limitDuration = limit.getDuration();
            if (limitIsFound(limitType, limitDuration)) {
                limits = limit.getAmount();
                break;
            }
        }
    }

    private Boolean limitIsFound(String type, String limitDuration) {
        return (type.equals(limitType) && limitDuration.equals(duration));
    }

    private void findTotalEntries() {
        if (duration.equals(LIMIT_DURATION_D)) {
            entries = getDailyEntries();
        } else if (duration.equals(LIMIT_DURATION_W)) {
            entries = getWeeklyEntries();
        } else {
            entries = getMonthlyEntries();
        }
    }

    private double getDailyEntries() {
        double entries = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Record entry = entryList.getFromList(i);
            String type = entry.getType();
            LocalDate date = entry.getDate();
            if (type.equals(entryType) && date.isEqual(dateToday)) {
                entries += entry.getAmount();
            }
        }
        return entries;
    }

    private double getWeeklyEntries() {
        double entries = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Record entry = entryList.getFromList(i);
            String type = entry.getType();
            LocalDate date = entry.getDate();
            if (type.equals(entryType) && withinRange(date, mondayDate, sundayDate)) {
                entries += entry.getAmount();
            }
        }
        return entries;
    }

    private double getMonthlyEntries() {
        double entries = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Record entry = entryList.getFromList(i);
            String type = entry.getType();
            LocalDate date = entry.getDate();
            if (type.equals(entryType) && withinRange(date, startOfMonth, endOfMonth)) {
                entries += entry.getAmount();
            }
        }
        return entries;
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

    private void findEntryAndLimitType(String type) {
        if (LIMIT_TYPE_B.equals(type)) {
            limitType = LIMIT_TYPE_B;
            entryType = ENTRY_TYPE_E;
        } else {
            limitType = LIMIT_TYPE_S;
            entryType = ENTRY_TYPE_I;
        }
    }

    @Override
    public String getCommandInfo() {
        return duration + SPACE + limitType;
    }
}