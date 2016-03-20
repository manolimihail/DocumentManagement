package ro.manoli.dm.security.common;
import java.util.ArrayList;
import java.util.List;

import it.unisa.dia.gas.jpbc.Element;

/* A ciphertext*/
public class Ciphertext {
	Element Ep;
	List<CiphertextElement> comps;
	// gamma
	List<String> attributes;
	
	public Ciphertext() {
		this.comps = new ArrayList<>();
		this.attributes = new ArrayList<>();
	}
	
	public Element getExByAttribute(String attribute) {
		return comps.stream()
				.filter(x -> x.attr.equals(attribute))
				.map(x -> x.E)
				.findFirst()
				.get();
	}
}
