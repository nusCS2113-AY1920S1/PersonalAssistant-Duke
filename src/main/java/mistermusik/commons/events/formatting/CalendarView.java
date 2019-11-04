//@@author ZhangYihanNus
package mistermusik.commons.events.formatting;

import mistermusik.commons.events.eventtypes.Event;
import mistermusik.logic.EventList;

import java.util.*;

public class CalendarView {
    private List<Queue<Event>> eventsOfTheWeek = new ArrayList<>(7);

    private ArrayList<String> daysToDisplay = new ArrayList<>();
    public ArrayList<String> datesToDisplay = new ArrayList<>();
    private String stringForOutput;
    static final int MONDAY = 0, TUESDAY = 1, WEDNESDAY = 2, THURSDAY = 3, FRIDAY = 4, SATURDAY = 6, SUNDAY = 7;

    public CalendarView(EventList eventList, EventDate startDate) {
        ArrayList<Event> eventArrayList = eventList.getEventArrayList();
        for (int i = 0; i < 7; i++) {
            eventsOfTheWeek.add(new LinkedList<>());
        }
        setDaysAndDatesList(startDate);
        getEventsOfTheWeek(eventArrayList, startDate);
    }

    public String getStringForOutput() {
        return stringForOutput;
    }

    /**
     * Find all the events in the coming 7 days.
     *
     * @param eventArrayList List of all events.
     * @param startDay          The current day.
     */
    private void getEventsOfTheWeek(ArrayList<Event> eventArrayList, EventDate startDay) {
        EventDate yesterday = new EventDate(startDay.getEventJavaDate());
        yesterday.addDaysAndSetMidnight(-1);
        EventDate endOfWeek = new EventDate(startDay.getEventJavaDate());
        endOfWeek.addDaysAndSetMidnight(6);
        EventDate thisDay = new EventDate(startDay.getEventJavaDate());
        thisDay.addDaysAndSetMidnight(0);

        int currentlyChecking = 0;
        for (Event thisEvent : eventArrayList) {
            //if this event is within the next 6 days
            if ((thisEvent.getStartDate().compare(endOfWeek) <= 0) && (thisEvent.getStartDate().compare(yesterday) > 0)) {
                while ((thisEvent.getStartDate().compare(thisDay) > 0) && (currentlyChecking < 7)) {
                    currentlyChecking++;
                    thisDay.addDaysAndSetMidnight(1);
                }
                if (currentlyChecking < 7) {
                    eventsOfTheWeek.get(currentlyChecking).offer(thisEvent);
                }
            } else if (thisEvent.getStartDate().compare(yesterday) <= 0) {
                continue;
            } else {
                break;
            }
        }
    }

    /**
     * Sets the two ArrayLists containing days and dates info for the coming 7 days.
     *
     * @param startDate The current day.
     */
    private void setDaysAndDatesList(EventDate startDate) {
        String currDay = startDate.getEventJavaDate().toString().split(" ")[0];
        String[] weekdays = new String[] {"    <Monday>    ", "   <Tuesday>    ", "   <Wednesday>  ",
                "   <Thursday>   ", "    <Friday>    ", "   <Saturday>   ", "    <Sunday>    "};

        int startDay = 0;
        switch (currDay) {
            case "Mon":
                startDay = MONDAY;
                break;

            case "Tue":
                startDay = TUESDAY;
                break;

            case "Wed":
                startDay = WEDNESDAY;
                break;

            case "Thu":
                startDay = THURSDAY;
                break;

            case "Fri":
                startDay = FRIDAY;
                break;

            case "Sat":
                startDay = SATURDAY;
                break;

            case "Sun":
                startDay = SUNDAY;
                break;
        }

        for (int i = 0; i < 7; i++) {
            this.daysToDisplay.add(weekdays[(startDay + i) % 7]);
        }

        EventDate tempDay = new EventDate(startDate.getEventJavaDate());
        for (int i = 0; i < 7; i++) {
            String thisDate = tempDay.getUserInputDateString().split(" ")[0];
            this.datesToDisplay.add("   " + thisDate + "   ");
            tempDay.addDaysAndSetMidnight(1);
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
        for (int i = 0; i < 7; i++) {
            calendarInfo += "|" + this.daysToDisplay.get(i);
        }
        calendarInfo += "|\n";

        // row of dates
        for (int i = 0; i < 7; i++) {
            calendarInfo += "|" + this.datesToDisplay.get(i);
        }
        calendarInfo += "|\n" +
                "________________________________________________________________________________________________________________________\n";

        // rows of events
        for (int idxOfEventRow = 0; idxOfEventRow < maxNumOfEvent; idxOfEventRow++) {
            String[][] eventsLine;
            eventsLine = getEventsOfOneRow(idxOfEventRow);

            for (int row = 0; row < 3; row++) {
                for (int day = 0; day < 7; day++) {
                    calendarInfo += "|" + eventsLine[row][day];
                }
                calendarInfo += "|\n";
            }
        }

        calendarInfo += "|                |                |                |                |                |                |                |\n" +
                "________________________________________________________________________________________________________________________";
        this.stringForOutput = calendarInfo;
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
        for (int day = 0; day < 7; day++) {
            String thisTime = emptySection;
            String thisDescription = emptySection;
            String thisDashes = emptySection;
            if (!eventsOfTheWeek.get(day).isEmpty()) {
                Event tempEvent = eventsOfTheWeek.get(day).poll();

                //time
                String thisStartTime, thisEndTime;
                assert tempEvent != null;
                if (!(tempEvent.getStartDate() == null) &&
                        (tempEvent.getStartDate().getFormattedDateString().split(", ").length > 2)) {
                    thisStartTime = tempEvent.getStartDate().getFormattedDateString().split(", ")[2];
                    thisTime = "* " + thisStartTime;
                    if (!(tempEvent.getEndDate() == null) &&
                            (tempEvent.getEndDate().getFormattedDateString().split(", ").length > 2)) {
                        thisEndTime = tempEvent.getEndDate().getFormattedDateString().split(", ")[2];
                        thisTime += " ~ " + thisEndTime + " ";
                    }
                } else if (tempEvent.getType() == 'T') {
                    thisTime = "* TODO          ";
                }

                //description
                String tempDescription = tempEvent.getDescription();
                if (tempDescription.length() > 13) {
                    thisDescription = tempDescription.substring(0, 13) + "...";
                } else {
                    String spaces = "";
                    for (int i = 0; i < (16 - tempDescription.length()); i++) {
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
