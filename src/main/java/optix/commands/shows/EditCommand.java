package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

//@@author CheeSengg
public class EditCommand extends Command {
    private String details;

    private static final String MESSAGE_UPDATE_SUCCESSFUL = "Show has been successfully updated to %1$s.\n";

    private static final String MESSAGE_UPDATE_UNSUCCESSFUL = "☹ OOPS!!! The show you are finding does not exist!\n";

    private OptixDateFormatter formatter = new OptixDateFormatter();

    /**
     * Edit the name of an existing show.
     * @param splitStr String of format "OLD_SHOW_NAME|SHOW_DATE|NEW_SHOW_NAME"
     */
    public EditCommand(String splitStr) {
        this.details = splitStr;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        try {
            String[] details = parseDetails(this.details);
            String oldShowName = details[0].trim();
            String showDate = details[1].trim();
            String newShowName = details[2].trim();

            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate localShowDate = formatter.toLocalDate(showDate);
            StringBuilder message = new StringBuilder();

            if (model.containsKey(localShowDate) && model.hasSameName(localShowDate, oldShowName)) {
                model.editShowName(localShowDate, newShowName);
                storage.write(model.getShows());
                message.append(String.format(MESSAGE_UPDATE_SUCCESSFUL, newShowName));
            } else {
                message.append(MESSAGE_UPDATE_UNSUCCESSFUL);
            }
            ui.setMessage(message.toString());
        } catch (OptixException e) {
            ui.setMessage(e.getMessage());
            return "";
        }
        return "show";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.split("\\|");
        if (detailsArray.length != 3) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }
}
