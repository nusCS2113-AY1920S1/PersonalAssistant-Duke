package parser;

import command.Command;
import command.CommentCommand;
import exception.DukeException;



public class CommentParser extends IndexParser {

    public CommentParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        String comment = extractComment(taskFeatures);
        return new CommentCommand(indexOfTask, comment);
    }

    private String extractComment(String taskFeatures) throws DukeException {
        String comment;
        try {
            String[] commentCommandParts = taskFeatures.split("\\s+", 2);
            comment = commentCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyComment());
        }
        return comment;
    }
}
