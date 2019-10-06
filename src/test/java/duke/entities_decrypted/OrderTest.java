//package duke.entities;
//
//import duke.model.order.Order;
//import duke.logic.parser.commons.TimeParser;
//import duke.logic.parser.exceptions.ParseException;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class OrderTest {
//
//    @Test
//    private void createOrder_withRemarksAndOneItem_success() {
//        try {
//            Order order = new Order("jj",
//                    "12345678",
//                    TimeParser.convertStringToDate("10/10/2019 18:00"));
//            order.setRemarks("no nuts");
//            order.addItem("cake", 1);
//
//            assertEquals("no nuts", order.getRemarks());
//            assertEquals("jj", order.getCustomerName());
//            assertEquals(1, order.getItems().size());
//        } catch (ParseException e) {
//            fail();
//        }
//    }
//
//    @Test
//    private void createOrder_invalidDate_dukeException() {
//        try {
//            Order order = new Order("jj",
//                    "12345678",
//                    TimeParser.convertStringToDate("123"));
//
//            fail();
//        } catch (ParseException e) {
//            assertEquals("Please enter date in correct format: dd/mm/yyyy hhmm. e.g. 18/12/1999 18:00.", e.getMessage());
//        }
//    }
//
//}
