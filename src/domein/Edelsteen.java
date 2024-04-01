package domein;

import java.util.ArrayList;
import java.util.HashMap;

public enum Edelsteen {
    ZWART, WIT, ROOD, GROEN, BLAUW;

    public static HashMap<Edelsteen, Integer> getNewMap(int n) {
	// gebruik dit om een map te krijgen == elke edelsteen wijst naar integer n, zo kan je impliciet "map.put(Edelsteen.WIT, map.get(Edelsteen.WIT)+1)"
	HashMap<Edelsteen, Integer> map = new HashMap<Edelsteen, Integer>();
	for (Edelsteen edelsteen : Edelsteen.values()) {
	    map.put(edelsteen, n);
	}
	return map;
    }

    // soort naar int voor DTO's, mapping mss mogelijk met java enums?
    public static int toInt(Edelsteen soort) {
	switch (soort) {
	    case ZWART:
		return 0;
	    case WIT:
		return 1;
	    case ROOD:
		return 2;
	    case GROEN:
		return 3;
	    case BLAUW:
		return 4;
	    default:
		throw new RuntimeException("Edelsteen->Integer fout");
	}
    }

    // en terug, zeker mogelijk met arraylist ofzo maar is teveel instantiëren
    public static Edelsteen toSoort(int i) {
	switch (i) {
	    case 0:
		return ZWART;
	    case 1:
		return WIT;
	    case 2:
		return ROOD;
	    case 3:
		return GROEN;
	    case 4:
		return BLAUW;
	    default:
		throw new RuntimeException("Integer->Edelsteen fout");
	}
    }

    public static ArrayList<Integer> edelsteenHashMapDTO(HashMap<Edelsteen, Integer> edelsteenHashMapInp) {
	// edelsteenFiches -> DTOs
	ArrayList<Integer> edelsteenHashMapDTO = new ArrayList<>();
	for (int i = 0; i < Edelsteen.values().length; i++) {
	    Edelsteen soort = Edelsteen.toSoort(i);
	    edelsteenHashMapDTO.add(edelsteenHashMapInp.get(soort));
	}
	return edelsteenHashMapDTO;
    }
}