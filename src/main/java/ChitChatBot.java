import java.util.ArrayList;
import java.util.Scanner;

public class ChitChatBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";
        String indentation = "    ";
        ArrayList<Task> Tasks = new ArrayList<>();

        //Greet the user
        System.out.println(printChat(indentation + "Hello! I'm " + name + "\n"
                + indentation + "What can i do for you?" + "\n"));

        //Takes in user input
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(" ");
            if (inputArr[0].equals("bye")) {

                System.out.println(printChat(indentation + "Bye. Hope to see you again soon!\n"));
                break;

            } else if (inputArr[0].equals("list")) {

                String toPrint = "";
                for (int i = 0; i < Task.getNoOfActivity(); i++) {
                    int index = i + 1;
                    toPrint += indentation + index + "." + Tasks.get(i).toString() + "\n";
                }
                System.out.println(printChat(toPrint));

            } else if (inputArr[0].equals("mark")) {
                int index = Integer.parseInt(inputArr[1]) - 1;
                Task targetedTask = Tasks.get(index);
                targetedTask.markAsDone();

                System.out.println(printChat(indentation + "Nice! I've marked this task as done:\n"
                        + indentation + "  " + targetedTask + "\n"));
            } else if (inputArr[0].equals("unmark")) {
                int index = Integer.parseInt(inputArr[1]) - 1;
                Task targetedTask = Tasks.get(index);
                targetedTask.markAsNotDone();

                System.out.println(printChat(indentation + "OK, I've marked this task as not done yet:\n"
                        + indentation + "  " + targetedTask + "\n"));

            } else if (inputArr[0].equals("todo")) {

                try {
                    Task newTask = Todo.createToDo(inputArr);
                    Tasks.add(newTask);
                    Tasks.add(newTask);

                    int noOfTasks = Task.getNoOfActivity();
                    System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                            + indentation + "  " + newTask + "\n"
                            + indentation + "Now you have " + noOfTasks + " tasks in the list.\n"));
                } catch (EmptyParameterException e) {
                    System.out.println(printChat(indentation +
                            e.getMessage()));
                }
//                Tasks.add(newTask);
//
//                int noOfTasks = Task.getNoOfActivity();
//                System.out.println(printChat(indentation + "Got it. I've added this task:\n"
//                        + indentation + "  " + newTask + "\n"
//                        + indentation + "Now you have " + noOfTasks + " tasks in the list.\n"));

            } else if (inputArr[0].equals("deadline")) {

                Task newTask = Deadline.createDeadline(inputArr);
                Tasks.add(newTask);
                int noOfTasks = Task.getNoOfActivity();
                System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                        + indentation + "  " + newTask + "\n"
                        + indentation + "Now you have " + noOfTasks + " tasks in the list.\n"));

            } else if (inputArr[0].equals("event")) {

                Task newTask = Event.createEvent(inputArr);
                Tasks.add(newTask);
                int noOfTasks = Task.getNoOfActivity();
                System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                        + indentation + "  " + newTask + "\n"
                        + indentation + "Now you have "
                        + noOfTasks + " tasks in the list.\n"));

            } else {
//                String add = "added: ";
//                Task newTask = new Task(input);
//                Tasks.add(newTask);
//                System.out.println(printChat(indentation + add + input + "\n"));
                System.out.println(printChat(indentation
                        + "OOPS!!! I'm sorry, but I don't know what that means :-(\n"));
            }
        }
    }

    private static String printChat(String message) {
        String line = "_____________________________________________________";
        String indentation = "    ";
        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
    }
}
