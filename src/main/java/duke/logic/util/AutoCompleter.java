package duke.logic.util;

import duke.commons.LogsCenter;
import duke.logic.command.*;
import duke.ui.MainWindow;

import java.util.*;
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
    private boolean isCompletable;
    private String lastSuggestion;
    private Purpose purpose;
    private String fromInput;
    private int numberOfTokens;
    private int startIndexOfLastToken;
    private List<String> suggestionList;
    private int suggestionIndex;


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

        suggestionList = new ArrayList<String>();
        lastSuggestion = EMPTY_STRING;
    }

    private enum Purpose {
        COMPLETE_COMMAND_NAME,
        GENERATE_PARAMETER,
        COMPLETE_PARAMETER,
        ITERATE,
        NOT_DOABLE;
    }

    // criteria for purpose
    private boolean isSameAsLastSuggestion() {
        return fromInput.equals(lastSuggestion);
    }

    private boolean isEmpty() {
        logger.info("1.1");
        return fromInput.trim().equals(EMPTY_STRING);
    }

    private boolean endsWithSpace() {
        return fromInput.endsWith(SPACE);
    }

    private boolean inUncompletedParameter() {
        return getLastToken().startsWith(PARAMETER_INDICATOR);
    }

    private boolean hasValidCommandName() {
        logger.info("3.1");
        logger.info("The size of commandNames is " + allCommandNames.size());
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
        while(index >= 0 && SPACE.charAt(0) != fromInput.charAt(index)) {
            index -= 1;
        }
        startIndexOfLastToken = index + 1;
        return fromInput.substring(startIndexOfLastToken);
    }

    public void receiveText(String fromInput) {
        this.fromInput = fromInput;
        isCompletable = true;
        logger.info("start receive Text");
        if (isEmpty()) {
            purpose = Purpose.NOT_DOABLE;
        } else if (isSameAsLastSuggestion()) {
            purpose = Purpose.ITERATE;
        } else if (!hasValidCommandName()) {
            if(numberOfTokens > 1 || endsWithSpace()) {
                purpose = Purpose.NOT_DOABLE;
            } else {
                purpose = Purpose.COMPLETE_COMMAND_NAME;
            }
        } else if (endsWithSpace()) {
            purpose = Purpose.GENERATE_PARAMETER;
        } else if (inUncompletedParameter()) {
            logger.info("5");
            purpose = Purpose.COMPLETE_PARAMETER;
        } else if (numberOfTokens == 1){
            logger.info("6");
            purpose = Purpose.COMPLETE_COMMAND_NAME;
        } else {
            logger.info("7");
            purpose = Purpose.NOT_DOABLE;
        }
        logger.info("purpose decided.");
    }

    private void generateCommandNameSuggestions() {
        String unCompletedCommandName = getLastToken();
        suggestionList = allCommandNames.stream()
                .filter(s -> s.startsWith(unCompletedCommandName)).collect(Collectors.toList());
    }

    private void completeParameterSuggestions() {
        String unCompletedParameter = getLastToken().substring(1);
        List<String> usableParameters = allSecondaryParams.get(getCommandName());
        List<String> options = usableParameters.stream()
                .filter(s -> s.startsWith(unCompletedParameter)).collect(Collectors.toList());
        logger.info("options for suggestionList lengths " + options.size());
        suggestionList = options.stream().map(s -> PARAMETER_INDICATOR + s).collect(Collectors.toList());
    }

    private void generateParameterSuggestions() {
        String empty = getLastToken(); // updates the starting index of last token.
        List<String> options = allSecondaryParams.get(getCommandName());
        suggestionList = options.stream().map(s -> PARAMETER_INDICATOR + s).collect(Collectors.toList());
    }

    public String getFullSuggestion() {
        String suggestion = getSuggestion();
        lastSuggestion = fromInput.substring(0, startIndexOfLastToken) + suggestion;
        return fromInput.substring(0, startIndexOfLastToken) + suggestion;
    }

    public boolean isCompletable() {
        return isCompletable;
    }

    private String getSuggestion() {
        switch (purpose) {
            case COMPLETE_COMMAND_NAME:
                generateCommandNameSuggestions();
                suggestionIndex = 0;
                break;

            case GENERATE_PARAMETER:
                generateParameterSuggestions();
                suggestionIndex = 0;
                break;

            case COMPLETE_PARAMETER:
                logger.info("Completing parameter");
                completeParameterSuggestions();
                suggestionIndex = 0;
                break;

            case ITERATE:
                logger.info("reach ");
                iterateSuggestionIndex();
                break;

            case NOT_DOABLE:
                isCompletable = false;
                suggestionList.clear();
        }

        if(suggestionList.isEmpty()) {
            isCompletable = false;
            return EMPTY_STRING;
        } else {
            return suggestionList.get(suggestionIndex);
        }
    }

    private void iterateSuggestionIndex() {
        suggestionIndex += 1;
        if(suggestionIndex >= suggestionList.size()) {
            suggestionIndex = 0;
        }
    }

}
