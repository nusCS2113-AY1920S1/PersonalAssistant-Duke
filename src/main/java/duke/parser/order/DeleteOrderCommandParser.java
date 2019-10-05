//package duke.parser.order;
//
//import duke.logic.command.order.DeleteOrderCommand;
//import duke.parser.*;
//import duke.parser.exceptions.ParseException;
//
//
//public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {
//    @Override
//    public DeleteOrderCommand parse(String args) throws ParseException {
//        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
//                CliSyntax.PREFIX_ORDER_INDEX
//        );
//        return new DeleteOrderCommand(ParserUtil.getIndexes(map.getPreamble()));
//    }
//
//}
