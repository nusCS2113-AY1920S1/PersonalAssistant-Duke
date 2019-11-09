package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.UiCode;

public class CommandUntrackTag extends Command {
    private String inputStr;

    /**
     * Constructor for CommandUntrack Class.
     */
    public CommandUntrackTag(String userInput) {
        super();
        this.commandType = CommandType.UNTRACK;
        this.userInput = userInput;
        this.inputStr = Parser.parseForPrimaryInput(this.commandType, userInput);
        this.description = "Untrack a particular tag. \n"
                + "FORMAT : UNTRACK <tag>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        String[] tagsToTrack = this.inputStr.split(" ");
        if (tagsToTrack[0].equals("")) {
            this.infoCapsule.setUiCode(UiCode.ERROR);
            this.infoCapsule.setOutputStr("Please enter a tag to untrack");
            return;
        }
        for (String tag : tagsToTrack) {
            try {
                storageManager.untrackTag(tag);
            } catch (DukeException e) {
                this.infoCapsule.setUiCode(UiCode.ERROR);
                this.infoCapsule.setOutputStr(e.getMessage()
                        + "\nIf you wish to track this tag, try TRACK <tag>.");
                return;
            }
        }
        this.infoCapsule.setUiCode(UiCode.TOAST);
        this.infoCapsule.setOutputStr("Untracked tags: " + this.inputStr);
    }
}
