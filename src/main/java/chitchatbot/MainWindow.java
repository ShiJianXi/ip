package chitchatbot;

import java.nio.file.Path;
import java.nio.file.Paths;

import chitchatbot.command.Parser;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;


    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private ChitChatBot bot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userDP.jpg"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/botDP.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getBotDialog(Ui.greetUser(), botImage));
    }


    public void setBot(ChitChatBot bot) {
        this.bot = bot;
    }

    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        Path path = Paths.get("data", "chat.txt");
        ChitChatBot chitChatBot = new ChitChatBot(path);
        Storage storage = new Storage(path);
        Parser parser = new Parser(userText.split(" "), storage);

        String botText = chitChatBot.getBotResponse(parser);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBotDialog(botText, botImage)
        );
        userInput.clear();
    }


}
