package ducats;

/**
 * Custom exception class for all Ducats-related exceptions.
 */
public class DucatsException extends Exception {

    private String input;
    private String type = "other";
    private static final String[] COMMAND_STRINGS =
        {"todo","deadline", "event", "doafter", "new",
        "view", "addbar", "copy", "group", "overlay",
        "delete", "deletebar", "editbar", "insertbar", "swapbar"};

    //@@author rohan-av
    /**
     * Constructor for ducats.DukeException for default type.
     *
     * @param input input message that triggered the exception
     */
    public DucatsException(String input) {
        super(input);
        this.input = input;
    }

    /**
     * Constructor for ducats.DukeException for specific Tasks, in order to provide
     * type-specific error messages.
     *
     * @param input input message that triggered the exception
     * @param type the type of the ducats.tasks.Task that was attempted to be created which
     *             caused the exception (i.e. ducats.tasks.ToDo, ducats.tasks.Event, or ducats.tasks.Deadline)
     */
    public DucatsException(String input, String type) {
        super(input);
        this.input = input;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    /**
     * Returns the message associated with the type of ducats.DukeException that has occurred.
     *
     * @return the error message associated with the exception
     */
    public String getMessage() {

        String message = "An unknown exception has occurred.";
        String word = input.trim().equals("editbar")
                || input.trim().equals("overlay")
                || input.trim().equals("addbar")
                || input.trim().equals("insertbar")
                ? "an " : "a ";

        if (hasEmptyDescription(input)) {

            message = "OOPS!!! The description of "
                    + word
                    + input.trim()
                    + " command cannot be empty.";
        } else if (!type.equals("other")) {
            switch (type) {
            case "delete": {
                message = "OOPS!!! The song cannot be deleted due to invalid input format.";
                break;
            }
            case "deletebar": {
                message = "OOPS!!! The bar cannot be deleted due to invalid input format.";
                break;
            }
            case "new": {
                message = "OOPS!!! ducats.components.Song cannot be created due to invalid input format.";
                break;
            }
            case "repeat_song_name": {
                message = "OOPS!!! ducats.components.Song cannot be created as that song name is already in use.";
                break;
            }
            case "key": {
                message = "OOPS!!! The song cannot be created as the only supported key is c.";
                break;
            }
            case "time_sig": {
                message = "OOPS!!! The song cannot be created as the only supported time signature is 4/4.";
                break;
            }
            case "tempo": {
                message = "OOPS!!! The song cannot be created as the tempo is invalid; it must be a positive number.";
                break;
            }
            case "view": {
                message = "OOPS!!! I don't know that song. Please be more specific in the song name.";
                break;
            }
            case "addbar": {
                message = "OOPS!!! New bar cannot be added due to invalid input.";
                break;
            }
            case "edit": {
                message = "OOPS!!! Bar cannot be edited due to invalid input.";
                break;
            }
            case "insertbar": {
                message = "OOPS!!! New bar cannot be inserted due to invalid input.";
                break;
            }
            case "swap": {
                message = "OOPS!!! Bars cannot be swapped due to invalid input.";
                break;
            }
            case "io": {
                message = "OOPS!!! An IO exception has occurred.";
                break;
            }

            case "no_index": {
                message = "The index does not exist!";
                break;
            }
            case "empty": {
                message = "List is empty! Please enter a valid command.";
                break;
            }
            case "conflict": {
                message = "There is a conflict between this event and another event!";
                break;
            }
            case "index": {
                message = "Invalid index! Please try again.";
                break;
            }
            case "between": {
                message = "Invalid input for a between task. "
                        + "Please follow this format: between <task_description> /between <start> and <end>";
                break;
            }
            case "recur": {
                message = "Invalid input for a recurring task. Please follow this format:"
                        + " recur <frequency> <description> /on <date> /at <time>\n";
                message += "<frequency> could only be one of: daily, weekly, monthly or yearly\n";
                message += "<date> has to follow the specific format of: dd/mm/yy\n";
                message += "/at <time> is optional.";
                break;
            }
            case "group": {
                message = "OOPS!!! These groups cannot be grouped due to invalid input.";
                break;
            }
            case "copy": {
                message = "OOPS!!! Invalid input for copy command.";
                break;
            }
            case "create": {
                message = "OOPS!!! Invalid syntax.";
                break;
            }
            case "data": {
                message = "OOPS!!! The data is corrupted.";
                break;
            }
            case "IO": {
                message = "IO exception when serializing!";
                break;
            }
            case "AsciiCommand": {
                message = "OOPS!!! Your Ascii Command is not recognised.";
                break;
            }
            case "no_song": {
                message = "OOPS!! No such song exists! Can you check if you have spelt it right?";
                break;
            }
            case "number_index": {
                message = "OOPS!! The index must be a number not a string!";
                break;
            }
            case "special_characters": {
                message = "OOPS!! No special characters allowed";
                break;
            }
            case "whitespace_name": {
                message = "OOPS!! You cant just have a space as a song name!!";
                break;
            }
            case "overlay_bar_song_format" : {
                message = "OOPS!! Please follow this format: overlay_bar_song <song_name_to_be_copied_from> "
                    + "bar_num_copied_from <song_name_to_be_copied_to> bar_num_copied_to";
                break;
            }
            case "overlay_format" : {
                message = "OOPS!! Please follow this format: overlay <bar_num_to_be_copied_from> "
                    + "<bar_num_copied_to>";
                break;
            }
            case "overlay_bar_group_format" : {
                message = "OOPS!! Please follow this format: overlay_bar_group <bar_num_to_be_copied_from> "
                        + "<group_num_copied_to>";
                break;
            }
            case "overlay_group_group_format" : {
                message = "OOPS!! Please follow this format: overlay_group_group <song_name_to_be_copied_from> "
                        + "<group_num_to_be_copied_from> <song_name_to_be_copied_to> <group_num_to_be_copied_to>";
                break;
            }
            case "group_not_found": {
                message = "OOPS!! That group does not exist in this song";
                break;
            }
            case "name_exists": {
                message = "OOPS!! That name already exists";
                break;
            }
            case "no_song_in_songlist": {
                message = "OOPS!! You have no songs in songlist. :-(";
                break;
            }
            case "group_format": {
                message = "OOPS!! To create a group, please use this format: \n"
                        + "group <start_index> <end_index> <group_name>";
                break;
            }
            default: {
                message = "OOPS!!! I'm sorry, but I don't know what that means :-(";
            }
            }
        } else {
            message = "OOPS!!! I'm sorry, but I don't know what that means :-(";
        }
        return Ui.wrap(message);
        // wrap is called from Ui in order to standardize the formatting of the output
    }

    /**
     * Returns a boolean value corresponding to whether the input string, when trimmed, is equal to just the command,
     * hence indicating the lack of further description.
     *
     * @param message the input String
     * @return a boolean indicating whether the message has an empty description
     */
    private boolean hasEmptyDescription(String message) {
        for (String commandString: COMMAND_STRINGS) {
            if (message.trim().equals(commandString)) {
                return true;
            }
        }
        return false;
    }
}
