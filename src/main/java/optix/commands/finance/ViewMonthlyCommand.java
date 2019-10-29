package optix.commands.finance;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.Map;

//@@author NicholasLiu97
public class ViewMonthlyCommand extends Command {
    private String details;
    private String formattedMonthOfYear;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_NO_SHOW_FOUND = "☹ OOPS!!! There are no shows in %1$s.\n";

    private static final String MESSAGE_NOT_YET_CALCULATED = "☹ OOPS!!! The earnings for %1$s has not been"
            + " calculated. Try other months.\n";

    private static final String MESSAGE_SUCCESSFUL_LIST = "The earnings for %1$s is $%2$.2f.\n";

    public ViewMonthlyCommand(String details) {
        this.details = details;
    }

    /**
     * Calculates the earnings for a certain month from the Optix file.
     * @param shows All shows found in ShowMap.
     * @param mth The month in numerical form.
     * @param yr The year.
     * @return A message String that contains the profit to show to the user.
     */
    private String findFromList(ShowMap shows, int mth, int yr) {
        StringBuilder message = new StringBuilder();
        double profit = 0;

        ShowMap showsWanted = new ShowMap();
        for (Map.Entry<LocalDate, Theatre> entry : shows.entrySet()) {
            showsWanted.put(entry.getKey(), entry.getValue());
        }

        showsWanted.entrySet().removeIf(entry -> entry.getKey().getMonthValue() != mth
                || entry.getKey().getYear() != yr);

        if (showsWanted.isEmpty()) {
            message.append(String.format(MESSAGE_NO_SHOW_FOUND, formattedMonthOfYear));
        } else {
            for (Map.Entry<LocalDate, Theatre> entry : showsWanted.entrySet()) {
                profit += entry.getValue().getProfit();
            }
            message.append(String.format(MESSAGE_SUCCESSFUL_LIST, formattedMonthOfYear, profit));
        }
        return message.toString();
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        int mth;
        int yr;
        try {
            String[] detailsArray = parseDetails(this.details);
            mth = formatter.getMonth(detailsArray[0].trim().toLowerCase());
            yr = formatter.getYear(detailsArray[1].trim());

            if (mth == 0 || yr == 0) {
                throw new OptixInvalidDateException();
            }
            formattedMonthOfYear = formatter.intToMonth(mth) + ' ' + yr;
            if (yr < storage.getToday().getYear()) {
                message.append(findFromList(model.getShowsHistory(), mth, yr));
            } else if (yr > storage.getToday().getYear()) {
                message.append(findFromList(model.getShows(), mth, yr));
            } else {
                if (mth < storage.getToday().getMonthValue()) {
                    message.append(findFromList(model.getShowsHistory(), mth, yr));
                } else if (mth == storage.getToday().getMonthValue()) {
                    message.append(String.format(MESSAGE_NOT_YET_CALCULATED, formattedMonthOfYear));
                } else {
                    message.append(findFromList(model.getShows(), mth, yr));
                }
            }
        } catch (OptixException e) {
            message.append(e.getMessage());
        } finally {
            ui.setMessage(message.toString());
        }
        return "finance";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.trim().split(" ");
        if (detailsArray.length != 2) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

}