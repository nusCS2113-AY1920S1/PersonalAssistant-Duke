package javafx;

import javafx.beans.property.SimpleStringProperty;

/**
 * javafx class to display choice of degrees in a tabular format in the GUI.
 * Each ChoicesFX object is a degree containing only its id and degree name.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.3
 */
public class ChoicesFX {
    private final SimpleStringProperty degree = new SimpleStringProperty("");
    private final SimpleStringProperty number = new SimpleStringProperty("");

    /**
     * Empty constructor.
     */
    public ChoicesFX() {
        this("", "");
    }

    /**
     * Constructor for this ChoicesFX object.
     *
     * @param number The numerical ID of the degree.
     * @param degree The name of the degree.
     */
    public ChoicesFX(String number, String degree) {
        setNumber(number);
        setDegree(degree);
    }

    /**
     * Returns the name of the degree.
     *
     * @return the name of the degree as a String.
     */
    public String getDegree() {
        return degree.get();
    }

    /**
     * Sets the name of the degree in this ChoicesFX object.
     *
     * @param input The name of the degree to be used in this ChoicesFX object.
     */
    public void setDegree(String input) {
        degree.set(input);
    }

    /**
     * Returns the numerical ID of the Choice.
     * Required to be used by tableview.
     *
     * @return The numerical ID of this ChoicesFX as a string.
     */
    public String getNumber() {
        return number.get();
    }

    /**
     * Sets the numerical ID of this ChoicesFX object
     *
     * @param input The numerical ID of this ChoicesFX object as a string, used in the constructor
     */
    public void setNumber(String input) {
        number.set(input);
    }


}
