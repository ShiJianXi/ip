package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * The deadline class that handles deadline commands
 */
public class Deadline extends Task {

    protected LocalDate by;
    protected LocalTime time;
    private boolean containTime = false;

    /**
     * Constructs a new Deadline task with the given name and date.
     *
     * @param name the description of the Deadline, in String
     * @param by the deadline of the task, in LocalDate, in the dd/MM/yyyy format.
     * @see Task
     */
    public Deadline(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    /**
     * Constructs a new Deadline task with the given name, date and time.
     *
     * @param name the description of the Deadline, in String
     * @param by the deadline of the task, in LocalDate, in the dd/MM/yyyy format.
     * @param time the time the task is due, in LocalTime, in the hhMM format.
     * @see Task
     */
    public Deadline(String name, LocalDate by, LocalTime time) {
        super(name);
        this.by = by;
        this.time = time;
        this.containTime = true;
    }

    /**
     * Returns the String to be printed to the user's screen using chat UI.
     * If an exception is catch during execution, an empty string will be returned.
     *
     * @param inputArr The user's input that was split from a String into a String[]
     * @param storage The storage where the text will be stored at.
     * @return The String to be printed to the user's screen using chat UI.
     * @throws MissingParameterException If the user's input has missing parameters
     * @see Ui
     * @see Storage
     */
    public static String createDeadline(String[] inputArr, Storage storage) throws MissingParameterException {
        checkDeadlineInputFormat(inputArr);
        return tryGetStringOfNewDeadline(inputArr, storage);
    }

    private static String tryGetStringOfNewDeadline(String[] inputArr, Storage storage) {
        try {
            int byIndex = getByIndex(inputArr);
            String taskDescription = addDescriptionUpToBy(byIndex, inputArr);
            return getStringOfNewDeadline(inputArr, storage, byIndex, taskDescription);
        } catch (DateTimeException e1) {
            return "Incorrect date time format: \n"
                    + "please ensure the correct format is used:\n"
                    + "    deadline <Description> /by dd/mm/yyyy\n"
                    + "    OR deadline <Description /by dd/mm/yyyy HHmm";
        }
    }

    private static String getStringOfNewDeadline(String[] inputArr, Storage storage, int byIndex, String taskDescription) {
        boolean containsTime = inputArr.length > byIndex + 2;

        if (containsTime) {
            return getStringOfDeadlineTaskWithTime(inputArr, storage, byIndex, taskDescription);
        } else {
            return getStringOfDeadlineTaskWithoutTime(inputArr, storage, byIndex, taskDescription);
        }
    }

    private static String getStringOfDeadlineTaskWithoutTime(String[] inputArr, Storage storage, int byIndex, String taskDescription) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate by = LocalDate.parse(inputArr[byIndex + 1], formatter);

        Deadline newTask = new Deadline(taskDescription, by);
        storage.appendToFile(newTask.toString());
        return "Got it. I've added this task:\n"
                + "  " + newTask + "\n"
                + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.";
    }

    private static String getStringOfDeadlineTaskWithTime(String[] inputArr, Storage storage, int byIndex, String taskDescription) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDate date = LocalDate.parse(inputArr[byIndex + 1], formatter);
        LocalTime time = LocalTime.parse(inputArr[byIndex + 2], timeFormatter);
        Deadline newTask = new Deadline(taskDescription, date, time);

        storage.appendToFile(newTask.toString());
        return "Got it. I've added this task:\n"
                + "  " + newTask + "\n"
                + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.";
    }


    private static void checkDeadlineInputFormat(String[] inputArr) throws MissingParameterException {
        if (inputArr.length < 2 || !Arrays.asList(inputArr).contains("/by") || inputArr[1].equals("/by")
                || Arrays.asList(inputArr).indexOf("/by") == inputArr.length - 1) {

            throw new MissingParameterException("Missing parameter: \n"
                    + "please ensure that the correct format is used:\n"
                    + "    deadline <Description> /by dd/mm/yyyy\n"
                    + "    OR deadline <Description /by dd/mm/yyyy HHmm");
        }
    }

    private static int getByIndex(String[] inputArr) {
        int index = 0;
        for (int i = 1; i < inputArr.length; i++) {
            if (inputArr[i].equals("/by")) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static String addDescriptionUpToBy(int byIndex, String[] inputArr) {
        StringJoiner taskDescription = new StringJoiner(" ");
        for (int i = 1; i < byIndex; i++) {
            taskDescription.add(inputArr[i]);
        }

        return taskDescription.toString();
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
