package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.util.List;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.util.math.BigIntegerUtils;

/**
 * 
 * @author Mihail
 *
 */
public class PublicKey {
	public String pairingDesc;
	public BigInteger p;
	public Element g;
	public Element Y;
	public List<Ti> T;
	public Pairing e;
	
	public BigInteger getRandomElement() {
		return BigIntegerUtils.getRandom(p).mod(p);
	}
	
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
		builder.append(T);
		return builder.toString();
	}
}
