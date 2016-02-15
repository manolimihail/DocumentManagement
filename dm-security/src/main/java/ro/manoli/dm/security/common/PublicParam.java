package ro.manoli.dm.security.common;

import java.util.List;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * 
 * @author Mihail
 *
 */
public class PublicParam {
	public String pairingDesc;
	public Pairing p;
	public Element g;
	public Element Y;
	public List<PublicParamElement> comps;
}
