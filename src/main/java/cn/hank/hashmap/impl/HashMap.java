package cn.hank.hashmap.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hank.hashmap.BaseMap;

public class HashMap<K, V> implements BaseMap<K, V> {
	// 初始容量
	public int defaultLength = 16;
	// 扩容因子
	public double defaultAddFactor = 0.75;
	// 数组
	public Entry<K, V>[] table;
	// 目前使用量
	public int useSize;

	public HashMap() {
		this(16, 0.75);
	}

	public HashMap(int defaultLength, double defaultAddFactor) {
		if (defaultLength < 0) {
			throw new RuntimeException("初始容量异常");
		}
		if (defaultAddFactor <= 0 || defaultAddFactor > 1) {
			throw new RuntimeException("负载因子异常");
		}

		this.defaultLength = defaultLength;
		this.defaultAddFactor = defaultAddFactor;
		this.table = new Entry[defaultLength];
	}

	public int hash(int hashcode) {
		return hashcode ^ (hashcode >>> 16);
	}

	public int getIndex(int hash, int length) {
		return hash & (length - 1);
	}

	public void put(K k, V v) {
		int index = getIndex(k.hashCode(), table.length);
		Entry<K, V> entry = table[index];
		if (entry == null) {
			table[index] = new Entry(k, v, null);
			// 使用量加1
			useSize++;
		} else {
			Entry<K, V> t = entry;
			while (t.next != null) {
				if (t.k == k || k.equals(t.getK())) {
					t.v = v;
					break;
				}
				t = t.next;
			}
			if (t.next == null) {
				t.next = new Entry(k, v, null);
			}
		}
		// 判断是否扩容
		if (useSize > defaultAddFactor * table.length) {
			extend();
		}
	}

	// 扩容
	private void extend() {
		Entry[] newEntry = new Entry[table.length * 2];
		List<Entry<K, V>> list = new ArrayList<Entry<K, V>>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			Entry<K, V> t = table[i];
			while (t != null) {
				list.add(t);
				t = t.next;
			}
		}

		if (list.size() > 0) {
			this.useSize = 0;
			defaultLength = defaultLength * 2;
			table = newEntry;
			for (Entry<K, V> entry : list) {
				if (entry.next != null) {
					entry.next = null;
				}
				this.put(entry.getK(), entry.getV());
			}
		}

	}

	public V get(K k) {
		int index = getIndex(k.hashCode(), table.length);
		Entry<K, V> entry = table[index];
		if (entry == null) {
			return null;
		}
		Entry<K, V> t = entry;
		while (t != null) {
			if (k == t.getK() || k.equals(t.getK())) {
				return t.getV();
			}
			t = t.next;
		}
		return null;
	}

}
