package optix.util;

import optix.commands.shows.AddCommand;
import optix.commands.ByeCommand;
import optix.commands.Command;
import optix.commands.shows.DeleteAllCommand;
import optix.commands.shows.DeleteOneCommand;
import optix.commands.HelpCommand;
import optix.commands.shows.EditCommand;
import optix.commands.shows.ListCommand;
import optix.commands.shows.ListShowCommand;
import optix.commands.shows.PostponeCommand;
import optix.commands.seats.SellSeatCommand;
import optix.commands.seats.ViewSeatsCommand;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;

/**
 * Parse input arguments and create a new Command Object
 */
public class Parser {

    /**
     * Parse input argument and create a new Command Object based on the first input word.
     * @param fullCommand The entire input argument.
     * @return Command Object based on the first input word.
     * @throws OptixException if the Command word is not recognised by Optix.
     */
    public static Command parse(String fullCommand) throws OptixException {

        // add exception for null pointer exception. e.g. postpone
        String[] splitStr = fullCommand.trim().split(" ", 2);
      
        if (splitStr.length == 1) {
            switch (splitStr[0].toLowerCase().trim()) {
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
            switch (splitStr[0].toLowerCase().trim()){
            case "edit":
                return parseEditShow(splitStr[1]);
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

    /**
     * Parse the remaining user input to its respective parameters for PostponeCommand.
     * @param postponeDetails The details to create new PostponeCommand Object.
     * @return new PostponeCommand Object.
     * @throws OptixInvalidCommandException if the user input does not have the correct number of parameters.
     */
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

    /**
     * Parse the remaining user input to its respective parameters for AddCommand.
     * @param showDetails The details to create a new AddCommand Object.
     * @return new AddCommand Object.
     * @throws OptixInvalidCommandException if the user input does not have the correct number of parameters.
     * @throws NumberFormatException if user attempt to convert String into double.
     */
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

    /**
     * Parse the remaining user input to its respective parameters for DeleteOneCommand.
     * @param showDetails The details to create a new DeleteOneCommand Object.
     * @return new DeleteOneCommand Object.
     * @throws OptixInvalidCommandException if the user input does not have the correct number of parameters.
     */
    private static Command parseDeleteOneOfShow(String showDetails) throws OptixInvalidCommandException {
        String[] splitStr = showDetails.trim().split("\\|");

        if (splitStr.length != 2) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();

        return new DeleteOneCommand(showName, showDate);
    }

    /**
     * Parse the remaining user input to its respective parameters for DeleteAllCommand.
     * @param deleteDetails The name of all the shows being queried.
     * @return new DeleteAllCommand Object.
     */
    private static Command parseDeleteAllOfShow(String deleteDetails) {
        String[] splitStr = deleteDetails.trim().split("\\|");

        return new DeleteAllCommand(splitStr);
    }

    /**
     * Parse the remaining user input to its respective parameters for ViewSeatsCommand.
     * @param showDetails The details to create a new ViewSeatsCommand Object.
     * @return new ViewSeatsCommand Object.
     * @throws OptixInvalidCommandException if the user input does not have the correct number of parameters.
     */
    private static Command parseViewSeating(String showDetails) throws OptixInvalidCommandException {
        String[] splitStr = showDetails.trim().split("\\|");

        if (splitStr.length != 2) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();

        return new ViewSeatsCommand(showName, showDate);
    }

    /**
     * Parse the remaining user input to its respective parameters for SellSeatsCommand
     * @param details The details to create a new SellSeatsCommand Object.
     * @return new SellSeatsCommand Object.
     * @throws OptixInvalidCommandException if the user input does not have the correct number of parameters.
     */
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

    private static Command parseEditShow(String details) {
        String[] splitStr = details.split("\\|");
        String oldShowName = splitStr[0].trim();
        String showDate = splitStr[1].trim();
        String newShowName = splitStr[2].trim();

        return new EditCommand(oldShowName, showDate, newShowName);
    }
}
