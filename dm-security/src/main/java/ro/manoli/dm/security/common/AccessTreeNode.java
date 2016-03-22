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
	
	public AccessTreeNode(String attr, Long num) {
		this.isChild = true;
		this.attribute = attr;
		this.num = new BigInteger(num.toString());
	}
	
	public AccessTreeNode(int threashold, Long num, AccessTreeNode... nodes) {
		isParent = true;
		children = new ArrayList<>();
		for(AccessTreeNode child : nodes) {
			child.parent = this;
			children.add(child);
		}
		this.num = new BigInteger(num.toString());
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
