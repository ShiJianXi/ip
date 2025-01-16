import java.util.ArrayList;
import java.util.Scanner;

public class ChitChatBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";
        String line = "_____________________________________________________";
        String indentation = "    ";
        ArrayList<Task> Tasks = new ArrayList<>();

        //Greet the user
        System.out.println(indentation + line);
        System.out.println(indentation + "Hello! I'm " + name);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + line);

        //Takes in user input
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(" ");
            if (inputArr[0].equals("bye")) {
                System.out.println(indentation + line);
                System.out.println(indentation + "Bye. Hope to see you again soon!");
                System.out.println(indentation + line);
                break;
            } else if (inputArr[0].equals("list")){
                System.out.println(indentation + line);
                for (int i = 0; i < Task.getNoOfActivity(); i++) {
                    int index = i + 1;
                    System.out.println(indentation + index + "."
                            + Tasks.get(i).toString());
                }
                System.out.println(indentation + line);
            } else if (inputArr[0].equals("mark")){
                int index = Integer.parseInt(inputArr[1]) - 1;
                Task targetedTask = Tasks.get(index);
                targetedTask.markAsDone();
                System.out.println(indentation + line);
                System.out.println(indentation + "Nice! I've marked this task as done");
                System.out.println(indentation + "  " + targetedTask);
                System.out.println(indentation + line);
            } else if (inputArr[0].equals("unmark")){
                int index = Integer.parseInt(inputArr[1]) - 1;
                Task targetedTask = Tasks.get(index);
                targetedTask.markAsNotDone();
                System.out.println(indentation + line);
                System.out.println(indentation + "OK, I've marked this task as not done yet:");
                System.out.println(indentation + "  " + targetedTask);
                System.out.println(indentation + line);
            } else {
                String add = "added: ";
                Task newTask = new Task(input);
                Tasks.add(newTask);
                System.out.println(indentation + line);
                System.out.println(indentation + add + input);
                System.out.println(indentation + line);
            }
        }
    }
}
