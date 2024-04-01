package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domein.Spel;
import domein.Speler;
import exceptions.SpelRegelException;
import domein.Edelsteen;
import domein.Ontwikkelingskaart;

public class SpelTest {
    static Spel newSpel() {
	ArrayList<Speler> spelers = new ArrayList<>();
	spelers.add(new Speler("Liam", 2002));
	spelers.add(new Speler("Kenji", 2004));
	spelers.add(new Speler("Brent", 2004));
	spelers.add(new Speler("Nobel", 2004));
	return new Spel(spelers);
    }

    @Test
    void geldigeFichesTeNemen_geldigDrieVerschillende_returnsTrue() {
	Spel spel = newSpel();
	List<Edelsteen> soorten = Arrays.asList(Edelsteen.values());
	List<Edelsteen> keuzes = new ArrayList<>();

	keuzes.add(Edelsteen.BLAUW);
	keuzes.add(Edelsteen.ZWART);
	keuzes.add(Edelsteen.GROEN);

	assertTrue(spel.geldigeFichesTeNemen(keuzes));
    }

    @Test
    void geldigeFichesTeNemen_geldigDubbel_returnsTrue() {
	Spel spel = newSpel();
	for (Edelsteen soort : Edelsteen.values()) {
	    List<Edelsteen> fiches = new ArrayList<>();
	    fiches.add(soort);
	    fiches.add(soort);
	    assertTrue(spel.geldigeFichesTeNemen(fiches));
	}
    }

    @Test
    void geldigeFichesTeNemen_nogNietGeldig_returnsFalse() {
	Spel spel = newSpel();
	List<Edelsteen> soorten = Arrays.asList(Edelsteen.values());
	List<Edelsteen> keuzes = new ArrayList<>();
	keuzes.add(Edelsteen.BLAUW);
	assertFalse(spel.geldigeFichesTeNemen(keuzes));
    }

    @Test
    void geldigeFichesTeNemen_ongeldigDubbel_throws() {
	Spel spel = newSpel();
	List<Edelsteen> fiches = new ArrayList<>();
	fiches.add(Edelsteen.ZWART);
	fiches.add(Edelsteen.ZWART);
	fiches.add(Edelsteen.WIT);
	assertThrows(SpelRegelException.class, () -> spel.geldigeFichesTeNemen(fiches));
    }

    @Test
    void geldigeFichesTeNemen_ongeldig_throws() {
	Spel spel = newSpel();
	List<Edelsteen> fiches = new ArrayList<>();
	fiches.add(Edelsteen.ZWART);
	fiches.add(Edelsteen.ROOD);
	fiches.add(Edelsteen.ROOD);
	assertThrows(SpelRegelException.class, () -> spel.geldigeFichesTeNemen(fiches));
    }

    @Test
    void neemFiches_fichesGenomen() {
	Spel spel = newSpel();
	HashMap<Edelsteen, Integer> fiches = Edelsteen.getNewMap(0);
	fiches.put(Edelsteen.ZWART, 2);
	HashMap<Edelsteen, Integer> spelFichesToAssert = Edelsteen.getNewMap(5);
	fiches.put(Edelsteen.ZWART, 3);

	spel.neemFiches(fiches);

	assertEquals(spel.getSpelers().get(3).getEdelsteenFiches(), fiches);
    }

    @Test
    void neemKaart_kaartGenomen() {
	Spel spel = newSpel();
	Ontwikkelingskaart kaart = spel.kaartOpPos(0, 0);
	spel.getSpelers().get(0).voegEdelsteenFichesToe(kaart.getVereisteFiches());
	spel.neemKaart(0, 0);

	assertEquals(spel.getSpelers().get(3).getOntwikkelingskaarten().get(0), kaart);

	HashMap<Edelsteen, Integer> spelFichesToAssert = new HashMap<>();
	for (Edelsteen soort : Edelsteen.values()) {
	    spelFichesToAssert.put(soort, 5 + kaart.getVereisteFiches().get(soort));
	}
	assertEquals(spel.getSpelers().get(3).getEdelsteenFiches(), Edelsteen.getNewMap(0));
    }

    static final int ITER_LIMIT = 100;

    @Test
    void winnaars_getWinnaars() {
	// geen manier om vaste spel te maken, checkt enkel of winnaars wordt gemaakt,
	// niet of ze correct is

	Spel spel = newSpel();
	int iter = 0;
	for (Speler speler : spel.getSpelers()) {
	    speler.voegEdelsteenFichesToe(Edelsteen.getNewMap(100));
	}
	while (!spel.spelUit() && iter < ITER_LIMIT) {
	    spel.neemKaart(2, 0);
	}
	assertNotEquals(spel.getWinnaars(), new ArrayList<>());
    }

    @Test
    void geldigeKaartTeNemen_ongeldigeKaart_throws() {
	Spel spel = newSpel();
	assertThrows(SpelRegelException.class, () -> spel.geldigeKaartTeNemen(0, 0));
    }

    @Test
    void geldigeKaartTeNemen_geldigeKaart_returnsTrue() {
	Spel spel = newSpel();
	spel.spelerAanBeurt().voegEdelsteenFichesToe(Edelsteen.getNewMap(100));
	assertTrue(spel.geldigeKaartTeNemen(0, 0));
    }
}
