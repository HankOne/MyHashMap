package cn.hank.hashmap.demo;

import cn.hank.hashmap.impl.HashMap;

public class Test {

	public static void main(String[] args) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "A");
		map.put(2, "B");
		map.put(3, "C");
		map.put(4, "D");

		System.out.println(map.get(3));
		System.out.println(map.get(2));
		System.out.println(map.get(1));
	}
}
