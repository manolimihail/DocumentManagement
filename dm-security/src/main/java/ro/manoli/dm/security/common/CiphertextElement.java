package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Class that models an attribute structure in ciphertext key.
 * 
 * @author Mihail
 *
 */
public class CiphertextElement {
	String attr;
	Element E; /*G1*/
	
	public CiphertextElement(Element E, String attr) {
		this.E = E;
		this.attr = attr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(attr);
		builder.append(":");
		builder.append(E);
		return builder.toString();
	}
}
