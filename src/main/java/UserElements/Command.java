package UserElements;

import Events.EventTypes.Event;
import Events.Formatting.DateObj;
import Events.Storage.EventList;
import Events.Storage.Storage;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     * @param events   Class containing the list of events and all relevant methods.
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
                searchForEvent(events, ui);
                changesMade = false;
                break;

            case "event":
                createNewEvent(events, ui);
                break;

            case "view":
                viewEvents(events, ui);
                changesMade = false;
                break;

            case "check":
                checkFreeDays(events, ui);
                break;

            default:
                ui.printInvalidCommand();
                break;
        }
        if (changesMade) {
            storage.saveToFile(events, ui);
        }
    }

    public void checkFreeDays(EventList events, UI ui) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        DateObj today = new DateObj(f.format(new Date()));
        Queue<String> daysFree = new LinkedList<String>();
        int addDays = 1;
        while(daysFree.size() <= 3) {
            boolean isFree = true;
            for(Event viewEvent : events.getEventArrayList()) {
                if(viewEvent.toString().contains(today.toOutputString())) {
                    isFree = false;
                    break;
                }
            }
            if(isFree) {
                daysFree.add(today.toOutputString());
            }
            today.addDays(addDays);
        }
        ui.printFreeDays(daysFree);
    }

    public void viewEvents(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
        } else {
            String dateToView = continuation;
            String foundEvent = "";
            int viewIndex = 1;
            DateObj findDate = new DateObj(dateToView);
            for (Event viewEvent : events.getEventArrayList()) {
                if (viewEvent.toString().contains(findDate.toOutputString())) {
                    foundEvent += viewIndex + ". " + viewEvent.toString() + "\n";
                    viewIndex++;
                }
            }
            boolean isEventsFound = !foundEvent.isEmpty();
            ui.searchEvents(foundEvent, isEventsFound);
        }
    }

    public void createNewEvent(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
        } else {
            int NO_PERIOD = -1;

            try {
                EntryForRecEvent entryForRecEvent = new EntryForRecEvent().invoke(); //separate all info into relevant details
                Event newEvent = new Event(entryForRecEvent.getDescription(), entryForRecEvent.getDate());
                boolean succeeded = false;

                if (entryForRecEvent.getPeriod() == NO_PERIOD) { //add non-recurring event
                    succeeded = events.addEvent(newEvent);
                } else { //add recurring event
                    succeeded = events.addRecurringEvent(newEvent, entryForRecEvent.getPeriod());
                }

                if (succeeded) {
                    if (entryForRecEvent.getPeriod() == NO_PERIOD) {
                        ui.eventAdded(newEvent, events.getNumEvents());
                    } else {
                        ui.recurringEventAdded(newEvent, events.getNumEvents(), entryForRecEvent.getPeriod());
                    }
                } else {
                    ui.scheduleClash(newEvent);
                }

            } catch (StringIndexOutOfBoundsException outOfBoundsE) {
                ui.eventFormatWrong();
            }
        }
    }

    public void searchForEvent(EventList events, UI ui) {
        String searchFor = continuation;
        String allEventsFound = "";
        int index = 1;
        for (Event eventFound : events.getEventArrayList()) {
            if (eventFound.getDescription().contains(searchFor)) {
                allEventsFound += index + ". " + eventFound.toString() + "\n";
            }
            index++;
        }

        boolean eventsFound = !allEventsFound.isEmpty();
        ui.searchEvents(allEventsFound, eventsFound);
    }

    public void deleteEvent(EventList events, UI ui) {
        try {
            int eventNo = Integer.parseInt(continuation);
            Event currEvent = events.getEvent(eventNo - 1);
            events.deleteEvent(eventNo - 1);
            ui.eventDeleted(currEvent);
            return;
        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();
            return;
        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
            return;
        }
    }

    public void markEventAsDone(EventList events, UI ui) {
        try {
            int eventNo = Integer.parseInt(continuation);
            events.getEvent(eventNo - 1).markAsDone();
            ui.eventDone(events.getEvent(eventNo - 1));
            return;
        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();
            return;
        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
            return;
        }
    }

    public void remindEvents(EventList events, UI ui) {
        ui.printReminder(events);
    }

    public void listEvents(EventList events, UI ui) {
        ui.printListOfEvents(events);
    }

    /**
     * Contains all info concerning a new entry for a recurring event.
     */
    private class EntryForRecEvent {
        private String description;
        private String date;
        private int period;

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public int getPeriod() {
            return period;
        }

        /**
         * contains all info regarding an entry for a non-recurring event
         * @return
         */
        public EntryForRecEvent invoke() {
            int NON_RECURRING = -1;
            int datePos = continuation.indexOf("/at"); //to find index of position and date
            int periodPos = continuation.indexOf("/every"); //to find index of position and period

            if (periodPos == -1) { //cant find period extension of command, event is non-recurring
                periodPos = continuation.length();
                date = continuation.substring(datePos + 4);
                period = NON_RECURRING;
            } else {
                period = Integer.parseInt(continuation.substring(periodPos + 7));
                date = continuation.substring(datePos + 4, periodPos);
            }
            description = continuation.substring(0, datePos);

            return this;
        }
    }
}