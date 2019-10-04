package optix.util;

import optix.commands.shows.AddCommand;
import optix.commands.ByeCommand;
import optix.commands.Command;
import optix.commands.shows.DeleteAllCommand;
import optix.commands.shows.DeleteOneCommand;
import optix.commands.HelpCommand;
import optix.commands.shows.ListCommand;
import optix.commands.shows.ListShowCommand;
import optix.commands.shows.PostponeCommand;
import optix.commands.seats.SellSeatCommand;
import optix.commands.seats.ViewSeatsCommand;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;

public class Parser {
    public static Command parse(String fullCommand) throws OptixException {

        // add exception for null pointer exception. e.g. postpone
        String[] splitStr = fullCommand.trim().split(" ", 2);
      
        if (splitStr.length == 1) {
            switch (splitStr[0].toLowerCase()) {
            case "bye":
                return new ByeCommand();
            case "list":
                return new ListCommand();
            case "help":
                return new HelpCommand();
            default:
                throw new OptixInvalidCommandException();
            }
        } else if (splitStr.length == 2) {

            // There will definitely be exceptions thrown here. Need to stress test and then categorise
            switch (splitStr[0].toLowerCase()) {
            case "sell":
                return parseSellSeats(splitStr[1]);
            case "view":
                return parseViewSeating(splitStr[1]);
            case "postpone":
                return parsePostpone(splitStr[1]);
            case "list":
                return new ListShowCommand(splitStr[1]);
            case "bye":
                return new ByeCommand();
            case "add": // add poto|5/10/2020|2000|20
                return parseAddShow(splitStr[1]);
            case "delete-all": // e.g. delete-all poto|lion king
                return parseDeleteAllOfShow(splitStr[1]);
            case "delete": // e.g. delete 2/10/2019|poto
                return parseDeleteOneOfShow(splitStr[1]);
            case "help":
                return new HelpCommand(splitStr[1].trim());
            default:
                throw new OptixInvalidCommandException();
            }
        } else {
            throw new OptixInvalidCommandException();
        }
    }

    private static Command parsePostpone(String postponeDetails) throws OptixInvalidCommandException {
        String[] splitStr = postponeDetails.trim().split("\\|", 3);

        if (splitStr.length != 3) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String oldDate = splitStr[1].trim();
        String newDate = splitStr[2].trim();

        return new PostponeCommand(showName, oldDate, newDate);
    }

    private static Command parseAddShow(String showDetails) throws OptixInvalidCommandException, NumberFormatException {
        String[] splitStr = showDetails.trim().split("\\|", 4);

        if (splitStr.length != 4) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();
        double showCost = Double.parseDouble(splitStr[2]);
        double seatBasePrice = Double.parseDouble(splitStr[3]);

        return new AddCommand(showName, showDate, showCost, seatBasePrice);
    }

    // delete a single show on a particular date
    private static Command parseDeleteOneOfShow(String showDetails) throws OptixInvalidCommandException {
        String[] splitStr = showDetails.trim().split("\\|");

        if (splitStr.length != 2) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();

        return new DeleteOneCommand(showName, showDate);
    }

    // delete all instances of shows with specified name. Can contain multiple names, separated by pipe.
    private static Command parseDeleteAllOfShow(String deleteDetails) {
        String[] splitStr = deleteDetails.trim().split("\\|");

        return new DeleteAllCommand(splitStr);
    }

    private static Command parseViewSeating(String showDetails) throws OptixInvalidCommandException {
        String[] splitStr = showDetails.trim().split("\\|");

        if (splitStr.length != 2) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();

        return new ViewSeatsCommand(showName, showDate);
    }

    private static Command parseSellSeats(String details) throws OptixInvalidCommandException {
        String[] splitStr = details.trim().split("\\|");

        if (splitStr.length < 3 || splitStr.length > 4) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();
        String buyerName = splitStr[2].trim();

        if(splitStr.length == 4) {
            String seats = splitStr[3].trim();

            return new SellSeatCommand(showName, showDate, buyerName, seats);
        }

        return new SellSeatCommand(showName, showDate, buyerName);

    }
}
