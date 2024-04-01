package domein;

import java.util.HashMap;

public class Edele {

    private int prestige;
    private HashMap<Edelsteen, Integer> vereisteBonussen;
    private String img;

    public Edele(HashMap<Edelsteen, Integer> vereisteBonussenInp, int prestige, String img) {
	HashMap<Edelsteen, Integer> vereisteBonussen = Edelsteen.getNewMap(0);
	for (Edelsteen soort : vereisteBonussenInp.keySet()) {
	    vereisteBonussen.put(soort, vereisteBonussenInp.get(soort));
	}
	this.vereisteBonussen = vereisteBonussen;
	setPrestige(prestige);
	setImg(img);
    }

    private void setImg(String img) {
	if (img == null || img.isBlank())
	    throw new IllegalArgumentException("Img mag niet leeg zijn");

	this.img = img;
    }

    private void setPrestige(int prestige) {
	if (prestige <= 0)
	    throw new IllegalArgumentException("Prestige moet strikt positief zijn.");
	this.prestige = prestige;
    }

    public int getPrestige() {
	return this.prestige;
    }

    public HashMap<Edelsteen, Integer> getVereisteBonussen() {
	return this.vereisteBonussen;
    }

    public String getImg() {
	return this.img;
    }
}