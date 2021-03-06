package osc.git.eh3.cache;

import java.util.*;
import java.util.Map.Entry;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 管理缓存
 * </p>
 * Deep blue 2008-11-28 think
 * 可扩展的功能：当chche到内存溢出时必须清除掉最早期的一些缓存对象，这就要求对每个缓存对象保存创建时间
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Deepblue 2008-11-11
 * @version 1.0
 */
public class CacheManager {
	private static HashMap<String, Object> cacheMap = new HashMap<String, Object>();

	// 单实例构造方法
	private CacheManager() {
		super();
	}

	// 得到缓存。同步静态方法
	private synchronized static Cache getCache(String key) {
		return (Cache) cacheMap.get(key);
	}

	// 判断是否存在一个缓存
	private synchronized static boolean hasCache(String key) {
		return cacheMap.containsKey(key);
	}

	// 清除所有缓存
	public synchronized static void clearAll() {
		cacheMap.clear();
	}

	// 清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配
	public synchronized static void clearAll(String type) {
		Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
		String key;
		ArrayList<String> arr = new ArrayList<String>();
		try {
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				key = (String) entry.getKey();
				if (key.startsWith(type)) { // 如果匹配则删除掉
					arr.add(key);
				}
			}
			for (int k = 0; k < arr.size(); k++) {
				clearOnly(arr.get(k));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 清除指定的缓存
	public synchronized static void clearOnly(String key) {
		cacheMap.remove(key);
	}

	// 载入缓存
	public synchronized static void putCache(String key, Cache obj) {
		cacheMap.put(key, obj);
	}

	// 获取缓存信息
	public static Cache getCacheInfo(String key) {
		if (hasCache(key)) {
			Cache cache = getCache(key);
			if (cacheExpired(cache)) { // 调用判断是否终止方法
				cache.setExpired(true);
			}
			return cache;
		} else {
			return null;
		}
	}

	// 载入缓存信息
	public static void putCacheInfo(String key, Cache obj, long dt, boolean expired) {
		Cache cache = new Cache();
		cache.setKey(key);
		cache.setTimeOut(dt + System.currentTimeMillis()); // 设置多久后更新缓存
		cache.setValue(obj);
		cache.setExpired(expired); // 缓存默认载入时，终止状态为FALSE
		cacheMap.put(key, cache);
	}

	// 重写载入缓存信息方法
	public static void putCacheInfo(String key, Cache obj, long dt) {
		Cache cache = new Cache();
		cache.setKey(key);
		cache.setTimeOut(dt + System.currentTimeMillis());
		cache.setValue(obj);
		cache.setExpired(false);
		cacheMap.put(key, cache);
	}

	// 判断缓存是否终止
	public static boolean cacheExpired(Cache cache) {
		if (null == cache) { // 传入的缓存不存在
			return false;
		}
		long nowDt = System.currentTimeMillis(); // 系统当前的毫秒数
		long cacheDt = cache.getTimeOut(); // 缓存内的过期毫秒数
		if (cacheDt <= 0 || cacheDt > nowDt) { // 过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE
			return false;
		} else { // 大于过期时间 即过期
			return true;
		}
	}

	// 获取缓存中的大小
	public static int getCacheSize() {
		return cacheMap.size();
	}

	// 获取指定的类型的大小
	public static int getCacheSize(String type) {
		int k = 0;
		Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
		String key;
		try {
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				key = (String) entry.getKey();
				if (key.indexOf(type) != -1) { // 如果匹配则删除掉
					k++;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return k;
	}

	// 获取缓存对象中的所有键值名称
	public static ArrayList<String> getCacheAllkey() {
		ArrayList<String> a = new ArrayList<String>();
		try {
			Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				a.add((String) entry.getKey());
			}
		} catch (Exception ex) {
		}
		return a;
	}

	// 获取缓存对象中指定类型 的键值名称
	public static ArrayList<String> getCacheListkey(String type) {
		ArrayList<String> a = new ArrayList<String>();
		String key;
		try {
			Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				key = (String) entry.getKey();
				if (key.indexOf(type) != -1) {
					a.add(key);
				}
			}
		} catch (Exception ex) {
		}
		return a;
	}
}