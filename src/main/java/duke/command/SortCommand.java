package duke.command;

import duke.Duke;
import duke.exception.DukeException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortCommand extends Command {
    private static final String name = "sort";
    private static final String description = "Sort expenses according to a given criteria";
    private static final String usage = "sort $criteria";

    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs an {@code SortCommand} object.
     */
    public SortCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        String mainParam = commandParams.getMainParam();
        duke.expenseList.setSortCriteria(mainParam);
    }

}
