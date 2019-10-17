package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class DeleteOneCommand extends Command {
    private String showDate;
    private String showName;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SUCCESSFUL = "Noted. The show %1$s scheduled on %2$s has been removed.\n";

    private static final String MESSAGE_UNSUCCESSFUL = "Unable to find show called %1$s scheduled on %2$s.\n";

    public DeleteOneCommand(String showName, String showDate) {
        this.showDate = showDate;
        this.showName = showName;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();

        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) {
                model.deleteShow(showLocalDate);
                message.append(String.format(MESSAGE_SUCCESSFUL, showName, showDate));
            } else {
                message.append(String.format(MESSAGE_UNSUCCESSFUL, showName, showDate));
            }
        } catch (OptixInvalidDateException e) {
            message.append(e.getMessage());
        } finally {
            ui.setMessage(message.toString());
        }
    }
}
