package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

/*Helper method. It is used for some classes who return 
 * both cph and key for further encryption
 */
public class CiphertextKey {
	public Ciphertext cph;
	public Element key;
}
