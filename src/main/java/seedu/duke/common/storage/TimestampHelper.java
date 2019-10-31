package seedu.duke.common.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public interface TimestampHelper {
    DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'")
            .withResolverStyle(ResolverStyle.STRICT);
    /**
     * Gets a timestamp to be used in file.
     *
     * @return timestamp in string
     */
    static String getTimestamp() {
        return LocalDateTime.now().format(timestampFormatter);
    }

    static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(timestampFormatter);
    }

    static LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    /**
     * Parses a timestamp to LocalDateTime.
     *
     * @param timestamp timestamp stored in file in string
     * @return LocalDateTime parsed from timestamp
     */
    static LocalDateTime parseTimestamp(String timestamp) {
        return LocalDateTime.parse(timestamp, timestampFormatter);
    }
}
