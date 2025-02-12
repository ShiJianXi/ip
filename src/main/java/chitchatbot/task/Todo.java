package chitchatbot.task;

import java.util.StringJoiner;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

/**
 * The class that deals with the todo command
 */
public class Todo extends Task {
    private String status = "T";
    /**
     * Constructs a Todo test with the given name.
     *
     * @param name The description of the Task
     * @see Task
     */
    public Todo(String name) {
        super(name);
    }



    /**
     * Returns the String to be printed to the user's screen
     * when the user inputs a Todo command.
     * <p>
     * An empty String will be returned if an exception is catch during execution.
     *
     * @param inputArr The user's input that will be split into a String[].
     * @param storage  The storage that handles the txt file that stores the user's activities.
     * @return A String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     * @see Storage
     */
    public static String createToDo(String[] inputArr, Storage storage) throws MissingParameterException {
        if (inputArr.length < 2) {
            throw new MissingParameterException("Incorrect format:\n"
                    + "Please ensure the correct format is used: todo <Description>");
        }

        String taskDescription = getDescription(inputArr);
        Todo newTask = new Todo(taskDescription);
        storage.appendToFile(newTask.toString());

        return "Got it. I've added this task:\n"
                + "  " + newTask + "\n"
                + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.";

    }

    private static String getDescription(String[] inputArr) {
        StringJoiner task = new StringJoiner(" ");
        for (int i = 1; i < inputArr.length; i++) {
            if (inputArr[i].equals("/by")) {
                break;
            }
            task.add(inputArr[i]);
        }
        return task.toString();
    }

    @Override
    public String toString() {
        String string = String.format("[%s]%s", status, super.toString());
        return string;
    }
}

