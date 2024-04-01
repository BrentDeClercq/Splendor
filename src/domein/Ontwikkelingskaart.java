package domein;

import java.util.HashMap;

public class Ontwikkelingskaart {

    private int niveau;
    private HashMap<Edelsteen, Integer> vereisteFiches;
    private int prestige;
    private Edelsteen bonus;
    private String img;

    public Ontwikkelingskaart(int niveau, int prestige, Edelsteen bonus, HashMap<Edelsteen, Integer> vereisteFiches, String img) {
	this.prestige = prestige;
	this.niveau = niveau;
	this.bonus = bonus;
	this.vereisteFiches = vereisteFiches;
	this.img = img;
    }

    public Ontwikkelingskaart(int niveau, int prestige, Edelsteen bonus, int z, int w, int r, int g, int b, String img) {
	this(niveau, prestige, bonus, Ontwikkelingskaart.toEdelsteenHashMap(z, w, r, g, b), img);
    }

    public int getNiveau() {
	return this.niveau;
    }

    public HashMap<Edelsteen, Integer> getVereisteFiches() {
	return this.vereisteFiches;
    }

    public int getPrestige() {
	return this.prestige;
    }

    public Edelsteen getBonus() {
	return this.bonus;
    }

    private static HashMap<Edelsteen, Integer> toEdelsteenHashMap(int z, int w, int r, int g, int b) {
	HashMap<Edelsteen, Integer> edelsteenHashMap = new HashMap<>();
	edelsteenHashMap.put(Edelsteen.ZWART, z);
	edelsteenHashMap.put(Edelsteen.WIT, w);
	edelsteenHashMap.put(Edelsteen.ROOD, r);
	edelsteenHashMap.put(Edelsteen.GROEN, g);
	edelsteenHashMap.put(Edelsteen.BLAUW, b);
	return edelsteenHashMap;
    }

    public String getImg() {
	return this.img;
    }
}