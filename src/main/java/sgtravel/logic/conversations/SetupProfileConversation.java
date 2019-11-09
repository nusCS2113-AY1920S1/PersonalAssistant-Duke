package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a profile setup command is entered.
 */
public class SetupProfileConversation extends Conversation {
    private static final String COMMAND = "profile";
    private String name;
    private String birthday;

    /**
     * Initialises the SetupProfileConversation object.
     */
    public SetupProfileConversation() {
        super();
        prompt = Messages.PROMPT_SETUP_PROFILE;
    }

    /**
     * Executes Prompt and returns a String reply.
     *
     * @param input The user input.
     */
    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = Messages.PROMPT_BIRTHDAY;
            name = input;
            state++;
            break;
        case 2:
            if (isDateInput(input)) {
                birthday = input;
                state++;
                buildResult();
                setFinished(true);
            }
            break;
        default:
            prompt = Messages.PROMPT_ERROR;
            break;
        }
        tryCancelConversation(input);
    }

    /**
     * Builds the result of the conversation string.
     */
    @Override
    protected void buildResult() {
        if (name != null && birthday != null) {
            result = COMMAND + " " + name + " " + birthday;
        } else {
            attempts++;
        }
    }
}
