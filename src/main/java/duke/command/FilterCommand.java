package duke.command;

import duke.Duke;
import duke.exception.DukeException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterCommand extends Command {
    private static final String name = "filter";
    private static final String description = "Filter expenses according to a given criteria";
    private static final String usage = "filter $criteria";

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
     * Constructs an {@code FilterCommand} object.
     */
    public FilterCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        String mainParam = commandParams.getMainParam();
        duke.expenseList.setFilterCriteria(mainParam);
    }

}
