package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;


public class Event extends Task {
    private LocalDate fromDate;
    private LocalTime fromTime;
    private LocalDate toDate;
    private LocalTime toTime;

    public Event(String name, LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime) {
        super(name);

        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;

    }

    //A method to create an event task
    public static String createEvent(String[] inputArr, Storage storage) throws MissingParameterException {
        //Check for the various exceptions and  throw an exception when required
        if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/from")
                || !Arrays.asList(inputArr).contains("/to")
                || inputArr[1].equals("/from") || inputArr[1].equals("/to")
                || Arrays.asList(inputArr).indexOf("/from") > Arrays.asList(inputArr).indexOf("/to")) {
            throw new MissingParameterException("    Missing parameters error: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n");
        }
        String result = "";
        try {

            String task = "";
            int fromIndex = Arrays.asList(inputArr).indexOf("/from");
            int toIndex = Arrays.asList(inputArr).indexOf("/to");


            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/from")) {
                    break;
                }

                task += inputArr[i];
                task += " ";
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            LocalDate fromDate = LocalDate.parse(inputArr[fromIndex + 1], dateFormatter);
            LocalTime fromTime = LocalTime.parse(inputArr[fromIndex + 2], timeFormatter);
            LocalDate toDate = LocalDate.parse(inputArr[toIndex + 1], dateFormatter);
            LocalTime toTime = LocalTime.parse(inputArr[toIndex + 2], timeFormatter);


            Event newTask = new Event(task, fromDate, fromTime, toDate, toTime);

            storage.appendToFile(newTask.toString());
            result = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                    + Ui.indentation + "  " + newTask + "\n"
                    + Ui.indentation + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.\n");
            return result;

        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            System.out.println(Ui.printChat("    Wrong format error: Incorrect format\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n"));
        }
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d yyyy");
        return String.format("[E]" + super.toString() + "(from: %s %s to: %s %s)",
                this.fromDate.format(dateFormat), this.fromTime,
                this.toDate.format(dateFormat), this.toTime);
    }
}
