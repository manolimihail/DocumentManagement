package ro.manoli.dm.security.common;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * 
 * @author Mihail
 *
 */
public class Abe {
	private static String curveParams = "type a\n"
			+ "q 87807107996633125224377819847540498158068831994142082"
			+ "1102865339926647563088022295707862517942266222142315585"
			+ "8769582317459277713367317481324925129998224791\n"
			+ "h 12016012264891146079388821366740534204802954401251311"
			+ "822919615131047207289359704531102844802183906537786776\n"
			+ "r 730750818665451621361119245571504901405976559617\n"
			+ "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";
	
	/*!
	 * Generate public and master key with the provided attributes list.
	 *
	 * @param pub			Pointer to the public key data structure
	 * @param msk			Pointer to the master key data structure
	 * @param attributes	Attributes list
	 * @return				none.
	 */

	public static void setup(PublicParam publicParams, MasterKey masterKey, String[] attrs) {
		Element tmp;
		publicParams.pairingDesc = curveParams; 
		URL propertiesFileURL = Abe.class.getResource("a.properties");
		publicParams.p = PairingFactory.getPairing(propertiesFileURL.getPath());
		Pairing pairing = publicParams.p;
		
		publicParams.g = pairing.getG1().newElement();
		tmp = pairing.getG1().newElement();
		publicParams.Y = pairing.getGT().newElement();
		masterKey.y = pairing.getZr().newElement();
	
		publicParams.comps = new ArrayList<PublicParamElement>();
		masterKey.comps = new ArrayList<MasterKeyElement>();
		
		masterKey.y.setToRandom();
		publicParams.g.setToRandom();
		
		tmp = publicParams.g.duplicate();
		tmp.powZn(masterKey.y);
		
		publicParams.Y = pairing.pairing(publicParams.g, tmp);
		for (int i = 0; i < attrs.length; i++) {
			PublicParamElement publicTa = new PublicParamElement();
			MasterKeyElement mskTa = new MasterKeyElement();
			publicTa.attr = attrs[i];
			mskTa.attr = publicTa.attr;
			
			mskTa.t = pairing.getZr().newElement();
			publicTa.T = pairing.getG1().newElement();
			mskTa.t.setToRandom();
			publicTa.T = publicParams.g.duplicate();
			(publicTa.T).powZn(mskTa.t);
			publicParams.comps.add(publicTa);
			masterKey.comps.add(mskTa);
		}
	}
	
	public static PrivateKey keygen(PublicParam pub, MasterKey msk, String policy) throws Exception{
		PrivateKey prv = new PrivateKey();
		prv.p = parsePolicyPostfix(policy);
		if(prv.p == null){
			System.out.println("Policy cannot be found!");
			return null;
		} else {
			fillPolicy(prv.p, pub, msk, msk.y);
			return prv;
		}
	}
	
	/*!
	 * Generate a Policy tree from the input policy string.
	 *
	 * @param s				Policy string
	 * @return				Policy root node data structure
	 */

	private static AbePolicy parsePolicyPostfix(String s) throws Exception {
		String[] toks;
		String tok;
		List<AbePolicy> stack = new ArrayList<AbePolicy>();
		AbePolicy root;

		toks = s.split(" ");

		int toks_cnt = toks.length;
		for (int index = 0; index < toks_cnt; index++) {
			int i, k, n;

			tok = toks[index];
			if (!tok.contains("of")) {
				stack.add(baseNode(1, tok));
			} else {
				AbePolicy node;

				/* parse "kofn" node */
				String[] k_n = tok.split("of");
				k = Integer.parseInt(k_n[0]);
				n = Integer.parseInt(k_n[1]);
				
				if (k < 1) {
					System.out.println("error parsing " + s
							+ ": trivially satisfied operator " + tok);
					return null;
				} else if (k > n) {
					System.out.println("error parsing " + s
							+ ": unsatisfiable operator " + tok);
					return null;
				} else if (n == 1) {
					System.out.println("error parsing " + s
							+ ": indentity operator " + tok);
					return null;
				} else if (n > stack.size()) {
					System.out.println("error parsing " + s
							+ ": stack underflow at " + tok);
					return null;
				}

				/* pop n things and fill in children */
				node = baseNode(k, null);
				node.children = new AbePolicy[n];

				for (i = n - 1; i >= 0; i--)
					node.children[i] = stack.remove(stack.size() - 1);

				/* push result */
				stack.add(node);
			}
		}

		if (stack.size() > 1) {
			System.out.println("error parsing " + s	+ ": extra node left on the stack");
			return null;
		} else if (stack.size() < 1) {
			System.out.println("error parsing " + s + ": empty policy");
			return null;
		}

		root = stack.get(0);
		return root;
	}
	

	/*!
	 * Generate a Policy tree from the input policy string.
	 *
	 * @param s				Policy string
	 * @return				Policy root node data structure
	 */

	private static AbePolicy baseNode(int k, String s) {
		AbePolicy p = new AbePolicy();

		p.k = k;
		p.attr = s;
		p.q = null;

		return p;
	}

	/*!
	 * Routine to fill out the Policy tree
	 *
	 * @param P				Pointer to Root node policy data structure
	 * @param pub			Public key
	 * @param msk			Master key
	 * @param e				Root secret
	 * @return				None
	 */
	private static void fillPolicy(AbePolicy p, PublicParam pub, MasterKey msk, Element e)
			throws NoSuchAlgorithmException {
		int i;
		Element r, t, a;
		Pairing pairing = pub.p;
		r = pairing.getZr().newElement();
		t = pairing.getZr().newElement();
		a = pairing.getZr().newElement();

		p.q = randPoly(p.k - 1, e);

		if (p.children == null || p.children.length == 0) {
			p.D = pairing.getG1().newElement();
			
			for(i=0; i<msk.comps.size();i++){
				if(msk.comps.get(i).attr.compareTo(p.attr)==0){
					a=p.q.coef[0].duplicate();
					a.div(msk.comps.get(i).t);
					p.D=pub.g.duplicate();
					p.D.powZn(a);
					break;
				}
				else{
					if(i==msk.comps.size()-1){
						System.err.println("Check your attribute universe. Certain attribute not included!");
						System.exit(0);
					}
				}
			}
		} else {
			for (i = 0; i < p.children.length; i++) {
				r.set(i + 1);
				evalPoly(t, p.q, r);
				fillPolicy(p.children[i], pub, msk, t);
			}
		}

	}
	
	/*!
	 * Compute the constant value of the child node's Lagrange basis polynomial,
	 *
	 * @param r				Constant value of this child node's Lagrange basis polynomial
	 * @param q				Pointer to the lagrange basis polynomial of parent node
	 * @param x				index of this child node in its parent node
	 * @return				None
	 */
	private static void evalPoly(Element r, Polynomial q, Element x) {
		int i;
		Element s, t;

		s = r.duplicate();
		t = r.duplicate();

		r.setToZero();
		t.setToOne();

		for (i = 0; i < q.deg + 1; i++) {
			/* r += q->coef[i] * t */
			s = q.coef[i].duplicate();
			s.mul(t); 
			r.add(s);

			/* t *= x */
			t.mul(x);
		}

	}
	
	/*!
	 * Randomly generate the Lagrange basis polynomial base on provided constant value
	 *
	 * @param deg			Degree of the lagrange basis polynomial
	 * @param zero_val		Constant value of the lagrange basis polynomial
	 * @return				Lagrange basis polynomial data structure
	 */

	private static Polynomial randPoly(int deg, Element zeroVal) {
		int i;
		Polynomial q = new Polynomial();
		q.deg = deg;
		q.coef = new Element[deg + 1];

		for (i = 0; i < deg + 1; i++)
			q.coef[i] = zeroVal.duplicate();

		q.coef[0].set(zeroVal);

		for (i = 1; i < deg + 1; i++)
			q.coef[i].setToRandom();

		return q;
	}
	
	private static void elementFromString(Element h, String s)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(s.getBytes());
		h.setFromHash(digest, 0, digest.length);
	}
	
	/*!
	 * Encrypt a secret message with the provided attributes list, return a ciphertext.
	 *
	 * @param pub			Public key structure
	 * @param m				Secret Message
	 * @param attributes	Attributes list
	 * @return				Ciphertext structure
	 */
	public static Ciphertext enc(PublicParam pub,Element m, String[] attrs)throws Exception{
		Ciphertext cph=new Ciphertext();
		Element s;
		int i;
		//initialize
		Pairing pairing=pub.p;
		s=pairing.getZr().newElement();
		m=pairing.getGT().newElement();
		cph.Ep=pairing.getGT().newElement();
		//compute
		m.setToRandom();
		s.setToRandom();
		cph.Ep=pub.Y.duplicate();
		cph.Ep.powZn(s);
		cph.Ep.mul(m);
		cph.comps=new ArrayList<CipertextElement>();
		int len=attrs.length;
		for (i=0;i<len;i++){
			CipertextElement c=new CipertextElement();
			c.attr=attrs[i];
			c.E=pairing.getG1().newElement();
			for (int j=0;j<pub.comps.size(); j++){
				String pubAttr=pub.comps.get(j).attr;
				if (pubAttr.compareTo(c.attr)==0){
					c.E=pub.comps.get(j).T.duplicate();
					c.E.powZn(s);
					break;
				}
				else{
					if(j==(pub.comps.size()-1)){
						System.out.println("Check your attribute universe. Certain attribute is not included.");
						System.exit(0);
					}
				}
			}
			cph.comps.add(c);
		}
		return cph;
	}
	
	/*
	 * Pick a random group element and encrypt it under the attributes
	 * The resulting ciphertext is return and the Element given as an argument.
	 */
	public static CiphertextKey enc(PublicParam publicParams, String[] attrs) throws Exception {
		CiphertextKey cphKey = new CiphertextKey();
		Ciphertext cph = new Ciphertext();
		//initialize
		Pairing pairing = publicParams.p;
		Element s = pairing.getZr().newElement();
		Element m = pairing.getGT().newElement();
		cph.Ep = pairing.getGT().newElement();
		//compute
		m.setToRandom();
		s.setToRandom();
		Element duplicate = publicParams.Y.duplicate();
		cph.Ep = duplicate.powZn(s);
		cph.Ep = cph.Ep.mul(m);
		cph.comps = new ArrayList<CipertextElement>();
		for (int i = 0; i < attrs.length; i++) {
			CipertextElement c = new CipertextElement();
			c.attr = attrs[i];
			c.E = pairing.getG1().newElement();
			boolean found = false;
			for (int j = 0; j < publicParams.comps.size(); j++){
				String pubAttr = publicParams.comps.get(j).attr;
				if (pubAttr.compareTo(c.attr) == 0) {
					Element dupl = publicParams.comps.get(j).T.duplicate();
					c.E = dupl.powZn(s);
					found = true;
					break;
				}
			}
			if( ! found) {
				System.out.println("Check your attribute universe. Certain attribute is not included.");
			}
			cph.comps.add(c);
		}
		cphKey.cph = cph;
		cphKey.key = m; /*used for AES encryption*/
		return cphKey;
	}
	
	/*!
	 * Check whether the attributes in the ciphertext data structure can
	 * access the root secret in the policy data structure, and mark all
	 * possible path
	 *
	 * @param p				Policy node data structure (root)
	 * @param cph			Ciphertext data structure
	 * @param oub			Public key data structure
	 * @return				None
	 */
	private static void checkSatisfy(AbePolicy p, Ciphertext cph, PublicParam pub) {
		int i, l;
		String cphAttr;
		String pubAttr;

		p.satisfiable = false;
		if (p.children == null || p.children.length == 0) {
			for (i = 0; i < cph.comps.size(); i++) {
				cphAttr = cph.comps.get(i).attr;
				// System.out.println("cphAttr:" + cphAttr);
				// System.out.println("p.attr" + p.attr);
				if (cphAttr.compareTo(p.attr) == 0) {
					// System.out.println("=satisfy=");
					p.satisfiable = true;
					p.attri = i;
					break;
				}
			}
			for(i=0;i<pub.comps.size();i++){
				pubAttr=pub.comps.get(i).attr;
				if(pubAttr.compareTo(p.attr)==0){
					break;
				}
				else{
					if(i==pub.comps.size()-1){
						System.out.println("Check your attribute universe. Certain attribute is not included!");
						break;
					}
				}
			}
		} else {
			for (i = 0; i < p.children.length; i++)
				checkSatisfy(p.children[i], cph, pub);

			l = 0;
			for (i = 0; i < p.children.length; i++)
				if (p.children[i].satisfiable)
					l++;

			if (l >= p.k)
				p.satisfiable = true;
		}
	}
	
	/*!
	 * Choose the path with minimal leaves node from all possible path which are marked as satisfiable
	 * Mark the respective "min_leaves" element in the policy node data structure
	 *
	 * @param p				Policy node data structure (root)
	 * @return				None
	 */
	private static void pickSatisfyMinLeaves(AbePolicy p) {
		int i, k, l, c_i;
		int len;
		List<Integer> c = new ArrayList<Integer>();

		assert(p.satisfiable);
		if (p.children == null || p.children.length == 0)
			p.min_leaves = 1;
		else {
			len = p.children.length;
			for (i = 0; i < len; i++)
				if (p.children[i].satisfiable)
					pickSatisfyMinLeaves(p.children[i]);

			for (i = 0; i < len; i++)
				c.add(i);

			Collections.sort(c, new IntegerComparator(p));

			p.satl = new ArrayList<Integer>();
			p.min_leaves = 0;
			l = 0;

			for (i = 0; i < len && l < p.k; i++) {
				c_i = c.get(i).intValue(); /* c[i] */
				if (p.children[c_i].satisfiable) {
					l++;
					p.min_leaves += p.children[c_i].min_leaves;
					k = c_i + 1;
					p.satl.add(k);
				}
			}
			assert(l==p.k);
		}
	}
	
	/*!
	 * Compute Lagrange coefficient
	 *
	 * @param r				Lagrange coefficient
	 * @param s				satisfiable node set
	 * @param i				index of this node in the satisfiable node set
	 * @return				None
	 */
	private static void lagrangeCoef(Element r, List<Integer> s, int i) {
		int j, k;
		Element t;

		t = r.duplicate();

		r.setToOne();
		for (k = 0; k < s.size(); k++) {
			j = s.get(k).intValue();
			if (j == i)
				continue;
			t.set(-j);
			r.mul(t); /* num_muls++; */
			t.set(i - j);
			t.invert();
			r.mul(t); /* num_muls++; */
		}
	}
	
	/*!
	 * DecryptNode algorithm for root secret
	 *
	 * @param r				Root secret
	 * @param p				Policy node dtat structure(root)
	 * @param cph			Ciphertext data structure
	 * @param pub			Public key data structure
	 * @return				None
	 */
	private static void decFlatten(Element r, AbePolicy p, Ciphertext cph,
			PublicParam pub) {
		Element one;
		one = pub.p.getZr().newElement();
		one.setToOne();
		r.setToOne();

		decNodeFlatten(r, one, p, cph, pub);
	}

	
	private static void decNodeFlatten(Element r, Element exp, AbePolicy p,
			Ciphertext cph, PublicParam pub) {
		assert(p.satisfiable);
		if (p.children == null || p.children.length == 0)
			decLeafFlatten(r, exp, p, cph, pub);
		else
			decInternalFlatten(r, exp, p, cph, pub);
	}

	private static void decLeafFlatten(Element r, Element exp, AbePolicy p,
			Ciphertext cph, PublicParam pub) {
		CipertextElement c;
		Element s;

		c = cph.comps.get(p.attri);

		s = pub.p.getGT().newElement();

		s = pub.p.pairing(p.D, c.E); /* num_pairings++; */
		s.powZn(exp); /*num_exps++;*/
		r.mul(s); /*num_muls++;*/
	}

	private static void decInternalFlatten(Element r, Element exp,
			AbePolicy p, Ciphertext cph, PublicParam pub) {
		int i;
		Element t, expnew;

		t = pub.p.getZr().newElement();
		expnew = pub.p.getZr().newElement();

		for (i = 0; i < p.satl.size(); i++) {
			lagrangeCoef(t, p.satl, (p.satl.get(i)).intValue());
			expnew = exp.duplicate();
			expnew.mul(t);
			decNodeFlatten(r, expnew, p.children[p.satl.get(i)-1],cph, pub);
		}
	}

	
	public static Element dec(PublicParam pub, PrivateKey prv, Ciphertext cph){
		Element Ys;
		Element m;
		Pairing pairing=pub.p;
		m=pairing.getGT().newElement();
		Ys=pairing.getGT().newElement();
		checkSatisfy(prv.p, cph, pub);
		if(!prv.p.satisfiable){
			System.out.println("Cannot decrypt.");
			return null;
		}
		pickSatisfyMinLeaves(prv.p);
		decFlatten(Ys, prv.p, cph, pub);
		Element tmp=cph.Ep.duplicate();
		m=tmp.div(Ys);
		return m;
	}
	
	
	private static class IntegerComparator implements Comparator<Integer> {
		AbePolicy policy;

		public IntegerComparator(AbePolicy p) {
			this.policy = p;
		}

		@Override
		public int compare(Integer o1, Integer o2) {
			int k, l;

			k = policy.children[o1.intValue()].min_leaves;
			l = policy.children[o2.intValue()].min_leaves;

			return	k < l ? -1 : 
					k == l ? 0 : 1;
		}
	}
}
