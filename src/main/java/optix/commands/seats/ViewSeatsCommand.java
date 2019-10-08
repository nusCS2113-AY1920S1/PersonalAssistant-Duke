package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class ViewSeatsCommand extends Command {
    private String showName;
    private String showDate;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    public ViewSeatsCommand(String showName, String showDate) {
        this.showName = showName;
        this.showDate = showDate;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        ShowMap shows = model.getShows();
        StringBuilder message = new StringBuilder();
        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);
            message.append(String.format("Here is the layout of the theatre for %s on %s:\n", showName, showDate));

            if (!shows.isEmpty() && shows.containsKey(showLocalDate) && shows.get(showLocalDate).hasSameName(showName)) {
                Theatre theatre = shows.get(showLocalDate);
                message.append(theatre.getSeatingArrangement());
            } else {
                message = new StringBuilder("☹ OOPS!!! Sorry the show " + showName + " cannot be found.\n");
            }

        } catch (OptixInvalidDateException e) {
            message.append(e.getMessage());
        } finally {
            ui.setMessage(message.toString());
        }
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
