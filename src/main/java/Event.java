import java.io.File;
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

    public Event(String name, String fromDate, String fromTime, String toDate, String toTime) {
        super(name);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        this.fromDate = LocalDate.parse(fromDate, dateFormatter);
        this.fromTime = LocalTime.parse(fromTime, timeFormatter);
        this.toDate = LocalDate.parse(toDate, dateFormatter);
        this.toTime = LocalTime.parse(toTime, timeFormatter);

    }

    //A method to create an event task
    public static void createEvent(String[] inputArr, File file) {
        //Check for the various exceptions and  throw an exception when required
        try {
            if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/from")
                    || !Arrays.asList(inputArr).contains("/to")
                    || inputArr[1].equals("/from") || inputArr[1].equals("/to")
                    || Arrays.asList(inputArr).indexOf("/from") > Arrays.asList(inputArr).indexOf("/to")) {
                throw new MissingParameterException("    ERROR: Missing parameters\n" +
                        "    Please ensure the correct format is used: " +
                        "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n");
            }

            String task = "";
            int fromIndex = Arrays.asList(inputArr).indexOf("/from");
            int toIndex = Arrays.asList(inputArr).indexOf("/to");

            String fromDate = "";
            String fromTime = "";
            String toDate = "";
            String toTime = "";
            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/from")) {
                    break;
                }

                task += inputArr[i];
                task += " ";
            }

            fromDate = inputArr[fromIndex + 1];
            fromTime = inputArr[fromIndex + 2];
            toDate = inputArr[toIndex + 1];
            toTime = inputArr[toIndex + 2];

            Event newTask = new Event(task, fromDate, fromTime, toDate, toTime);

            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Got it. I've added this task:\n"
                    + ChitChatBot.indentation + "  " + newTask + "\n"
                    + ChitChatBot.indentation + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.\n"));
            ChitChatBot.appendToFile(newTask.toString(), file);
        } catch (MissingParameterException e) {
            System.out.println(ChitChatBot.printChat(e.getMessage()));
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            System.out.println(ChitChatBot.printChat("    ERROR: Incorrect format\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n"));
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
