package exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domein.Edele;
import dto.EdeleDTO;

public class EdeleException extends RuntimeException {
    private List<EdeleDTO> edelen;

    public EdeleException(List<Edele> edelen) {
	this.edelen = edelen.stream().map(e -> new EdeleDTO(e)).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<EdeleDTO> getEdelen() {
	return this.edelen;
    }
}