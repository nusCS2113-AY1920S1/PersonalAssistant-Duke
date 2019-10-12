package duke.ui;

import com.jfoenix.controls.JFXTextField;
import duke.storage.InputSuggestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 * @author Caleb Brinkman
 */
public class AutoCompleteTextField extends JFXTextField
{
    /** The existing autocomplete entries. */
    private final SortedSet<String> entries;
    /** The popup used to select an entry. */
    private ContextMenu entriesPopup;

    private Node anchorNode = AutoCompleteTextField.this;
    List<CustomMenuItem> menuItems = new LinkedList<>();
    LinkedList<String> searchResult = new LinkedList<>();



    private final int VERTICAL_OFF_SET = -30;
    private final int VERTICAL_INITIAL = -50;
    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField() {
        super();
        entries = new TreeSet<>();
        entries.addAll(InputSuggestion.getInputSuggestion());

        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getCurrWord().length() == 0)
                {
                    entriesPopup.hide();
                } else
                {
                    searchResult.clear();
                    searchResult.addAll(entries.subSet(getCurrWord(), getCurrWord() + Character.MAX_VALUE));
                    if (entries.size() > 0)
                    {
                        populatePopup(searchResult);
                        double dy = searchResult.size() * VERTICAL_OFF_SET + VERTICAL_INITIAL;
                        {
                            entriesPopup.show(anchorNode, Side.BOTTOM, 0, dy);
                        }
                    } else
                    {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });
    }

    //public void setAnchor(Node node) {
    //    anchorNode = node;
    //}
/*    private String getCurrWord() {
       // String command = getText(); .*[\\s|]*\\w+;
        getFrontWords();
        Pattern pattern = Pattern.compile("((?:\\W*)(?<lastWord>\\w+)(?<ignore>\\W*))+");
        Matcher matcher = pattern.matcher(getText());
        if (matcher.matches()) {
            if (matcher.group("ignore").length() != 0) {
                return "";
            }
            return matcher.group("lastWord");
        }
        return "";
    }*/

    public String getCurrWord() {
        Pattern pattern = Pattern.compile("(?<front>(.*)(\\W+))*(?<lastWord>\\w+)$");
        Matcher matcher = pattern.matcher(getText());
        if (matcher.matches()) {
            return matcher.group("lastWord");
        }
        return "";
    }

    public String getFrontWords() {
        Pattern pattern = Pattern.compile("(?<front>(.*)(\\W+))*(?<lastWord>\\w+)$");
        Matcher matcher = pattern.matcher(getText());
        if (matcher.matches()) {
            if (matcher.group("front") != null) {
                return matcher.group("front");
            }
        }
        return "";
    }

    /**
     * Get the existing set of autocomplete entries.
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() { return entries; }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        menuItems.clear();
        // If you'd like more entries, modify this line.
        int maxEntries = 5;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = count - 1; i >= 0; i--)
        {
            final String result = searchResult.get(i);
            Label entryLabel;
                entryLabel = new Label(getFrontWords() + result);
                entryLabel.setAlignment(Pos.CENTER_RIGHT);
                firstSuggestion = entryLabel.getText();
           // }
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                   // setText(getFrontWords() + result);
                    setText(entryLabel.getText());
                    entriesPopup.hide();
                    AutoCompleteTextField.this.positionCaret(getText().length());
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    public String firstSuggestion = "";

    public String getFirstSuggestion() {
        //if(menuItems.size() != 0) {
        //    return menuItems.get(0).getText();
        //}
        //return "";
        return firstSuggestion;
    }

    public boolean hasSuggestion() {
        Pattern pattern = Pattern.compile(".*(?<ignore>\\W+)");
        Matcher matcher = pattern.matcher(getText());
        if (matcher.matches() && matcher.group("ignore") != null) {
            return false;
        }
        return searchResult.size() != 0;
    }
}

///////
//https://gist.github.com/floralvikings/10290131
