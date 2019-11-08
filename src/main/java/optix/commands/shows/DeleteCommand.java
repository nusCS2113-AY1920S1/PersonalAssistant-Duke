package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author OungKennedy
public class DeleteCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SUCCESSFUL = "Noted. The following shows has been deleted:\n";

    private static final String MESSAGE_ENTRY = "%1$d. %2$s (on: %3$s)\n";

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! Unable to find the following shows:\n";

    /**
     * Instantiate vars.
     * @param splitStr String of format "SHOW_NAME|DATE_1|DATE_2|etc."
     */
    public DeleteCommand(String splitStr) {
        this.details = splitStr;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        String[] detailsArray;
        try {
            detailsArray = parseDetails(this.details);
        } catch (OptixInvalidCommandException e) {
            ui.setMessage(e.getMessage());
            return "";
        }

        String[] showDates = detailsArray[1].split("\\|");
        String showName = detailsArray[0].trim();

        StringBuilder message = new StringBuilder(MESSAGE_SUCCESSFUL);
        ArrayList<String> missingShows = new ArrayList<>();
        int counter = 1;

        for (String showDate : showDates) {
            String date = showDate.trim();

            if (!hasValidDate(date)) {
                missingShows.add(date);
                continue;
            }

            LocalDate showLocalDate = formatter.toLocalDate(date);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) {
                model.deleteShow(showLocalDate);
                message.append(String.format(MESSAGE_ENTRY, counter, showName, date));
                counter++;
            } else {
                missingShows.add(date);
            }
        }
        if (missingShows.size() == showDates.length) {
            message = new StringBuilder(MESSAGE_SHOW_NOT_FOUND);
        } else if (missingShows.size() != 0) {
            message.append("\n" + MESSAGE_SHOW_NOT_FOUND);
        }
        for (int i = 0; i < missingShows.size(); i++) {
            message.append(String.format(MESSAGE_ENTRY, i + 1, showName, missingShows.get(i)));
        }
        ui.setMessage(message.toString());
        storage.write(model.getShows());
        return "show";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray;
        detailsArray = details.split("\\|",2);

        if (detailsArray.length != 2) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

    private boolean hasValidDate(String date) {
        return formatter.isValidDate(date);
    }
}
