package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.schedule.ScheduleTeamAllCommand;
import logic.command.schedule.ScheduleTeamTodoCommand;


public class ScheduleTeamParser {

    private static final String SCHEDULE_USAGE = "Usage: schedule tasks {all/todo}";

    //@@author yuyanglin28
    /**
     * parse schedule team
     *
     * @param argument after team, divide to all and todo type
     * @return ScheduleTeamAllCommand or ScheduleTeamTodoCommand
     * @throws DukeException throw exception when schedule tasks type is not correct
     */
    public static Command parseScheduleTeam(String argument) throws DukeException {

        String scheduleType = argument.trim();

        switch (scheduleType) {
        case ScheduleCommandParser.ALL:
            return new ScheduleTeamAllCommand();
        case ScheduleCommandParser.TODO:
            return new ScheduleTeamTodoCommand();
        default:
            throw new DukeException(SCHEDULE_USAGE);
        }

    }
}
