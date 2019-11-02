package duke.common;

public class BookingMessages {
    public static final String COMMAND_LIST_BOOKINGS = "listallbookings";
    public static final String COMMAND_ADD_BOOKING = "addbooking";
    public static final String COMMAND_DELETE_BOOKING = "deletebooking";
    public static final String COMMAND_VIEW_BOOKING_SCHEDULE = "viewbookingschedule";
    public static final String COMMAND_FIND_BOOKING = "findbooking";
    public static final String COMMAND_VIEW_ORDERS = "vieworders";


    public static final String MESSAGE_BOOKING_ADDED = "New booking added:\n";
    public static final String MESSAGE_ALL_CURRENT_BOOKINGS = "     Here are all the current bookings in your list:";
    public static final String MESSAGE_EMPTY_BOOKING_LIST = "Booking list is empty.";
    public static final String MESSAGE_BOOKING_REMOVED = "     Noted. I've removed this booking:\n";
    public static final String MESSAGE_MATCHING_BOOKINGS = "     Here are the matching bookings in your list:";
    public static final String MESSAGE_NO_BOOKING = "      No booking on ";
    public static final String MESSAGE_PROMPT_ADDBOOKING = ". \n      You may proceed with command: addbooking";
    public static final String MESSAGE_BOOKING_ON = "      Here is your booking on ";
    public static final String MESSAGE_ORDERS_FOR = "     Here are your orders for: ";
    public static final String MESSAGE_NO_EXISTING_BOOKING = "      Sorry, no existing booking.";
    public static final String MESSAGE_NO_BOOKING_FOR = "      Sorry, no booking for ";
    public static final String MESSAGE_NO_ORDER_FOR = "      Sorry, no order for ";
    public static final String MESSAGE_IS_FOUND = " is found.";


    public static final String ERROR_MESSAGE_EMPTY_BOOKING_DETAILS = "Booking details cannot be empty!\n" +
            "       Please enter in the following format:\n" +
            "       addbooking <customer_name e.g.John_Lim> <customer_contact> <number_of_pax> <booking_date e.g dd/MM/yyyy> orders/ <order_name_1>, <order_name_2>...";

    public static final String ERROR_MESSAGE_INVALID_BOOKING_DETAILS = "Incorrect Booking details.\n" +
            "       Please enter in the following format:\n" +
            "       addbooking <customer_name e.g.John_Lim> <customer_contact> <number_of_pax> <booking_date e.g dd/MM/yyyy> orders/ <order_name_1>, <order_name_2>...";

    public static final String ERROR_MESSAGE_INVALID_NAME = "Invalid Name entered.\n Customer name can only contain alphabets with '_' e.g. John_Lim";

    public static final String ERROR_MESSAGE_INVALID_CONTACT_NO = "Invalid Contact number. \n Contact number can only contain digits!";

    public static final String ERROR_MESSAGE_INVALID_PAX = "In valid pax. \n Number of Pax must be between 1-8.";

    public static final String ERROR_MESSAGE_NO_ORDERS = "No orders input! \n Please enter addbooking command again with orders/ <order_name_1>, <order_name_2>...";

    public static final String ERROR_MESSAGE_INVALID_DATE = "Invalid booking date entered.\n Please enter date again in this format: dd/MM/yyyy";

    public static final String ERROR_MESSAGE_UNAVAILABLE_DATE = "Date entered is unavailable due to existing booking:(\n Please enter another date!";

    public static final String ERROR_MESSAGE_EMPTY_BOOKING_INDEX = "Booking index cannot be empty!\n" +
            "       Please enter in the following format:\n" +
            "       deletebooking <booking_index>";

    public static final String ERROR_MESSAGE_INVALID_DELETE_COMMAND = "Incorrect delete booking command.\n " +
            "       Please enter in the following format:\n" +
            "       deletebooking <booking_index>";

    public static final String ERROR_MESSAGE_EMPTY_NAME_FIND = "Customer name cannot be empty!\n" +
            "       Please enter in the following format:\n" +
            "       findbooking <customer_name>";

    public static final String ERROR_MESSAGE_EMPTY_NAME_VIEW = "Customer name cannot be empty!\n" +
            "       Please enter in the following format:\n" +
            "       vieworders <customer_name>";

    public static final String ERROR_MESSAGE_INVALID_FIND_COMMAND = "Incorrect find booking command.\n " +
            "       Please enter in the following format:\n" +
            "       findbooking <customer_name>";

    public static final String ERROR_MESSAGE_EMPTY_DATE = "Booking date cannot be empty!\n" +
            "       Please enter in the following format:\n" +
            "       viewbookingschedule <date: dd/MM/yyyy>";

    public static final String ERROR_MESSAGE_INVALID_VIEWBOOKINGSCHEDULE_COMMAND = "Incorrect view booking schedule command.\n " +
            "       Please enter in the following format:\n" +
            "       viewbookingschedule <date: dd/MM/yyyy>";

    public static final String ERROR_MESSAGE_INVALID_VIEWORDERS_COMMAND = "Incorrect view orders command.\n " +
            "       Please enter in the following format:\n" +
            "       vieworders <customer_name>";


}
