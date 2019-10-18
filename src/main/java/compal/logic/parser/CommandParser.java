package compal.logic.parser;

import compal.logic.command.Command;

import java.text.ParseException;


public interface CommandParser {
    Command parseCommand(String input) throws ParseException, compal.logic.parser.exceptions.ParseException;

}