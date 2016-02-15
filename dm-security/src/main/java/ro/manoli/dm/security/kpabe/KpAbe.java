package ro.manoli.dm.security.kpabe;

import java.io.IOException;

import it.unisa.dia.gas.jpbc.Element;
import ro.manoli.dm.security.common.Abe;
import ro.manoli.dm.security.common.Ciphertext;
import ro.manoli.dm.security.common.CiphertextKey;
import ro.manoli.dm.security.common.MasterKey;
import ro.manoli.dm.security.common.PrivateKey;
import ro.manoli.dm.security.common.PublicParam;
import ro.manoli.dm.security.common.SerializeUtils;

/**
 * @author Mihail
 */
public class KpAbe {
	public void setup(String pubfile, String mskfile, String[] attrs_univ) throws IOException, ClassNotFoundException {
		byte[] pub_byte, msk_byte;
		PublicParam pub = new PublicParam();
		MasterKey msk = new MasterKey();
		Abe.setup(pub, msk, attrs_univ);

		/* store gpswabePub into pubfile */
		pub_byte = SerializeUtils.serializegpswabePub(pub);
		KpAbeUtils.spitFile(pubfile, pub_byte);

		/* store gpswabeMsk into mskfile */
		msk_byte = SerializeUtils.serializegpswabeMsk(msk);
		KpAbeUtils.spitFile(mskfile, msk_byte);
	}
	
	public void keygen(String pubfile, String mskfile, String prvfile, 
			String policy) throws Exception {
		PublicParam pub;
		MasterKey msk;
		PrivateKey prv;
		byte[] pub_byte, msk_byte, prv_byte;

		/* get gpswabePub from pubfile */
		pub_byte = KpAbeUtils.suckFile(pubfile);
		pub = SerializeUtils.unserializegpswabePub(pub_byte);

		/* get gpswabeMsk from mskfile */
		msk_byte = KpAbeUtils.suckFile(mskfile);
		msk = SerializeUtils.unserializegpswabeMsk(pub, msk_byte);

		/*String policy = LangPolicy.parsePolicy(attr_str);*/
		prv = Abe.keygen(pub, msk, policy);

		/* store gpswabePrv into prvfile */
		prv_byte = SerializeUtils.serializegpswabePrv(prv);
		KpAbeUtils.spitFile(prvfile, prv_byte);
	}
	
	public void enc(String pubfile, String inputfile, String[] attrs,
			String encfile) throws Exception {
		PublicParam pub;
		CiphertextKey cphKey;
		Ciphertext cph;
		byte[] plt;
		byte[] cphBuf;
		byte[] aesBuf;
		byte[] pub_byte;
		Element m;

		/* get gpswabePub from pubfile */
		pub_byte = KpAbeUtils.suckFile(pubfile);
		pub = SerializeUtils.unserializegpswabePub(pub_byte);

		cphKey = Abe.enc(pub,attrs);
		m=cphKey.key;
		cph=cphKey.cph;
		System.err.println("m = "+m.toString());

		if (cph == null) {
			System.out.println("Error happed in enc");
			System.exit(0);
		}

		cphBuf = SerializeUtils.gpswabeCphSerialize(cph);

		/* read file to encrypted */
		plt = KpAbeUtils.suckFile(inputfile);
		aesBuf = AESCoder.encrypt(m.toBytes(), plt);
		// PrintArr("element: ", m.toBytes());
		KpAbeUtils.writeKpabeFile(encfile, cphBuf, aesBuf);
	}
	
	public void dec(String pubfile, String prvfile, String encfile,
			String decfile) throws Exception {
		byte[] aesBuf, cphBuf;
		byte[] plt;
		byte[] prv_byte;
		byte[] pub_byte;
		byte[][] tmp;
		Ciphertext cph;
		PrivateKey prv;
		PublicParam pub;

		/* get gpswabePub from pubfile */
		pub_byte = KpAbeUtils.suckFile(pubfile);
		pub = SerializeUtils.unserializegpswabePub(pub_byte);

		/* read ciphertext */
		tmp = KpAbeUtils.readKpabeFile(encfile);
		aesBuf = tmp[0];
		cphBuf = tmp[1];
		cph = SerializeUtils.gpswabeCphUnserialize(pub, cphBuf);

		/* get gpswabePrv from prvfile */
		prv_byte = KpAbeUtils.suckFile(prvfile);
		prv = SerializeUtils.unserializegpswabePrv(pub, prv_byte);

		Element m = Abe.dec(pub, prv, cph);
		if (m != null) {
			plt = AESCoder.decrypt(m.toBytes(), aesBuf);
			KpAbeUtils.spitFile(decfile, plt);
		} else {
			throw new RuntimeException("Decrypted element is null. Please check the algorithm.");
		}
	}
}
