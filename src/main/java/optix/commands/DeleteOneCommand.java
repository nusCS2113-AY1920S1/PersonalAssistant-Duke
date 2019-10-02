package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.OptixDateFormatter;
import optix.util.ShowMap;

import java.time.LocalDate;

public class DeleteOneCommand extends Command {
	private String showDate;
	private String showName;

	private OptixDateFormatter formatter = new OptixDateFormatter();


	public DeleteOneCommand(String showName, String showDate) {

		this.showDate = showDate;
		this.showName = showName;
	}

	@Override
	public void execute(ShowMap shows, Ui ui, Storage storage) {
		StringBuilder message = new StringBuilder();
		LocalDate showLocalDate = formatter.toLocalDate(showDate);
		Theatre showToDelete = shows.get(showLocalDate);

		if (showToDelete != null && showToDelete.hasSameName(showName)) {
			shows.remove(showLocalDate, showToDelete);
			message.append(String.format("Noted. The show <%s> scheduled on <%s> has been removed.\n", showName, showDate));
		} else {
			message.append(String.format("Unable to find show called <%s> scheduled on <%s>.\n", showName, showDate));
		}
		ui.setMessage(message.toString());
	}
}
