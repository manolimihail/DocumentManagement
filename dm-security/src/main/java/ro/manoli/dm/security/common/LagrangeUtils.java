package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.util.List;

public class LagrangeUtils {
	
	public static BigInteger computeDelta(BigInteger x, BigInteger i, List<BigInteger> s, BigInteger p) {
		BigInteger prod = BigInteger.ONE;
		for(BigInteger j : s) {
			if(j.compareTo(i) != 0) {
				BigInteger up = x.subtract(i);
				BigInteger down = (i.subtract(j)).modInverse(p);
				prod = prod.multiply(up.multiply(down));
			}
		}
		return prod.mod(p);
	}
}
