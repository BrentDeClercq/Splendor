package ui;

import javafx.scene.control.Button;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.EdeleDTO;
import dto.SpelDTO;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class EdelenDeelScherm extends HBox {

    private DomeinController dc;
    private ResourceBundle bundle;
    private SpelDTO spel;

    public EdelenDeelScherm(DomeinController dc, ResourceBundle bundle) {
	this.dc = dc;
	this.bundle = bundle;
	this.setAlignment(Pos.CENTER);
	this.setSpacing(10);
	buildGui();
    }

    public void buildGui() {
	this.spel = dc.getSpelDTO();
	this.getChildren().clear();

	for (EdeleDTO edele : this.spel.edelen()) {
	    this.getChildren().add(maakEdeleImg(edele));
	}

    }

    public ImageView maakEdeleImg(EdeleDTO edele) {

	Image edele1 = new Image(getClass().getResourceAsStream(String.format("/images/edelen/%s.png", edele.img())));
	ImageView vEdele = new ImageView(edele1);
	vEdele.setFitHeight(120);
	vEdele.setFitWidth(120);
	return vEdele;
    }
}
