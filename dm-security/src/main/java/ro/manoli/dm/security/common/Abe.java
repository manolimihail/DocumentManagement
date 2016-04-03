package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * 
 * @author Mihail
 *
 */
public class Abe {

	public static void setup(PublicKey publicKey, MasterKey masterKey, String[] attrs, BigInteger p, Element g) {
		URL propertiesFileURL = Abe.class.getResource("a.properties");
		Pairing e = PairingFactory.getPairing(propertiesFileURL.getPath());
		Random rnd = new Random();
		publicKey.e = e;
		
		masterKey.t = new ArrayList<MasterKeyElement>();
		masterKey.g = g;
		masterKey.p = p;
		
		publicKey.T = new ArrayList<Ti>();
		publicKey.p = p;
		publicKey.g = g;
		for (int i = 0; i < attrs.length; i++) {
			// computing master key t1, t2, t3... from Zp
			BigInteger ti = BigInteger.probablePrime(256, rnd).mod(p);
			masterKey.t.add(new MasterKeyElement(ti, attrs[i]));
			
			// computing T1, T2, T3 
			computePK(publicKey, g, ti, attrs[i]);
		}

		masterKey.y = BigInteger.probablePrime(256, rnd);
		publicKey.Y = e.pairing(g, g).pow(masterKey.y);
	}

	private static void computePK(PublicKey publicParams, Element g, BigInteger t, String attribute) {
		Ti publicTi = new Ti();
		publicTi.T_i = g.pow(t);
		publicTi.attr = attribute;
		publicParams.T.add(publicTi);
	}
	
	public static DecryptionKey keygen(AccessTree accessTree, MasterKey masterKey) {
		DecryptionKey decryptionKey = new DecryptionKey();
		decryptionKey.dx = new ArrayList<>();
		
		accessTree.root.poly = new Polynomial(accessTree.root.threashold - 1, masterKey.p, masterKey.y);
		Queue<AccessTreeNode> queue = new LinkedList<>();
		queue.addAll(accessTree.root.children);
		 while( ! queue.isEmpty()) {
			 AccessTreeNode node = queue.remove();
			 BigInteger qx0 = node.parent.computePolynomial(node.index());
			 
			 if(node.children != null) {
				 // for each node in the tree set the degree of the polynomial to be one less than the threshold value of that node 
				 node.poly = new Polynomial(node.threashold - 1, masterKey.p, qx0);
				 queue.addAll(node.children);
			 } else {
				 node.poly = new Polynomial(0, masterKey.p, qx0);
				 BigInteger ti = masterKey.t.stream()
						 .filter(x -> x.attr.equals(node.attribute))
						 .map(x -> x.t)
						 .findFirst().orElse(null);
				 if(ti != null) {
					 ti = ti.modInverse(masterKey.p);
					 BigInteger exp = node.computePolynomial(BigInteger.ZERO).multiply(ti).mod(masterKey.p);
					 Element d = masterKey.g.pow(exp);
					 decryptionKey.dx.add(new SecretShare(d, node.attribute));
				 }
			 }
		 }
		 decryptionKey.accessTree = accessTree;
		 return decryptionKey;
	}
	
	
	public static Ciphertext enc(PublicKey publicKey, Element message, String[] attributes) {
		Ciphertext ciphertext = new Ciphertext();
		ciphertext.attributes.addAll(Arrays.asList(attributes));
		BigInteger s = publicKey.getRandomElement();
		Element yPows = publicKey.Y.pow(s);
		ciphertext.Ep = message.mul(yPows);

		List<String> attrList = Arrays.asList(attributes);
		for(Ti ti : publicKey.T) {
			if(attrList.contains(ti.attr)) {
				ciphertext.comps.add(new CiphertextElement(ti.T_i.pow(s), ti.attr));
			}
		}
		
		return ciphertext;
	}
	
	public static Element dec(DecryptionKey D, Ciphertext E, PublicKey publicKey) {
		return decryptNode(D.accessTree.root, D, E, publicKey);
	}
	
	private static Element decryptNode(AccessTreeNode node, DecryptionKey D, Ciphertext E, PublicKey publicKey) {
		if(node.isChild) {
			if(E.attributes.contains(node.attribute))
				return publicKey.e.pairing(D.getDxByAttribute(node.attribute), E.getExByAttribute(node.attribute));
			else
				return null;
		} else {
			Map<Integer, Element> fz = new HashMap<>();
			Map<Integer, BigInteger> sxP = new HashMap<>();
			Integer i = 0;
			List<Integer> sx = new ArrayList<>();
			for(AccessTreeNode child : node.children) {
				sxP.put(i, node.index());
				fz.put(i, decryptNode(child, D, E, publicKey));
				sx.add(i);
				i++;
			}
			Collection<List<Integer>> combinationsIds = SetUtils.generateCombinations(sx, node.threashold);
			for(List<Integer> combination : combinationsIds) {
				boolean hasValue = true;
				for(Integer id : combination) {
					if(fz.get(id) == null) {
						hasValue = false;
						break;
					}
				}
				if(hasValue) {
					Element first = fz.get(combination.get(0));
					List<BigInteger> s = new ArrayList<>();
					for(Integer id : combination) {
						s.add(sxP.get(id));
					}
					Element prod = first.pow(LagrangeUtils.computeDelta(BigInteger.ZERO, sxP.get(combination.get(0)), s, publicKey.p));
					for(int j = 1; j < combination.size(); j++) {
						prod = prod.mul(fz.get(combination.get(j)).pow(LagrangeUtils.computeDelta(BigInteger.ZERO, sxP.get(combination.get(j)), s, publicKey.p)));
					}
					return prod;
				}
			}
		}	
		return null;
	}
}
