package gazeeebo.commands.edit;

import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Timebound;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EditBothCommand {
    /**
     * Receives the user new description and time and edit the old description and time in the list.
     * @param list task lists
     * @param ui deals with printing things to the user.
     * @param listIndex  the index of the list
     * @throws IOException
     */
    public EditBothCommand(final ArrayList<Task> list, final Ui ui, final int listIndex) throws IOException {
        System.out.print("Type your description & date:\n");
        ui.readCommand();
        String[] breakListWords = list.get(listIndex).toString().split("\\|");
        if (breakListWords[0].equals("T")) {
            Todo newtodo = new Todo(ui.fullCommand);
            System.out.print("Ok, we have edited your ToDo description.\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newtodo.listFormat() + "\n");
            list.set(listIndex, newtodo);
        } else if (breakListWords[0].equals("D")) {
            String[] breakDeadline = ui.fullCommand.split("/by ");
            String description = breakDeadline[0];
            String by = breakDeadline[1];
            Deadline newdeadline = new Deadline(description, by);
            System.out.print("Ok, we have edited your Deadline description and time.\n\tFrom: "
                    + list.get(listIndex).listFormat()
                    + "\n\tTo:   " + newdeadline.listFormat() + "\n");
            list.set(listIndex, newdeadline);
        } else if (breakListWords[0].equals("E")) {
            String[] breakEvent = ui.fullCommand.split("/at ");
            String description = breakEvent[0];
            String at = breakEvent[1];
            Event newEvent = new Event(description, at);
            System.out.print("Ok, we have edited your Event description and time." + "\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newEvent.listFormat() + "\n");
            list.set(listIndex, newEvent);
        } else if (breakListWords[0].equals("DA")) {

        } else if (breakListWords[0].equals("FD")) {
            String[] breakFD = ui.fullCommand.split(" /require ");
            FixedDuration newFD = new FixedDuration(breakFD[0], breakFD[1]);
            System.out.print("Ok, we have edited your FixDuration description and time.\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newFD.listFormat() + "\n");
            list.set(listIndex, newFD);
        } else if (breakListWords[0].equals("P")) {
            String[] breakP = ui.fullCommand.split(" /between ");
            Timebound newP = new Timebound(breakP[0], breakP[1]);
            System.out.print("Ok, we have edited your FixDuration description and time.\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newP.listFormat() + "\n");
            list.set(listIndex, newP);
        }
    }
}
