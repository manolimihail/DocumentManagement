package ro.manoli.dm.security.common;

import java.util.List;

import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class DecryptionKey {
	List<SecretShare> dx;
	AccessTree accessTree;
	
	public Element getDxByAttribute(String attribute) {
		return dx.stream()
				.filter(x -> x.attribute.equals(attribute))
				.map(x -> x.d)
				.findFirst()
				.get();
	}
}
