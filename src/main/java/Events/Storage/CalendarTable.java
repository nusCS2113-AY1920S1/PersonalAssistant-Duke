package Events.Storage;

import Events.EventTypes.Event;
import Events.Formatting.EventDate;

import java.util.*;

public class CalendarTable {
    ArrayList<Queue<Event>> eventsOfTheWeek;
    ArrayList<String> dayOfWeek;
    ArrayList<String> dateOfWeek;
    String calendarInfo;

    public CalendarTable(EventList eventList) {
        ArrayList<Event> eventArrayList = eventList.getEventArrayList();

        EventDate today = new EventDate(new Date());
        setDaysAndDatesList(today);
        getEventsOfTheWeek(eventArrayList, today);
    }

    /**
     * Find all the events in the coming 7 days.
     *
     * @param eventArrayList List of all events.
     * @param today The current day.
     */
    public void getEventsOfTheWeek(ArrayList<Event> eventArrayList, EventDate today) {
        EventDate yesterday = today;
        yesterday.addDaysAndSetMidnight(-1);
        EventDate endOfWeek = today;
        endOfWeek.addDaysAndSetMidnight(6);
        EventDate thisDay = today;
        int thisDayNum = 0;
        thisDay.addDaysAndSetMidnight(thisDayNum);

        for (Event thisEvent : eventArrayList) {
            //if this event is within the next 6 days
            if ((thisEvent.getStartDate().compare(endOfWeek) <= 0) && (thisEvent.getStartDate().compare(yesterday) > 0)) {
                while ((thisEvent.getStartDate().compare(thisDay) > 0) && (thisDayNum < 7)) {
                    thisDayNum++;
                    thisDay.addDaysAndSetMidnight(1);
                }
                if (thisDayNum < 7) eventsOfTheWeek.get(thisDayNum).add(thisEvent);
            } else {
                break;
            }
        }
    }

    /**
     * Sets the two Arraylists containing days and dates info for the coming 7 days.
     *
     * @param today The current day.
     */
    public void setDaysAndDatesList(EventDate today) {
        String dayOfToday = today.getEventJavaDate().toString().split(" ")[0];
        List weekdays = Arrays.asList("    <Monday>    ", "    <Tuesday>    ", "   <Wednsday>   ",
                "   <Thursday>   ", "    <Friday>    ", "   <Saturday>   ", "    <Sunday>    ");
        int startDay;
        if (dayOfToday.equals("Mon")) {
            startDay = 1;
        } else if (dayOfToday.equals("Tue")) {
            startDay = 2;
        } else if (dayOfToday.equals("Wed")) {
            startDay = 3;
        } else if (dayOfToday.equals("Thu")) {
            startDay = 4;
        } else if (dayOfToday.equals("Fri")) {
            startDay = 5;
        } else if (dayOfToday.equals("Sat")) {
            startDay = 6;
        } else {
            startDay = 7;
        }
        for(int i=0; i<7; i++) {
            this.dayOfWeek.set(i, (String)weekdays.get((startDay + i) % 8 + 1));
        }

        EventDate tempDay = today;
        for (int i=0; i<7; i++) {
            tempDay.addDaysAndSetMidnight(1);
            String thisDate = tempDay.getUserInputDateString().split(" ")[0];
            this.dateOfWeek.add("   " + thisDate + "   ");
        }
    }

    public void setCalendarInfo() {
        String calendarInfo = "";

    }
}
