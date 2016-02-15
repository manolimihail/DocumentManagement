package ro.manoli.dm.security.common;

import java.util.ArrayList;
import java.util.List;

import it.unisa.dia.gas.jpbc.Element;

/**
 * 
 * @author Mihail
 *
 */
public class AbePolicy {
	/*serialized*/
	/* k=1 if leaf, otherwise threshold */
	int k;
	/* attribute string if leaf, otherwise null */
	String attr;
	Element D;			/* G_1 only for leaves */
	/* array of gpswabePolicy and length is 0 for leaves */
	AbePolicy[] children;

	/* only used during encryption */
	Polynomial q;

	/* only used during decryption */
	boolean satisfiable;
	int min_leaves;
	int attri;
	List<Integer> satl = new ArrayList<Integer>();
}