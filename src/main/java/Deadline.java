import java.io.File;
import java.util.Arrays;
import java.util.StringJoiner;

public class Deadline extends Task {

    protected String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    //A method to create a new deadline task
    public static void createDeadline(String[] inputArr, File file) throws MissingParameterException {
        //Check for the various exception due to incorrect format for deadline queries
        //Throw exceptions when necessary
        try {
            if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/by") || inputArr[1].equals("/by")) {
                throw new MissingParameterException("    ERROR: There is missing parameters, " +
                        "please ensure the correct format is used:\n" +
                        "    deadline <Description> /by <Date/Time>\n");
            }

            String task = "";
            int byIndex = 0;
            StringJoiner by = new StringJoiner(" ");
            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/by")) {
                    byIndex = i;
                    break;
                }
                task += inputArr[i];
                task += " ";
            }
            for (int i = byIndex + 1; i < inputArr.length; i++) {
                by.add(inputArr[i]);
            }

            Deadline newTask = new Deadline(task, by.toString());
            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Got it. I've added this task:\n"
                    + ChitChatBot.indentation + "  " + newTask
                    + "\n" + ChitChatBot.indentation + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.\n"));

            ChitChatBot.appendToFile(newTask.toString(), file);
        } catch (MissingParameterException e) {
            System.out.println(ChitChatBot.printChat(e.getMessage()));
        }

    }

    @Override
    public String toString() {
        return String.format("[%s]" + super.toString()
                + "(by: %s)", "D", this.by);
    }
}
