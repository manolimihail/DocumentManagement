package ro.manoli.dm.security.common;

import java.util.List;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * 
 * @author Mihail
 *
 */
public class PublicParam {
	public String pairingDesc;
	public Pairing p;
	public Element g;
	public Element Y;
	public List<PublicParamElement> comps;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pairingDesc:");
		builder.append(pairingDesc);
		builder.append(", ");
		builder.append("p:");
		builder.append(p);
		builder.append(", ");
		builder.append("g:");
		builder.append(g);
		builder.append(", ");
		builder.append("Y:");
		builder.append(Y);
		builder.append(", comps:");
		builder.append(comps);
		return builder.toString();
	}
}
