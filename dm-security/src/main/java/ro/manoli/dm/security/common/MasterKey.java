package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.util.List;

import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class MasterKey {
	BigInteger y;
	Element g;
	BigInteger p;
	List<MasterKeyElement> t;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(y);
		builder.append(":");
		builder.append(t);
		return builder.toString();
	}
}
