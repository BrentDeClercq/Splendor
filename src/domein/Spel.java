package domein;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import exceptions.EdeleException;
import exceptions.SpelRegelException;
import spelkaarten.EdeleMapper;
import spelkaarten.OntwikkelingskaartMapper;

public class Spel {
    // spelregels
    private final static int AANTAL_ONTWIKKELINGSKAARTEN_PER_RIJ = 4;
    private final static int PRESTIGE_OM_TE_WINNEN = 15;
    private static final int MIN_FICHES_VOOR_TWEE = 4;

    public ArrayList<Speler> spelers;
    public ArrayList<Edele> beschikbareEdelen;

    // ontwikkelingskaartStapels.get(0) retourneerd ArrayList van stapel kaarten van
    // niveau 1, enzovoort
    private ArrayList<ArrayDeque<Ontwikkelingskaart>> ontwikkelingskaartStapels;

    // gelijkaardig tot stapels, maar dit toont wat er op het veld ligt ipv in de
    // stapels
    private ArrayList<ArrayList<Ontwikkelingskaart>> ontwikkelingskaartVeld;
    private HashMap<Edelsteen, Integer> edelsteenFiches;
    public ArrayList<Edele> edelen;

    // winnaars, standaard leeg
    private List<Speler> winnaars;
    private int beurt;

    // Constructor, geef de spelers in in juiste volgorde
    public Spel(ArrayList<Speler> spelers) {
	this.spelers = spelers;
	this.edelsteenFiches = Edelsteen.getNewMap(spelers.size() == 2 ? 4 : spelers.size() == 3 ? 5 : 7);
	this.plaatsEdelen();
	this.maakNieuwOntwikkelingskaartVeld();
	this.beurt = 0;
	this.winnaars = new ArrayList<>();
    }

    public void volgendeSpeler() {
	revolveSpelers();
	beurt++;
    }

    // aantal spelers
    public int getAantalSpelers() {
	return spelers.size();
    }

    // actieve spelers in volgorde
    public ArrayList<Speler> getSpelers() {
	return spelers;
    }

    // mogelijke fiches te nemen
    public HashMap<Edelsteen, Integer> getEdelsteenFiches() {
	return edelsteenFiches;
    }

    // mogelijke kaarten te nemen
    public ArrayList<ArrayList<Ontwikkelingskaart>> getOntwikkelingskaartVeld() {
	return ontwikkelingskaartVeld;
    }

    // geeft de gedekte stapel
    public ArrayList<ArrayDeque<Ontwikkelingskaart>> getOntwikkelingskaartStapels() {
	return ontwikkelingskaartStapels;
    }

    // edelen in spel
    public ArrayList<Edele> getEdelen() {
	return edelen;
    }

    // retourneerd rondenummer adhv beurtnummer en aantal spelers
    public int getRonde() {
	return (int) Math.floor(this.getBeurt() / this.getAantalSpelers()) + 1;
    }

    // retourneerd beurtnummer
    public int getBeurt() {
	return this.beurt;
    }

    // retourneerd speler aan beurt
    public Speler spelerAanBeurt() {
	return this.getSpelers().get(0);
    }

    // retourneerd true als er winnaars zijn
    public boolean spelUit() {
	if (this.winnaars.size() > 0) {
	    return true;
	}
	return false;
    }

    public List<Speler> getWinnaars() {
	return this.winnaars;
    }

    public Ontwikkelingskaart kaartOpPos(int rij, int kol) {
	return this.ontwikkelingskaartVeld.get(rij).get(kol);
    }

    public boolean geldigeKaartTeNemen(int rij, int kol) {
	if (this.spelerAanBeurt().heeftGenoegFichesVoorOntwikkelingskaart(this.kaartOpPos(rij, kol))) {
	    return true;
	}
	throw new SpelRegelException("nietGenoegException");
    }

    // initieel plaatsen van edelen op het veld
    private void plaatsEdelen() {
	this.edelen = new ArrayList<>();
	List<Edele> alleEdelen = EdeleMapper.geefAlleEdelen();

	Collections.shuffle(alleEdelen);
	for (int i = 0; i < (spelers.size() + 1); i++) {
	    this.edelen.add(alleEdelen.get(i));
	}
    }

    // initieel plaatsen van ontwikkelingskaarten op het veld
    private void maakNieuwOntwikkelingskaartVeld() {
	maakNieuweStapels();
	ArrayList<ArrayList<Ontwikkelingskaart>> veld = new ArrayList<>();
	veld.add(new ArrayList<>());
	veld.add(new ArrayList<>());
	veld.add(new ArrayList<>());

	for (int niveau = 0; niveau < 3; niveau++) {
	    ArrayList<Ontwikkelingskaart> veldRij = new ArrayList<>();
	    ArrayDeque<Ontwikkelingskaart> stapel = this.ontwikkelingskaartStapels.get(niveau);
	    for (int i = 0; i < AANTAL_ONTWIKKELINGSKAARTEN_PER_RIJ; i++) {
		veldRij.add(stapel.pop());
	    }
	    this.ontwikkelingskaartStapels.set(niveau, stapel);
	    veld.set(niveau, veldRij);
	}
	this.ontwikkelingskaartVeld = veld;
    }

    // sorteert alle ontw. kaarten per niveau geschuffelt in
    // this.ontwikkelingskaartStapels
    private void maakNieuweStapels() {
	ArrayList<ArrayDeque<Ontwikkelingskaart>> stapels = new ArrayList<>();
	stapels.add(null);
	stapels.add(null);
	stapels.add(null);

	for (int n = 0; n < 3; n++) {
	    List<Ontwikkelingskaart> kaarten = new ArrayList<>(OntwikkelingskaartMapper.geefOntwikkelingskaartenNiveau(n));
	    Collections.shuffle(kaarten);
	    stapels.set(n, new ArrayDeque<>(kaarten));
	}

	this.ontwikkelingskaartStapels = stapels;
    }

    // neemKaart voert geen validatie uit!!
    // verplaatst de kaart in de ingegeven niveau en positie naar de speler aan
    // beurt
    public void neemKaart(int niveau, int pos) {
	// hou kaart bij
	Ontwikkelingskaart kaart = this.kaartOpPos(niveau, pos);
	// verwijder fiches in speler
	this.spelerAanBeurt().verwijderEdelsteenFiches(berekenKost(kaart));
	// voeg fiches toe in spel
	this.voegEdelsteenFichesToe(berekenKost(kaart));
	// vervang kaart in spel
	this.vervangKaart(niveau, pos);
	// voeg kaart toe in speler
	this.spelerAanBeurt().voegKaartToe(kaart);

	try {
	    this.beurtGedaan();
	} catch (EdeleException ee) {
	    throw ee;
	}
    }

    private HashMap<Edelsteen, Integer> berekenKost(Ontwikkelingskaart kaart) {
	HashMap<Edelsteen, Integer> origineleKost = kaart.getVereisteFiches();
	HashMap<Edelsteen, Integer> bonussen = this.spelerAanBeurt().getBonussen();

	HashMap<Edelsteen, Integer> totaleKost = new HashMap<>();

	// combineer de keys van beide hashmaps in een set
	Set<Edelsteen> keys = new HashSet<>();
	keys.addAll(origineleKost.keySet());
	keys.addAll(bonussen.keySet());

	// loop door de set en tel de waarden op
	for (Edelsteen key : keys) {
	    int value1 = origineleKost.getOrDefault(key, 0);
	    int value2 = bonussen.getOrDefault(key, 0);
	    totaleKost.put(key, (value1 - value2 < 0) ? 0 : value1 - value2);
	}
	return totaleKost;
    }

    // vervangt kaart in veld met bovenste uit passende stapel, verwijder de kaart
    // als de stapel leeg is
    private void vervangKaart(int niveau, int pos) {
	if (this.ontwikkelingskaartStapels.get(niveau).size() <= 0)
	    this.ontwikkelingskaartVeld.get(niveau).remove(this.ontwikkelingskaartVeld.get(niveau).get(pos));

	if (this.ontwikkelingskaartStapels.get(niveau).size() > 0)
	    this.ontwikkelingskaartVeld.get(niveau).set(pos, this.ontwikkelingskaartStapels.get(niveau).pop());
    }

    public void kiesEdele(int pos) {
	this.spelerAanBeurt().voegEdeleToe(this.edelen.remove(this.edelen.indexOf(this.beschikbareEdelen.get(pos))));
    }

    // neemt de eerste geldige kaart voor de speler aan beurt
    private void maakGeldigeEdelen() {
	beschikbareEdelen = new ArrayList<>();
	for (int i = 0; i < this.edelen.size(); i++) {
	    Edele edele = edelen.get(i);
	    if (this.spelerAanBeurt().heeftGenoegBonussenVoorEdele(edele)) {
		beschikbareEdelen = new ArrayList<>();
		beschikbareEdelen.add(edele);
	    }
	}
	if (beschikbareEdelen.size() > 1) {
	    throw new EdeleException(beschikbareEdelen);
	}
	if (beschikbareEdelen.size() == 1) {
	    this.spelerAanBeurt().voegEdeleToe(this.edelen.remove(this.edelen.indexOf(this.beschikbareEdelen.get(0))));
	}
    }

    // retourneerd true als het een geldige zet is, false als het NOG geen geldige
    // zet is, throwt SpelRegelException als het geen geldige zet kan worden (=te
    // veel van een soort)
    public boolean geldigeFichesTeNemen(List<Edelsteen> fichesTeNemen) {
	Set<Edelsteen> fichesTeNemenAlsSet = new HashSet<>(fichesTeNemen);

	// worden duplicaten genomen? ja -> throw of true
	if (fichesTeNemen.size() > fichesTeNemenAlsSet.size()) {
	    if (fichesTeNemen.size() != 2) {
		throw new SpelRegelException("duplicaten");
	    }
	    if (this.getEdelsteenFiches().get(fichesTeNemen.get(0)) < MIN_FICHES_VOOR_TWEE) {
		throw new SpelRegelException("minimum");

	    }
	    return true;
	}
	// ... nee -> geen duplicaten

	// minder dan 3 kaarten? ja -> false
	if (fichesTeNemen.size() < 3) {
	    return false;
	}

	// worden 3 kaarten genomen? -> throw of true
	if (fichesTeNemen.size() == 3) {
	    for (Edelsteen soort : fichesTeNemen) {
		if (this.getEdelsteenFiches().get(soort) <= 0) {
		    throw new SpelRegelException(soort.toString());
		}
	    }
	    return true;
	}

	// als het aan hier komt is er alsinds iets foutgelopen
	throw new RuntimeException();
    }

    // neemFiches voert geen validatie uit!!
    public void neemFiches(HashMap<Edelsteen, Integer> fiches) {
	this.verwijderEdelsteenFiches(fiches);
	this.spelerAanBeurt().voegEdelsteenFichesToe(fiches);
	try {
	    this.beurtGedaan();
	} catch (EdeleException ee) {
	    throw ee;
	}
    }

    private void verwijderEdelsteenFiches(HashMap<Edelsteen, Integer> fiches) {
	for (Edelsteen soort : fiches.keySet()) {
	    this.edelsteenFiches.put(soort, this.edelsteenFiches.get(soort) - fiches.get(soort));
	}
    }

    private void voegEdelsteenFichesToe(HashMap<Edelsteen, Integer> fiches) {
	for (Edelsteen soort : fiches.keySet()) {
	    this.edelsteenFiches.put(soort, this.edelsteenFiches.get(soort) + fiches.get(soort));
	}
    }

    // plaats hier methoden die op het einde van de beurt gedaan worden
    private void beurtGedaan() {
	try {
	    this.maakGeldigeEdelen();
	} catch (EdeleException ee) {
	    throw ee;
	}
	this.rondeAf();
	this.revolveSpelers();
	this.beurt++;
    }

    // volgende speler
    private void revolveSpelers() {
	this.spelers.add(this.spelers.remove(0));
    }

    // roept winnaars op als voorwaarde voldaan is
    private void rondeAf() {
	if (this.getBeurt() % this.getAantalSpelers() == 0) {
	    this.winnaars();
	}
    }

    // maakt winnaars aan
    private void winnaars() {
	this.winnaars = new ArrayList<>();
	List<Speler> kandidaten = spelers.stream().filter(s -> s.getPrestige() >= PRESTIGE_OM_TE_WINNEN).sorted(Comparator.comparing(Speler::getPrestige, Comparator.reverseOrder()).thenComparing(Speler::getAantalOntwikkelingskaarten, Comparator.reverseOrder())).collect(Collectors.toList());

	if (kandidaten.size() >= 1) {
	    // kijkt voor spelers met zelfde winnende waarden als eerste winnaar, wist niet
	    // hoe dit samen in de stream te zetten
	    this.winnaars.add(kandidaten.remove(0));
	    kandidaten.forEach(s -> {
		if (s.getPrestige() == this.winnaars.get(0).getPrestige() && s.getAantalOntwikkelingskaarten() == this.winnaars.get(0).getAantalOntwikkelingskaarten()) {
		    this.winnaars.add(s);
		}
	    });
	}

    }

    public void cheatVoorDemo() {
	this.winnaars.add(spelerAanBeurt());
    }

    public boolean rondeAfVoorWinnaars(boolean cheat) {
	return (this.getBeurt() % this.getAantalSpelers() == 0) || cheat;
    }

    public void edelenRemove(int i) {
	edelen.remove(beschikbareEdelen.get(i));
    }
}
