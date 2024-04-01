package domein;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import exceptions.LoginException;

public class Speler {
    private String gebruikersnaam;
    private int geboortejaar;
    private ArrayList<Edele> edelen;
    private ArrayList<Ontwikkelingskaart> ontwikkelingskaarten;
    private HashMap<Edelsteen, Integer> edelsteenFiches;
    public static final int OUDSTE_PERSOON_TER_WERELD_LEEFTIJD = 119;

    public Speler(String gebruikersnaam, int geboortejaar) {
	setGebruikersnaam(gebruikersnaam);
	setGeboortejaar(geboortejaar);
	this.edelen = new ArrayList<Edele>();
	this.ontwikkelingskaarten = new ArrayList<Ontwikkelingskaart>();
	this.edelsteenFiches = Edelsteen.getNewMap(0);
    }

    public String getGebruikersnaam() {
	return gebruikersnaam;
    }

    public int getGeboortejaar() {
	return geboortejaar;
    }

    public int getGebruikersnaamLengte() {
	return gebruikersnaam.length();
    }

    private void setGebruikersnaam(String gebruikersnaam) {
	// controleren of de gebruikersnaam leeg is
	if (gebruikersnaam == null || gebruikersnaam.isBlank())
	    throw new LoginException("invalidUsername1");

	// controleren of de gebruikersnaam start met een letter
	char eersteLetter = gebruikersnaam.charAt(0);
	if (!((eersteLetter >= 97 && eersteLetter <= 122) || (eersteLetter >= 65 && eersteLetter <= 90)))
	    throw new LoginException("invalidUsername2");

	// controleren of de gebruikersnaam illegale speciale tekens bevat (_underscore mag wel)
	String regex = "^[\\p{L}0-9_ ]+$"; // we doen dit via reguliere expressies #chatGPT for the win
	// extra uitleg: https://i.ibb.co/TcB627j/Screenshot-2023-03-26-171338.png
	if (!gebruikersnaam.matches(regex))
	    throw new LoginException("invalidUsername3");
	else
	    this.gebruikersnaam = gebruikersnaam;
    }

    private void setGeboortejaar(int geboortejaar) {

	int huidigJaar = Year.now().getValue();

	// Speler is (hoogst waarschijnlijk) dood
	if ((huidigJaar - OUDSTE_PERSOON_TER_WERELD_LEEFTIJD) - geboortejaar > 0)
	    throw new LoginException("invalidBirthYear1");

	// jaar dat in de toekomst ligt
	if (huidigJaar - geboortejaar < 0)
	    throw new LoginException("invalidBirthYear2");
	// speler moet ouder dan 6 jaar zijn
	if (huidigJaar - geboortejaar < 6)
	    throw new LoginException("invalidBirthYear3");

	else
	    this.geboortejaar = geboortejaar;
    }

    // TODO verkrijgingsmethodes voor ontwikkelingskaarten (met fiches) en edelen (met bonussen, let op je verliest die bonussen niet)
    public ArrayList<Edele> getEdelen() {
	return this.edelen;
    }

    public ArrayList<Ontwikkelingskaart> getOntwikkelingskaarten() {
	return this.ontwikkelingskaarten;
    }

    public HashMap<Edelsteen, Integer> getEdelsteenFiches() {
	return this.edelsteenFiches;
    }

    public int getPrestige() {
	int prestige = 0;
	for (Ontwikkelingskaart kaart : ontwikkelingskaarten) {
	    prestige += kaart.getPrestige();
	}
	for (Edele edele : edelen) {
	    prestige += edele.getPrestige();
	}
	return prestige;
    }

    // Retourneerd totaal aantal bonussen verkregen door ontwikkelingskaarten
    public HashMap<Edelsteen, Integer> getBonussen() {
	HashMap<Edelsteen, Integer> bonussen = Edelsteen.getNewMap(0);

	for (Ontwikkelingskaart kaart : ontwikkelingskaarten) {
	    bonussen.put(kaart.getBonus(), 1 + bonussen.get(kaart.getBonus()));
	}
	return bonussen;
    }

    public int getAantalOntwikkelingskaarten() {
	return this.ontwikkelingskaarten.size();
    }

    // retourneerd false als deze speler niet de gegeven kaart kan kopen, anders true
    public boolean heeftGenoegFichesVoorOntwikkelingskaart(Ontwikkelingskaart kaart) {
	HashMap<Edelsteen, Integer> fichesEnBonussen = fichesEnBonussen();
	HashMap<Edelsteen, Integer> vereisteFiches = kaart.getVereisteFiches();

	for (Edelsteen edelsteen : vereisteFiches.keySet()) {
	    int fichesVanSoort = fichesEnBonussen.get(edelsteen);
	    int vereisteFichesVanSoort = vereisteFiches.get(edelsteen);
	    if (fichesVanSoort < vereisteFichesVanSoort) {
		return false;
	    }
	}
	return true;
    }

    private HashMap<Edelsteen, Integer> fichesEnBonussen() {
	HashMap<Edelsteen, Integer> fiches = this.getEdelsteenFiches();
	HashMap<Edelsteen, Integer> bonussen = this.getBonussen();

	HashMap<Edelsteen, Integer> fichesEnBonussen = new HashMap<>();

	// combineer de keys van beide hashmaps in een set
	Set<Edelsteen> keys = new HashSet<>();
	keys.addAll(fiches.keySet());
	keys.addAll(bonussen.keySet());

	// loop door de set en tel de waarden op
	for (Edelsteen key : keys) {
	    int value1 = fiches.getOrDefault(key, 0);
	    int value2 = bonussen.getOrDefault(key, 0);
	    fichesEnBonussen.put(key, value1 + value2);
	}
	return fichesEnBonussen;
    }

    // retourneerd false als deze speler niet de edele kan verkrijgen, anders true
    public boolean heeftGenoegBonussenVoorEdele(Edele edele) {
	HashMap<Edelsteen, Integer> bonussen = this.getBonussen();
	HashMap<Edelsteen, Integer> vereisteBonussen = edele.getVereisteBonussen();

	for (Edelsteen edelsteen : vereisteBonussen.keySet()) {
	    if (bonussen.get(edelsteen) < vereisteBonussen.get(edelsteen)) {
		return false;
	    }
	}
	return true;
    }

    public void voegKaartToe(Ontwikkelingskaart kaart) {
	this.ontwikkelingskaarten.add(kaart);
    }

    public void voegEdeleToe(Edele edele) {
	this.edelen.add(edele);
    }

    public void voegEdelsteenFichesToe(HashMap<Edelsteen, Integer> fiches) {
	for (Edelsteen soort : fiches.keySet()) {
	    this.edelsteenFiches.put(soort, this.edelsteenFiches.get(soort) + fiches.get(soort));
	}
    }

    public void verwijderEdelsteenFiches(HashMap<Edelsteen, Integer> fiches) {
	for (Edelsteen soort : fiches.keySet()) {
	    this.edelsteenFiches.put(soort, this.edelsteenFiches.get(soort) - fiches.get(soort));
	}
    }
}
