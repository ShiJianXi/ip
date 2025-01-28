package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

public class Todo extends Task {

    /**
     * Constructs a Todo test with the given name.
     *
     * @param name The description of the Task
     * @see Task
     */
    public Todo(String name) {
        super(name);
    }

    String status = "T";

    /**
     * Returns the String to be printed to the user's screen
     * when the user inputs a Todo command.
     * <p>
     * An empty String will be returned if an exception is catch during execution.
     *
     * @param inputArr The user's input that will be split into a String[].
     * @param storage The storage that handles the txt file that stores the user's activities.
     * @return A String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     * @see Storage
     */
    public static String createToDo(String[] inputArr, Storage storage) throws MissingParameterException {
        //Check if the user missed out the description of the test
        //Throw exception when required
        String result = "";
        if (inputArr.length < 2) {
            throw new MissingParameterException("Missing parameters error: The description of todo cannot be empty\n"
                    + "    Please ensure the correct format is used: todo <Description>\n");
        }

        String task = "";
        for (int i = 1; i < inputArr.length; i++) {
            if (inputArr[i].equals("/by")) {
                break;
            }
            task += inputArr[i];
            task += " ";
        }

        Todo newTask = new Todo(task);

        result = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                + Ui.indentation + "  " + newTask + "\n"
                + Ui.indentation + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.\n");

        storage.appendToFile(newTask.toString());

        return result;

    }

    @Override
    public String toString() {
        String string = String.format("[%s]%s", status, super.toString());
        return string;
    }
}

