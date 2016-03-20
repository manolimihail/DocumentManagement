package ro.manoli.dm.security.common;

import java.math.BigInteger;

/**
 * 
 * @author Mihail
 *
 */
public class MasterKeyElement {
	String attr;
	BigInteger t;
	
	public MasterKeyElement(BigInteger ti, String attribute) {
		this.attr = attribute;
		this.t = ti;
	}

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
