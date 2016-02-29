package ro.manoli.dm.security.common;

import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class Polynomial {
	int deg;
	/*coefficients from [0] x^0 to [deg] x^deg */
	Element[] coef; /*Z_p (of length deg+1) */
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(deg);
		builder.append(":");
		builder.append(coef);
		return builder.toString();
	}
}
