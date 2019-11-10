package eggventory.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Raghav-B
/**
 * Tests if input predictor returns correct predictions for incomplete
 * inputs.
 */
public class InputPredictorTest {
    private InputPredictor inputPredictor = new InputPredictor();

    @Test
    public void testGetPrediction_Command_Succeeds() {
        String incompleteInput = "ad";
        assertEquals("add stock",
                incompleteInput + inputPredictor.getPrediction(incompleteInput, 0));

        incompleteInput = "b";
        assertEquals("bye",
                incompleteInput + inputPredictor.getPrediction(incompleteInput, 0));

        incompleteInput = "add stockt";
        assertEquals("add stocktype",
                incompleteInput + inputPredictor.getPrediction(incompleteInput, 0));
    }

    @Test
    public void testGetPrediction_Command_Fails() {
        String incompleteInput = "udfvjh";
        String remainString = inputPredictor.getPrediction(incompleteInput, 0);

        assertEquals("", remainString);
    }

    @Test
    void testGetPrediction_Argument_Succeeds() {
        String incompleteInput = "add stock";
        assertEquals(" <StockType> <StockCode> <Quantity> <Description> [Optional: -m <Minimum quantity>]",
                inputPredictor.getPrediction(incompleteInput, 0));

        incompleteInput += " ";
        System.out.println(inputPredictor.getPrediction(incompleteInput, 0));
        assertEquals("<StockType> <StockCode> <Quantity> <Description> [Optional: -m <Minimum quantity>] ",
                inputPredictor.getPrediction(incompleteInput, 0));

        incompleteInput += "Arduino 398SH ";
        assertEquals("<Quantity> <Description> [Optional: -m <Minimum quantity>] ",
                inputPredictor.getPrediction(incompleteInput, 0));

        inputPredictor.reset();
        incompleteInput = "edit stock";
        assertEquals(" <StockCode> <Property> <NewValue>",
                inputPredictor.getPrediction(incompleteInput, 0));

        incompleteInput += " Arduino";
        assertEquals(" <Property> <NewValue> ",
                inputPredictor.getPrediction(incompleteInput, 0));

        incompleteInput += " StockCode SH983";
        assertEquals(" ",
                inputPredictor.getPrediction(incompleteInput, 0));
    }
}
//@@author
