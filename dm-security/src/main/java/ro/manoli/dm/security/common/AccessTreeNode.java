package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AccessTreeNode {
	boolean isParent;
	boolean isChild;
	String attribute;
	int threashold;
	BigInteger num;
	List<AccessTreeNode> children;
	Polynomial poly;
	AccessTreeNode parent;
	
	public AccessTreeNode(String attr) {
		this.isChild = true;
		this.attribute = attr;
	}
	
	public AccessTreeNode(int threashold, AccessTreeNode... nodes) {
		isParent = true;
		children = new ArrayList<>();
		BigInteger numbering = BigInteger.ONE;
		for(AccessTreeNode child : nodes) {
			child.parent = this;
			child.num = numbering;
			children.add(child);
			numbering = numbering.add(BigInteger.ONE);
		}
		this.threashold = threashold;
	}

	public boolean satisfiesGamma(List<String> attributes) {
		if(isChild) {
			return attributes.contains(attribute);
		}
		int sum = 0;
		for(AccessTreeNode node : children) {
			sum += node.satisfiesGamma(attributes) ? 1 : 0;
		}
		return sum >= threashold;
	}
	
	public BigInteger index() {
		return num;
	}
	
	public BigInteger computePolynomial(BigInteger x) {
		return this.poly.computePolynomial(x);
	}
}
