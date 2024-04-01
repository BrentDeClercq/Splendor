package domein;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import dto.SpelDTO;
import dto.SpelerDTO;
import exceptions.EdeleException;
import exceptions.LoginException;
import exceptions.SpelRegelException;

public class DomeinController {

    public Spel spel;

    private static final int MAX_SPELERS = 4;
    private static final int MIN_SPELERS = 2;
    private List<Speler> spelers;
    private SpelerRepository spelerRepo;
    private Speler eersteSpeler;
    private ResourceBundle bundle;

    public DomeinController() {
	spelerRepo = new SpelerRepository();
	spelers = new ArrayList<>();
    }

    public void volgendeSpeler() {
	spel.volgendeSpeler();
    }

    public void setBundle(ResourceBundle bundle) {
	this.bundle = bundle;
    }

    public ArrayList<Edele> geefEdelen() {
	return spel.getEdelen();
    }

    // geef Ontwikkelingskaartveld terug
    public ArrayList<ArrayList<Ontwikkelingskaart>> geefOntwikkelingskaartVeld() {
	return spel.getOntwikkelingskaartVeld();
    }

    // maakt nieuw spel aan
    public ArrayList<SpelerDTO> nieuwSpel() {
	spel = new Spel(new ArrayList<>(this.sorteerSpelersOpSpelvolgorde()));
	return this.getSpelDTO().spelers();
    }

    // sorteert spelers en retourneert ze
    private List<Speler> sorteerSpelersOpSpelvolgorde() {
	eersteSpeler = spelers.stream().sorted(Comparator.comparing(Speler::getGeboortejaar, Comparator.reverseOrder()).thenComparing(Speler::getGebruikersnaamLengte, Comparator.reverseOrder()).thenComparing(Speler::getGebruikersnaam, Comparator.reverseOrder())).findFirst().orElse(null);
	while (!this.spelers.get(0).equals(eersteSpeler)) {
	    this.spelers.add(this.spelers.remove(0));
	}
	return spelers;
    }

    public String bepaalEersteSpeler() {
	return eersteSpeler.getGebruikersnaam();
    }

    // maakt een speler wanneer de ingegeven waarde geldig zijn
    public void logSpelerIn(String gebruikersnaam, int geboortejaar) {
	Speler speler = new Speler(gebruikersnaam, geboortejaar);
	boolean zitErAlIn = false;
	for (Speler s : spelers) {
	    if (speler.getGebruikersnaam().equals(s.getGebruikersnaam()) && speler.getGeboortejaar() == s.getGeboortejaar()) {
		zitErAlIn = true;
	    }

	}

	try {
	    if (spelerRepo.controleerSpeler(speler)) {
		if (!zitErAlIn) {
		    this.spelers.add(speler);
		} else {
		    throw new LoginException("ingelogdTRUE"); // speler is al ingelogd }
		}
	    } else {
		throw new LoginException("Speler404"); // speler is niet gevonden
	    }

	} catch (LoginException le) {
	    throw le;

	}
    }

    public int getAantalSpelersIngelogd() {
	return spelers.size();
    }

    public boolean minSpelersIngelogdBereikt() {
	return this.getAantalSpelersIngelogd() >= MIN_SPELERS;
    }

    public boolean maxSpelersIngelogdBereikt() {
	return this.getAantalSpelersIngelogd() >= MAX_SPELERS;
    }

    public List<SpelerDTO> getWinnaars() {
	return spel.getWinnaars().stream().map(s -> new SpelerDTO(s)).toList();
    }

    public SpelDTO getSpelDTO() {
	return new SpelDTO(spel);
    }

    // vormt fichesDTO naar fichesHashMap en geeft ze door aan spel
    public void neemFiches(List<Integer> fichesInts) {
	try {
	    HashMap<Edelsteen, Integer> fichesHashMap = Edelsteen.getNewMap(0);
	    spel.geldigeFichesTeNemen(fichesInts.stream().map(fiche -> Edelsteen.toSoort(fiche)).toList());
	    for (int soortInt : fichesInts) {
		Edelsteen soort = Edelsteen.toSoort(soortInt);
		fichesHashMap.put(soort, fichesHashMap.get(soort) + 1);
	    }
	    spel.neemFiches(fichesHashMap);
	} catch (SpelRegelException sre) {
	    throw sre;
	} catch (EdeleException ee) {
	    throw ee;
	}
    }

    // validatie en doorgeven aan spel
    public void neemKaart(int rij, int kol) {
	try {
	    spel.geldigeKaartTeNemen(rij, kol);
	    spel.neemKaart(rij, kol);
	} catch (SpelRegelException sre) {
	    throw sre;
	} catch (EdeleException ee) {
	    throw ee;
	}
    }

    // doorgave aan spel, geldige zet -> true | kan geldig worden -> false | ongeldig -> throw
    public boolean geldigeFichesTeNemen(List<Integer> fichesInts) {
	try {
	    return spel.geldigeFichesTeNemen(fichesInts.stream().map(fiche -> Edelsteen.toSoort(fiche)).toList());
	} catch (SpelRegelException sre) {
	    throw sre;
	}
    }

    public boolean spelUit() {
	return spel.spelUit();
    }

    public void cheatSpel() {
	spel.cheatVoorDemo();
    }

    public void kiesEdele(int pos) {
	spel.kiesEdele(pos);
    }
}