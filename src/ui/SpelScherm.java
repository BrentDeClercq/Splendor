package ui;

import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.EdeleDTO;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SpelScherm extends BorderPane {

    private DomeinController dc;
    private VBox midden;
    private EdelsteenDeelScherm esds;
    private EdelenDeelScherm eds;
    private ResourceBundle bundle;
    private boolean cheat = false;

    public SpelScherm(DomeinController dc, ResourceBundle bundle) {
	this.dc = dc;
	this.bundle = bundle;

	// css instellen
	this.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
	this.getStyleClass().add("borderpane");

	// this.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #f2ebdf, #c9c4b6, #f2ebdf);");

	// this.setStyle("-fx-padding: 10px;");

	eds = new EdelenDeelScherm(dc, bundle);
	midden = new VBox();
	midden.setSpacing(10);
	// midden.setStyle("-fx-background-color: #c5c0b3;");

	this.setPadding(new Insets(25, 0, 25, 0));

	buildGui();
    }

    private void buildGui() {

	// leg de Edelen, Ontwikkelingskaarten en Edelsteenfiches
	vulMidden();

	// overzicht van de spelers en wie aan de beurt is
	overzichtSpelers();

	// toon wat de speler heeft
	spelerInventory();
    }

    public void updateScherm() {
	midden.getChildren().removeAll(midden.getChildren());

	vulMidden();
	overzichtSpelers();
	spelerInventory();
	bepaalWinnaarsEnToonZe();
    }

    private void spelerInventory() {
	SpelerInventory spelerInv = new SpelerInventory(dc, bundle);
	spelerInv.getStyleClass().add("vbox");
	spelerInv.getStylesheets().add(getClass().getResource("/css/SpelerInventory.css").toExternalForm());
	spelerInv.setAlignment(Pos.CENTER);

	// einde beurt knop
	Button eindeBeurt = new Button(bundle.getString("endTurn"));
	eindeBeurt.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	eindeBeurt.setOnAction(this::beindigBeurt);

	Button cheatKnop = new Button(bundle.getString("cheat"));
	cheatKnop.setFont(Font.font("Comic sans", FontWeight.NORMAL, 14));
	cheatKnop.setOnAction(this::cheat);

	spelerInv.getChildren().addAll(eindeBeurt, cheatKnop);

	SpelerInventory.setMargin(eindeBeurt, new Insets(200, 0, 0, 0));

	this.setLeft(spelerInv);

    }

    private void overzichtSpelers() {
	OverzichtSpelers overzichtSpelers = new OverzichtSpelers(dc, bundle);
	overzichtSpelers.getStyleClass().add("vbox");
	overzichtSpelers.getStylesheets().add(getClass().getResource("/css/overzichtSpelers.css").toExternalForm());
	overzichtSpelers.setAlignment(Pos.CENTER);
	this.setRight(overzichtSpelers);
    }

    private void vulMidden() {
	legEdelen();
	legOntwikkelingskaarten();
	legEdelsteenFiches();

	this.setCenter(midden);
    }

    private void cheat(ActionEvent event) {
	dc.cheatSpel();
	cheat = true;
	bepaalWinnaarsEnToonZe();
    }

    private void bepaalWinnaarsEnToonZe() {
	if (!dc.getWinnaars().isEmpty() && dc.spel.rondeAfVoorWinnaars(cheat)) {
	    WinnaarsScherm w = new WinnaarsScherm(dc);

	    Scene scene = new Scene(w, 700, 500);
	    Stage stage = (Stage) this.getScene().getWindow();
	    stage.setScene(scene);

	    // scherm centreren
	    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
	    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

	    stage.show();
	}
    }

    private void beindigBeurt(ActionEvent event) {

	bepaalWinnaarsEnToonZe();

	esds.leegEdelstenen();

	dc.volgendeSpeler();

	overzichtSpelers();

	spelerInventory();

	updateScherm();

    }

    private void legEdelen() {
	eds.buildGui();
	midden.getChildren().add(eds);
    }

    private void legOntwikkelingskaarten() {

	for (int i = 3; i > 0; i--) {
	    midden.getChildren().add(new OntwikkelingskaartDeelScherm(i, dc, this, bundle));
	}
    }

    private void legEdelsteenFiches() {
	esds = new EdelsteenDeelScherm(dc, this, bundle);

	midden.getChildren().add(esds);
    }

    private Button maakEdeleBtn(EdeleDTO edele, int pos) {
	Button btnEdele = new Button();
	btnEdele.setGraphic(eds.maakEdeleImg(edele));
	btnEdele.setOnAction(e -> {
	    dc.kiesEdele(pos);
	});
	return btnEdele;
    }

    public void kiesEdele(List<EdeleDTO> edelen) {
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	alert.setTitle(bundle.getString("chooseNoble")); // TODO
	alert.setHeaderText(null);
	HBox edelenView = new HBox();
	for (int i = 0; i < edelen.size(); i++) {
	    edelenView.getChildren().add(maakEdeleBtn(edelen.get(i), i));
	}
	alert.showAndWait();
    }
}
