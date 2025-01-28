package chitchatbot.command;

import chitchatbot.Action;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.task.Deadline;
import chitchatbot.task.Event;
import chitchatbot.task.Task;
import chitchatbot.task.Todo;
import chitchatbot.ui.Ui;

import java.util.Arrays;

public class Parser {
    private Action action;
    private String[] inputArr;
    private Storage storage;

    public Parser(String[] inputArr, Storage storage) {
        this.storage = storage;
        this.inputArr = inputArr;
        this.action = null;
    }

    public void parseCommand() {
        try {
            this.action = Action.valueOf(this.inputArr[0]);
        } catch (IllegalArgumentException e) {
            System.out.println(Ui.printChat(Ui.indentation + "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                    + Ui.indentation + "Please use the correct queries:\n"
                    + Ui.indentation + "todo <description>\n"
                    + Ui.indentation + "deadline <description> /by <Date/Time>\n"
                    + Ui.indentation + "event <description> /from <Date/Time> /to <Date/Time>\n"
                    + Ui.indentation + "or list to show all the task\n"));
        }

        if (action == Action.bye) {
            System.out.println(Ui.printChat(Ui.indentation
                    + "Bye. Hope to see you again soon!\n"));
            //break;
            System.exit(0);

        } else if (this.action == Action.list) {

            this.storage.listTask();

        } else if (this.action == Action.mark) {

            String result = "";
            try {
                result = Task.markAsDone(this.storage.getPath(), this.inputArr);
                System.out.println(result);
            } catch (MissingParameterException e) {
                System.out.println(Ui.printChat(e.getMessage()));
            }


        } else if (this.action == Action.unmark) {

            String result = "";
            try {
                result = Task.markAsNotDone(this.storage.getPath(), this.inputArr);
                System.out.println(result);
            } catch (MissingParameterException e) {
                System.out.println(Ui.printChat(e.getMessage()));
            }


        } else if (this.action == Action.todo) {

            String result = "";
            try {
                result = Todo.createToDo(this.inputArr, this.storage);
                System.out.println(result);
            } catch (MissingParameterException e) {
                //System.out.println("test");
                //System.out.println(e.getMessage());
                System.out.println(Ui.printChat(Ui.indentation + e.getMessage()));
            }

        } else if (this.action == Action.deadline) {

            String result = "";

            try {
                result = Deadline.createDeadline(this.inputArr, this.storage);
            } catch (MissingParameterException e) {
                System.out.println(Ui.printChat(e.getMessage()));
            }

            System.out.println(result);

        } else if (this.action == Action.event) {

            String result = "";
            try {
                result = Event.createEvent(this.inputArr, this.storage);
                System.out.println(result);
            } catch (MissingParameterException e) {
                System.out.println(Ui.printChat(e.getMessage()));
            }


        } else if (this.action == Action.delete) {

            String result = "";
            try {
                result = Task.deleteTask(this.storage.getPath(), this.inputArr);
                System.out.println(result);
            } catch (MissingParameterException e) {
                System.out.println(e.getMessage());
            }


        }
    }
}

