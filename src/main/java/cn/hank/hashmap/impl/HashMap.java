package cn.hank.hashmap.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hank.hashmap.BaseMap;

public class HashMap<K, V> implements BaseMap<K, V> {
	// ��ʼ����
	public int defaultLength = 16;
	// ��������
	public double defaultAddFactor = 0.75;
	// ����
	public Entry<K, V>[] table;
	// Ŀǰʹ����
	public int useSize;

	public HashMap() {
		this(16, 0.75);
	}

	public HashMap(int defaultLength, double defaultAddFactor) {
		if (defaultLength < 0) {
			throw new RuntimeException("��ʼ�����쳣");
		}
		if (defaultAddFactor <= 0 || defaultAddFactor > 1) {
			throw new RuntimeException("���������쳣");
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
			// ʹ������1
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
		// �ж��Ƿ�����
		if (useSize > defaultAddFactor * table.length) {
			extend();
		}
	}

	// ����
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
