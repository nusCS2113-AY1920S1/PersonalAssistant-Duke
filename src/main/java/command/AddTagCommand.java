package command;

import dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashSet;

public class AddTagCommand extends Command {
    ArrayList<String> tags;
    String wordDescription;

    public AddTagCommand(String word, ArrayList<String> tags) {
        this.wordDescription = word;
        this.tags = tags;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            HashSet<String> tagList = wordBank.addTag(wordDescription, tags);
            return ui.showAddTag(wordDescription, tags, tagList);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
