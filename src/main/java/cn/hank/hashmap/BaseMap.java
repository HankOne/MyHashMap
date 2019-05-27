package cn.hank.hashmap;

public interface BaseMap<K, V> {

	public void put(K k, V v);

	public V get(K k);

}
