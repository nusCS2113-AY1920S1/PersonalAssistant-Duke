package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class EditCommand extends Command {

    private static final String MESSAGE_UPDATE_SUCCESSFUL = "Show has been successfully updated to ";

    private static final String MESSAGE_UPDATE_UNSUCCESSFUL = "☹ OOPS!!! The show you are finding does not exist!\n";

    private String oldShowName;
    private String showDate;
    private String newShowName;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    /**
     * Edit the name of an existing show.
     *
     * @param oldShowName current name
     * @param showDate    date where the show is scheduled
     * @param newShowName new name
     */
    public EditCommand(String oldShowName, String showDate, String newShowName) {
        this.oldShowName = oldShowName;
        this.newShowName = newShowName;
        this.showDate = showDate;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        String message;

        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate localShowDate = formatter.toLocalDate(showDate);

            if (model.containsKey(localShowDate) && model.hasSameName(localShowDate, oldShowName)) {

                model.editShowName(localShowDate, newShowName);

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
