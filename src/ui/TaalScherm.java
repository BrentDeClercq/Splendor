package ui;

import java.util.Locale;
import java.util.ResourceBundle;
import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TaalScherm extends GridPane {

    private DomeinController dc;
    private ResourceBundle bundle;

    public TaalScherm(DomeinController dc) {

	// achtergrondkleur instellen
	this.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #f2ebdf, #c9c4b6, #f2ebdf);");

	this.dc = dc;
	this.setHgap(10);
	this.setVgap(10);

	this.setPadding(new Insets(25, 25, 25, 25));

	buildGui();

    }

    private void buildGui() {

	Label lblTitle = new Label("Kies taal/Choose language");
	lblTitle.setFont(Font.font("Comic sans", FontWeight.NORMAL, 20));
	this.add(lblTitle, 4, 0, 2, 1);

	Button btnNederlands = new Button("Nederlands");
	btnNederlands.setFont(Font.font("Comic sans", FontWeight.NORMAL, 13));
	this.add(btnNederlands, 5, 3);
	btnNederlands.setOnAction(this::Nederlands);
	// btnNederlands.setOnAction(this::start);

	Button btnEngels = new Button("English");
	btnEngels.setFont(Font.font("Comic sans", FontWeight.NORMAL, 13));
	this.add(btnEngels, 5, 4);
	btnEngels.setOnAction(this::English);
	// btnEngels.setOnAction(this::start);
    }

    private void Nederlands(ActionEvent action) {
	taalInstellen("nl");
	start();
    }

    private void English(ActionEvent action) {
	taalInstellen("us");
	start();
    }

    private void start() {

	LoginScherm s = new LoginScherm(dc, bundle);
	Scene scene = new Scene(s, 480, 280);
	Stage stage = (Stage) this.getScene().getWindow();

	// loginscherm centreren
	Rectangle2D bounds = Screen.getPrimary().getBounds();

	stage.setX(bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2);
	stage.setY(bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2);

	stage.setScene(scene);
	stage.show();

    }

    private void taalInstellen(String countryCode) {

	if (countryCode.equals("us")) {
	    Locale currentLocale = new Locale("en", "US");
	    ResourceBundle bundel = ResourceBundle.getBundle("ui.MessageBundle", currentLocale);
	    bundle = bundel;
	} else if (countryCode.equals("nl")) {
	    Locale currentLocale = new Locale("nl", "NL");
	    ResourceBundle bundel = ResourceBundle.getBundle("ui.MessageBundle", currentLocale);
	    bundle = bundel;
	}

    }

    public ResourceBundle getBundle() {
	return bundle;
    }

}
