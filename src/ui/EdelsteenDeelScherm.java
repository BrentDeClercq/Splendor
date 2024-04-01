package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
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

public class EdelsteenDeelScherm extends HBox {

    private DomeinController dc;
    private ArrayList<Integer> edelstenen;
    private SpelScherm spelScherm;
    private ResourceBundle bundle;

    public EdelsteenDeelScherm(DomeinController dc, SpelScherm spelScherm, ResourceBundle bundle) {
	this.dc = dc;
	this.bundle = bundle;
	this.spelScherm = spelScherm;
	edelstenen = new ArrayList<>();
	this.setAlignment(Pos.CENTER);
	this.setSpacing(10);
	buildGui();
    }

    public List<Integer> getEdelstenen() {
	return this.edelstenen;
    }

    private Button getEdelsteenButton(int soort) {
	Image imgEdelsteen = new Image(getClass().getResourceAsStream("/images/edelstenen/Picture" + (soort + 1) + ".png"));

	ImageView imvEdelsteen = new ImageView(imgEdelsteen);
	imvEdelsteen.setFitHeight(75.5);
	imvEdelsteen.setFitWidth(75.5);

	Button btnEdelsteen = new Button();
	btnEdelsteen.setGraphic(imvEdelsteen);
	btnEdelsteen.setOnAction(e -> voegEdelsteenToe(e, soort));
	return btnEdelsteen;
    }

    private void buildGui() {
	this.getChildren().clear();
	for (int i = 0; i < 5; i++) {
	    Button btnEdelsteen = getEdelsteenButton(i);
	    String edelsteenAmounts = Integer.toString(Collections.frequency(this.edelstenen, i)) + "/" + dc.getSpelDTO().edelsteenFiches().get(i);
	    btnEdelsteen.setText(edelsteenAmounts);
	    this.getChildren().add(btnEdelsteen);
	}
    }

    public void voegEdelsteenToe(ActionEvent e, int i) {
	this.edelstenen.add(i);
	this.buildGui();
	try {
	    if (dc.geldigeFichesTeNemen(this.edelstenen)) {
		vraagBevestiging();
	    }

	} catch (SpelRegelException sre) {
	    this.leegEdelstenen();
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(bundle.getString("alertMelding"));
	    alert.setHeaderText(null);
	    alert.setContentText(bundle.getString("notAllowed") + bundle.getString(sre.getMessage()));
	    alert.showAndWait();
	}
    }

    public void vraagBevestiging() {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(bundle.getString("neemSteen"));
	alert.setHeaderText(bundle.getString("uSureGem"));
	HBox fotos = new HBox();

	for (int e : edelstenen) {
	    // Toon de edelstenen nog eens
	    Image image = new Image(getClass().getResourceAsStream(String.format("/images/edelstenen/Picture" + (e + 1) + ".png")));
	    ImageView imageView = new ImageView(image);
	    imageView.setFitHeight(60);
	    imageView.setFitWidth(60);
	    fotos.getChildren().add(imageView);
	}

	// foto's instellen
	alert.getDialogPane().setContent(fotos);

	// Verander de tekst van de knoppen
	ButtonType jaButton = new ButtonType(bundle.getString("ja"), ButtonData.OK_DONE);
	ButtonType neeButton = new ButtonType(bundle.getString("nee"), ButtonData.CANCEL_CLOSE);
	alert.getButtonTypes().setAll(jaButton, neeButton);

	// Voer iets uit op bassis van het antwoord
	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == jaButton) {
	    try {
		dc.neemFiches(this.edelstenen);
	    } catch (EdeleException ee) {
		spelScherm.kiesEdele(ee.getEdelen());
	    }
	    // spelScherm.edeleGekozen();
	    this.leegEdelstenen();
	}
	spelScherm.updateScherm();
    }

    public void leegEdelstenen() {
	this.edelstenen = new ArrayList<>();
	this.buildGui();
    }
}
