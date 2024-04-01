package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Edele;
import domein.Edelsteen;
import domein.Ontwikkelingskaart;
import domein.Speler;
import exceptions.LoginException;

class SpelerTest {

    @ParameterizedTest
    @ValueSource(strings = { "Kenji", "bren_t", "Li am", "No_b e3l" })
    void maakSpeler_GeldigeGegebruikersnamen_MaaktSpeler(String gebruikersnaam) {
	assertEquals(gebruikersnaam, new Speler(gebruikersnaam, 2004).getGebruikersnaam());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "25Kenji", "kenj!§i", " kenji" })
    void maakSpeler_OngeldigeGegebruikersnamen_ThrowError(String gebruikersnaam) {
	assertThrows(LoginException.class, () -> new Speler(gebruikersnaam, 2004));
    }

    @ParameterizedTest
    @ValueSource(ints = { 2018, 202, 2040 })
    void maakSpeler_OngeldigeGeboortejaren_ThrowError(int geboortejaar) {
	assertThrows(LoginException.class, () -> new Speler("Kenji", geboortejaar));
    }

    @ParameterizedTest
    @ValueSource(ints = { 2017, 2004, 1904, 1996 })
    void maakSpeler_GeldigeGeboortejaren_maaktSpeler(int geboortejaar) {
	assertEquals(geboortejaar, new Speler("Kenji", geboortejaar).getGeboortejaar());
    }

    @Test
    void voegOntwikkelingsKaartToe_GeldigeKaart_voegtKaartToe() {
	Ontwikkelingskaart o = new Ontwikkelingskaart(1, 1, Edelsteen.ZWART, 0, 0, 0, 0, 4, "lv1k1");
	List<Ontwikkelingskaart> lo = new ArrayList<>();
	lo.add(o);
	Speler kenji = new Speler("Kenji", 2004);
	kenji.voegKaartToe(o);
	assertEquals(lo, kenji.getOntwikkelingskaarten());
    }

    @Test
    void voegEdeleToe_GeldigeKaart_voegtEdeleToe() {
	HashMap<Edelsteen, Integer> vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 4);
	vereisteBonus.put(Edelsteen.WIT, 4);
	Edele e = new Edele(vereisteBonus, 3, "Edele01");
	List<Edele> le = new ArrayList<>();
	le.add(e);
	Speler kenji = new Speler("Kenji", 2004);
	kenji.voegEdeleToe(e);
	assertEquals(le, kenji.getEdelen());
    }

    @Test
    void vraagAantalOntwikkelingskaartenOp_GeenOntwikkelingsKaarten_GeefCorrectAantalOntwikkelingskaartenWeer() {
	Speler s = new Speler("Kenji", 2004);
	assertEquals(0, s.getAantalOntwikkelingskaarten());
    }

    @Test
    void vraagAantalOntwikkelingskaartenOp_GeldigeAantalOntwikkelingsKaarten_GeefCorrectAantalOntwikkelingskaartenWeer() {
	Speler s = new Speler("Kenji", 2004);
	s.voegKaartToe(new Ontwikkelingskaart(1, 1, Edelsteen.ZWART, 0, 0, 0, 0, 4, "lv1k1"));
	assertEquals(1, s.getAantalOntwikkelingskaarten());
    }

    @Test
    void vraagAantalOntwikkelingskaartenOp_GroterAantalOntwikkelingsKaarten_GeefCorrectAantalOntwikkelingskaartenWeer() {
	Speler s = new Speler("Kenji", 2004);
	s.voegKaartToe(new Ontwikkelingskaart(1, 1, Edelsteen.ZWART, 0, 0, 0, 0, 4, "lv1k1"));
	s.voegKaartToe(new Ontwikkelingskaart(1, 0, Edelsteen.BLAUW, 1, 1, 2, 1, 0, "lv1k2"));
	s.voegKaartToe(new Ontwikkelingskaart(1, 0, Edelsteen.BLAUW, 2, 1, 0, 0, 0, "lv1k3"));
	assertEquals(3, s.getAantalOntwikkelingskaarten());
    }

    @Test
    void vraagAantalPrestigePuntenOp_GeenPrestigePunten_GeeftAantalPrestigePuntenWeer() {
	Speler s = new Speler("Kenji", 2004);
	assertEquals(0, s.getPrestige());
    }

    @Test
    void vraagAantalPrestigePuntenOp_PrestigePuntenVanOntwikkelingskaarten_GeeftAantalPrestigePuntenWeer() {
	Speler s = new Speler("Kenji", 2004);
	s.voegKaartToe(new Ontwikkelingskaart(1, 1, Edelsteen.ZWART, 0, 0, 0, 0, 4, "lv1k1"));
	s.voegKaartToe(new Ontwikkelingskaart(1, 0, Edelsteen.BLAUW, 1, 1, 2, 1, 0, "lv1k2"));
	assertEquals(1, s.getPrestige());
    }

    @Test
    void vraagAantalPrestigePuntenOp_PrestigePuntenVanEdelen_GeeftAantalPrestigePuntenWeer() {
	Speler s = new Speler("Kenji", 2004);
	HashMap<Edelsteen, Integer> vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 4);
	vereisteBonus.put(Edelsteen.WIT, 4);
	s.voegEdeleToe(new Edele(vereisteBonus, 3, "Edele01"));
	assertEquals(3, s.getPrestige());
    }

    @Test
    void vraagAantalPrestigePuntenOp_PrestigePuntenVanEdelenEnOntwikkelingskaarten_GeeftAantalPrestigePuntenWeer() {
	Speler s = new Speler("Kenji", 2004);
	s.voegKaartToe(new Ontwikkelingskaart(1, 0, Edelsteen.BLAUW, 2, 1, 0, 0, 0, "lv1k3"));
	HashMap<Edelsteen, Integer> vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 4);
	vereisteBonus.put(Edelsteen.WIT, 4);
	s.voegEdeleToe(new Edele(vereisteBonus, 3, "Edele01"));
	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 3);
	vereisteBonus.put(Edelsteen.BLAUW, 3);
	vereisteBonus.put(Edelsteen.WIT, 3);
	s.voegEdeleToe(new Edele(vereisteBonus, 3, "Edele03"));
	assertEquals(6, s.getPrestige());
    }

}