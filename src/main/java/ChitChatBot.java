import java.util.ArrayList;
import java.util.Scanner;

public class ChitChatBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";
        String line = "_____________________________________________________";
        String indentation = "    ";
        ArrayList<Activity> activities = new ArrayList<>();

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
                for (int i = 0; i < Activity.getNoOfActivity(); i++) {
                    int index = i + 1;
                    System.out.println(indentation + index + "."
                            + activities.get(i).toString());
                }
                System.out.println(indentation + line);
            } else if (inputArr[0].equals("mark")){
                int index = Integer.parseInt(inputArr[1]) - 1;
                Activity targetedActivity = activities.get(index);
                targetedActivity.markAsDone();
                System.out.println(indentation + line);
                System.out.println(indentation + "Nice! I've marked this task as done");
                System.out.println(indentation + "  " + targetedActivity);
                System.out.println(indentation + line);
            } else if (inputArr[0].equals("unmark")){
                int index = Integer.parseInt(inputArr[1]) - 1;
                Activity targetedActivity = activities.get(index);
                targetedActivity.markAsNotDone();
                System.out.println(indentation + line);
                System.out.println(indentation + "OK, I've marked this task as not done yet:");
                System.out.println(indentation + "  " + targetedActivity);
                System.out.println(indentation + line);
            } else {
                String add = "added: ";
                Activity newActivity = new Activity(input);
                activities.add(newActivity);
                System.out.println(indentation + line);
                System.out.println(indentation + add + input);
                System.out.println(indentation + line);
            }
        }
    }
}
