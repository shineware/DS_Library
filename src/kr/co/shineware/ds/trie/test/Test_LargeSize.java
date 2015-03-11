package kr.co.shineware.ds.trie.test;

import kr.co.shineware.ds.trie.TrieDictionary;

public class Test_LargeSize {

	public void makeData(TrieDictionary<String> dic, int maxDepth) {
		makeDataSub(dic, "", 0, maxDepth);
	}
	
	private boolean makeDataSub(TrieDictionary<String> dic, String prefix, int depth, int maxDepth) {
		if(depth > maxDepth)
			return	false;
		
		for(int i=0; i<26; i++) {
			String	tmpStr	= prefix + (char)('A'+i);
			
			dic.put(tmpStr, "[" + tmpStr + "]");
			if(!makeDataSub(dic, tmpStr, depth+1, maxDepth))
				break;
		}
		
		return	true;
	}
	
	
	
	public static void main(String[] args) {
		String			testFileName	= "trie_Large.db";
		Test_LargeSize	inst			= new Test_LargeSize();
		
		//	make test data.
		TrieDictionary<String> trieDic = new TrieDictionary<String>();
		inst.makeData(trieDic, 3);
		trieDic.save(testFileName);
		
		//	reload test data from file.
		trieDic = new TrieDictionary<String>();
		trieDic.load(testFileName);
		
		
		String[] verifyData	= {"", "A", "AT", "AAG", "a", "Aa", "AAAR", "ZAAR"};
		
		for(String s: verifyData) {
			System.out.println(s + " - "+ trieDic.get(s));
			System.out.println(trieDic.hasChildren());
		}
		
	}
}
