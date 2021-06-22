package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.awt.*;


public class Main extends Application {
    public static Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResourceAsStream("resources/Daydream.ttf"),18);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        primaryStage.setTitle("PixPal");
        Scene scene = new Scene(root, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height-80);
        scene.getStylesheets().add(getClass().getResource("resources/mainStylesheet.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("resources/mainStylesheet1.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Controller getController() {return controller;}

    public static void main(String[] args) {
        launch(args);
    }
}
