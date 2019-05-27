package cn.hank.hashmap.impl;

import cn.hank.hashmap.BaseEntry;

public class Entry<K, V> implements BaseEntry<K, V> {

	K k;
	V v;
	Entry<K, V> next;


	public Entry(K k, V v, Entry<K, V> next) {
		super();
		this.k = k;
		this.v = v;
		this.next = next;
	}

	public K getK() {
		return k;
	}

	public V getV() {
		return v;
	}

}
