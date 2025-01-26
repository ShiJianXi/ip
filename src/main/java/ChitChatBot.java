
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ChitChatBot {

    public static void main(String[] args) throws MissingParameterException {

        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";

        //Get the path to create where the chat.txt supposed to be
        Path path = Paths.get("data", "chat.txt");
        Storage storage = new Storage(path);

        storage.initStorage();

        //Greet the user
        System.out.println(Ui.printChat(Ui.indentation + "Hello! I'm "
                + name + "\n"
                + Ui.indentation + "What can i do for you?" + "\n"));

        //Takes in user input
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(" ");
            Action action = null;

            Parser parser = new Parser(inputArr, storage);
            parser.parseCommand();
        }
    }
}
