import java.io.File;
import java.util.Arrays;
import java.util.StringJoiner;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
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
                        "event <Description> /from <Date/Time> /to <Date/Time>\n");
            }
            String task = "";
            int fromIndex = 0;
            int toIndex = 0;
            StringJoiner from = new StringJoiner(" ");
            StringJoiner to = new StringJoiner(" ");
            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/from")) {
                    fromIndex = i;
                    break;
                }

                task += inputArr[i];
                task += " ";
            }
            for (int i = fromIndex + 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/to")) {
                    toIndex = i;
                    break;
                }
                from.add(inputArr[i]);
            }
            for (int i = toIndex + 1; i < inputArr.length; i++) {
                to.add(inputArr[i]);
            }

            Event newTask = new Event(task, from.toString(), to.toString());
            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Got it. I've added this task:\n"
                    + ChitChatBot.indentation + "  " + newTask + "\n"
                    + ChitChatBot.indentation + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.\n"));
            ChitChatBot.appendToFile(newTask.toString(), file);
        } catch (MissingParameterException e) {
            System.out.println(ChitChatBot.printChat(e.getMessage()));
        }
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + "(from: %s to: %s)", from, to);
    }
}
