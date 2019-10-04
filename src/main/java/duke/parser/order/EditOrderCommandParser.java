//package duke.parser.order;
//
//import duke.logic.command.order.EditOrderCommand;
//import duke.parser.ArgumentMultimap;
//import duke.parser.ArgumentTokenizer;
//import duke.parser.CliSyntax;
//import duke.parser.Parser;
//import duke.parser.exceptions.ParseException;
//
//import java.util.Optional;
//
//
//public class EditOrderCommandParser implements Parser<EditOrderCommand> {
//    @Override
//    public EditOrderCommand parse(String args) throws ParseException {
//        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
//                CliSyntax.PREFIX_ORDER_INDEX,
//                CliSyntax.PREFIX_ORDER_NAME,
//                CliSyntax.PREFIX_ORDER_CONTACT,
//                CliSyntax.PREFIX_ORDER_ITEM,
//                CliSyntax.PREFIX_ORDER_DEADLINE,
//                CliSyntax.PREFIX_ORDER_STATUS,
//                CliSyntax.PREFIX_ORDER_REMARKS
//        );
//        return new EditOrderCommand();
//    }
//
//    public static class EditOrderMask {
//        private Optional<String> name = Optional.empty();
//        private Optional<String> contact = Optional.empty();
//        private Optional<String> remarks = Optional.empty();
//
//        public Optional<String> getName() {
//            return name;
//        }
//
//        public Optional<String> getContact() {
//            return contact;
//        }
//
//        public Optional<String> getRemarks() {
//            return remarks;
//        }
//
//        public void setName(Optional<String> name) {
//            this.name = name;
//        }
//
//        public void setContact(Optional<String> contact) {
//            this.contact = contact;
//        }
//
//        public void setRemarks(Optional<String> remarks) {
//            this.remarks = remarks;
//        }
//    }
//}
