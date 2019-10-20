package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoToCommand extends Command {

    private static final String name = "goto";
    private static final String description = "go to a desired page.";
    private static final String usage = "goto $paneName";

    private static final String COMPLETE_MESSAGE = "";

    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    public GoToCommand() {
        super(name,
                description,
                usage,
                Stream.of(GoToCommand.SecondaryParam.values())
                        .collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        String desiredPage = commandParams.getMainParam();
        CommandResult.DisplayedPane displayedPane;
        try {
            displayedPane = CommandResult.DisplayedPane.valueOf(desiredPage.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_DESIRED_PANE_NAME_INVALID, desiredPage));
        }
        return new CommandResult(COMPLETE_MESSAGE, displayedPane);
    }
}
