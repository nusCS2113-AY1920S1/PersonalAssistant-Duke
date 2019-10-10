package duke.parsers.prompts;

import duke.ui.Ui;

/**
 * Abstract class representing individual Prompt.
 */
public abstract class Prompt {
    protected String result;
    protected String message;
    protected int state;
    protected boolean isDone;
    protected boolean isCancelled;
    protected int attempts;

    /**
     * Initialises the Prompt object.
     */
    public Prompt() {
        state = 0;
        isDone = false;
        isCancelled = false;
        result = null;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    public abstract void execute(String input, Ui ui);

    public String getReply() {
        return message;
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
