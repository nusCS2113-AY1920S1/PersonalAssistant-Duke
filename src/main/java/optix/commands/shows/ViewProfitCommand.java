package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.ShowMap;

import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class ViewProfitCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

    private static final String MESSAGE_DOES_NOT_MATCH = "☹ OOPS!!! Did you get the wrong date or wrong show. \n"
            + "Try again!\n";

    private static final String MESSAGE_SUCCESSFUL = "The profit for %1$s on %2$s is %3$.2f\n";

    /**
     * Views the profit made from a show on a certain date.
     * @param splitStr String of format "SHOW_NAME|SHOW_DATE"
     */
    public ViewProfitCommand(String splitStr) {
        this.details = splitStr;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        String showName, showDate;
        try {
            String[] detailsArray = parseDetails(this.details);
            showName = detailsArray[0].trim();
            showDate = detailsArray[1].trim();
        } catch (OptixInvalidCommandException e) {
            ui.setMessage(e.getMessage());
            return "show";
        }

        String message = "";

        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate localDate = formatter.toLocalDate(showDate);

            if (localDate.compareTo(storage.getToday()) <= 0) { //in archive list
                ShowMap showsHistory = model.getShowsHistory();
                if (!showsHistory.containsKey(localDate)) { //date not found
                    message = MESSAGE_SHOW_NOT_FOUND;
                } else if (!showsHistory.get(localDate).hasSameName(showName)) {
                    message = MESSAGE_DOES_NOT_MATCH;
                } else {
                    message = String.format(MESSAGE_SUCCESSFUL, showName, showDate,
                            showsHistory.get(localDate).getProfit());
                }
            } else {
                ShowMap shows = model.getShows();
                if (!shows.containsKey(localDate)) {
                    message = MESSAGE_SHOW_NOT_FOUND;
                } else if (!shows.get(localDate).hasSameName(showName)) {
                    message = MESSAGE_DOES_NOT_MATCH;
                } else {
                    message = String.format(MESSAGE_SUCCESSFUL, showName, showDate,
                            shows.get(localDate).getProfit());
                }
            }
        } catch (OptixInvalidDateException e) {
            message = e.getMessage();
        } finally {
            ui.setMessage(message);
        }
        return "show";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray =  details.trim().split("\\|");
        if (detailsArray.length != 2) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

}
