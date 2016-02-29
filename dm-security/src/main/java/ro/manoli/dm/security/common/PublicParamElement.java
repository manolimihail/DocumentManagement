package ro.manoli.dm.security.common;
import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class PublicParamElement {
	String attr;
	Element T;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(attr);
		builder.append(":");
		builder.append(T);
		return builder.toString();
	}
}
