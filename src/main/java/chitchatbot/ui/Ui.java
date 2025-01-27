package chitchatbot.ui;

public class Ui {

    //Format for printing of message
    public static String printChat(String message) {
        String line = "_____________________________________________________";
        String indentation = "    ";
        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
    }

    public static String indentation = "    ";

    public static void greetUser() {
        System.out.println(Ui.printChat(Ui.indentation + "Hello! I'm "
                + "chitchatbot.ChitChatBot" + "\n"
                + Ui.indentation + "What can i do for you?" + "\n"));
    }
}
