package ui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.LoginException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginScherm extends GridPane {

    private Label lblMessage;
    private TextField txfGebruikersnaam;
    private TextField txfGeboortejaar;
    private DomeinController dc;
    private Label lblAantalSpelers;
    private Button btnStart;
    private ResourceBundle bundle;

    public LoginScherm(DomeinController dc, ResourceBundle taalKeuze) {

	// achtergrondkleur instellen
	this.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #f2ebdf, #c9c4b6, #f2ebdf);");

	this.dc = dc;
	bundle = taalKeuze;
	this.setHgap(10);
	this.setVgap(10);

	this.setPadding(new Insets(25, 25, 25, 25));

	buildGui();

    }

    private void buildGui() {
	// bundle = t.getBundle();
	Label lblTitle = new Label("Splendor");
	lblTitle.setFont(Font.font("old english Text MT", FontWeight.NORMAL, 35));
	this.add(lblTitle, 0, 0, 2, 1);

	Label lblAdd = new Label(bundle.getString("spelerADD"));
	lblAdd.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	// Label lblUitleg = new Label("error detected bij de Label LOGIN:");
	this.add(lblAdd, 0, 1);

	Label lblGebruikersNaam = new Label(bundle.getString("naam"));
	lblGebruikersNaam.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	this.add(lblGebruikersNaam, 0, 2);

	txfGebruikersnaam = new TextField();
	this.add(txfGebruikersnaam, 1, 2);

	Label lblGeboortejaar = new Label(bundle.getString("jaar"));
	lblGeboortejaar.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	this.add(lblGeboortejaar, 0, 3);

	txfGeboortejaar = new TextField();
	this.add(txfGeboortejaar, 1, 3);

	Button btnVoegToe = new Button(bundle.getString("btnVoegToe"));
	btnVoegToe.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	this.add(btnVoegToe, 1, 4);

	btnStart = new Button("Start");
	btnStart.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	// TODO volgende lijn weghalen (gewoon om te testen)
	// this.add(btnStart, 2, 4);

	lblMessage = new Label();
	lblMessage.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	this.add(lblMessage, 0, 6, 3, 1);

	lblAantalSpelers = new Label(bundle.getString("aantalSpelers"));
	lblAantalSpelers.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	this.add(lblAantalSpelers, 2, 2);

	btnVoegToe.setOnAction(this::buttonVoegToe);
	btnStart.setOnAction(this::start);

	txfGebruikersnaam.setOnKeyPressed(event -> {
	    if (event.getCode().equals(KeyCode.ENTER)) {
		txfGeboortejaar.requestFocus();
	    }
	});

	txfGeboortejaar.setOnKeyPressed(event -> {
	    if (event.getCode().equals(KeyCode.ENTER)) {
		btnVoegToe.requestFocus();
	    }
	});

    }

    // centreerd scherm
    // stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
    // stage.setY((bounds.getHeight() - stage.getHeight()) / 2);

    private void buttonVoegToe(ActionEvent event) {

	try {
	    if (txfGebruikersnaam.getText() == null || txfGebruikersnaam.getText().isBlank() || txfGeboortejaar.getText() == null || txfGeboortejaar.getText().isBlank()) {
		lblMessage.setText(bundle.getString("emptyField"));
	    } else {
		dc.logSpelerIn(txfGebruikersnaam.getText(), Integer.parseInt(txfGeboortejaar.getText()));
		lblMessage.setText(bundle.getString("spelerADDtrue"));
		lblAantalSpelers.setText(String.format("%s %d", bundle.getString("Xspelers"), dc.getAantalSpelersIngelogd()));
	    }
	} catch (LoginException e) {
	    lblMessage.setText(String.format(bundle.getString(e.getMessage()))); // RESOURCEBUNDEL TEST-01
//		lblMessage.setText(e.getMessage());
	} catch (NumberFormatException nfe) {
	    lblMessage.setText(bundle.getString("datumINTfalse"));
	}

	if (dc.maxSpelersIngelogdBereikt()) {
	    // volgend scherm
	    start(event);
	}

	txfGebruikersnaam.setText("");
	txfGeboortejaar.setText("");
	txfGebruikersnaam.requestFocus();

	if (dc.getAantalSpelersIngelogd() == 2)
	    this.add(btnStart, 2, 4);
    }

    private void start(ActionEvent event) {

	// start spel
	dc.nieuwSpel();

	SpelScherm s = new SpelScherm(dc, bundle);

	Rectangle2D bounds = Screen.getPrimary().getBounds();

	Scene scene = new Scene(s, 1130, 830);
	Stage stage = (Stage) this.getScene().getWindow();

	stage.setScene(scene);

	// stage.setFullScreen(true);

	double centerX = bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2;
	double centerY = bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2;
	stage.setX(centerX);
	stage.setY(centerY);

	stage.show();
    }

//    private void mainLoop() {
//   	ArrayList<SpelerDTO> spelers = dc.nieuwSpel();
//		String spelersMelding = "";
//		for (SpelerDTO speler : spelers) {
//			spelersMelding += speler.toString();
//		}
//		System.out.printf("De speelvolgorde is:%n%s%n", spelersMelding);
//		

//		while (!dc.spelUit()) {
//			System.out.println(Overzichten.geefOverzicht(dc.getSpelDTO()));
//			this.speelBeurt();
//		} 

//		for (SpelerDTO winnaar : dc.getWinnaars()) {
//			this.spelerWint(winnaar);
//		}
//    }

//    private void speelBeurt() {
//		System.out.printf("Wat wil je doen?%n"
//				+ "%d: Neem fiches%n"
//				+ "%d: Neem ontwikkelingskaart%n",
//				NEEM_FICHES, NEEM_KAART);
//		
//		try {
//			switch (s.nextInt()) {
//				case NEEM_FICHES: this.neemFiches(); break;
//				case NEEM_KAART: this.neemKaart(); break;
//				default: {
//					throw new SpelRegelException("Deze zet bestaat niet");
//				}
//			}
//		} catch (SpelRegelException sre) {
//			System.out.printf("Deze zet is niet mogelijk: %s%n", sre.getMessage());
//			this.speelBeurt();
//		} catch (InputMismatchException ime) {
//			System.out.println("Voer een integer in");
//			s.nextLine();
//			this.speelBeurt();
//		}
//	}

}
