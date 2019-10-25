package duke.logic.util;

import duke.commons.LogsCenter;
import duke.logic.command.AddExpenseCommand;
import duke.logic.command.Command;
import duke.logic.command.ConfirmTentativeCommand;
import duke.logic.command.DeleteExpenseCommand;
import duke.logic.command.ExitCommand;
import duke.logic.command.FilterExpenseCommand;
import duke.logic.command.GoToCommand;
import duke.logic.command.PlanBotCommand;
import duke.logic.command.SortExpenseCommand;
import duke.logic.command.ViewExpenseCommand;
import duke.ui.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoCompleter {

    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String PARAMETER_INDICATOR = "/";

    private final List<String> allCommandNames;
    private final HashMap<String, List<String>> allSecondaryParams;
    private String lastComplement;
    private Purpose purpose;
    private String fromInput;
    private int numberOfTokens;
    private int startIndexOfLastToken;
    private List<String> complementList;
    private int iteratingIndex;

    private static final Supplier<Stream<Command>> COMMANDS = () -> Stream.of(
            new AddExpenseCommand(),
            new DeleteExpenseCommand(),
            new ConfirmTentativeCommand(),
            new ExitCommand(),
            new FilterExpenseCommand(),
            new SortExpenseCommand(),
            new ViewExpenseCommand(),
            new GoToCommand(),
            new PlanBotCommand()
    );

    public AutoCompleter() {
        allCommandNames = COMMANDS.get().map(Command::getName).collect(Collectors.toList());

        allSecondaryParams = new HashMap<String, List<String>>();
        COMMANDS.get().forEach(c -> {
            Set<String> secondaryParamSet = c.getSecondaryParams().keySet();
            List<String> secondaryParamList = new ArrayList<String>(secondaryParamSet);
            allSecondaryParams.put(c.getName(), secondaryParamList);
        });

        complementList = new ArrayList<String>();
        lastComplement = EMPTY_STRING;
    }

    private enum Purpose {
        COMPLETE_COMMAND_NAME,
        PRODUCE_PARAMETER,
        COMPLETE_PARAMETER,
        ITERATE,
        NOT_DOABLE;
    }

    // criteria for purpose
    private boolean isSameAsLastComplement() {
        return fromInput.equals(lastComplement);
    }

    private boolean isEmpty() {
        return fromInput.trim().equals(EMPTY_STRING);
    }

    private boolean endsWithSpace() {
        return fromInput.endsWith(SPACE);
    }

    private boolean inUncompletedParameter() {
        return getLastToken().startsWith(PARAMETER_INDICATOR);
    }

    private boolean hasValidCommandName() {
        return allCommandNames.contains(getCommandName());
    }

    private String getCommandName() {
        List<String> tokens = Arrays.asList(fromInput.split(SPACE));
        tokens = tokens.stream().filter(s -> !s.equals(EMPTY_STRING)).collect(Collectors.toList());
        tokens = tokens.stream().map(String::trim).collect(Collectors.toList());
        numberOfTokens = tokens.size();

        return tokens.get(0);
    }

    private String getLastToken() {
        int index = fromInput.length() - 1;
        while (index >= 0 && SPACE.charAt(0) != fromInput.charAt(index)) {
            index -= 1;
        }
        startIndexOfLastToken = index + 1;
        return fromInput.substring(startIndexOfLastToken);
    }

    public void receiveText(String fromInput) {
        this.fromInput = fromInput;
        logger.info("start receive Text");
        if (isEmpty()) {
            purpose = Purpose.NOT_DOABLE;
        } else if (isSameAsLastComplement()) {
            purpose = Purpose.ITERATE;
        } else if (!hasValidCommandName()) {
            if (numberOfTokens > 1 || endsWithSpace()) {
                purpose = Purpose.NOT_DOABLE;
            } else {
                purpose = Purpose.COMPLETE_COMMAND_NAME;
            }
        } else if (endsWithSpace()) {
            purpose = Purpose.PRODUCE_PARAMETER;
        } else if (inUncompletedParameter()) {
            purpose = Purpose.COMPLETE_PARAMETER;
        } else if (numberOfTokens == 1) {
            purpose = Purpose.COMPLETE_COMMAND_NAME;
        } else {
            purpose = Purpose.NOT_DOABLE;
        }
        logger.info("purpose decided.");
    }

    private void completeCommandNameComplements() {
        String unCompletedCommandName = getLastToken();
        complementList = allCommandNames.stream()
                .filter(s -> s.startsWith(unCompletedCommandName)).collect(Collectors.toList());
    }

    private void completeParameterComplements() {
        String unCompletedParameter = getLastToken().substring(1);
        List<String> usableParameters = allSecondaryParams.get(getCommandName());
        List<String> options = usableParameters.stream()
                .filter(s -> s.startsWith(unCompletedParameter)).collect(Collectors.toList());
        logger.info("options for suggestionList lengths " + options.size());
        complementList = options.stream().map(s -> PARAMETER_INDICATOR + s).collect(Collectors.toList());
    }

    private void produceParameterComplements() {
        String empty = getLastToken(); // updates the starting index of last token.
        List<String> options = allSecondaryParams.get(getCommandName());
        complementList = options.stream().map(s -> PARAMETER_INDICATOR + s).collect(Collectors.toList());
    }

    public String getFullComplement() {
        String complement = getComplement();
        lastComplement = fromInput.substring(0, startIndexOfLastToken) + complement;
        return lastComplement;
    }

    private String getComplement() {
        switch (purpose) {
        case COMPLETE_COMMAND_NAME:
            completeCommandNameComplements();
            iteratingIndex = 0;
            break;

        case PRODUCE_PARAMETER:
            produceParameterComplements();
            iteratingIndex = 0;
            break;

        case COMPLETE_PARAMETER:
            completeParameterComplements();
            iteratingIndex = 0;
            break;

        case ITERATE:
            iterateIndex();
            break;

        case NOT_DOABLE:
            complementList.clear();
            break;
        }

        if (complementList.isEmpty()) {
            return getLastToken();
        } else {
            return complementList.get(iteratingIndex);
        }
    }

    private void iterateIndex() {
        iteratingIndex += 1;
        if (iteratingIndex >= complementList.size()) {
            iteratingIndex = 0;
        }
    }

}
