package gui_resources.a7_map_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class A7Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader listLoader = new FXMLLoader(A7Application.class.getResource("A7ViewList.fxml"));
        Scene listScene = new Scene(listLoader.load(), 552, 740);
        stage.setTitle("A7_LIST");
        stage.setScene(listScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}