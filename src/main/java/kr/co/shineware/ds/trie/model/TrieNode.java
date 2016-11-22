package kr.co.shineware.ds.trie.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import kr.co.shineware.ds.model.Node;

public class TrieNode<V> extends Node<Character, V>{
	private TrieNode<V>[] children;
	
	public TrieNode(){
		setChildren(null);
	}

	public TrieNode<V>[] getChildren() {
		return children;
	}

	public void setChildren(TrieNode<V>[] children) {
		this.children = children;
	}
	
	public void save(String filename) {
		ObjectOutputStream dos;
		try {
			dos = new ObjectOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(filename))));
			write(dos,true);
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void write(ObjectOutputStream dos,boolean isRoot) throws Exception {
		if(!isRoot){
			dos.writeChar(this.getKey());
			dos.writeObject(this.getValue());
		}
		if(children == null) {
			dos.writeInt(0);
		} else {
			dos.writeInt(children.length);
			for(int i=0; i<children.length; i++) {
				children[i].write(dos,false);
			}
		}
	}
	public void load(String filename) {
		ObjectInputStream dis;
		try {
			dis = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(filename))));
			load(dis,true);
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void load(ObjectInputStream dis,boolean isRoot) throws Exception {
		if(!isRoot){
			setKey(dis.readChar());
			setValue((V)dis.readObject());
		}
		int length = dis.readInt();
		if(length != 0){
			children = new TrieNode[length];
		}
		for(int i=0; i<length; i++) {
			children[i] = new TrieNode<V>();
			children[i].load(dis,false);
		}
	}
}
