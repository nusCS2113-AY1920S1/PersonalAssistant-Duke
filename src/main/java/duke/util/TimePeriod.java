package duke.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public interface TimePeriod {

    TimeInterval getInterval();

    LocalTime getBeginTime();

    LocalTime getEndTime();

    TemporalAccessor getBegin();

    TemporalAccessor getEnd();

    boolean isClashing(TimePeriod other);

    List<DayOfWeek> getDaysOfWeek();

    @Override
    String toString();
}
