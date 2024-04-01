package spelkaarten;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

import domein.Edele;
import domein.Edelsteen;

public class EdeleMapper {
    public static List<Edele> geefAlleEdelen() {
	List<Edele> edelen = new LinkedList<>();

	HashMap<Edelsteen, Integer> vereisteBonus;

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 4);
	vereisteBonus.put(Edelsteen.WIT, 4);
	edelen.add(new Edele(vereisteBonus, 3, "Edele1"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 3);
	vereisteBonus.put(Edelsteen.ROOD, 3);
	vereisteBonus.put(Edelsteen.WIT, 3);
	edelen.add(new Edele(vereisteBonus, 3, "Edele2"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 3);
	vereisteBonus.put(Edelsteen.BLAUW, 3);
	vereisteBonus.put(Edelsteen.WIT, 3);
	edelen.add(new Edele(vereisteBonus, 3, "Edele3"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.GROEN, 3);
	vereisteBonus.put(Edelsteen.BLAUW, 3);
	vereisteBonus.put(Edelsteen.WIT, 3);
	edelen.add(new Edele(vereisteBonus, 3, "Edele4"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 3);
	vereisteBonus.put(Edelsteen.ROOD, 3);
	vereisteBonus.put(Edelsteen.GROEN, 3);
	edelen.add(new Edele(vereisteBonus, 3, "Edele5"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.GROEN, 3);
	vereisteBonus.put(Edelsteen.BLAUW, 3);
	vereisteBonus.put(Edelsteen.ROOD, 3);
	edelen.add(new Edele(vereisteBonus, 3, "Edele6"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.BLAUW, 4);
	vereisteBonus.put(Edelsteen.GROEN, 4);
	edelen.add(new Edele(vereisteBonus, 3, "Edele7"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ZWART, 4);
	vereisteBonus.put(Edelsteen.ROOD, 4);
	edelen.add(new Edele(vereisteBonus, 3, "Edele8"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.BLAUW, 4);
	vereisteBonus.put(Edelsteen.WIT, 4);
	edelen.add(new Edele(vereisteBonus, 3, "Edele9"));

	vereisteBonus = new HashMap<Edelsteen, Integer>();
	vereisteBonus.put(Edelsteen.ROOD, 4);
	vereisteBonus.put(Edelsteen.GROEN, 4);
	edelen.add(new Edele(vereisteBonus, 3, "Edele10"));

	return edelen;
    }
}
