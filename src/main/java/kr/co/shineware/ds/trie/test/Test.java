package kr.co.shineware.ds.trie.test;

import kr.co.shineware.ds.trie.FindContext;
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

		final FindContext<String> context = new FindContext<>(trieDic.getRoot());
		System.out.println(trieDic.get(context, "goo"));
		System.out.println(context.hasCurrentChildren());
		System.out.println(trieDic.get(context, "good"));
		System.out.println(context.hasCurrentChildren());
	}

}

