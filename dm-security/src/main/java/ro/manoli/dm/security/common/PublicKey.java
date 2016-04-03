package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

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
	
	private Random random = new Random();
	
	public BigInteger getRandomElement() {
		return BigInteger.probablePrime(256, random);
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
