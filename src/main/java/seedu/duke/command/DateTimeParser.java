package seedu.duke.command;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    public static LocalDateTime getDateTime(String dateTime) {

        LocalDateTime localDateTime;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/uuuu HHmm");
            localDateTime = LocalDateTime.parse(dateTime,formatter);
            return localDateTime;

        } catch ( e){

            return null;
        }

    }

//    public LocalDateTime getDate(String date){
//
//
//
//    }



}
