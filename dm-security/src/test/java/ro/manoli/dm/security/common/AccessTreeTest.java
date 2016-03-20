package ro.manoli.dm.security.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

public class AccessTreeTest {

	private AccessTree accessTree;
	
	@Before
	public void populateAccessTree() {
		AccessTreeNode n1 = new AccessTreeNode("1");
		AccessTreeNode n2 = new AccessTreeNode("2");
		AccessTreeNode n3 = new AccessTreeNode("3");
		AccessTreeNode n4 = new AccessTreeNode("4");
		AccessTreeNode n5 = new AccessTreeNode("5");
		
		AccessTreeNode r1 = new AccessTreeNode(2, n1, n2, n3);
		AccessTreeNode r2 = new AccessTreeNode(1, n4, n5);

		AccessTreeNode root = new AccessTreeNode(2, r1, r2);
		
		accessTree = new AccessTree(root);
	}
	
	@Test
	public void testAccessTree() {
		List<String> attributes = new ArrayList<>();
		attributes.add("1");
		attributes.add("2");
		attributes.add("3");
		attributes.add("4");
		attributes.add("5");
		Set<Set<String>> sets = Sets.powerSet(new HashSet<>(attributes));

		for(Set<String> attribute : sets){
			if(attribute.size() == 3) {
//				Assert.assertEquals(true, accessTree.satisfiesGamma(new ArrayList<>(attribute)));
				System.out.println(attribute);
				System.out.println(accessTree.satisfiesGamma(new ArrayList<>(attribute)));
				System.out.println("-----------------");
			}
		}
	}
}
