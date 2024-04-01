package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Edele;
import domein.Edelsteen;

public class EdeleTest {

    private HashMap<Edelsteen, Integer> vereisteBonus;
    private int prestige = 3;
    private String img = "Edele01";

    @BeforeEach
    public void before() {
	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 4);
	vereisteBonus.put(Edelsteen.GROEN, 0);
	vereisteBonus.put(Edelsteen.ROOD, 0);
	vereisteBonus.put(Edelsteen.WIT, 4);
	vereisteBonus.put(Edelsteen.BLAUW, 0);
    }

    @Test
    void maakEdele_GeldigeWaarden_MaaktEdele() {

	Edele edele = new Edele(vereisteBonus, prestige, img);
	assertEquals(vereisteBonus, edele.getVereisteBonussen());
	assertEquals(img, edele.getImg());
	assertEquals(prestige, edele.getPrestige());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, -30 })
    void maakEdele_OngeldigePrestige_ThrowError(int fPrestige) {
	assertThrows(IllegalArgumentException.class, () -> new Edele(vereisteBonus, fPrestige, img));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "", "  " })
    void maakEdele_OngeldigeImg_ThrowError(String fImg) {
	assertThrows(IllegalArgumentException.class, () -> new Edele(vereisteBonus, prestige, fImg));
    }
}
