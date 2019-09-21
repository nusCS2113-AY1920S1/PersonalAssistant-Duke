package compal.logic.parser;

import compal.main.Duke;

import java.text.ParseException;

public interface CommandParser {
    void Command(String userIn) throws Duke.DukeException, ParseException;
}