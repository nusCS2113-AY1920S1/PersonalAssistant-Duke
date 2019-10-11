package duke.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface TimePeriod {

    TimeInterval getInterval();

    LocalTime getBeginTime();

    LocalTime getEndTime();

    DateTime getBegin();

    DateTime getEnd();

    boolean isClashing(TimePeriod other);

    List<DayOfWeek> getDaysOfWeek();

    @Override
    String toString();
}
