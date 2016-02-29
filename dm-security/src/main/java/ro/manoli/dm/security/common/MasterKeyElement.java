package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class MasterKeyElement {
	String attr;
	Element t;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(attr);
		builder.append(" - ");
		builder.append(t);
		builder.append(")");
		return builder.toString();
	}
}
