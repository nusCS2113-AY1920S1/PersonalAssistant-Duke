package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.SongList;

/**
 * A class representing the command to copy bars or verses and paste them within the same track.
 */
public class CopyCommand extends Command<SongList> {

    private String message;

    /**
     * Constructor for the command to copy and paste bars or verse.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public CopyCommand(String message) {
        this.message = message.trim();
    }


    /**
     * Copy bars between a certain range and paste it between a certain range
     * in song creator.
     * @param songList the duke.TaskList or duke.components.SongList object that contains the task list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DukeException {
        //copy 2 4 6
        //copy 2 4
        //copy <versename> 6
        //copy <versename>

        if (message.length() < 5 || !message.substring(0,4).equals("copy")) {
            //exception if not fully spelt
            throw new DukeException(message);
        }
        try {
            message = message.substring(5).trim();
            String[] sections = message.split(" ");
            if (sections.length < 1 || sections.length > 3) {
                throw new DukeException(message, "copy");
            }
            //trimmer
            for (int i = 0; i < sections.length; i++) {
                sections[i] = sections[i].trim();
            }
            if (sections.length == 1) {
                //copy the verse to the end of the song list
                String verseName = sections[0];
                //check if the verseName exists in the stored verse-list if not throw exception
                //code to add those bars in that verse to the end of the song

                return ui.formatCopy(verseName, null, null, null, 0);
            } else if (sections.length == 2) {
                try {
                    //copy the bars between the starting num and ending num to the end of the song
                    int startNum = Integer.parseInt(sections[0]);
                    int endNum = Integer.parseInt(sections[1]);
                    //retrieve the bars between the range
                    //code to copy this to the end of the song
                    return ui.formatCopy(null, startNum, endNum, null, 1);
                } catch (NumberFormatException e) {
                    //copy the verse and insert it between the numbers stated
                    String verseName = sections[0].trim();
                    int startNum = Integer.parseInt(sections[1]);
                    //check if the versename exists inside the list, if not throw exception
                    //code to insert this verse into the range between start_num and end_num
                    return ui.formatCopy(verseName, null, null, startNum, 2);
                }
            } else if (sections.length == 3) {
                //copy the bars between the a range into a new range
                int copyStartNum = Integer.parseInt(sections[0]);
                int copyEndNum = Integer.parseInt(sections[1]);
                int pasteStartNum = Integer.parseInt(sections[2]);
                //retrieve the bars between the copy range
                //code to copy this to the stated paste range
                return ui.formatCopy(null, copyStartNum, copyEndNum, pasteStartNum, 3);
            } else {
                throw new DukeException(message, "copy");
            }
        } catch (Exception e) {
            throw new DukeException(message, "copy");
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
}
