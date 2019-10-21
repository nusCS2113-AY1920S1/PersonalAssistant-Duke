package UserElements;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubclasses.AssessmentSubclasses.Exam;
import Events.EventTypes.EventSubclasses.AssessmentSubclasses.Recital;
import Events.EventTypes.EventSubclasses.Concert;
import Events.EventTypes.EventSubclasses.RecurringEventSubclasses.Lesson;
import Events.EventTypes.EventSubclasses.RecurringEventSubclasses.Practice;
import Events.EventTypes.EventSubclasses.ToDo;
import Events.Formatting.EventDate;
import Events.Formatting.CalendarView;
import Events.Storage.ClashException;
import Events.Storage.EventList;
import Events.Storage.Storage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Represents a command that is passed via user input.
 * Multiple types of commands are possible, executed using switch case method.
 */
public class Command {

    /**
     * The String representing the type of command e.g add/delete event
     */
    protected String command;

    /**
     * The String representing the continuation of the command, if it exists.
     * Contains further specific instructions about the command passed e.g which event to add or delete
     */
    protected String continuation;

    /**
     * Creates a new command with the command type and specific instructions
     *
     * @param command      The Model_Class.Command type
     * @param continuation The Model_Class.Command specific instructions
     */
    public Command(String command, String continuation) {
        this.command = command;
        this.continuation = continuation;
    }

    /**
     * Creates a new command where only command param is passed.
     * Specific instructions not necessary for these types of commands.
     *
     * @param command The Model_Class.Command type
     */
    public Command(String command) {
        this.command = command;
        this.continuation = "";
    }

    /**
     * Executes the command stored.
     *
     * @param events  Class containing the list of events and all relevant methods.
     * @param ui      Class containing all relevant user interface instructions.
     * @param storage Class containing access to the storage file and related instructions.
     */
    public void execute(EventList events, UI ui, Storage storage) {
        boolean changesMade = true;
        switch (command) {
            case "list":
                listEvents(events, ui);
                changesMade = false;
                break;

            case "reminder":
                remindEvents(events, ui);
                changesMade = false;
                break;

            case "done":
                markEventAsDone(events, ui);
                break;

            case "delete":
                deleteEvent(events, ui);
                break;

            case "find":
                searchEvents(events, ui);
                changesMade = false;
                break;

            case "todo":
                createNewTodo(events, ui);
                break;

            case "lesson":
                createNewEvent(events, ui, 'L');
                break;

            case "concert":
                createNewEvent(events, ui, 'C');
                break;

            case "practice":
                createNewEvent(events, ui, 'P');
                break;

            case "exam":
                createNewEvent(events, ui, 'E');
                break;

            case "recital":
                createNewEvent(events, ui, 'R');
                break;

            case "view":
                viewEvents(events, ui);
                changesMade = false;
                break;

            case "check":
                checkFreeDays(events, ui);
                changesMade = false;
                break;

            case "reschedule":
                rescheduleEvents(events, ui);
                break;

            case "edit":
                editEvent(events, ui);
                break;

            case "calendar":
                printCalendar(events, ui);
                break;

            default:
                ui.printInvalidCommand();
                changesMade = false;
                break;
        }
        if (changesMade) {
            events.sortList();
            storage.saveToFile(events, ui);
        }
    }

    private void printCalendar(EventList events, UI ui) {
        CalendarView calendarView = new CalendarView(events);
        calendarView.setCalendarInfo();
        ui.printCalendar(calendarView.getStringForOutput());
    }

    /**
     * Command to edit an event in the list.
     */
    private void editEvent(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
        } else {
            String[] splitInfo = continuation.split("/");
            int eventIndex = Integer.parseInt(splitInfo[0]) - 1;
            String newDescription = splitInfo[1];
            events.editEvent(eventIndex, newDescription);
            ui.printEditedEvent(eventIndex + 1, events.getEvent(eventIndex));
        }
    }

    private void searchEvents(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
        } else {
            String searchKeyWords = continuation;
            String foundEvent = "";
            int viewIndex = 1;
            for (Event viewEvent : events.getEventArrayList()) {
                if (viewEvent.toString().contains(searchKeyWords)) {
                    foundEvent += viewIndex + ". " + viewEvent.toString() + "\n";
                }
                viewIndex++;
            }

            boolean isEventsFound = !foundEvent.isEmpty();
            ui.printFoundEvents(foundEvent, isEventsFound);
        }
    }

    /**
     * Finds the next 3 free days in the schedule and passes them to UI class to be printed.
     */
    public void checkFreeDays(EventList events, UI ui) {
        Calendar dayToCheckIfFree = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDay = formatter.format(dayToCheckIfFree.getTime());
        EventDate dayToCheckIfFreeObject = new EventDate(currentDay);
        Queue<String> daysFree = new LinkedList<>();
        int nextDays = 1;
        while (daysFree.size() <= 3) {
            boolean isFree = true;
            for (Event viewEvent : events.getEventArrayList()) {
                if (viewEvent.getStartDate().getFormattedDateString().equals(dayToCheckIfFreeObject.getFormattedDateString())) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                daysFree.add(dayToCheckIfFreeObject.getFormattedDateString());
            }
            dayToCheckIfFreeObject.addDaysAndSetMidnight(nextDays);
            nextDays++;
        }
        ui.printFreeDays(daysFree);
    }

    /**
     * Searches list for events found in a singular date, passes to UI for printing.
     */
    public void viewEvents(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
        } else {
            String dateToView = continuation;
            String foundEvent = "";
            int viewIndex = 1;
            EventDate findDate = new EventDate(dateToView);
            for (Event viewEvent : events.getEventArrayList()) {
                if (viewEvent.toString().contains(findDate.getFormattedDateString())) {
                    foundEvent += viewIndex + ". " + viewEvent.toString() + "\n";
                }
                viewIndex++;
            }
            boolean isEventsFound = !foundEvent.isEmpty();
            ui.printFoundEvents(foundEvent, isEventsFound);
        }
    }

    public void createNewEvent(EventList events, UI ui, char eventType) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
        } else {
            int NO_PERIOD = -1;

            try {
                EntryForEvent entryForEvent = new EntryForEvent().invoke(); //separate all info into relevant details
                Event newEvent = null;
                switch (eventType) {
                    case 'L':
                        newEvent = new Lesson(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                                entryForEvent.getEndDate());
                        break;
                    case 'C':
                        newEvent = new Concert(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                                entryForEvent.getEndDate());
                        break;
                    case 'P':
                        newEvent = new Practice(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                                entryForEvent.getEndDate());
                        break;
                    case 'E':
                        newEvent = new Exam(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                                entryForEvent.getEndDate());
                        break;
                    case 'R':
                        newEvent = new Recital(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                                entryForEvent.getEndDate());
                        break;
                }

                try {
                    if (entryForEvent.getPeriod() == NO_PERIOD) {
                        events.addEvent(newEvent);
                        ui.eventAdded(newEvent, events.getNumEvents());
                    } else {
                        assert newEvent != null;
                        events.addRecurringEvent(newEvent, entryForEvent.getPeriod());
                        ui.recurringEventAdded(newEvent, events.getNumEvents(), entryForEvent.getPeriod());
                    }
                } catch (ClashException e) {
                    ui.scheduleClash(e.getClashEvent());
                }

            } catch (StringIndexOutOfBoundsException outOfBoundsE) {
                ui.eventFormatWrong();
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.eventFormatWrong();
            }
        }
    }

    public void createNewTodo(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
            return;
        }
        EntryForEvent entryForEvent = new EntryForEvent().invoke(); //separate all info into relevant details
        Event newEvent = new ToDo(entryForEvent.getDescription(), entryForEvent.getStartDate());
        events.addNewTodo(newEvent);
        ui.eventAdded(newEvent, events.getNumEvents());
    }

    public void deleteEvent(EventList events, UI ui) {
        try {
            int eventNo = Integer.parseInt(continuation);
            Event currEvent = events.getEvent(eventNo - 1);
            events.deleteEvent(eventNo - 1);
            ui.eventDeleted(currEvent);
        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();
        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
        }
    }

    public void markEventAsDone(EventList events, UI ui) {
        try {
            int eventNo = Integer.parseInt(continuation);
            if (events.getEvent(eventNo - 1) instanceof ToDo) {
                events.getEvent(eventNo - 1).markAsDone();
                ui.eventDone(events.getEvent(eventNo - 1));
            } else {
                ui.noSuchEvent();
            }
        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();
        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
        }
    }

    public void rescheduleEvents(EventList events, UI ui) {
        try {
            String[] rescheduleDetail = continuation.split(" ");
            int eventIndex = Integer.parseInt(rescheduleDetail[0]) - 1;
            Event tempEvent = events.getEvent(eventIndex);
            events.deleteEvent(eventIndex);
            EventDate newStartDate;
            EventDate newEndDate;

            if (rescheduleDetail.length > 2) {
                newStartDate = new EventDate(rescheduleDetail[1] + " " + rescheduleDetail[2]);
                newEndDate = new EventDate(rescheduleDetail[1] + " " + rescheduleDetail[3]);

            } else {
                newStartDate = new EventDate(rescheduleDetail[1]);
                newEndDate = null;
            }

            tempEvent.rescheduleStartDate(newStartDate);
            tempEvent.rescheduleEndDate(newEndDate);

            try {
                events.addEvent(tempEvent);
                ui.rescheduleEvent(tempEvent);
            } catch (ClashException clashE) {
                ui.scheduleClash(clashE.getClashEvent());
            }

        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();

        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
        }
    }

    public void remindEvents(EventList events, UI ui) {
        ui.printReminder(events);
    }

    public void listEvents(EventList events, UI ui) {
        ui.printListOfEvents(events);
    }

    /**
     * Contains all info concerning a new entry an event.
     */
    private class EntryForEvent {
        private String description;
        private String startDate;
        private String endDate;
        private int period; //recurring period. -1(NON_RECURRING) if non-recurring.

        public String getDescription() {
            return description;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public int getPeriod() {
            return period;
        }

        /**
         * contains all info regarding an entry for a non-recurring event
         *
         * @return organized entryForEvent information
         */
        public EntryForEvent invoke() {
            int NON_RECURRING = -1;
            String[] splitEvent = continuation.split("/");
            description = splitEvent[0];

            String date = splitEvent[1];
            String[] splitDate = date.split(" ");

            if (splitDate.length == 3) {
                startDate = splitDate[0] + " " + splitDate[1];
                endDate = splitDate[0] + " " + splitDate[2];
            } else if (splitDate.length == 2) {
                startDate = splitDate[0] + " " + splitDate[1];
                endDate = "";
            } else {
                startDate = splitDate[0];
                endDate = "";
            }

            if (splitEvent.length == 2) {//cant find period extension of command, event is non-recurring
                period = NON_RECURRING;
            } else {
                period = Integer.parseInt(splitEvent[2]);
            }
            return this;
        }
    }
}