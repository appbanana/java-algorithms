package com.jqc.map;

/**
 * 映射接口
 * @param <K> 泛型
 * @param <V> 泛型
 */
public interface Map<K, V> {
	int size();
	boolean isEmpty();
	void clear();

	/**
	 * 添加 key: value
	 * @param key 键值
	 * @param value value
	 * @return
	 */
	V put(K key, V value);

	/**
	 * 由key获取对应value
	 * @param key
	 * @return
	 */
	V get(K key);

	/**
	 * 由key删除对应的node
	 * @param key
	 * @return
	 */
	V remove(K key);

	/**
	 * 是否包含某个key值
	 * @param key
	 * @return
	 */
	boolean containsKey(K key);

	/**
	 * 是否包含某个value值
	 * @param value
	 * @return
	 */
	boolean containsValue(V value);

	/**
	 * 遍历
	 * @param visitor
	 */
	void traversal(Visitor<K, V> visitor);
	
	public static abstract class Visitor<K, V> {
		boolean stop;
		public abstract boolean visit(K key, V value);
	}
}
