package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.model.PlanBot;
import duke.storage.Storage;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlanBotCommand extends  Command{
    private static final String name = "plan";
    private static final String description = "a reply to planBot";
    private static final String usage = "sends the user input to planBot";

    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }


    public  PlanBotCommand() {
        super(name, description, usage, Stream.of(PlanBotCommand.SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        model.processPlanInput(commandParams.getMainParam());
        storage.savePlanAttributes(model.getKnownPlanAttributes());
        return new CommandResult("PlanBot replied!");
    }
}
