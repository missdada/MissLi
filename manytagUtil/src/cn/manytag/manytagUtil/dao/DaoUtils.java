package cn.manytag.manytagUtil.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cn.manytag.manytagUtil.util.Runnable;

public class DaoUtils {

	/**
	 * 批量执行sql
	 * 
	 * @param run
	 * @param objs
	 * @param amount
	 * @return
	 */
	public static <E> int batch(Runnable run, List<E> objs, int amount) {
		if (objs.isEmpty()) {
			return 0;
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("amount必须大于0");
		}

		Iterator<E> ite = objs.iterator();
		List<E> news = new ArrayList<E>(amount);
		int sum = 0;
		while (ite.hasNext()) {
			news.add(ite.next());
			if (news.size() == amount) {
				Object obj = run.run(news);
				sum += (int) obj;
				news.clear();
			}
		}
		if (!news.isEmpty()) {
			Object obj = run.run(news);
			sum += (int) obj;
		}
		return sum;
	}

	/**
	 * 批量执行sql
	 * 
	 * @param run
	 * @param obja
	 * @param amount
	 * @return
	 */
	public static <T> int batch(Runnable run, T[] obja, int amount) {
		if (obja.length == 0) {
			return 0;
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("amount必须大于0");
		}

		int sum = 0;
		for (int i = 0; i < obja.length; i += amount) {
			T[] ta = Arrays.copyOfRange(obja, i, obja.length > i + amount ? i + amount : obja.length);
			Object obj = run.run(ta);
			sum += (int) obj;
		}
		return sum;
	}
}
