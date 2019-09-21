package compal.logic.parser;

import compal.main.Duke;

import java.text.ParseException;

public interface CommandParser  {
    public void Command(String userIn) throws Duke.DukeException, ParseException;
}