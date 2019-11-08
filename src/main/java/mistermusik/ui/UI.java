package mistermusik.ui;

import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;
import mistermusik.commons.Contact;
import mistermusik.logic.EventList;
import mistermusik.commons.Goal;

import java.util.ArrayList;
import java.util.Queue;


/**
 * User interface: contains all methods pertaining to user interaction.
 */
public class UI {
    private static String lineSeparation = "____________________________________________________________\n";

    /**
     * prints welcome message and instructions for use.
     */
    public void welcome() {
        String logo = " ___    ___   __________    __________   __________   __________   _________" + "\n"
                + "|   \\  /   | |___    ___|  /  ________| |___    ___| |   _______| |   ____  \\" + "\n"
                + "| |\\ \\/ /| |     |  |     /  /              |  |     |  |         |  |    \\  \\" + "\n"
                + "| | \\  / | |     |  |     \\  \\_______       |  |     |  |____     |  |____/  /" + "\n"
                + "| |  \\/  | |     |  |      \\_______  \\      |  |     |   ____|    |   ___   /" + "\n"
                + "| |      | |     |  |              \\  \\     |  |     |  |         |  |   \\  \\" + "\n"
                + "| |      | |  ___|  |___   ________/  /     |  |     |  |_______  |  |    |  |" + "\n"
                + "|_|      |_| |__________| |__________/      |__|     |__________| |__|    |__|" + "\n"
                + " ___    ___   __      __    __________   __________   __      __ " + "\n"
                + "|   \\  /   | |  |    |  |  /  ________| |___    ___| |  |    /  /" + "\n"
                + "| |\\ \\/ /| | |  |    |  | /  /              |  |     |  |   /  /" + "\n"
                + "| | \\  / | | |  |    |  | \\  \\_______       |  |     |  |__/  /" + "\n"
                + "| |  \\/  | | |  |    |  |  \\_______  \\      |  |     |   __  |" + "\n"
                + "| |      | | |  |    |  |          \\  \\     |  |     |  |  \\  \\" + "\n"
                + "| |      | | |  |____|  |  ________/  /  ___|  |___  |  |   \\  \\" + "\n"
                + "|_|      |_| |__________| |__________/  |__________| |__|    \\__\\" + "\n";
        System.out.println(logo);
        System.out.println(lineSeparation + "Hello! I'm MisterMusik!\nWhat can I do for you?\n");
        printHelpList();
        System.out.println("Please enter the command: ");
    }

    public void printHelpList() {
        System.out.print(lineSeparation);
        System.out.println("Notice: Words in <> are the parameters to be supplied by the user;");
        System.out.println("        Items in [] are optional;");
        System.out.println("        Items with | in between them indicate the user can choose to use either of them");
        System.out.println("-----Basic Commands-----");
        System.out.println("1.  \"help\"                     -- Print out all the commands you can input.");
        System.out.println("2.  \"list\"                     -- Print out all the events in the list.");
        System.out.println("3.  \"reminder [number of days]\"-- Display the list of events over the next given number (default 3) days.");
        System.out.println("4.  \"check\"                    -- Print the next 3 free days.");
        System.out.println("5.  \"find <keyword>\"           -- Search for a specific event using keywords.");
        System.out.println("6.  \"view dd-MM-yyyy\"          -- Print the event list for a particular date.");
        System.out.println("7.  \"budget MM-yyyy\"           -- View monthly cost of concerts");
        System.out.println("8.  \"budget set <new budget>\"  -- Set new monthly budget");
        System.out.println("9.  \"bye\"                      -- Exit the program.");
        System.out.println("-----More Commands-----");
        System.out.println("1.  \"help calendar\"   -- To see commands about calendar.");
        System.out.println("2.  \"help event\"      -- To see commands about how to add or delete event.");
        System.out.println("3.  \"help goal\"       -- To see commands about goal management of an event.");
        System.out.println("4.  \"help contact\"    -- To see commands about contact management of an event.");
        System.out.println("5.  \"help checklist\"  -- To see commands about checklist management of an event.");
        System.out.println("6.  \"help instruments\"-- To see commands about instruments management");
        System.out.println("7.  \"help change\"     -- To see commands about changing basic information of an event.");
        System.out.print(lineSeparation);
    }

    public void printCalendarHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"calendar\"      -- Print the calendar for the current week.");
        System.out.println("2.  \"calendar last\" -- Print the calendar for the last week.");
        System.out.println("3.  \"calendar next\" -- Print the calendar for the next week.");
        System.out.print(lineSeparation);
    }

    public void printEventHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"todo <description> /dd-MM-yyyy [HHmm]\"                                  \n\t-- " +
                "Add a todo task to the list.");
        System.out.println("2.  \"exam|recital|lesson|practice <description> /dd-MM-yyyy HHmm HHmm\"       \n\t-- " +
                "Add an event to the list.");
        System.out.println("3.  \"concert <description> /dd-MM-yyyy HHmm HHmm [/<cost of concert>]\"       \n\t-- " +
                "Add a concert to the list.");
        System.out.println("4.  \"practice|lesson <description> /dd-MM-yyyy HHmm HHmm /<period(in days)>\" \n\t-- " +
                "Add recurring events with the first event date to the list.");
        System.out.println("5.  \"delete <event index>\"                                                   \n\t-- " +
                "Delete the specific event.");
        System.out.print(lineSeparation);
    }

    public void printGoalHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"goal add <event index> /<goal>\"                   \n\t-- " +
                "Add a goal to a specific event.");
        System.out.println("2.  \"goal delete <event index> <goal>\"                 \n\t-- Delete a specified goal.");
        System.out.println("3.  \"goal edit <event index> <goal index> /<new goal>\" \n\t-- Edit an existing goal.");
        System.out.println("4.  \"goal achieved <event index> <goal index>\"         \n\t-- Set the goal as achieved.");
        System.out.println("5.  \"goal view <event index>\"                          " +
                "\n\t-- Display the goals list of a specified event with their status.");
        System.out.print(lineSeparation);
    }

    public void printContactHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"contact add <event index> /<name>, [<email>], [<phone number>]\"        \n\t-- " +
                "Add a contact to a specific event.");
        System.out.println("2.  \"contact delete <event index> <contact index>\"                          \n\t-- " +
                "Delete a specified contact.");
        System.out.println("3.  \"contact edit <event index> <contact index> <edit type> /<new contact>\" \n\t-- " +
                "Edit an existing contact, the edit type should be one of \"name\", \"email\", and \"phone\".");
        System.out.println("4.  \"contact view <event index>\"                                            \n\t-- " +
                "Display the contact list of a specified event with their status.");
        System.out.print(lineSeparation);
    }

    public void printChecklistHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"checklist add <event index> /<checklist>\"                        \n\t-- " +
                "Add a checklist to a specific event.");
        System.out.println("2.  \"checklist delete <event index> <checklist index>\"                \n\t-- " +
                "Delete a specified checklist.");
        System.out.println("3.  \"checklist edit <event index> <checklist index> /<new checklist>\" \n\t-- " +
                "Edit an existing checklist.");
        System.out.println("4.  \"checklist view <event index>\"                                    \n\t-- " +
                "Display the checklist list of a specified event with their status.");
        System.out.print(lineSeparation);
    }

    public void printChangeHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"done <event index>\"                  \n\t-- " +
                "Mark a Todo task as completed. Do not work for non-Todo event.");
        System.out.println("2.  \"reschedule <event index> dd-MM-yyyy\" \n\t-- " +
                "Reschedule the date and time of an event.");
        System.out.println("3.  \"edit <event index> /<description>\"   \n\t-- Edit the description of an event.");
        System.out.print(lineSeparation);
    }

    public void printInstrumentsHelp() {
        System.out.print(lineSeparation);
        System.out.println("1.  \"instruments add /<instrument name>\"                      \n\t-- " +
                "Add an instrument.");
        System.out.println("2.  \"instruments service <instrument index> /<details>/<date>\"\n\t-- " +
                "Add an instrument into service list.");
        System.out.println("3.  \"instruments view services <service index>\"               \n\t-- " +
                "View past servicing information of the specified instrument.");
        System.out.println("4.  \"instruments view instruments\"                            \n\t-- " +
                "View list of instruments.");
        System.out.print(lineSeparation);
    }

    public void periodNotPositive() {
        System.out.print(lineSeparation);
        System.out.println("Please ensure that the period is positive number.");
        System.out.print(lineSeparation);
    }

    /**
     * Obtains the current date and prints the events to be completed within the next
     * input number of days as a reminder.
     *
     * @param events the EventList used in the Duke function.
     */
    public void printReminderDays(EventList events, int days) {
        System.out.print(lineSeparation);
        System.out.print(events.getReminder(days));
        System.out.print(lineSeparation);
    }


    /**
     * Prints a message when an invalid command is entered.
     */
    public void printInvalidCommand() {
        System.out.print(lineSeparation);
        System.out.println("Sorry! I don't know what that means.");
        System.out.print(lineSeparation);
    }

    public void calendarCommandWrongFormat() {
        System.out.print(lineSeparation);
        System.out.println("The format of calendar command is wrong.");
        System.out.println("Please enter \"help calendar\" to see right format.");
        System.out.print(lineSeparation);
    }

    public void contactAdded() {
        System.out.print(lineSeparation);
        System.out.println("Ok, the contact has been added to the event.");
        System.out.print(lineSeparation);
    }

    public void contactDeleted() {
        System.out.print(lineSeparation);
        System.out.println("Ok, the contact has been deleted from the event.");
        System.out.print(lineSeparation);
    }

    public void printEventContacts(Event viewEventContact) {
        System.out.print(lineSeparation);
        System.out.println("Here is the list of contacts for the following event " + viewEventContact.toString());
        int contactNo = 1;
        for (Contact currContact : viewEventContact.getContactList()) {
            System.out.println(contactNo + ". Name: " + currContact.getName() + " Email: " + currContact.getEmail()
            + " Phone Number: " + currContact.getPhoneNo());
            contactNo++;
        }
        System.out.print(lineSeparation);
    }

    public void contactEdited(Contact newContact) {
        System.out.print(lineSeparation);
        System.out.println("The contact has been edited to: Name: " + newContact.getName() + " Email: "
                + newContact.getEmail() + " Phone Number: " +newContact.getPhoneNo());
        System.out.print(lineSeparation);
    }

    public void noContactInEvent() {
        System.out.print(lineSeparation);
        System.out.println("Do not have any contact in this event.");
        System.out.print(lineSeparation);
    }

    /**
     * prints entire list of events stored.
     *
     * @param events Model_Class.EventList object containing all stored classes and pertaining methods.
     */
    public static void printListOfEvents(EventList events) {
        System.out.print(lineSeparation);
        System.out.print(events.listOfEvents_String());
        System.out.print(events.getPastEventsWithUnachievedGoals());
        System.out.print(lineSeparation);
    }

    /**
     * prints goodbye message
     */
    public static void bye() {
        System.out.print(lineSeparation + "Bye. Hope to see you again soon!\n" + lineSeparation);
    }

    /**
     * @return line of underscores to separate different Model_Class.UI outputs.
     */
    public String getLineSeparation() {
        return lineSeparation;
    }

    /**
     * prints message when a event is successfully added
     *
     * @param eventAdded event in question
     * @param numEvents  total number of events
     */
    public void eventAdded(Event eventAdded, int numEvents) {
        try {
            System.out.println(lineSeparation + "Got it. I've added this event:");
            System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                    eventAdded.getDescription() + " START: " + eventAdded.getStartDate().getFormattedDateString() +
                    " END: " + eventAdded.getEndDate().getFormattedDateString());
            System.out.println("Now you have " + numEvents + " events in the list.");
            System.out.print(lineSeparation);
        } catch (NullPointerException e) {
            System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                    eventAdded.getDescription() + " BY: " + eventAdded.getStartDate().getFormattedDateString());
            System.out.println("Now you have " + numEvents + " events in the list.");
            System.out.print(lineSeparation);
        }
    }

    /**
     * prints message when a event is marked as completed
     *
     * @param event event in question
     */
    public void eventDone(Event event) {
        System.out.print(lineSeparation);
        System.out.println("Nice! I've marked this event as done:");
        System.out.println(event.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message when a event is deleted successfully
     *
     * @param event event in question to be deleted
     */
    public void eventDeleted(Event event) {
        System.out.print(lineSeparation);
        System.out.println("Noted. I've removed this event:");
        System.out.println(event.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message containing events found when a search is performed.
     * prints error message if no events are found
     *
     * @param allFoundEvents string containing all the events found, separated by newline character
     * @param found          boolean signifying whether or not any events were found
     */
    public void printFoundEvents(String allFoundEvents, boolean found) {
        if (found) {
            System.out.print(lineSeparation);
            System.out.println("Here are the matching events in your list:");
            System.out.print(allFoundEvents);
            System.out.print(lineSeparation);
        } else {
            System.out.print(lineSeparation);
            System.out.println("No such events were found! Please try again.");
            System.out.print(lineSeparation);
        }
    }

    /**
     * prints message if command does not contain valid input for related event.
     */
    public void noSuchEvent() {
        System.out.print(lineSeparation);
        System.out.println("There is no such event! Please try again.");
        System.out.print(lineSeparation);
    }

    public void rescheduleFormatWrong() {
        System.out.print(lineSeparation);
        System.out.println("Please enter command in the following format:\n" +
                "reschedule <taskIndex> dd-MM-yyyy HHmm HHmm\n" +
                "Please ensure that the taskIndex is a valid integer as well!");
        System.out.print(lineSeparation);
    }

    /**
     * prints message if no event description is found when adding a new event to the list
     */
    public void eventDescriptionEmpty() {
        System.out.print(lineSeparation);
        System.out.println("The description of your event cannot be empty!");
        System.out.print(lineSeparation);
    }

    public void checklistCommandWrongFormat() {
        System.out.print(lineSeparation);
        System.out.println("The format of checklist command is wrong.");
        System.out.println("Please enter \"help checklist\" to see the right format.");
        System.out.print(lineSeparation);
    }

    public void budgetCommandWrongFormat() {
        System.out.print(lineSeparation);
        System.out.println("The format of budget command is wrong.");
        System.out.println("Please enter \"budget MM-yyyy\" to view monthly cost of concerts.");
        System.out.print(lineSeparation);
    }

    public void viewCommandWrongFormat() {
        System.out.print(lineSeparation);
        System.out.println("The format of view command is wrong.");
        System.out.println("Please enter \"view dd-MM-yyyy\" to view the events of a particular date.");
        System.out.print(lineSeparation);
    }

    public void goalCommandWrongFormat() {
        System.out.print(lineSeparation);
        System.out.println("The format of goal management command is wrong.");
        System.out.println("Please enter \"help goal\" to see the right commands.");
        System.out.print(lineSeparation);
    }

    public void contactCommandWrongFormat() {
        System.out.print(lineSeparation);
        System.out.println("The format of contact management command is wrong.");
        System.out.println("Please enter \"help contact\" to see the right commands.");
        System.out.print(lineSeparation);
    }

    public void eventEndsBeforeStart() {
        System.out.print(lineSeparation);
        System.out.println("The event you added ends before it starts! Please try again.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when expected number from input is not an integer
     */
    public void notAnInteger() {
        System.out.print(lineSeparation);
        System.out.println("That is not a valid integer! Please check the value you entered!");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when input format is wrong for addition of new event type event.
     */
    public void newEntryFormatWrong() {
        System.out.print(lineSeparation);
        System.out.println("Please enter the date in the format 'dd-MM-yyyy HHmm HHmm' for Event" +
                " or 'dd-MM-yyyy' for ToDo.\n" +
                "For Event entry, first time entered is start time, second time entered is end time.");
        System.out.println("Please also ensure that the date you entered is valid.");
        System.out.print(lineSeparation);
    }

    public void scheduleClash(Event event) {
        System.out.print(lineSeparation);
        System.out.println("That event clashes with another in the schedule! " +
                "Please resolve the conflict and try again!");
        System.out.println("Clashes with: " + event.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message when recurring events are added to the list successfully
     */
    public void recurringEventAdded(Event eventAdded, int numEvents, int period) {
        System.out.println(lineSeparation + "Got it. I've added these recurring events:");
        System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                eventAdded.getDescription() + " START: " + eventAdded.getStartDate().getFormattedDateString() +
                " END: " + eventAdded.getEndDate().getFormattedDateString() + " (every " + period + " days)");
        System.out.println("Now you have " + numEvents + " events in the list.");
        System.out.print(lineSeparation);
    }

    /**
     * prints next 3 days that are free
     *
     * @param freeDays queue of free days of type DateObj
     */
    public void printFreeDays(Queue<String> freeDays) {
        System.out.print(lineSeparation);
        System.out.println("Here are the next 3 free days!");
        for (int i = 0; i <= freeDays.size(); i++) {
            System.out.println(freeDays.poll());
        }
        System.out.print(lineSeparation);
    }

    /**
     * prints message when reschedule an event successfully
     *
     * @param event event after rescheduled
     */
    public void rescheduleEvent(Event event) {
        System.out.print(lineSeparation);
        System.out.println("Rescheduled event to " + event.toString() + " successfully!");
        System.out.print(lineSeparation);
    }

    public void errorWritingToFile() {
        System.out.print(lineSeparation);
        System.out.println("Error writing to file! Details not saved!");
        System.out.print(lineSeparation);
    }

    /**
     * Prints message to show success of edit command.
     *
     * @param eventIndex  The index of the edited event.
     * @param eventEdited The event after edit.
     */
    public void printEditedEvent(int eventIndex, Event eventEdited) {
        try {
            System.out.println(lineSeparation + "Got it. Successfully edited event" + eventIndex + ":");
            System.out.println("[" + eventEdited.getDoneSymbol() + "][" + eventEdited.getType() + "] " +
                    eventEdited.getDescription() + " START: " + eventEdited.getStartDate().getFormattedDateString() +
                    " END: " + eventEdited.getEndDate().getFormattedDateString());
            System.out.print(lineSeparation);
        } catch (NullPointerException e) {
            System.out.println("[" + eventEdited.getDoneSymbol() + "][" + eventEdited.getType() + "] " +
                    eventEdited.getDescription() + " BY: " + eventEdited.getStartDate().getFormattedDateString());
            System.out.print(lineSeparation);
        }
    }

    public void printCalendar(String calendarInfo) {
        System.out.print(lineSeparation);
        System.out.println("Here is the current calendar of the 7 days!");
        System.out.println(calendarInfo);
        System.out.println("\nEnter a command:");
    }

    public void costExceedsBudget(Concert concert, int budget) {
        System.out.print(lineSeparation);
        System.out.println("The following concert you wanted to add causes you to exceed the stipulated budget for that month!");
        System.out.println(concert.toString());
        String date = concert.getStartDate().getFormattedDateString().substring(8, 16);
        System.out.println("exceeds budget of $" + budget + " for the month of " + date);
        System.out.println("Operation has been cancelled.");
        System.out.print(lineSeparation);
    }

    public static void printCostForMonth(String monthAndYear, int cost) {
        System.out.print(lineSeparation);
        System.out.println("Your total concert costs for " + monthAndYear + " is:");
        System.out.println("$" + cost);
        System.out.print(lineSeparation);
    }

    public static void printNoCostsForThatMonth() {
        System.out.print(lineSeparation);
        System.out.println("There are no concerts for that month!");
        System.out.print(lineSeparation);
    }

    public void printEventGoals(Event viewEventGoal) {
        System.out.println(lineSeparation);
        System.out.println("Here is the list of goals for the following event: " + viewEventGoal.toString());
        if (!viewEventGoal.getGoalList().isEmpty()) {
            int goalIndex = 1;
            for (Goal goalObject : viewEventGoal.getGoalList()) {
                System.out.println(goalIndex + ". " + goalObject.getGoal() + " - " + "Achieved: " + goalObject.getStatus());
                goalIndex += 1;
            }
            System.out.println(lineSeparation);
        } else {
            System.out.println("You currently have no goals for this event.");
            System.out.println(lineSeparation);
        }
    }

    public void goalAdded(String goal) {
        System.out.println("Ok, the following goal has been added to the event.");
        System.out.println(goal);
        System.out.println(lineSeparation);
    }

    public void goalDeleted(String deletedGoal) {
        System.out.println("Ok, the following goal has been deleted from the event.");
        System.out.println(deletedGoal);
        System.out.println(lineSeparation);
    }

    public void goalUpdated(EventList events, int eventID, int goalID) {
        System.out.println("Ok, the goal has been updated to: ");
        System.out.println(events.getEvent(eventID).getGoalObject(goalID).getGoal());
        System.out.println(lineSeparation);
    }

    public void goalSetAsAchieved(Goal goal) {
        System.out.println("Ok, the goal has been set as achieved. Congratulations for achieving the goal!");
        System.out.println(goal.getGoal() + " - " + goal.getStatus());
        System.out.println(lineSeparation);
    }

    public void noSuchGoal() {
        System.out.println("Sorry, the specified goal does not exist!");
        System.out.println(lineSeparation);
    }
  
    public void checklistDeleted(int eventIndex) {
        System.out.print(lineSeparation);
        System.out.println("Ok, checklist of event " + eventIndex + 1 + " has been deleted.");
        System.out.print(lineSeparation);
    }

    public void checklistEdited(String newChecklistItem, int eventIndex) {
        System.out.print(lineSeparation);
        System.out.println("Ok, checklist of event " + eventIndex + 1 + " has been edited to:");
        System.out.println(newChecklistItem);
        System.out.print(lineSeparation);
    }

    public void checklistAdded(String newChecklistItem, int eventIndex) {
        System.out.print(lineSeparation);
        System.out.println("Ok, the following item has been added to checklist of event " + eventIndex + 1 + ":");
        System.out.println(newChecklistItem);
        System.out.print(lineSeparation);
    }

    public void printEventChecklist(ArrayList<String> thisChecklist, int eventIndex, Event eventAdded) {
        System.out.print(lineSeparation);
        System.out.println("Here is the checklist for the following event: ");
        System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                eventAdded.getDescription());
        System.out.println("Checklist: ");
        int checklistIndex = 1;
        for (String checklistItem : thisChecklist) {
            System.out.println(checklistIndex + ". " + checklistItem);
            checklistIndex += 1;
        }
        System.out.print(lineSeparation);
    }
    
    public void instrumentAdded(String instrumentIndexAndName) {
    	System.out.print(lineSeparation);
    	System.out.println("Ok, the following instrument has been added: ");
    	System.out.println(instrumentIndexAndName);
    	System.out.println(lineSeparation);
    }
    
    public void serviceAdded(String serviceIndexAndName, String instrumentIndexAndName) {
    	System.out.println(lineSeparation);
    	System.out.println("Ok, the following service: ");
    	System.out.println(serviceIndexAndName);
    	System.out.println("has been added for the following instrument: ");
    	System.out.println(instrumentIndexAndName);
    	System.out.println(lineSeparation);
    }
    
    public void printInstruments(String instruments) {
    	System.out.println(lineSeparation);
    	System.out.println("Here are the list of instruments stored in the system: ");
    	System.out.println(instruments);
    	System.out.println(lineSeparation);
    }
    
    public void printServices(String services, String instrumentIndexAndName) {
    	System.out.println(lineSeparation);
    	System.out.println("Here are the list of services: ");
    	System.out.println(services);
    	System.out.println("Done before for the following instrument: ");
    	System.out.println(instrumentIndexAndName);
    	System.out.println(lineSeparation);
    }

    public static void budgetSet(int newBudget) {
        System.out.print(lineSeparation);
        System.out.println("Success! Your new monthly budget is $" + newBudget);
        System.out.print(lineSeparation);
    }

    public void printEnteredEventOver() {
        System.out.println("Reminder: the event you have added has a start date that is already over and hence won't be displayed in the list. You may still view it by" +
                " using the view schedules command.");
    }

}
