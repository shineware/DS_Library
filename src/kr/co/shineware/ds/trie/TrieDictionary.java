package kr.co.shineware.ds.trie;

import kr.co.shineware.ds.trie.model.TrieNode;

public class TrieDictionary<V> {
	private TrieNode<V> root;
	private TrieNode<V> currentNode;
	private boolean hasChildren;
	public TrieDictionary(){
		setRoot(new TrieNode<V>());
		hasChildren = false;
		currentNode = null;
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
		return this.get(keys.toCharArray());
	}

	public V get(char key){
		hasChildren = false;
		if(currentNode == null){
			currentNode = root;
		}

		TrieNode<V>[] children = currentNode.getChildren();
		if(children == null){
			hasChildren = false;
			return null;
		}

		int idx = retrieveNode(children, key);
		if(idx == -1){
			hasChildren = false;
			return null;
		}
		currentNode = children[idx];

		if(currentNode.getChildren() == null){
			hasChildren = false;
		}else{
			hasChildren = true;
		}
		return currentNode.getValue();
	}

	public V get(char[] keys){
		hasChildren = false;
		TrieNode<V> node = root;
		for(int i=0;i<keys.length;i++){
			char key = keys[i];
			TrieNode<V>[] children = node.getChildren();
			if(children == null){
				hasChildren = false;
				return null;
			}

			int idx = retrieveNode(children, key);
			if(idx == -1){
				hasChildren = false;
				return null;
			}
			node = children[idx];
		}
		if(node.getChildren() == null){
			hasChildren = false;
		}else{
			hasChildren = true;
		}
		return node.getValue();
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
	public boolean hasChildren() {
		return hasChildren;
	}
	public TrieNode<V> getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(TrieNode<V> currentNode) {
		this.currentNode = currentNode;
	}
}
