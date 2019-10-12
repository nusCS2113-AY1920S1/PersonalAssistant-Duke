package duke.parsers;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.parsers.conversations.AddConversation;
import duke.parsers.conversations.DeadlineConversation;
import duke.parsers.conversations.DeleteConversation;
import duke.parsers.conversations.EventConversation;
import duke.parsers.conversations.FindPathConversation;
import duke.parsers.conversations.FindConversation;
import duke.parsers.conversations.FixedConversation;
import duke.parsers.conversations.FreeTimeConversation;
import duke.parsers.conversations.GetBusRouteConversation;
import duke.parsers.conversations.GetBusStopConversation;
import duke.parsers.conversations.HolidayConversation;
import duke.parsers.conversations.MarkDoneConversation;
import duke.parsers.conversations.Conversation;
import duke.parsers.conversations.RecommendationsConversation;
import duke.parsers.conversations.RepeatConversation;
import duke.parsers.conversations.RescheduleConversation;
import duke.parsers.conversations.SearchConversation;
import duke.parsers.conversations.ToDoConversation;
import duke.parsers.conversations.ViewScheduleConversation;
import duke.parsers.conversations.WithinConversation;
import duke.ui.Ui;

/**
 * Conversation manager for Duke to handle two-way communications.
 */
public class ConversationManager {
    private boolean inConversation;
    private boolean isFinished;
    private Conversation conversation;
    private String output;

    public ConversationManager() {
        inConversation = false;
    }

    /**
     * Start or continue a conversation with Duke.
     * @param input The user input
     * @param ui The ui obj
     */
    public void converse(String input, Ui ui) throws DukeException {
        if (isOngoing()) {
            conversation.execute(input, ui);

            if (conversation.isCancelled()) {
                output = null;
                inConversation = false;
                isFinished = true;
                conversation = null;
            } else if (conversation.getResult() != null) {
                output = conversation.getResult();
                inConversation = false;
                isFinished = true;
                conversation = null;
            }

        } else {
            startConversation(input, ui);
            conversation.execute(input, ui);
            inConversation = true;
        }
    }

    /**
     * Create a Conversation based on input.
     * @param input The command word from input
     */
    public void startConversation(String input, Ui ui) throws DukeException {
        switch (input) {
        case "add":
            conversation = new AddConversation();
            break;
        case "done":
            conversation = new MarkDoneConversation();
            break;
        case "delete":
            conversation = new DeleteConversation();
            break;
        case "findtime":
            conversation = new FreeTimeConversation();
            break;
        case "fetch":
            conversation = new ViewScheduleConversation();
            break;
        case "reschedule":
            conversation = new RescheduleConversation();
            break;
        case "search":
            conversation = new SearchConversation();
            break;
        case "busStop":
            conversation = new GetBusStopConversation();
            break;
        case "busRoute":
            conversation = new GetBusRouteConversation();
            break;
        case "findPath":
            conversation = new FindPathConversation();
            break;
        case "todo":
            conversation = new ToDoConversation();
            break;
        case "deadline":
            conversation = new DeadlineConversation();
            break;
        case "event":
            conversation = new EventConversation();
            break;
        case "find":
            conversation = new FindConversation();
            break;
        case "within":
            conversation = new WithinConversation();
            break;
        case "repeat":
            conversation = new RepeatConversation();
            break;
        case "fixed":
            conversation = new FixedConversation();
            break;
        case "holiday":
            conversation = new HolidayConversation();
            break;
        case "recommend":
            conversation = new RecommendationsConversation();
            break;
        default:
            throw new DukeException(MessagesPrompt.PROMPT_UNKNOWN);
        }
    }

    /**
     * Get the result of Conversation.
     * @return result The String result made from Conversation
     */
    public String getResult() {
        String result = output;
        output = null;
        isFinished = false;
        return result;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isOngoing() {
        return inConversation;
    }

    /**
     * Gets the prompt from Conversation object if applicable.
     * @return Reply The prompt
     */
    public String getPrompt() {
        if (conversation != null) {
            return conversation.getPrompt();
        } else {
            return null;
        }
    }
}
