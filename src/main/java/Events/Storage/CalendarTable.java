package Events.Storage;

import Events.EventTypes.Event;
import Events.Formatting.EventDate;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarTable {
    private List<Queue<Event>> eventsOfTheWeek = new ArrayList<Queue<Event>>(7);

    private ArrayList<String> dayOfWeek = new ArrayList<String>();
    private ArrayList<String> dateOfWeek = new ArrayList<String>();
    private String calendarInfo;

    public CalendarTable(EventList eventList) {
        ArrayList<Event> eventArrayList = eventList.getEventArrayList();
        for (int i = 0; i < 7; i++) {
            eventsOfTheWeek.add(new LinkedList<Event>());
        }
        EventDate today = new EventDate(new Date());
        setDaysAndDatesList(today);
        getEventsOfTheWeek(eventArrayList, today);
    }

    public String getCalendarInfo() {
        return calendarInfo;
    }

    /**
     * Find all the events in the coming 7 days.
     *
     * @param eventArrayList List of all events.
     * @param today The current day.
     */
    private void getEventsOfTheWeek(ArrayList<Event> eventArrayList, EventDate today) {
        EventDate yesterday = new EventDate(new Date());
        yesterday.addDaysAndSetMidnight(-1);
        EventDate endOfWeek = new EventDate(new Date());
        endOfWeek.addDaysAndSetMidnight(6);
        EventDate thisDay = new EventDate(new Date());
        int thisDayNum = 0;
        thisDay.addDaysAndSetMidnight(thisDayNum);

        for (Event thisEvent : eventArrayList) {
            //if this event is within the next 6 days
            if ((thisEvent.getStartDate().compare(endOfWeek) <= 0) && (thisEvent.getStartDate().compare(yesterday) > 0)) {
                while ((thisEvent.getStartDate().compare(thisDay) > 0) && (thisDayNum < 7)) {
                    thisDayNum++;
                    thisDay.addDaysAndSetMidnight(thisDayNum);
                }
                if (thisDayNum < 7) eventsOfTheWeek.get(thisDayNum).offer(thisEvent);
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
    private void setDaysAndDatesList(EventDate today) {
        String dayOfToday = today.getEventJavaDate().toString().split(" ")[0];
        String[] weekdays = new String[]{"    <Monday>    ", "   <Tuesday>    ", "   <Wednsday>   ",
                "   <Thursday>   ", "    <Friday>    ", "   <Saturday>   ", "    <Sunday>    "};
        int startDay;
        if (dayOfToday.equals("Mon")) {
            startDay = 0;
        } else if (dayOfToday.equals("Tue")) {
            startDay = 1;
        } else if (dayOfToday.equals("Wed")) {
            startDay = 2;
        } else if (dayOfToday.equals("Thu")) {
            startDay = 3;
        } else if (dayOfToday.equals("Fri")) {
            startDay = 4;
        } else if (dayOfToday.equals("Sat")) {
            startDay = 5;
        } else {
            startDay = 6;
        }
        for(int i=0; i<7; i++) {
            this.dayOfWeek.add(weekdays[(startDay + i) % 7]);
        }

        EventDate tempDay = today;
        for (int i=0; i<7; i++) {
            tempDay.addDaysAndSetMidnight(i);
            String thisDate = tempDay.getUserInputDateString().split(" ")[0];
            this.dateOfWeek.add("   " + thisDate + "   ");
        }
    }

    public void setCalendarInfo() {
        String calendarInfo = "";
        int maxNumOfEvent = 0;
        for (Queue<Event> thisQue : eventsOfTheWeek) {
            if (thisQue.size() > maxNumOfEvent) {
                maxNumOfEvent = thisQue.size();
            }
        }

        // head of table
        calendarInfo += "________________________________________________________________________________________________________________________\n" +
                "|                                                  Events of the week                                                  |\n" +
                "________________________________________________________________________________________________________________________\n";

        // row of days
        for(int i=0; i<7; i++) {
            calendarInfo += "|" + this.dayOfWeek.get(i);
        }
        calendarInfo += "|\n";

        // row of dates
        for(int i=0; i<7; i++) {
            calendarInfo += "|" + this.dateOfWeek.get(i);
        }
        calendarInfo += "|\n" +
                "________________________________________________________________________________________________________________________\n";;

        // rows of events
        for(int idxOfEventRow=0; idxOfEventRow<maxNumOfEvent; idxOfEventRow++) {
            String[][] eventsLine = null;
            eventsLine = getEventsOfOneRow(idxOfEventRow);

            for (int row=0; row<3; row++) {
                for (int day=0; day<7; day++) {
                    calendarInfo += "|" + eventsLine[row][day];
                }
                calendarInfo += "|\n";
            }
        }

        calendarInfo += "|                |                |                |                |                |                |                |\n" +
                "________________________________________________________________________________________________________________________";
        this.calendarInfo = calendarInfo;
    }

    /**
     * Provides the events info in one (3) row, of 7 days.
     * If there is no event in a day, keep empty.
     *
     * @param idxOfEventRow the current row of calendar where we wish to get events' info
     * @return the String info of this row of events
     */
    private String[][] getEventsOfOneRow(int idxOfEventRow) {
        String[][] eventsLine = new String[3][7];
        String emptySection = "                ";
        for(int day=0; day<7; day++) {
            String thisTime = emptySection;
            String thisDescription = emptySection;
            String thisDashes = emptySection;
            if (!eventsOfTheWeek.get(day).isEmpty()) {
                Event tempEvent = eventsOfTheWeek.get(day).poll();

                //time
                String thisStartTime = null, thisEndTime = null;
                assert tempEvent != null;
                if (!(tempEvent.getStartDate()==null) &&
                        (tempEvent.getStartDate().getFormattedDateString().split(", ").length>2)) {
                    thisStartTime = tempEvent.getStartDate().getFormattedDateString().split(", ")[2];
                    thisTime = "* " + thisStartTime;
                    if (!(tempEvent.getEndDate()==null) &&
                            (tempEvent.getEndDate().getFormattedDateString().split(", ").length>2)) {
                        thisEndTime = tempEvent.getEndDate().getFormattedDateString().split(", ")[2];
                        thisTime += " ~ " + thisEndTime + " ";
                    }
                } else if (tempEvent.getType() == 'T') {
                    thisTime = "* TODO          ";
                }

                //description
                String tempDescription = tempEvent.getDescription();
                if (tempDescription.length() > 13) {
                    thisDescription = tempDescription.substring(0,13) + "...";
                } else {
                    String spaces = "";
                    for (int i=0; i<(16-tempDescription.length()); i++) {
                        spaces += " ";
                    }
                    thisDescription = tempDescription + spaces;
                }

                //dashes
                thisDashes = "----------------";
            }
            eventsLine[0][day] = thisTime;
            eventsLine[1][day] = thisDescription;
            eventsLine[2][day] = thisDashes;
        }
        return eventsLine;
    }
}
