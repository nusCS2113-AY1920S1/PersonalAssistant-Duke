package userinterface;

import javafx.scene.text.Text;

/**
 * Class for response table to store and display.
 */
public class DukeResponseView {
    private final String index;
    private final Text response;

    public DukeResponseView(String index, Text response) {
        this.index = index;
        this.response = response;
    }

    public String getIndex() {
        return index;
    }

    public Text getResponse() {
        return response;
    }
}
