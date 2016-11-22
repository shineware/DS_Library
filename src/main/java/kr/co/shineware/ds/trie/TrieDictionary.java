package kr.co.shineware.ds.trie;

import kr.co.shineware.ds.trie.model.TrieNode;

public class TrieDictionary<V> {
	private TrieNode<V> root;

	public TrieDictionary(){
		setRoot(new TrieNode<V>());
	}
	public TrieNode<V> getRoot() {
		return root;
	}
	public void setRoot(TrieNode<V> root) {
		this.root = root;
	}
	public void put(String keys,V value){
		this.put(keys.toCharArray(), value);
	}
	public void put(char[] keys,V value){
		TrieNode<V> node = root;
		TrieNode<V>[] children = null;

		for(int i=0;i<keys.length;i++){

			char key = keys[i];

			//get children
			children = node.getChildren();

			//if node has no children,			
			if(children == null){
				//insert current key initialization state
				node.setChildren(this.initNode(key));
				//move to child node
				node = node.getChildren()[0];
			}

			//if node has children,
			else{
				//retrieve children to find index.
				int idx = retrieveNode(children,key);
				//if children has no key.
				if(idx == -1){
					int head = 0;
					int tail = children.length-1;
					idx = 0;

					while(head<=tail){
						idx = (head+tail)/2;
						if(children[idx].getKey() < key){
							head = idx+1;
						}else if(children[idx].getKey() > key){
							tail = idx-1;
						}else if(children[idx].getKey() == key){
							break;
						}			
					}

					@SuppressWarnings("unchecked")
					TrieNode<V>[] newArray = new TrieNode[children.length + 1];
					System.arraycopy(children, 0, newArray, 0, head);
					newArray[head] = new TrieNode<V>();
					newArray[head].setKey(key);
					System.arraycopy(children, head, newArray, head+1, children.length-head);
					node.setChildren(newArray);
					idx = head;
				}
				node = node.getChildren()[idx];
			}
		}
		node.setValue(value);
	}

	public V get(String keys){
		return this.get(new FindContext<V>(root), keys.toCharArray());
	}

	public V get(FindContext<V> context, String keys) {
		return get(context, keys.toCharArray());
	}

	public V get(FindContext<V> context, char key){
		final TrieNode<V>[] children = context.getCurrentChildren();
		if (children == null) {
			return null;
		}

		final int idx = retrieveNode(children, key);
		if (idx == -1) {
			return null;
		}
		context.setCurrentNode(children[idx]);

		return context.getCurrentNode().getValue();
	}

	public V get(FindContext<V> context, char[] keys) {
		V value = null;
		for (final char eachKey : keys) {
			value = get(context, eachKey);
		}

		return value;
	}

	private int retrieveNode(TrieNode<V>[] children, char key) {
		int head = 0;
		int tail = children.length-1;
		int idx = 0;
		while(head<=tail){
			idx = (head+tail)/2;
			if(children[idx].getKey() < key){
				head = idx+1;
			}else if(children[idx].getKey() > key){
				tail = idx-1;
			}else if(children[idx].getKey() == key){
				return idx;
			}
		}
		return -1;
	}
	private TrieNode<V>[] initNode(char key) {
		@SuppressWarnings("unchecked")
		TrieNode<V>[] nodes = new TrieNode[1];
		nodes[0] = new TrieNode<V>();
		nodes[0].setKey(key);
		return nodes;
	}

	public void save(String filename){
		root.save(filename);
	}
	public void load(String filename){
		root.load(filename);
	}
}
