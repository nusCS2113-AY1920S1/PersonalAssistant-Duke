package planner.util.periods;

import planner.exceptions.original.ModInvalidTimePeriodException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public interface TimePeriod {

    TimeInterval getInterval();

    LocalTime getBeginTime();

    LocalTime getEndTime();

    Temporal getBegin();

    Temporal getEnd();

    <E extends TimePeriod> boolean isClashing(E other);

    <E extends TemporalAccessor> boolean isClashing(E other);

    <E extends TemporalAccessor> boolean isClashing(E begin, E end) throws ModInvalidTimePeriodException;

    List<DayOfWeek> getDaysOfWeek();

    @Override
    String toString();
}
