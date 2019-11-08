package diyeats.commons.constants;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public final class DateConstants {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");
}
