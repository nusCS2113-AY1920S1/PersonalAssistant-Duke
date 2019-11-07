package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.message.ProductMessageUtils;
import duke.testutil.ProductDescriptorBuilder;
import org.junit.jupiter.api.Test;

import static duke.logic.command.product.ProductCommandTestUtil.VALID_NAME;
import static duke.logic.parser.CommandParserTestUtil.assertParseFailure;
import static duke.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static duke.testutil.TypicalProducts.CHEESE_CAKE;

public class AddProductCommandParserTest {
    private AddProductCommandParser parser = new AddProductCommandParser();

    private static final String VALID_CHEESE_CAKE_USER_INPUT = "product add -name Cheese cake -cost 3.0 "
            + "-price 5.9 -ingt [Cream cheese, 3.0]";
    private static final String VALID_NAME_ONLY_USER_INPUT = "product add -name " + VALID_NAME;
    private static final String INVALID_MISSING_NAME_PARAM_USER_INPUT = "product add " + VALID_NAME;
    private static final String INVALID_NAME_EMPTY_USER_INPUT = "product add -name ";

    @Test
    public void parse_validCommand_success() {
        //all fields present and valid
        assertParseSuccess(parser, VALID_CHEESE_CAKE_USER_INPUT,
                new AddProductCommand(new ProductDescriptorBuilder(CHEESE_CAKE).build()));
        //only name field present
        assertParseSuccess(parser, VALID_NAME_ONLY_USER_INPUT,
                new AddProductCommand(new ProductDescriptorBuilder().withName(VALID_NAME).build()));
    }

    @Test
    public void parse_nameInvalid_failure() {
        String expectedMessage = ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME;

        assertParseFailure(parser, INVALID_NAME_EMPTY_USER_INPUT, expectedMessage);
        assertParseFailure(parser, INVALID_MISSING_NAME_PARAM_USER_INPUT, expectedMessage);
    }


}
