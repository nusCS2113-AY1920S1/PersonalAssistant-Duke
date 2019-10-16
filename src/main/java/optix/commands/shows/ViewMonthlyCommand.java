package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.Show;
import optix.commons.model.ShowHistoryMap;
import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;

import java.time.LocalDate;
import java.util.Map;

public class ViewMonthlyCommand extends Command {

    private String month;
    private String year;
    private static String[] monthList = {"january", "february", "march", "april", "may", "june", "july", "august",
        "september", "october", "november", "december"};

    private static final String MESSAGE_NO_SHOW_FOUND = "☹ OOPS!!! There are no shows in %1$s %2$s.\n";

    private static final String MESSAGE_NOT_YET_CALCULATED = "☹ OOPS!!! The earnings for %1$s %2$s has not been"
            + " calculated. Try other months.\n";

    private static final String MESSAGE_SUCCESSFUL_ARCHIVE = "The amount earned in %1$s %2$s is %3$s.\n";

    private static final String MESSAGE_SUCCESSFUL_LIST = "The projected earnings for %1$s %2$s is %3$s.\n";

    /**
     * Views the profit for a certain month.
     * @param month Month to query for.
     * @param year  Year to query for.
     */
    public ViewMonthlyCommand(String month, String year) {
        this.month = month.trim().toLowerCase();
        this.year = year.trim().toLowerCase();
        System.out.println("the month is " + month);
        System.out.println("the year is " + year);
    }

    /**
     * Converts month into numerical number.
     * @param month the month.
     * @return an integer corresponding to the month.
     */
    private int parseInt(String month) {
        int mth = 1;

        for (int i = 0; i < monthList.length; i++) {
            if (month.equals(monthList[i])) {
                return mth;
            } else {
                mth++;
            }
        }

        return mth;
    }

    /**
     * Checks if the String entered for month is valid.
     * @param description The String to be checked.
     * @return True if the description matches any of the months.
     */
    private boolean isValidMonth(String description) {
        for (int i = 0; i < monthList.length; i++) {
            if (description.equals(monthList[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the String entered for year is valid.
     * @param year The String to be checked.
     * @return True if the String can be parsed into an integer.
     * @throws NumberFormatException if the String cannot be parsed into an integer.
     */
    private boolean isValidYear(String year) {
        try {
            Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Calculates the earnings for a certain month from the archive file.
     * @param model The model containing the shows stored in the archive file.
     * @param mth The month in numerical form.
     * @param yr The year.
     * @return A message String that contains the profit to show to the user.
     */
    private String findFromArchive(Model model, int mth, int yr) {
        String message = "";
        double profit = 0.0;

        ShowHistoryMap shows = model.getShowsHistory();
        ShowHistoryMap showsWanted = new ShowHistoryMap();
        LocalDate key;
        Show value;
        for (Map.Entry<LocalDate, Show> entry : shows.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            showsWanted.put(key, value);
        }

        showsWanted.entrySet().removeIf(entry -> entry.getKey().getMonthValue() != mth
                || entry.getKey().getYear() != yr);

        if (showsWanted.isEmpty()) {
            message = String.format(MESSAGE_NO_SHOW_FOUND, month, year);
        } else {
            for (Map.Entry<LocalDate, Show> entry : showsWanted.entrySet()) {
                profit += entry.getValue().getProfit();
            }
            message = String.format(MESSAGE_SUCCESSFUL_ARCHIVE, month, year, profit);
        }

        return message;
    }

    /**
     * Calculates the earnings for a certain month from the Optix file.
     * @param model The model containing the shows stored in the Optix file.
     * @param mth The month in numerical form.
     * @param yr The year.
     * @return A message String that contains the profit to show to the user.
     */
    private String findFromList(Model model, int mth, int yr) {
        String message = "";
        double profit = 0.0;

        ShowMap shows = model.getShows();
        ShowMap showsWanted = new ShowMap();
        LocalDate key;
        Theatre value;
        for (Map.Entry<LocalDate, Theatre> entry : shows.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            showsWanted.put(key, value);
        }

        showsWanted.entrySet().removeIf(entry -> entry.getKey().getMonthValue() != mth
                || entry.getKey().getYear() != yr);

        if (showsWanted.isEmpty()) {
            message = String.format(MESSAGE_NO_SHOW_FOUND, month, year);
        } else {
            for (Map.Entry<LocalDate, Theatre> entry : showsWanted.entrySet()) {
                profit += entry.getValue().getProfit();
            }
            message = String.format(MESSAGE_SUCCESSFUL_LIST, month, year, profit);
        }

        return message;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        String message = "";
        int mth = parseInt(month);
        int yr = Integer.parseInt(year);

        try {
            if (!isValidMonth(month) || !isValidYear(year)) {
                throw new OptixInvalidDateException();
            }

            if (yr <= storage.getToday().getYear()) {
                if (mth < storage.getToday().getMonthValue()) {
                    message = findFromArchive(model, mth, yr);
                } else if (mth == storage.getToday().getMonthValue()) {
                    message = String.format(MESSAGE_NOT_YET_CALCULATED, month, year);
                } else {
                    message = findFromList(model, mth, yr);
                }
            } else {
                message = findFromList(model, mth, yr);
            }
        } catch (OptixInvalidDateException e) {
            message = e.getMessage();
        } finally {
            ui.setMessage(message);
        }
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
