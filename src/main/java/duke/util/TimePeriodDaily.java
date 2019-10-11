//package duke.util;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//
//public class TimePeriodDaily implements TimePeriod {
//
//    LocalTime begin;
//    LocalTime end;
//    private boolean isUntilNextDay;
//    TimePeriodWeekly[] periods;
//    public static final transient DayOfWeek[] daysOfWeeks = DayOfWeek.values();
//
//    public TimePeriodDaily(LocalTime begin, LocalTime end) {
//        this.setPeriod(begin, end);
//    }
//
//    public TimePeriodDaily(LocalTime begin, TimeInterval duration) {
//        this.setPeriod(begin, duration);
//    }
//
//    public TimePeriodDaily(LocalTime begin, boolean isInstantEnd) {
//        this(begin, (LocalTime) null);
//        if (isInstantEnd) {
//            this.setEnd(this.getBegin());
//        }
//    }
//
//    public TimePeriodDaily(LocalTime begin, DayOfWeek dayOfWeek) {
//        this(begin, true);
//    }
//
//    public TimePeriodDaily(DayOfWeek dayOfWeek) {
//        this(null, (LocalTime) null);
//    }
//
//    public boolean isClashing(LocalTime localTime, boolean strictBegin, boolean strictEnd) {
//        if (localTime == null) {
//            return false;
//        }
//        boolean beforeBegin = localTime.isBefore(this.begin);
//        boolean afterBegin = localTime.isAfter(this.begin);
//        boolean beforeEnd = localTime.isBefore(this.end);
//        boolean afterEnd = localTime.isAfter(this.end);
//        return !this.isUntilNextDay && afterBegin && beforeEnd
//                || this.isUntilNextDay && (afterBegin && afterEnd || beforeBegin && beforeEnd)
//                || strictBegin && localTime.equals(this.begin)
//                || strictEnd && localTime.equals(this.end);
//    }
//
//    public LocalTime getBegin() {
//        return this.begin;
//    }
//
//    public LocalTime getEnd() {
//        return this.end;
//    }
//
//    public void setBegin(LocalTime begin) {
//        this.setPeriod(begin, this.end);
//    }
//
//    public void setEnd(LocalTime end) {
//        this.setPeriod(this.begin, end);
//    }
//
//    public void setPeriod(LocalTime begin, LocalTime end) {
//        if (this.periods == null) {
//            this.periods = new TimePeriodWeekly[7];
//        }
//        if (begin != null && end != null && end.isBefore(begin)) {
//            this.isUntilNextDay = true;
//        }
//        else {
//            this.isUntilNextDay = false;
//        }
//        for (int i = 0; i < daysOfWeeks.length; ++i) {
//            this.periods[i] = new TimePeriodWeekly(begin, end, daysOfWeeks[i]);
//        }
//    }
//
//    public void setPeriod(LocalTime begin, TimeInterval duration) {
//        this.setPeriod(begin, begin.plus(duration));
//    }
//
//    @Override
//    public TimeInterval getInterval() {
//        return null;
//    }
//
//    @Override
//    public String toString() {
//        return this.begin + " - " + this.end;
//    }
//
//    @Override
//    public LocalTime getBeginTime() {
//        return this.begin;
//    }
//
//    @Override
//    public LocalTime getEndTime() {
//        return this.end;
//    }
//
//    @Override
//    public List<DayOfWeek> getDaysOfWeek() {
//        return Arrays.asList(TimePeriodDaily.daysOfWeeks);
//    }
//}
