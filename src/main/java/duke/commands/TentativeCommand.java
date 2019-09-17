package duke.commands;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.tasks.TentativeEvent;

import java.util.Date;
import java.util.List;

public class TentativeCommand extends Command {
    private boolean isAdd;
    public TentativeCommand (String str, boolean isAdd) {
        input = str;
        type = CmdType.TENTATIVE;
        this.isAdd = isAdd;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
        if (isAdd && TentativeEvent.dates.isEmpty()) {
            executeAdd(ui);
        } else if (isAdd) {
            ui.showMessage("You can only have one tentative event at a time.");
        } else if (!TentativeEvent.dates.isEmpty()){
            getString(ui, "This is your current tentative event:");
        } else {
            ui.showError("You don't have a tentative event.");
        }
    }

    private void executeAdd(Ui ui) throws DukeException {
        if (input.length() == 15) {
            throw new DukeException("     ☹ OOPS!!! The description of a event cannot be empty.");
        } else {
            TentativeEvent.description = input.substring(16);
        }

        ui.showMessage("Enter the number of slots for this event:");
        String slots = ui.readCommand();
        int numberOfSlots = 0;
        try {
            numberOfSlots = Integer.parseInt(slots);
        } catch (NumberFormatException e) {
            throw new DukeException("Not a valid number!");
        }

        if (numberOfSlots <= 0 || numberOfSlots > 10) {
            ui.showError("Only 1 to 10 slots are acceptable. Please try again.");
        } else {
            ui.showMessage("Enter the timing of the slots.");
            for (int i = 0; i < numberOfSlots; i++) {
                try {
                    TentativeEvent.dates.add(parseDate(ui));
                    ui.showMessage("Accepted");
                } catch (DukeException e) {
                    ui.showError(e.getMessage());
                    i--;
                }
            }
            getString(ui, "Got it. I've taken note of this event:");
        }
    }

    private Date parseDate(Ui ui) throws DukeException{
        String timing = ui.readCommand();
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(timing);
            return groups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new DukeException("   Date cannot be parsed: " + timing + "\n   Try again.");
        }
    }

    private void getString(Ui ui, String topMessage) {
        ui.showMessage(topMessage);
        ui.showMessage("    [E] " + TentativeEvent.description);
        ui.showMessage("with these slots:");
        int idx = 1;
        for (Date d : TentativeEvent.dates) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("    ").append(idx++).append(".");
            stringBuilder.append(d.toString());
            ui.showMessage(stringBuilder.toString());
        }
        ui.showMessage("Confirm this event by typing \"confirm\" followed by the corresponding slot number.");
    }
}
