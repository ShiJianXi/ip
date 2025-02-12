package chitchatbot.command;

import chitchatbot.Action;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.task.Deadline;
import chitchatbot.task.Event;
import chitchatbot.task.Task;
import chitchatbot.task.Todo;

/**
 * A class to parse all the commands entered by the user
 */
public class Parser {
    private Action action;
    private String[] inputArr;
    private Storage storage;

    /**
     * Constructs the parser to parse the user's input
     * The user will input in a regular String and the string will be split into a String[]
     * The data will be stored in a data folder under chatbot.txt within the application directory.
     *
     * @param inputArr A String[] split from user's input.
     * @param storage  The location that the chatbot.txt will be stored.
     */
    public Parser(String[] inputArr, Storage storage) {
        this.storage = storage;
        this.inputArr = inputArr;
        this.action = null;
    }

    /**
     * Prints to the user's screen the chat ui based on the user's input.
     *
     * @throws IllegalArgumentException if the user input an invalid command.
     * @see Action
     * @see Storage
     * @see Deadline
     * @see Event
     * @see Task
     * @see Todo
     */
    public String parseCommand() {
        try {
            this.action = Action.valueOf(this.inputArr[0]);
        } catch (IllegalArgumentException e) {
            return "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                    + "Please use the correct queries:\n"
                    + "todo <description>\n"
                    + "deadline <description> /by <Date/Time>\n"
                    + "event <description> /from <Date&Time> /to <Date&Time>\n"
                    + "or list to show all the task";
        }

        if (action == Action.bye) {
            return "Bye. Hope to see you again soon!";

        } else if (this.action == Action.list) {

            return this.storage.listTask();

        } else if (this.action == Action.mark) {

            try {
                return Task.markAsDone(this.storage.getPath(), this.inputArr);
            } catch (MissingParameterException e) {
                return e.getMessage();
            }


        } else if (this.action == Action.unmark) {

            try {
                return Task.markAsNotDone(this.storage.getPath(), this.inputArr);
            } catch (MissingParameterException e) {
                return e.getMessage();
            }


        } else if (this.action == Action.todo) {

            try {
                return Todo.createToDo(this.inputArr, this.storage);

            } catch (MissingParameterException e) {

                return e.getMessage();
            }

        } else if (this.action == Action.deadline) {

            try {
                return Deadline.createDeadline(this.inputArr, this.storage);
            } catch (MissingParameterException e) {
                return e.getMessage();
            }

        } else if (this.action == Action.event) {
            try {
                return Event.createEvent(this.inputArr, this.storage);
            } catch (MissingParameterException e) {
                return e.getMessage();
            }


        } else if (this.action == Action.delete) {
            try {
                return Task.deleteTask(this.storage.getPath(), this.inputArr);
            } catch (MissingParameterException e) {
                return e.getMessage();
            }
        } else if (this.action == Action.find) {

            Find find = new Find(storage);
            try {
                String result = find.executeFindCommand(inputArr);
                return "List of similar task: \n" + result;
            } catch (MissingParameterException e) {
                return e.getMessage();
            }

        }

        return "";
    }
}

