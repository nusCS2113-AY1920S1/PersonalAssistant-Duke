package compal.logic.parser;

import compal.commons.CompalUtils;
import compal.logic.command.Command;
import compal.logic.command.EditCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.text.ParseException;
import java.util.Date;

public class EditCommandParser implements CommandParser {
    private String description;
    private Date date;
    private Date startTime;
    private Date endTime;
    private int taskId;
    private Task.Priority priority;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException, ParseException {
        taskId = getTokenTaskID(restOfInput);
        description = getTokenDescription(restOfInput);
        date = getDate(restOfInput);
        startTime = CompalUtils.stringToDate(getTokenStartTime(restOfInput));

        return new EditCommand(taskId,description,date,startTime,endTime,priority);


    }
}
