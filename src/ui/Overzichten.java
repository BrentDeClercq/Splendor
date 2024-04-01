package ui;

import java.util.ArrayList;
import dto.EdeleDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelDTO;
import dto.SpelerDTO;

// eens proberen op functioneel programmerende wijze opnieuw te maken mss
public class Overzichten {

    private static final boolean OMIT_ZERO_BIJ_SPELKAARTEN = true;

    // algemeen overzicht voor cui
    public static String geefOverzicht(SpelDTO spel) {
	return String.format(
		"%n================VELD================%n" + "Edelen------------------ %n%s%n%n" + "Ontwikkelingskaarten---- %n%s%n%n" + "Edelsteenfiches--------- %n%s%n%n" + "================SPEL================%n" + "Aan de beurt: %s%n%n" + "===============SPELER===============%n" + "%s%n",
		overzichtEdelen(spel.edelen()), overzichtOntwikkelingskaartVeld(spel.ontwikkelingskaartVeld()), overzichtEdelsteen(spel.edelsteenFiches(), false), spel.spelerAanBeurt(), overzichtSpeler(spel.spelerAanBeurt()));
    }

    // overzicht ingegeven speler
    private static Object overzichtSpeler(SpelerDTO speler) {
	return String.format("Speler: %s%n%n" + "Prestige: %d%n%n" + "Bonussen---------------- %n%s%n" + "Edelsteenfiches--------- %n%s%n%n", speler.gebruikersnaam(), speler.prestige(), overzichtEdelsteen(speler.bonussen(), false), overzichtEdelsteen(speler.edelsteenFiches(), false));
    }

    // overzicht veld voor cui
    public static String overzichtOntwikkelingskaartVeld(ArrayList<ArrayList<OntwikkelingskaartDTO>> ontwikkelingskaartVeld) {
	String overzichtVeld = "";
	for (int i = 0; i < 3; i++) {
	    ArrayList<OntwikkelingskaartDTO> rij = ontwikkelingskaartVeld.get(i);
	    overzichtVeld += String.format("%n---RIJ %d--- %n%n", i);
	    for (int j = 0; j < rij.size(); j++) {
		OntwikkelingskaartDTO kaart = rij.get(j);
		overzichtVeld += String.format("Ontwikkelingskaart op %d, %d:%n%s%n", i, j, overzichtOntwikkelingskaart(kaart));
	    }
	}
	return overzichtVeld;
    }

    // overzicht ingegeven ontwikkelingskaart
    public static String overzichtOntwikkelingskaart(OntwikkelingskaartDTO kaart) {
	return String.format("Prestige: %d%n" + "Bonus: %s%n" + "Vereiste fiches: %s%n%n", kaart.prestige(), edelsteenString(kaart.bonusSoort()), overzichtEdelsteen(kaart.vereisteFiches(), OMIT_ZERO_BIJ_SPELKAARTEN));
    }

    // overzicht ingegeven edelen
    public static String overzichtEdelen(ArrayList<EdeleDTO> edelen) {
	String overzichtEdelen = "";
	for (int i = 0; i < edelen.size(); i++) {
	    EdeleDTO edele = edelen.get(i);
	    overzichtEdelen += String.format("Edele: %s%n", overzichtEdele(edele));
	}
	return overzichtEdelen;
    }

    // overzicht ingegeven edele
    public static String overzichtEdele(EdeleDTO edele) {
	return String.format("Prestige: %d%n" + "Vereiste bonussen: %s%n", edele.prestige(), overzichtEdelsteen(edele.vereisteBonussen(), OMIT_ZERO_BIJ_SPELKAARTEN));
    }

    public static String overzichtEdelsteen(ArrayList<Integer> edelstenen, boolean omitZero) {
	String overzichtEdelsteen = "";

	for (int soort = 0; soort < edelstenen.size(); soort++) {
	    int aantal = edelstenen.get(soort);
	    if (!omitZero || aantal != 0) {
		overzichtEdelsteen += String.format("%s: %d, ", edelsteenString(soort), aantal);
	    }
	}

	return overzichtEdelsteen.substring(0, overzichtEdelsteen.length() - 2);
    }

    public static String edelsteenString(int bonusSoort) {
	switch (bonusSoort) {
	    case 0:
		return "zwart";
	    case 1:
		return "wit";
	    case 2:
		return "rood";
	    case 3:
		return "groen";
	    case 4:
		return "blauw";
	    default:
		throw new IllegalArgumentException("edelsteenString fout");
	}
    }
}
