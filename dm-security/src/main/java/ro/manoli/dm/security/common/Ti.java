package ro.manoli.dm.security.common;
import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class Ti {
	String attr;
	Element T_i;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(attr);
		builder.append(":");
		builder.append(T_i);
		return builder.toString();
	}
}
