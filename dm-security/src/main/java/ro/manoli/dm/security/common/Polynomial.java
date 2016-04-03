package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.util.Random;

/**
 * 
 * @author Mihail
 *
 */
public class Polynomial {
	int degree;
	// a0 * x^0 + a1 * x^1 + ...
	BigInteger[] coeficient;
	BigInteger p;
	
	private Random random = new Random();
	
	public Polynomial(int degree, BigInteger p, BigInteger qx0) {
		this.degree = degree;
		this.p = p;
		
		coeficient = new BigInteger[this.degree + 1];
		coeficient[0] = qx0;
		if(this.degree > 0) {
			for(int i = 1; i <= degree; i++) {
				coeficient[i] = BigInteger.probablePrime(256, random).mod(p);
			}
		}
	}
	
	public BigInteger computePolynomial(BigInteger x) {
		BigInteger sum = new BigInteger("0");
		for(int i = 0; i <= degree; i++) {
			sum = sum.add(x.pow(i).multiply(coeficient[i]));
		}
		return sum.mod(p);
	}
}
