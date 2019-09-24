package compal.logic.parser;

import compal.main.Duke;

import java.text.ParseException;

public interface CommandParser {
    void parseCommand(String userIn) throws Duke.DukeException, ParseException;
}