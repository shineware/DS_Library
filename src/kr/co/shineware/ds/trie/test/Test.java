package kr.co.shineware.ds.trie.test;

import kr.co.shineware.ds.trie.TrieDictionary;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TrieDictionary<String> trieDic = new TrieDictionary<String>();
		
		trieDic.put("good", "NNG");
		trieDic.put("after", "MAG");
		trieDic.put("go", "VV");
		trieDic.save("trie.db");
		trieDic = new TrieDictionary<String>();
		trieDic.load("trie.db");
		System.out.println(trieDic.get("goo"));
		System.out.println(trieDic.hasChildren());
		System.out.println(trieDic.get("good"));
		System.out.println(trieDic.hasChildren());
	}

}

