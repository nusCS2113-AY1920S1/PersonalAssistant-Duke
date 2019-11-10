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
import duke.logic.command.BudgetCommand;
import duke.logic.command.ViewBudgetCommand;
import duke.logic.command.payment.AddPaymentCommand;
import duke.logic.command.payment.ChangePaymentCommand;
import duke.logic.command.payment.DeletePaymentCommand;
import duke.logic.command.payment.DonePaymentCommand;
import duke.logic.command.payment.FilterPaymentCommand;
import duke.logic.command.payment.SearchPaymentCommand;
import duke.logic.command.payment.SortPaymentCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 * Provides a auto-complete to what the user has typed in userInput when TAB key is pressed.
 * It can complete a commandName or iterate through all suitable commandNames.
 * It can also produce a parameter name, complete a parameter name and iterate through all suitable parameter names.
 * If the given input ends with space, autoCompleter will produce a parameter name.
 * If the given input doesn't end with space and is different from last complement,
 * autoCompleter will complete the fragment after the last space, which can be a commandName or a parameter.
 * Instead, if the given input is same as last complement,
 * autoCompleter will iterate through other suitable complements in the list.
 * <p>
 * The mechanism of complement and iteration is firstly getting a suitable token(complement),
 * which can be either a complete commandName or parameter, or the fragment after the last space in input
 * in case that no suitable complements can be applied to current input.
 * Then auto-completer replaces the fragment after the last space of input with this suitable complement,
 * and lastly returns the modified input to userInput TextField.
 */
public class AutoCompleter {

    private static final Logger logger = LogsCenter.getLogger(AutoCompleter.class);

    /**
     * Works as a detector of an empty string.
     * A keyword used to decide the purpose of complement.
     **/
    private static final String EMPTY_STRING = "";

    /**
     * Works as a space regex to tokenize the input.
     * A keyword used to decide the purpose of complement.
     */
    private static final String SPACE = " ";

    /**
     * Works as a indicator of secondary parameter.
     * A keyword used to decide the purpose of complement.
     */
    private static final String PARAMETER_INDICATOR = "/";

    /**
     * Indicates the first element of a list.
     */
    private static final int INITIAL_INDEX = 0;

    /**
     * Helps conversion between 0-based and 1-based indexes.
     */
    private static final int BASE_CONVERSION = 1;

    /**
     * The list storing all commands' names.
     * Works as the information source to complete command names.
     **/
    private final List<String> allCommandNames;

    /**
     * Maps each commandNames to their respective collections of secondaryParams' names.
     **/
    private final HashMap<String, List<String>> allSecondaryParams;

    /**
     * The most recent complement provided by auto-completer.
     **/
    private String lastComplement;

    /**
     * The content inside the userInput TextField when TAB key is pressed.
     **/
    private String fromInput;

    /**
     * Number of tokens of {@code fromInput}.
     **/
    private int numberOfTokens;

    /**
     * The starting index of the fragment after the last space in {@code fromInput}.
     **/
    private int startIndexOfLastFragment;

    /**
     * All suitable tokens that can replace the fragment after the last space of {@code fromInput}.
     **/
    private List<String> complementList;

    /**
     * The index used to iterate through {@code complementList}.
     **/
    private int iteratingIndex;

    /**
     * A supplier that supplies streams of all command classes.
     * It works as the source of all complements information.
     **/
    private static final Supplier<Stream<Command>> COMMANDS = () -> Stream.of(
            new AddExpenseCommand(),
            new DeleteExpenseCommand(),
            new ConfirmTentativeCommand(),
            new ExitCommand(),
            new FilterExpenseCommand(),
            new SortExpenseCommand(),
            new ViewExpenseCommand(),
            new GoToCommand(),
            new PlanBotCommand(),
            new AddPaymentCommand(),
            new ChangePaymentCommand(),
            new DeletePaymentCommand(),
            new FilterPaymentCommand(),
            new SearchPaymentCommand(),
            new SortPaymentCommand(),
            new DonePaymentCommand(),
            new BudgetCommand(),
            new ViewBudgetCommand()
    );

    /**
     * Purposes of complement.
     */
    private enum Purpose {
        COMPLETE_COMMAND_NAME,
        PRODUCE_PARAMETER,
        COMPLETE_PARAMETER,
        ITERATE,
        NOT_DOABLE;
    }

    /**
     * Constructs a auto-completer.
     * All commandNames are stored in {@code allCommandNames}.
     * All collections of secondaryParams of each command are stored in {@code allSecondaryParams}.
     * The {@code complementList} is initialized as an empty ArrayList.
     * The {@code lastComplement} is initialized as an empty String.
     */
    public AutoCompleter() {
        allCommandNames = COMMANDS.get().map(Command::getName).collect(Collectors.toList());
        assert !allCommandNames.isEmpty();

        allSecondaryParams = new HashMap<String, List<String>>();
        COMMANDS.get().forEach(command -> {
            Set<String> secondaryParamSet = command.getSecondaryParams().keySet();
            List<String> secondaryParamList = new ArrayList<String>(secondaryParamSet);
            // maps commandName to collections of its all secondaryParams' names
            allSecondaryParams.put(command.getName(), secondaryParamList);
        });
        assert !allSecondaryParams.isEmpty();

        complementList = new ArrayList<String>();
        lastComplement = EMPTY_STRING;

        logger.info("Auto Completer is constructed.");
    }


    /**
     * Receives the content in userInput TextField when TAB key is pressed.
     * Stores it in {@code fromInput}.
     *
     * @param fromInput The content in userInput TextField.
     */
    public void receiveText(String fromInput) {
        this.fromInput = requireNonNull(fromInput);
        logger.info("Received text for auto-completer.");
    }

    /**
     * Replaces the fragment after the last space of {@code fromInput} with complement token.
     * and stores this modified String in {@code lastComplement}.
     * This modified String is the full complement and will be set as text in userInput TextField.
     * <p>
     * i.e full complement = fromInput - fragment after the last space + complement token.
     *
     * @return Full complement going to be set in userInput TextField.
     */
    public String getFullComplement() {
        Purpose purpose = getPurpose();
        requireNonNull(purpose);

        String complement = getComplement(purpose);
        lastComplement = getTailoredInput() + complement;
        return lastComplement;
    }

    /**
     * Decides the {@code purpose} with various criteria.
     */
    private Purpose getPurpose() {
        if (isEmpty()) {
            return Purpose.NOT_DOABLE;
        }

        if (isSameAsLastComplement()) {
            return Purpose.ITERATE;
        }

        if (!hasValidCommandName()) {
            if (numberOfTokens > 1 || endsWithSpace()) {
                return Purpose.NOT_DOABLE;
            }
            return Purpose.COMPLETE_COMMAND_NAME;
        }

        if (endsWithSpace()) {
            return Purpose.PRODUCE_PARAMETER;
        }

        if (inUncompletedParameter()) {
            return Purpose.COMPLETE_PARAMETER;
        }

        if (numberOfTokens == 1) {
            return Purpose.COMPLETE_COMMAND_NAME;
        }

        logger.info("The original input itself is already complete.");
        return Purpose.NOT_DOABLE;
    }

    /**
     * Gets the complement token.
     * If the {@code purpose} is not {@code ITERATE},
     * it firstly generates {@code complementList} containing all suitable complements(tokens)
     * and chooses the first element as complement token.
     * If the {@code purpose} is {@code ITERATE},
     * the {@code iteratingIndex} increases by one and the element at {@code iteratingIndex}
     * in the existing {@code complementList} will be chose as complement token.
     *
     * @return The complement token.
     */
    private String getComplement(Purpose purpose) {
        switch (purpose) {
        case COMPLETE_COMMAND_NAME:
            completeCommandNameComplements();
            iteratingIndex = INITIAL_INDEX;
            break;

        case PRODUCE_PARAMETER:
            produceParameterComplements();
            iteratingIndex = INITIAL_INDEX;
            break;

        case COMPLETE_PARAMETER:
            completeParameterComplements();
            iteratingIndex = INITIAL_INDEX;
            break;

        case ITERATE:
            iterateIndex();
            break;

        case NOT_DOABLE:
            complementList.clear();
            break;

        default:
            logger.warning("Purpose takes unexpected value.");
            break;
        }

        if (complementList.isEmpty()) { // return original last token if there's no suitable complement
            return getFragmentAfterLastSpace();
        }

        assert iteratingIndex < complementList.size();

        return complementList.get(iteratingIndex);
    }

    /**
     * Cuts off the fragment after the last space from {@code fromInput}.
     *
     * @return A tailored input without the last fragment.
     */
    private String getTailoredInput() {
        return fromInput.substring(INITIAL_INDEX, startIndexOfLastFragment);
    }

    /**
     * Tests whether the given input is same as the most recent complement.
     * This is a criteria to help decide purpose.
     *
     * @return True if {@code fromInput} is same as {@code lastComplement} and false otherwise.
     */
    private boolean isSameAsLastComplement() {
        return fromInput.equals(lastComplement);
    }

    /**
     * Tests whether the given input is empty.
     * This is a criteria to help decide purpose.
     *
     * @return True if {@code fromInput} is an empty String and false otherwise.
     */
    private boolean isEmpty() {
        return fromInput.trim().equals(EMPTY_STRING);
    }

    /**
     * Tests whether the given input ends with a space.
     * This is a criteria to help decide purpose.
     *
     * @return True if {@code fromInput} ends with space and false otherwise.
     */
    private boolean endsWithSpace() {
        return fromInput.endsWith(SPACE);
    }

    /**
     * Tests whether the fragment after the last space of given input starts with {@code PARAMETER_INDICATOR}.
     * This is a criteria to help decide purpose.
     *
     * @return True if the fragment after the last space starts with {@code PARAMETER_INDICATOR}.
     */
    private boolean inUncompletedParameter() {
        return getFragmentAfterLastSpace().startsWith(PARAMETER_INDICATOR);
    }

    /**
     * Gets the first token of the given input.
     * Updates {@code numberOfTokens} after the given input is tokenized.
     *
     * @return The first token of {@code fromInput}
     */
    private String getCommandName() {
        List<String> tokens = Arrays.asList(fromInput.split(SPACE));
        // Gets rid of empty tokens
        tokens = tokens.stream().filter(s -> !s.equals(EMPTY_STRING)).collect(Collectors.toList());
        // Gets rid of extra space
        tokens = tokens.stream().map(String::trim).collect(Collectors.toList());
        numberOfTokens = tokens.size();

        return tokens.get(INITIAL_INDEX);
    }

    /**
     * Tests whether the first token of given input is a valid commandName.
     * This is a criteria to help decide purpose.
     *
     * @return True if the first token of {@code fromInput} is a valid commandName and false otherwise.
     */
    private boolean hasValidCommandName() {
        return allCommandNames.contains(getCommandName());
    }

    /**
     * Gets the fragment after the last space of given input.
     * Updates {@code startIndexOfLastFragment}.
     *
     * @return The last fragment of {@code fromInput}.
     */
    private String getFragmentAfterLastSpace() {
        int index = fromInput.length() - BASE_CONVERSION;

        boolean isSpaceNotReached;

        // gets the position of the last space in input
        while (index >= INITIAL_INDEX) {
            isSpaceNotReached = (fromInput.charAt(index) != SPACE.charAt(INITIAL_INDEX));

            if(isSpaceNotReached) {
                index--;
            } else {
                break;
            }
        }

        startIndexOfLastFragment = index + BASE_CONVERSION;
        assert startIndexOfLastFragment <= fromInput.length();

        return fromInput.substring(startIndexOfLastFragment);
    }

    /**
     * Fills the {@code complementList} with all complete versions of current commandName.
     * This is called when purpose is {@code COMPLETE_COMMAND_NAME}.
     */
    private void completeCommandNameComplements() {
        String unCompletedCommandName = getFragmentAfterLastSpace();
        complementList = allCommandNames.stream()
                .filter(s -> s.startsWith(unCompletedCommandName)).collect(Collectors.toList());

        logger.info("ComplementList for command names is constructed.");
    }

    /**
     * Fills the {@code complementList} with all complete versions of current secondaryParameter.
     * This is called when purpose is {@code COMPLETE_PARAMETER}.
     */
    private void completeParameterComplements() {
        String unCompletedParameter = getFragmentAfterLastSpace().substring(1); // gets rid of "/" at index 0
        List<String> options = allSecondaryParams.get(getCommandName());
        List<String> usableParameters = options.stream()
                .filter(s -> s.startsWith(unCompletedParameter)).collect(Collectors.toList());
        complementList = usableParameters.stream().map(s -> PARAMETER_INDICATOR + s).collect(Collectors.toList());

        logger.info("ComplementList for parameter names is constructed.");
    }

    /**
     * Fills the {@code complementList} with all secondaryParameters belonging to commandName.
     * This is called when purpose is {@code PRODUCE_PARAMETER}.
     */
    private void produceParameterComplements() {
        getFragmentAfterLastSpace();
        List<String> options = allSecondaryParams.get(getCommandName());
        complementList = options.stream().map(s -> PARAMETER_INDICATOR + s).collect(Collectors.toList());

        logger.info("ComplementList producing parameter names is constructed.");
    }

    /**
     * Iterates the index of {@code complementList} by adding it by one.
     * This is called when purpose is {@code ITERATE}.
     */
    private void iterateIndex() {
        iteratingIndex++;
        if (iteratingIndex >= complementList.size()) {
            iteratingIndex = INITIAL_INDEX;
        }

        logger.info("Index has been iterated.");
    }

}
