package ui;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WinnaarsScherm extends VBox {

    private DomeinController dc;

    public WinnaarsScherm(DomeinController dc) {

	// achtergrondkleur instellen
	this.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #f2ebdf, #c9c4b6, #f2ebdf);");

	this.dc = dc;
	buildGui();
    }

    private void buildGui() {
	String winnaarTekst = "";
	Label lblWinnaarTekst = new Label();

	winnaarTekst += String.format("De %s", dc.getWinnaars().size() == 1 ? "winnaar is: " : "winnaars zijn: ");

	for (SpelerDTO winnaar : dc.getWinnaars()) {
	    winnaarTekst += String.format("%s (%d)%n", winnaar.gebruikersnaam(), winnaar.geboortejaar());
	}

	lblWinnaarTekst.setText(winnaarTekst);
	lblWinnaarTekst.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	// overzicht ingegeven speler
	Label lblOverzicht = new Label(String.format("Spel overzicht"));
	lblOverzicht.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	Label lblSpeler = new Label();
	String overzicht = "";
	for (SpelerDTO speler : dc.getSpelDTO().spelers()) {
	    overzicht += String.format("Speler: %s had %d prestige punten%n", speler.gebruikersnaam(), speler.prestige());
	}
	lblSpeler.setText(overzicht);
	lblSpeler.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));

	Image image = new Image(getClass().getResourceAsStream("/images/splendor1.png"));
	ImageView vStapel = new ImageView(image);
	vStapel.setFitHeight(330);
	vStapel.setFitWidth(680);

	Label lblWitRuimte = new Label(String.format("%n"));

	this.getChildren().addAll(lblWinnaarTekst, lblOverzicht, lblSpeler, lblWitRuimte, vStapel);

    }

}
