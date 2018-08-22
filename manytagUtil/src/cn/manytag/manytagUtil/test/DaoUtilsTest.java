package cn.manytag.manytagUtil.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import cn.manytag.manytagUtil.dao.DaoUtils;
import cn.manytag.manytagUtil.util.Runnable;
import cn.manytag.manytagUtil.util.StringUtil;;

public class DaoUtilsTest {

	private static final Logger log = Logger.getLogger(DaoUtilsTest.class.toString());

	private Object o = new Object();

	@Test
	public void test0() {
		List<Integer> ints = new ArrayList<Integer>(3);
		ints.add(1);
		ints.add(2);
		ints.add(3);

		Runnable run = new Runnable() {
			@Override
			public Object run(Object obj) {
				@SuppressWarnings("unchecked")
				List<Integer> ints = (List<Integer>) obj;
				log.info(o.toString() + ints);
				return ints.size();
			}
		};

		int i = DaoUtils.batch(run, ints, 2);
		log.info(StringUtil.valueOfEmpty(i));
	}

	@Test
	public void test3() {
		Integer[] inta = new Integer[3];
		inta[0] = 1;
		inta[1] = 2;
		inta[2] = 3;

		Runnable run = new Runnable() {
			@Override
			public Object run(Object obj) {
				Integer[] inta = (Integer[]) obj;
				log.info(o.toString() + Arrays.toString(inta));
				return inta.length;
			}
		};

		int i = DaoUtils.batch(run, inta, 2);
		log.info(StringUtil.valueOfEmpty(i));
	}
}
