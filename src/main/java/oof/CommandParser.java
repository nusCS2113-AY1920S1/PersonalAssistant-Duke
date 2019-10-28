package oof;

import oof.command.AddAssessmentCommand;
import oof.command.AddAssignmentCommand;
import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.AddLessonCommand;
import oof.command.AddModuleCommand;
import oof.command.AddSemesterCommand;
import oof.command.AddToDoCommand;
import oof.command.CalendarCommand;
import oof.command.Command;
import oof.command.CompleteCommand;
import oof.command.DeleteAssessmentCommand;
import oof.command.DeleteAssignmentCommand;
import oof.command.DeleteCommand;
import oof.command.DeleteLessonCommand;
import oof.command.DeleteModuleCommand;
import oof.command.DeleteSemesterCommand;
import oof.command.ExitCommand;
import oof.command.FindCommand;
import oof.command.FreeCommand;
import oof.command.HelpCommand;
import oof.command.ListCommand;
import oof.command.RecurringCommand;
import oof.command.ScheduleCommand;
import oof.command.SnoozeCommand;
import oof.command.StartTrackerCommand;
import oof.command.StopTrackerCommand;
import oof.command.SummaryCommand;
import oof.command.ThresholdCommand;
import oof.command.ViewAssessmentCommand;
import oof.command.ViewAssignmentCommand;
import oof.command.ViewLessonCommand;
import oof.command.ViewModuleCommand;
import oof.command.ViewSemesterCommand;
import oof.command.ViewWeekCommand;
import oof.exception.OofException;

import java.util.InputMismatchException;

/**
 * Represents a parser to process the commands inputted by the user.
 */
public class CommandParser {

    private static Ui ui;
    private static final int LENGTH_COMMAND_ONLY = 1;
    private static final int LENGTH_COMMAND_AND_TASK = 2;
    private static final int INDEX_ARGUMENT_COMMAND = 0;
    private static final int INDEX_ARGUMENT_TASK_NUMBER = 1;
    private static final int INDEX_ARGUMENT_COUNT = 2;
    private static final int INDEX_TASK_TYPE = 1;

    /**
     * Parses the input given by user and calls specific Commands
     * after checking the validity of the input.
     *
     * @param line Command inputted by user.
     * @return Command based on the user input.
     * @throws OofException Catches invalid commands given by user.
     */
    public static Command parse(String line) throws OofException {
        String[] argumentArray = line.split(" ");
        switch (argumentArray[INDEX_ARGUMENT_COMMAND]) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "help":
            line = line.replaceFirst("help", "").trim();
            return new HelpCommand(line);
        case "done":
            return parseDone(argumentArray, line);
        case "todo":
            line = line.replaceFirst("todo", "").trim();
            return new AddToDoCommand(line);
        case "deadline":
            line = line.replaceFirst("deadline", "").trim();
            return new AddDeadlineCommand(line);
        case "event":
            line = line.replaceFirst("event", "").trim();
            return new AddEventCommand(line);
        case "delete":
            return parseDelete(argumentArray, line);
        case "find":
            return new FindCommand(line);
        case "snooze":
            return parseSnooze(argumentArray, line);
        case "schedule":
            line = line.replaceFirst("schedule", "").trim();
            return new ScheduleCommand(line);
        case "summary":
            return new SummaryCommand();
        case "recurring":
            return parseRecurring(argumentArray);
        case "calendar":
            return new CalendarCommand(argumentArray);
        case "viewweek":
            return new ViewWeekCommand(argumentArray);
        case "free":
            line = line.replaceFirst("free", "").trim();
            return new FreeCommand(line);
        case "start":
            line = line.replaceFirst("start", "").trim();
            return new StartTrackerCommand(line);
        case "stop":
            line = line.replaceFirst("stop", "").trim();
            return new StopTrackerCommand(line);
        case "threshold":
            line = line.replaceFirst("threshold", "").trim();
            return new ThresholdCommand(line);
        case "semester":
            return parseSemester(argumentArray, line);
        case "module":
            return parseModule(argumentArray, line);
        case "lesson":
            return parseLesson(argumentArray, line);
        case "assignment":
            return parseAssignment(argumentArray, line);
        case "assessment":
            return parseAssessment(argumentArray, line);
        default:
            throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses input if the user input starts with done.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns an instance of CompleteCommand if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseDone(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            throw new OofException("OOPS!!! Please enter a number!");
        }
        try {
            int completeIndex = Integer.parseInt(line.replaceFirst("done", "").trim()) - 1;
            return new CompleteCommand(completeIndex);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input if the user input starts with delete.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns an instance of DeleteCommand if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseDelete(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            throw new OofException("OOPS!!! Please enter a number!");
        } else if (argumentArray.length == LENGTH_COMMAND_AND_TASK) {
            try {
                int deleteIndex = Integer.parseInt(line.replaceFirst("delete", "").trim()) - 1;
                return new DeleteCommand(deleteIndex);
            } catch (NumberFormatException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        } else {
            throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses input if the user input starts with semester.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns relevant Semester Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseSemester(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            return new ViewSemesterCommand();
        } else {
            if (argumentArray[INDEX_TASK_TYPE].equals("add")) {
                line = line.replaceFirst("semester add", "").trim();
                return new AddSemesterCommand(line);
            } else if (argumentArray[INDEX_TASK_TYPE].equals("delete")) {
                try {
                    int deleteIndex = Integer.parseInt(line.replaceFirst("semester delete", "")
                            .trim()) - 1;
                    return new DeleteSemesterCommand(deleteIndex);
                } catch (NumberFormatException e) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                }
            } else {
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    /**
     * Parses input if the user input starts with module.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns relevant Module Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseModule(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            return new ViewModuleCommand();
        } else {
            if (argumentArray[INDEX_TASK_TYPE].equals("add")) {
                line = line.replaceFirst("module add", "").trim();
                return new AddModuleCommand(line);
            } else if (argumentArray[INDEX_TASK_TYPE].equals("delete")) {
                try {
                    int deleteIndex = Integer.parseInt(line.replaceFirst("module delete", "")
                            .trim()) - 1;
                    return new DeleteModuleCommand(deleteIndex);
                } catch (NumberFormatException e) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                }
            } else {
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    /**
     * Parses input if the user input starts with lesson.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns relevant Lesson Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseLesson(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            return new ViewLessonCommand();
        } else {
            if (argumentArray[INDEX_TASK_TYPE].equals("add")) {
                line = line.replaceFirst("lesson add", "").trim();
                return new AddLessonCommand(line);
            } else if (argumentArray[INDEX_TASK_TYPE].equals("delete")) {
                try {
                    int deleteIndex = Integer.parseInt(line.replaceFirst("lesson delete", "")
                            .trim()) - 1;
                    return new DeleteLessonCommand(deleteIndex);
                } catch (NumberFormatException e) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                }
            } else {
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    /**
     * Parses input if the user input starts with assignment.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns relevant Assignment Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseAssignment(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            return new ViewAssignmentCommand();
        } else {
            if (argumentArray[INDEX_TASK_TYPE].equals("add")) {
                line = line.replaceFirst("assignment add", "").trim();
                return new AddAssignmentCommand(line);
            } else if (argumentArray[INDEX_TASK_TYPE].equals("delete")) {
                try {
                    int deleteIndex = Integer.parseInt(line.replaceFirst("assignment delete", "")
                            .trim()) - 1;
                    return new DeleteAssignmentCommand(deleteIndex);
                } catch (NumberFormatException e) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                }
            } else {
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    /**
     * Parses input if the user input starts with assessment.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns relevant Assessment Commands if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseAssessment(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            return new ViewAssessmentCommand();
        } else {
            if (argumentArray[INDEX_TASK_TYPE].equals("add")) {
                line = line.replaceFirst("assessment add", "").trim();
                return new AddAssessmentCommand(line);
            } else if (argumentArray[INDEX_TASK_TYPE].equals("delete")) {
                try {
                    int deleteIndex = Integer.parseInt(line.replaceFirst("assessment delete", "")
                            .trim()) - 1;
                    return new DeleteAssessmentCommand(deleteIndex);
                } catch (NumberFormatException e) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                }
            } else {
                throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    /**
     * Parses input if the user input starts with snooze.
     * @param argumentArray Command inputted by user in string array format.
     * @param line Command inputted by user in string format.
     * @return Returns an instance of SnoozeCommand if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseSnooze(String[] argumentArray, String line) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            throw new OofException("OOPS!!! Please enter a number!");
        }
        try {
            int snoozeIndex = Integer.parseInt(line.replaceFirst("snooze", "").trim()) - 1;
            return new SnoozeCommand(snoozeIndex);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }

    /**
     * Parses input if the user input starts with recurring.
     * @param argumentArray Command inputted by user in string array format.
     * @return Returns an instance of RecurringCommand if the parameters are valid.
     * @throws OofException Throws exception if the parameters are invalid.
     */
    private static Command parseRecurring(String[] argumentArray) throws OofException {
        if (argumentArray.length == LENGTH_COMMAND_ONLY) {
            throw new OofException("OOPS!!! Please enter the task number and number of recurrences!");
        } else if (argumentArray.length == LENGTH_COMMAND_AND_TASK) {
            throw new OofException("OOPS!!! Please enter the number of recurrences!");
        }
        try {
            int recurringIndex = Integer.parseInt(argumentArray[INDEX_ARGUMENT_TASK_NUMBER].trim()) - 1;
            int recurringCount = Integer.parseInt(argumentArray[INDEX_ARGUMENT_COUNT].trim());
            ui = new Ui();
            ui.printRecurringOptions();
            int recurringFrequency = ui.scanInt();
            return new RecurringCommand(recurringIndex, recurringCount, recurringFrequency);
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!!! Please enter valid numbers!");
        } catch (InputMismatchException e) {
            throw new OofException("OOPS!!! Please enter a valid number!");
        }
    }
}
