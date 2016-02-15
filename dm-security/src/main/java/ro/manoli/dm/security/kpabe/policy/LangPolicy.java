package ro.manoli.dm.security.kpabe.policy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import ro.manoli.dm.security.kpabe.KpAbe;

/**
 * 
 * @author Mihail
 *
 */
public class LangPolicy {
	public static String[] parseAttribute(String s) {
		ArrayList<String> str_arr = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(s);
		String token;
		String res[];
		int len;

		while (st.hasMoreTokens()) {
			token = st.nextToken();
			if (token.contains(":")) {
				str_arr.add(token);
			} else {
				System.out.println("Some error happens in the input attribute");
				System.exit(0);
			}
		}

		Collections.sort(str_arr, new SortByAlphabetic());

		len = str_arr.size();
		res = new String[len];
		for (int i = 0; i < len; i++)
			res[i] = str_arr.get(i);
		return res;
	}

	public static void main(String[] args) {
		String attr = "objectClass:inetOrgPerson objectClass:organizationalPerson "
				+ "sn:student2 cn:student2 uid:student2 userPassword:student2 "
				+ "ou:idp o:computer mail:student2@sdu.edu.cn title:student";
		String[] arr = parseAttribute(attr);
		for (int i = 0; i < arr.length; i++)
			System.out.println(arr[i]);
		KpAbe kpabe = new KpAbe();
		// TODO de schimbat ca sa se citeasca din BD.
		
		String publicFile = LangPolicy.class.getResource("pubfile.txt").getPath();
		String mskFile = LangPolicy.class.getResource("mskfile.txt").getPath();
		String prvFile = LangPolicy.class.getResource("prvfile.txt").getPath();
		String policy = "sn:student2 ou:idp title:student 1of2 2of2";
		try {
			kpabe.setup(publicFile, mskFile, arr);
			kpabe.keygen(publicFile, mskFile, prvFile, policy);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static class SortByAlphabetic implements Comparator<String> {
		@Override
		public int compare(String s1, String s2) {
			if (s1.compareTo(s2) >= 0)
				return 1;
			return 0;
		}

	}

	/* TODO: parse policy
	public static String parsePolicy(String s) {
		// TODO Auto-generated method stub
		String parsed_policy;
		String cur_string=s;
		yyparse();
		simplify(finalPolicy);
		tidy(finalPolicy);
		parsed_policy=formatPolicyPostfix(finalPolicy);
		return parsed_policy;
	}
	*/
}