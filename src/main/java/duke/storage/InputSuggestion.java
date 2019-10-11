package duke.storage;

import duke.commons.util.CollectionUtil;
import duke.logic.parser.commons.CliSyntax;
import duke.model.BakingHome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class InputSuggestion extends ArrayList {

    public static ArrayList<String> getInputSuggestion() {
        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.addAll(getCommands());
        suggestions.addAll(getProductName());
        return suggestions;
    }

    private static ArrayList<String> getCommands() {
        return CliSyntax.getAllCliSyntax();
    }

    private static ArrayList<String> getProductName() {
        //return Storage.getProductName();
        return new ArrayList<>();
    }
}
