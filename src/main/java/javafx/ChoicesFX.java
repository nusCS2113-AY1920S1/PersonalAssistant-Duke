package javafx;

import javafx.beans.property.SimpleStringProperty;

public class ChoicesFX {
    private final SimpleStringProperty degree = new SimpleStringProperty("");
    private final SimpleStringProperty number = new SimpleStringProperty("");

    public ChoicesFX() {
        this("", "");
    }

    public ChoicesFX(String number, String degree) {
        setNumber(number);
        setDegree(degree);
    }

    public String getDegree() {
        return degree.get();
    }

    public void setDegree(String input) {
        degree.set(input);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String input) {
        number.set(input);
    }


}
