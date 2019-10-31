package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.EventSelectionOutOfBoundsException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.edits.Editor;
import duke.model.Event;
import duke.model.Model;

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

    public QuickEditCommand(int index, String... descriptors) {
        this(index);
        this.descriptors = descriptors;
    }

    @Override
    public CommandResultText execute(Model model) throws ApiException,
            FileNotSavedException, CorruptedFileException, EventSelectionOutOfBoundsException,
            ParseException {
        Event event = model.getEvents().get(index);
        Editor.edit(descriptors[DESCRIPTION], event, DESCRIPTION);
        Editor.edit(descriptors[START_DATE], event, START_DATE);
        Editor.edit(descriptors[END_DATE], event, END_DATE);
        model.save();
        return new CommandResultText(MESSAGE_EDIT_SUCCESS + event);
    }
}
