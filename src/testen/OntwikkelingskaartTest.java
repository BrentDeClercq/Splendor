package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domein.Edelsteen;
import domein.Ontwikkelingskaart;

public class OntwikkelingskaartTest {

    private int niveau = 1;
    private int prestige = 1;
    private Edelsteen bonus = Edelsteen.ZWART;

    private String img = "lv1k1";

    @Test
    void maakOntwikkelingskaart_GeldigeWaarden_maaktKaart() {
	Ontwikkelingskaart kaart = new Ontwikkelingskaart(niveau, prestige, bonus, 0, 0, 0, 0, 4, img);

	assertEquals(niveau, kaart.getNiveau());
	assertEquals(prestige, kaart.getPrestige());
	assertEquals(bonus, kaart.getBonus());
	assertEquals(img, kaart.getImg());
    }
}
