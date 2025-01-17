import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

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
                String task = "";
                for (int i = 1; i < inputArr.length; i++) {
                    if (inputArr[i].equals("/by")) {
                        break;
                    }
                    task += inputArr[i];
                    task += " ";
                }
                Task newTask = new Todo(task);
                Tasks.add(newTask);

                int noOfTasks = Task.getNoOfActivity();
                System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                        + indentation + "  " + newTask + "\n"
                        + indentation + "Now you have " + noOfTasks + " tasks in the list.\n"));
            } else if (inputArr[0].equals("deadline")) {
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

                Task newTask = new Deadline(task, by.toString());
                Tasks.add(newTask);
                int noOfTasks = Task.getNoOfActivity();
                System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                        + indentation + "  " + newTask + "\n"
                        + indentation + "Now you have " + noOfTasks + " tasks in the list.\n"));

            } else if (inputArr[0].equals("event")) {
                String task = "";
                int fromIndex = 0;
                int toIndex = 0;
                StringJoiner from = new StringJoiner(" ");
                StringJoiner to = new StringJoiner(" ");
                for (int i = 1; i < inputArr.length; i++) {
                    if (inputArr[i].equals("/from")) {
                        fromIndex = i;
                        break;
                    }

                    task += inputArr[i];
                    task += " ";
                }
                for (int i = fromIndex + 1; i < inputArr.length; i++) {
                    if (inputArr[i].equals("/to")) {
                        toIndex = i;
                        break;
                    }
                    from.add(inputArr[i]);
                }

                for (int i = toIndex + 1; i < inputArr.length; i++) {
                    to.add(inputArr[i]);
                }

                Task newTask = new Event(task, from.toString(), to.toString());
                Tasks.add(newTask);
                int noOfTasks = Task.getNoOfActivity();
                System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                        + indentation + "  " + newTask + "\n"
                        + indentation + "Now you have "
                        + noOfTasks + " tasks in the list.\n"));
            } else {
                String add = "added: ";
                Task newTask = new Task(input);
                Tasks.add(newTask);
                System.out.println(printChat(indentation + add + input + "\n"));
            }
        }
    }

    private static String printChat(String message) {
        String line = "_____________________________________________________";
        String indentation = "    ";
        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
    }
}
