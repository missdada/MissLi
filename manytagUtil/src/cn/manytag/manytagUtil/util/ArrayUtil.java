package cn.manytag.manytagUtil.util;

import java.lang.reflect.Array;

public class ArrayUtil {
	/**
	 * 去除重复数据<br>
	 * 两个null也会去除一个
	 * 
	 * @param objArr 原数据，不对原数据做修改
	 * @return 去除重复后的数据
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] deduplication(T[] objArr) {
		T[] arrTemp = (T[]) Array.newInstance(objArr.getClass().getComponentType(), objArr.length);
		int arrNum = 0;
		int j;
		for (int i = 0; i < objArr.length; i++) {
			if (objArr[i] == null) {
				for (j = 0; j < arrNum; j++) {
					if (arrTemp[j] == null) {
						break;
					}
				}
			} else {
				for (j = 0; j < arrNum; j++) {
					if (objArr[i].equals(arrTemp[j])) {
						break;
					}
				}
			}
			if (j >= arrNum) {
				arrTemp[arrNum++] = objArr[i];
			}
		}
		T[] arrTemp2 = (T[]) Array.newInstance(objArr.getClass().getComponentType(), arrNum);
		System.arraycopy(arrTemp, 0, arrTemp2, 0, arrNum);
		return arrTemp2;
	}

	/**
	 * 判断数据是否重复<br>
	 * 
	 * @param objArr
	 * @return
	 */
	public static <T> boolean isDuplicateData(T[] objArr) {
		if (objArr == null) {
			return false;
		}
		int j;
		for (int i = 0; i < objArr.length - 1; i++) {
			if (objArr[i] == null) {
				for (j = i + 1; j < objArr.length; j++) {
					if (objArr[j] == null) {
						return true;
					}
				}
			} else {
				for (j = i + 1; j < objArr.length; j++) {
					if (objArr[i].equals(objArr[j])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断obj是否在objArr中存在
	 * 
	 * @return boolean
	 */
	public static <T> boolean isExist(T[] objArr, Object obj) {
		if (obj == null) {
			for (T t : objArr) {
				if (t == null) {
					return true;
				}
			}
		} else {
			for (T t : objArr) {
				if (obj.equals(t)) {
					return true;
				}
			}
		}
		return false;
	}
}
