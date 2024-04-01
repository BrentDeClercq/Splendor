package testen;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import exceptions.LoginException;
import exceptions.SpelRegelException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.DomeinController;
import domein.Edele;
import domein.Spel;
import domein.Speler;

class DomeinControllerTest {
    DomeinController dc;

    @BeforeEach
    public void before() {
	dc = new DomeinController();
    }

    @ParameterizedTest
    @ValueSource(strings = { "Kenji", "Brent", "Bob", "Nobel" })
    void logSpelerIn_spelerAlIngelogd_throwtException(String naam) {
	dc.logSpelerIn(naam, 2004);
	Speler speler = new Speler(naam, 2004);
	assertThrows(LoginException.class, () -> dc.logSpelerIn(naam, 2004));
    }

    @ParameterizedTest
    @ValueSource(strings = { "strata", "ekko", "rosa", "rico" })
    void logSpelerIn_spelerBestaatNiet_throwtException(String naam) {
	// dc.logSpelerIn(naam, 2004);
	Speler speler = new Speler(naam, 2004);
	assertThrows(LoginException.class, () -> dc.logSpelerIn(naam, 2004));
    }

    @Test
    void minSpelersIngelogdBereikt_minAantalBereikt_returnTrue() {
	dc.logSpelerIn("Liam", 2002);
	dc.logSpelerIn("Kenji", 2004);
	assertTrue(dc.minSpelersIngelogdBereikt());
    }

    @Test
    void minSpelersIngelogdBereikt_minAantalNietBereikt_returnFalse() {
	dc.logSpelerIn("Kenji", 2004);
	assertFalse(dc.minSpelersIngelogdBereikt());
    }

    @Test
    void maxSpelersIngelogdBereikt_maxAantalBereikt_returnTrue() {
	dc.logSpelerIn("Kenji", 2004);
	dc.logSpelerIn("Liam", 2002);
	dc.logSpelerIn("Brent", 2004);
	dc.logSpelerIn("Bob", 2004);
	assertTrue(dc.maxSpelersIngelogdBereikt());
    }

    @Test
    void maxSpelersIngelogdBereikt_maxAantalNietBereikt_returnFalse() {
	dc.logSpelerIn("Nobel", 2004);
	dc.logSpelerIn("Brent", 2004);
	dc.logSpelerIn("Bob", 2004);
	assertFalse(dc.maxSpelersIngelogdBereikt());
    }

    @Test
    void neemFiches_3dezelfde_throwtException() {
	dc.logSpelerIn("Kenji", 2004);
	dc.logSpelerIn("Nobel", 2004);
	dc.logSpelerIn("Brent", 2004);
	dc.logSpelerIn("Bob", 2004);
	dc.nieuwSpel();
	List<Integer> fiches = new ArrayList<>();
	fiches.add(3);
	fiches.add(2);
	fiches.add(2);
	assertThrows(SpelRegelException.class, () -> dc.neemFiches(fiches));
    }

}
