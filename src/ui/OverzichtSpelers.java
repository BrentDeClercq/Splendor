package ui;

import java.util.ResourceBundle;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OverzichtSpelers extends VBox {

    private DomeinController dc;
    private ResourceBundle bundle;

    public OverzichtSpelers(DomeinController dc, ResourceBundle bundle) {
	this.dc = dc;
	this.bundle = bundle;
	buildGui();
    }

    private void buildGui() {

	this.setSpacing(10);

	Label lBeurt = new Label(String.format(bundle.getString("spelerBeurt"), dc.getSpelDTO().spelerAanBeurt().gebruikersnaam()));
	lBeurt.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	Label lIsStartSpeler = new Label(String.format(bundle.getString("startSpeler"), (dc.bepaalEersteSpeler().equals(dc.getSpelDTO().spelers().get(0).gebruikersnaam()) ? "" : bundle.getString("niet"))));
	lIsStartSpeler.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	Label txt = new Label(bundle.getString("volgorde"));
	txt.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));

	this.getChildren().addAll(lBeurt, lIsStartSpeler, txt);

	VBox.setMargin(lIsStartSpeler, new Insets(0, 0, 20, 0));

	for (SpelerDTO dto : dc.getSpelDTO().spelers()) {
	    Label lblGebruikersNaam = new Label(dto.gebruikersnaam());
	    lblGebruikersNaam.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	    this.getChildren().add(lblGebruikersNaam);
	}

    }
}
