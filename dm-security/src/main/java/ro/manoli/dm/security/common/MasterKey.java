package ro.manoli.dm.security.common;

import java.util.List;

import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class MasterKey {
	Element y;
	List<MasterKeyElement> comps;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(y);
		builder.append(":");
		builder.append(comps);
		return builder.toString();
	}
}
