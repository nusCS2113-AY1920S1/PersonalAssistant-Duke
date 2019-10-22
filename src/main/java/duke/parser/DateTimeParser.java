package duke.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
	public static LocalDateTime parseDateTime(String info) {
		LocalDateTime result;
		switch (info) {
			case "today":
				result = LocalDateTime.now();
				break;
			case "tomorrow":
				result = LocalDateTime.now().plusDays(1);
				break;
			default:
				String[] details = info.split(" ", 2);
				switch (details[0]) {
					case "today":
						result = LocalDateTime.of(LocalDate.now(),
								LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
						break;
					case "tomorrow":
						result = LocalDateTime.of(LocalDate.now().plusDays(1),
								LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
						break;
					default:
						result = LocalDateTime.parse(info, DateTimeFormatter.ofPattern("ddMMyy HHmm"));
				}
		}
		return result;
	}
}
