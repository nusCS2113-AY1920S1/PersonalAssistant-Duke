package mistermusik.logic;

import mistermusik.commons.Contact;
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Exam;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Recital;
import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Lesson;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Practice;
import mistermusik.commons.events.eventtypes.eventsubclasses.ToDo;
import mistermusik.commons.events.formatting.CalendarView;
import mistermusik.commons.events.formatting.DateStringValidator;
import mistermusik.commons.events.formatting.EventDate;
import mistermusik.storage.*;
import mistermusik.storage.Instruments.InstrumentList;
import mistermusik.commons.budgeting.CostExceedsBudgetException;
import mistermusik.commons.Goal;
import mistermusik.ui.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

//@@author
/**
 * Represents a command that is passed via user input.
 * Multiple types of commands are possible, executed using switch case method.
 */
public class Command {

    private static Logger logger = Logger.getLogger("Command");


    /**
     * The String representing the type of command e.g add/delete event
     */
    private String command;

    /**
     * The String representing the continuation of the command, if it exists.
     * Contains further specific instructions about the command passed e.g which event to add or delete
     */
    private String continuation;

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
    public void execute(EventList events, UI ui, Storage storage, InstrumentList instruments) {
        boolean changesMade = true;
//        logger.log(Level.INFO, "Read in the command");
        switch (command) {
            case "help":
                findHelp(ui);
                break;

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
                addNewTodo(events, ui);
                break;

            case "lesson":
                addNewEvent(events, ui, 'L');
                break;

            case "concert":
                addNewEvent(events, ui, 'C');
                break;

            case "practice":
                addNewEvent(events, ui, 'P');
                break;

            case "exam":
                addNewEvent(events, ui, 'E');
                break;

            case "recital":
                addNewEvent(events, ui, 'R');
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
                rescheduleEvent(events, ui);
                break;

            case "edit":
                editEvent(events, ui);
                break;

            case "calendar":
                printCalendar(events, ui);
                break;

            case "budget":
                showBudget(events, ui);
                break;

            case "goal":
                goalsManagement(events, ui);
                break;

            case "contact":
                contactManagement(events, ui);
                break;

            case "checklist":
                checklistManagement(events, ui);
                break;

            case "instrument":
                instrumentManagement(instruments, ui);
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

    private void findHelp(UI ui) {
        if (continuation.isEmpty()) {
            ui.printHelpList();
        } else {
            switch (continuation) {
                case "calendar":
                    ui.printCalendarHelp();
                    break;
                case "lesson":
                case "practice":
                case "concert":
                case "exam":
                case "recital":
                case "todo":
                case "delete":
                case "event":
                    ui.printEventHelp();
                    break;
                case "goal":
                    ui.printGoalHelp();
                    break;
                case "contact":
                    ui.printContactHelp();
                    break;
                case "checklist":
                    ui.printChecklistHelp();
                    break;
                case "reschedule":
                case "edit":
                case "done":
                case "change":
                    ui.printChangeHelp();
                    break;
                default:
                    ui.printHelpList();
            }
        }

    }

    private void checklistManagement(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.checklistCommandWrongFormat();
//            logger.log(Level.WARNING, "The description of checklistManagement is empty");
        } else {
            try {
                String[] splitChecklist = continuation.split("/");
                String[] checklistCommand = splitChecklist[0].split(" ");
                int eventIndex = Integer.parseInt(checklistCommand[1]) - 1;
//                if(!((events.getEvent(eventIndex).getType()=='P') || (events.getEvent(eventIndex).getType()=='L'))) {
//                    ui.noSuchEvent();
//                    return;
//                }
                if (checklistCommand.length == 3) {
                    int checklistIndex = Integer.parseInt(checklistCommand[2]);
                    switch (checklistCommand[0]) {
                        case "delete":
                            events.getEvent(eventIndex).deleteChecklist(checklistIndex - 1);
                            ui.checklistDeleted(eventIndex);
                            break;

                        case "edit":
                            events.getEvent(eventIndex).editChecklist(checklistIndex - 1, splitChecklist[1]);
                            ui.checklistEdited(splitChecklist[1], eventIndex);
                            break;
                    }
                } else {
                    switch (checklistCommand[0]) {
                        case "add":
                            events.getEvent(eventIndex).addChecklist(splitChecklist[1]);
                            System.out.println(splitChecklist[1] + "___" + eventIndex);
                            ui.checklistAdded(splitChecklist[1], eventIndex);
                            break;

                        case "view":
                            //print goals list
                            ArrayList<String> thisChecklist = events.getEvent(eventIndex).getChecklist();
                            ui.printEventChecklist(thisChecklist, eventIndex, events.getEvent(eventIndex));
                            break;
                    }
                }
            } catch (IndexOutOfBoundsException ne) {
                ui.noSuchEvent();
//                logger.log(Level.WARNING, ne.getMessage(), ne);
            } catch (NumberFormatException numE) {
                ui.notAnInteger();
//                logger.log(Level.WARNING, numE.getMessage(), numE);
            }
        }
    }

    private void printCalendar(EventList events, UI ui) {
        CalendarView calendarView = null;
        if (continuation.isEmpty()) {
            EventDate today = new EventDate(new Date());
            calendarView = new CalendarView(events, today);
        } else if (continuation.equals("next")) {
            EventDate nextWeek = new EventDate(new Date());
            nextWeek.addDaysAndSetMidnight(7);
            calendarView = new CalendarView(events, nextWeek);
        } else if (continuation.equals("last")) {
            EventDate lastWeek = new EventDate(new Date());
            lastWeek.addDaysAndSetMidnight(-7);
            calendarView = new CalendarView(events, lastWeek);
        } else {
            ui.calendarCommandWrongFormat();
        }

        calendarView.setCalendarInfo();
        ui.printCalendar(calendarView.getStringForOutput());
    }

    /**
     * Command to edit an event in the list.
     */
    private void editEvent(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
//            logger.log(Level.WARNING, "The description of editEvent is empty");
        } else {
            String[] splitInfo = continuation.split("/");
            int eventIndex = Integer.parseInt(splitInfo[0]) - 1;
            String newDescription = splitInfo[1];
            events.editEvent(eventIndex, newDescription);
            ui.printEditedEvent(eventIndex + 1, events.getEvent(eventIndex));
        }
    }

    //@@author Ryan-Wong-Ren-Wei
    /**
     * passes budget to UI for printing to output
     */
    private void showBudget(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.budgetCommandWrongFormat();
//            logger.log(Level.WARNING, "The description of showBudget is empty");
        } else {
            String monthAndYear = continuation;
            try {
                int cost = events.getBudgeting().getCostForMonth(monthAndYear);
                UI.printCostForMonth(monthAndYear, cost);
                //NEED TO PRINT COST HERE!
            } catch (NullPointerException e) {
                UI.printNoCostsForThatMonth();
//                logger.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    //@@author
    private void searchEvents(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
//            logger.log(Level.WARNING, "The description of searchEvents is empty");
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
    private void checkFreeDays(EventList events, UI ui) {
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
            dayToCheckIfFreeObject.addDaysAndSetMidnight(1);
            nextDays++;
        }
        ui.printFreeDays(daysFree);
    }

    /**
     * Searches list for events found in a singular date, passes to UI for printing.
     */
    private void viewEvents(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.viewCommandWrongFormat();
//            logger.log(Level.WARNING, "The description of viewEvents is empty");
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

    //@@author Ryan-Wong-Ren-Wei
    private void addNewEvent(EventList events, UI ui, char eventType) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
//            logger.log(Level.WARNING, "The description of the addNewEvent is empty");
        } else {
            int NO_PERIOD = -1;

            try {
                EntryForEvent entryForEvent = new EntryForEvent().invoke(); //separate all info into relevant details
                Event newEvent = newEvent(eventType, entryForEvent); //instantiate new event
                assert newEvent != null;

                if (entryForEvent.getPeriod() == NO_PERIOD) { //non-recurring

                    events.addEvent(newEvent);
                    ui.eventAdded(newEvent, events.getNumEvents());

                } else { //recurring
                    events.addRecurringEvent(newEvent, entryForEvent.getPeriod());
                    ui.recurringEventAdded(newEvent, events.getNumEvents(), entryForEvent.getPeriod());
                }

            } catch (ClashException e) { //clash found
                ui.scheduleClash(e.getClashEvent());
//                logger.log(Level.WARNING, e.getMessage(), e);
            } catch (CostExceedsBudgetException e) { //budget exceeded in attempt to add concert
                ui.costExceedsBudget(e.getConcert(), e.getBudget());
//                logger.log(Level.WARNING, e.getMessage(), e);
            } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException | ParseException e) {
                ui.newEntryFormatWrong();
//                logger.log(Level.WARNING, e.getMessage(), e);
            } catch (EndBeforeStartException e) { //start time is after end time
                ui.eventEndsBeforeStart();
//                logger.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    //@@author Ryan-Wong-Ren-Wei
    /**
     * Instantiates a new event based on details passed as parameter
     *
     * @param entryForEvent contains all necessary info for creating new event
     * @return instantiated event
     */
    private Event newEvent(char eventType, EntryForEvent entryForEvent) {
        Event newEvent = null;
        switch (eventType) {
            case 'L':
                newEvent = new Lesson(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                        entryForEvent.getEndDate());
                break;
            case 'C':
                newEvent = new Concert(entryForEvent.getDescription(), false, entryForEvent.getStartDate(),
                        entryForEvent.getEndDate(), entryForEvent.getCost());
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
//        logger.log(Level.INFO, "New event is created");
        return newEvent;
    }

    private void addNewTodo(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.eventDescriptionEmpty();
//            logger.log(Level.WARNING, "The description of createNewTodo is empty");
            return;
        }
        try {
            EntryForToDo entryForToDo = new EntryForToDo().invoke(); //separate all info into relevant details
            Event newToDo = new ToDo(entryForToDo.getDescription(), entryForToDo.getDate());
            events.addNewTodo(newToDo);
            ui.eventAdded(newToDo, events.getNumEvents());
        } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException | ParseException e) {
            ui.newEntryFormatWrong();
        }
    }

    //@@author
    private void deleteEvent(EventList events, UI ui) {
        try {
            int eventNo = Integer.parseInt(continuation);
            Event currEvent = events.getEvent(eventNo - 1);
            events.deleteEvent(eventNo - 1);
            ui.eventDeleted(currEvent);
        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();
//            logger.log(Level.WARNING, outOfBoundsE.getMessage(), outOfBoundsE);
        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
//            logger.log(Level.WARNING, notInteger.getMessage(), notInteger);
        }
    }

    private void markEventAsDone(EventList events, UI ui) {
        try {
            int eventNo = Integer.parseInt(continuation);
            if (events.getEvent(eventNo - 1) instanceof ToDo) {
                events.getEvent(eventNo - 1).markAsDone();
                ui.eventDone(events.getEvent(eventNo - 1));
            } else {
                ui.noSuchEvent();
//                logger.log(Level.INFO, "Do not have the event in the list");
            }
        } catch (IndexOutOfBoundsException outOfBoundsE) {
            ui.noSuchEvent();
//            logger.log(Level.WARNING, outOfBoundsE.getMessage(), outOfBoundsE);
        } catch (NumberFormatException notInteger) {
            ui.notAnInteger();
//            logger.log(Level.WARNING, notInteger.getMessage(), notInteger);
        }
    }

    //@@author YuanJiayi

    /**
     * Reschedules the date and time of an existing event.
     *
     * @param events The event list.
     */
    private void rescheduleEvent(EventList events, UI ui) {
        Event newEvent;
        EventDate copyOfStartDate;
        EventDate copyOfEndDate;
        try {
            String[] rescheduleDetail = continuation.split(" "); //split details by space (dd-MM-yyyy HHmm HHmm)
            int eventIndex = Integer.parseInt(rescheduleDetail[0]) - 1;

            newEvent = events.getEvent(eventIndex); //event to be used as a replacement.

            copyOfStartDate = new EventDate(newEvent.getStartDate().getUserInputDateString());
            copyOfEndDate = new EventDate(newEvent.getEndDate().getUserInputDateString());

            EventDate newStartDate = new EventDate(rescheduleDetail[1] + " " + rescheduleDetail[2]);
            EventDate newEndDate = new EventDate(rescheduleDetail[1] + " " + rescheduleDetail[3]);

            events.deleteEvent(eventIndex); //delete event from list before continuing

            newEvent.rescheduleStartDate(newStartDate); //reschedule start date & time
            newEvent.rescheduleEndDate(newEndDate); //reschedule end date & time

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            ui.rescheduleFormatWrong();
//            logger.log(Level.WARNING, e.getMessage(), e);
            return;
        }

        try {
            events.addEvent(newEvent);
            ui.rescheduleEvent(newEvent);
        } catch (ClashException clashE) {
            ui.scheduleClash(clashE.getClashEvent());
            newEvent.rescheduleStartDate(copyOfStartDate);
            newEvent.rescheduleEndDate(copyOfEndDate);
            events.undoDeletionOfEvent(newEvent);
//            logger.log(Level.WARNING, clashE.getMessage(), clashE);
        } catch (CostExceedsBudgetException e) {
            ui.costExceedsBudget(e.getConcert(), e.getBudget());
            newEvent.rescheduleStartDate(copyOfStartDate);
            newEvent.rescheduleEndDate(copyOfEndDate);
            events.undoDeletionOfEvent(newEvent);
//            logger.log(Level.WARNING, e.getMessage(), e);
        } catch (Exception e) {
            ui.eventEndsBeforeStart();
            newEvent.rescheduleStartDate(copyOfStartDate);
            newEvent.rescheduleEndDate(copyOfEndDate);
            events.undoDeletionOfEvent(newEvent);
//            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    //@@author yenpeichih

    /**
     * Manages the goals of an existing event.
     *
     * @param events The event list.
     */
    private void goalsManagement(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.goalCommandWrongFormat();
//            logger.log(Level.INFO, "The description of goalManagement is empty");
            return;
        }
        try {
            String[] splitGoal = continuation.split("/");
            String[] goalCommand = splitGoal[0].split(" ");
            int eventIndex = Integer.parseInt(goalCommand[1]) - 1;
            if (goalCommand.length == 3) {
                int goalIndex = Integer.parseInt(goalCommand[2]);
                switch (goalCommand[0]) {
                    case "delete":
                        if (!events.getEvent(eventIndex).getGoalList().isEmpty()) {
                            events.getEvent(eventIndex).removeGoal(goalIndex - 1);
                            ui.goalDeleted();
                        } else {
                            ui.noSuchGoal();
                        }
                        break;

                    case "edit":
                        if (!events.getEvent(eventIndex).getGoalList().isEmpty()) {
                            Goal newGoal = new Goal(splitGoal[1]);
                            events.getEvent(eventIndex).editGoalList(newGoal, goalIndex - 1);
                            ui.goalUpdated();
                        } else {
                            ui.noSuchGoal();
                        }
                        break;

                    case "achieved":
                        if (!events.getEvent(eventIndex).getGoalList().isEmpty()) {
                            events.getEvent(eventIndex).updateGoalAchieved(goalIndex - 1);
                            ui.goalSetAsAchieved();
                        } else {
                            ui.noSuchGoal();
                        }
                        break;
                }
            } else {
                switch (goalCommand[0]) {
                    case "add":
                        Goal newGoal = new Goal(splitGoal[1]);
                        events.getEvent(eventIndex).addGoal(newGoal);
                        ui.goalAdded();
                        break;

                    case "view":
                        ui.printEventGoals(events.getEvent(eventIndex));
                        break;
                }
            }
        } catch (IndexOutOfBoundsException ne) {
            ui.noSuchEvent();
//            logger.log(Level.WARNING, ne.getMessage(), ne);
        } catch (NumberFormatException numE) {
            ui.notAnInteger();
//            logger.log(Level.WARNING, numE.getMessage(), numE);
        }
    }

    //@@author YuanJiayi

    /**
     * Manage the contacts of an existing event.
     *
     * @param events The event list.
     */
    private void contactManagement(EventList events, UI ui) {
        if (continuation.isEmpty()) {
            ui.contactCommandWrongFormat();
//            logger.log(Level.WARNING, "The description of contactManagement is empty");
            return;
        }
        try {
            String[] splitContact = continuation.split("/");
            String[] contactCommand = splitContact[0].split(" ");
            int eventIndex = Integer.parseInt(contactCommand[1]) - 1;
            if (contactCommand.length == 2) {
                switch (contactCommand[0]) {
                    case "add":
                        String[] contactDetails = splitContact[1].split(",");
                        Contact newContact = new Contact(contactDetails[0], contactDetails[1], contactDetails[2]);
                        events.getEvent(eventIndex).addContact(newContact);
                        ui.contactAdded();
                        break;

                    case "view":
                        if (events.getEvent(eventIndex).getContactList().isEmpty()) {
                            ui.noContactInEvent();
                        } else {
                            ui.printEventContacts(events.getEvent(eventIndex));
                        }
                        break;
                }
            } else {
                int contactIndex = Integer.parseInt(contactCommand[2]) - 1;
                switch (contactCommand[0]) {
                    case "delete":
                        events.getEvent(eventIndex).removeContact(contactIndex);
                        ui.contactDeleted();
                        break;
                    case "edit":
                        char editType = ' ';
                        switch (contactCommand[3]) {
                            case "name":
                                editType = 'N';
                                break;
                            case "email":
                                editType = 'E';
                                break;
                            case "phone":
                                editType = 'P';
                                break;
                        }
                        events.getEvent(eventIndex).editContact(contactIndex, editType, splitContact[1]);
                        ui.contactEdited(events.getEvent(eventIndex).getContactList().get(contactIndex));
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            ui.noSuchEvent();
//            logger.log(Level.WARNING, e.getMessage(), e);
        } catch (NumberFormatException en) {
            ui.notAnInteger();
//            logger.log(Level.WARNING, en.getMessage(), en);
        }
    }

    //@@author Dng132FEI
    public void instrumentManagement(InstrumentList instruments, UI ui) {
        try {
            if (continuation.isEmpty()) {
                ui.noSuchEvent();
//	            logger.log(Level.WARNING, "The description of instrumentManagement is empty");
                return;
            }
            String splitInstrument[] = continuation.split("/");
            String instrumentCommand[] = continuation.split(" ");
            int instrumentIndex;
            String instrumentIndexAndName;
            switch (instrumentCommand[0]) {
                case "add":
                    instrumentIndex = instruments.addInstrument(splitInstrument[1]);
                    instrumentIndexAndName = instruments.getIndexAndInstrument(instrumentIndex);
                    ui.instrumentAdded(instrumentIndexAndName);
                    break;
                case "service":
                    instrumentIndex = Integer.parseInt(instrumentCommand[1]);
                    EventDate inputDate = new EventDate(splitInstrument[2]);
                    int serviceIndex = instruments.service(instrumentIndex, inputDate, splitInstrument[1]);
                    instrumentIndexAndName = instruments.getIndexAndInstrument(instrumentIndex);
                    String serviceIndexAndName = instruments.getIndexAndService(instrumentIndex, serviceIndex);
                    ui.serviceAdded(serviceIndexAndName, instrumentIndexAndName);
                    break;
                case "view":
                    switch (instrumentCommand[1]) {
                        case "instruments":
                            String listOfInstruments = instruments.getInstruments();
                            ui.printInstruments(listOfInstruments);
                            break;
                        case "services":
                            instrumentIndex = Integer.parseInt(instrumentCommand[2]);
                            String listOfServices = instruments.getInstrumentServiceInfo(instrumentIndex);
                            instrumentIndexAndName = instruments.getIndexAndInstrument(instrumentIndex);
                            ui.printServices(listOfServices, instrumentIndexAndName);
                            break;
                    }
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            ui.noSuchEvent();
//            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    //@@author
    private void remindEvents(EventList events, UI ui) {
        ui.printReminder(events);
    }

    private void listEvents(EventList events, UI ui) {
        UI.printListOfEvents(events);
    }

    //@@author Ryan-Wong-Ren-Wei
    /**
     * Contains all info concerning a new entry an event.
     */
    private class EntryForEvent {
        private String description;
        private String startDate;
        private String endDate;
        private int cost; //only for concert events
        private int period; //recurring period. -1(NON_RECURRING) if non-recurring.

        public String getDescription() {
            return description;
        }

        String getStartDate() {
            return startDate;
        }

        String getEndDate() {
            return endDate;
        }

        int getPeriod() {
            return period;
        }

        int getCost() {
            return cost;
        }

        /**
         * contains all info regarding an entry for a non-recurring event
         *
         * @return organized entryForEvent information
         */
        private EntryForEvent invoke() throws ParseException {
            int NON_RECURRING = -1;
            String[] splitEvent = continuation.split("/");
            description = splitEvent[0];

            String date = splitEvent[1];
            String[] splitDate = date.split(" ");

            if (splitDate.length == 3) {
                startDate = splitDate[0] + " " + splitDate[1];
                endDate = splitDate[0] + " " + splitDate[2];
            }

            if (!DateStringValidator.isValidDateForEvent(startDate) ||
                    !DateStringValidator.isValidDateForEvent(endDate)) {

                throw new ParseException("Invalid date for Event", 0);
            }

            if (splitEvent.length == 2) {//cant find period extension of command, event is non-recurring
                period = NON_RECURRING;
            } else {
                if (command.equals("concert")) {
                    cost = Integer.parseInt(splitEvent[2]);
                    period = NON_RECURRING;
                } else {
                    period = Integer.parseInt(splitEvent[2]);
//                    logger.log(Level.INFO, "The event to be added is recurring");
                }
            }
            return this;
        }
    }

    /**
     * Contains all info concerning a new entry for a ToDo
     */
    private class EntryForToDo {
        private String description;
        private String date;

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        /**
         * contains all info regarding an entry for a ToDo
         *
         * @return organized entryForEvent information
         */
        public EntryForToDo invoke() throws ParseException{
            String[] splitEvent = continuation.split("/");
            description = splitEvent[0];
            date = splitEvent[1];
            if (DateStringValidator.isValidDateForToDo(date)) {
                return this;
            } else {
                throw new ParseException("Date is invalid", 0);
            }
        }
    }
}
