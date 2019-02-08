package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClient extends Application {

    /**
     * @param primaryStage Stage instance
     * @throws Exception resource exception
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final int width = 400;
        final int height = 400;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client.fxml"));
        primaryStage.setTitle("Styled Chat");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }

    /**
     * @param args startup args
     */
    public static void main(final String[] args) {
        launch(args);
    }
}