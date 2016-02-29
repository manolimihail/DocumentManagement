package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Helper method. It is used for some classes who return 
 * both cph and key for further encryption 
 * 
 * @author Mihail
 *
 */
public class CiphertextKey {
	public Ciphertext cph;
	public Element key;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(cph);
		builder.append(":");
		builder.append(key);
		return builder.toString();
	}
}
