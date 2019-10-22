package seedu.hustler.parser;

public class ParserForCommand {
    /**
     * The name of the command to parse for.
     */
    private String command;

    /**
     * Initializes command variable.
     * @param command name of command
     */
    public ParserForCommand(String command) {
        this.command = command; 
    } 
    
    /**
     * A method that parses a user input string of index
     * to integer index.
     * @param description description that contains string integer
     * @return Integer object that is the parsed integer index
     */
    public Integer parseIndex(String description) {
        switch (this.command) {
        case "done":
        case "delete":
        case "snooze":
            return Integer.parseInt(description) - 1;
        default:
            return -1;
        }
    }
}
