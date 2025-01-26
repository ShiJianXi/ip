import java.io.File;
import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.StringJoiner;
import java.time.LocalDate;

public class Deadline extends Task {

    protected LocalDate by;
    protected LocalTime time;
    private boolean containTime = false;

    public Deadline(String name, String by) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        this.by = LocalDate.parse(by, formatter);
    }

    public Deadline(String name, String by, String time) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        this.by = LocalDate.parse(by, formatter);
        this.time = LocalTime.parse(time, timeFormatter);
        this.containTime = true;
    }

    //A method to create a new deadline task
    public static void createDeadline(String[] inputArr, File file) {
        //Check for the various exception due to incorrect format for deadline queries
        //Throw exceptions when necessary
        try {
            if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/by") || inputArr[1].equals("/by")
                    || Arrays.asList(inputArr).indexOf("/by") == inputArr.length - 1) {

                throw new MissingParameterException("    ERROR: There is missing parameters, " +
                        "please ensure the correct format is used:\n" +
                        "    deadline <Description> /by dd/mm/yyyy\n" +
                        "    OR deadline <Description /by dd/mm/yyyy HHmm\n");
            }

            String task = "";
            int byIndex = 0;
            String date = "";
            String time = "";
            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/by")) {
                    byIndex = i;
                    break;
                }
                task += inputArr[i];
                task += " ";
            }

            if (inputArr.length > byIndex + 2) { //Contains time
                date = inputArr[byIndex + 1];
                time = inputArr[byIndex + 2];
                Deadline newTask = new Deadline(task, date, time);
                System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Got it. I've added this task:\n"
                        + ChitChatBot.indentation + "  " + newTask
                        + "\n" + ChitChatBot.indentation + "Now you have "
                        + Task.getNoOfActivity() + " tasks in the list.\n"));
                ChitChatBot.appendToFile(newTask.toString(), file);
            } else {
                date = inputArr[byIndex + 1];
                Deadline newTask = new Deadline(task, date);
                System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Got it. I've added this task:\n"
                        + ChitChatBot.indentation + "  " + newTask
                        + "\n" + ChitChatBot.indentation + "Now you have "
                        + Task.getNoOfActivity() + " tasks in the list.\n"));
                ChitChatBot.appendToFile(newTask.toString(), file);
            }

        } catch (MissingParameterException e) {
            System.out.println(ChitChatBot.printChat(e.getMessage()));
        } catch (DateTimeException e1) {
            System.out.println(ChitChatBot.printChat("    ERROR: Incorrect format, " +
                    "please ensure the correct format is used:\n" +
                    "    deadline <Description> /by dd/mm/yyyy\n" +
                    "    OR deadline <Description /by dd/mm/yyyy HHmm\n"));
        }
    }

    @Override
    public String toString() {
        LocalDate date = this.by;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        if (this.containTime) {
            return String.format("[%s]" + super.toString()
                    + "(by: %s %s)", "D", date.format(formatter), this.time);
        } else {
            return String.format("[%s]" + super.toString()
                    + "(by: %s)", "D", date.format(formatter));
        }
    }
}
