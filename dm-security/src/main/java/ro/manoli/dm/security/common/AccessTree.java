package ro.manoli.dm.security.common;

import java.util.List;

/**
 * 
 * @author Mihail
 *
 */
public class AccessTree {
	AccessTreeNode root;

	public AccessTree() {
	}

	public AccessTree(AccessTreeNode root) {
		this.root = root;
	}

	public boolean satisfiesGamma(List<String> attributes) {
		return root.satisfiesGamma(attributes);
	}
}
