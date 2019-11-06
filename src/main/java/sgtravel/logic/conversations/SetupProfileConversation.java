package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

public class SetupProfileConversation extends Conversation {
    private static final String COMMAND = "profile";
    private String name;
    private String birthday;

    public SetupProfileConversation() {
        super();
        prompt = Messages.PROMPT_SETUP_PROFILE;
    }

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

    @Override
    protected void buildResult() {
        if (name != null && birthday != null) {
            result = COMMAND + " " + name + " " + birthday;
        } else {
            attempts++;
        }
    }
}
