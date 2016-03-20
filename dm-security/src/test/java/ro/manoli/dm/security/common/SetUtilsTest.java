package ro.manoli.dm.security.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class SetUtilsTest {
	
	@Test
	public void testPermutations() {
		List<Integer> elements = new ArrayList<>();
		elements.add(new Integer(1));
		elements.add(new Integer(2));
		elements.add(new Integer(3));
		Collection<List<Integer>> permutations = SetUtils.combinations(elements, 2).collect(Collectors.toCollection(ArrayList::new));
		Assert.assertEquals(3, permutations.size());
	}
}
