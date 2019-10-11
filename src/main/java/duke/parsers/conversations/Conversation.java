package duke.parsers.conversations;

import duke.ui.Ui;

/**
 * Abstract class representing individual Prompt.
 */
public abstract class Conversation {
    protected String result;
    protected String prompt;
    protected int state;
    protected boolean isDone;
    protected boolean isCancelled;
    protected int attempts;

    /**
     * Initialises the Prompt object.
     */
    public Conversation() {
        state = 0;
        isDone = false;
        isCancelled = false;
        result = null;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    public abstract void execute(String input, Ui ui);

    public String getPrompt() {
        return prompt;
    }

    public void badAttempt() {
        attempts++;
        isDone = true;
    }

    /**
     * Gets result of prompt if applicable.
     * @return result The result
     */
    public String getResult() {
        if (isDone) {
            return result;
        } else {
            return null;
        }
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    abstract void buildResult();
}
