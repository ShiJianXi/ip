package chitchatbot.task;

import chitchatbot.exception.AlreadyMarkedException;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * The general class for task that contains the general methods for all task
 */
public class Task {
    private String name;
    private boolean isDone;
    private int index;
    private static int noOfActivity = 0;

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
     * @param path The relative path of the txt file where the data is stored at.
     * @param inputArr The user's input that is split into a String[].
     * @return The String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     */
    public static String markAsDone(Path path, String[] inputArr) throws MissingParameterException {

        String result = "";
        if (inputArr.length < 2) {
            throw new MissingParameterException(Ui.indentation + "Missing parameters error: Missing parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: mark <Task Number>\n");
        }

        try {
            int index = Integer.parseInt(inputArr[1]) - 1;

            String text = Files.readAllLines(path).get(index);

            char[] charArr = text.toCharArray();
            if (charArr[4] == 'X') {
                throw new AlreadyMarkedException("    Already marked error: This task is already marked as done\n");
            } else {
                charArr[4] = 'X';
            }
            String newString = String.valueOf(charArr);

            List<String> lines = Files.readAllLines(path);

            lines.set(index, newString);

            Files.write(path, lines);

            result = Ui.printChat(Ui.indentation + "Nice! I've marked this task as done:\n"
                    + Ui.indentation + "  " + newString + "\n");
            return result;

        } catch (FileNotFoundException e) {
            //return
            //System.out.println("File error: File not found");
            return "File error: File not found";
        } catch (IOException e) {
            //System.out.println("Input error: Unable to read file");
            return "Input error: Unable to read file";
        } catch (AlreadyMarkedException e) {
            //System.out.println(Ui.printChat(e.getMessage()));
            return Ui.printChat(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            if (noOfActivity == 0) {
//                System.out.println(Ui.printChat(Ui.indentation + "Unable to mark, no task in the list, "
//                        + "please add task first\n"));
                return Ui.printChat(Ui.indentation + "Unable to mark, no task in the list, "
                        + "please add task first\n");
            } else if (noOfActivity == 1) {
//                System.out.println(Ui.printChat(Ui.indentation + "Unable to mark, this task doesn't exist, "
//                        + "only 1 task in the list\n"));
                return Ui.printChat(Ui.indentation + "Unable to mark, this task doesn't exist, "
                        + "only 1 task in the list\n");
            } else {
//                System.out.println(Ui.printChat(Ui.indentation + "Unable to mark, this task doesn't exist, "
//                        + "please pick a task from 1 to "
//                        + Task.getNoOfActivity() + " to mark.\n"));
                return Ui.printChat(Ui.indentation + "Unable to mark, this task doesn't exist, "
                        + "please pick a task from 1 to "
                        + Task.getNoOfActivity() + " to mark.\n");
            }
        } catch (NumberFormatException e) {
            return Ui.printChat(Ui.indentation + "Number Format error: "
                    + "Please enter the number of the task that you want to mark\n");
//            System.out.println(Ui.printChat(Ui.indentation + "Number Format error: "
//                    + "Please enter the number of the task that you want to mark\n"));
        }
        //return result;
    }

    /**
     * Returns a String that will be printed to the user's screen
     * using Chat UI to show the user's the task that is marked as not done.
     * <p>
     * Will return an empty String if an exception is catch during execution.
     *
     * @param path The relative path of the txt file where the data is stored at.
     * @param inputArr The user's input that is split into a String[].
     * @return The String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     */
    public static String markAsNotDone(Path path, String[] inputArr) throws MissingParameterException {

        if (inputArr.length != 2) {
            throw new MissingParameterException(Ui.indentation + "Missing parameters error: Missing parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: "
                    + "unmark <Task Number>\n");
        }

        String result = "";
        try {

            int index = Integer.parseInt(inputArr[1]) - 1;

            String text = Files.readAllLines(path).get(index);

            char[] charArr = text.toCharArray();
            if (charArr[4] == ' ') {
                throw new AlreadyMarkedException("    Not yet marked error: This task is not yet marked as done\n");
            } else {
                charArr[4] = ' ';
            }
            String newString = String.valueOf(charArr);

            List<String> lines = Files.readAllLines(path);

            lines.set(index, newString);

            Files.write(path, lines);

            result = Ui.printChat(Ui.indentation + "OK, I've marked this task as not done yet:\n"
                    + Ui.indentation + "  " + newString + "\n");
            return result;

        } catch (FileNotFoundException e) {
            //System.out.println("File error: File not found");
            return "File error: File not found";
        } catch (IOException e) {
            //System.out.println("Input error: Unable to read file");
            return "Input error: Unable to read file";
        } catch (AlreadyMarkedException e) {
            //System.out.println(Ui.printChat(e.getMessage()));
            return Ui.printChat(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            if (Task.getNoOfActivity() == 0) {
//                System.out.println(Ui.printChat(Ui.indentation + "Unable to unmark, no task in the list, "
//                        + "please add and mark task first\n"));
                return Ui.printChat(Ui.indentation + "Unable to unmark, no task in the list, "
                        + "please add and mark task first\n");
            } else if (Task.getNoOfActivity() == 1) {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to unmark, This task doesn't exist, "
                        + "only 1 task in the list\n"));
                return Ui.printChat(Ui.indentation + "Unable to unmark, This task doesn't exist, "
                        + "only 1 task in the list\n");
            } else {
//                System.out.println(Ui.printChat(Ui.indentation + "Unable to unmark, This task doesn't exist, "
//                        + "please pick a task from 1 to " + Task.getNoOfActivity() + " to unmark.\n"));
                return Ui.printChat(Ui.indentation + "Unable to unmark, This task doesn't exist, "
                        + "please pick a task from 1 to " + Task.getNoOfActivity() + " to unmark.\n");
            }
        } catch (NumberFormatException e) {
//            System.out.println(Ui.printChat(Ui.indentation + "Number format error: "
//                    + "Please enter the number of the task that you want to unmark\n"));
            return Ui.printChat(Ui.indentation + "Number format error: "
                    + "Please enter the number of the task that you want to unmark\n");
        }
        //return result;
    }


    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    public int getIndex() {
        return this.index;
    }

    /**
     * Returns a String that will be printed to the user's screen
     * when the user inputs a delete command.
     * <p>
     * An empty String will be returned if an exception is catch during execution.
     *
     * @param path The relative path for the text file will the data will be stored at.
     * @param inputArr The user's input that is split into a String[].
     * @return A String that will be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters.
     * @see Ui
     */
    public static String deleteTask(Path path, String[] inputArr) throws MissingParameterException {
        if (inputArr.length != 2) {
            throw new MissingParameterException(Ui.printChat(Ui.indentation
                    + "Missing parameters error: Incorrect format for delete queries:\n"
                    + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n"));
        }
        String result = "";
        try {

            int index = Integer.parseInt(inputArr[1]) - 1;

            List<String> lines = Files.readAllLines(path);
            String toRemove = lines.get(index);
            lines.remove(index);
            Files.write(path, lines);
            noOfActivity--;
            result = Ui.printChat(Ui.indentation + "Noted. I've removed this task:\n"
                    + Ui.indentation + "  " + toRemove + "\n"
                    + Ui.indentation + "Now you have " + Task.getNoOfActivity()
                    + " tasks in the list.\n");
            return result;

        } catch (FileNotFoundException e) {
            System.out.println("File error: File not found");
        } catch (IOException e) {
            System.out.println("Input error: Unable to read file");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Ui.printChat(Ui.indentation + "Missing parameters error: Missing parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n"));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Ui.printChat(Ui.indentation + "Missing parameters error: This task doesn't exist\n"
                    + Ui.indentation + "You can only delete an existing task\n"));
        } catch (NumberFormatException e) {
            System.out.println(Ui.printChat(Ui.indentation + "Missing parameters error: Wrong parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n"));
        }
        return result;
    }

    @Override
    public String toString() {
        String string = "[%s] %s";
        return String.format(string, isDone ? "X" : " ", this.name);
    }
}
