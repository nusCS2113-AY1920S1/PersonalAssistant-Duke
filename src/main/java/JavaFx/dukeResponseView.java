package JavaFx;

import javafx.scene.text.Text;

public class dukeResponseView {
    String index;
    Text response;

    public dukeResponseView(String index, Text response) {
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
