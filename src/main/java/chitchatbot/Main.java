package chitchatbot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class that starts the stage for the GUI
 */
public class Main extends Application {

    private Path path = Paths.get("data", "chat.txt");
    private ChitChatBot chitChatBot = new ChitChatBot(path);

    private ChitChatBot bot = chitChatBot;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("ChitChatBot");
            stage.setResizable(true);
            stage.setMinHeight(600);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setBot(bot);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
