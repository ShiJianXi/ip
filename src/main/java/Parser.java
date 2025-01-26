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

            Task.markAsDone(this.storage.getPath(), this.inputArr);

        } else if (this.action == Action.unmark) {

            Task.markAsNotDone(this.storage.getPath(), this.inputArr);

        } else if (this.action == Action.todo) {

            Todo.createToDo(this.inputArr, this.storage);

        } else if (this.action == Action.deadline) {

            Deadline.createDeadline(this.inputArr, this.storage);

        } else if (this.action == Action.event) {

            Event.createEvent(this.inputArr, this.storage);

        } else if (this.action == Action.delete) {

            Task.deleteTask(this.storage.getPath(), this.inputArr);

        }
    }
}

