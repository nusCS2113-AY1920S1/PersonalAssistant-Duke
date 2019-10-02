package duke;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import duke.tasks.ToDo;
import duke.tasks.Deadline;
import duke.tasks.Event;


class ParseTime {

    enum TimePatternType {
        DAY_OF_WEEK,DATE_TIME,DATE, TIME
    }


    LocalDateTime parseStringToDate(String line) throws DukeTimeException {

        DatePattern[] patterns = {  new DatePattern("['next ']['this ']E",TimePatternType.DAY_OF_WEEK),
                                    new DatePattern("['this ']['next ']EEEE",TimePatternType.DAY_OF_WEEK),
                                    new DatePattern("dd/MM/yyyy HHmm",TimePatternType.DATE_TIME),
                                    new DatePattern("dd/MM/yy HHmm",TimePatternType.DATE_TIME),
                                    new DatePattern("d/MM/yyyy HHmm",TimePatternType.DATE_TIME),
                                    new DatePattern("HHmm",TimePatternType.TIME),
                                    new DatePattern("dd/MM/yy",TimePatternType.DATE),
                                    new DatePattern("yyyy-MM-dd'T'HH:mm[:ss.n]",TimePatternType.DATE_TIME) };
        for (int i = 0; i < patterns.length;) {
            try {
                TemporalAccessor accessor = DateTimeFormatter.ofPattern(patterns[i].get_pattern()).parse(line);
                if (patterns[i].get_type() ==  TimePatternType.DAY_OF_WEEK) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    return localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.from(accessor)));
                } else if (patterns[i].get_type() == TimePatternType.DATE_TIME) {
                    return LocalDateTime.from(accessor);
                } else if (patterns[i].get_type() == TimePatternType.TIME) {
                    LocalDate localDate = LocalDate.now();
                    return localDate.atTime(LocalTime.from(accessor));
                } else if (patterns[i].get_type() == TimePatternType.DATE) {
                    LocalTime localTime = LocalTime.now();
                    return localTime.atDate(LocalDate.from(accessor));
                }

            } catch (DateTimeParseException e) {
                i += 1;
            }
        }
        throw new DukeTimeException("invalid format!");
    }
}