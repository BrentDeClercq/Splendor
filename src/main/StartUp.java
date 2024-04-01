package main;

import domein.DomeinController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ui.TaalScherm;

public class StartUp extends Application {

    /*
     * public static void main(String[] args) {
     * SplendorConsoleApplicatie sca = new SplendorConsoleApplicatie(new DomeinController());
     * sca.start();
     * }
     */
    @Override
    public void start(Stage primaryStage) {
	Image icon = new Image(getClass().getResourceAsStream("/images/Splendor.png"));
	primaryStage.getIcons().add(icon);

	TaalScherm root = new TaalScherm(new DomeinController());

	Scene scene = new Scene(root, 350, 200);
	primaryStage.setScene(scene);
	primaryStage.setTitle("Splendor");

	primaryStage.show();

	// scherm centreren
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public static void main(String[] args) {
	launch(args);
    }
}
