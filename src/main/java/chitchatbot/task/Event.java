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
        if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/from")
                || !Arrays.asList(inputArr).contains("/to")
                || inputArr[1].equals("/from") || inputArr[1].equals("/to")
                || Arrays.asList(inputArr).indexOf("/from") > Arrays.asList(inputArr).indexOf("/to")) {
            throw new MissingParameterException("Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm");
        }
        try {
            StringJoiner task = new StringJoiner(" ");
            int fromIndex = Arrays.asList(inputArr).indexOf("/from");
            int toIndex = Arrays.asList(inputArr).indexOf("/to");


            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/from")) {
                    break;
                }
                task.add(inputArr[i]);
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            LocalDate fromDate = LocalDate.parse(inputArr[fromIndex + 1], dateFormatter);
            LocalTime fromTime = LocalTime.parse(inputArr[fromIndex + 2], timeFormatter);
            LocalDate toDate = LocalDate.parse(inputArr[toIndex + 1], dateFormatter);
            LocalTime toTime = LocalTime.parse(inputArr[toIndex + 2], timeFormatter);


            Event newTask = new Event(task.toString(), fromDate, fromTime, toDate, toTime);

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

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d yyyy");
        return String.format("[E]" + super.toString() + "(from: %s %s to: %s %s)",
                this.fromDate.format(dateFormat), this.fromTime,
                this.toDate.format(dateFormat), this.toTime);
    }
}
