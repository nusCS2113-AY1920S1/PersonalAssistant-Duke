package optix.commands.seats;

import optix.Ui;
import optix.commands.Command;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.OptixDateFormatter;
import optix.util.ShowMap;

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
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        LocalDate showLocalDate = formatter.toLocalDate(showDate);

        StringBuilder message = new StringBuilder("Here is the layout of the theatre for " + showName + " on " + showDate + ": \n");

        if (!shows.isEmpty() && shows.get(showLocalDate).hasSameName(showName)) {
            Theatre theatre = shows.get(showLocalDate);
            message.append(theatre.getSeatingArrangement());
        } else {
            message = new StringBuilder("☹ OOPS!!! Sorry the show " + showName + " cannot be found.");
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
