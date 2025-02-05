package chitchatbot.ui;

public class Ui {

    //Format for printing of message
//    public static String printChat(String message) {
//        String line = "_____________________________________________________";
//        String indentation = "    ";
//        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
//    }

    public static String indentation = "    ";

    public static String greetUser() {
//        return Ui.printChat(Ui.indentation + "Hello! I'm "
//                + "ChitChatBot" + "\n"
//                + Ui.indentation + "What can i do for you?" + "\n");
        return "Hello! I'm "
                + "ChitChatBot" + "\n"
                + "What can i do for you?";
    }
}
