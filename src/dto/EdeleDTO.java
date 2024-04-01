package dto;

import java.util.ArrayList;
import java.util.HashMap;

import domein.Edele;
import domein.Edelsteen;

public record EdeleDTO(ArrayList<Integer> vereisteBonussen, int prestige, String img) {

    public EdeleDTO(Edele edele) {
	this(Edelsteen.edelsteenHashMapDTO(edele.getVereisteBonussen()), edele.getPrestige(), edele.getImg());
    }
}
