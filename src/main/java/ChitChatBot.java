import java.util.Scanner;

public class ChitChatBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";
        String line = "_____________________________________________________";
        String indentation = "    ";
        System.out.println(indentation + line);
        System.out.println(indentation + "Hello! I'm " + name);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + line);
        while (sc.hasNext()) {
            String input = sc.next();
            if (input.equals("bye")) {
                System.out.println(indentation + line);
                System.out.println(indentation + "Bye. Hope to see you again soon!");
                System.out.println(indentation + line);
                break;
            } else {
                System.out.println(indentation + line);
                System.out.println(indentation + input);
                System.out.println(indentation + line);
            }
        }
    }
}
