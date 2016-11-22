package kr.co.shineware.ds.trie;

import kr.co.shineware.ds.trie.model.TrieNode;

/**
 * FindContext keeps state of trie traversal.
 *
 * @param <V> Trie dictionary item type
 */
public class FindContext<V> {
	private TrieNode<V> currentNode;

	public FindContext(final TrieNode<V> start) {
		setCurrentNode(start);
	}

	public void setCurrentNode(final TrieNode<V> node) {
		this.currentNode = node;
	}

	public TrieNode<V> getCurrentNode() {
		return currentNode;
	}

	public boolean hasCurrentChildren() {
		final TrieNode<V> [] children = currentNode.getChildren();
		return children != null && children.length > 0;
	}

	public TrieNode<V>[] getCurrentChildren() {
		return currentNode.getChildren();
	}
}
