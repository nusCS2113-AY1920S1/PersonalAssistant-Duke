package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;
import duke.task.TimedTask;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class NewRecurringTaskCommand extends MultiArgCommand {
    public NewRecurringTaskCommand() {
        argc = 2;
        delim = "/repeat";
        emptyArgMsg = "You didn't tell me anything about the recurring tasks!";
        invalidArgMsg = "You didn't tell me how the task is supposed to recur!";
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        super.execute(ctx);
        Parser recurParser = new Parser();
        NewTimedTaskCommand refCommand;
        try {
            refCommand = (NewTimedTaskCommand) recurParser.parse(argv[0]);
        } catch (ClassCastException excp) {
            throw new DukeException("Can't have that as a recurring task!");
        }

        TemporalUnit period = null;
        int firstSpaceIdx = argv[1].indexOf(" "); //index of first space
        if (firstSpaceIdx == -1) {
            throw new DukeException("You didn't tell me how frequently this task should recur!");
        }
        switch(argv[1].substring(0, firstSpaceIdx)) { //extract period from frequency, use fallthrough to add synonyms
        case "daily":
            period = ChronoUnit.DAYS;
            break;
        case "weekly":
            period = ChronoUnit.WEEKS;
            break;
        case "monthly":
            period = ChronoUnit.MONTHS;
            break;
        default:
            throw new DukeException("I don't know how frequently that's supposed to be!");
        }

        argv[1] = argv[1].substring(firstSpaceIdx).strip(); //remove frequency descriptor

        String countDelim = "/count";
        String untilDelim = "/until";

        DukeException invalidRecurrenceExcp = new DukeException("You need to tell me how many times you want that "
                + "task to recur!" + System.lineSeparator() + "Either tell me to repeat it with e.g. '/count 5' or"
                + "with e.g. '/until " + LocalDateTime.now().plus(3, period)
                .format(TimedTask.getDataFormatter()) + "'.");
        if (argv[1].matches("^" + countDelim + "\\s+\\d+$")) {
            Integer.parseInt(argv[1].substring(countDelim.length()).strip());
            //...
        } else if (argv[1].matches("^" + untilDelim + "\\s+[A-Za-z 0-9/]+$")) {
            try {
                LocalDateTime until = LocalDateTime.parse(argv[1].substring(untilDelim.length()).strip(),
                        TimedTask.getDataFormatter());
            } catch (DateTimeParseException excp) {
                throw invalidRecurrenceExcp;
            }
            //...
        } else {
            throw invalidRecurrenceExcp;
        }
        /*String[] recurArr = argv[1].split("\\s*(/count|/until)\\s*");
        if (recurArr.length < 2) {
            throw new DukeException("You need to tell me the total /count of recurring tasks,"
                    + "or when it will recur /until.");
        } else if (recurArr.length > 2) {
            throw new DukeException("I'm not sure how many recurring tasks you want to create!");
        } else {

            case (recurArr[0]):

        }*/
        refCommand.execute(ctx); //TODO use refCommand to construct the other commands
    }
}
