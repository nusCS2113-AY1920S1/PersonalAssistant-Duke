package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;


public interface CommandParser {
    Command parseCommand(String input) throws ParseException, ParserException;

}