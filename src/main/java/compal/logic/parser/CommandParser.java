package compal.logic.parser;

import compal.logic.command.Command;
import compal.model.tasks.Task;

import java.text.ParseException;
import java.util.ArrayList;


public interface CommandParser {
    Command parseCommand(String input) throws ParseException, compal.logic.parser.exceptions.ParseException;


    // T parseCommand(String input, ArrayList<Task> taskList) throws ParseException, compal.logic.parser.exceptions.ParseException;
}