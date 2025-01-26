import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class ChitChatBot {
    private Path path;
    private Storage storage;

    public ChitChatBot(Path path) {
        this.path = path;
        this.storage = new Storage(path);
        storage.initStorage();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        Ui.greetUser();
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(" ");

            Parser parser = new Parser(inputArr, storage);
            parser.parseCommand();
        }
    }

    public static void main(String[] args) throws MissingParameterException {

        //Get the path to create where the chat.txt supposed to be
        Path path = Paths.get("data", "chat.txt");
        ChitChatBot chitChatBot = new ChitChatBot(path);
        chitChatBot.run();

    }
}
