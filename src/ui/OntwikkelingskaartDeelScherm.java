package ui;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Ontwikkelingskaart;
import exceptions.EdeleException;
import exceptions.SpelRegelException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class OntwikkelingskaartDeelScherm extends HBox {

    private int niveau;
    private DomeinController dc;
    private Button btnKaart;
    private Ontwikkelingskaart kaart;
    private ArrayList<Ontwikkelingskaart> lijst;
    private ResourceBundle bundle;
    private SpelScherm spelScherm;

    public OntwikkelingskaartDeelScherm(int niveau, DomeinController dc, SpelScherm spelScherm, ResourceBundle bundle) {
	this.niveau = niveau;
	this.dc = dc;
	this.bundle = bundle;
	this.spelScherm = spelScherm;
	lijst = new ArrayList<>();
	this.setSpacing(10);
	this.setAlignment(Pos.CENTER);
	buildGui();
    }

    private void buildGui() {
	Image stapel = new Image(getClass().getResourceAsStream(String.format("/images/Decks/niveau0%d.png", niveau)));
	ImageView vStapel = new ImageView(stapel);
	vStapel.setFitHeight(155);
	vStapel.setFitWidth(105);
	vStapel.setId("kaart");

	if (dc.spel.getOntwikkelingskaartStapels().get(niveau - 1).size() == 0) {
	    vStapel.setOpacity(0);
	} else {
	    vStapel.setOpacity(100);
	}

	this.getChildren().add(vStapel);

	for (int i = 0; i < dc.geefOntwikkelingskaartVeld().get(niveau - 1).size(); i++) {
	    kaart = dc.geefOntwikkelingskaartVeld().get(niveau - 1).get(i);
	    lijst.add(kaart);
	    this.getChildren().add(buildOntwikkelingskaart(kaart));
	}
    }

    private Button buildOntwikkelingskaart(Ontwikkelingskaart kaart) {
	Image imgKaart = new Image(getClass().getResourceAsStream(String.format("/images/Ontwikkelingskaarten/%s.png", kaart.getImg())));
	ImageView vKaart = new ImageView(imgKaart);
	vKaart.setFitHeight(150);
	vKaart.setFitWidth(100);

	btnKaart = new Button();
	btnKaart.setGraphic(vKaart);
	btnKaart.setUserData(kaart);

	btnKaart.setOnAction(this::ontwikkelinskaartGekozen);

	return btnKaart;

    }

    private void ontwikkelinskaartGekozen(ActionEvent event) {
	Ontwikkelingskaart gekozenKaart = (Ontwikkelingskaart) ((Button) event.getSource()).getUserData();

	try {
	    vraagBevestiging(gekozenKaart);
	    spelScherm.updateScherm();
	} catch (SpelRegelException sre) {
	    // Maak een Alert aan met het type ERROR
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);

	    // Stel de titel en de boodschap van de Alert in
	    alert.setTitle(bundle.getString("alertMelding"));
	    alert.setHeaderText(null);
	    alert.setContentText(bundle.getString(sre.getMessage()));
	    // Toon de Alert
	    alert.showAndWait();
	}

    }

    public void vraagBevestiging(Ontwikkelingskaart gekozenKaart) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(bundle.getString("koopKaart"));
	alert.setHeaderText(bundle.getString("uSure"));

	// Toon de kaart nog eens
	Image image = new Image(getClass().getResourceAsStream(String.format("/images/Ontwikkelingskaarten/%s.png", gekozenKaart.getImg())));
	ImageView imageView = new ImageView(image);
	imageView.setFitHeight(155);
	imageView.setFitWidth(105);

	// foto instellen
	alert.setGraphic(imageView);

	// Verander de tekst van de knoppen
	ButtonType jaButton = new ButtonType(bundle.getString("ja"), ButtonData.OK_DONE);
	ButtonType neeButton = new ButtonType(bundle.getString("nee"), ButtonData.CANCEL_CLOSE);
	alert.getButtonTypes().setAll(jaButton, neeButton);
	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == jaButton) {
	    try {
		dc.neemKaart(niveau - 1, lijst.indexOf(gekozenKaart));
	    } catch (EdeleException ee) {
		spelScherm.kiesEdele(ee.getEdelen());
	    }
	    // spelScherm.edeleGekozen();
	}
    }
}
