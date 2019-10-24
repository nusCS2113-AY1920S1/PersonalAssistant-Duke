package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.Bar;
import duke.components.Group;
import duke.components.Song;
import duke.components.SongList;

import java.util.ArrayList;

/**
 * A class representing the command to
 * group bars together as a verse.
 */
public class GroupCommand extends Command<SongList> {

    //@@author Samuel787
    private String message;
    private Song song;

    /**
     * Constructor for the command to group bars together as a verse.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public GroupCommand(String message) {
        this.message = message.trim();
    }

    /**
     * Saves the range of bars as a verse with the specified name and returns the messages intended to be displayed.
     *
     * @param songList the duke.TaskList or duke.components.SongList object that contains the task list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DukeException {
        if (message.length() < 6 || !message.substring(0, 6).equals("group ")) {
            //exception if not fully spelt
            throw new DukeException(message);
        }
        try {
            message = message.substring(6).trim();
            int startNo;
            int endNo;
            String name;
            String[] sections = message.split(" ");
            startNo = Integer.parseInt(sections[0]);
            endNo = Integer.parseInt(sections[1]);
            name = sections[2];

            Group group = createGroup(name, startNo, endNo);

            //code to add this group into the storage (verse list)

            return ui.formatGroupBar(startNo, endNo, name);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException(message, "group");
        }
    }


    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

    private Group createGroup(String name, int start, int end) throws DukeException {
        //check that the bounds are valid
        if (start < 1 || end > song.getNumBars()) {
            throw new DukeException("", "group");
        }

        ArrayList<Bar> myBars = new ArrayList<>();
        ArrayList<Bar> allBars = song.getBars();
        for (int i = start - 1; i < end - 1; i++) {
            myBars.add(allBars.get(i));
        }

        return new Group(name, myBars);
    }
}
