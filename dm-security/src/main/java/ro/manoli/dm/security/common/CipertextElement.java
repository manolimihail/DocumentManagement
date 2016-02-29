package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Class that models an attribute structure in ciphertext key.
 * 
 * @author Mihail
 *
 */
public class CipertextElement {
	String attr;
	Element E; /*G1*/
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(attr);
		builder.append(":");
		builder.append(E);
		return builder.toString();
	}
}
