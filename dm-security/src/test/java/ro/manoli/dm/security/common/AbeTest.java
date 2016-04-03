package ro.manoli.dm.security.common;

import java.math.BigInteger;
import java.net.URL;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * 
 * @author Mihail
 *
 */
public class AbeTest {

	private PublicKey publicKey;
	private MasterKey masterKey;
	
	private String[] attributes;
	private BigInteger p;
	private Element g;
	private Element message;
	
	private AccessTree accessTree;
	private String[] otherAttributes;
	
	private AccessTree anotherAccessTree;
	
	@Before
	public void init() {
		publicKey = new PublicKey();
		Random rnd = new Random();
		p = BigInteger.probablePrime(256, rnd);
		URL propertiesFileURL = Abe.class.getResource("a.properties");
		Pairing e = PairingFactory.getPairing(propertiesFileURL.getPath());
		g = e.getG1().newElement();
	
		message = e.getGT().newElement();
		masterKey = new MasterKey();
		
		attributes = new String[] {"1", "2", "3", "4", "5"};
		otherAttributes = new String[] {"1", "2", "5"};
		
		createAccessTree();
		createAnotherAccessTree();
	}

	private void createAccessTree() {
		AccessTreeNode n1 = new AccessTreeNode("1", 4L);
		AccessTreeNode n2 = new AccessTreeNode("2", 5L);
		AccessTreeNode n3 = new AccessTreeNode("3", 6L);
		AccessTreeNode n4 = new AccessTreeNode("4", 7L);
		AccessTreeNode n5 = new AccessTreeNode("5", 8L);
		
		AccessTreeNode r1 = new AccessTreeNode(3, 2L, n1, n2, n3);
		AccessTreeNode r2 = new AccessTreeNode(1, 3L, n4, n5);

		AccessTreeNode root = new AccessTreeNode(2, 1L, r1, r2);
		
		accessTree = new AccessTree(root);
	}

	private void createAnotherAccessTree() {
		AccessTreeNode n1 = new AccessTreeNode("1", 12L);
		AccessTreeNode n2 = new AccessTreeNode("2", 13L);
		AccessTreeNode n3 = new AccessTreeNode("3", 14L);
		AccessTreeNode n4 = new AccessTreeNode("4", 15L);
		AccessTreeNode n5 = new AccessTreeNode("5", 16L);
		AccessTreeNode n6 = new AccessTreeNode("6", 17L);
		AccessTreeNode n7 = new AccessTreeNode("7", 18L);
		AccessTreeNode n8 = new AccessTreeNode("8", 19L);
		AccessTreeNode n9 = new AccessTreeNode("9", 20L);
		AccessTreeNode n10 = new AccessTreeNode("10", 21L);
		AccessTreeNode n11 = new AccessTreeNode("11", 22L);
		AccessTreeNode n12 = new AccessTreeNode("12", 23L);
		AccessTreeNode n13 = new AccessTreeNode("13", 24L);
		AccessTreeNode n14 = new AccessTreeNode("14", 25L);
		
		AccessTreeNode r2_1 = new AccessTreeNode(1, 5L, n1, n2);
		AccessTreeNode r2_2 = new AccessTreeNode(2, 6L, n3, n4);
		AccessTreeNode r2_3 = new AccessTreeNode(1, 7L, n5, n6);
		AccessTreeNode r2_4 = new AccessTreeNode(2, 8L, n7, n8);
		AccessTreeNode r2_5 = new AccessTreeNode(1, 9L, n9, n10);
		AccessTreeNode r2_6 = new AccessTreeNode(2, 10L, n11, n12);
		AccessTreeNode r2_7 = new AccessTreeNode(2, 11L, n13, n14);

		AccessTreeNode r1_1 = new AccessTreeNode(2, 2L, r2_1, r2_2);
		AccessTreeNode r1_2 = new AccessTreeNode(1, 3L, r2_3, r2_4, r2_5);
		AccessTreeNode r1_3 = new AccessTreeNode(1, 4L, r2_6, r2_7);
		
		AccessTreeNode root = new AccessTreeNode(1, 1L, r1_1, r1_2, r1_3);
		
		anotherAccessTree = new AccessTree(root);
	}

	@Test
	public void testSetupBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
	}
	
	@Test
	public void testEncryptionBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
		Abe.enc(publicKey, message, attributes);
	}
	
	@Test
	public void testKeyGenerationBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
		Abe.enc(publicKey, message, attributes);
		Abe.keygen(accessTree, masterKey);
	}

	@Test
	public void testDecryptionBuild() {
		Abe.setup(publicKey, masterKey, attributes, p, g);
		Ciphertext ciphertext = Abe.enc(publicKey, message, attributes);
		DecryptionKey decryptionKey = Abe.keygen(accessTree, masterKey);
		Element result = Abe.dec(decryptionKey, ciphertext, publicKey);
		Assert.assertEquals(message, result);
	}

	@Test
	public void testFailedDecryption() {
		Abe.setup(publicKey, masterKey, otherAttributes, p, g);
		Ciphertext ciphertext = Abe.enc(publicKey, message, otherAttributes);
		DecryptionKey decryptionKey = Abe.keygen(accessTree, masterKey);
		Element result = Abe.dec(decryptionKey, ciphertext, publicKey);
		Assert.assertNotEquals(message, result);
	}

	@Test
	public void testAlgorithmOnSecondTree() {
		String[] attributes = {"1", "3", "4"};
		Abe.setup(publicKey, masterKey, attributes, p, g);
		Ciphertext ciphertext = Abe.enc(publicKey, message, attributes);
		DecryptionKey decryptionKey = Abe.keygen(anotherAccessTree, masterKey);
		Element result = Abe.dec(decryptionKey, ciphertext, publicKey);
		Assert.assertEquals(message, result);
	}
}
