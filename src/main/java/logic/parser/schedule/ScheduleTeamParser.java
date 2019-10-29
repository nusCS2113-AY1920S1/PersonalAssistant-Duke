package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.ScheduleTeamAllCommand;
import logic.command.ScheduleTeamTodoCommand;


public class ScheduleTeamParser {

    private static final String ALL = "all";
    private static final String TODO = "todo";
    private static final String SCHEDULE_USAGE = "Usage: schedule [team/member] [all/todo] {member name}";

    public static Command parseScheduleTeam(String argument) throws DukeException {

        String scheduleType = argument.trim();

        switch (scheduleType) {
            case ALL:
                return new ScheduleTeamAllCommand();
            case TODO:
                return new ScheduleTeamTodoCommand();
            default:
                throw new DukeException(SCHEDULE_USAGE);
        }

    }
}
