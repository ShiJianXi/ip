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
            if (input.equals("bye")) {
                System.out.println(indentation + line);
                System.out.println(indentation + "Bye. Hope to see you again soon!");
                System.out.println(indentation + line);
                break;
            } else if (input.equals("list")){
                int len = activities.size();
                System.out.println(indentation + line);
                for (int i = 0; i < len; i++) {
                    String activityString = ". " + activities.get(i).toString();
                    int index = i + 1;
                    System.out.println(indentation + index + activityString);
                }
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
