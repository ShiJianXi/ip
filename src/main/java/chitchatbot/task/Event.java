package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * The event class that handles the event command
 */
public class Event extends Task {
    private LocalDate fromDate;
    private LocalTime fromTime;
    private LocalDate toDate;
    private LocalTime toTime;

    /**
     * Constructs an Event task with the given name, date and time.
     *
     * @param name The description of the event, in String.
     * @param fromDate The starting date of the event, in LocalDate, in the dd/MM/yyyy format.
     * @param fromTime The starting date of the event, in LocalTime, in the hhMM format.
     * @param toDate The ending date of the event, in LocalDate, in the dd/MM/yyyy format.
     * @param toTime The ending time of the event, in LocalTime, in the dd/MM/yyyy format.
     * @see Task
     */
    public Event(String name, LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime) {
        super(name);

        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;

    }

    /**
     * Returns the String to be printed to the user's screen using chat UI.
     * When an exception is catch during execution, an empty String will be returned.
     *
     * @param inputArr The user's input that will be split into a String[]
     * @param storage The storage where the data will be stored at.
     * @return The String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     * @see Storage
     */
    public static String createEvent(String[] inputArr, Storage storage) throws MissingParameterException {
        checkConditionForEventParameters(inputArr);

        try {
            Event newTask = parseUserInputAndCreateNewEvent(inputArr);
            storage.appendToFile(newTask.toString());
            
            return "Got it. I've added this task:\n"
                    + "  " + newTask + "\n"
                    + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.";

        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            return "Incorrect format:\n"
                    + "Please ensure the correct format is used:\n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        }
    }

    private static Event parseUserInputAndCreateNewEvent(String[] inputArr) {
        int fromIndex = Arrays.asList(inputArr).indexOf("/from");
        int toIndex = Arrays.asList(inputArr).indexOf("/to");

        String taskDescription = getTaskDescription(inputArr);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate fromDate = getFromDateFromUserInput(inputArr, fromIndex, dateFormatter);
        LocalDate toDate = getToDateFromUserInput(inputArr, toIndex, dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime fromTime = getFromTimeFromUserInput(inputArr, fromIndex, timeFormatter);
        LocalTime toTime = getToTimeFromUserInput(inputArr, toIndex, timeFormatter);


        Event newTask = new Event(taskDescription, fromDate, fromTime, toDate, toTime);
        return newTask;
    }

    private static void checkConditionForEventParameters(String[] inputArr) throws MissingParameterException {
        if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/from")
                || !Arrays.asList(inputArr).contains("/to")
                || inputArr[1].equals("/from") || inputArr[1].equals("/to")
                || Arrays.asList(inputArr).indexOf("/from") > Arrays.asList(inputArr).indexOf("/to")) {
            throw new MissingParameterException("Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm");
        }
    }

    private static LocalDate getFromDateFromUserInput(String[] inputArr, int fromIndex, DateTimeFormatter dateFormatter) {
        LocalDate fromDate = LocalDate.parse(inputArr[fromIndex + 1], dateFormatter);
        return fromDate;
    }

    private static LocalDate getToDateFromUserInput(String[] inputArr, int toIndex, DateTimeFormatter dateFormatter) {
        LocalDate toDate = LocalDate.parse(inputArr[toIndex + 1], dateFormatter);
        return toDate;
    }

    private static LocalTime getFromTimeFromUserInput(String[] inputArr, int fromIndex, DateTimeFormatter timeFormatter) {
        LocalTime fromTime = LocalTime.parse(inputArr[fromIndex + 2], timeFormatter);
        return fromTime;
    }

    private static LocalTime getToTimeFromUserInput(String[] inputArr, int toIndex, DateTimeFormatter timeFormatter) {
        LocalTime toTime = LocalTime.parse(inputArr[toIndex + 2], timeFormatter);
        return toTime;
    }

    private static String getTaskDescription(String[] inputArr) {
        StringJoiner task = new StringJoiner(" ");
        for (int i = 1; i < inputArr.length; i++) {
            if (inputArr[i].equals("/from")) {
                break;
            }
            task.add(inputArr[i]);
        }
        return task.toString();
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d yyyy");
        return String.format("[E]" + super.toString() + "(from: %s %s to: %s %s)",
                this.fromDate.format(dateFormat), this.fromTime,
                this.toDate.format(dateFormat), this.toTime);
    }
}
