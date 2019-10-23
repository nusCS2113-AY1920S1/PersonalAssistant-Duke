package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;

import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddCommand extends Command {
    private String showName;
    private String[] showDates;
    private double seatBasePrice;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SUCCESSFUL = "Noted. The following shows has been added:\n";

    private static final String MESSAGE_ENTRY = "%1$d. %2$s (on: %3$s)\n";

    private static final String MESSAGE_UNSUCCESSFUL = "☹ OOPS!!! Unable to add the following shows:\n";

    /**
     * Add a show to the show list.
     *
     * @param splitStr String of format "SHOW_NAME|SEAT_BASE_PRICE|DATE_1|DATE_2|etc"
     */
    public AddCommand(String splitStr) throws OptixInvalidCommandException {
        String[] details = parseDetails(splitStr);
        if (details.length != 3) {
            throw new OptixInvalidCommandException();
        }
        // need to check if it is a valid date if not need to throw exception

        this.showName = details[0].trim();
        this.showDates = details[2].trim().split("\\|");
        this.seatBasePrice = Double.parseDouble(details[1]);

    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        LocalDate today = storage.getToday();
        ArrayList<String> errorShows = new ArrayList<>();


        StringBuilder message = new StringBuilder(MESSAGE_SUCCESSFUL);

        int counter = 1;

        for (int i = 0; i < showDates.length; i++) {
            String date = showDates[i].trim();


            if (!hasValidDate(date)) {
                errorShows.add(date);
                continue;
            }
            
            LocalDate showLocalDate = formatter.toLocalDate(date);

            if (showLocalDate.compareTo(today) <= 0 || model.containsKey(showLocalDate)) {
                errorShows.add(date);
            } else {
                model.addShow(showName, showLocalDate, seatBasePrice);
                message.append(String.format(MESSAGE_ENTRY, counter, showName, date));
                counter++;
            }
        }

        if (errorShows.size() == showDates.length) {
            message = new StringBuilder(MESSAGE_UNSUCCESSFUL);
        } else if (errorShows.size() != 0) {
            message.append("\n" + MESSAGE_UNSUCCESSFUL);
        }

        for (int i = 0; i < errorShows.size(); i++) {
            message.append(String.format(MESSAGE_ENTRY, i + 1, showName, errorShows.get(i)));
        }

        ui.setMessage(message.toString());
    }

    @Override
    public String[] parseDetails(String details) {
        return details.trim().split("\\|", 3);
    }

    private boolean hasValidDate(String date) {
        return formatter.isValidDate(date);
    }
    
    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
