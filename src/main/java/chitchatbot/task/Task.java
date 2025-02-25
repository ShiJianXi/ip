package chitchatbot.task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import chitchatbot.command.Parser;
import chitchatbot.exception.AlreadyMarkedException;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.ui.Ui;

/**
 * The general class for task that contains the general methods for all task
 */
public class Task {
    private static String[] previousInput;
    private static String previouslyDeletedTask = "";
    private static int noOfActivity = 0;
    private String name;
    private boolean isDone;
    private int index;



    /**
     * Constructs a Task with the given name.
     *
     * @param name The description of the task.
     */
    public Task(String name) {

        this.name = name;
        this.isDone = false;
        noOfActivity++;
        this.index = noOfActivity;

    }

    public static int getNoOfActivity() {
        return noOfActivity;
    }

    public static void setNoOfActivity(int value) {
        noOfActivity = value;
    }

    /**
     * Returns a String that will be printed to the user's screen
     * using Chat UI to show the user's the task that is marked as done.
     * <p>
     * Will return an empty String if an exception is catch during execution.
     *
     * @param path     The relative path of the txt file where the data is stored at.
     * @param inputArr The user's input that is split into a String[].
     * @return The String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     */
    public static String markAsDone(Path path, String[] inputArr) throws MissingParameterException {
        if (inputArr.length < 2) {
            throw new MissingParameterException("Missing parameters:\n"
                    + "Please ensure the correct format is used: mark <Task Number>");
        }

        try {
            int index = Integer.parseInt(inputArr[1]) - 1;
            String text = Files.readAllLines(path).get(index);
            char[] charArr = text.toCharArray();
            markAsX(charArr);
            String newString = String.valueOf(charArr);
            writeBackToChatFile(path, newString, index);
            Parser.addPreviousCommand(inputArr);
            return "Nice! I've marked this task as done:\n"
                    + "  " + newString;

        } catch (FileNotFoundException e) {
            return "File not found";
        } catch (IOException e) {
            return "Unable to read file";
        } catch (AlreadyMarkedException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e) {
            return printMarkErrorMsgBasedOnNumOfTask();
        } catch (NumberFormatException e) {
            return "Please enter the ID number of the task that you want to mark.";
        }
    }

    private static String printMarkErrorMsgBasedOnNumOfTask() {
        if (noOfActivity == 0) {
            return "Unable to mark, no task in the list, "
                    + "please add task first";
        } else if (noOfActivity == 1) {
            return "Unable to mark, this task doesn't exist, "
                    + "only 1 task in the list";
        } else {
            return "Unable to mark, this task doesn't exist, "
                    + "please pick a task from 1 to "
                    + Task.getNoOfActivity() + " to mark.";
        }
    }

    /**
     * Undos the mark or unmark command.
     * @param path The path where chat.txt is stored.
     * @param index The task number of the task that is marked or unmarked.
     * @param type Type 1 to undo a mark command and type 2 to undo a unmark command.
     */
    public static void undoMarkUnmark(Path path, int index, int type) {
        try {
            String allText = Files.readAllLines(path).get(index);
            char[] charArr = allText.toCharArray();
            //Type 1 is to undo mark command, type 2 is to undo unmark command
            if (type == 1) {
                charArr[4] = ' ';
            } else {
                charArr[4] = 'X';
            }
            String newString = String.valueOf(charArr);
            writeBackToChatFile(path, newString, index);
        } catch (IOException e) {
            System.out.println("Unable to read file");
        }
    }

    private static void markAsX(char[] charArr) throws AlreadyMarkedException {
        if (charArr[4] == 'X') {
            throw new AlreadyMarkedException("This task is already marked as done!");
        } else {
            charArr[4] = 'X';
        }
    }

    private static void writeBackToChatFile(Path path, String newString, int index) throws IOException {
        List<String> lines = Files.readAllLines(path);

        lines.set(index, newString);

        Files.write(path, lines);
    }

    /**
     * Returns a String that will be printed to the user's screen
     * using Chat UI to show the user's the task that is marked as not done.
     * <p>
     * Will return an empty String if an exception is catch during execution.
     *
     * @param path     The relative path of the txt file where the data is stored at.
     * @param inputArr The user's input that is split into a String[].
     * @return The String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     */
    public static String markAsNotDone(Path path, String[] inputArr) throws MissingParameterException {

        if (inputArr.length != 2) {
            throw new MissingParameterException("Missing parameters:\n"
                    + "Please ensure the correct format is used: "
                    + "unmark <Task Number>");
        }

        try {
            int index = Integer.parseInt(inputArr[1]) - 1;
            String text = Files.readAllLines(path).get(index);
            char[] charArr = text.toCharArray();
            unmarkX(charArr);
            String newString = String.valueOf(charArr);
            writeBackToChatFile(path, newString, index);
            Parser.addPreviousCommand(inputArr);
            return "OK, I've marked this task as not done yet:\n"
                    + "  " + newString;

        } catch (FileNotFoundException e) {
            return "File not found";
        } catch (IOException e) {
            return "Unable to read file";
        } catch (AlreadyMarkedException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e) {
            return printUnmarkErrorMsgBasedOnNumOfTask();
        } catch (NumberFormatException e) {
            return "Please enter the ID number of the task that you want to unmark.";
        }
    }

    private static String printUnmarkErrorMsgBasedOnNumOfTask() {
        if (Task.getNoOfActivity() == 0) {
            return "Unable to unmark, no task in the list, "
                    + "please add and mark task first";
        } else if (Task.getNoOfActivity() == 1) {
            return "Unable to unmark, this task doesn't exist, "
                    + "only 1 task in the list";
        } else {
            return "Unable to unmark, this task doesn't exist, "
                    + "please pick a task from 1 to " + Task.getNoOfActivity() + " to unmark.";
        }
    }

    private static void unmarkX(char[] charArr) throws AlreadyMarkedException {
        if (charArr[4] == ' ') {
            throw new AlreadyMarkedException("This task is not yet marked as done!");
        } else {
            charArr[4] = ' ';
        }
    }


    /**
     * Returns a String that will be printed to the user's screen
     * when the user inputs a delete command.
     * <p>
     * An empty String will be returned if an exception is catch during execution.
     *
     * @param path     The relative path for the text file will the data will be stored at.
     * @param inputArr The user's input that is split into a String[].
     * @return A String that will be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     */
    public static String deleteTask(Path path, String[] inputArr) throws MissingParameterException {
        if (inputArr.length != 2) {
            throw new MissingParameterException("Incorrect format:\n"
                    + "Please ensure the correct format is used: delete <Task number>");
        }
        try {
            int index = Integer.parseInt(inputArr[1]) - 1;
            List<String> lines = Files.readAllLines(path);
            String toRemove = lines.get(index);
            removeTaskFromChatFile(path, lines, index);
            previouslyDeletedTask = toRemove;
            noOfActivity--;
            Parser.addPreviousCommand(inputArr);
            return "Noted. I've removed this task:\n"
                    + "  " + toRemove + "\n"
                    + "Now you have " + Task.getNoOfActivity()
                    + " tasks in the list.";

        } catch (FileNotFoundException e) {
            return "File not found";
        } catch (IOException e) {
            return "Unable to read file";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Incorrect format: \n"
                    + "Please ensure the correct format is used: delete <Task ID number>";
        } catch (IndexOutOfBoundsException e) {
            return "This task doesn't exist:\n"
                    + "You can only delete an existing task";
        } catch (NumberFormatException e) {
            return "Incorrect format:\n"
                    + "Please ensure the ID number of the task is provided";
        }
    }

    public static String getPreviouslyDeletedTask() {
        return previouslyDeletedTask;
    }

    /**
     * Removes a task from the chat file.
     * @param path The path the chat.txt is stored.
     * @param lines The collection of all the text in the chat.txt as a List of String.
     * @param index The line number to be removed.
     * @throws IOException If unable to write to file.
     */
    public static void removeTaskFromChatFile(Path path, List<String> lines, int index) throws IOException {
        lines.remove(index);
        Files.write(path, lines);
    }


    @Override
    public String toString() {
        String string = "[%s] %s";
        return String.format(string, isDone ? "X" : " ", this.name);
    }
}
