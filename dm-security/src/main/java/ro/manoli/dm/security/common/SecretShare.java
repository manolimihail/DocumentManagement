package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

public class SecretShare {
	Element d;
	String attribute;

	public SecretShare(Element d, String attribute) {
		this.d = d;
		this.attribute = attribute;
	}
}
