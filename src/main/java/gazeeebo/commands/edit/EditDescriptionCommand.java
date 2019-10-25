package gazeeebo.commands.Edit;

import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Timebound;
import gazeeebo.tasks.DoAfter;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EditDescriptionCommand {
    /**
     * This method will receive the user's
     * new description and edit the old description.
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param listIndex the index of the list
     * @throws IOException
     */
    public EditDescriptionCommand(final ArrayList<Task> list, final Ui ui, final int listIndex) throws IOException {
        System.out.print("Type your description:\n");
        ui.readCommand();
        String newDescription = ui.fullCommand;

        String[] breakListWords = list.get(listIndex).toString().split("\\|");

        if (breakListWords[0].equals("T")) {
            Todo newtodo = new Todo(newDescription);
            System.out.print("Ok, we have edited your ToDo description." + "\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newtodo.listFormat() + "\n");
            list.set(listIndex, newtodo);
        } else if (breakListWords[0].equals("D")) {
            String[] by = breakListWords[3].split("by: ");
            Deadline newdeadline = new Deadline(newDescription, by[1]);
            System.out.print("Ok, we have edited your Deadline description." + "\n\tFrom: " + list.get(listIndex).listFormat()
                    + "\n\tTo:   " + newdeadline.listFormat() + "\n");
            list.set(listIndex, newdeadline);
        } else if (breakListWords[0].equals("E")) {
            String[] at = breakListWords[3].split("at: ");
            Event newEvent = new Event(newDescription, at[1]);
            System.out.print("Ok, we have edited your Event description." + "\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newEvent.listFormat() + "\n");
            list.set(listIndex, newEvent);
        } else if (breakListWords[0].equals("DA")) {
            String[] splitter = newDescription.split(" /after ");
            DoAfter newDA = new DoAfter(splitter[1], splitter[1], splitter[0]);
            System.out.print("Ok, we have edited your DoAfter description." + "\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newDA.listFormat() + "\n");
            list.set(listIndex, newDA);
        } else if (breakListWords[0].equals("FD")) {
            String by = breakListWords[3];
            FixedDuration newFA = new FixedDuration(newDescription, by);
            System.out.print("Ok, we have edited your FixedDuration description." + "\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newFA.listFormat() + "\n");
            list.set(listIndex, newFA);
        } else if (breakListWords[0].equals("P")) {
            Timebound newP = new Timebound(newDescription, breakListWords[3]);
            System.out.print("Ok, we have edited your Timebound description." + "\n\tFrom: " + list.get(listIndex).listFormat() + "\n\tTo:   " + newP.listFormat() + "\n");
            list.set(listIndex, newP);
        }
    }
}

