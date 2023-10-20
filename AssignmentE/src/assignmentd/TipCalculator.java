package assignmentd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TipCalculator extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TipCalculator.fxml"));

        Scene scene = new Scene(root); // Attach scene graph to the scene
        stage.setTitle("Tip Calculator"); // Displayed in the window's title bar
        stage.setScene(scene); // Attach the scene to the stage
        stage.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}

