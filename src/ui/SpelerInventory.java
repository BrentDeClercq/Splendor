package ui;

import java.util.ResourceBundle;

import domein.DomeinController;
import dto.EdeleDTO;
import dto.OntwikkelingskaartDTO;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SpelerInventory extends VBox {

    private DomeinController dc;
    private ResourceBundle bundle;

    public SpelerInventory(DomeinController dc, ResourceBundle bundle) {
	this.dc = dc;
	this.bundle = bundle;
	buildGui();
    }

    private void buildGui() {

	// this.setSpacing(10);

	Label lSpeler = new Label(dc.getSpelDTO().spelerAanBeurt().gebruikersnaam());
	lSpeler.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	lSpeler.setId("speler");

	Label lPrestige = new Label(String.format(bundle.getString("prestige"), dc.getSpelDTO().spelerAanBeurt().prestige()));
	lPrestige.setId("prestige");
	lPrestige.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	this.getChildren().addAll(lSpeler, lPrestige);

	VBox vEdelstenen = new VBox();
	vEdelstenen.setSpacing(10);
	vEdelstenen.setId("edelstenen");

	int teller = 1;
	for (int edelsteen : dc.getSpelDTO().spelerAanBeurt().edelsteenFiches()) {

	    Label lblEdelsteen = new Label(Integer.toString(edelsteen));
	    lblEdelsteen.setId("edelsteen");
	    lblEdelsteen.getStyleClass().add(String.format("edelsteen%d", teller++));

	    Label lblBonus = new Label(String.format("+ %d", dc.getSpelDTO().spelerAanBeurt().bonussen().get(teller - 2)));
	    lblBonus.setId("bonus");

	    HBox edelsteenEnBonus = new HBox();
	    edelsteenEnBonus.setSpacing(10);
	    edelsteenEnBonus.setId("edelstenenEnBonussen");
	    edelsteenEnBonus.getChildren().addAll(lblEdelsteen, lblBonus);

	    vEdelstenen.getChildren().add(edelsteenEnBonus);
	}
	vEdelstenen.setAlignment(Pos.CENTER);
	this.getChildren().add(vEdelstenen);

	/*
	 * //toon de bovenkant van de gekochte ontwikkelingskaarten
	 * for(OntwikkelingskaartDTO o : dc.getSpelDTO().spelerAanBeurt().ontwikkelingskaarten()) {
	 * Image image = new Image(getClass().getResourceAsStream(String.format("/images/Ontwikkelingskaarten/%s.png", o.img())));
	 * 
	 * Rectangle2D viewport = new Rectangle2D(0, 0, image.getWidth(), image.getHeight() * 0.25);
	 * ImageView imageView = new ImageView();
	 * imageView.setImage(image);
	 * imageView.setViewport(viewport);
	 * imageView.setFitWidth(120);
	 * imageView.setFitHeight(40);
	 * 
	 * this.getChildren().add(imageView);
	 * }
	 * 
	 */

	for (EdeleDTO oudBrugge : dc.getSpelDTO().spelerAanBeurt().edelen()) {
	    Image image = new Image(getClass().getResourceAsStream(String.format("/images/edelen/%s.png", oudBrugge.img())));

	    ImageView imageView = new ImageView();
	    imageView.setImage(image);
	    imageView.setFitWidth(120);
	    imageView.setFitHeight(120);

	    this.getChildren().add(imageView);
	}

    }
}
