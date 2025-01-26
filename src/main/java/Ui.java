public class Ui {
    //Format for printing of message
    public static String printChat(String message) {
        String line = "_____________________________________________________";
        String indentation = "    ";
        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
    }

    public static String indentation = "    ";
}
