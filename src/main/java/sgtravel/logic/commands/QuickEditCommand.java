package sgtravel.logic.commands;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.logic.edits.Editor;
import sgtravel.model.Event;
import sgtravel.model.Model;

/**
 * Edits an event.
 */
public class QuickEditCommand extends Command {
    private static final String MESSAGE_EDIT_SUCCESS = "The following is successfully changed:\n";
    private static final int DESCRIPTION = 0;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;
    private int index;
    private String[] descriptors;

    private QuickEditCommand(int index) {
        this.index = index;
    }

    /**
     * Constructs a QuickEditCommand object.
     * @param index The index of the Event.
     * @param descriptors The new descriptors of the Event.
     */
    public QuickEditCommand(int index, String... descriptors) {
        this(index);
        this.descriptors = descriptors;
    }

    /**
     * Executes this command and returns a text result.
     * @param model {@code Model} which the command should operate on.
     * @throws ApiException If the api call fails.
     * @throws FileNotSavedException If the data cannot be saved.
     * @throws OutOfBoundsException If the index given is out of bounds.
     * @throws ParseException If the descriptors cannot be parsed.
     */
    @Override
    public CommandResultText execute(Model model) throws ApiException,
            FileNotSavedException, OutOfBoundsException,
            ParseException {
        try {
            Event event = model.getEvents().get(index);
            Editor.edit(descriptors[DESCRIPTION], event, DESCRIPTION);
            Editor.edit(descriptors[START_DATE], event, START_DATE);
            Editor.edit(descriptors[END_DATE], event, END_DATE);
            model.save();
            return new CommandResultText(MESSAGE_EDIT_SUCCESS + event);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
