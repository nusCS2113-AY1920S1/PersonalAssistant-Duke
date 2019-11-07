package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.schedule.ScheduleTeamAllCommand;
import logic.command.schedule.ScheduleTeamTodoCommand;


public class ScheduleTeamParser {

    private static final String ALL = "all";
    private static final String TODO = "todo";
    private static final String SCHEDULE_USAGE = "Usage: schedule [tasks/member] [all/todo] {member name}";

    //@@author yuyanglin28
    /**
     * parse schedule team
     * @param argument after team, divide to all and todo type
     * @return ScheduleTeamAllCommand or ScheduleTeamTodoCommand
     * @throws DukeException exception
     */
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
