package javafx;

import javafx.beans.property.SimpleStringProperty;

public class DegreesFX {
    private final SimpleStringProperty degree = new SimpleStringProperty("");

    public DegreesFX() {
        this("");
    }

    public DegreesFX(String degree) {
        setDegree(degree);
    }

    public String getDegree() {
        return degree.get();
    }

    public void setDegree(String input) {
        degree.set(input);
    }



}
