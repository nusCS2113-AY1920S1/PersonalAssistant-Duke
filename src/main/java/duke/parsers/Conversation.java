package duke.parsers;

import duke.commons.PromptMessages;
import duke.commons.exceptions.DukeException;
import duke.parsers.prompts.AddPrompt;
import duke.parsers.prompts.DeadlinePrompt;
import duke.parsers.prompts.DeletePrompt;
import duke.parsers.prompts.EventPrompt;
import duke.parsers.prompts.FindPathPrompt;
import duke.parsers.prompts.FindPrompt;
import duke.parsers.prompts.FixedPrompt;
import duke.parsers.prompts.FreeTimePrompt;
import duke.parsers.prompts.GetBusRoutePrompt;
import duke.parsers.prompts.GetBusStopPrompt;
import duke.parsers.prompts.HolidayPrompt;
import duke.parsers.prompts.MarkDonePrompt;
import duke.parsers.prompts.Prompt;
import duke.parsers.prompts.RepeatPrompt;
import duke.parsers.prompts.ReschedulePrompt;
import duke.parsers.prompts.SearchPrompt;
import duke.parsers.prompts.ToDoPrompt;
import duke.parsers.prompts.ViewSchedulePrompt;
import duke.parsers.prompts.WithinPrompt;
import duke.ui.Ui;

/**
 * Conversation manager for Duke to handle two-way communications.
 */
public class Conversation {
    private boolean inConversation;
    private boolean isFinished;
    private Prompt prompt;
    private String output;

    public Conversation() {
        inConversation = false;
    }

    public void converse(String input, Ui ui) throws DukeException {
        System.out.println("conversing");
        System.out.println("input: " + input);
        if (isOngoing()) {
            System.out.println("is ongoing");
            prompt.execute(input, ui);

            if (prompt.isCancelled()) {
                output = null;
                inConversation = false;
                isFinished = true;
                prompt = null;

            } else if (prompt.getResult() != null) {
                output = prompt.getResult();
                System.out.println("output: " + output);
                inConversation = false;
                isFinished = true;
                prompt = null;
            }

        } else {
            System.out.println("make a new prompt");
            startConversation(input, ui);
            prompt.execute(input, ui);
            inConversation = true;
        }
    }

    /**
     * Create a prompt based on input.
     * @param input The command word from input
     */
    public void startConversation(String input, Ui ui) throws DukeException {
        System.out.println("making new convo with input: " + input);
        switch (input) {
            case "add":
                prompt = new AddPrompt(ui);
                break;
            case "done":
                prompt = new MarkDonePrompt(ui);
                break;
            case "delete":
                prompt = new DeletePrompt(ui);
                break;
            case "findtime":
                prompt = new FreeTimePrompt(ui);
                break;
            case "fetch":
                prompt = new ViewSchedulePrompt(ui);
                break;
            case "reschedule":
                prompt = new ReschedulePrompt(ui);
                break;
            case "search":
                prompt = new SearchPrompt(ui);
                break;
            case "busStop":
                prompt = new GetBusStopPrompt(ui);
                break;
            case "busRoute":
                prompt = new GetBusRoutePrompt(ui);
                break;
            case "findPath":
                prompt = new FindPathPrompt(ui);
                break;case "todo":
                prompt = new ToDoPrompt(ui);
                break;
            case "deadline":
                prompt = new DeadlinePrompt(ui);
                break;
            case "event":
                prompt = new EventPrompt(ui);
                break;
            case "find":
                prompt = new FindPrompt(ui);
                break;
            case "within":
                prompt = new WithinPrompt(ui);
                break;
            case "repeat":
                prompt = new RepeatPrompt(ui);
                break;
            case "fixed":
                prompt = new FixedPrompt(ui);
                break;
            case "holiday":
                prompt = new HolidayPrompt(ui);
                break;
            default:
                throw new DukeException(PromptMessages.PROMPT_UNKNOWN);
        }
    }

    public void continueConversation(String input, Ui ui) {

    }

    public String getResult() {
        String result = output;
        output = null;
        isFinished = false;
        System.out.println("returning: " + result);
        return result;
    }

    public boolean isFinished() { return isFinished; }

    public boolean isOngoing() { return inConversation; }
}
