package optix.commands.shows;

import optix.Ui;
import optix.commands.Command;
import optix.core.Storage;
import optix.core.Theatre;
import optix.exceptions.OptixInvalidDateException;
import optix.util.OptixDateFormatter;
import optix.util.ShowMap;

import java.time.LocalDate;

public class EditCommand extends Command {

    private static final String MESSAGE_UPDATE_SUCCESSFUL = "Show has been successfully updated to ";

    private static final String MESSAGE_UPDATE_UNSUCCESSFUL = "☹ OOPS!!! The show you are finding does not exist!\n";

    private String oldShowName;
    private String showDate;
    private String newShowName;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    public EditCommand(String oldShowName, String showDate, String newShowName) {
        this.oldShowName = oldShowName;
        this.newShowName = newShowName;
        this.showDate = showDate;
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        String message;
        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }
            LocalDate localShowDate = formatter.toLocalDate(showDate);

            if (!shows.isEmpty() && shows.containsKey(localShowDate) && shows.get(localShowDate).hasSameName(oldShowName)) {
                Theatre show = shows.get(localShowDate);
                show.setShowName(newShowName);

                shows.replace(localShowDate, show);
                message = MESSAGE_UPDATE_SUCCESSFUL + newShowName + ".\n";
            } else {
                message = MESSAGE_UPDATE_UNSUCCESSFUL;
            }
            ui.setMessage(message);
        } catch (OptixInvalidDateException e) {
            ui.setMessage(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
