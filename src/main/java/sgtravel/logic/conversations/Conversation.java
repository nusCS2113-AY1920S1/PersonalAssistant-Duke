package sgtravel.logic.conversations;

import sgtravel.commons.Messages;
import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.parsers.ParserTimeUtil;

/**
 * Abstract class representing an individual Conversation.
 */
public abstract class Conversation {
    protected String result;
    protected String prompt;
    protected int state;
    protected int attempts;
    private static final int ATTEMPTS_LIMIT = 3;
    private boolean isFinished;

    /**
     * Initialises the Conversation object.
     */
    public Conversation() {
        isFinished = false;
        attempts = 0;
        state = 1;
        result = null;
    }

    /**
     * Executes Prompt and returns a String reply.
     *
     * @param input The user input.
     */
    public abstract void execute(String input);

    /**
     * Builds the result of the conversation string.
     */
    protected abstract void buildResult();

    public String getPrompt() {
        return prompt;
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result.
     */
    public String getResult() {
        assert (result != null) : "result should not be null";
        return result;
    }

    protected void setFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * Checks if input is int.
     *
     * @param input The input.
     * @return true If the input is int.
     */
    protected Boolean isIntInput(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            attempts++;
            prompt = Messages.PROMPT_NOT_INT;
            return false;
        }
    }

    /**
     * Checks if input is a field of a Route.
     *
     * @param input The input.
     * @return true If the input is a field of a Route.
     */
    protected boolean isRouteField(String input) {
        if ("name".equals(input) || "description".equals(input)) {
            return true;
        } else {
            attempts++;
            prompt = Messages.PROMPT_NOT_ROUTE_FIELD;
            return false;
        }
    }

    /**
     * Checks if input is a Constraint enum.
     *
     * @param input The input.
     * @return true If the input is a Constraint.
     */
    protected boolean isConstraint(String input) {
        try {
            Constraint.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            attempts++;
            prompt = Messages.ERROR_CONSTRAINT_UNKNOWN;
            return false;
        }
    }

    /**
     * Checks if input is a DateTime.
     *
     * @param input The input.
     * @return true If the input is a DateTime.
     */
    protected Boolean isDateInput(String input) {
        try {
            ParserTimeUtil.parseStringToDate(input);
            return true;
        } catch (ParseException e) {
            attempts++;
            prompt = Messages.PROMPT_NOT_DATE;
            return false;
        }
    }

    /**
     * Tries to cancel the conversation.
     *
     * @param userInput The userInput from UI object.
     */
    protected void tryCancelConversation(String userInput) {
        if (attempts > ATTEMPTS_LIMIT || "cancel".equals(userInput)) {
            result = "cancel";
            isFinished = true;
        }
    }

    /**
     * Returns whether the conversation is finished.
     *
     * @return true If the conversation is finished.
     */
    public boolean isFinished() {
        return isFinished;
    }
}
