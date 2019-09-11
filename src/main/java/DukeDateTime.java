import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DukeDateTime {

    public LocalDateTime getLocalDateTime(String time){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("[d/M/yyyy HHmm]")
                .appendPattern("[d-M-yyyy HHmm]")
                .appendPattern("[dMyyyy HHmm]")
                .toFormatter();
        return LocalDateTime.parse(time,formatter);
    }
}
