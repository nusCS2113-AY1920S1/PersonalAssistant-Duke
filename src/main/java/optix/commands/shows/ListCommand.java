package optix.commands.shows;

import optix.commons.Model;
import optix.ui.Ui;
import optix.commands.Command;
import optix.commons.Storage;
import optix.commons.model.Theatre;
import optix.commons.model.ShowMap;

import java.time.LocalDate;
import java.util.Map;

public class ListCommand extends Command {
    private static final String MESSAGE_LIST_FOUND = "Here are the list of shows:\n";
    private static final String MESSAGE_LIST_NOT_FOUND = "☹ OOPS!!! There are no shows in the near future.\n";
    private static final String MESSAGE_ENTRY = "%1$d. %2$s (on: %3$s)\n";

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        ShowMap shows = model.getShows();
        StringBuilder message = new StringBuilder();
        LocalDate today = storage.getToday();

        int counter = 1;

        if (!shows.isEmpty()) {
            message.append(MESSAGE_LIST_FOUND);
            for (Map.Entry<LocalDate, Theatre> entry : shows.entrySet()) {
                Theatre show = entry.getValue();
                LocalDate showDate = entry.getKey();

                message.append(String.format(MESSAGE_ENTRY, counter, show.getShowName(), showDate));
                counter++;
            }
        } else {
            message = new StringBuilder(MESSAGE_LIST_NOT_FOUND);
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
