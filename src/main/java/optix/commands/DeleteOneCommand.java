package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DeleteOneCommand extends Command {
	private String showDate;
	private String showName;

	public DeleteOneCommand(String showDate, String showName) {
		this.showDate = showDate;
		this.showName = showName;
	}

	@Override
	public void execute(ShowMap shows, Ui ui, Storage storage) {
		StringBuilder message = new StringBuilder();
		LocalDate showLocalDate = toLocalDate(showDate);
		Theatre showToDelete = shows.get(showLocalDate);
		if (showToDelete != null && showToDelete.hasSameName(showName)) {
			shows.remove(showLocalDate, showToDelete);
			message.append(String.format("Noted. The show <%s> scheduled on <%s> has been removed.\n", showName, showDate));
		} else {
			message.append(String.format("Unable to find show called <%s> scheduled on <%s>.\n", showName, showDate));
		}
		ui.setMessage(message.toString());
	}

	//TODO create a date formatter class
	/**
	 * function to convert String to localDate
	 * note that currently the format is fixed 1/1/1997
	 * @param dateString
	 * @return
	 */
	private LocalDate toLocalDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getFormat(dateString));
		//Convert string to localdate
		LocalDate localDate = LocalDate.parse(dateString,formatter);
		return localDate;
	}

	private String getFormat(String date) {
		int padCount = 0;
		StringBuilder format = new StringBuilder();
		String[] timeType = {"d","M","y","H","H","m","m"};
		for (int i = 0; i < date.length(); i += 1) {
			char c = date.charAt(i);
			if (Character.isDigit(c)) {
				format.append(timeType[padCount]);
				if (padCount >= 3) { padCount += 1;}
			} else {
				format.append(c);
				padCount += 1;
			}
		}
		return format.toString();
	}
}
