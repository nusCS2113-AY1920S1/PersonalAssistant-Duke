package compal.logic.parser;

import compal.compal.Compal;

import java.text.ParseException;

public interface CommandParser {
    void parseCommand(String userIn) throws Compal.DukeException, ParseException;
}