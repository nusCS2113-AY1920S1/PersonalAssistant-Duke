package commands;

import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;
import UI.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class EditCommand extends Command {
    /**
     * @param list    task lists
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws IOException, NullPointerException {
        String[] break_command_words = ui.FullCommand.split(" ");
        String[] break_list_words;
        String newChangeString = "";
        int num_in_list = Integer.parseInt(break_command_words[1]);
        for (int i = 2; i < break_command_words.length; i++) {
            if (i != break_command_words.length - 1) {
                newChangeString += break_command_words[i] + " ";
            } else {
                newChangeString += break_command_words[i];
            }
        }

        break_list_words = list.get(num_in_list - 1).toString().split("\\|");
        if (break_list_words[0].equals("T")) {
//            System.out.println("Please input your new ToDo description:");
            Todo newtodo = new Todo(newChangeString);
            System.out.print("Ok, we have edited your ToDo description. \n\tFrom: " + list.get(num_in_list - 1).listFormat() +
                    "\n\tTo:   " + newtodo.listFormat());
            list.set(num_in_list - 1, newtodo);
        } else if (break_list_words[0].equals("D")) {
//            System.out.println("Please input your new Deadline description and time:");
            String[] break_deadline = newChangeString.split("/by ");
            String description = break_deadline[0];
            String by = break_deadline[1];
            Deadline newdeadline = new Deadline(description, by);
            System.out.print("Ok, we have edited your Deadline description and time. \n\tFrom: " + list.get(num_in_list - 1).listFormat() +
                    "\n\tTo:   " + newdeadline.listFormat());
            list.set(num_in_list - 1, newdeadline);
        } else {
//            System.out.println("Please input your new Event description and time:");
            System.out.println(newChangeString);
            String[] break_event = newChangeString.split("/at ");
            String description = break_event[0];
            String at = break_event[1];
            Event newEvent = new Event(description, at);
            System.out.print("Ok, we have edited your Event description and time. \n\tFrom: " + list.get(num_in_list - 1).listFormat() +
                    "\n\tTo:   " + newEvent.listFormat());
            list.set(num_in_list - 1, newEvent);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }

    /**
     * Tells the main Duke class that the system should not exit and continue running
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
