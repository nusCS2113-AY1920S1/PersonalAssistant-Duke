package duke.commands;

import duke.EventDateTimeComparator;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.enums.CommandType;
import duke.items.DateTime;
import duke.items.Event;
import java.util.ArrayList;

public class FindFreeTimeCommand extends Command {
    private int reqFreeHours;

    public FindFreeTimeCommand(CommandType type, int reqFreeHours) {
        super(type);
        this.reqFreeHours = reqFreeHours;
    }

    /**
     * Outputs the nearest event after which a block of free time has been detected.
     * Uses the reqFreeHours variable as the size of the block of free time the user wishes to find.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        if (reqFreeHours < 0) {
            System.out.println("Please enter an hour value >= 0.");
            return;
        }

        // Creating temporary ArrayList of events.
        ArrayList<Event> eventSortedList = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.getTask(i) instanceof Event) {
                eventSortedList.add((Event) list.getTask(i));
            }
        }

        eventSortedList.sort(new EventDateTimeComparator());
        if (eventSortedList.size() <= 1) {
            System.out.println("You need at least 2 events to run this command!");
            return;
        }
        // Array is definitely sorted at this point.

        // Printing out for testing purposes.
        for (int i = 0; i < eventSortedList.size(); i++) {
            System.out.println(eventSortedList.get(i).toString());
        }

        DateTime latestEndTime = eventSortedList.get(0).getEventEndTimeObj();
        Event eventBeforeFreeTime = eventSortedList.get(0);
        int curMaxFreeHours = 0;
        boolean freeTimeFound = false;

        for (int i = 1; i < eventSortedList.size(); i++) {
            DateTime nextStartTime = eventSortedList.get(i).getEventStartTimeObj();
            DateTime nextEndTime = eventSortedList.get(i).getEventEndTimeObj();

            int compare = latestEndTime.getAt().compareTo(nextStartTime.getAt());
            // latestEndTime is earlier than nextStartTime
            if (compare < 0) {
                // Getting number of hours between latestEndTime and nextStartTime

                long ms = nextStartTime.getAt().getTime().getTime() - latestEndTime.getAt().getTime().getTime();


                //long ms = nextStartTime.getAt().getTime() - latestEndTime.getAt().getTime();
                int potentialMaxFreeHours = Math.round((float)ms / (1000 * 60 * 60));
                System.out.println(potentialMaxFreeHours);

                if (potentialMaxFreeHours >= curMaxFreeHours) {
                    curMaxFreeHours = potentialMaxFreeHours;
                    eventBeforeFreeTime = eventSortedList.get(i - 1);
                }

                // Since curEndTime is earlier than or equal to nextStartTime, it is guaranteed that
                // our latestEndTime will be equiv to nextEndTime - since this definitely
                // takes place after curEndTime.
                latestEndTime = nextEndTime;

                if (curMaxFreeHours >= reqFreeHours) {
                    eventBeforeFreeTime = eventSortedList.get(i - 1);
                    freeTimeFound = true;
                    break;
                }
            } else {
                // If curEndTime is later than or equal to nextStartTime - this only happens in the
                // event of a clash between events, i.e. events running concurrently.

                // Assuming the (next) clashing event takes place perfectly within the current event
                // we keep the value of latestEndTime untouched.
                if (nextEndTime.getAt().before(latestEndTime.getAt())
                        || nextEndTime.getAt().equals(latestEndTime.getAt())) {
                    // Leave latestEndTime untouched.
                } else {
                    // Else, if the clashing event happens to end after the current event, we need to update
                    // out latestEndTime value accordingly.
                    latestEndTime = nextEndTime;
                }
            }
        }

        if (!freeTimeFound) {
            eventBeforeFreeTime = eventSortedList.get(eventSortedList.size() - 1);
        }

        System.out.println("The earliest free time I found was after the following event:\n"
                + eventBeforeFreeTime.toString());
    }
}
