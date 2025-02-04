package chitchatbot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import chitchatbot.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class Main extends Application {
    //private Duke duke = new Duke();
    @FXML
    private VBox dialogContainer;
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/botDP.jpg"));

    Path path = Paths.get("data", "chat.txt");
    ChitChatBot chitChatBot = new ChitChatBot(path);

    private ChitChatBot bot = chitChatBot;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBot(bot);
            //greetUser();
            stage.show();
            //greetUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void greetUser() {
        dialogContainer.getChildren().add(DialogBox.getBotDialog("Test", botImage));
    }

}
